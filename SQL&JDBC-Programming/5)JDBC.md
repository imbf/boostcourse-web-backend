## 1) JDBC

### JDBC(Java Database Connectivity)

- **JDBC(Java Database Connectivity)의 정의**
  - 자바를 이용한 데이터베이스 접속과 SQL 문장의 실행, 그리고 실행 결과로 얻어진 데이터의 핸들링을 제공하는 방법과 절차에 관한 규약
  - 자바 프로그램내에서 SQL문을 실행하기 위한 자바 API
  - SQL과 프로그래밍 언어의 통합 접근 중 한 형태
- **JAVA는 표준 인터페이스인 JDBC API를 제공**
  - 인터페이스가 이미 정의되어 있기 때문에 어떤 Vendor등 JDBC인터페이스를 구현한 드라이버를 제공하기 때문에 이를 사용할 수 있다.
- **데이터베이스 벤더, 또는 기타 써드파티에서는 JDBC 인터페이스를 구현한 드라이버(Driver)를 제공한다.**

### 개요 - 환경 구성

- JDK 설치

- JDBC 드라이버 설치

  - Maven에 다음과 같은 의존성을 추가한다.

  - MySQL을 사용하기 때문에 MySQL JDBC 드라이버를 사용하면 된다.

    - 직접 MySQL 사이트에서 해당 드라이버를 다운로드 할 수도 있겠지만, Maven을 사용해서 Maven Respository에 존재하는 드라이버를 로컬에 다운받을 수 있다.

      ```xml
      <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>5.1.45</version>
      </dependency>
      ```

### JDBC 사용 - 단계별 정리

- JDBC를 이용한 프로그래밍 방법

  1. import java.sql.*;

  2. 드라이버 로드

     - `Class.forName("com.mysql.jdbc.Driver");`
       - 이 메서드를 이용하면 com.mysql.jdbc.Driver 객체가 메모리에 올라간다.

  3. Connection 객체 생성 (DB와 연결)

     1. `String dburl = "jdbc:mysql://localhost/dbName";`

     2. `Connection con = DriverManager.getConnection(dburl,ID,PWD);`

        - 예제 코드 (Oracle DB Connection)

          ```java
          public static Connection getConnection() throws Exception{
          	String url = "jdbc:oracle:thin:@117.16.46.111:1521:xe";
          	String user = "smu";
          	String password = "smu";
          	Connection conn = null;
          	Class.forName("oracle.jdbc.driver.OracleDriver");
          	conn = DriverManager.getConnection(url, user, password);
          	return conn;
          }
          ```

  4. Statement 객체를 생성 및 질의 수행 (쿼리 작성 및 실행)

     - `Statement stmt = con.createStatement();`

  5. SQL문에 결과물이 있다면 ResultSet 객체를 생성한다. (결과 받아서 비즈니스단에서 활용)

     - 쿼리문이 무엇인지에 대해서 ResultSet 객체가 다르다. 이 부분을 꼭 중요시 생각해 개발하자.

     - ```java
       ResultSet rs = stmt.executeQuery("select no from user");
       while(rs.next())
          System.out.println(rs.getInt("no"));
       ```

  6. 모든 객체를 닫는다.(Connection 종료)

     - 객체를 만든 역순으로 닫는다. (ResultSet -> Statement -> Connection)
       1. rs.close();
       2. stmt.close();
       3. con.close();

###  JDBC 클래스의 생성관계

1. DriverManager를 이용해서 Connection인스턴스를 얻는다.
2. Connection을 통해서 Statement를 얻는다.
3. Statement를 이용해서 ResultSet을 얻는다.

### 예제 소스 코드

```java
// DB에서 GuestBook 테이블을 참조해서 이를 객체로써 가져오는 코드.
public List<GuestBookVO> getGuestBookList() {
	List<GuestBookVO> list = new ArrayList<>();
   GuestBookVO vo = null;
   Connection conn = null;
   PreparedStatement ps = null;
   ResultSet rs = null;
   try{
      conn = DBUtil.getConnection();
      String sql = "select * from guestbook";
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next()) {
         vo = new GuestBookVO();
         vo.setNo(rs.getInt(1));
         vo.setId(rs.getString(2));
         vo.setTitle(rs.getString(3));
         vo.setContent(rs.getString(4));
         vo.setRegDate(rs.getString(5));
         list.add(vo);
      } 
   } catch(Exception e) {
      e.printStackTrace();
   } finally {
      DBUtil.close(conn, ps, rs);
   }
   return list;
}

// Connection close 메소드

public static void close(Connection conn, PreparedStatement ps) {
   if (ps != null) {
      try {
         ps.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
   
   if (conn != null) {
      try {
         conn.close();
      } catch (SQLException e) {
         e.printStackTtrace();
      }
   }
}
```

### **생각해보기**

1. java.sql패키지를 보면 대부분이 interface로 되어 있는 것을 알 수 있습니다.
   이를 실제로 구현하는 것은 DBMS를 만든 회사입니다.
   java.sql외에 JAVA가 인터페이스만 대부분 제공하는 패키지는 또 어떤 것이 있을까요?
   참고로 XML문서의 표준은 w3c에서 정합니다.
   - Java가 인터페이스(명세만) 제공하는 패키지는 자바 ORM 명세인 JPA(Java Persistent API)가 있으며 이를 구현하는 구현체로는 Hibernate, EclipseLink 등이 존재합니다.

## 2) JDBC 실습 - 1

### JDBC 실습 과정

