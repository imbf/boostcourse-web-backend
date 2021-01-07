## 1) Spring MVC란?

### MVC란?

- **MVC는 Model-View-Controller의 약자입니다.**
  - 원래는 제록스 연구소에서 일하던 트뤼그베 린즈커그가 처음으로 소개한 개념으로, 데스트톱 어플리케이션용으로 고안되었습니다.
- **Model : 모델은 뷰가 렌더링하는데 필요한 데이터입니다.** 예를 들어 사용자가 요청한 상품 목록이나, 주문 내역이 이에 해당합니다.
- **View : 웹 애플리케이션에서 뷰(View)는 실제로 보이는 부분이며, 모델을 사용해 렌더링을 합니다.** 뷰는 JSP, JSF, PDF, XML등으로 결과를 표현합니다.
- **Controller : 컨트롤러는 사용자의 액션에 응답하는 컴포넌트입니다. 컨트롤러는 모델을 업데이트하고, 다른 액션을 수행합니다.**

### Java MVC Model 아키텍처 1

![image-20210105114132744](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105114132744.png)

> ---
>
> ## POJO vs Java Bean vs Spring Bean
>
> ### POJO (아주 순수한 객체)
>
> **POJO는 Plain Old Java Object의 약자이며 프레임 워크가 필요하지 않거나 애플리케이션 서버 환경에서 실행될 필요가 없는 일반 Java 객체를 설명하는 방법입니다.**
>
> ### Java Bean
>
> JavaBeans의 개념은 원래 독립형 GUI 구성 요소의 개발을 용이하게하기 위해 Swing을 위해 고안되었지만 <u>패턴은 Spring Bean과 Hibernate의 백엔드 지속성을 위해 용도가 변경되었습니다.</u>
>
> JavaBeans는 많은 객체를 단일 객체(Bean)로 캡슐화하는 클래스입니다. "Bean"이라는 이름은 Java 용 재사용 가능한 소프트웨어 구성 요소를 만드는 것을 목표로하는 다음 표준을 포함하기 위해 부여되었습니다.
>
> 1. Serializable 인터페이스를 구현해야 한다.
> 2. public 한 기본 생성자를 가지고 있어야 한다.
> 3. 모든 프로퍼티는 private이며 getter와 setter 메소드를 가지고 있어야 한다.
>
> **장점**
>
> - 이러한 클래스 자체는 다른 어플리케이션에서 재사용 가능하다.
> - 이벤트 등록
> - persistent 스토리지와 restored를 세이브할 수 있따.
>
> **단점**
>
> - 기본 생성자로인해 적절한 초기화를 보장할 수 없다.
> - mutable 하다.
> - 중복된 코드가 많이 발생한다.
>
> ### Java Beans VS POJO
>
> 자바 기본 객체는 POJO일 수 있지만, Java Beans는 아닐수 있다.
>
> ### Spring Bean
>
> Spring Bean는 Bean 정의에 기반하여 생성되고 Spring IOC Container에 의해서 관리되어지는 객체들의 인스턴스입니다.
>
> **Bean Definition**
>
> - Bean 이름
> - Bean의 클래스
> - Bean의 의존성
> - Bean의 socpe, lifecycle callbacks, ...
> - other configuration settings
>
> ### Java Bean VS Spring Bean
>
> 1. Spring Bean은 Spring IOC Container에서 관리됩니다. Java Bean은 그러지 않습니다.
> 2. Java Bean은 항상 직렬화가 가능해야 하지만 Spring Bean은 그러지 않습니다.
> 3. Java Bean은 public default constructor가 꼭 필요하지만, Spring Bean은 그러지 않습니다.
> 4. Java 객체는 JavaBean인 동시에 POJO인 동시에 Spring Bean이 될 수 있습니다.

### Java MVC Model 2 아키텍처

![image-20210105120459454](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105120459454.png)

### Java MVC Model 2 Advanced 아키텍처 (Spring MVC와 비슷한 아키텍처)

![image-20210105120529769](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105120529769.png)

