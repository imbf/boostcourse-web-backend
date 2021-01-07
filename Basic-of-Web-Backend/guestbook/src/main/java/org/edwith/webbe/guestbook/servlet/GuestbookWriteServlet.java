package org.edwith.webbe.guestbook.servlet;

import org.edwith.webbe.guestbook.dao.GuestbookDao;
import org.edwith.webbe.guestbook.dto.Guestbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/guestbooks/write")
public class GuestbookWriteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	
    	String name = (String) request.getParameter("name");
    	String content = (String) request.getParameter("content");
    	Guestbook guestbook = new Guestbook(name, content);
    	
    	GuestbookDao guestbookDao = new GuestbookDao();
    	guestbookDao.addGuestbook(guestbook);
    	
        response.sendRedirect("http://localhost:8080/guestbook/guestbooks");
    }

}

/*
 * Request로 들어오는 Content가 UTF-8로 Encoding 되지 않아서 인코딩 에러가 계속 발생했다.
 * 이를 없애기 위해 HttpServletRequest 객체에 문자 인코딩 필드로 UTF-8을 명시했고 정상적으로 출력 되었다.
 * 관련 코드가 눈에 보이지 않음으로 아마 WAS가 Encoding Set을 보고 처리해주는 것 같다. 좀 더 공부해 보아야 겠다.
 */