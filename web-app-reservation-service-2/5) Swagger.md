## 1) Swagger 개요

### 1. 스웨거(Swagger)란?

**스웨거는 Web API 문서화를 위한 도구입니다.** 스웨거는 API들이 가지는 명세(Spec)을 관리하기 위한 프로젝트 입니다.

Web API를 수동으로 문서화 하는 것은 굉장히 힘든 작업입니다. Web API의 스펙이 변경되었을 때 문서 역시 변경이 되야 하는데 이를 유지하는 것이 쉽지가 않습니다.

**Swagger를 사용하면 Web API가 수정되더라도 상관 없습니다. 문서가 자동으로 갱신이 되기 때문입니다.**



### 2. Swagger의 기능

스웨거 홈페이지를 가보면 아래와 같은 기능이 있습니다.

- **API Design**
- **API Development**
- **API Documentation**
- **API Testing**
- **API Mocking and Virtualization**
- **API Governance**
- **API Monitoring**
- **OpenAPI & Swagger**

Web API를 만드는 개발자와 Web API를 이용하는 개발자가 있다고 생각해 보겠습니다. 

Web API를 이용하는 개발자는 Web API가 만들어질 때까지 기다린다면 작업이 상당히 느려질 수 있을 것입니다.

Web API를 만드는 사람과 Web API를 사용하는 사람 간에 미리 명세를 정의하고 공유할 수 있다면 개발이 상당히 편리해 질 것입니다.

지금 이야기 한 것들을 편하게 해주는 도구 중에 하나가 ‘**스웨거**’라고 말할 수 있습니다.



### **3. 스웨거 허브를 이용하여 API를 명세화 하고 테스트하기**

스웨거 허브 사이트를 이용하면 Web API를 만들지 않더라도 Web API를 명세화할 수 있습니다. 또한, Web API를 명세화만 하는게 아니라 간단히 테스트도 할 수 있다는 장점을 가지고 있습니다.



---

## 2) Swagger 설정하기

### 1. Maven 프로젝트 생성하기

Maven을 이용해 Spring MVC프로젝트를 생성해 보도록 하겠습니다.

