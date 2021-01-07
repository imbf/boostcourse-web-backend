package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusinessCardManagerDao {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/projectA?serverTimezone=UTC";
	private static final String DB_USER = "projectA";
	private static final String DB_PASSWORD = "projectA";
	
    public List<BusinessCard> searchBusinessCard(String keyword){
    	List<BusinessCard> businessCards = new ArrayList<>();
    	String sql = "SELECT * FROM business_card WHERE name LIKE ?";
    	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, "%" + keyword + "%");
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				while(resultSet.next()) {
					String name = resultSet.getString(1);
					String phone = resultSet.getString(2);
					String companyName = resultSet.getString(3);
					BusinessCard businessCard = new BusinessCard(name, phone, companyName);
					businessCard.setCreateDate(new Date(resultSet.getTimestamp(4).getTime()));
					businessCards.add(businessCard);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return businessCards;
    }

    public BusinessCard addBusinessCard(BusinessCard businessCard){
    	String sql = "INSERT INTO business_card VALUES (?, ?, ?, ?)";
    	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, businessCard.getName());
			preparedStatement.setString(2, businessCard.getPhone());
			preparedStatement.setString(3, businessCard.getCompanyName());
			preparedStatement.setTimestamp(4, new Timestamp(businessCard.getCreateDate().getTime()));
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return businessCard;
    }
}
