## 1) Spring 이란?

### Spring Framework란?

강사님은 Framework를 생각하면서 반제품을 떠올린다.

- 엔터프라이즈 급 어플리케이션을 구축할 수 있는 가벼운 솔루션이자, One-Stop-Shop

  - One-Stop-Shop : 모든 과정을 함께 해결할 수 있는 솔루션이다.

- **원하는 부분만 가져다 사용할 수 있도록 모듈화가 잘 되어 있습니다.**

  - 스프링 프로젝트 전체를 가져다 쓸 필요가 없다.

- **IoC(Inversion of Control) 컨테이너입니다.**

- 선언적으로 트랜잭션을 관리할 수 있다.

- 완전한 기능을 갖춘 MVC Framework를 제공한다.

- **AOP(Aspect Oriented Programming) 지원**

- **PSA(Portable Service Abstraction) 지원**

- **스프링은 도메인 논리 코드와 쉽게 분리될 수 있는 구조로 되어 있습니다.** (매우 중요)

- Spring Framework Runtime Architecture

  ![image-20210104100409512](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210104100409512.png)

### 프레임 워크 모듈

- 스프링 프레임워크는 약 20개의 모듈로 구성되어 있습니다.
- 필요한 모듈만 가져다 사용할 수 있습니다.

### AOP 와 인스트루멘테이션 (Instrumentation)

- **spring-AOP :** AOP 얼라이언스(Alliance)와 호환되는 방법으로 AOP를 지원합니다.
- **spring-aspects :** AspectJ와의 통합을 제공합니다.
- **spring-instrument :** 인스트루멘테이션을 지원하는 클래스와 특정 WAS에서 사용하는 클래스로 더 구현체를 제공합니다. 참고로 BCI(Byte Code Instrumentations)은 런타임이나 로드(Load) 때 클래스의 바이트 코드에 변경을 가하는 방법을 말합니다.

### 메시징(Messaging)

- **spring-messaging :** 스프링 프레임워크 4는 메시지 기반 어플리케이션을 작성할 수 있는 Message, MessageChannel, MessageHandler 등을 제공합니다. 또한, 해당 모듈에는 메소드에 메시지를 맵핑하기 위한 어노테이션도 포함되어 있으며, Spring MVC 어노테이션과 유사합니다.

### 데이터 엑서스(Data Access) / 통합(Integration)

- **데이터 엑세스/통합 계층은 JDBC, ORM, OXM, JMS 및 트랜잭션 모듈로 구성되어 있다.**
- **spring-jdbc** : 자바 JDBC프로그래밍을 스프링 환경에서 쉽게 할 수 있도록 기능을 제공합니다.
- **spring-tx** : 선언적 트랜잭션 관리를 할 수 있는 기능을 제공합니다.
- spring-orm : JPA, JDO및 Hibernate를 포함한 ORM API를 위한 통합 레이어를 제공합니다.
- spring-oxm : JAXB, Castor, XMLBeans, JiBX 및 XStream과 같은 Object/XML 맵핑을 지원합니다.
- spring-jms : 메시지 생성(producing) 및 사용(consuming)을 위한 기능을 제공, Spring Framework 4.1부터 spring-messaging모듈과의 통합을 제공합니다.

### 웹(Web)

- **웹 계층은 spring-web, spring-webmvc, spring-websocket, spring-webmvc-portlet 모듈로 구성됩니다.**
- **spring-web** : 멀티 파트 파일 업로드, 서블릿 리스너 등 웹 지향 통합 기능을 제공한다. HTTP클라이언트와 Spring의 원격 지원을 위한 웹 관련 부분을 제공합니다.
- **spring-webmvc** : Web-Servlet 모듈이라고도 불리며, Spring MVC 및 REST 웹 서비스 구현을 포함합니다.
- spring-websocket : 웹 소켓을 지원합니다.
- spring-webmvc-portlet : 포틀릿 환경에서 사용할 MVC 구현을 제공합니다.

**생각해보기**

1. 스프링은 프레임워크라고 합니다. 프레임워크와 라이브러리는 어떤 차이가 있을까요? 조사해보세요.
   - 프레임워크와 라이브러리의 차이는 사용의 관점에서 존재한다고 생각합니다. 라이브러리를 사용하는 애플리케이션 코드는 애플리케이션 흐름을 직접 제어한다. 단지 동작하는 중에 필요한 기능이 있을 때 능동적으로 라이브러리를 사용할 뿐이다. 반면에 프레임워크는 거꾸로 애플리케이션 코드가 프레임워크에 의해서 사용된다.(IOC) 보통 프레임워크 위에 개발한 클래스를 등록해두고, 프레임워크가 흐름을 주도하는 중에 개발자가 만든 애플리케이션 코드를 사용하도록 만드는 방식이다. 즉, 프레임워크와 라이브러리의 가장 큰 차이는 제어의 역전(IoC)이다.