프로젝트 생성은 ‘Spring MVC를 이용한 웹 페이지 작성 실습[[링크](https://www.edwith.org/boostcourse-web-be/lecture/58981)] 1분 20초’ 부분을 참고해서 생성해주세요.

단, Group Id와 Artifact Id를 다음과 같이 설정해 주세요.

- Group Id : org.edwith.webbe
- Artifact Id : calculator

### 2. pom.xml 설정하기

**pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>org.edwith.webbe</groupId>
  <artifactId>calculator</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>

  <name>calculator Maven Webapp</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

	<!-- Eclipse에서는 web.xml 파일을 작성하지 않고, Java Config를 사용할 때 failOnMissingWebXml를 false로 설정합니다. -->
    <!-- spring 버전은 5.0.2.RELEASE버전을 사용합니다. 2020/02/03일 기준 최신버전 -->
    <properties>
        <failOnMissingWebXml>false</failOnMissingWebXml>
        <spring.version>5.2.3.RELEASE</spring.version>
    </properties>

<dependencies>
	    <!-- servlet-api이다. tomcat에 배포될 경우엔 사용되지 않도록 하기 위해서 scope를 provided로 설정하였다. -->
	<dependency>
	    <groupId>javax.servlet</groupId>
	    <artifactId>javax.servlet-api</artifactId>
	    <version>3.1.0</version>
	    <scope>provided</scope>
	</dependency>
	
	<!-- jsp-api이다. tomcat에 배포될 경우엔 사용되지 않도록 하기 위해서 scope를 provided로 설정하였다. -->
	<dependency>
	    <groupId>javax.servlet.jsp</groupId>
	    <artifactId>javax.servlet.jsp-api</artifactId>
	    <version>2.3.2-b02</version>
	    <scope>provided</scope>
	</dependency>
	
	<!-- jstl은 tomcat이 기본 지원하지 않는다. 그렇기 때문에 tomcat에도 배포가 되야 한다.-->
	<dependency>
	    <groupId>javax.servlet</groupId>
	    <artifactId>jstl</artifactId>
	    <version>1.2</version>
	</dependency>
	
	<!-- spring webmvc에 대한 의존성을 추가한다. spring webmvc에 대한 의존성을 추가하게 되면 spring-web, spring-core등이 자동으로 의존성이 추가된다.-->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-webmvc</artifactId>
	    <version>${spring.version}</version>
	</dependency>
	
	<!-- RestController의 json 변환을 위해 필요함 -->
	<dependency>
	          <groupId>com.fasterxml.jackson.core</groupId>
	          <artifactId>jackson-core</artifactId>
	          <version>2.9.8</version>
	      </dependency>
	      <dependency>
	          <groupId>com.fasterxml.jackson.core</groupId>
	          <artifactId>jackson-databind</artifactId>
	          <version>2.9.8</version>
	      </dependency>

	<!-- java 9 이상에서 추가해줘야 합니다. @PostConstruct 등을 사용하려면 필요함-->
	<dependency>
	    <groupId>javax.annotation</groupId>
	    <artifactId>javax.annotation-api</artifactId>
	    <version>1.3.2</version>
	</dependency>
	<dependency>
	    <groupId>junit</groupId>
	    <artifactId>junit</artifactId>
	    <version>4.12</version>
	    <scope>test</scope>
	</dependency>
	
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-test</artifactId>
	    <version>${spring.version}</version>
	</dependency>


	<!-- swagger2 의존성 추가 Swagger 사용을 위해서는 구현체인 springfox-swagger2 가 필요하며,
	또 가장 중요한 (사용목적이라해도 과언이 아닌) UI 적으로 확인을 위해서는springfox-swagger-ui 이렇게 2개의 라이브러리가 필요하다.-->
	<dependency>
	    <groupId>io.springfox</groupId>
	    <artifactId>springfox-swagger2</artifactId>
	    <version>2.6.1</version>
	</dependency>
	<dependency>
	    <groupId>io.springfox</groupId>
	    <artifactId>springfox-swagger-ui</artifactId>
	    <version>2.6.1</version>
	</dependency>
</dependencies>

	<!-- 사용할 JDK버전을 입력합니다. JDK 11을 사용할 경우에는 1.8대신에 11로 수정합니다.-->
    <build>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.7.0</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>utf-8</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

```

### 3. WebAppIntializer 클래스 작성하기

**자바 웹 어플리케이션을 만들 때는 web.xml 파일이나 WebApplicationInitializer를 구현한 클래스를 만들거나 해야 한다고 앞에서 설명했었습니다. (Spring MVC를 이용한 웹 페이지 작성 실습 참고)**

앞에서 설명한 WebApplicationInitializer를 앞에서는 직접 구현했었는데요, 이번에는 WebApplicationInitializer를 구현하고 있는 AbstractAnnotationConfigDispatcherServletInitializer를 상속받아 작성해 보도록 하겠습니다. 

**AbstractAnnotationConfigDispatcherServletInitializer 클래스는 WebApplicationInitializer를 구현하고 있으면서 필요한 부분만 오버라이딩 하여 구현하도록 제공하는 클래스입니다.** 

**WebAppInitializer.java**

```java
package org.edwith.webbe.calculator.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    
	// Spring 기본 설정파일 클래스를 지정합니다.
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ApplicationConfig.class};
    }

    // Spring MVC 설정 파일 클래스를 지정합니다.
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfig.class};
    }

    // DispatcherServlet이 동작할 맵핑정보를 설정합니다. "/"를 설정한다는 것은 모든 요청을 DispatcherServlet이 처리한다는 것을 의미합니다.
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // 필터를 설정합니다. 여기에서는 인코딩 필터를 설정하고 있습니다.
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");

        return new Filter[]{encodingFilter};
    }
}
```



### 4. 기본적인 스프링 설정 파일 작성하기

Spring MVC에서 사용할 Bean들을 설정하는 스프링 설정 파일을 작성합니다. 여기에서는 'org.edwith.webbe.calculator.ser' 패키지 이하의 Bean들만 찾도록 설정되어 있습니다.

**ApplicationConfig.java**

```java
package org.edwith.webbe.calculator.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.edwith.webbe.calculator.service"})
public class ApplicationConfig {
}
```



### 5. Spring MVC 설정 파일 작성하기

**MvcConfig.java**

```java
package org.edwith.webbe.calculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = {"org.edwith.webbe.calculator.controller"})
public class MvcConfig implements WebMvcConfigurer {
	