- 프런트 컨트롤러는 Servlet이다.
  - 어플리케이션에 프런트 컨트롤러 Servlet 하나만 존재하고 관련 요청들은 다른 객체에게 위임한다.
- **Model 2 MVC 패턴을 지원하는 Spring Module**

### 생각해보기

1. 프론트 컨트롤러(Front Controller)는 모든 요청을 받아 들여 공통적인 작업을 처리해 줍니다. 이를 통해 얻을 수 있는 장점엔 어떤 것이 있을 수 있을까요?
   - 각 Servlet마다 발생하는 중복되는 코드를 없앨 수 있다.
   - 관리하는 Servlet이 줄어들기 때문에, 시스템의 복잡도가 상대적으로 낮아진다.

---

## 2) Spring MVC 구성요소

### Spring MVC 기본 동작 흐름

![image-20210105120950737](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105120950737.png)

- **Spring MVC는 프레임워크이기 때문에 프레임워크의 흐름에 개발자의 코드가 종속된다. 즉, 프레임워크가 나의 코드를 가져다 쓰는 것이다.**
  - **파란색 상자:** Spring에 의해서 구현되어진 것들
  - **보라색 상자:** 개발자가 구현해야하는 것들
  - **초록색 상자:** Spring에 의해서 구현되어져 있지만, 개발자가 때때로 구현해야하는 것들

### 요청 처리를 위해 사용되는 컴포넌트

- **DispatcherServlet**

  - HandlerMapping
  - HandlerAdapter
  - MultipartResolver
  - LocaleResolver
  - ThemeResolver
  - HandlerExceptionResolver
  - RequestToViewNameTranslator
  - ViewResolver
  - FlashMapManager

  **이러한 컴포넌트들은 구체적으로 살펴보도록 하겠다.**

### DispatcherServlet

- 프론트 컨트롤러 (Front Controller)

  - 회사의 대표 번호와 같다.

- **<u>클라이언트의 모든 요청을 받은 후 이를 처리할 핸들러에게 넘기고 핸들러가 처리한 결과를 받아 사용자에게 응답 결과를 보여준다.</u>**

- **DispathcerServlet은 여러 컴포넌트를 이용해 작업을 처리한다.**

- **DisepatcherServlet의 내부 동작흐름**

  ![image-20210105135358987](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105135358987.png)

  1. 요청이 들어온다.
  2. 요청 선처리 작업
  3. HandlerExecutionChain 결정
  4. HandlerExecutionChain 실행
     1. 예외 발생 시 예외처리
  5. 뷰 렌더링
  6. 요청 처리 종류
  7. 요청 처리

### DispatcherServlet 내부 동작흐름 상세 - 요청 선처리 작업

![image-20210105140449319](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105140449319.png)

**요청 선처리 작업**

1. Locale(다국화) 결정 
2. RequestContextHolder에 요청 저장 (ThreadLocal 객체이다.)
3. FlashMap 복원
   - Redirect로 값을 전달할 때 값을 유지시킬 수 있게 해준다.
4. 멀티파트 요청시 MultipartResolver가 멀티파트 결정
5. 핸들러 결정과 실행

### 요청 선처리 작업시 사용된 컴포넌트

- **org.springframework.web.servlet.LocaleResolver**
  - 지역 정보를 결정해주는 전략 오브젝트이다. 디폴트인 AcceptHeaderLocaleResolver는 HTTP 헤더의 정보를 보고 지역정보를 설정해준다.
- **org.springframework.web.servlet.FlashMapManager**
  - FlashMap 객체를 조회(retrieve) & 저장을 위한 인터페이스
  - RedirectAttributes의 addFlashAttribute메소드를 이용해서 저장한다.
  - 리다이렉트 후 조회를 하면 바로 정보는 삭제된다.
- **org.springframework.web.context.request.RequestContextHolder**
  - 알번 반앳 HttpServletRequest, HttpServletResponse, HttpSession 등을 사용할 수 있다.
  - 해당 객체를 일반 빈에서 사용하게 되면, Web에 종속적이 될 수 있다.
- **org.springframework.web.multipart.MultipartResolver**
  - 멀티파트 파일 업로드를 처리하는 전략