---

## 2) Spring IoC/DI 컨테이너

### 컨테이너(Container)

- **컨테이너는 인스턴스의 생명주기를 관리한다.**
  - 대표적인 컨테이너 예시인 Servlet Container인 WAS (Provider: Tomcat)
    - Servlet을 실행해주는 WAS는 Servlet 컨테이너를 가지고 있다고 말합니다.
    - WAS는 웹 브라우저로부터 서블릿 URL에 해당하는 요청을 받으면, 서블릿을 메모리에 올린 후 실행합니다.
    - 개발자가 서블릿 클래스를 작성했지만, 실제로 메모리에 올리고 실행하는 것은 WAS가 가지고 있는 Servlet 컨테이너입니다.
    - Servlet 컨테이너는 동일한 서블릿에 해당하는 요청을 받으면, 또 메모리에 올리지 않고 기존에 메모리에 올라간 서블릿을 실행하여 그 결과를 웹 브라우저에게 전달합니다.
- 생성된 인스턴스들에게 추가적인 기능을 제공한다.

### IoC(Inversion of Control)

- IoC란 Inversion of Control의 약어이고, 보통 제어의 역전이라고 번역한다.
- **개발자는 프로그램의 흐름을 제어하는 코드를 작성한다. 그런데, 이 흐름의 제어를 개발자가 하는 것이 아니라 다른 프로그램이 그 흐름을 제어하는 것을 IoC라고 말한다. (Framework의 대표적인 특징)**

### DI(Dependency Injection)

- DI는 Dependency Injection의 약자로, 의존성 주입이란 뜻을 가지고 있다.

- **DI는 클래스 사이의 의존 관계를 빈(Bean)설정 정보를 바탕으로 컨테이너가 자동으로 연결해주는 것을 말한다.**

- DI가 적용 안된 예시

  개발자가 직접 인스턴스를 생성한다.

  ```java
  class 엔진 {
  
  }
  
  class 자동차 {
       엔진 v5 = new 엔진();
  }
  ```

- **Spring에서 DI가 적용된 예**

  컨테이너가 변수에 인스턴스를 할당해준다.

  ```java
  @Component
  class 엔진 {
  
  }
  
  @Component
  class 자동차 {
       @Autowired
       엔진 v5;
  }
  ```

- **서블릿에서 이미 DI와 IoC를 사용했었다.**

### Spring에서 제공하는 IoC/DI 컨테이너

- **BeanFactory :** IoC/DI에 대한 기본 기능을 가지고 있습니다.
- **ApplicationContext :** BeanFactory의 모든 기능을 포함(상속)하며, 일반적으로 BeanFactory보다 추천됩니다. <u>트랜잭션처리, AOP등에 대한 처리를 할 수 있습니다. BeanPostProcessor, BeanFactoryPostProcessor등을 자동으로 등록하고, 국제화 처리, 어플리케이션 이벤트 등을 처리할 수 습니다.</u>

- <u>BeanPostProcessor :</u> **컨테이너의 기본로직을 오버라이딩하여 인스턴스화 와 의존성 처리 로직 등을 개발자가 원하는 대로 구현 할 수 있도록 합니다.**
- <u>BeanFactoryPostProcessor :</u> 설정된 메타 데이터를 커스터마이징 할 수 있습니다.

**생각해보기**

1. 스프링 프레임워크는 DI Container라고도 말을 합니다. 스프링 프레임워크 이외에도 DI Container는 존재할까요?
   - DI Container는 Spring IoC Container 이외에도 PicoContainer, Guice 등이 있습니다.
     - **PicoContainer** : PicoContainer는 DI기능에 집중한 작은 컨테이너 프레임워크로서, 큰 오버헤드가 없이 단순하게 구성할 수 있습니다. 비교적 작은 프로젝트에 적합합니다.
     - **Guice** : Guice또한 단순하게 구성할수 있으며 Annotation, Generics등의 기능을 적극적으로 사용한다는 특징이 있습니다. 무엇보다 큰 장점은 속도가 빠르다는 것입니다.

---

## 3) xml파일을 이용한 설정

### Bean class란?

예전에는 Visual 한 컴포넌트를 Bean이라고 불렀지만, 근래 들어서는 일반적인 Java클래스를 Bean클래스라고 보통 말합니다.

Bean클래스의 3가지 특징은 다음과 같습니다.