	// DefaultServlet에 대한 설정을 합니다.
	// DispatcherServlet이 처리하지 못하는 URL은 DefaultServlet이 처리하게 됩니다.
	// 해당 설정이 없으면 자동 생성된 Swaager 페이지를 볼 수 없습니다.
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/*
	Swagger 사용 시에는 Docket Bean 을 품고있는 설정 클래스 1개가 기본으로 필요하다.
	Spring Boot 에서는 이 기본적인 설정파일 1개로 Swagger 와 Swagger UI 를 함께 사용가능하지만,
	Spring MVC 의 경우 Swagger UI 를 위한 별도의 설정이 필요하다.
	이는, Swagger UI 를 ResourceHandler 에 수동으로 등록해야 하는 작업인데,
	Spring Boot 에서는 이를 자동으로 설정해주지만 Spring MVC 에서는 그렇지 않기 때문이다.
     */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any()) // // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
				.paths(PathSelectors.ant("/api/**"))// PathSelectors.any() 를 할경우 모든 경로가 다 사용된다. RestController가 아닌 것 까지 사용된다.
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false);
	}

	/**
	 * API Info
	 */
	private ApiInfo apiInfo() {
		Contact contact = new Contact("강경미", "https://www.edwith.org", "carami@edwith.org");
		ApiInfo apiInfo =
				new ApiInfo("Swagger Sample", "APIs Sample", "Sample Doc 0.1v", "", contact, "This sentence will be display.", "/");
		return apiInfo;
	}
}
```



---

## 2) Swagger-ui 확인 및 기능 테스트하기

### 1. CalculatorService 클래스 작성하기

덧셈, 뺄셈 기능을 가진 CalculatorService클래스를 작성합니다.

**CalculatorService.java**

```java
package org.edwith.webbe.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public int plus(int value1, int value2){
        return value1 + value2;
    }

    public int minus(int value1, int value2){
        return value1 - value2;
    }
}
```



### 2. Web API 클래스 작성하기

**CalculatorResult.java**

```java
package org.edwith.webbe.calculator.dto;

public class CalculatorResult {
    // 아래의 상수값은 enum으로 변경 가능하다.
    public static final String PLUS_OPERATION = "+";
    public static final String MINUS_OPERATION = "-";

    private int value1;
    private int value2;
    private String operation;
    private int result;

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
```

**CalculatorApiController.java**

```java
package org.edwith.webbe.calculator.controller;

import io.swagger.annotations.*;
import org.edwith.webbe.calculator.dto.CalculatorResult;
import org.edwith.webbe.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/calculator")
public class CalculatorApiController {
    @Autowired
    private CalculatorService calculatorService;

    @ApiOperation(value = "덧셈 구하기") // API에 대한 간단한 설명
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Exception")
    })
    @GetMapping("/plus")
    public CalculatorResult plus(@RequestParam("value1")int value1, @RequestParam("value2") int value2){
        CalculatorResult calculatorResult = new CalculatorResult();
        calculatorResult.setValue1(value1);
        calculatorResult.setValue2(value2);
        calculatorResult.setOperation(CalculatorResult.PLUS_OPERATION);
        calculatorResult.setResult(calculatorService.plus(value1, value2));
        return calculatorResult;
    }

    @ApiOperation(value = "덧셈 구하기") // API에 대한 간단한 설명
    @ApiResponses({  // Response Message에 대한 Swagger 설명
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Exception")
    })
    @GetMapping("/minus")
    public CalculatorResult minus(@RequestParam("value1")int value1, @RequestParam("value2") int value2){
        CalculatorResult calculatorResult = new CalculatorResult();
        calculatorResult.setValue1(value1);
        calculatorResult.setValue2(value2);
        calculatorResult.setOperation(CalculatorResult.MINUS_OPERATION);
        calculatorResult.setResult(calculatorService.minus(value1, value2));
        return calculatorResult;
    }
}
```



### 3. Swagger-ui를 통해서 확인하기

![image-20210106105423986](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210106105423986.png)

### [이슈] Swagger Ui가 보이지 않는다.

**main/webapp/WEB-INF/web.xml 이 존재한다면, Servlet 설정시 web.xml의 설정 파일을 먼저 읽어들이게 되고 WebAppInitializer 설정 파일은 읽지 않게 된다. 즉, 나는 WebAppInitializer Config가 하나도 설정되지 않아서 Swagger-ui.html 페이지가 보이지 않았다.**

 **즉, 나와 같은 이슈를 겪는 사람들은 web.xml 파일을 삭제해보고 위의 영상을 그대로 따라해보면 정상 동작할 것 같다.**