### DispatcherServlet 내부 동작흐름 상세 - 요청 전달

![image-20210105141213136](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105141213136.png)

**요청 전달 흐름**

1. HandlerMapping으로 HandlerExecutionChain 결정
2. HandlerExecutionchain 발견 여부
   - 발견하지 못했다면 Http 404 전달하면서 요청 처리
3. HandlerAdapter 발견 여부
   - 발견하지 못했다면 ServletException 발생(Http 5xx)하며 요청 처리
4. 요청 처리

**요청 전달시 사용된 컴포넌트**

- **org.springframework.web.servlet.HandlerMapping**
  - HandlerMapping구현체는 어떤 핸들러가 요청을 처리할지에 대한 정보를 알고 있다.
  - 디폴트로 설정되는 있는 핸들러매핑은 BeanNameHandlerMapping과 DefaultAnnotationHandlerMapping 2가지가 설정되어 있다.
- **org.springframework.web.servlet.HandlerExecutionChain**
  - HandlerExecutionChain구현체는 실제로 호출된 핸들러에 대한 참조를 가지고 있다.
  - 즉, 무엇이 실행되어야 될지 알고 있는 객체라고 말할 수 있으며, 핸들러 실행전과 실행후에 수행될 HandlerInterceptor도 참조하고 있다.
- **org.springframework.web.servlet.HandlerAdapter**
  - 실제 핸들러를 실행하는 역할을 담당한다.
  - 핸들러 어댑터는 선택된 핸들러를 실행하는 방법과 응답을 ModelAndView로 변화하는 방법에 대해 알고 있다.
  - 디폴트로 설정되어 있는 핸들러어댑터는 HttpRequestHandlerAdapter, SimpleControllerHandlerAdapter, AnnotationMethodHanlderAdapter 3가지이다.
  - @RequestMapping과 @Controller 애노테이션을 통해 정의되는 컨트롤러의 경우 DefaultAnnotationHandlerMapping에 의해 핸들러가 결정되고, 그에 대응되는 AnnotationMethodHandlerAdapter에 의해 호출이 일어난다.

### DispatcherServlet 내부 동작흐름 상세 - 요청 처리

![image-20210105141614381](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105141614381.png)

**요청 처리 흐름**

1. 결정된 HandlerExecutionChain
2. 사용 가능한 인터셉터가 존재하나?
   - 존재한다면 인터셉터의 preHandler를 호출해 요청 처리후 종료
3. 핸들러 실행
4. ModelAndView를 리턴하는가?
   - 리턴하지 않는다면 인터셉터의 postHandle를 호출해 요청 처리 후 뷰 렌더링
5. ModelAndView가 뷰를 갖는가?
   - 뷰를 갖으면 인터셉터의 postHandle를 호출해 요청 처리 후 뷰 렌더링
6. RequestToViewNameTranslator가 View Name을 Translate한다.
7. 뷰 렌더링

**요청 처리시 사용된 컴포넌트**

- **org.springframework.web.servlet.ModelAndView**
  - ModelAndView는 Controller의 처리 결과를 보여줄 view와 view에서 사용할 값을 전달하는 클래스이다.

- **org.springframework.web.servlet.RequestToViewNameTranslator**
  - 컨트롤러에서 뷰 이름이나 뷰 오브젝트를 제공해주지 않았을 경우 URL과 같은 요청정보를 참고해서 자동으로 뷰 이름을 생성해주는 전략 오브젝트이다. 디폴트는 DefaultRequestToViewNameTranslator이다.

### DispatcherServlet 내부 동작흐름 상세 - 예외 처리

![image-20210105142039957](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105142039957.png)

**예외 처리 흐름**

1. HandlerExceptionResolver에 문의
2. ModelAndView 리턴 여부
   - 리턴한다면 요청 처리 재개
   - 리턴하지 않는다면 예외를 던짐

**예외 처리시 사용된 컴포넌트**

- **org.springframework.web.servlet.handlerexceptionresolver**
  - 기본적으로 DispatcherServlet이 DefaultHandlerExceptionResolver를 등록한다.
  - HandlerExceptionResolver는 예외가 던져졌을 때 어떤 핸들러를 실행할 것인지에 대한 정보를 제공한다.