1. 프로젝트 생성
2. Java 버젼 맞추기
3. 프로젝트 라이브러리 의존성 설정하기
4. JDBC 실습 시작

### 실습 코드

**Main class**

```java
package kr.or.connect.jdbcexam;

import kr.or.connect.jdbcexam.dao.RoleDao;
import kr.or.connect.jdbcexam.dto.Role;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       RoleDao dao = new RoleDao();
       Role role = dao.getRole(100);
       
       System.out.println(role);
      
    }
}

```

**RoleDao Class**

```java
package kr.or.connect.jdbcexam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.connect.jdbcexam.dto.Role;

public class RoleDao {
	private static String dbUrl = "jdbc:mysql://localhost:3306/connectdb?serverTimezone=UTC";
	private static String dbUser = "connectuser";
	private static String dbPasswd = "connect123!@#"; 
	
	public Role getRole(Integer roleId) {
		Role role = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이버가 메모리에 로딩된다.
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
			String sql = "SELECT role_id, description FROM role WHERE role_id = ?";
			ps = conn.prepareStatement(sql); // Statement에 변수를 넣기 위해서 해당 객체 사
			ps.setInt(1, roleId); // 인덱스는 1부터 시작
			rs = ps.executeQuery(); // 쿼리 실행해 ResultSet 객체 생
			
			if (rs.next()) {
				Integer id = rs.getInt("role_id"); // Column Name 뿐만 아니라 Index를 사용해서 값을 가져올 수 있다.
				String description = rs.getString("description");
				role = new Role(id, description);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return role;
	}
}
```

**Role 클래스**

```java
package kr.or.connect.jdbcexam.dto;

public class Role {
	private Integer roleId;
	private String description;
	
	public Role(Integer roleId, String description) {
		this.roleId = roleId;
		this.description = description;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
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



### 이슈 발생

현재 위 강의를 실습하시는 분들은 mysql을 비교적 최근에 설치하셨을 텐데요!! 아마 에러가 발생한다면 저와 비슷한 이유일 겁니다.

1. **mysql connector java 라이브러리 의존성이 현재 로컬에 설치된 mysql과 맞지 않는다.**
   해결방법 => mysql-connector-java 라이브러리 의존성을 현재 로컬에 설치된 mysql과 호환되는 버젼으로 올린다.

2. **최신 버젼의 jdbc Driver는 com.mysql.jdbc.Driver가 deprecated** 되었음으로 해당 코드를 아래와 같이 바꾼다.
   => Class.*forName*("com.mysql.cj.jdbc.Driver");

3. **Time Zone 에러가 발생한다면 dbURL을 아래와 같이 바꾼다.**
   => **private** **static** String *dbUrl* = "jdbc:mysql://localhost:3306/connectdb?serverTimezone=UTC";;

저 말고도 아래에 다양한 해결 방법이 존재하니, 포기하지 마시고 꼭 같이 완주 해봅시다~!!
**(이 또한 다 좋은 성장의 밑바탕이 될 꺼에요!!)**

---

## JDBC 실습 - 2

### Role을 하나 추가하는 메서드

```java
	public int addRole(Role role) {
		int insertCount = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
			
			String sql = "INSERT INTO role (role_id, description) VALUES (?, ?)";
			
			ps = conn.prepareStatement(sql); // sql의 ?의 값 인자로써 넣어주기 위한 prepareStatement 객
			
			ps.setInt(1, role.getRoleId());
			ps.setString(2, role.getDescription());
			
			insertCount = ps.executeUpdate(); // update query를 사용하기 위한 메서
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
```

### Role을 업데이트 하는 메서드

```java
	public int updateRole(Integer roleId, Role role) {
		int updateCount = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
			String sql = "UPDATE role SET role_id = ?, description = ? WHERE role_id = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(3, roleId);
			ps.setInt(1, role.getRoleId());
			ps.setString(2, role.getDescription());
			
			updateCount = ps.executeUpdate(); // Update Query를 사용하기 위한 메서드
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return updateCount;
	}
```

### Role을 삭제하는 메서드

```java
	public int deleteRole(Integer roleId) {
		int deleteCount = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
			String sql = "DELETE FROM role WHERE role_id = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roleId);
			
			deleteCount = ps.executeUpdate(); // delete Query도 동일하게 executeUpdate() 메서드 사용
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return deleteCount;
	}
```

### Role을 여러개 가져오는 리스트

```java
	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql = "SELECT role_id, description FROM role ORDER BY role_id DESC";
		
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			try (ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					Integer id = rs.getInt("role_id");
					String description = rs.getString("description");
					roles.add(new Role(id, description));
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return roles;
	}
```

### 생각해보기

1.  JDBC를 이용해서 입력/수정/삭제/조회 메소드를 만들다 보면, 반복적인 코드가 많다는 것을 알 수 있습니다.반복되는 부분을 별도의 메서드나 클래스로 추출한다면 어떤 부분의 중복을 제거할수 있을까요?
   - 제 생각엔 DataBase와 Connection을 만드는 부분과, Driver 클래스를 JVM에 로드하는 부분의 중복을 제거할 수 있을 것 같습니다.
2.  JDK7부터 추가된 try-with-resource 구문을 이용한다면 예제 코드 중 어떤 부분을 줄일 수 있을까요? 
   - Java SE 7 부터 추가된 try-with-resources 문을 사용한다면, Connection 객체, PreparedStatement 객체, ResultSet 객체를 사용한 후 자동으로 닫아주기 때문에 해당 객체의 close 부분의 코드를 줄일 수 있습니다.













