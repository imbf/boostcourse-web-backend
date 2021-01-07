package kr.or.connect.reservation.dao;

public class ReservationDaoSqls {
	
	public static final String SELECT_ALL_CATEGORY_WITH_DISPLAY_INFO_COUNT =
			"SELECT category.id, category.name, COUNT(display_info.id) AS count "
			+ "FROM category "
			+ "INNER JOIN product ON product.category_id = category.id "
			+ "INNER JOIN display_info ON display_info.product_id = product.id "
			+ "GROUP BY category.id;";
	
	public static final String SELECT_DISPLAY_INFO_COUNT =
			"SELECT COUNT(*) "
			+ "FROM display_info "
			+ "INNER JOIN product ON display_info.product_id = product.id;";
	
	public static final String SELECT_DISPLAY_INFO_COUNT_BY_CATEGORY_ID =
			"SELECT COUNT(*) "
			+ "FROM display_info "
			+ "INNER JOIN product ON display_info.product_id = product.id "
			+ "WHERE product.category_id = :categoryId;";

	public static final String SELECT_PRODUCTS_WITH_DISPLAY_INFO_AND_CATEGORY = 
			"SELECT product.id, product.category_id, display_info.id AS display_info_id, category.name, product.description, product.content, "
			+ "product.event, display_info.opening_hours, display_info.place_name, display_info.place_lot, display_info.place_street, "
			+ "display_info.tel, display_info.homepage, display_info.email, display_info.create_date, display_info.modify_date, product_image.file_id "
			+ "FROM product "
			+ "INNER JOIN category ON category.id = product.category_id "
			+ "INNER JOIN display_info ON product.id = display_info.product_id "
			+ "INNER JOIN product_image ON product_image.product_id = product.id AND MOD(product_image.file_id, 2) = 1 "
			+ "WHERE product.category_id = :categoryId "
			+ "LIMIT :start, 1000 ;";

	public static final String SELECT_ALL_PROMOTION_WITH_PRODUCT_AND_CATEGORY =
			"SELECT promotion.id, promotion.product_id, category.id AS category_id, category.name AS category_name, "
			+ "product.description, product_image.file_id "
			+ "FROM promotion "
			+ "INNER JOIN product ON product.id = promotion.product_id "
			+ "INNER JOIN category ON category.id = product.category_id "
			+ "INNER JOIN product_image ON product_image.product_id = product.id "
			+ "WHERE product_image.type = 'ma';";

	public static final String SELECT_PRODUCT_WITH_DISPLAY_INFO_AND_CATEGORY_BY_DISPLAY_ID =
			"SELECT product.id, product.category_id, display_info.id AS display_info_id, category.name, product.description, product.content, "
			+ "product.event, display_info.opening_hours, display_info.place_name, display_info.place_lot, display_info.place_street, "
			+ "display_info.tel, display_info.homepage, display_info.email, display_info.create_date, display_info.modify_date, product_image.file_id "
			+ "FROM product "
			+ "INNER JOIN category ON category.id = product.category_id "
			+ "INNER JOIN display_info ON product.id = display_info.product_id "
			+ "INNER JOIN product_image ON product_image.product_id = product.id AND MOD(product_image.file_id, 2) = 1 "
			+ "WHERE display_info.id = :displayId; ";

	public static final String SELECT_PRODUCT_IMAGES_WITH_FILE_INFO_BY_PRODUCT_ID =
			"SELECT product_image.product_id, product_image.id AS product_image_id, product_image.type, product_image.file_id AS file_info_id, "
			+ "file_info.file_name, file_info.save_file_name, file_info.content_type, file_info.delete_flag, file_info.create_date, file_info.modify_date "
			+ "FROM product_image "
			+ "INNER JOIN file_info ON file_info.id = product_image.file_id "
			+ "WHERE product_image.product_id = :productId ;";

	public static final String SELECT_DISPLAY_INFO_IMAGES_WITH_FILE_INFO_BY_PRODUCT_ID =
			"SELECT display_info.product_id AS id, display_info_image.display_info_id, display_info_image.file_id, file_info.id AS file_id, "
			+ "file_info.file_name, file_info.save_file_name, file_info.content_type, file_info.delete_flag, file_info.create_date, file_info.modify_date "
			+ "FROM display_info_image "
			+ "INNER JOIN display_info ON display_info.id = display_info_image.display_info_id "
			+ "INNER JOIN file_info ON file_info.id = display_info_image.file_id "
			+ "WHERE display_info.product_id = :productId ;";

	public static final String SELECT_PRODUCT_AVG_SCORE =
			"SELECT AVG(score) FROM reservation_user_comment WHERE product_id = :productId;";

	public static final String SELECT_PRODUCT_PRICES_BY_PRODUCT_ID = 
			"SELECT * FROM product_price WHERE product_id = :productId;";

	public static final String SELECT_RESERVATION_USER_COMMENT_COUNT_BY_PRODUCT_ID = 
			"SELECT COUNT(*) FROM reservation_user_comment WHERE product_id = :productId;";

	public static final String SELECT_RESERVATION_USER_COMMENTS_WITH_USER_AND_FILE_INFO =
			"SELECT reservation_user_comment.id, reservation_user_comment.product_id, reservation_user_comment.reservation_info_id, "
			+ "reservation_user_comment.score, user.email AS reservation_email, reservation_user_comment.comment, "
			+ "reservation_user_comment.create_date, reservation_user_comment.modify_date, file_info.* "
			+ "FROM reservation_user_comment "
			+ "INNER JOIN user ON user.id = reservation_user_comment.user_id "
			+ "LEFT JOIN reservation_user_comment_image ON reservation_user_comment_image.reservation_user_comment_id = reservation_user_comment.id "
			+ "LEFT JOIN file_info ON file_info.id = reservation_user_comment_image.file_id "
			+ "WHERE reservation_user_comment.product_id = :productId "
			+ "ORDER BY reservation_user_comment.id DESC "
			+ "LIMIT :start, 5 ;";
	
}
