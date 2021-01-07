## 1) Maven 이란?

### Maven이란?

Maven은 지금까지 애플리케이션을 개발하기 위해 반복적으로 진행해왔던 작업들을 지원하기 위하여 등장한 도구입니다. 

**Maven을 사용하면 빌드(Build), 패키징, 문서화, 테스트와 테스트 리포팅, git, 의존성관리, svn등과 같은 형상관리서버와 연동(SCMs), 배포 등의 작업을 손쉽게 할 수 있습니다.**

Maven을 이해하려면 CoC(Convention over Configuration)라는 단어를 먼저 이해해야 합니다.

CoC란 일종의 관습을 말하는데, 예를 들자면 프로그램의 소스파일은 어떤 위치에 있어야 하고, 소스가 컴파일된 파일들은 어떤 위치에 있어야 하고 등을 미리 정해놨다는 것입니다.

이 말은 관습에 이미 익숙한 사용자는 쉽게 Maven을 사용할 수 있는데, 관습에 익숙하지 않은 사용자는 이러한 제약사항에 대해서 심한 거부감을 느낄 수 있습니다.

Maven을 사용한다는 것은 어쩌면 이러한 관습 즉 CoC에 대해서 알아나가는 것이라고도 말할 수 있습니다. 

### **Maven을 사용할 경우 얻게 되는 이점은?**

Maven을 사용할 경우, 굉장히 편리한 점들이 많습니다.

**많은 사람이 손꼽는 장점 중에는 편리한 의존성 라이브러리 관리가 있습니다.**

앞에서 JSTL을 학습할 때, 몇 가지 파일을 다운로드 하여 /WEB-INF/lib폴더에 복사하여 사용했었습니다.

관련된 라이브러리가 많아질수록 이러한 방식은 상당히 불편해집니다.

Maven을 사용하면 설정 파일에 몇 줄 적어줌으로써 직접 다운로드 받거나 하는 것을 하지 않아도 라이브러리를 사용할 수 있습니다.

프로젝트에 참여하는 개발자가 많아지게 되면, 프로젝트를 빌드하는 방법에 대하여 가이드하는 것도 쉬운 일이 아닙니다.

**Maven을 사용하게 되면 Maven에 설정한 대로 모든 개발자가 일관된 방식으로 빌드를 수행할 수 있게 됩니다.**

**Maven은 또한 다양한 플러그인을 제공해줘서, 굉장히 많은 일들을 자동화시킬 수 있습니다.**

### Maven 기본

Archetype을 이용하여 Maven 기반 프로젝트를 생성할 경우 생성된 프로젝트 하위에 pom.xml 파일이 생성됩니다.

pom.xml 파일을 살펴보면 다음과 같습니다. 

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>kr.or.connect</groupId>
    <artifactId>examples</artifactId>
    <packaging>jar</packaging>
    <version>1.0-SNAPSHOT</version>
    <name>mysample</name>
    <url>http://maven.apache.org</url>
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

**각각의 태그의 의미는 다음과 같습니다.**

- **project** : pom.xml 파일의 최상위 루트 엘리먼트(Root Element)입니다.
- **modelVersion** : POM model의 버전입니다. 
- **groupId** : 프로젝트를 생성하는 조직의 고유 아이디를 결정합니다. <u>일반적으로 도메인 이름을 거꾸로 적습니다.</u>
- **artifactId** : 해당 프로젝트에 의하여 생성되는 artifact의 고유 아이디를 결정합니다. Maven을 이용하여 pom.xml을 빌드할 경우 다음과 같은 규칙으로 artifact가 생성됩니다. artifactid-version.packaging. 위 예의 경우 빌드할 경우 examples-1.0-SNAPSHOT.jar 파일이 생성됩니다.
- **packaging** : 해당 프로젝트를 어떤 형태로 packaging 할 것인지 결정합니다. jar, war, ear 등이 해당됩니다.
- **version** : 프로젝트의 현재 버전. 추후 살펴보겠지만 프로젝트가 개발 중일 때는 SNAPSHOT을 접미사로 사용합니다. Maven의 버전 관리 기능은 라이브러리 관리를 편하게 합니다.
- **name** : 프로젝트의 이름입니다.
- **url** : 프로젝트 사이트가 있다면 사이트 URL을 등록하는 것이 가능합니다.

Maven 을 이용할 경우 얻게 되는 큰 이점 중의 하나는 Dependency Management 기능입니다.

위 pom.xml 파일에서 `<dependencies/>` 엘리먼트가 Dependency Management 기능의 핵심이라고 할 수 있습니다.

해당 엘리먼트 안에 필요한 라이브러리를 지정하게 됩니다.

---

## 2) Maven을 이용한 웹 어플리케이션 실습

### Archetype

아키타입(Archetype)을 선택합니다. 아키타입이란 일종의 프로젝트 템플릿(Template)이라고 말할 수 있습니다.
어떤 아키타입을 선택했느냐에 따라서 자동으로, 여러 가지 파일들을 생성하거나 라이브러리를 셋팅해주거나 등의 일을 해줍니다.

### Tomcat 설정

index.jsp를 보면 HttpServlet을 찾을 수 없다는 오류 메시지가 보인다.

**WAS Runtime 설정을 하면서 Tomcat을 지정하게 된다면, Tomcat안에 있는 서블릿 라이브러리가 사용되면서 문제가 없게 된다.**

**실행시에도 WAS 위에서 실행되기 때문에 WAS의 서블릿 라이브러리를 사용하게 된다.**

Maven 프로젝트로 생성했을 경우에는 WAS 런타임을 지정하지 않았기 때문에 서블릿 라이브러리를 찾을 수 없어dependencies 엘리먼트 아래에 다음을 추가합니다.

