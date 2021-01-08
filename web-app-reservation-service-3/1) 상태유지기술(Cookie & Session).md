## 1) 상태정보란?

### **웹에서의 상태 유지 기술**

- **HTTP프로토콜은 상태 유지가 안되는 프로토콜입니다. => (Stateless)**
  - 이전에 무엇을 했고, 지금 무엇을 했는지에 대한 정보를 갖고 있지 않습니다.
  - 웹 브라우저(클라이언트)의 요청에 대한 응답을 하고 나면 해당 클라이언트와의 연결을 지속하지 않습니다.
- **상태 유지를 위해 Cookie와 Session기술이 등장합니다.**



### 쿠키(Cookie)와 세션(Session)

- **쿠키 (Cookie)**
  - 사용자 컴퓨터(로컬)에 저장
  - 저장된 정보를 다른 사람 또는 시스템이 볼 수 있는 단점 (공용 컴퓨터)
  - 유효시간이 지나면 사라짐
- **세션 (Session)**
  - 서버에 저장
  - 서버가 종료되거나 유효시간이 지나면 사라집니다.



### 쿠키(Cookie) 동작 이해

![image-20210108104844908](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210108104844908.png)

**쿠키 동작**

1. 클라이언트가 서버에 요청한다.
2. 서버에서 쿠키를 생성한다.
3. 서버에서 클라이언트에게 응답 헤더(Set-Cookie)에 쿠키를 실어서 전송한다.
4. 클라이언트는 로컬에 쿠키를 저장해 놓고 다음 요청에 쿠키를 포함해서 서버에 전송한다.
5. 서버는 쿠키를 받아서 유효성 검사를 하고 이에 알맞는 서비스를 응답한다.



### 세션의 동작 이해

![image-20210108111625221](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210108111625221.png)

**세션 동작 이해**

1. 클라이언트가 서버에 요청한다.
2. 서버에서 세션키를 생성한다.
3. 서버에서 세션키를 이용한 저장소를 생성한다.
4. 서버에서 세션키를 담은 Cookie를 생성한다.
5. 서버에서 클라이언트에게 응답 헤더에 세션키를 담은 쿠키를 실어서 전송한다.
6. 클라이언트는 로컬에 쿠키를 저장해 놓고 다음 요청에 쿠키(세션키)를 실어 서버에게 전송한다.
7. 서버는 요청의 쿠키(세션키)를 이용해 이전에 생성한 저장소를 활용해서 이에 알맞는 서비스를 응답한다.



### **생각해보기**

1. HTTP프로토콜은 상태가 유지 안 되는 프로토콜이라고 합니다. 웹 클라이언트가 서버와 연결을 지속하지 않기 때문인데요. 상태가 유지되는 프로토콜엔 어떤 것이 있을까요?
   1. stateful한 protocol은 POP3, FRTP, SMTP등이 있다.

---

## 2) 쿠키(Cookie)

### 쿠키(Cookie)

- **정의**
  - **클라이언트 단에 저장되는 작은 정보의 단위입니다.**
  - 클라이언트에서 생성하고 저장될 수 있고, 서버 단에서 전송한 쿠키가 클라이언트에 저장될 수 있습니다.
- **이용 방법**
  - **서버에서 클라이언트의 브라우저로 전송되어 사용자의 컴퓨터에 저장합니다.**
  - 저장된 쿠키는 다시 해당하는 웹 페이지에 접속할 때, 브라우저에서 서버로 쿠키를 전송합니다.
  - **쿠키는 이름(name)과 값(value) 쌍으로 정보를 저장합니다.**
  - 이름-값 쌍 외에도 도메인(Domain), 경로(Path), 유효기간(Max-Age, Expires), 보안(Secure), HttpOnly 속성을 저장할 수 있습니다.
- **쿠키는 수와 크기에 제한**
  - 하나의 쿠키는 4K Byte 크기로 제한
  - 브라우저는 각각의 웹사이트 당 20개의 쿠키를 허용
  - 모든 웹 사이트를 합쳐 최대 300개를 허용 그러므로 클라이언트 당 쿠키의 최대 용량은 1.2M Byte이다.

### 

### **javax.servlet.http.Cookie**

