## 2. Test 개요

### 테스팅이란 무엇인가?

- **'테스팅'**이란 응용 프로그램 또는 시스템(구성요소 포함)의 동작과 성능, 안정성이 요구하는 수준을 만족하는지 확인하기 위해 결함을 발견하는 과정이라고 말할 수 있습니다.
- 전통적인 테스팅 개념은 응용 프로그램 또는 시스템이 잘 작동하는지 확인하는 것입니다. 
- 현재의 테스팅 개념은 사용자의 기대 수준과 요구 사항에 맞게 구현되고 동작하는지를 확인하고 이를 통해 결함을 발견하고, 최종적으로 결함 데이터를 근간으로 개발 프로젝트의 리스크(Risk)에 대한 수치적인 판단 근거를 의사 결정권자(프로젝트 관리자 등)에게 전달하는 것을 말합니다.
- 개발 프로젝트 초기에 개발 중간 산출물(Work products)을 테스팅 관점에서 리뷰(Review)하고, 테스트 케이스를 미리 만드는 과정에서 결함을 발견하는 작업(결함 예방 활동)도 테스팅 활동의 중요한 부분이라고 말할 수 있습니다.
- 프로그램을 개발하기 전에 요구사항 등을 리뷰하는 것을 **정적 테스트**라고 하고, 프로그램 개발 이후에 실제 실행하면서 테스트하는 것을 **동적 테스트**라고 합니다.

### 소프트웨어에서 테스트가 필요한 이유

소프트웨어가 올바르게 동작하지 않는 경우, 다음과 같은 문제가 발생할 수 있습니다.

1.  금전적인 손실
2. 시간 낭비
3. 비즈니스의 이미지 손상
4. 부상이나 사망

따라서, 테스팅은 이러한 소프트웨어 시스템의 문제를 최소화하기 위해 필요합니다.

### 소프트웨어 결함의 원인

소프트웨어가 결함이 발생하는 이유는 무엇일까요?

개발자가 잘못 작성한 오류로 인하여 결함(Defects 또는 Bug)이 발생합니다.

결함이 있는 소프트웨어를 실행하게 되면 장애(Failure)가 발생하여 의도한대로 소프트웨어가 동작하지 않거나 또는 소프트웨어가 동작하지 않아야 하는 상황에서 동작하는 문제가 발생할 수 있습니다.

단, 모든 결함의 원인이 인간이 범하는 오류 떄문 만은 아닙니다.

인간이 오류를 범하기 쉽기 때문에 발생하는 결함도 있지만 시간적인 압박, 복잡한 코드, 기반환경의 복잡성, 기술이나 시스템의 변경, 그리고 수많은 시스템 상호간의 연동 등의 이유로 발생할 수도 있습니다.

### 소프트웨어 개발, 유지보수, 운영 시 테스팅의 역할

소프트웨어는 개발이 완료되고 실제 환경에 배포를 해야 운영됩니다.

운영되는 도중에도 해당 소프트웨어를 더 이상 사용하지 않을 때까지 계속해서 유지보수를 하게 됩니다. 

테스팅은 개발 시에만 필요한 것이 아니라 개발, 유지보수, 운영 시에 모두 필요합니다.

1. 테스팅을 통해 릴리즈 전에 발견되지 않은 결함들이 수정된다면,운영 환경 내에서 발생하는 리스크(risk)를 줄이는데 기여할 수 있으며 소프트웨어 품질에 도움을 줍니다.
2. 테스팅은 개발 초기의 요구사항 분석 단계부터 리뷰 및 인스펙션을 통해 정적으로 이뤄질 수 있으며 각각의 개발 단계에 대응하는 테스트 레벨(test level)에 따라 테스팅이 이뤄집니다.
3. 기존에 운영되는 소프트웨어 시스템이 유지 보수 활동으로 변경 및 단종되거나 환경이 변하는 경우, 변경된 소프트웨어에 대한 테스팅과 변경된 환경에서의 운영 테스팅이 요구됩니다.
4. 소프트웨어 테스팅은 계약상(법적) 요구조건들, 또는 산업에 특화된 표준들을 만족시키기 위해서 필요합니다.