```xml
<dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
</dependency>
```

**위의 코드를 보면 서블릿 라이브러리의 scope를 볼 수 있는데 provided는 컴파일 시에만 사용되고 배포 시에는 사용되지 않는다는 것을 의미합니다.**

**서블릿 scope는 다음과 같은 4가지가 존재합니다.**

- **compile** : 컴파일 할 때 필요. 테스트 및 런타임에도 클래스 패스에 포함됩니다. scope 을 설정하지 않는 경우 기본값입니다.
- **runtime** : 런타임에 필요. JDBC 드라이버 등이 예가 됩니다. 컴파일 시에는 필요하지 않지만, 실행 시에 필요한 경우입니다.
- **provided** : 컴파일 시에 필요하지만, 실제 런타임 때에는 컨테이너 같은 것에서 제공되는 모듈. servlet, jsp api 등이 이에 해당. 배포 시 제외됩니다. 
- **test** : 테스트 코드를 컴파일 할 때 필요. 테스트 시 클래스 패스에 포함되며, 배포 시 제외됩니다.

<u>다시 index.jsp를 보면 HttpServlet을 찾을 수 없다는 오류 메시지가 없어진 것을 볼 수 있다.</u>

### 이슈: Eclipse 내장 WAS인 Jetty가 JSP를 지원하지 않앗다.

1. **index.jsp가 보이지 않고 왜 다음과 같은 에러만 보일까요..? 아시는 분 있다면 공유 부탁드립니다~**

   **No JSP support. Check that JSP jars are in lib/jsp and that the JSP option has been specified to start.jar**

   - 일단 아래의 문제가 발생한 이유는 JSP를 지원해주지 않아서 인 것 같다. 내가 사용하는 이클립스는 Default WAS 로써 Jetty를 사용하는데, 어떤 강의에도 강사님처럼 Tomcat을 설치해서 사용하라는 말이 없어서 나는 Eclipse Default WAS를 사용했지만, JSP를 제공해주지 않아 애먹었다.

      

     Eclipse 내장 Jetty가 JSP를 지원하기 위해서는 JSP 관련 라이브러리 의존성을 가져와야 했고 추가적으로 WebAppContext 경로 설정 또한 해줘야만 했다. 

     관련 링크 : https://stackoverflow.com/questions/4235082/configuring-jetty-jsp-support-in-embedded-mode-in-maven-project

      

     추가적으로 강사님처럼, Tomcat v8.5로 위의 프로젝트를 실행하니 정상적으로 jsp 기능을 잘 지원해주었다.

      

     다른사람이 혹여 나처럼 Tomcat을 사용하지 않고 Eclipse의 내장 WAS로써 위 실습을 진행해 많은 시간을 소모하지 않길 바라면서.. 글을 남긴다. (하지만, 절대 가치없는 시간은 아니고 매우 값진 시간이었다.)

### JSTL 사용

**자바서버 페이지 표준 태그 라이브러리**(JavaServer Pages Standard Tag Library, 약칭 **JSTL**)은 [Java EE](https://ko.wikipedia.org/wiki/Java_EE) 기반의 [웹 애플리케이션](https://ko.wikipedia.org/wiki/웹_애플리케이션) 개발 플랫폼을 위한 컴포넌트 모음이다. JSTL은 [XML](https://ko.wikipedia.org/wiki/XML) 데이터 처리와 [조건문](https://ko.wikipedia.org/wiki/조건문), 반복문, [국제화와 지역화](https://ko.wikipedia.org/wiki/국제화와_지역화)와 같은 일을 처리하기 위한 JSP [태그 라이브러리](https://ko.wikipedia.org/w/index.php?title=태그_라이브러리&action=edit&redlink=1)를 추가하여 [JSP](https://ko.wikipedia.org/wiki/자바서버_페이지) 사양을 확장했다. JSTL은 JSR 52로서 [JCP](https://ko.wikipedia.org/wiki/자바_커뮤니티_프로세스) 하에서 개발되었으며, 2006년 5월 8일에 JSTL 1.2가 출시되었다.

JSTL은 JSP 페이지 내에서 [자바](https://ko.wikipedia.org/wiki/자바_(프로그래밍_언어)) 코드를 바로 사용하지 않고 로직을 내장하는 효율적인 방법을 제공한다. 표준화된 태그 셋을 사용하여 자바 코드가 들락거리는 것보다 더 코드의 유지보수와 [응용 소프트웨어](https://ko.wikipedia.org/wiki/응용_소프트웨어) 코드와 [사용자 인터페이스](https://ko.wikipedia.org/wiki/사용자_인터페이스) 간의 [관심사의 분리](https://ko.wikipedia.org/wiki/관심사의_분리)로 이어지게 한다.

### JSTL 설정 방법

1. JSTML을 설정하기 위해서는 다음과 같은 프로젝트 의존성을 가져와야 한다.

   ```xml
   <dependency>
           <groupId>javax.servlet</groupId>
           <artifactId>jstl</artifactId>
           <version>1.2</version>
   </dependency>
   ```

2. 다이나믹 웹 모듈의 2.4부터 EL이 기본으로 사용할 수 있도록 설정되기 때문에 2.3일 경우에는 EL표기법의 결과가 출력되지 않습니다. 앞의 프로젝트처럼 다이나믹 웹 모듈 3.1이 되도록 설정한다.

3. web.xml 파일을 아래와 같이 수정한다.

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
            xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" version="3.1">
     <display-name>Archetype Created Web Application</display-name>
   </web-app>
   ```

4. 톰캣으로 실행해 본다면 이제 잘 보이는 것을 확인할 수 있다.

