- **기본생성자를 가지고 있습니다.**
- **필드는 private하게 선언합니다.**
- **getter, setter 메소드를 가집니다.**
- **getName() setName() 메소드를 name 프로퍼티(property)라고 합니다. (용어 중요)**
- 내가 직접 Bean Class의 인스턴스를 생성할 것이 아니라, 프레임워크 내부에서 인스턴스가 생성되어야 하기 때문에 이를 위해 다음과 같은 규칙이 필요하다.

### xml 파일을 이용한 설정 실습

**pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>kr.or.connect</groupId>
  <artifactId>diexam01</artifactId>
  <version>0.0.1-SNAPSHOT</version>

  <name>diexam01</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <!-- 상수로 사용할 수 있도록 하는 pom.xml의 property  -->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    
    <!-- 스프링 프로젝트들의 버젼 관리를 위해서 이 상수를 사용한다. -->
    <spring.version>4.3.14.RELEASE</spring.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    
    <!-- Import Spring Core  -->
    <dependency>
    	<groupId>org.springframework</groupId>
    	<artifactId>spring-context</artifactId>
    	<version>${spring.version}</version>
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

**UserBean.java**

```java
package kr.or.connect.diexam01;

public class UserBean {
	
	private String name;
	private int age;
	private boolean male;
	
	public UserBean() {
		
	}
	
	public UserBean(String name, int age, boolean male) {
		this.name = name;
		this.age = age;
		this.male = male;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}
	
}
```

**src/main/resources/applicationContext.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<!-- id는 변수명이라고 할 수 있고, class는 인스턴스를 생성하는 클래스명이다.(패키지명까지 꼭 작성) -->
	<bean id="userBean" class="kr.or.connect.diexam01.UserBean"/>
	
</beans>
```

**ApplicationContextExam01.java**

```java
package kr.or.connect.diexam01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextExam01 {

	public static void main(String[] args) {
		// xml 설정 파일을 명시해준다.
      // main에 resources는 java 폴더와 마찬가지로 classpath로 지정된다.
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		System.out.println("공장 생성 완료!!");
		
		// ApplicationContext(Container)에서 Bean을 가져온다.
		UserBean userBean = (UserBean) ctx.getBean("userBean");
		userBean.setName("Bae");
		
		System.out.println(userBean.getName());
		
		UserBean userBean2 = (UserBean) ctx.getBean("userBean");

      // Spring Bean들은 기본적으로 Default Scope가 Singlton 이기 때문에 특별한 설정 없이 특정 레이어에서 Bean을 주입 받을 경우 매번 같은 인스턴스를 가져온다.
		if (userBean == userBean2) {
			System.out.println("출력된 userBean과 userBean2 같은 인스턴스입니다.");
		}
	}
}
```

### xml 파일을 이용한 설정 및 DI 실습

**Engine.java**

```java
package kr.or.connect.diexam01;

public class Engine {
	public Engine() {
		System.out.println("Engine 생성 완료");
	}
	
	public void exec() {
		System.out.println("엔진이 동작합니다.");
	}
}
```

**Car.java**

```java
package kr.or.connect.diexam01;

public class Car {
	private Engine v8;
	
	public Car() {
		System.out.println("Car 생성 완료");
	}
	
	public void setEngine(Engine engine) {
		this.v8 = engine;
	}
	
	public void run() {
		System.out.println("엔진을 이용하여 달립니다.");
		v8.exec();
	}
	
}
```

**src/main/resources/applicationContext.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<!-- id는 변수명이라고 할 수 있고, class는 인스턴스를 생성하는 클래스명이다.(패키지명까지 꼭 작성) -->
	<bean id="userBean" class="kr.or.connect.diexam01.UserBean"/>
	
	<bean id="engine" class="kr.or.connect.diexam01.Engine" />
	<bean id="car" class="kr.or.connect.diexam01.Car">
      <!-- Setter에 engine이라는 id를 가진 Bean을 주입하는 코드 -->
		<property name="engine" ref="engine"></property>
	</bean>
	
</beans>
```

**ApplicationContextExam02.java**

```java
package kr.or.connect.diexam01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextExam02 {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		
		Car car = (Car) ctx.getBean("car");
		car.run();
	}
}
```

### DI 장점

- Bean을 주입받아 사용하는 코드를 바꾸지 않아도 내부의 합성을 변경할 수 있다.

### 참고 자료