### 테스팅과 품질

테스팅으로 발견된 결함이 소수이거나 없을 경우에 소프트웨어의 품질에 대한 확신(Confidence)를 가지게 됩니다.

잘 설계된 테스트는 시스템의 전반적인 리스크를 감소시키고 결함을 발견합니다.

발견된 결함이 수정될 때 소프트웨어 시스템의 품질 증가됩니다.



품질을 높이기 위해서는 이전 프로젝트를 통해 많은 테스팅 경험과 정보를 확보할 필요성이 있습니다. 

다른 프로젝트에서 발견된 결함의 근본적인 원인에 대한 이해함으로써 프로세스를 개선할 수 있으며, 그러한 결함의 재발을 방지함으로써, 결과적으로, 차후 시스템의 품질을 개선할 수 있게 됩니다.



개발 표준이나 교육 훈련 그리고 결함 분석 등과 함께 테스팅은 품질 보증 활동의 하나로 테스팅을 통해 소프트웨어 시스템의 품질을 확보할 수 있습니다.

### 테스팅은 얼마나 해야 충분한가?

어느 정도 테스팅 하는 것이 적절한지를 파악하기 위해서는 다음과 같은 리스크(Risk) 수준과 프로젝트 제약사항을 고려해야합니다.

1. 기술적인 내용
2. 비즈니스 제품
3. 프로젝트 리스크
4. 시간과 비용 

테스팅은 개발 프로젝트 관련자들이 테스트된 소프트웨어나 시스템의 다음 개발 단계로의 릴리즈(Release)에 대한 결정 또는 고객에게 이양(Handover)하는 릴리즈에 대한 결정을 내릴 수 있도록 충분한 정보를 제공해야 합니다.

### 테스팅의 일반적인 원리

1. **테스팅은 결함이 존재함을 밝히는 활동이다.**
   - 테스팅은 결함이 존재함을 드러내지만, 결함이 없다는 것을 증명할 수 없습니다. **즉, 프로그램이 완벽하다고 증명할 수 없습니다.** 이는 테스트 한 부분까지만 잘 동작한다고 말할 수 있고 테스트를 하지 않는 부분은 결함이 있는지 없는지에 대해서 예측할 수 없다는 의미입니다.
2. **완벽한 테스팅(Exhaustive testing)은 불가능하다.**
   - 모든 가능성(입력과 사건 조건의 모든 조합)을 테스팅하는 것은 지극히 간단한 소프트웨어를 제외하고 가능하지 않습니다. 완벽한 테스팅 대신, 리스크 분석과 결정된 우선순위에 따라 테스팅 활동 노력을 집중시켜야 합니다.
3. 테스팅을 개발 초기에 시작한다.
   - 테스팅 활동은 소프트웨어나 시스템 개발 수명주기에서 가능한 초기에 시작되어야 하며, 설정한 테스팅 목표에 집중해야 합니다. 개발 초기에 테스팅을 시작한다는 것의 의미는 개발의 시작과 동시에 테스트를 계획하고 전략적으로 접근하는 것을 고려하는 것은 물론, 요구사항 부석서와 설계서 등의 개발 중간 산출물을 분석하여 테스트하는 것을 의미합니다.

**생각해보기**

1. **테스팅이 중요하다보니 TDD같은 방법론도 존재합니다. TDD가 무엇인지 알아보세요.**

   - TDD는 먼저 테스트 코드를 작성하고 그 다음에 프로덕션 코드를 작성하는 개발 방법론인 테스트 주도 개발의 약자입니다.

     객체지향에서 TDD를 사용하는 이유는 매우 다양하지만 제가 가장 중요하다고 생각하는 부분은 좋은 객체 지향 설계를 갖는 시스템을 구축하기 위해서 사용한다고 생각합니다.

     객체지향에서 테스트는 객체의 구현을 테스트하기 보다는 외부에서 접근할 수 있는 객체의 인터페이스를 테스트 하는 경우가 대부분 입니다. 프로덕션 코드를 먼저 작성하는 개발 방법론은 객체의 인터페이스 보다는 내부 구현에 집중하여 개발하는 경우가 많은데 내부 구현에 집중하면서 개발하다 보면 객체 지향의 목적인 응집도 높고 결합도가 낮은 코드와 멀어지는 경우가 많이 생깁니다. 

     TDD를 적용해 테스트를 먼저 개발함으로써 개발자는 자연스레 객체의 내부 구현 보다 객체의 인터페이스를 먼저 생각하게 되고 이는 좋은 객체 지향 설계를 가져갈 수 있는 첫 걸음이 됩니다. 즉, 응집도가 높고 결합도가 낮은 코드를 개발할 확률이 더 높아진다는 이야기 입니다.

