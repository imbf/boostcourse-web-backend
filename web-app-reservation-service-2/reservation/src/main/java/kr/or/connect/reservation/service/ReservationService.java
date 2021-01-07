package kr.or.connect.reservation.service;

import java.util.List;
import java.util.Map;

import kr.or.connect.reservation.dto.CategoryWithDisplayInfoCount;
import kr.or.connect.reservation.dto.DisplayInfoImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionsWithProductAndCategory;
import kr.or.connect.reservation.dto.ReservationUserCommentWithUserAndFileInfo;


public interface ReservationService {
	
	public List<CategoryWithDisplayInfoCount> getAllCategoryWithDisplayInfoCount();
	public Integer getDisplayInfoCountByCategoryId(int categoryId); 
	public List<ProductWithDisplayInfoAndCategory> getProductsWithDisplayInfoAndCategoryByCategoryId(int categoryId, int start);
	public List<PromotionsWithProductAndCategory> getAllPromotionWithProductAndCategory();
	public ProductWithDisplayInfoAndCategory getProductWithDisplayInfoAndCategoryByDisplayId(int displayId);
	public List<ProductImageWithFileInfo> getProductImagesWithFileInfoByProductId(int productId);
	public List<DisplayInfoImageWithFileInfo> getDisplayInfoImagesWithFileInfoByProductId(int productId);
	public int getProductAvgScore(int productId);
	public List<ProductPrice> getProductPricesByProductId(int productId);
	public Integer getReservationUserCommentCountByProductId(int productId);
	public List<ReservationUserCommentWithUserAndFileInfo> getReservationUserCommentsWithUserAndFileInfo(int productId, int start);
	
}
