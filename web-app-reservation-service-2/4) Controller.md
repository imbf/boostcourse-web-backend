## 1) RestController 란?

### @RestController

- **Spring 4 에서 Rest API 또는 Web API를 개발하기 위해 등장한 애노테이션합니다.**
- **이전 버전의 @Controller와 @ResponseBody를 포함합니다.**
- 즉, 이 애노테이션을 사용함으로써 REST API를 쉽게 개발할 수 있다.



### **MessageConvertor**

- **자바 객체와 HTTP 요청 / 응답 바디를 변환하는 역할**
- **@ResponseBody(응답 바디 변환), @RequestBody(요청 바디 변환)**
- **@EnableWebMvc 로 인한 기본 설정**
- **WebMvcConfigurationSupport 를 사용하여 Spring MVC 구현**
  - 하지만 기본적인 @EnableWebMvc가 적용되지 않는다.
- **Default MessageConvertor 를 제공**
- [링크 바로가기](https://github.com/spring-projects/spring-framework/blob/master/spring-webmvc/src/main/java/org/springframework/web/servlet/config/annotation/WebMvcConfigurationSupport.java) 의 addDefaultHttpMessageConverters메소드 항목 참조



### MessageConvertor 종류

![image-20210105225441371](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105225441371.png)

> ### **클래스패스(Class Path)란?**
>
> **클래스패스란 말 그대로 클래스를 찾기위한 경로이다.** 자바에서 클래스패스의 의미도 똑같다. **즉, JVM이 프로그램을 실행할 때, 클래스파일을 찾는 데 기준이 되는 파일 경로를 말하는 것이다.** 소스 코드(.java로 끝나는 파일)를 컴파일하면 소스 코드가 “바이트 코드”(바이너리 형태의 .class 파일)로 변환된다. java runtime(java 또는 jre)으로 이 .class 파일에 포함된 명령을 실행하려면, 먼저 이 파일을 찾을 수 있어야 한다. 이때 **.class 파일을 찾을 때 classpath에 지정된 경로를 사용한다.** classpath는 .class 파일이 포함된 디렉토리와 파일을 콜론으로 구분한 목록이다. java runtime은 이 classpath에 지정된 경로를 모두 검색해서 특정 클래스에 대한 코드가 포함된 .class 파일을 찾는다. 찾으려는 클래스 코드가 포함된 .class 파일을 찾으면 첫 번째로 찾은 파일을 사용한다.
>
> classpath를 지정할 수 있는 두 가지 방법이 있다. 하나는 환경 변수 CLASSPATH를 사용하는 방법이고, 또 하나는 java runtime에 -classpath 플래그를 사용하는 방법이다. (-classpath 플래그 사용에 대한 자세한 설명은 java 메뉴얼 페이지를 참조하라.)

### JSON 응답하기

- **컨트롤러의 메소드에서는 JSON으로 변환될 객체를 반환합니다.**
- **jackson라이브러리를 추가할 경우 객체를 JSON으로 변환하는 메시지 컨버터가 사용되도록 @EnableWebMvc에서 기본으로 설정되어 있습니다.**
- jackson라이브러리를 추가하지 않으면 JSON메시지로 변환할 수 없어 500오류가 발생합니다.
  - Json 으로 Convert 시키고 싶다면 반드시 Jackson 라이브러리를 추가하자.
- **사용자가 임의의 메시지 컨버터(MessageConverter)를 사용하도록 하려면 WebMvcConfigurerAdapter의 configureMessageConverters메소드를 오버라이딩 하도록 합니다.**



### **생각해보기**

1. **Web API에서 JSON메시지를 자주 사용하는 이유는 무엇일까요?**
   1. JSON메시지는 자바스크립트의 문법을 채용했기 때문에, 이러한 데이터 형식은 자바스크립트를 자주 사용하는 웹 환경에서 편리하게 사용할 수 있어 WEB API에서 JSON메시지를 사용합니다.
2. **JSON메시지의 장점에 대해 찾아보세요.**
   - JSON은 텍스트로 이루어져 있으므로, 사람과 기계 모두 읽고 쓰기 쉽다.
   - 프로그래밍 언어와 플랫폼에 독립적이므로, 서로 다른 시스템간에 객체를 교환하기에 좋다.
   - 자바스크립트의 문법을 채용했기 때문에, 자바스크립트에서 `eval` 명령으로 곧바로 사용할 수 있다. <u>이런 특성은 자바스크립트를 자주 사용하는 웹 환경에서 유리하다.</u>

---

## 2) RestController를 이용하여 web api작성하기

**GuestbookApiController.java**

```java
package kr.or.connect.guestbook.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.service.GuestbookService;

@RestController
@RequestMapping(path="/guestbooks")
public class GuestbookApiController {
	
	@Autowired
	GuestbookService guestbookService;
	
	@GetMapping
	public Map<String, Object> list(@RequestParam(name="start", required=false, defaultValue="0") int start) {
		
		List<Guestbook> list = guestbookService.getGuestbooks(start);
		
		int count = guestbookService.getCount();
		int pageCount = count / GuestbookService.LIMIT;
		if(count % GuestbookService.LIMIT > 0)
			pageCount++;
		
		List<Integer> pageStartList = new ArrayList<>();
		for(int i = 0; i < pageCount; i++) {
			pageStartList.add(i * GuestbookService.LIMIT);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		map.put("pageStartList", pageStartList);
		
		return map;
	}
	
	@PostMapping
	public Guestbook write(@RequestBody Guestbook guestbook, HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		
		Guestbook resultGuestbook = guestbookService.addGuestbook(guestbook, clientIp);
		return resultGuestbook;
	}
	
	@DeleteMapping("/{id}")
	public Map<String, String> delete(@PathVariable(name="id") Long id, HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		
		int deleteCount = guestbookService.deleteGuestbook(id, clientIp);
		return Collections.singletonMap("success", deleteCount > 0 ? "true" : "false");
	}

}
```

**GuestbookApiController를 만들었다면 그 다음 Postman으로 API를 테스트한다.**

### **생각해보기**

1. Web API에게 POST방식으로 값을 전달할 때 JSON메시지를 보냈고, 결과도 JSON메시지를 출력하도록 하였습니다. JSON메시지를 자바객체로 변환하고, 자바객체를 반대로 JSON메시지로 변화하는 부분들이 모두 자동으로 이뤄지고 있는 것을 알 수 있었습니다. 만약 서블릿을 이용해 개발한다면, 이 부분들을 어떻게 구현해야 할까요?
   - 직접 Request객체의 Body와 Response객체의 Body를 Serialize 또는 Deserialize할 수 있는  MessageConverter를 구현해야만 한다.
2. 이를 통해 Spring MVC의 장점에 대해 생각해보세요.
   - 라이브러리 의존성만 추가하는 것으로 많은 기능을 기본적으로 지원해주기 때문에 편리하다.

---

## 2) Web API 테스트 코드 작성하기

### 개발자가 수동으로 Web API를 테스트할 경우 발생하는 문제점

- 개발자의 수동 테스트
- 코드를 수정한 후에 서버를 재시작하고 테스트하기

### 문제 해결 방법

- JUnit 사용하기
- MockMVC 사용하기



### 1. MockMVC란?

우리는 웹 애플리케이션을 작성한 후, 해당 웹 애플리케이션을 Tomcat이라는 이름의 WAS(Web Application Server)에 배포(deploy)하여 실행을 하였습니다. 

브라우저의 요청은 WAS에게 전달되는 것이고 응답도 WAS에게서 받게 됩니다.

WAS는 요청을 받은 후, 해당 요청을 처리하는 웹 어플리케이션을 실행하게 됩니다.

즉, Web API를 테스트한다는 것은 WAS를 실행해야만 된다는 문제가 있습니다.

이런 문제를 해결하기 위해서 스프링 3.2부터 MockMVC가 추가되었습니다.

MockMVC는 WAS와 같은 역할을 수행합니다.

**요청을 받고 응답을 받는 WAS와 같은 역할을 수행하면서 여러분들이 작성한 웹 애플리케이션을 실행해줍니다.**

WAS는 실행 시 상당한 많은 작업을 수행합니다.

**MockMVC는 웹 어플리케이션을 실행하기 위한 최소한의 기능만을 가지고 있습니다. 그렇기 때문에 MockMVC를 이용한 웹 어플리케이션 실행은 상당히 빠릅니다.**

**MockMVC를 이용해서 할 수 있는 테스트**

![image-20210106090213554](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210106090213554.png)



### 2. 예제를 통해 알아보는 MockMVC Test

전 시간에 만든 GuestbookApiController를 테스트하는 GuestbookApiControllerTest 클래스를 작성해보도록 하겠습니다.

**pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>kr.or.connect</groupId>
  <artifactId>guestbook</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>

  <name>guestbook Maven Webapp</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>


  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <spring.version>4.3.5.RELEASE</spring.version>
    <jackson2.version>2.8.6</jackson2.version>
  </properties>

  <dependencies>
  
  	<!-- Test Framework Dependency -->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
    </dependency>
    
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>1.9.5</version>
    </dependency>
    
    <!-- Servlet JSP & JSTL -->
    
	<dependency>
    	<groupId>javax.servlet</groupId>
    	<artifactId>javax.servlet-api</artifactId>
    	<version>3.1.0</version>
    	<scope>provided</scope>
    </dependency>
    
    <dependency>
    	<groupId>javax.servlet.jsp</groupId>
    	<artifactId>javax.servlet.jsp-api</artifactId>
    	<version>2.3.1</version>
    	<scope>provided</scope>
    </dependency>
  	
  	<dependency>
		<groupId>jstl</groupId>
		<artifactId>jstl</artifactId>
		<version>1.2</version>
	</dependency>
    
    <!--  spring -->
	<dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>${spring.version}</version>
    </dependency>
    
  	<dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>${spring.version}</version>
    </dependency>
    
 	<dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${spring.version}</version>
    </dependency>
    
    <!-- spring jdbc & jdbc driver & connection pool -->
    <dependency>
    	<groupId>org.springframework</groupId>
    	<artifactId>spring-jdbc</artifactId>
    	<version>${spring.version}</version>
    </dependency>
    
    <dependency>
    	<groupId>org.springframework</groupId>
    	<artifactId>spring-tx</artifactId>
    	<version>${spring.version}</version>
    </dependency>
    
	<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>8.0.18</version>
	</dependency>
	
	<!-- basic data source -->
	<dependency>
    	<groupId>org.apache.commons</groupId>
    	<artifactId>commons-dbcp2</artifactId>
    	<version>2.1.1</version>
    </dependency>
    
    <!-- jackson module -->
    <dependency>
    	<groupId>com.fasterxml.jackson.core</groupId>
    	<artifactId>jackson-databind</artifactId>
    	<version>${jackson2.version}</version>
    </dependency>
	
	<dependency>
    	<groupId>com.fasterxml.jackson.datatype</groupId>
    	<artifactId>jackson-datatype-jdk8</artifactId>
    	<version>${jackson2.version}</version>
    </dependency>

  </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.6.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

```

**GuestbookApiControllerTest.java**

```java
package kr.or.connect.guestbook.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.or.connect.guestbook.config.ApplicationConfig;
import kr.or.connect.guestbook.config.WebMvcContextConfiguration;
import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.service.GuestbookService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebMvcContextConfiguration.class, ApplicationConfig.class})
public class GuestbookApiControllerTest {
	
