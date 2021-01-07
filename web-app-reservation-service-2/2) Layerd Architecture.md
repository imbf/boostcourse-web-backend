## 1) Layered Architecture 란?

### Controller에서 중복되는 부분을 처리하려면?

- **별도의 객체, 메서드, 모듈로 분리합니다.** (Layerd Architecture의 기본적인 개념)
- 예를 들어 쇼핑몰에서 게시판에서도 회원 정보를 보여주고, 상품 목록 보기에서도 회원 정보를 보여줘야 한다면 회원 정보를 읽어오는 코드는 어떻게 해야 할까요?

### 컨트롤러와 서비스

- **비지니스 메소드를 별도의 Service객체에서 구현하도록 하고 컨트롤러는 Service객체를 사용하도록 합니다.**

- **Architecture**

  ![image-20210105163719548](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105163719548.png)

### 서비스(Service)객체란?

- **비지니스 로직(Business logic)을 수행하는 메소드를 가지고 있는 객체를 서비스 객체라고 합니다.**

- 보통 하나의 비지니스 로직은 하나의 트랜잭션으로 동작합니다.

### 트랜잭션(Transaction)이란?

- **트랜잭션은 하나의 논리적인 작업을 의미합니다.**
- 트랜잭션의 특징은 크게 아래의 4가지로 구분됩니다.
  - **원자성 (Atomicity)**
  - **일관성 (Consistency)**
  - **독립성 (Isolation)**
  - **지속성 (Durability)**



### 원자성 (Atomicity)

- **전체가 성공하거나 전체가 실패하는 것을 의미한다.**
- 예를 들어 "출금"이라는 기능의 흐름이 다음과 같다고 생각해봅시다. (1번부터 5번까지 하나로 묶인 작업이라고 본다.)
  1. 잔액이 얼마인지 조회한다.
  2. 출금하려는 금액이 잔액보다 작은지 검사한다.
  3. 출금하려는 금액이 잔액보다 작다면 (잔액 - 출금액)으로 수정한다.
  4. 언제, 어디서 출금했는지 정보를 기록한다.
  5. 사용자에게 출금한다.
- 위의 작업이 4번에서 오류가 발생했다면 어떻게 될까요? <u>4번에서 오류가 발생했다면, 앞의 작업을 모두 원래대로 복원을 시켜야 합니다.</u> **이를 rollback이라고 합니다.** <u>5번까지 모두 성공했을 때만 정보를 모두 반영해야 합니다.</u> **이를 commit 한다고 합니다. 이렇게 rollback 하거나 commit을 하게 되면 하나의 트랜잭션 처리가 완료됩니다.**



### 일관성 (Consistency)

- **일관성은 트랜잭션의 작업 처리 결과가 항상 <u>일관성</u>이 있어야 한다는 것입니다.**

- 트랜잭션이 진행되는 동안에 데이터가 변경되더라도 업데이트된 데이터로 트랜잭션이 진행되는 것이 아니라, 처음에 트랜잭션을 진행하기 위해 참조한 데이터로 진행됩니다. 이렇게 함으로써 각 사용자는 일관성 있는 데이터를 볼 수 있는 것입니다.



### 독립성 (Isolation)

- **독립성은 둘 이상의 트랜잭션이 동시에 병행 실행되고 있을 경우에 어느 하나의 트랜잭션이라도 다른 트랜잭션의 연산을 끼어들 수 없습니다.**

- 하나의 특정 트랜잭션이 완료될 때까지, 다른 트랜잭션이 특정 트랜잭션의 결과를 참조할 수 없습니다.

 

### 지속성 (Durability)

- **지속성은 트랜잭션이 성공적으로 완료됬을 경우, 결과는 영구적으로 반영되어야 한다는 점입니다.**



### JDBC 프로그래밍에서 트랜잭션 처리 방법

- **DB에 연결된 후 Connection객체의 setAutoCommit메소드에 false를 파라미터로 지정합니다.**
  - default는 false이다. 즉, 하나의 DB 연산이 수행되었을 경우 이를 바로 반영한다.