---

## 2) JUnit

### JUnit

프로그래밍 언어마다 테스트를 위한 프레임워크가 존재합니다. 이러한 도구들은 보통 xUnit이라고 말하며, 자바 언어의 경우는 JUnit이라고 말합니다.

### JUnit 사용하기

**JUnit을 사용하려면 JUnit 라이브러리가 클래스패스(CLASSPATH)에 존재해야 합니다.** 직접 다운로드를 받는 것은 번거롭기 때문에 보통 빌드 도구인 Maven이나 Gradle을 이용해 다운로드 받아 사용합니다. Maven을 사용할 경우 **pom.xml**에 다음을 추가합니다.

```xml
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>버전</version>
  <scope>test</scope>
</dependency> 
```

**scope가 test인 이유는 해당 라이브러리가 테스트 시에만 사용된다는 뜻입니다.** 테스트가 아닌 상황에선 해당 라이브러리가 사용되지 않습니다.

### JUnit 어노테이션

![image-20210104162623504](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210104162623504.png)

### **JUnit 어노테이션 기반의 실행 순서**

JUnit은 테스트 프레임워크로써 개발자가 직접 흐름을 조정하기 보다는 JUnit 프레임워크에 흐름이 구현되어 있다. 이 흐름의 순서는 아래와 같다.

![image-20210104162716149](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210104162716149.png)

1. 첫 번째 테스트 메소드가 실행되기 전에 @BeforeClass가 붙은 메소드가 실행된다.
2. 테스트 메소드가 실행되기 전에 @Before가 붙은 메소드가 실행된다.
3. @Test가 붙은 메소드가 실행된다.
4. @After가 붙은 메소드가 실행된다.
5. 모든 테스트 메소드가 실행된 후 @AfterClass 가 붙은 메소드가 실행된다.

### JUnit의 중요 assert

**JUnit의 Assert 클래스는 다양한 assert 메소드를 가집니다. 그 중에서 자주 사용하는 메소드는 아래와 같습니다.**

![image-20210104163039314](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210104163039314.png)

### **생각해보기**

스프링 프레임워크를 사용하면 객체의 생성을 컨테이너가 한다고 했습니다. 그렇다면, 컨테이너가 관리하는 객체(Bean)은 어떻게 테스트를 수행하면 될까요?

- 첫 번째로 스프링 설정을 기반으로 직접 가상의 컨테이너를 띄워서 테스트 할 Bean을 가져와 테스트를 수행하는 방법이 있을 것 같구요, 두 번째로 테스트 할 객체의 생성을 컨테이너에게 위임하지 말고 직접 해당 클래스의 인스턴스를 생성해서 테스트를 수행하는 방법이 있을 것 같습니다. 

---

## 3) 스프링 테스트 애노테이션 사용하기

**일반 자바 객체를 Spring Bean으로 만들고 이를 테스트 하는 실습**

**pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.edwith.webbe</groupId>
    <artifactId>calculatorcli</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <failOnMissingWebXml>false</failOnMissingWebXml>
        <spring.version>5.2.3.RELEASE</spring.version>
    </properties>

    <dependencies>
        <!-- junit 4.12 라이브러리를 추가합니다. -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

        <!-- spring-context와 spring-test를 의존성에 추가합니다.-->
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

    </dependencies>

    <!-- 사용할 JDK버전을 입력합니다. JDK 11을 사용할 경우에는 1.8대신에 11로 수정합니다.--><!-- 사용할 JDK버전을 입력합니다. JDK 11을 사용할 경우에는 1.8대신에 11로 수정합니다.-->
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
package org.edwith.webbe.calculatorcli;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.edwith.webbe.calculatorcli"})
public class ApplicationConfig {
}
```

**CalculatorService.java**

```java
package kr.or.connect.calculatorcli;