### DispatchServlet 내부 동작흐름 상세 - 뷰 렌더링 과정

![image-20210105142248541](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105142248541.png)

**뷰 렌더링 흐름**

1. 뷰가 String을 참조하는지 여부
   - 참조 한다면 ViewResolver로 View 구현체를 찾는다.
2. View 구현체가 존재하는지 여부?
   - 존재하지 않는다면 ServletException을 던진다.
3. View 구현체로 렌더링
4. 요청 처리 재개

**뷰 렌더링 과정시 사용된 컴포넌트**

- **org.springframework.web.servlet.ViewResolver**
  - 컨트롤러가 리턴한 뷰 이름을 참고해서 적절한 뷰 오브젝트를 찾아주는 로직을 가진 전략 오프젝트이다.
  - 뷰의 종류에 따라 적절한 뷰 리졸버를 추가로 설정해줄 수 있다.

### DispatcherServlet 내부 동작흐름 상세 - 요청 처리 존재

![image-20210105142515097](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105142515097.png)

**요청 처리 종료 흐름**

1. HandlerExceptionChain 존재 여부
   - 존재하지 않는다면 RequestHandledEvent 발생 후 요청 처리
2. 인터셉트의 afterCompletion 메소드 실행
3. RequestHandledEvent 발생
4. 요청 처리

**생각해보기**

1. DispatcherServlet은 요청을 받은 후, 요청을 처리하기 위해서 여러가지 작업을 수행하고 있습니다. 
2. 개발자는 DispatcherServlet이 어떤 방식으로 동작하는지 이해한다면 좀 더 잘 Spring MVC를 잘 사용할 수 있습니다.
3. Spring외에 다른 프레임워크를 학습할 때에도, 해당 프레임워크의 동작원리를 이해하는 것은 굉장히 중요합니다.
4. 어떻게 하면, 다른 사람이 만든 라이브러리나 프레임워크를 좀 더 잘 이해할 수 있을지 고민해보세요.

---

## 3) Spring MVC를 이용한 웹 페이지 작성 실습

**pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>kr.or.connect</groupId>
  <artifactId>mvcexam</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>

  <name>mvcexam Maven Webapp</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <spring.version>5.2.3.RELEASE</spring.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    
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
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>${spring.version}</version>
    </dependency>
    
 	<dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${spring.version}</version>
    </dependency>
    
	<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>8.0.18</version>
	</dependency>
	
	<dependency>
		<groupId>jstl</groupId>
		<artifactId>jstl</artifactId>
		<version>1.2</version>
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

**.settings/org.eclipse.wst.common.project.facet.core**

- \<installed facet="jst.web" version="2.3"/> 의 vesion을 3.1로 수정



### DispatchServlet을 FrontController로 설정하기

서블릿 설정 방법

- **web.xml 파일에 설정**
- javax.servlet.ServletContainerInitializer 사용
  - 서블릿 3.0 스펙 이상에서 web.xml 파일을 대신해서 사용할 수 있다.
- **org.springframework.web.WebApplicationInitializer 인터페이스를 구현해서 사용**



### web.xml 파일에서 DispatcherServlet 설정하기

- xml spring 설정 읽어들이도록 DispatcherServlet 설정
- Java Config Spring 설정 읽어들이도록 DispatcherServlet 설정



### WebApplicationInitializer를 구현해서 설정하기 (수업에서 다루지는 않는다.)

- Spring MVC는 ServletContainerInitializer를 구현하고 있는 SpringServletContainerInitializer를 제공한다.
- SpringServletContainerInitializer는 WebApplicationInitializer 구현체를 찾아 인스턴스를 만들고 해당 인스턴스의 onStartup 메소드를 호출하여 초기화 한다.



### Spring MVC 설정

- kr.or.connect.webmvc.config.WebMvcContextConfiguration 클래스의 설정들을 읽어들인다.



### @Configuration 애노테이션

