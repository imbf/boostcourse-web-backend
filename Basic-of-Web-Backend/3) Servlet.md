# 1) Servlet 이란? 

### 자바 웹 어플리케이션(Java Web Application)

**WAS에 설치(deploy)되어 동작하는 어플리케이션입니다.**

자바 웹 어플리케이션에는 HTML, CSS, 이미지, 자바로 작성된 클래스(Servlet도 포함됨, package, 인터페이스 등), 각종 설정 파일 등이 포함됩니다.

### 자바 웹 어플리케이션 폴더 구조

**자바 웹 어플리케이션은 WAS에 의해서 동작되기 때문에 WAS에서 정해진 약속을 지켜야 한다.**

![image-20210102221427420](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210102221427420.png)

**WEB-INF 폴더**

- **web.xml** : 배포 기술자라고 하며, 웹 어플리케이션에 대한 정보를 다 가지고 있는 파일이다. 
  - Servlet 3.0 미만에서는 필수로 존재해야만 했고 3.0 이상에서는 어노테이션을 사용해서 대체할 수 있다.

### 이클립스에서 실행된 Dynamic Web Project

- 이클립스에서 Dynamic Web Project의 Servlet을 실행하면, 해당 프로젝트가 이클립스가 관리하는 .metadata 폴더 아래에 자바 웹 어플리케이션 폴더 구조로 만들어져 실행됨

### Servlet이란?

- **자바 웹 어플리케이션의 구성요서 중 동적인 처리를 하느 프로그램의 역할**
- 서블릿을 정의 해보면
  - 서블릿은 WAS에서 동작하는 Java 클래스이다.
  - 서블릿은 HttpServlet 클래스를 상속받아야 한다.
  - 서블릿과 JSP로부터 최상의 결과를 얻으려면, 웹 페이지를 개발할 때 이 두가지(JSP, 서블릿)를 조화롭게 사용해야 한다.

### 생각해보기

1. 동적인 페이지가 필요한 경우는 어떤 것일까요?
   - 동적인 페이지가 필요한 경우는 아주 많지만, 대표적으로 사용자 별로 개인화된 웹 서비스에서 동적인 페이지가 꼭 필요할 것 같습니다.

---

## 2) Servlet 작성 방법

### Servlet 3.0을 기준으로 하는 서블릿 작성 방법

1. **Servlet 3.0 spec 이상에서 사용하는 방법**
   - web.xml 파일을 사용하지 않음
   - 자바 어노테이션(annotation)을 사용
2. **Servlet 3.0 spec 미만에서 사용하는 방법**
   - Servlet을 등록할 때 web.xml 파일에 등록

### Servlet 3.0 spec 이상에서 사용하는 방법

**실습 코드 (애노테이션으로 서블릿 설정)**

```java
package exam;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TenServlet
 */
@WebServlet("/ten")
public class TenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		
		printWriter.println("<h1>1~10까지 출력!</h1>");
		for(int i = 1; i <= 10; i++) {
			printWriter.println(i + "<br>");
		}
		printWriter.close();
	}

}
```

### Servlet 3.0 Spec 미만에서 사용하는 방법

**실습 코드(web.xml을 통해서 서블릿 설정)**