- **입력, 수정, 삭제 SQL이 실행을 한 후 모두 성공했을 경우 Connection이 가지고 있는 commit()메소드를 호출합니다.**



### @EnableTransactionManagement

- **Spring Java Config파일에서 트랜잭션을 활성화 할 때 사용하는 애노테이션입니다.**

- Java Config를 사용하게 되면 PlatformTransactionManager 구현체를 모두 찾아서 그 중에 하나를 매핑해 사용합니다.

- 특정 트랜잭션 메니저를 사용하고자 한다면 TransactionManagementConfigurer를 Java Config파일에서 구현하고 원하는 트랜잭션 메니저를 리턴하도록 합니다. 아니면, 특정 트랜잭션 메니저 객체를 생성시 @Primary 애노테이션을 지정합니다.



### 서비스 객체에서 중복으로 호출되는 코드의 처리

- 데이터 엑세스 메소드를 별도의 Repository(Dao) 객체에서 구현하도록 하고 Service는 Repository객체를 사용하도록 합니다.



### **레이어드 아키텍처**

![image-20210105164716173](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105164716173.png)

- Presentation Layer : Controller와 같은 객체가 존재하는 레이어
- Service Layer : Business 코드가 들어 있는 객체 존재하는 레이어
- Repository Layer: Dao와 객체가 존재하는 레이어
- **재사용이나 유지보수 측면을 봤을 때 위와 같이 분리하는게 좋다.**



### 설정의 분리

- **Spring 설정 파일을 프리젠테이션 레이어쪽과 나머지를 분리할 수 있습니다.**

- **web.xml 파일에서 프리젠테이션 레이어에 대한 스프링 설정은 DispathcerServlet이 읽도록 하고, 그 외의 설정은 ContextLoaderListener를 통해서 읽도록 합니다.**

- DispatcherServlet을 경우에 따라서 2개 이상 설정할 수 있는데 이 경우에는 <u>각각의 DispathcerServlet의 ApplicationContext가 각각 독립적이기 때문에 각각의 설정 파일에서 생성한 빈을 서로 사용할 수 없습니다.</u> 위의 경우와 같이 동시에 필요한 빈은 ContextLoaderListener를 사용함으로써 공통으로 사용하게 할 수 있습니다.

- ContextLoaderListener와 DispatcherServlet은 각각 ApplicationContext를 생성하는데, <u>ContextLoaderListener가 생성하는 ApplicationContext가 root컨텍스트가 되고 DispatcherServlet이 생성한 인스턴스는 root컨텍스트를 부모로 하는 자식 컨텍스트가 됩니다.</u> 참고로, 자식 컨텍스트들은 root컨텍스트의 설정 빈을 사용할 수 있습니다.



### **생각해보기**

1. 레이어로 구성하지 않을 경우 어떤 문제가 발생할까요?
   - 중복되는 코드가 발생하게 되어 추후 변경에 유연하게 대처할 수 없게 된다.
2. 레이어를 구성하지 않고 중복코드를 제거할 수 있을까요?
   - 가능하나..?

---

## 2) 레이어드 아키텍처(Layerd Architecture) 실습

### 방명록 만들기

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
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
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

**.settings/org.eclipse.wst.common.project.facet.core**

- \<installed facet="jst.web" version="2.3"/> 의 vesion을 3.1로 수정

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
@ComponentScan(basePackages = {"kr.or.connect.guestbook.controller"})
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {
	
	// 특정 리소스에 대한 요청은 아래와 같은 경에서 찾아라는 것을 명시.
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    }
 
    // default Servlet handler를 사용하게 합니다. Mapping이 없는 URL이 넘어 왔을 때 WAS의 default Servlet이 Static한 자원을 읽어서 보여준다.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
   
    // 특정 URL에 대한 처리를 컨트롤러에 위임하지 말고 View를 호출하는 설정
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
    		System.out.println("addViewControllers가 호출됩니다. ");
        registry.addViewController("/").setViewName("index");
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

**DBConfig.java**

