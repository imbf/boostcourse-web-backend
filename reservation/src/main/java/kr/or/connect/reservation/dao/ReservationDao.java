package kr.or.connect.reservation.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.CategoryWithDisplayInfoCount;
import kr.or.connect.reservation.dto.DisplayInfoImageWithFileInfo;
import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.dto.ProductImageWithFileInfo;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ProductWithDisplayInfoAndCategory;
import kr.or.connect.reservation.dto.PromotionsWithProductAndCategory;
import kr.or.connect.reservation.dto.ReservationUserCommentWithUserAndFileInfo;

@Repository
public class ReservationDao { 
	
	private NamedParameterJdbcTemplate jdbc;
	
	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<CategoryWithDisplayInfoCount> getAllCategoryWithDisplayInfoCount() {
		RowMapper<CategoryWithDisplayInfoCount> rowMapper = BeanPropertyRowMapper.newInstance(CategoryWithDisplayInfoCount.class);
		return jdbc.query(ReservationDaoSqls.SELECT_ALL_CATEGORY_WITH_DISPLAY_INFO_COUNT, rowMapper);
	}

	public int getDisplayInfoCountByCategoryId(int categoryId) {
		if (categoryId == 0) {
			return jdbc.queryForObject(ReservationDaoSqls.SELECT_DISPLAY_INFO_COUNT, EmptySqlParameterSource.INSTANCE ,Integer.class);
		}
		
		Map<String, Integer> params = Collections.singletonMap("categoryId", categoryId);
		return jdbc.queryForObject(ReservationDaoSqls.SELECT_DISPLAY_INFO_COUNT_BY_CATEGORY_ID, params, Integer.class);
	}

	public List<ProductWithDisplayInfoAndCategory> getProductsWithDisplayInfoAndCategory(int categoryId, int start) {
		RowMapper<ProductWithDisplayInfoAndCategory> rowMapper = BeanPropertyRowMapper.newInstance(ProductWithDisplayInfoAndCategory.class);
		Map<String, Object> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("start", start);
		return jdbc.query(ReservationDaoSqls.SELECT_PRODUCTS_WITH_DISPLAY_INFO_AND_CATEGORY, params, rowMapper);
	}

	public List<PromotionsWithProductAndCategory> getAllPromotionWithProductAndCategory() {
		RowMapper<PromotionsWithProductAndCategory> rowMapper = BeanPropertyRowMapper.newInstance(PromotionsWithProductAndCategory.class);
		return jdbc.query(ReservationDaoSqls.SELECT_ALL_PROMOTION_WITH_PRODUCT_AND_CATEGORY, rowMapper);
		
	}

	public ProductWithDisplayInfoAndCategory getProductWithDisplayInfoAndCategory(int displayId) {
		RowMapper<ProductWithDisplayInfoAndCategory> rowMapper = BeanPropertyRowMapper.newInstance(ProductWithDisplayInfoAndCategory.class);
		Map<String, Object> params = Collections.singletonMap("displayId", displayId);
		// Single Column이 아니니까 당연히 에러가 발생할 수 밖에 없다. rowMapper로 바꾸어주자.
		return jdbc.queryForObject(ReservationDaoSqls.SELECT_PRODUCT_WITH_DISPLAY_INFO_AND_CATEGORY_BY_DISPLAY_ID, params, rowMapper);
	}

	public List<ProductImageWithFileInfo> getProductImagesWithFileInfoByProductId(int productId) {
		RowMapper<ProductImageWithFileInfo> rowMapper = BeanPropertyRowMapper.newInstance(ProductImageWithFileInfo.class);
		Map<String, Object> params = Collections.singletonMap("productId", productId);
		return jdbc.query(ReservationDaoSqls.SELECT_PRODUCT_IMAGES_WITH_FILE_INFO_BY_PRODUCT_ID, params, rowMapper);
	}

	public List<DisplayInfoImageWithFileInfo> getDisplayInfoImagesWithFileInfoByProductId(int productId) {
		RowMapper<DisplayInfoImageWithFileInfo> rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfoImageWithFileInfo.class);
		Map<String, Object> params = Collections.singletonMap("productId", productId);
		return jdbc.query(ReservationDaoSqls.SELECT_DISPLAY_INFO_IMAGES_WITH_FILE_INFO_BY_PRODUCT_ID, params, rowMapper);
	}

	public int getProductAvgScore(int productId) {
		Map<String, Object> params = Collections.singletonMap("productId", productId);
		return jdbc.queryForObject(ReservationDaoSqls.SELECT_PRODUCT_AVG_SCORE, params, Integer.class);
	}

	public List<ProductPrice> getProductPricesByProductId(int productId) {
		RowMapper<ProductPrice> rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);
		Map<String, Object> params = Collections.singletonMap("productId", productId);
		return jdbc.query(ReservationDaoSqls.SELECT_PRODUCT_PRICES_BY_PRODUCT_ID, params, rowMapper);
	}

	public Integer getReservationUserCommentCountByProductId(int productId) {
		Map<String, Object> params = Collections.singletonMap("productId", productId);
		return jdbc.queryForObject(ReservationDaoSqls.SELECT_RESERVATION_USER_COMMENT_COUNT_BY_PRODUCT_ID, params, Integer.class);
	}

	public List<ReservationUserCommentWithUserAndFileInfo> getReservationUserCommentsWithUserAndFileInfo(int productId, int start) { 
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("start", start);
		return jdbc.query(ReservationDaoSqls.SELECT_RESERVATION_USER_COMMENTS_WITH_USER_AND_FILE_INFO, params, (resultSet, row) -> {
			ReservationUserCommentWithUserAndFileInfo reservationUserComment = new ReservationUserCommentWithUserAndFileInfo();
			reservationUserComment.setId(resultSet.getInt("id"));
			reservationUserComment.setProductId(resultSet.getInt("product_id"));
			reservationUserComment.setReservationInfoId(resultSet.getInt("reservation_info_id"));
			reservationUserComment.setScore(resultSet.getInt("score"));
			reservationUserComment.setReservationEmail(resultSet.getString("reservation_email"));
			reservationUserComment.setComment(resultSet.getString("comment"));
			reservationUserComment.setCreateDate(resultSet.getDate("create_date"));
			reservationUserComment.setModifyDate(resultSet.getDate("modify_date"));
			List<FileInfo> reservationUserCommentImages = new ArrayList<>();
			reservationUserComment.setReservationUserCommentImages(reservationUserCommentImages);

			int fileId = resultSet.getInt("file_info.id");
			if(fileId == 0) {
				return reservationUserComment;
			}
			
			FileInfo file = new FileInfo();
			file.setId(fileId);
			file.setFileName(resultSet.getString("file_info.file_name"));
			file.setSaveFileName(resultSet.getString("file_info.save_file_name"));
			file.setContentType(resultSet.getString("file_info.content_type"));
			file.setDeleteFlag(resultSet.getInt("file_info.delete_flag"));
			file.setCreateDate(resultSet.getDate("file_info.create_date"));
			file.setModifyDate(resultSet.getDate("file_info.modify_date"));
			reservationUserCommentImages.add(file);
			
			return reservationUserComment;
		});
	}
}
