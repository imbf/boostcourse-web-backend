## 1) Scope란?

### 4가지 Scope

- **Application :** <u>웹 어플리케이션이 시작되고 종료될 때까지 변수가 유지</u>되는 경우 사용
- **Session :** <u>웹 브라우저 별로 변수가 관리</u>되는 경우 사용
  - 상태 유지를 위해서 사용하는 Scope
- **Request :** <u>http요청을 WAS가 받아서 웹 브라우저에게 응답할 때까지 변수가 유지</u>되는 경우 사용
- **Page :** 페이지 내에서 <u>지역변수</u>처럼 사용

![image-20210103100627727](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103100627727.png)

### 생각해보기

1. **회사 관리 프로그램을 만듭니다. 회사 전체적으로 사용되는 특정 정보가 약 1메가 정도 있습니다. 이 정보는 자주 바뀌지 않고, 모든 사원들이 공통적으로 사용하는 코드입니다. 이러한 정보는 어떤 scope로 사용하는 것이 좋을까요?**
   - 위의 정보는 Application Scope를 사용하면 좋을 것 같다.

---

## 2) Page Scope

### **Page Scope**

- PageContext 추상 클래스를 사용한다.
- **JSP 페이지에서 pageContext라는 내장 객체로 사용 가능 하다.**
- **forward가 될 경우 해당 Page scope에 지정된 변수는 사용할 수 없다.**
  - **다른 페이지로 이동 될 경우 Page Scope에 지정된 변수는 사용할 수 없다.**
- 사용방법은 Application scope나 Session scope, request scope와 같다.
- **마치 지역변수처럼 사용된다는 것이 다른 Scope들과 다릅니다.**
- **jsp에서 pageScope에 값을 저장한 후 해당 값을 EL표기법 등에서 사용할 때 사용됩니다. 지역 변수처럼 해당 jsp나 서블릿이 실행되는 동안에만 정보를 유지하고자 할 때 사용됩니다.**

**생각해보기**

1. **page scope를 이용하여 사용되는 변수와 지역변수는 거의 유사합니다. 하지만, 앞으로 배울 EL과 JSTL을 이용할 경우에는 구분되어 사용될 경우가 발생합니다. page scope와 지역변수가 유사하다고 하였는데요. 어떤 부분에서 유사할까요?**
   - page scope 변수와 지역 변수 모두 _jspService()  메소드 내에 위치하고 있음으로 메모리에 올라가고 내려가는 시점이 비슷하다.

---

## 3) Request Scope

### Request Scope

- **http 요청을 WAS가 받아서 웹 브라우저에게 응답할 때까지 변수 값을 유지하고자 할 경우 사용한다.**
- **HttpServletRequest 객체를 사용한다.**
- JSP에서는 request 내장 변수를 사용한다.
  - _jspService 메소드의 인자인 HttpServletRequest형 변수 이름이 request 이기 때문이다.
- 서블릿에서는 HttpServletRequest객체를 사용한다.
  - 직접 해당 타입 객체를 사용하면 된다.
- 값을 저장할 때는 request 객체의 setAttribute() 메소드를 사용한다.
- 값을 읽어들일 때는 request 객체의 getAttribute() 메소드를 사용한다.
- forward시 값을 유지하고자 사용한다.
- 앞에서 forward에 대하여 배울 때 forward하기 전에 request객체의 setAttribute() 메소드로 값을 설정한 후, 서블릿이나 jsp에게 결과를 전달하여 값을 출력하도록 하였는데 이렇게 포워드 되는 동안 값이 유지되는 것이 Request Scope를 이용했다고 합니다.

### 생각해보기

1. 리다이렉트 될 경우에도 request scope를 이용하면 정보를 유지할 수 있을까요?
   - 유지할 수 없습니다.
2. 만약 정보를 유지할 수 없다면 이유가 무엇일까요?
   - 리다이렉트는 동일한 요청이 아니라 새로운 요청에 의해서 발생되는 것이기 때문에 request scope 범위에서 벗어납니다.

---

## 4)  Session Scope

### Session Scope

- **웹 브라우저(클라이언트)별로 변수를 관리하고자 할 경우 사용한다.**
  - **세션 객체는 브라우저의(클라이언트) 상태 정보를 유지하기 위해서는 사용한다.**
- **웹 브라우저간의 탭 간에는 세션정보가 공유되기 때문에, 각각의 탭에서는 같은 세션정보를 사용할 수 있다.**
- HttpSession 인터페이스를 구현한 객체를 사용한다.
- JSP에서는 session 내장 변수를 사용한다.
- 서블릿에서는 HttpServletRequest의 getSession()메소드를 이용하여 session 객체를 얻는다.
- 값을 저장할 때는 session 객체의 setAttribute()메소드를 사용한다.
- 값을 읽어 들일 때는 session 객체의 getAttribute()메소드를 사용한다.
- 장바구니처럼 사용자별로 유지가 되어야 할 정보가 있을 때 사용한다.