```java
package kr.or.connect.guestbook.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement // Transaction과 관련된 설정을 자동으로 해준다.
public class DBConfig implements TransactionManagementConfigurer {
	private String driverClassName = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/connectdb?serverTimezone=UTC";
	private String username = "connectuser";
	private String password = "connect123!@#";

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	// Platform Transaction Manager 처리를 위한 Bean 생성
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManger();
	}

	// DB 연결을 위해 DataSourceTransactionManag에 DataSource 등록
	@Bean
	public PlatformTransactionManager transactionManger() {
		return new DataSourceTransactionManager(dataSource());
	}
}

```

**ApplicationConfig.java**

```java
package kr.or.connect.guestbook.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"kr.or.connect.guestbook.dao", "kr.or.connect.guestbook.service"})
@Import({DBConfig.class})
public class ApplicationConfig {

}

```

**webapp/WEB-INF/web.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app>

	<display-name>Spring JavaConfig Sample</display-name>
	
	<context-param>
		<!-- ApplicationContext를 생성하기 위해 로드  -->
		<param-name>contextClass</param-name>
		<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext
		</param-value>
	</context-param>
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>kr.or.connect.guestbook.config.ApplicationConfig
		</param-value>
	</context-param>
	
	<!-- Context가 로딩될 때 Context-param에 지정된 context-param 을 실행 -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<!-- 서블릿 설정 -->
	<servlet>
		<servlet-name>mvc</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextClass</param-name>
			<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
		</init-param>
		<init-param>
			<!-- DispatcherServlet이 생성될 아래의 설정을 참고하라는 설정 --> 
			<param-name>contextConfigLocation</param-name>
			<param-value>kr.or.connect.guestbook.config.WebMvcContextConfiguration</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>mvc</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>

	<!--요청이 수행되기 전, 응답이 나가기 전에 실행하는 filter 설정 -->
	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter
		</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
	</filter>
	<!-- 특정 URL에 필터 맵핑 -->   
	<filter-mapping> 
		<filter-name>encodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
</web-app>
```

### Mysql 테이블 생성

**guestbook 테이블 생성 쿼리**

```sql
CREATE TABLE guestbook(
   id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
   name varchar(255) NOT NULL,
   content text,
   regdate datetime,
   PRIMARY KEY (id)
);
```

**log 테이블 생성 쿼리**

```sql
CREATE TABLE log (
   id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
   ip varchar(255) NOT NULL,
   method varchar(10) NOT NULL,
   regdate datetime,
   PRIMARY KEY (id)
);
```

**Guestbook.java**

```java
package kr.or.connect.guestbook.dto;

import java.util.Date;

public class Guestbook {
	private Long id;
	private String name;
	private String content;
	private Date regdate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "Guestbook [id=" + id + ", name=" + name + ", content=" + content + ", regdate=" + regdate + "]";
	}
}
```

**Log.java**

```java
package kr.or.connect.guestbook.dto;

import java.util.Date;

public class Log {
	private Long id;
	private String ip;
	private String method;
	private Date regdate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "Log [id=" + id + ", ip=" + ip + ", method=" + method + ", regdate=" + regdate + "]";
	}
}
```

**LogDao.java**

```java
package kr.or.connect.guestbook.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kr.or.connect.guestbook.dto.Log;

@Repository
public class LogDao {
	
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	public LogDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("log")
				.usingGeneratedKeyColumns("id"); // id가 자동으로입력된다.
	}
	
	public Long insert(Log log) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(log);
		return insertAction.executeAndReturnKey(params).longValue();
	}
}
```

**GuestBookDaoSqls.java**

```java
package kr.or.connect.guestbook.dao;

public class GuestbookDaoSqls {
	public static final String SELECT_PAGING = "SELECT id, name, content, regdate FROM guestbook ORDER BY id DESC limit :start, :limit";
	public static final String DELETE_BY_ID = "DELETE FROM guestbook WHERE id = :id";
	public static final String SELECT_COUNT = "SELECT count(*) FROM guestbook";
}
```

**GuestBookDao.java**

```java
package kr.or.connect.guestbook.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.guestbook.dto.Guestbook;

