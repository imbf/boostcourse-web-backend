## 1) Spring JDBC 소개

### Spring JDBC

- **JDBC 프로그래밍을 보면 반복되는 개발 요소가 있습니다.**
  - 드라이버 메모리에 로드, 커넥션 연결, Statement 객체 생성, 결과 객체 생성
  - 위의 객체들 닫기
- 이러한 반복적인 요소는 개발자를 지루하게 만듭니다.
- **개발하기 지루한 JDBC의 모든 저수준 세부사항을 스프링 프레임워크가 처리해줍니다.**
- 개발자는 필요한 부분만 개발하면 됩니다.

### Spring JDBC - 개발자가 해야 할 일은?

![image-20210105092740908](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105092740908.png)

JDBC를 사용해서 데이터베이스와 통신하기 위해 해야하는 많은 일들 중에서 어플리케이션 개발자가 해야하는 일은 다음과 같다.

- 연결 파라미터 정의
- SQL문 지정
- 파라미터 선언과 파라미터 값 제공
- 각 이터레이션 대한 작업 수행

### Spring JDBC 패키지

- **org.springframework.jdbc.core**
  - JdbcTemplate 및 관련 Helper 객체 제공

- **org.springframework.jdbc.datasource**
  - DataSource를 쉽게 접근하기 위한 유틸 클래스, 트랜젝션매니져 및 다양한 DataSource 구현을 제공

- **org.springframework.jdbc.object**
  - RDBMS 조회, 갱신, 저장등을 안전하고 재사용 가능한 객제 제공

- **org.springframework.jdbc.support**
  - jdbc.core 및 jdbc.object를 사용하는 JDBC 프레임워크를 지원

### JDBC Template

- org.springframework.jdbc.core에서 가장 중요한 클래스
- 리소스 생성, 해지를 처리해서 연결을 닫는 것을 잊어 발생하는 문제등을 피할 수 있돍 한다.
- 스테이트먼트(Statement)의 생성과 실행을 처리한다.
- SQL 조회, 업데이트, 저장 프로시저 호출, ResultSet 반복호출 등을 실행한다.
- JDBC 예외가 발생할 경우 org.springframework.dao 패키지에 정의되어 있는 일반적인 예외로 변환시킵니다.

### **JdbbcTemplate Select 실습코드**

**JdbcTemplate select 예제1**

열의 수 구하기

```java
int rowCount = this.jdbcTemplate.queryForInt("select count(*) from t_actor");
```

**JdbcTemplate select 예제2**

변수 바인딩 사용하기

```java
int countOfActorsNamedJoe = this.jdbcTemplate.queryForInt("select count(*) from t_actor where first_name = ?", "Joe"); 
```

**JdbcTemplate select 예제3**

String값으로 결과 받기

```java
String lastName = this.jdbcTemplate.queryForObject("select last_name from t_actor where id = ?", new Object[]{1212L}, String.class); 
```

**JdbcTemplate select 예제4**

한 건 조회하기 (한 행의 데이터를 조회해서 객체에 담아서 리턴해줄 수 있다.)

```java
Actor actor = this.jdbcTemplate.queryForObject(

  "select first_name, last_name from t_actor where id = ?",

  new Object[]{1212L},

  new RowMapper<Actor>() {

    public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {

      Actor actor = new Actor();

      actor.setFirstName(rs.getString("first_name"));

      actor.setLastName(rs.getString("last_name"));

      return actor;

    }

  });
```

**JdbcTemplate select 예제5**

여러 건 조회하기

```java
List<Actor> actors = this.jdbcTemplate.query(

  "select first_name, last_name from t_actor",

  new RowMapper<Actor>() {

    public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {

      Actor actor = new Actor();

      actor.setFirstName(rs.getString("first_name"));

      actor.setLastName(rs.getString("last_name"));

      return actor;

    }

  });
```

**JdbcTemplate select 예제6**

중복 코드 제거 (1건 구하기와 여러 건 구하기가 같은 코드에 있을 경우)

