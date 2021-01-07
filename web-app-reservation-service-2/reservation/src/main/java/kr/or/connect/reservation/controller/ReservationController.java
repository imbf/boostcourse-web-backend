package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.or.connect.reservation.dto.CategoryWithDisplayInfoCount;
import kr.or.connect.reservation.dto.DisplayInfoImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionsWithProductAndCategory;
import kr.or.connect.reservation.dto.ReservationUserCommentWithUserAndFileInfo;
import kr.or.connect.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api")
public class ReservationController {
	
	@PostConstruct
	public void init() {
		System.out.println("Creadtion ReservationController Bean");
	}
	
	ReservationService reservationService;
	
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
    @ApiOperation(value = "카테고리 목록 구하기") // API에 대한 간단한 설명
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Exception")
    })
	@GetMapping("/categories")
	public Map<String, Object> getAllCategoryWithDisplayInfoCount() {
		Map<String, Object> result = new HashMap<>();
		
		List<CategoryWithDisplayInfoCount> lists = reservationService.getAllCategoryWithDisplayInfoCount();
		result.put("size", lists.size());
		result.put("items", lists);
		
		return result;
	}
    
    @ApiOperation(value = "상품 목록 구하기") // API에 대한 간단한 설명
    @ApiResponses({  // Response Message에 대한 Swagger 설명
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
    @Transactional(readOnly=true)
    @GetMapping("/displayinfos")
    public Map<String, Object> getProductsByCategoryId(
    		@RequestParam(name="categoryId", required=false, defaultValue="0") int categoryId,
    		@RequestParam(name="start", required=false, defaultValue="0") int start) {
    	Map<String, Object> result = new HashMap<>();
    	Integer totalCount = reservationService.getDisplayInfoCountByCategoryId(categoryId);
    	List<ProductWithDisplayInfoAndCategory> products = reservationService.getProductsWithDisplayInfoAndCategoryByCategoryId(categoryId, start);
		
    	result.put("totalCount", totalCount);
    	result.put("productCount", products.size());
		result.put("products", products);
		return result;
    }
    
    @ApiOperation(value = "프로모션 정보 구하기") // API에 대한 간단한 설명
    @ApiResponses({  // Response Message에 대한 Swagger 설명
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
    @GetMapping("/promotions")
    public Map<String, Object> getPromotions() {
    	Map<String, Object> result = new HashMap<>();
    	
    	List<PromotionsWithProductAndCategory> promotions = reservationService.getAllPromotionWithProductAndCategory();
    	result.put("size", promotions.size());
    	result.put("items", promotions);
    	return result;
    }
    
    @ApiOperation(value = "전시 정보 구하기") // API에 대한 간단한 설명
    @ApiResponses({  // Response Message에 대한 Swagger 설명
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
    @Transactional(readOnly=true)
    @GetMapping("/displayinfos/{displayId}")
    public Map<String, Object> getDisplayInfosById(@PathVariable("displayId") int displayId) {
    	Map<String, Object> result = new HashMap<>();
    	
    	ProductWithDisplayInfoAndCategory product = reservationService.getProductWithDisplayInfoAndCategoryByDisplayId(displayId);
    	int productId = product.getId();
    	result.put("product", product);
    	
    	List<ProductImageWithFileInfo> productImages = reservationService.getProductImagesWithFileInfoByProductId(productId);
    	result.put("productImages", productImages);
    	
    	List<DisplayInfoImageWithFileInfo> displayInfoImages = reservationService.getDisplayInfoImagesWithFileInfoByProductId(productId);
    	result.put("displayInfoImages", displayInfoImages);
    	
    	int avgScore = reservationService.getProductAvgScore(productId);
    	result.put("avgScore", avgScore);
    	
    	List<ProductPrice> productPrices = reservationService.getProductPricesByProductId(productId);
    	result.put("productPrices", productPrices);
    	
    	return result;
    }
    
    @ApiOperation(value = "댓글 목록 구하기") // API에 대한 간단한 설명
    @ApiResponses({  // Response Message에 대한 Swagger 설명
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
    @Transactional(readOnly=true)
    @GetMapping("/usercomments")
    public Map<String, Object> getReservationUserCommentsByProductId(
    		@RequestParam(name="productId", required=false, defaultValue="0") int productId,
    		@RequestParam(name="start", required=false, defaultValue="0") int start) {
    	Map<String, Object> result = new HashMap<>();
    	
    	Integer totalCount = reservationService.getReservationUserCommentCountByProductId(productId);
    	result.put("totalCount", totalCount);
    	
    	List<ReservationUserCommentWithUserAndFileInfo> reservationUserComments = 
    			reservationService.getReservationUserCommentsWithUserAndFileInfo(productId, start);
    	result.put("commentCount", reservationUserComments.size());
    	result.put("reservationUserComments", reservationUserComments);
    	
		return result;
    }
    
}