import org.springframework.stereotype.Component;

@Component
public class CalculatorService {
    public int plus(int value1, int value2) {
        return value1 + value2;
    }

    public int minus(int value1, int value2) {
        return value1 - value2;
    }

    public int multiple(int value1, int value2) {
        return value1 * value2;
    }

    public int divide(int value1, int value2) throws ArithmeticException {
        return value1 / value2;
    }

}
```

**Main.java**

```java
package kr.or.connect.calculatorcli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		CalculatorService calculatorService = ctx.getBean(CalculatorService.class);
		
		System.out.println(calculatorService.plus(10, 50));
	}

}

```

### 테스트 클래스를 스프링 빈 컨테이너를 사용하도록 수정하기

**CalculatorServiceTest.java**

```java
package kr.or.connect.calculatorcli;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class CalculatorServiceTest {
    @Autowired
    CalculatorService calculatorService;

    @Test
    public void plus() throws Exception{
        // given
        int value1 = 10;
        int value2 = 5;

        // when
        int result = calculatorService.plus(value1, value2);

        // then
        Assert.assertEquals(result, 15); // 결과와 15가 같을 경우에만 성공
    }

    @Test
    public void divide() throws Exception{
        // given
        int value1 = 10;
        int value2 = 5;

        // when
        int result = calculatorService.divide(value1, value2);

        // then
        Assert.assertEquals(result, 2); // 결과와 15가 같을 경우에만 성공
    }

    @Test
    public void divideExceptionTest() throws Exception{
        // given
        int value1 = 10;
        int value2 = 0;

        try {
            calculatorService.divide(value1, value2);
        }catch (ArithmeticException ae){
            Assert.assertTrue(true); // 이부분이 실행되었다면 성공
            return; // 메소드를 더이상 실행하지 않는다.
        }
        
        Assert.assertTrue(false); // 이부분이 실행되면 무조건 실패다.
    }
}
```

- **@RunWith(SpringJUnit4ClassRunner.class)**
  - 스프링에서 JUnit을 확장하도록 SpringJUnit4ClassRunner.class를 제공한다. **해당 코드는 JUnit이 테스트 코드를 실행할 때 스프링 빈 컨테이너가 내부적으로 생성되도록 합니다.**
- **@ContextConfiguration(classes = {ApplicationConfig.class})**
  - **내부적으로 생성된 스프링 빈 컨테이너가 사용할 설정파일을 지정할 때 사용합니다.**

**생각해보기**

1. 여러 패키지에 걸쳐서 빈(Bean)클래스들이 있고, 특정 패키지에 있는 클래스만 테스트 하려면 어떻게 해야할까요? 참고로 테스트 소스코드가 있는 곳에도 스프링 설정 파일이 있을 수 있습니다.
   - @ContextConfiguration(classes = {testingClass.**class**})와 같이 JUnit의 @ContextConfiguration 애노테이션을 사용해서 특정 패키지에 있는 클래스만 Spring Bean Container에 로드시키고 이를 불러와 테스트하면 됩니다.

---

## 4) 로직 단위테스트 구현하기

### 통합 테스트와 단위 테스트의 관계

- **빈들 간에는 다양한 관계를 맺고 있는 경우가 많습니다. 즉, 하나의 빈을 사용한다는 것은 관계된 빈들도 함께 동작한다는 것을 의미합니다.** 

- **하나의 빈을 테스트할 때 관련된 빈들이 모두 잘 동작하는지 테스트하는 것을 우리는 통합 테스트(integration test)라 합니다.** 

- **관계된 다른 클래스와는 상관 없이 특정 빈이 가지고 있는 기능만 잘 동작하는지 확인하는 것을 우리는 단위 테스트(unit test)라 합니다.**

### 실습

**MyService.java**

```java
package kr.or.connect.calculatorcli;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    private final CalculatorService calculatorService;

    public MyService(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public int execute(int value1, int value2){
        return calculatorService.plus(value1, value2) * 2;
    }
}
```

**MyServiceTest.java**

```java
package org.edwith.webbe.calculatorcli;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class MyServiceTest {
    @Autowired
    MyService myService;

    @Test
    public void execute() throws Exception{
        // given
        int value1 = 5;
        int value2 = 10;

        // when
        int result = myService.execute(value1, value2);

        // then
        Assert.assertEquals(30, result);
    }
}
```

위의 테스트는 정상적으로 성공합니다. 그런데 CalculatorService의 plus() 메소드에 버그가 있다면 어떻게 될까요?? 즉, MyService의 문제가 아닌데도 위 테스트는 실패할 것입니다.

**즉, 전적으로 하나의 모듈(클래스)만 테스트하기 위해서 Mock 객체를 이용하는 방법이 있습니다.**

MyService가 사용하던 CalculatorService를 사용하는 대신, 가짜 객체를 하나 생성하도록 하는 것입니다. 내가 원하는 동작을 하는 Mock객체로 CalculatorService를 사용함으로써 MyService의 내용만 테스트를 수행할 수 있습니다.

### Mock 객체를 사용해서 단위 테스트 진행하기 실습

**pom.xml에 추가해야하는 라이브러리 의존성**

```xml
<!-- test mock을 위한 라이브러리를 추가합니다. -->
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>1.9.5</version>
            <scope>test</scope>
        </dependency>
