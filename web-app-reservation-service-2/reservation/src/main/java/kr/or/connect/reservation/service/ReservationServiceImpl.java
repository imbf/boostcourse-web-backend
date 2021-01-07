package kr.or.connect.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.ReservationDao;
import kr.or.connect.reservation.dto.CategoryWithDisplayInfoCount;
import kr.or.connect.reservation.dto.DisplayInfoImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionsWithProductAndCategory;
import kr.or.connect.reservation.dto.ReservationUserCommentWithUserAndFileInfo;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	ReservationDao reservationDao;
	
	public ReservationServiceImpl(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	@Override
	public List<CategoryWithDisplayInfoCount> getAllCategoryWithDisplayInfoCount() {
		return reservationDao.getAllCategoryWithDisplayInfoCount();
	}
	
	@Override
	public Integer getDisplayInfoCountByCategoryId(int categoryId) {
		return reservationDao.getDisplayInfoCountByCategoryId(categoryId);
	}

	@Override
	public List<ProductWithDisplayInfoAndCategory> getProductsWithDisplayInfoAndCategoryByCategoryId(int categoryId, int start) {
		return reservationDao.getProductsWithDisplayInfoAndCategory(categoryId, start);
	}

	@Override
	public List<PromotionsWithProductAndCategory> getAllPromotionWithProductAndCategory() {
		return reservationDao.getAllPromotionWithProductAndCategory();
	}

	@Override
	public ProductWithDisplayInfoAndCategory getProductWithDisplayInfoAndCategoryByDisplayId(int displayId) {
		return reservationDao.getProductWithDisplayInfoAndCategory(displayId);
	}

	@Override
	public List<ProductImageWithFileInfo> getProductImagesWithFileInfoByProductId(int productId) {
		return reservationDao.getProductImagesWithFileInfoByProductId(productId);
	}

	@Override
	public List<DisplayInfoImageWithFileInfo> getDisplayInfoImagesWithFileInfoByProductId(int productId) {
		return reservationDao.getDisplayInfoImagesWithFileInfoByProductId(productId);
	}

	@Override
	public int getProductAvgScore(int productId) {
		return reservationDao.getProductAvgScore(productId);
	}

	@Override
	public List<ProductPrice> getProductPricesByProductId(int productId) {
		return reservationDao.getProductPricesByProductId(productId);
	}

	@Override
	public Integer getReservationUserCommentCountByProductId(int productId) {
		return reservationDao.getReservationUserCommentCountByProductId(productId);
	}

	@Override
	public List<ReservationUserCommentWithUserAndFileInfo> getReservationUserCommentsWithUserAndFileInfo(int productId, int start) {
		return reservationDao.getReservationUserCommentsWithUserAndFileInfo(productId, start);
	}

}