1. Spring컨테이너가 관리하는 객체를 빈(Bean)이라고 말합니다. (여러분들이 직접 new연산자로 생성해서 사용하는 객체는 빈(Bean)이라고 말하지 않습니다.) Spring은 빈을 생성할 때 기본적으로 싱글톤(Singleton)객체로 생성합니다. 싱글톤이란 메모리에 하나만 생성한다는 것입니다. 메모리에 하나만 생성되었을 경우, 해당 객체를 동시에 이용한다면 어떤 문제가 발생할 수 있을까요? 이런 문제를 해결하려면 어떻게 해야할까요?  ( 참고로 Spring에서 빈을 생성할 때 스코프(scope)를 줄 수 있습니다. 스코프를 줌으로써 기본으로 설정된 싱글톤 외에도 다른 방법으로 객체를 생성할 수 있습니다. )
   - Singleton Scope Bean을 여러 스레드에서 동시에 사용한다면 스레드간에 동시성 문제가 발생할 수 있습니다. 이를 해결하기 위한 방법으로는 synchronized() 라고 하는 블록으로 임계 구역을 감싸서 동시성 문제를 해결하는 방법이 있습니다. 

---

## 4) Java Config를 이용한 설정

**Java Annotation은 Java SE 5 부터 지원이 되었다.** 스프링은 설정을 위해 다양한 어노테이션을 지원해준다.

### 실습

**ApplicationConfig.java**

```java
package kr.or.connect.diexam01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//설정 파일임을 알려주는 애노테이션
@Configuration
public class ApplicationConfig {

	// 빈 등록을 위한 애플리케이션
	@Bean
	public Car car(Engine engine) {
		Car car = new Car();
		car.setEngine(engine);
		return car;
	}
	
	@Bean
	public Engine engine() {
		return new Engine();
	}
	
}

```

**ApplicationContextExam03.java**

```java
package kr.or.connect.diexam01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextExam03 {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		Car car = (Car) ctx.getBean("car"); 
      car.run();
      
      // Bean Id 뿐만 아니라 Class를 기반으로도 검색할 수 있다.
      // Car car = (Car) ctx.getBean(Car.class);
	}

}
```

### 실습 2 (Component Scan을 이용한 Config)

**ApplicationConfig2.java**

```java
package kr.or.connect.diexam01;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("kr.or.connect.diesxam01")
public class ApplicationConfig2 {

}
```

**Car.java**

```java
package kr.or.connect.diexam01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {
	
	@Autowired
	private Engine v8;
	
	public Car() {
		System.out.println("Car 생성 완료");
	}
	
	public void run() {
		System.out.println("엔진을 이용하여 달립니다.");
		v8.exec();
	}
	
}
```

**Engine.java**

```java
package kr.or.connect.diexam01;

import org.springframework.stereotype.Component;

@Component
public class Engine {
	public Engine() {
		System.out.println("Engine 생성자");
	}
	
	public void exec() {
		System.out.println("엔진이 동작합니다.");
	}
}
```

**ApplicationContextExam04.java** 

```java
package kr.or.connect.diexam01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextExam04 {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig2.class);
		
		Car car = ctx.getBean(Car.class);
		car.run();

	}

}
```

### 생각해보기

- **다루는 빈(Bean)이 많아질수록 xml로 설정하는 것과 @ComponentScan, @Component, @Autowired를 이용하는 것 중 어떤 것이 유지보수에 좋을 것 같습니까?**

  - 제 생각에는 다루는 Bean 이 많아질 수록 ComponentScan이 유지보수에 좋을 것 같습니다. 하지만, 자동으로 해주는 Bean 설정인 만큼 시스템 복잡도가 높아지는 경우 Bean 설정 관리에 많은 신경을 써야 합니다.

    각 클래스에 Bean 설정을 나눔으로써 좋은 장점이 있겠지만, 추후에 수정할 경우 Bean 설정들이 한 파일에서 관리되지 못하고 여러 파일에서 관리되는 것이 추후 변경에 안좋은 점을 발생시킬 수도 있겠다는 생각이 드네요.

- @AutoWired 는 Field, Constructor, Setter Method 에 사용할 수 있습니다. 각각의 방식에 장단점에 대해서 더 생각해보세요.

  - Field로써 Bean을 주입 받는다면 특정 Bean을 테스트할 때 Stub으로 주입될 Bean들을 만들어 주입시키기 어려워 테스트시 불편함을 일으킬 수 있다.
  - Constructor Bean은 생성자를 사용해서 Bean이 주입되기 때문에 해당 클래스 테스트 시 Stub Bean을 만들어서 테스트 할 수 있다.
  - Setter Method는 Setter를 사용해서 Bean이 주입되기 때문에 테스트시에도 편리하고, 다 좋지만 setter 메소드가 캡슐화를 방해해 추후 객체지향 프로그래밍을 방해할 수 있다.

  





























