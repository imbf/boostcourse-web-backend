## 1) EL(Expression Language)

### EL(Expression Language)란?

- **Expression Language는 값을 표현하는 데 사용되는 스크립트 언어로서 JSP의 기본 문법을 보완하는 역할을 한다.**

### EL(Expression Language)가 제공하는 기능

- JSP의 스코프(scope)에 맞는 속성 사용
- 집합 객체에 대한 접근 방법 제공
- 수치 연산, 관계 연산, 논리 연산자 제공
- 자바 클래스 메소드 호출 기능 제공
- 표현언어만의 기본 객체 제공

### EL(Expression Language)의 표현 방법

- Expression Language는 JSP의 스크립트 요소를 제외한 나머지 부분에서 사용될 수 있으며, 표현식을 통해서 표현식보다 편리하게 값을 출력할 수 있다.

![image-20210103111228063](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103111228063.png)

### Expresion Language의 기본 객체

![image-20210103111335972](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103111335972.png)

### EL(Expression Language)의 데이터 타입

- 불리언 타입 - true와 false
- 정수타입 - 0~9로 이루어진 정수 값 음수의 경우 '-'가 붙음
- 실수타입 - 0~9로 이루어져 있으며, 소수점('.')을 사용할 수 있고, 3.24e3과 같이 지수형으로 표현 가능하다.
- 문자열 타입 - 따옴표( ' 또는" )로 둘러싼 문자열. 만약 작은 따옴표(')를 사용해서 표현할 경우 값에 포함된 작은 따옴표는 \' 와 같이 \ 기호와 함께 사용해야 한다.
- \ 기호 자체는 \\ 로 표시한다.
- 널 타입 - null

### 객체 접근 규칙

- **문법**
  - ${<표현1>.<표현2>}
- 표현 1이나 표현 2가 null이면 null을 반환한다.
- 표현1이 Map일 경우 표현2를 key로한 값을 반환한다.
- 표현1이 List나 배열이면 표현2가 정수일 경우 해당 정수 번째 index에 해당하는 값을 반환한다.
- 만약 정수가 아닐 경우에는 오류가 발생한다.
- 표현1이 객체일 경우는 표현2에 해당하는 getter메소드에 해당하는 메소드를 호출한 결과를 반환한다.

### EL(Expression Language)의 수치 연산자

- \+ : 덧셈
- \- : 뺄셈
- \* : 곱셈
- / 또는 div : 나눗셈
- % 또는 mod : 나머지
- 숫자가 아닌 객체와 수치 연산자를 사용할 경우 객체를 숫자 값으로 변환 후 연산자를 수행 : ${"10"+1} → ${10+1}
- 숫자로 변환할 수 없는 객체와 수치 연산자를 함께 사용하면 에러를 발생 : ${"열"+1} → 에러
- 수치 연산자에서 사용되는 객체가 null이면 0으로 처리 : ${null + 1} → ${0+1}

### 비교 연산자

- == 또는 eq
- != 또는 ne
- < 또는 lt
- \> 또는 gt
- <= 또는 le
- \>= 또는 ge
- 문자열 비교: ${str == '값'} str.compareTo("값") == 0 과 동일

### 논리 연산자

- && 또는 and
- || 또는 or
- ! 또는 not

### 연산자 우선순위

1. [] .
2. ()
3. \- (단일) not ! empty
4. \* / div % mod
5. \+ -
6. < > <= >= lt gt le ge
7. == != eq ne
8. && and
9. || or
10. ? :

### 표현 언어 비활성화 : JSP에 명시하기

- <%@ page isELIgnored = "true" %>
  - default 값은 false이니까 기본적으로 EL이 적용된다.

### Expression Language를 사용하여 Scope 실습하기

**실습 코드**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	pageContext.setAttribute("p1", "page scope value");
	request.setAttribute("r1", "request scope value");
	session.setAttribute("s1", "session scope value");
	application.setAttribute("a1", "application scope value");
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
pageContext.getAttribute("p1") : <%=pageContext.getAttribute("p1") %> <br> 
pageContext.getAttribute("p1") : ${pageScope.p1} <br>
request.getAttribute("p1") : ${requestScope.r1 } <br>
session.getAttribute("s1") : ${sessionScope.s1 } <br>
application.getAttribute("a1") : ${applicationScope.a1 } <br>
</body>
</html>
```



### Expression Language를 사용하여 사칙연산 or 논리연산 실습하기

**실습 코드**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("k", 10); 
request.setAttribute("m", true);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
k : ${requestScope.k }
k + 5 : ${k+5 } <br>
k - 5 : ${k-5 } <br>
k * 5 : ${k*5 } <br>
k / 5 : ${k div 5 } <br>

k : ${requestScope.k } <br>
m : ${requestScope.m } <br>
k > 5 : ${ k > 5 } <br>
k < 5 : ${ k < 5 } <br>
k <= 10 : ${ k <= 10} <br>
k >= 10 : ${ k >= 10 } <br>
m : ${ m } <br>
!m : ${ !m } <br>

</body>
</html>
```

### 생각해보기