- **web.xml 코드**

  web.xml 파일을 참고해서 WAS가 요청된 URL에 맵핑되는 서블릿을 찾는다.
  서블릿 네임을 기반으로 해당 서블릿이 구현된 클래스를 찾는다.

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
    <display-name>exam25</display-name>
    <welcome-file-list>
      <welcome-file>index.html</welcome-file>
      <welcome-file>index.htm</welcome-file>
      <welcome-file>index.jsp</welcome-file>
      <welcome-file>default.html</welcome-file>
      <welcome-file>default.htm</welcome-file>
      <welcome-file>default.jsp</welcome-file>
    </welcome-file-list>  
    <servlet>
      <description></description>
      <display-name>TenServlet</display-name>
      <servlet-name>TenServlet</servlet-name>
      <servlet-class>exam.TenServlet</servlet-class>
    </servlet>
    <servlet-mapping>
      <servlet-name>TenServlet</servlet-name>
      <url-pattern>/ten</url-pattern>
    </servlet-mapping>
  </web-app>
  ```

- **TenServlet.java 코드**

  ```java
  package exam;
  
  import java.io.IOException;
  import java.io.PrintWriter;
  
  import javax.servlet.ServletException;
  import javax.servlet.http.HttpServlet;
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  
  /**
   * Servlet implementation class TenServlet
   */
  public class TenServlet extends HttpServlet {
  	private static final long serialVersionUID = 1L;
         
      /**
       * @see HttpServlet#HttpServlet()
       */
      public TenServlet() {
          super();
          // TODO Auto-generated constructor stub
      }
  
  	/**
  	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  	 */
  	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		response.setContentType("text/html;charset=utf-8");
  		PrintWriter printWriter = response.getWriter();
  		
  		printWriter.println("<h1>1~10까지 출력!</h1>");
  		for(int i = 1; i <= 10; i++) {
  			printWriter.println(i + "<br>");
  		}
  		printWriter.close();
  	}
  
  }
  
  ```

### 생각해보기

1. HelloServlet 클래스 파일을 작성할 때 HttpServlet을 상속하지 않았다면 어떻게 동작할까요? 
   - HttpServlet을 상속하지 않았다면 해당 클래스가 Servlet 명세를 구현한 클래스인지 WAS가 알지 못하며 당연히 정상적인 웹 어플리케이션의 서블릿 역할을 하지 못하게 된다.

---

## 3) Servlet 라이프 싸이클

### LifecycleServlet 실습

HttpServlet을 상속받는 LifecycleServlet 클래스를 만들어서 3가지의 메소드를 오버라이딩

- init()
- service(request, response)
- destroy()

### **Servlet 생명주기**

- **클라이언트로부터 첫 번째 요청 발생**
  1. Servlet 객체 생성
  2. init 호출
  3. service 호출
- **클라이언트로부터 재 요청 발생**
  - Servlet 객체가 만들어져 있다면 service 호출
- **메모리에 서블릿이 삭제되었을 때 Destory 호출**



### 그림을 통해 Servlet 생명주기 파악하기

![image-20210102232136443](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210102232136443.png)

- WAS는 서블릿 요청을 받으면 해당 서블릿이 메모리에 있는지 확인

- if(메모리에 없음) {

  - 해당 서블릿 클래스를 메모리에 올림 (객체 생성)
  - init() 메소드 실행

  }

  - service 메소드를 실행

- WAS가 종료되거나, 웹 어플리케이션이 새롭게 갱신될 경우 destory() 메소드가 실행됨

### HttpServlet의 service(request, response) 메소드

- **HttpServlet의 service메소드는 템플릿 메소드 패턴으로 구현.** 즉, service 메소드 내에 doGet과 doPost 등의 메소드가 존재한다.
  - 클라이언트의 요청이 GET일 경우에는 자신이 가지고 있는 doGet(request, response) 메소드를 호출
  - 클라이언트의 요청이 Post일 경우에는 자신이 가지고 있는 doPost(request, response)메소드를 호출

> **템플릿 메소드 패턴**
>
> 알고리즘의 구조를 바꾸지 않고 서브클래스에서 알고리즘의 특정 단계를 재정의하는 것을 의미한다.

- **실습 코드**

  ```java
  package examples;
  
  import java.io.IOException;
  import java.io.PrintWriter;
  
  import javax.servlet.ServletConfig;
  import javax.servlet.ServletException;
  import javax.servlet.annotation.WebServlet;
  import javax.servlet.http.HttpServlet;
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  
  
  @WebServlet("/LifecycleServlet")
  public class LifecycleServlet extends HttpServlet {
  	private static final long serialVersionUID = 1L;
         
   
      public LifecycleServlet() {
          System.out.println("LifecycleServlet 생성!!");
      }
  
  	public void init(ServletConfig config) throws ServletException {
  		System.out.println("init test 호출!!");
  	}
  
  	
  	public void destroy() {
  		System.out.println("destroy 호출!!");
  	}
  
  	@Override
  	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		response.setContentType("text/html");
  		PrintWriter out = response.getWriter();
  		out.println("<html>");
  		out.println("<head><title>form</title></head>");
  		out.println("<body>");
  		out.println("<form method='post' action='/firstweb/LifecycleServlet'>");
  		out.println("name : <input type='text' name='name'><br>");
  		out.println("<input type='submit' value='ok'><br>");                                                 
  		out.println("</form>");
  		out.println("</body>");
  		out.println("</html>");
  		out.close();
  	}
  
  	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		response.setContentType("text/html");
  		PrintWriter out = response.getWriter();
  		String name = request.getParameter("name");
  		out.println("<h1> hello " + name + "</h1>");
  		out.close();
  	}
  
  //	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  //		System.out.println("service 호출!!");
  //	}
  //	
  
  }
  ```

---

## 4) Request, Response 객체 이해하기

### 요청과 응답

- **WAS는 웹 브라우저로부터 Servlet 요청을 받으면**

  - 요청할 때 가지고 있는 정보를 HttpServletRequest 객체를 생성하여 저장
  - 웹 브라우저에게 응답을 보낼 때 사용하기 위하여 HttpServletResponse 객체를 생성
  - **생성된 HttpServletRequest, HttpServletResponse 객체를 서블릿에게 전달**

  ![image-20210102233452100](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210102233452100.png)

### HttpServletRequest

- **http 프로토콜의 request 정보를 서블릿에게 전달하기 위한 목적으로 사용**
- 헤더정보, 파라미터, 쿠키, URL, URI 등의 정보를 읽어 들이는 메소드를 가지고 있다.
- Body의 Stream을 읽어 들이는 메소드를 가지고 있다.

### HttpServletResponse

- **WAS는 어떤 클라이언트가 요청을 보냈는지 알고 있고, 해당 클라이언트에게 응답을 보내기 위한 HttpServletResponse 객체를 생성하여 서블릿에게 전달**
- 서블릿은 해당 객체를 이용하여 Content Type, 응답 코드, 응답 메시지 등을 전송

### 요청 헤더 정보 출력하는 Servlet 작성 실습

```java
package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HeaderServlet
 */
@WebServlet("/header")
public class HeaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeaderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>form</title></head>");
		out.println("<body>");
		
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			out.println(headerName + " : " + headerValue + " <br> ");
		}
		
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
```

### URL 요청 파라미터를 읽어 출력하는 Servlet 실습

```java
package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ParameterServlet
 */
@WebServlet("/param")
public class ParameterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParameterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>form</title></head>");
		out.println("<body>");
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		out.println("name : " + name + " <br> ");
		out.println("age : " + age + " <br> ");
		
		out.println("</body>");
		out.println("</html>");
	}
}
```

### 다양한 요청 정보를 읽어 출력하는 Servlet 실습

```java
package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ParameterServlet
 */
@WebServlet("/param")
public class ParameterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParameterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>form</title></head>");
		out.println("<body>");
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		out.println("name : " + name + " <br> ");
		out.println("age : " + age + " <br> ");
		
		out.println("</body>");
		out.println("</html>");
	}

}
```