import static kr.or.connect.guestbook.dao.GuestbookDaoSqls.*;

@Repository
public class GuestbookDao {
	 private NamedParameterJdbcTemplate jdbc;
	    private SimpleJdbcInsert insertAction;
	    private RowMapper<Guestbook> rowMapper = BeanPropertyRowMapper.newInstance(Guestbook.class);

	    public GuestbookDao(DataSource dataSource) {
	        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	        this.insertAction = new SimpleJdbcInsert(dataSource)
	                .withTableName("guestbook")
	                .usingGeneratedKeyColumns("id");
	    }
	    
	    public List<Guestbook> selectAll(Integer start, Integer limit) {
	    		Map<String, Integer> params = new HashMap<>();
	    		params.put("start", start);
	    		params.put("limit", limit);
	        return jdbc.query(SELECT_PAGING, params, rowMapper);
	    }


		public Long insert(Guestbook guestbook) {
			SqlParameterSource params = new BeanPropertySqlParameterSource(guestbook);
			return insertAction.executeAndReturnKey(params).longValue();
		}
		
		public int deleteById(Long id) {
			Map<String, ?> params = Collections.singletonMap("id", id);
			return jdbc.update(DELETE_BY_ID, params);
		}
		
		public int selectCount() {
			return jdbc.queryForObject(SELECT_COUNT, Collections.emptyMap(), Integer.class);
		}
}
```

**GuestbookDaoTest.java**

```java
package kr.or.connect.guestbook.dao;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.guestbook.config.ApplicationConfig;
import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.dto.Log;

public class GuestbookDaoTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class); 
		GuestbookDao guestbookDao = ctx.getBean(GuestbookDao.class);
		
		Guestbook guestbook = new Guestbook();
		guestbook.setName("강경미");
		guestbook.setContent("반갑습니다. 여러분.");
		guestbook.setRegdate(new Date());
		Long id = guestbookDao.insert(guestbook);
		System.out.println("id : " + id);
		
		LogDao logDao = ctx.getBean(LogDao.class);
		Log log = new Log();
		log.setIp("127.0.0.1");
		log.setMethod("insert");
		log.setRegdate(new Date());
		logDao.insert(log);
	}

}
```

### 이슈: @Configuration 이 붙어 있는 클래스가 @ComponentScan이 되면 에러가 발생하더라. 이를 피해서 @ComponentScan하자. (이유는 알아보자)

**GuestbookService.java**

```java
package kr.or.connect.guestbook.service;

import java.util.List;

import kr.or.connect.guestbook.dto.Guestbook;

public interface GuestbookService {
	public static final Integer LIMIT = 5;
	public List<Guestbook> getGuestbooks(Integer start);
	public int deleteGuestbook(Long id, String ip);
	public Guestbook addGuestbook(Guestbook guestbook, String ip);
	public int getCount();
}
```

**GuestbookServiceImpl.java**

```java
package kr.or.connect.guestbook.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.guestbook.dao.GuestbookDao;
import kr.or.connect.guestbook.dao.LogDao;
import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.dto.Log;
import kr.or.connect.guestbook.service.GuestbookService;

@Service
public class GuestbookServiceImpl implements GuestbookService{
	
	@Autowired
	GuestbookDao guestbookDao;
	
	@Autowired
	LogDao logDao;

	@Override
	@Transactional // Transaction에서 수행해야하는 연산이 select 임으로 트랜잭션을 유지하기 위해서 @Transactional(readOnly=false)를 해주어야 한다.
	public List<Guestbook> getGuestbooks(Integer start) {
		List<Guestbook> list = guestbookDao.selectAll(start, GuestbookService.LIMIT);
		return list;
	}

	@Override
	@Transactional(readOnly=false) // Transaction 에서 수행하는 연산이 insert 임으로 트랜잭션을 유지하기 위해서 @Transactional(readOnly=false)를 해주어야 한다.
	public int deleteGuestbook(Long id, String ip) {
		int deleteCount = guestbookDao.deleteById(id);
		Log log = new Log();
		log.setIp(ip);
		log.setMethod("delete");
		log.setRegdate(new Date());
		logDao.insert(log);
		return deleteCount;
	}