	@InjectMocks
	public GuestbookApiController guestbookApiController;
	
	@Mock
	GuestbookService guestbookService;
	
	private MockMvc mockMvc;
	
	@Before
	public void createController() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(guestbookApiController).build();
	}
	
	@Test
	public void getGuestbooks() throws Exception {
		Guestbook guestbook1 = new Guestbook();
		guestbook1.setId(1L);
		guestbook1.setRegdate(new Date());
		guestbook1.setContent("hello");
		guestbook1.setName("kim");
		
		List<Guestbook> list = Arrays.asList(guestbook1);
		when(guestbookService.getGuestbooks(0)).thenReturn(list);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/guestbooks").contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print());
		
		verify(guestbookService).getGuestbooks(0);
	}
	
	@Test
	public void deleteGuestbook() throws Exception {
		Long id = 1L;
		when(guestbookService.deleteGuestbook(id, "127.0.0.1")).thenReturn(1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/guestbooks/" + id).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print());
		
		verify(guestbookService).deleteGuestbook(id, "127.0.0.1");
	}
}
```

- **@Mock**
  **GuestbookService guestbookService;**
  - @Mock어노테이션을 붙여서 선언된 guestbookService는 mockito에 위해 목객체로 생성됩니다. 말그대로 가짜 객체가 됩니다.
- **@InjectMocks**
  **public GuestbookApiController guestbookApiController;**
  - @InjectMocks어노테이션이 붙여서 선언된 guestbookApiController는 목객체인 GuestbookService를 사용하게 됩니다. 스프링에 위해 주입된 객체를 사용하는 것이 아닌 Mockito 프레임워크에 위해 생성된 목객체가 주입되어 객체가 생성됩니다.
- **MockitoAnnotations.initMocks(this);**
  - 현재 객체에서 @Mock이 붙은 필드를 목객체로 초기화시킵니다.
- **mockMvc = MockMvcBuilders.standaloneSetup(guestbookApiController).build();**
  - MockMVC타입의 변수 mockMvc를 초기화 합니다. guestbookApiController를 테스트 하기 위한 MockMvc객체를 생성합니다.
- **List\<Guestbook> list = Arrays.asList(guestbook1);**
  **when(guestbookService.getGuestbooks(0)).thenReturn(list);**
  - guestbookService.getGuestbook(0) 이 호출되면 위에서 선언된 list객체가 리턴 되도록 설정합니다.
- **RequestBuilder reqBuilder  = MockMvcRequestBuilders.get("/guestbooks").contentType(MediaType.APPLICATION_JSON);**
  - application/json형식으로 /guestbooks를 GET방식으로 호출한다는 것을 뜻합니다. 이러한 URL정보를 가진 reqBuilder를 생성합니다
- **mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print());**
  - mockMvc.perform(reqBuilder) 는 reqBuilder에 해당하는 URL에 대한 요청을 보냈다는 것을 의미합니다.
  - andExpect(status().isOk()) 는 mockMvc에 위해 URL이 실행되고 상태코드값이 200이 나와야 한다는 것을 의미합니다. 
  - andDo(print())는 처리 내용을 출력하게 됩니다.

**deleteGuestbook Test도 getGuestbooks Test와 동일하게 동작합니다.**



### **생각해보기**

**MockMVC와 목 객체를 이용해 Web API를 테스트하는 방법에 대해 배웠습니다.**
**컨트롤러에서 Exception을 강제로 발생시켰을 때는 테스트 결과가 어떻게 출력될까요?**
**컨트롤러가 오류가 발생하도록 코드를 수정한 후 확인해보세요.**

- Exception이 강제로 발생되었을 경우에는 테스트는 실패합니다. 이를 방지하기 위해 Controller 내부에서는 발생하는 Exception에 대해서 catch하는 코드를 작성해주어야 하며 이에 맞게 Http Response Status Code를 리턴해주어야 합니다.

존재하지 않는 경로를 호출했을 때는 어떤 결과가 나올지 예상해보세요. 그리고 직접 확인해보세요.

- 마찬가지로 에러가 발생한다.























