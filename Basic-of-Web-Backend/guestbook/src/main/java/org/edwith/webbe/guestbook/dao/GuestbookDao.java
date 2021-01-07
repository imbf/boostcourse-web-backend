package org.edwith.webbe.guestbook.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.edwith.webbe.guestbook.dto.Guestbook;
import org.edwith.webbe.guestbook.util.DBUtil;

public class GuestbookDao {
	
    public List<Guestbook> getGuestbooks(){
        List<Guestbook> list = new ArrayList<>();
        String sql = "SELECT * FROM guestbook;";
    	
        try(Connection connection = DBUtil.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	try(ResultSet resultSet = preparedStatement.executeQuery()) {
        		while(resultSet.next()) {
        			Long id = resultSet.getLong(1);
        			String name = resultSet.getString(2);
        			String content = resultSet.getString(3);
        			Date date = resultSet.getDate(4);
        			list.add(new Guestbook(id, name, content, date));
        		}
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }

    public void addGuestbook(Guestbook guestbook){
        String sql = "INSERT INTO guestbook(name, content, regdate) VALUES(?, ?, ?);";
    	
        try(Connection connection = DBUtil.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	preparedStatement.setString(1, guestbook.getName());
        	preparedStatement.setString(2, guestbook.getContent());
        	preparedStatement.setDate(3, new Date(guestbook.getRegdate().getTime()));
        	preparedStatement.executeUpdate();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