**생각해보기**

1. **session scope는 어떤 경우에 이용하는 것이 좋을까요? 예를 한번 생각해보세요.**
   - 사용자 별로 상태 정보를 저장하고 싶은 경우, 클라이언트 별로 활동 정보를 저장하고 싶은 경우 등이 있을 것 같다.

---

## 5) Application Scope

### **Application Scope**

- **웹 어플리케이션이 시작되고 종료될 때까지 변수를 사용할 수 있다.**
  - 웹 어플리케이션의 예시로서 예제로 만든 firstweb이라는 프로젝트를 들 수 있다.

- ServletContext 인터페이스를 구현한 객체를 사용한다.
- jsp에서는 application 내장 객체를 이용한다.
- 서블릿의 경우는 getServletContext()메소드를 이용하여 application객체를 이용한다.
- 웹 어플리케이션 하나당 하나의 application객체가 사용된다.
- 값을 저장할 때는 application객체의 setAttribute()메소드를 사용한다.
- 값을 읽어 들일 때는 application객체의 getAttribute()메소드를 사용한다.
- **모든 클라이언트가 공통으로 사용해야 할 값들이 있을 때 사용한다.**

---

## 6) Application Scope 실습

- ApplicationScope01, ApplicationScope02 서블릿 2개를 생성한다.

- ApplicationScope01.jsp를 생성한다.

- 이 3개의 모듈을 통해서 Application Scope를 실습한다.

  - **ApplicationScope01 서블릿 실습 코드**

    ```java
    package examples;
    
    import java.io.IOException;
    import java.io.PrintWriter;
    
    import javax.servlet.ServletContext;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    
    /**
     * Servlet implementation class ApplicationScope01
     */
    @WebServlet("/ApplicationScope01")
    public class ApplicationScope01 extends HttpServlet {
    	private static final long serialVersionUID = 1L;
           
        /**
         * @see HttpServlet#HttpServlet()
         */
        public ApplicationScope01() {
            super();
            // TODO Auto-generated constructor stub
        }
    
    	/**
    	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    	 */
    	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		response.setContentType("text/html; charset=UTF-8");
    		
    		PrintWriter out = response.getWriter();
    		
    		ServletContext application = getServletContext();
    		int value = 1;
    		application.setAttribute("Value", value);
    		
    		out.println("<h1>value : " + value + "</h1>");
    	}
    }
    ```

  - **ApplicationScope02 서블릿 실습 코드**

    ```java
    package examples;
    
    import java.io.IOException;
    import java.io.PrintWriter;
    
    import javax.servlet.ServletContext;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    
    /**
     * Servlet implementation class ApplicationScope02
     */
    @WebServlet("/ApplicationScope02")
    public class ApplicationScope02 extends HttpServlet {
    	private static final long serialVersionUID = 1L;
           
        /**
         * @see HttpServlet#HttpServlet()
         */
        public ApplicationScope02() {
            super();
            // TODO Auto-generated constructor stub
        }
    
    	/**
    	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    	 */
    	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		response.setContentType("text/html; charset=UTF-8");
    		
    		PrintWriter out = response.getWriter();
    	
    		ServletContext application = getServletContext();
    		try {
    			int value = (int) application.getAttribute("value");
    			value++;
    			application.setAttribute("value", value);
    			
    			out.println("<h1>value : " + value + "</h1>");
    		} catch (NullPointerException e) {
    			out.print("value의 값이 설정되지 않았습니다.");
    		}
    	}
    }
    ```

  - **ApplicationScope01 JSP 실습 코드**

    ```jsp
    <%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html>
    <head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    </head>
    <body>
    <%
    	try {
    		int value = (int) getServletContext().getAttribute("value");
    		value = value + 2;
    		application.setAttribute("value", value);
    	
    %>
    
    	<h1><%= value %></h1>
    
    <%
    	} catch (NullPointerException e) {
    %>
    		<h1>설정된 값이 없습니다.</h1>
    <%
    	}
    %>
    </body>
    </html>
    ```

### 생각해보기

1. **어플리케이션 스코프에 List객체를 저장했습니다. 이 List객체에 주기적으로 값이 저장은 되지만, 삭제는 되지 않고 있습니다. 이 경우 어떤 문제가 발생할 수 있을까요?**
   - 일단 List는 인터페이스 이기때문에 문제의 원인이 되지는 않을 것 같다는 생각이 들고, List를 구현하는 구현체에 따라 다양한 메모리 문제가 발생할 수 있을 것 같습니다.  기본적으로 jvm에서 자바 어플리케이션의 메모리를 관리하기 때문에 너무 많은 Element가 List 구현체에 저장된다면 예외를 뱉어낼 것입니다.











