```

**mockito 라이브러리란 하나의 프레임워크로써 테스트를 위해 가짜 객체를 쉽게 만들어줄 수 있는 프레임워크 입니다.**

**MyServiceTest.java**

```java
package kr.or.connect.calculatorcli;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MyServiceTest {
	
    @InjectMocks
    MyService myService;
    
    @Mock
    CalculatorService calculatorService;

    @Test
    public void execute() throws Exception{
        // given
        int value1 = 5;
        int value2 = 10;
        given(calculatorService.plus(5, 10)).willReturn(15);
        //when

        // when
        int result = myService.execute(value1, value2);

        // then
        verify(calculatorService).plus(anyInt(), anyInt());
        Assert.assertEquals(30, result);
    }
}

```

- **@RunWith(MockitoJUnitRunner.class)**
  - mokito가 제공하는 JUnit 확장 클래스 MockitoJunitRunner를 이용해 테스트 클래스를 실행하도록 한다.
- **@Mock**
  **CalculatorService calculatorService;**
  - CalculatorService 클래스의 인스턴스를 Mock 객체로 생성한다.
- **@InjectMocks**
  **MyService myService;**
  - @InjectMocks 애노테이션이 붙은 객체를 생성할 때 Mock 객체를 생성하여 초기화하라는 의미이다.
- **given(calculatorService.plus(5,10)).willReturn(15);**
  - Mock객체의 리턴값을 정의하는 static 메소드이다.
- **verify(calculatorService).plus(anyInt(), anyInt());**
  - calculatorService의 plus 메소드가 호출된 적이 있는지를 검증한다.

### **생각해보기**

- 단위 테스트, 통합 테스트, 인수 테스트에 대해 조사해보고 각각 어떤 차이점이 있는지 정리해보세요.
  - 단위 테스트란 하나의 단위를 테스트 하는 것으로 이 단위는 정하기 나름이지만 대부분 하나의 모듈을 의미한다. 자바에서 이 모듈은 클래스(객체)가 일반적이다.
  - 통합 테스트란 여러 모듈간의 상호작용 테스트 하는 것으로 자바에서 객체간의 상호작용의 결과를 테스트하는 것이 일반적이다.
  - 인수 테스트란 사용자의 관점에서 특정 서비스가 요구 사항을 충족시키는지 평가하는 테스트로 알파 테스트 및 베타 테스트로 나뉘기도 한다.

















