- **서버에서 쿠키 생성, Reponse의 addCookie메소드를 이용해 클라이언트에게 전송**

  ```java
  Cookie cookie = new Cookie(이름, 값);
  response.addCookie(cookie);
  ```

  - 쿠키는 (이름, 값)의 쌍 정보를 입력하여 생성합니다.
  - 쿠키의 이름은 일반적으로 알파벳과 숫자, 언더바로 구성합니다. 정확한 정의를 알고 싶다면 RFC 6265(https://tools.ietf.org/html/rfc6265) 문서 [4.1.1 Syntax] 항목을 참조하세요.
  - 한글을 사용하고 싶다면 Encoding과 Decoding을 해야만 한다.

- **클라이언트가 보낸 쿠키 정보 읽기**

  ```java
  Cookie[] cookies = request.getCookies();
  ```

  - 쿠키 값이 없으면 **null**이 반환됩니다. (NPE Error에 대처해야만 한다.)
  - Cookie가 가지고 있는 getName()과 getValue()메소드를 이용해서 원하는 쿠키정보를 찾아 사용합니다.

- **클라이언트에게 쿠키 삭제 요청**

  - 쿠키를 삭제하는 명령은 없고, maxAge가 0인 같은 이름의 쿠키를 전송합니다.

    - 같은 이름의 쿠키는 클라이언트에 유지될 수 없다는 점을 활용해서 작성하는 코드

    ```java
    Cookie cookie = new Cookie("이름", null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
    ```

  - **쿠키의 유효기간 설정**

    - 메소드 setMaxAge()
      - 인자는 유효기간을 나타내는 초 단위의 정수형
      - 만일 유효기간을 0으로 지정하면 쿠키의 삭제
      - 음수를 지정하면 브라우저가 종료될 때 쿠키가 삭제
    - 유효기간을 10분으로 지정하려면
      - cookie.setMaxAge(10 * 60); //초 단위 : 10분
      - 1주일로 지정하려면 (7*24*60*60)로 설정합니다.



### **Spring MVC에서의 Cookie 사용**

- **@CookieValue 애노테이션 사용**
  - 컨트롤러 메소드의 파라미터에서 CookieValue애노테이션을 사용함으로써 원하는 쿠키정보를 파라미터 변수에 담아 사용할 수 습니다.
- 컨트롤러메소드(@CookieValue(value="쿠키이름", required=false, defaultValue="기본값") String 변수명)



### **생각해보기**

1. 이번 시간엔 쿠키에 대해 배웠습니다. 쿠키는 브라우저별로 웹 사이트당 정해진 개수가 있다는 것을 알았습니다. 쿠키를 만든 사람은 왜 이런 제약을 주었을까요?
   - 클라이언트는 브라우저에 존재하는 모든 쿠키의 정보를 요청에 담아 전송하기 때문에 쿠키의 용량, 갯수, 시간 제한이 없다면 http 프로토콜 및 서버측의 부담이 심해지기 때문이다.

---

## 3) 쿠키를 이용한 상태정보 유지하기

**GuestbookApiController.java**

```java
package kr.or.connect.guestbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.service.GuestbookService;

@Controller
public class GuestbookController {
   
	@Autowired
	GuestbookService guestbookService;

	@GetMapping(path="/list")
	public String list(@RequestParam(name="start", required=false, defaultValue="0") int start, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String value = null;
		boolean find = false;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("count".equals(cookie.getName())) {
					find = true;
					value = cookie.getValue();
				}
			}
		}
		
		if(!find) {
			value = "1";
		}else { // 쿠키를 찾았다면.
			try {
				int i = Integer.parseInt(value);
				value = Integer.toString(++i);
			}catch(Exception ex) {
				value = "1";
			}
		}
		
		Cookie cookie = new Cookie("count", value);
		cookie.setMaxAge(60 * 60 * 24 * 365); // 1년 동안 유지.
		cookie.setPath("/"); // / 경로 이하에 모두 쿠키 적용. 
		response.addCookie(cookie);
		
		List<Guestbook> list = guestbookService.getGuestbooks(start);
		
		int count = guestbookService.getCount();
		int pageCount = count / GuestbookService.LIMIT;
		if(count % GuestbookService.LIMIT > 0)
			pageCount++;
		
		List<Integer> pageStartList = new ArrayList<>();
		for(int i = 0; i < pageCount; i++) {
			pageStartList.add(i * GuestbookService.LIMIT);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageStartList", pageStartList);
		model.addAttribute("cookieCount", value); // jsp에게 전달하기 위해서 쿠키 값을 model에 담아 전송한다.
		
		return "list";
	}
	
	@PostMapping(path="/write")
	public String write(@ModelAttribute Guestbook guestbook,
						HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		System.out.println("clientIp : " + clientIp);
		guestbookService.addGuestbook(guestbook, clientIp);
		return "redirect:list";
	}
}
```



**list.jsp 에서 방명록 전체 수 옆에 방문한 수를 출력하는 el 코드를 추가합니다.**

- 방명록 전체 수 : ${count }
- 방문한 수 : ${cookieCount }<br><br>

### Spring MVC가 제공하는 CookieValue 애노테이션 이용해서 리팩터링

```java
package kr.or.connect.guestbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.service.GuestbookService;

@Controller
public class GuestbookController {
	@Autowired
	GuestbookService guestbookService;

  	@GetMapping(path="/list")
	public String list(@RequestParam(name="start", required=false, defaultValue="0") int start,
					   ModelMap model, @CookieValue(value="count", defaultValue="1", required=true) String value,
					   HttpServletResponse response) {
		
        // 쿠키 값을 1증가 시킨다.
		try {
			int i = Integer.parseInt(value);
			value = Integer.toString(++i);
		}catch(Exception ex){
			value = "1";
		}
		
        // 쿠키를 전송한다.
		Cookie cookie = new Cookie("count", value);
		cookie.setMaxAge(60 * 60 * 24 * 365); // 1년 동안 유지.
		cookie.setPath("/"); // / 경로 이하에 모두 쿠키 적용. 
		response.addCookie(cookie);
		
		List<Guestbook> list = guestbookService.getGuestbooks(start);
		
		int count = guestbookService.getCount();
		int pageCount = count / GuestbookService.LIMIT;
		if(count % GuestbookService.LIMIT > 0)
			pageCount++;
		
		List<Integer> pageStartList = new ArrayList<>();
		for(int i = 0; i < pageCount; i++) {
			pageStartList.add(i * GuestbookService.LIMIT);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageStartList", pageStartList);
		model.addAttribute("cookieCount", value); // 쿠키를 추가한다.
		
		return "list";
	}
	
	@PostMapping(path="/write")
	public String write(@ModelAttribute Guestbook guestbook,
						HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		System.out.println("clientIp : " + clientIp);
		guestbookService.addGuestbook(guestbook, clientIp);
		return "redirect:list";
	}
}
```



---

## 4) Session 이란?

### 세션(Session)

- **정의**
  - 클라이언트 별로 서버에 저장되는 정보입니다.
- **이용방법**
  - 웹 클라이언트가 서버측에 요청을 보내게 되면 서버는 **클라이언트를 식별하는 session id를 생성**합니다.
  - 서버는 session id를 이용해서 key와 value를 이용한 **저장소인 HttpSession**을 생성합니다.
  - **서버는 session id를 저장하고 있는 쿠키를 생성하여 클라이언트에 전송합니다.**
    - 결국엔 Session도 Cookie를 사용
  - 클라이언트는 서버측에 **요청을 보낼때 session id를 가지고 있는 쿠키를 전송**합니다.
  - **서버는 쿠키에 있는 session id를 이용해서 그 전 요청에서 생성한 HttpSession을 찾고 사용합니다.**



### 세션(Session) 생성 및 얻기

```java
HttpSession session = request.getSession();
HttpSession session = request.getSession(true);
```

- request의 getSession()메소드는 서버에 생성된 세션이 있다면 세션을 반환하고 없다면 새롭게 세션을 생성하여 반환합니다.
- 새롭게 생성된 세션인지는 HttpSession이 가지고 있는 isNew()메소드를 통해 알 수 있습니다.

```java
HttpSession session = request.getSession(false);
```

- request의 getSession()메소드에 파라미터로 false를 전달하면, 이미 생성된 세션이 있다면 반환하고 없으면 null을 반환합니다.



### 세션에 값 저장

```java
setAttribute(String name, Object value)
```

- name과 value의 쌍으로 객체 Object를 저장하는 메소드입니다.
- 세션이 유지되는 동안 저장할 자료를 저장합니다.

```java
session.setAttribute(이름, 값)
```



### 세션에 값 조회

- **getAttribute(String name) 메소드**

  - 세션에 저장된 자료는 다시 getAttribute(String name) 메소드를 이용해 조회합니다.
  - 반환 값은 Object 유형이므로 저장된 객체로 자료유형 변환이 필요합니다.
  - 메소드 setAttribute()에 이용한 name인 “id”를 알고 있다면 바로 다음과 같이 바로 조회합니다.

  ```java
  String value = (String) session.getAttribute("id");
  ```



### **세션에 값 삭제**

- removeAttribute(String name) 메소드
  - name값에 해당하는 세션 정보를 삭제합니다.
- invalidate() 메소드
  - 모든 세션 정보를 삭제합니다.



### **javax.servlet.http.HttpSession**

![image-20210108120305191](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210108120305191.png)

![image-20210108120322126](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210108120322126.png)



### **javax.servlet.http.HttpSession**

**세션은 클라이언트가 서버에 접속하는 순간 생성**

- 특별히 지정하지 않으면 세션의 유지 시간은 기본 값으로 30분 설정합니다.

- 세션의 유지 시간이란 서버에 접속한 후 서버에 요청을 하지 않는 최대 시간입니다.

- 30분 이상 서버에 전혀 반응을 보이지 않으면 세션이 자동으로 끊어집니다.

- 이 세션 유지 시간은 web.xml파일에서 설정 가능합니다.

  ```java
  <session-config>
    <session-timeout>30</session-timeout>
  </session-config>
  ```

  

### **생각해보기**

1. 쿠키는 이름과 값이 모두 문자열이지만, 세션의 키값은 문자열이고 값은 객체를 저장할 수 있습니다. 왜 이런 차이가 있을까요?
   - 쿠키는 클라이언트의 로컬에 저장되고 세션은 자바 어플리케이션 내부의 저장소에 저장되기 때문에 이러한 차이가 존재합니다.

---

## 2) 세션 실습

### 요구사항

- /guess로 요청을 하면 컴퓨터가 1부터 100 사이의 임의의 값 중의 하나를 맞춰보라는 메시지가 출력합니다.
- 해당 값은 세션에 저장합니다.
- 사용자는 1부터 100 사이의 값을 입력합니다.
- 입력한 값이 세션 값보다 작으면, 입력한 값이 작다고 출력합니다.
- 입력한 값이 세션 값보다 크면, 입력한 값이 크다고 출력합니다.
- 입력한 값이 세션 값과 같다면 몇 번째에 맞췄다고 출력합니다.

**GuessNumberController.java**

```java
package kr.or.connect.guestbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GuessNumberController {
	
	@GetMapping("/guess")
	public String guess(@RequestParam(name="number", required=false) Integer number, HttpSession session, ModelMap model) {
		String message = null;
		if(number == null) {
			session.setAttribute("count", 0);
			session.setAttribute("randomNumber", (int)(Math.random()*100) + 1);
			message = "내가 생각한 숫자를 맞춰보세요";
		} else {
			int count = (Integer) session.getAttribute("count");
			int randomNumber = (Integer) session.getAttribute("randomNumber");
			
			if(number < randomNumber) {
				message = "입력한 값은 내가 생각하고 있는 숫자보다 작습니다.";
				session.setAttribute("count", ++count);
			} else if(number > randomNumber) {
				message = "입력한 값은 내가 생각하고 있는 숫자보다 큽니다.";
				session.setAttribute("count", ++count);
			} else {
				message = "OK ^^ " + ++count + "번째 맞췄습니다. 내가 생각한 숫자는 " + number + "입니다.";
				session.removeAttribute("count");
				session.removeAttribute("randomNumber");
			}
		}
		
		model.addAttribute("message", message);
		return "guess";
	}
}

```

**guess.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>숫자 맞추기 게임</title>
</head>
<body>
	<h1> 숫자 맞추기 게임.</h1>
	<hr>
	<h3>${message }</h3>

	<c:if test="${sessionScope.count != null}">
		<form method="get" action="guess">
			1부터 100사이의 숫자로 맞춰주세요.<br>
			<input type="text" name="number"><br>
			<input type="submit" value="확인">
		</form>
	</c:if>

<a href="guess">게임 다시 시작하기.</a>
</body>
</html>
```





