- org.springframework.context.annotation의 Configuration 애노테이션과 Bean 애노테이션 코드를 이용하여 스프링 컨테이너에 새로운 Bean 객체를 제공할 수 있다.



### @EnableWebMvc 애노테이션

- DispatchServlet의 RequestMappingHandlerMapping, RequestMappingHandlerAdapter, ExceptionHandlerExceptionResolver, MessageConverter 등 web에 필요한 빈들을 대부분 자동으로 설정 해준다.
- xml 설정의 <mvc: annotation-driven/> 와 동일하다.
- 기본 설정 이외의 설정이 필요하다면 WebMvcConfigurerAdapter를 상속받도록 Java config class를 작성한 후 해당 메소드를 오버라이딩 하면 좋다.



### @ComponentScan

- **ComponentScan애노테이션을 이용하면 Controller, Service, Repository, Component애노테이션이 붙은 클래스를 찾아 스프링 컨테이너가 관리하게 된다.**
- DefaultAnnotationHandlerMapping과 RequestMappingHandlerMapping구현체는 다른 핸들러 매핑보다 훨씬 더 정교한 작업을 수행한다. <u>이 두 개의 구현체는 애노테이션을 사용해 매핑 관계를 찾는 매우 강력한 기능을 가지고 있다.</u> **이들 구현체는 스프링 컨테이너 즉 애플리케이션 컨텍스트에 있는 요청 처리 Bean에서 RequestMapping애노테이션을 클래스나 메소드에서 찾아 HandlerMapping객체를 생성하게 된다.**
- **HandlerMapping은 서버로 들어온 요청을 어느 핸들러로 전달할지 결정하는 역할을 수행한다.**
- DefaultAnnotationHandlerMapping은 DispatcherServlet이 기본으로 등록하는 기본 핸들러 맵핑 객체이고, RequestMappingHandlerMapping은 더 강력하고 유연하지만 사용하려면 명시적으로 설정해야 한다.



### WebMvcConfigurerAdapter

- org.springframework.web.servlet.config.annotation. WebMvcConfigurerAdapter
- **@EnableWebMvc 를 이용하면 기본적인 설정이 모두 자동으로 되지만, 기본 설정 이외의 설정이 필요할 경우 해당 클래스를 상속 받은 후, 메소드를 오버라이딩 하여 구현한다.**



### Controller(Handler) 클래스 작성하기

- @Controller 애노테이션을 클래스 위에 붙인다.
- 맵핑을 위해 @RequestMapping 애노테이션을 클래스나 메소드에서 사용한다.



### @RequestMapping

- **Http 요청과 이를 다루기 위한 Controller 의 메소드를 연결하는 어노테이션**
- Http Method 와 연결하는 방법
  - @RequestMapping(value="/users", method=RequestMethod.POST)
  -  From Spring 4.3 version 
    - @GetMapping
    - @PostMapping
    - @PutMapping
    - @DeleteMapping
    - @PatchMapping
- Http 특정 해더와 연결하는 방법
  - @RequestMapping(method = RequestMethod.GET, headers = "content-type=application/json")
- Http Parameter 와 연결하는 방법
  - @RequestMapping(method = RequestMethod.GET, params = "type=raw")
- Content-Type Header 와 연결하는 방법
  - @RequestMapping(method = RequestMethod.GET, consumes = "application/json")
- Accept Header 와 연결하는 방법
  - @RequestMapping(method = RequestMethod.GET, produces = "application/json")

### 실습 코드

**WebMvcContextConfiguration.java**

```java
package kr.or.connect.guestbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"kr.or.connect.guestbook"})
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {
	
	// 특정 리소스에 대한 요청은 아래와 같은 경에서 찾아라는 것을 명시.
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    }
 
    // default Servlet handler를 사용하게 합니다.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
   
    // 특정 URL에 대한 처리를 컨트롤러에 위임하지 말고 View를 호출하는 설정
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
    		System.out.println("addViewControllers가 호출됩니다. ");
        registry.addViewController("/").setViewName("main");
    }
    
    // 내부 Resolver에 대해 prefix와 suffix를 지정한다.
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
```