1. **표현식으로 값을 출력하는 것과 EL을 이용해서 값을 출력하는 것 중 어떤 게 편해 보이나요? 혹시 표현식으로 표현하는 것이 좀 더 편리해 보인다 하더라도 실망하지 마세요. EL은 다음 시간에 배우게 될 JSTL과 함께 사용하면 더 효율적으로 사용할 수 있습니다.**
   - 개인적으로 자바 언어를 주로 사용하는 저에게는 표현식을 통해서 값을 출력하는 것이 좀 더 편리해 보입니다. 값을 참조하는 부분이나 여러 문법들이 자바 러버인 저에게는 조금 혼란을 주는 것 같기도 하네요!! 

---

## 2) JSTL 이란?

### JSTL이란?

- **JSTL(JSP Standard Tag Library)은 JSP 페이지에서 조건문 처리, 반복문 처리 등을 html tag형태로 작성할 수 있게 도와줍니다.**

![image-20210103114144232](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103114144232.png)

### STL을 사용하려면?

- http://tomcat.apache.org/download-taglibs.cgi
- 위의 사이트에서 3가지 jar파일을 다운로드 한 후 WEB-INF/lib/ 폴더에 복사를 한다.

![image-20210103114308444](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103114308444.png)

### **JSTL이 제공하는 태그의 종류**

![image-20210103114328498](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103114328498.png)

### **코어 태그**

![image-20210103114347453](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103114347453.png)

### 코어 태그: 변수 지원 태그 - set, remove

![image-20210103114441997](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103114441997.png)

### **실습코드**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="value1" scope="request" value="kang"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
성 : ${requestScope.value1 } <br>
<c:remove var="value1" scope="request"/>
성 : ${requestScope.value1 } <br>
</body>
</html>
```

### **코어태그: 변수 지원 태그 - 프로퍼티, 맵의 처리**

![image-20210103115208349](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103115208349.png)

### **코어 태그: 흐름제어 태그**

- 자바와는 다르게 else에 대한 처리는 없다.

![image-20210103115433231](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103115433231.png)

### 실습 코드

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <% 아래의 JSTL과 동일한 코드이다.
	request.setAttribute("n", 10);
%> --%>    
<c:set var="n" scope="request" value="10"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:if test="${n == 0 }">
	n과 0은 같습니다.
</c:if>

<c:if test="${n == 10 }">
	n과 10은 같습니다.
</c:if>

</body>
</html>
```

### 코어 태그: 흐름제어 태그 - choose

![image-20210103115810337](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103115810337.png)

### 실습 코드

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    

<%
	request.setAttribute("score", 83);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:choose>
	<c:when test="${score >= 90 }">
		A학점입니다.
	</c:when>
	<c:when test="${score >= 80 }">
		B학점입니다.
	</c:when>
	<c:when test="${score >= 70 }">
		C학점입니다.
	</c:when>
	<c:when test="${score >= 60 }">
		D학점입니다.
	</c:when>
	<c:otherwise>
		F학점입니다.
	</c:otherwise>
</c:choose>

</body>
</html>
```

### **코어 태그: 흐름제어 태그 - forEach**

begin과 end는 생략 가능하고 생략한다면 아이템의 처음부터 끝까지 반복 될 것이다.

![image-20210103120059489](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103120059489.png)

### 실습 코드

```jsp
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%
	List<String> list = new ArrayList<>();
	list.add("hello");
	list.add("world");
	list.add("!!!!!");
	
	request.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:forEach var="item" items="${requestScope.list }">
	${item} <br>
</c:forEach>

</body>
</html>
```

### 코어 태그: 흐름제어태그 - import

![image-20210103120710820](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103120710820.png)

### 실습 코드

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="http://localhost:8080/firstweb/jstlValue.jsp" var="urlValue" scope="request"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

${requestScope.urlValue }

</body>
</html>
```

### 코어 태그: 흐름제어태그 - redirect

![image-20210103121553701](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103121553701.png)

### 실습 코드

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:redirect url="http://localhost:8080/firstweb/jstl05.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
```

### 코어 태그: 기타태그 - out

![image-20210103121829692](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210103121829692.png)

### 실습 코드

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="t" value="<script type='text/javascript'>alert(1);</script>"/>

${t }
<c:out value="${t }" escapeXml="true"/>
</body>
</html>
```

**생각해보기**

1. JSTL의 태그는 XML태그 문법을 사용합니다. 그래서 반드시 태그가 닫히거나 '/>'로 끝나야 합니다. JSP가 제공하는 문법 중 사용자 정의 태그라는 것을 통해 만들어졌기 때문입니다. 이번 시간엔 JSTL의 core태그에 대해서 살펴봤는데요. 그 외의 태그들에는 어떤 게 있는지 찾아보고, 왜 그러한 태그가 필요한지 생각해보세요.
   - 일반적으로 JSTL 태그는 자바 코드를 사용하는 JSP 문법을 간단히(?) 사용하기 위해 만들어 졌다. 즉, JSTL 태그를 사용하더라도 자바 코드를 사용하는 JSP와 같이 로직들을 모두 표현할 수 있어야 하며 이를 위해 특정 기능을 가진 여러 태그를 지원하고 있다.

