	@Override
	@Transactional(readOnly=false) // Transaction 적용을 위해 @Transactional(readOnly=false) 애노테이션 추
	public Guestbook addGuestbook(Guestbook guestbook, String ip) {
		guestbook.setRegdate(new Date());
		Long id = guestbookDao.insert(guestbook);
		guestbook.setId(id);
		
//		if(1 == 1)
//			throw new RuntimeException("test exception");
//			
		Log log = new Log();
		log.setIp(ip);
		log.setMethod("insert");
		log.setRegdate(new Date());
		logDao.insert(log);
		
		
		return guestbook;
	}

	@Override
	public int getCount() {
		return guestbookDao.selectCount();
	}
	
	
}
```

**GuestbookServiceTest.java**

```java
package kr.or.connect.guestbook.service.impl;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.guestbook.config.ApplicationConfig;
import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.service.GuestbookService;

public class GuestbookServiceTest {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		GuestbookService guestbookService = ctx.getBean(GuestbookService.class);
		
		Guestbook guestbook = new Guestbook();
		guestbook.setName("강경미");
		guestbook.setContent("반갑습니다. 여러분.");
		guestbook.setRegdate(new Date());
		Guestbook result = guestbookService.addGuestbook(guestbook, "127.0.0.1");
		System.out.println(result);
	}

}

```

**GuestbookController.java**

```java
package kr.or.connect.guestbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
					   ModelMap model) {
		
		// start로 시작하는 방명록 목록 구하기
		List<Guestbook> list = guestbookService.getGuestbooks(start);
		
		// 전체 페이지수 구하기
		int count = guestbookService.getCount();
		int pageCount = count / GuestbookService.LIMIT;
		if(count % GuestbookService.LIMIT > 0)
			pageCount++;
		
		// 페이지 수만큼 start의 값을 리스트로 저장
		// 예를 들면 페이지수가 3이면
		// 0, 5, 10 이렇게 저장된다.
		// list?start=0 , list?start=5, list?start=10 으로 링크가 걸린다.
		List<Integer> pageStartList = new ArrayList<>();
		for(int i = 0; i < pageCount; i++) {
			pageStartList.add(i * GuestbookService.LIMIT);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageStartList", pageStartList);
		
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

**list.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록 목록</title>
</head>
<body>

	<h1>방명록</h1>
	<br> 방명록 전체 수 : ${count }
	<br>
	<br>

	<c:forEach items="${list}" var="guestbook">
		${guestbook.id }<br>
		${guestbook.name }<br>
		${guestbook.content }<br>
		${guestbook.regdate }<br>
	</c:forEach>
	<br>

	<c:forEach items="${pageStartList}" var="pageIndex" varStatus="status">
		<a href="list?start=${pageIndex}">${status.index +1 }</a>&nbsp; &nbsp;
	</c:forEach>

	<br>
	<br>
	<form method="post" action="write">
		name : <input type="text" name="name"><br>
		<textarea name="content" cols="60" rows="6"></textarea>
		<br> <input type="submit" value="등록">
	</form>
</body>
</html>
```

### **생각해보기**

1. **레이어 별로 개발을 진행할 때, 각 레이어별로 잘 동작하는지 확인하는 것은 매우 중요합니다. 어떤 특정 레이어가 올바르게 동작하지 않는다면 웹 어플리케이션은 제대로 동작하지 않을 것입니다. 어느 특정 레이어가 문제가 있는지 알려면, 각 레이어별로 테스트가 필요합니다. 자바에서 테스트 코드를 좀 더 효과적으로 작성할 수 있는 방법에 대해 알아보세요.**
   - 특정 레이어에 속해있는 Bean들만 ApplicationContext에 직접 로드해 해당 레이어를 테스트 하는 방법과 애노테이션을 기반으로 이러한 과정들을 대신 해주는 방법이 존재합니다.











