**webapp/WEB-INF/web.xml**

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>
  
  <servlet>
   <!-- 서블릿 이름 --> 
  	<servlet-name>dispatcherServlet</servlet-name>
   <!-- 서블릿 클래스 --> 
  	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     
  	<init-param>
      <!-- Context Class 즉, ApplicationContext는 AnnotationConfigWebApplicationContext를 사용하겠다.-->
  		<param-name>contextClass</param-name>
  		<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
  	</init-param>
     
   <!-- Spring Context를 초기화하기 위한 Config Class 지정 -->
  	<init-param>
  		<param-name>contextConfigLocation</param-name>
  		<param-value>kr.or.connect.mvcexam.config.WebMvcContextConfiguration</param-value>
  	</init-param>
     
  	<load-on-startup>1</load-on-startup>
     
  <!-- 특정 URL에 맵핑될 Servlet을 지정 -->   
  </servlet>
  <servlet-mapping>
  	<servlet-name>dispatcherServlet</servlet-name>
  	<url-pattern>/</url-pattern>
  </servlet-mapping>

</web-app>
```



### Controller 작성 실습

**webapp/WEB-INF/views/plusForm.jsp**

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
	<form method="post" action="plus">  
		value1 : <input type="text" name="value1"><br>
		value2 : <input type="text" name="value2"><br>
		<input type="submit" value="확인">
	</form>  
</body>
</html>
```

**webapp/WEB-INF/views/plusResult.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	${value1} 더하기 ${value2} (은/는) ${result} 입니다.
</body>
</html>
```

### Spring MVC가 지원하는 Controller 메소드의 인수 타입

**Bold 처리 되어 있는 것이 수업중에 사용 될 인수 타입이다.**

- javax.servlet.ServletRequest
- **javax.servlet.http.HttpServletRequest**
- org.springframework.web.multipart.MultipartRequest
- org.springframework.web.multipart.MultipartHttpServletRequest
- javax.servlet.ServletResponse
- **javax.servlet.http.HttpServletResponse**
- **javax.servlet.http.HttpSession**
- org.springframework.web.context.request.WebRequest
- org.springframework.web.context.request.NativeWebRequest
- java.util.Locale
- java.io.InputStream
- java.io.Reader
- java.io.OutputStream
- java.io.Writer
- javax.security.Principal
- java.util.Map
- org.springframework.ui.Model
- org.springframework.ui.ModelMap
- **org.springframework.web.multipart.MultipartFile**
- javax.servlet.http.Part
- org.springframework.web.servlet.mvc.support.RedirectAttributes
- org.springframework.validation.Errors
- org.springframework.validation.BindingResult
- org.springframework.web.bind.support.SessionStatus
- org.springframework.web.util.UriComponentsBuilder
- org.springframework.http.HttpEntity<?>
- Command 또는 Form 객체

### **Spring MVC가 지원하는 메소드 인수 애노테이션**

- **@RequestParam**
  - Mapping된 메소드의 Argument에 붙일 수 있는 애노테이션
  - **@RequestParam의 name에는 http parameter의 name과 맵핑**
  - @RequestParam의 required는 필수인지 아닌지 판단하게 해준다. (필수가 아닐 경우 null 맵핑)
- **@RequestHeader**
  - 요청 정보의 헤더 정보를 읽어들일 때 사용
  - @RequestHeader(name="헤더명") String 변수명
- **@RequestBody**
- @RequestPart
- **@ModelAttribute**
- **@PathVariable**
  - @RequestMapping의 path에 변수명을 입력받기 위한 place holder가 필요하다.
  - place holder의 이름과 PathVariable의 name 값이 같으면 mapping 됨
  - required 속성은 기본적으로 true 이다.
- @CookieValue

### Spring MVC가 지원하는 메소드 리턴 값

**Spring MVC가 지원하는 메소드 리턴 값**

- **org.springframework.web.servlet.ModelAndView**
- org.springframework.ui.Model
- java.util.Map
- org.springframework.ui.ModelMap
- org.springframework.web.servlet.View
- **java.lang.String**
- java.lang.Void
- org.springframework.http.HttpEntity<?>
- org.springframework.http.ResponseEntity<?>
- **기타 리턴 타입**

### 실습 코드

**kr.or.connect.mvcexam.controller.PlusController.java**

```java
package kr.or.connect.mvcexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlusController {
	
	@GetMapping(path="/plusform")
	public String plusForm() {
		// ViewResolver가 Controller의 return 값으로 String이 나왔을 때 String을 suffix와 prefix를 붙여 View를 탐
		return "plusForm";
	}
	
	@PostMapping
	public String plusResult(@RequestParam(name = "value1", required = true) int value1,
			@RequestParam(name = "value2", required = true) int value2, ModelMap modelMap) {
		int result = value1 + value2;
		
		// modelMap을 사용하면 Request Scope에 알아서 변수를 넣어준다. 즉, JSP에서 이를 받아다 사용할 수 있다.
		modelMap.addAttribute("value1", value1);
		modelMap.addAttribute("value2", value2);
		modelMap.addAttribute("result", result);
		return "plusResult";
	}

}