```java
public List<Actor> findAllActors() {

  return this.jdbcTemplate.query( "select first_name, last_name from t_actor", new ActorMapper());

}

private static final class ActorMapper implements RowMapper<Actor> {

  public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {

    Actor actor = new Actor();

    actor.setFirstName(rs.getString("first_name"));

    actor.setLastName(rs.getString("last_name"));

    return actor;

  }

}
```

### JdbcTemplate insert 예제

INSERT 하기

```java
this.jdbcTemplate.update("insert into t_actor (first_name, last_name) values (?, ?)",  "Leonor", "Watling");
```

### JdbcTemplate update 예제

UPDATE 하기

```java
this.jdbcTemplate.update("update t_actor set = ? where id = ?",  "Banjo", 5276L);
```

### JdbcTemplate delete 예제

DELETE 하기

```java
this.jdbcTemplate.update("delete from actor where id = ?", Long.valueOf(actorId));
```

### JdbcTemplate외의 접근방법

JdbcTemplate 기반의 더 편리한 JDBC Programming 접근 방법

- **NamedParameterJdbcTemplate**
  - JdbcTemplate에서 JDBC statement 인자를 ?를 사용하는 대신 파라미터명을 사용하여 작성하는 것을 지원
  - [NamedParameterJdbcTemplate 예제](https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#jdbc-NamedParameterJdbcTemplate)

- **SimpleJdbcTemplate**
  - JdbcTemplate과 NamedParameterJdbcTemplate 합쳐 놓은 템플릿 클래스
  - 이제 JdbcTemplate과 NamedParameterJdbcTemplate에 모든 기능을 제공하기 때문에 삭제 예정될 예정(deprecated)
  - [SimpleJdbcTemplate 예제](https://www.concretepage.com/spring/simplejdbctemplate-spring-example)

- **SimpleJdbcInsert**
  - 테이블에 쉽게 데이터 insert 기능을 제공
  - [SimpleJdbcInsert 예제](https://www.tutorialspoint.com/springjdbc/springjdbc_simplejdbcinsert.htm)

### 생각해보기

1. JDBC 프로그래밍이 불편해서 이를 해결하기 위해서 등장한 기술에는 Spring JDBC 외에도 다양한 기술들이 존재합니다. 대표적으로 JPA와 MyBatis가 그러한 기술입니다. 문제를 해결하는 방법이 왜 여러 가지가 존재할끼요?
   - 매번 중복되는 코드가 발생하고 이러한 중복되는 코드는 많은 변경을 야기하고 이러한 많은 변경은 많은 버그를 야기하기 때문입니다. 즉, 여러 해결 방법 중 내 도메인에 맞는 문제 해결 방법을 선택해서 좀 더 품질 좋은 소프트웨어를 개발하기 위해서 여러 방법이 존재한다고 생각합니다.

---

## 2) Spring JDBC 실습 -1

### DTO란?

- DTO란 Data Transfer Object의 약자입니다.
- **계층간 데이터 교환을 위한 자바빈즈입니다.**
- **여기서의 계층이란 컨트롤러, 뷰, 비지니스 계층, 퍼시스턴스 계층을 의미합니다.**
- 일반적으로 DTO는 로직을 가지고 있지 않고, 순수한 데이터 객체입니다.
- **필드와 getter, setter를 가진다. 추가적으로 toString(), equals(), hashCode()등의 Object 메소드를 오버라이딩 할 수 있습니다.**

### DAO란?

- **DAO란 Data Access Object의 약자로 데이터를 조회하거나 조작하는 기능을 전담하도록 만든 객체입니다. 보통 데이터베이스를 조작하는 기능을 전담하는 목적으로 만들어집니다.**

### ConnectionPool 이란?

- DB연결은 비용이 많이 듭니다.
- **커넥션 풀은 미리 커넥션을 여러 개 맺어 둡니다. 커넥션이 필요하면 커넥션 풀에게 빌려서 사용한 후 반납합니다.**
- 커넥션을 반납하지 않으면 어떻게 될까요?
  - 커넥션 풀에서 사용가능한 커넥션이 없어서 프로그램이 늦어지거나 장애를 발생시킬 수 있습니다.

### DataSource란?

- **DataSource는 커넥션 풀을 관리하는 목적으로 사용되는 객체입니다.**
  - 어떻게 보면 커넥션 풀을 관리하는 DataSource 객체가 참 중요한 것 같다.
  - 이러한 객체들을 개발자가 관리하지 않고 Spring JDBC가 관리해주니 참 고마운 것 같다. 하지만 성능에 문제가 생긴다면 이러한 객체들을 뜯어 봐야만 한다.
- **DataSource를 이용해 커넥션을 얻어오고 반납하는 등의 작업을 수행합니다.**
  - 꼭 주의하자!

---

## 2) Spring JDBC 실습 - 2

### 모든 실습은 이어서 진행되니 아래의 클래스들을 이어서 참고하시기 바랍니다.

**pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>kr.or.connect</groupId>
  <artifactId>daoexam</artifactId>
  <version>0.0.1-SNAPSHOT</version>

  <name>daoexam</name>
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

    <!-- 스프링 핵심 Core -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <!-- Spring JDBC를 활용하기 위한 라이브러리 의존성 -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <!-- Spring Transaction을 활용하기 위한 라이브러리 의존성 -->
	 <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>${spring.version}</version>
    </dependency>
    
    <!-- MySQL과 Connection을 위한 Driver가 포함된 라이브러리 의존성 -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.18</version>
    </dependency>
    
    <!-- Connection Pool을 관리하기 위한 DataSource 객체를 가져오기 위한 라이브러리 의존성  -->
    <dependency>
    	<groupId>org.apache.commons</groupId>
    	<artifactId>commons-dbcp2</artifactId>
    	<version>2.1.1</version>
    </dependency>
  </dependencies>

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

**ApplicationConfig.java**

```java
package kr.or.connect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DBConfig.class})	// 다른 설정 파일을 Import 하기위한 애노테이션
public class ApplicationConfig {

}
```

**DBConfig.java**

```java
package kr.or.connect.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // 트랜잭션 관리를 위한 애노테이션
public class DBConfig {
	private String driverClassName = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/projectA?serverTimezone=UTC";
	
	private String username = "root";
	private String password = "";

	// Connection Pool 관리를 위한 DataSource 객체 Bean 등
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
}
```

**DataSourceTest.java**

DataSource를 활용해서 Connection이 잘 되는지 DB 접속 테스트

```java
package kr.or.connect.main;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.config.ApplicationConfig;

public class DataSourceTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		DataSource dataSource = ctx.getBean(DataSource.class);
		
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			if(connection != null) {
				System.out.println("DB 접속 성공!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
```

---

## 2) Spring JDBC 실습 -3

**Role.java**

어플리케이션에서 관리하기 위한 Role 클래스

```java
package kr.or.connect.daoexam.dto;

public class Role {
	
	private int roleId;
	private String description;
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", description=" + description + "]";
	}
	
}

```

**RoleDaoSqls.java**

자바 어플리케이션이 DB와 연산을 수행하기 위한 쿼리문 저장 클래스

```java
package kr.or.connect.daoexam.dao;

public class RoleDaoSqls {
	public static final String SELECT_ALL = "SELECT role_id, description FROM role order by role_id";
	
}
```

**RoleDao.java**

DB와의 연산(쿼리)을 위한 Data Access Object 클래스

```java
package kr.or.connect.daoexam.dao;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.daoexam.dto.Role;
import static kr.or.connect.daoexam.dao.RoleDaoSqls.*;

@Repository
public class RoleDao {
	
	// 이름을 이용해 파라미터를 사용하기 위한 JdbcTemplate
	private NamedParameterJdbcTemplate jdbc;
	
	// Row값을 Role객체로 Mapping하기 위한 인스턴스 필드
	private RowMapper<Role> rowMapper = BeanPropertyRowMapper.newInstance(Role.class); 
	
	// Spring Version 4.3 부터는 ComponentScan으로 Bean을 검색 했을 때 기본 생성자가 없다면 해당 생성자에 자동으로 객체를 주입해준다.
	public RoleDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Role> selectAll() {
		return jdbc.query(SELECT_ALL, Collections.EMPTY_MAP, rowMapper);
	}

}
```

**SelectAllTest.java**

RoleDao의 selectAll 메소드 테스트를 위한 테스트 클래스

```java
package kr.or.connect.daoexam.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.daoexam.config.ApplicationConfig;
import kr.or.connect.daoexam.dao.RoleDao;
import kr.or.connect.daoexam.dto.Role;

public class SelectAllTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		RoleDao roleDao = ctx.getBean(RoleDao.class);
		
		List<Role> roles = roleDao.selectAll();
		
		for(Role role: roles) {
			System.out.println(role);
		}

	}

}
```

---

## 2) Spring JDBC 실습 - 4

**RoleDapSqls.java**

Update 쿼리를 실행하기 위해 해당 쿼리문을 문자열 상수로써 추가

```java
package kr.or.connect.daoexam.dao;

public class RoleDaoSqls {
	public static final String SELECT_ALL = "SELECT role_id, description FROM role order by role_id";
	public static final String UPDATE = "UPDATE role set description = :description WHERE role_id = :roleId";
	
}
```

**RoleDao.java**

Spring JDBC를 활용해 Insert 쿼리를 수행하기 위한 insert() 메소드 추가

Spring JDBC를 활용해 Update쿼리를 수행하기 위한 update() 메소드 추가

```java
package kr.or.connect.daoexam.dao;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.daoexam.dto.Role;
import static kr.or.connect.daoexam.dao.RoleDaoSqls.*;

@Repository
public class RoleDao {
	
	// 이름을 이용해 파라미터를 사용하기 위한 JdbcTemplate
	private NamedParameterJdbcTemplate jdbc;
	
	// Insert 쿼리를 쉽게 사하기 위한 JdbcTemplate
	private SimpleJdbcInsert insertAction;
	
	// Row값을 Role객체로 Mapping하기 위한 인스턴스 필드
	private RowMapper<Role> rowMapper = BeanPropertyRowMapper.newInstance(Role.class); 
	
	// Spring Version 4.3 부터는 ComponentScan으로 Bean을 검색 했을 때 기본 생성자가 없다면 해당 생성자에 자동으로 객체를 주입해준다.
	public RoleDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("role");
	}
	
	public List<Role> selectAll() {
		return jdbc.query(SELECT_ALL, Collections.EMPTY_MAP, rowMapper);
	}
	
	public int insert(Role role) {
		// Role 객체의 Property를 DB 연산을 위한 Map 객체로 바꾸어준다.
		SqlParameterSource params = new BeanPropertySqlParameterSource(role);
		return insertAction.execute(params);
	}
	
	public int update(Role role) {
		// Role 객체의 Property를 DB 연산을 위한 Map 객체로 바꾸어준다.
		SqlParameterSource params = new BeanPropertySqlParameterSource(role);
		return jdbc.update(UPDATE, params);
	}
	
}
```

**JDBCTest.java**

insert 와 update 쿼리를 테스트하기 위한 테스트 클래스

```java
package kr.or.connect.daoexam.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.daoexam.config.ApplicationConfig;
import kr.or.connect.daoexam.dao.RoleDao;
import kr.or.connect.daoexam.dto.Role;

public class JDBCTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		RoleDao roleDao = ctx.getBean(RoleDao.class);
		
		Role role = new Role();
		role.setRoleId(600);
		role.setDescription("CEO");
		
      // Insert Test
		int insertCount = roleDao.insert(role);
		System.out.println(insertCount + "건 입력하였습니다.");
		
      // Update Test
		role.setDescription("CTO");
		int updateCount = roleDao.update(role);
		System.out.println(updateCount + "건 수정하였습니다.");
		
      // Select All Test
		List<Role> roles = roleDao.selectAll();
		for (Role r: roles) {
			System.out.println(r);
		}

	}

}
```

---

## 2) Spring JDBC 실습 - 5

### SelectById와 DeleteById 실습

**RoleDapSqls.java**

SelectById와 DeleteById를 실습하기 위해 SQL문을 문자열 상수로써 추가

```java
package kr.or.connect.daoexam.dao;

public class RoleDaoSqls {
	public static final String SELECT_ALL = "SELECT role_id, description FROM role order by role_id";
	public static final String UPDATE = "UPDATE role set description = :description WHERE role_id = :roleId";
	public static final String SELECT_BY_ROLE_ID = "SELECT role_id, description FROM role WHERE role_id = :roleId";
	public static final String DELETE_BY_ROLE_ID = "DELETE FROM role WHERE role_id = :roleId";
	
}

```

**RoleDao.java**

deleteById와 selectById 메소드 추가

```java
package kr.or.connect.daoexam.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.daoexam.dto.Role;
import static kr.or.connect.daoexam.dao.RoleDaoSqls.*;

@Repository
public class RoleDao {
	
	// 이름을 이용해 파라미터를 사용하기 위한 JdbcTemplate
	private NamedParameterJdbcTemplate jdbc;
	
	// Insert 쿼리를 쉽게 사하기 위한 JdbcTemplate
	private SimpleJdbcInsert insertAction;
	
	// Row값을 Role객체로 Mapping하기 위한 인스턴스 필드
	private RowMapper<Role> rowMapper = BeanPropertyRowMapper.newInstance(Role.class); 
	
	// Spring Version 4.3 부터는 ComponentScan으로 Bean을 검색 했을 때 기본 생성자가 없다면 해당 생성자에 자동으로 객체를 주입해준다.
	public RoleDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("role");
	}
	
	public List<Role> selectAll() {
		return jdbc.query(SELECT_ALL, Collections.EMPTY_MAP, rowMapper);
	}
	
	public int insert(Role role) {
		// Role 객체의 Property를 DB 연산을 위한 Map 객체로 바꾸어준다.
		SqlParameterSource params = new BeanPropertySqlParameterSource(role);
		return insertAction.execute(params);
	}
	
	public int update(Role role) {
		// Role 객체의 Property를 DB 연산을 위한 Map 객체로 바꾸어준다.
		SqlParameterSource params = new BeanPropertySqlParameterSource(role);
		return jdbc.update(UPDATE, params);
	}
	
	public int deleteById(Integer id) {
		Map<String, ?> param = Collections.singletonMap("roleId", id);
		return jdbc.update(DELETE_BY_ROLE_ID, param);
	}
	
	public Role selectById(Integer id) {
		Map<String, ?> param = Collections.singletonMap("roleId", id);
		try {
			return jdbc.queryForObject(SELECT_BY_ROLE_ID, param, rowMapper);			
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
```

**JDBCTest.java**

```java
package kr.or.connect.daoexam.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.daoexam.config.ApplicationConfig;
import kr.or.connect.daoexam.dao.RoleDao;
import kr.or.connect.daoexam.dto.Role;

public class JDBCTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		RoleDao roleDao = ctx.getBean(RoleDao.class);
		
		Role role = new Role();
		role.setRoleId(600);
		role.setDescription("CEO");
		
		Role resultRole = roleDao.selectById(600);
		System.out.println(resultRole);
		
		int deleteCount = roleDao.deleteById(600);
		System.out.println(deleteCount + "건을 삭제하였습니다.");

	}

}
```

### 생각해보기

1. JdbcTemplate을 이용하지 않고 NamedParameterJdbcTemplate를 이용하여 DAO를 작성한 이유는 무엇이라고 생각하나요?

   - JdbcTemplate을 사용하면 자바에 존재하는 쿼리문과 DB 연산으로 부터 발생하는 값 객체의 파라미터를 관리하기 매우 힘들어진다.

      

     대표적인 예로, Java는 클래스의 인스턴스 변수에 camelCase를 주로 사용하고 sql은 Column명으로 snake case를 주로 사용하기 때문에 서로 통신하기 위해서 Parsing 하는 작업이 필요 하고 이러한 작업들을 NamedParameterJdbcTemplate이 지원해준다.

      

     만약 JdbcTemplate을 직접 사용한다면 NamedParameterJdbcTemplate가 재공해주는 Parsing 작업들을 개발자가 직접해줘야만 하고 이는 Type Safe하지 못할 뿐더러 많은 중복된 코드를 발생시킨다.

      

     이러한 중복된 코드는 모든 에러의 근원이며 이를 없애기 위해서 JdbcTemplate 대신 NamedParameterJdbcTemplate를 사용할 것을 권장한다.



