```

### Controller 작성 실습 3

**webapp/WEB-INF/views/userForm.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="regist">  
	name : <input type="text" name="name"><br>
	email : <input type="text" name="email"><br>
	age : <input type="text" name="age"><br>
	<input type="submit" value="확인">
	</form> 
</body>
</html>
```

**User.java**

```java
package kr.or.connect.mvcexam.dto;

public class User {
	
	private String name;
	private String email;
	private int age;
	
	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", age=" + age + "]";
	}
}

```

**UserController.java**

```java
package kr.or.connect.mvcexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.connect.mvcexam.dto.User;

@Controller
public class UserController {
	
	@GetMapping(path="/userform")
	public String userForm() {
		return "userForm";
	}
	
	@RequestMapping(path="/regist", method=RequestMethod.POST)
	public String regist(@ModelAttribute User user) {
		
		System.out.println("사용자가 입력한 user 정보입니다. 해당 정보를 이용하는 코드가 와야합니다.");
		System.out.println(user);
		return "regist";
	}

}
```

**webapp/WEB-INF/views/regist.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>등록되었습니다.</h2>
</body>
</html>
```

### Controller 작성 실습 - 4

**webapp/WEB-INF/views/goodsById.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
   id : ${id } <br>
   user_agent : ${userAgent }<br>
   path : ${path }<br>
</body>
</html>
```

**GoodsController.java**

```java
package kr.or.connect.mvcexam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class GoodsController {
	@GetMapping("/goods/{id}")
	public String getGoodsById(@PathVariable(name="id") int id,
							   @RequestHeader(value="User-Agent", defaultValue="myBrowser") String userAgent,
							  HttpServletRequest request,
							  ModelMap model
							  ) {
		
		String path = request.getServletPath();
		
		System.out.println("id : " + id);
		System.out.println("user_agent : " + userAgent);
		System.out.println("path : " + path);
		
		model.addAttribute("id", id);
		model.addAttribute("userAgent", userAgent);
		model.addAttribute("path", path);
		return "goodsById";
	}
}

```

### **생각해보기**

1. **SpringMVC를 이용해서 웹 어플리케이션을 개발하는 것과 서블릿을 이용해 개발하는 것과 비교해보면 어떤 장단점이 있다고 생각하세요?**

   - 일단 기본적으로 웹 어플리케이션을 서비스하기위해 개발자가 사용해야하는 수 많은 기능들을 Spring MVC가 대신 구현해 주어 서비스를 빠르고 안전하게 구현할 수 있다.

     서블릿 대신에 Spring MVC를 사용함으로써 수 많은 장점과 단점이 존재하겠지만 대표적으로 하나씩만 꼽아보도록 하겠다.

     대표적인 장점으로는 어플리케이션 개발의 생산성 향상을 이룰 수 있다.

     대표적인 단점으로는 개발자가 직접 Request와 Response 흐름을 조절할 수 없어 간단한 요청에도 Spring MVC의 불필요한 로직들이 많이 수행되어 기본적인 Servlet 프로그래밍 보다 처리 속도가 느릴 수 있다.



















