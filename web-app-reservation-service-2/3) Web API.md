## 1) Rest API란?

2000년도에 로이 필딩(Roy Fielding) 박사의 학위 논문에 REST라는 개념이 처음 등장하였습니다. 

**REST는 ‘Representational State Transfer’의 약자로 <u>자원을 이름으로 구분하여 해당 자원의 상태(정보)를 주고 받는 모든 것</u>을 의미합니다.** 

로이 필딩의 논문에는 먼저 소프트웨어 아키텍처 스타일(architectural style)에 대해 설명합니다. 

아키텍처 스타일이란 아키텍처가 지켜야 하는 제약 조건들의 집합이라고 말할 수 있습니다.

**로이 필딩은 웹 아키텍처의 요구사항과 해결해야할 문제를 설명하고 이를 해결하기 위한 접근 방법을 논문에서 제시하면서, 이러한 문제를 해결하기 위한 아키텍처 스타일인 REST를 소개합니다.** 

**REST는 기본적으로 웹의 기존 기술과 HTTP프로토콜을 그대로 활용하기 때문에 웹의 장점을 최대한 활용할 수 있는 아키텍처 스타일이라고 말할 수 있습니다.**

이번 학습에서는 로이필딩이 말한 REST에 대해 알아보도록 하겠습니다.

### **1. REST 구성**

REST API는 다음과 같은 3가지 부분으로 구성됩니다. 

- **자원(Resource) : 자원은 Data, Meta Data, HATEOAS로 나뉩니다.**

- **행위(Verb) : HTTP Method로 표현됩니다.**

- **표현(Representations)**



### **2. REST의 특징**

1. **Uniform Interface(유니폼 인터페이스)**

   구성 요소(클라이언트, 서버 등) 사이의 인터페이스는 균일(uniform)해야합니다.

   인터페이스를 일반화함으로써, 전체 시스템 아키텍처가 단순해지고, 상호 작용의 가시성이 개선되며, 구현과 서비스가 분리되므로 독립적인 진화가 가능해질 수 있습니다.

2. **Stateless (무상태성)**

   클라이언트와 서버의 통신에는 상태가 없어야합니다. 모든 요청은 필요한 모든 정보를 담고 있어야합니다.

   요청 하나만 봐도 바로 뭔지 알 수 있으므로 가시성이 개선되고, task 실패시 복원이 쉬우므로 신뢰성이 개선되며, 상태를 저장할 필요가 없으므로 규모 확장성이 개선될 수 있습니다.

3. **Cacheable (캐시 가능)**

   캐시가 가능해야합니다. 즉, 모든 서버 응답은 캐시가 가능한지 그렇지 아닌지 알 수 있어야합니다. 

   효율, 규모 확장성, 사용자 입장에서의 성능이 개선됩니다.

4. **Self-descriptiveness (자체 표현 구조)**

   REST의 또 다른 큰 특징 중 하나는 REST API 메시지만 보고도 이를 쉽게 이해 할 수 있는 자체 표현 구조로 되어 있다는 것입니다.

5. **Client - Server 구조**

   클라이언트-서버 스타일은 사용자 인터페이스에 대한 관심(concern)을 데이터 저장에 대한 관심으로부터 분리함으로써클라이언트의 이식성과 서버의 규모확장성을 개선할 수 있습니다.

6. **Layered System(계층형 구조)**

   REST 서버는 다중 계층으로 구성될 수 있으며 보안, 로드 밸런싱, 암호화 계층을 추가해 구조상의 유연성을 둘 수 있고 PROXY, 게이트웨이 같은 네트워크 기반의 중간매체를 사용할 수 있게 합니다.

### 

### 3. Rest API 설계 가이드

1. **URI는 정보의 자원을 표현해야 합니다.**

   - resource는 동사보다는 명사를, 대문자보다는 소문자를 사용한다.

   - resource의 도큐먼트 이름으로는 단수 명사를 사용해야 한다.

   - resource의 컬렉션 이름으로는 복수 명사를 사용해야 한다.

   - resource의 스토어 이름으로는 복수 명사를 사용해야 한다.

   - 예 : GET /members/1

2. **자원에 대한 행위는 HTTP Method (GET, POST, PUT, DELETE)로 표현합니다.**

3. **URI에 HTTP Method가 들어가면 안됩니다.**

   - 예) GET /books/delete/1 -> DELETE /books/1

4. **URI에 행위에 대한 동사 표현이 들어가면 안됩니다.**

   - (즉, CRUD 기능을 나타내는 것은 URI에 사용하지 않습니다.)
   - 예) GET /books/show/1 -> GET /books/1
   - 예2) GET /books/insert/2 -> POST /books/2

5. **경로 부분 중 변하는 부분은 유일한 값으로 대체합니다.**

   - (즉, id는 하나의 특정 resource를 나타내는 고유값을 의미합니다.)
   - 예) book을 생성하는 URI: POST /students
   - 예2) id=10 인 book을 삭제하는 URI: DELETE /students/10

6. **슬래시 구분자(/ )는 계층 관계를 나타내는데 사용합니다.**

   - 예) http://edwith.org/courses/java

7. **URI 마지막 문자로 슬래시(/ )를 포함하지 않습니다.**

   - 예) http://edwith.org/courses/ (X)

8. **URI에 포함되는 모든 글자는 리소스의 유일한 식별자로 사용되어야 하며 URI가 다르다는 것은 리소스가 다르다는 것이고, 역으로 리소스가 다르면 URI도 달라져야 합니다.**

9. **하이픈(- )은 URI 가독성을 높이는데 사용할 수 있습니다.**

10. **밑줄( _ )은 URI에 사용하지 않습니다.**

11. **URI 경로에는 소문자가 적합합니다.**

    - URI 경로에 대문자 사용은 피하도록 합니다. RFC 3986(URI 문법 형식)은 URI 스키마와 호스트를 제외하고는 대소문자를 구별하도록 규정하기 때문입니다.

12. **파일 확장자는 URI에 포함하지 않습니다. Accept header를 사용하도록 합니다.**

    - 예) http://edwith.org/files/java.jpg (X)
    - 예) GET /files/jdk18.exe HTTP/1.1 Host: edwith.org Accept: image/jpg (O)

13. **리소스 간에 연관 관계가 있는 경우 다음과 같은 방법으로 표현합니다.**

    - /리소스명/리소스 ID/관계가 있는 다른 리소스명
    - 예) GET : /books/{bookid}/viewers (일반적으로 소유 ‘has’의 관계를 표현할 때)

14. **자원을 표현하는 컬렉션(Collection)과 도큐먼트(Document)**

    - 컬렉션은 객체의 집합, 도큐먼트는 객체라고 생각하면 됩니다. 컬렉션과 도큐먼트 모두 리소스로 표현할 수 있으며 URI로 표현할 수 있습니다.
    - 예) http://edwith.org/courses/1 
    - courses는 컬렉션을 나타냅니다. 복수로 표현해야 합니다. courses/1 은 courses중에서 id가 1인 도큐먼트를 의미합니다.

### 4. HTTP 응답 상태 코드

- 잘 설계된 REST API는 URI만 잘 설계되는 것이 아니라 그 리소스에 대한 응답도 잘 표현되야 합니다. 정확한 응답의 상태 코드만으로도 많은 정보를 전달할 수 있기 때문입니다. 

- 자주 사용되는 HTTP 상태 코드는 다음과 같습니다. 

  ![image-20210105221841408](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210105221841408.png)



### **5. HATEOAS**

우리는 웹 게시판을 이용한다고 생각해보겠습니다. 게시판 목록보기 화면에서 제목을 누르면 상세 보기 화면으로 이동합니다. 상세 보기 화면에서 글쓰기 버튼을 클릭하면 글쓰기 화면으로 이동합니다. **이렇게 웹이란 하이퍼 링크를 통해 관계된 URL로 이동할 수 있습니다.**

#### **HATEOAS**란 ‘Hypermedia As The Engine Of Application State’ 의 약자입니다.

아래의 JSON문서를 보면 ‘_links’ 부분이 보일 것입니다. 이 부분이 HATEOAS부분입니다. 

자기 자신의 URL, book 컬렉션과 관련된 URL, book저장을 위한 URL 등이 표현되어 있는 것을 알 수 있을 것입니다.

```json
{
    "id" : 1,
    "title" : "hello spring",
    "author" : "carami"
    "price" : 5000,
    "_links":{
        "self":{
        "href":"http://localhost:8080/books/1"
        },
        "query-books":{
        "href":"http://localhost/books"
        },
        "write-books":{
        "href":"http://localhost/books"
        }
    }
}
```

이렇게, DATA와 함께 관련된 URL정보를 제공하는 것을 HATEOAS라고 말합니다.



### 6. REST와 관련된 논란

REST API에 대한 명확한 표준은 없습니다. 그런 문제로 REST API와 관련되서 많은 논쟁이 벌어집니다.

REST API를 구현하는 개발자들은 아키텍처 스타일을 모두 지키는 것이 힘들게 생각하는 경우가 있는 것 같습니다.

그러다보니, 모든 아키텍처 스타일을 지키지 않고 개발하게 됩니다.

특히 REST의 HATEOAS와 자체 표현 구조(self-descriptiveness)를 만족 못하도록 개발되는 경우가 많습니다.

**이렇게 REST API를 완벽하게 구현하지 못할 경우를 Web API라고 합니다.**



### **생각해보기**

**github에서 제공하는 REST API입니다. 기업들은 왜 REST API를 제공할까요?** 

- 기업들은 REST API를 제공함으로써 사용자들이 API를 더 쉽고 빠르게 사용할 수 있도록 합니다.
- 또한 이러한 REST API를 기점으로 시스템을 개발할 때 변경에 유연하고 확장성이 높은 코드를 작성할 수 있게 해주기 때문에 기업들은 REST API를 제공합니다.

---

## 2) Web API란?

Message Converter를 이용해서 객체를 Json Data로 Serialize 해주었다.

이 Serialize는 Jackson 라이브러리가 해주었으며, 아래의 라이브러리 의존성을 추가해주어야 한다.

```xml
<dependency>
   <groupId>com.fasterxml.jackson.core</groupId>
   <artifactId>jackson-core</artifactId>
   <version>2.10.2</version>
</dependency>
<dependency>
   <groupId>com.fasterxml.jackson.core</groupId>
   <artifactId>jackson-databind</artifactId>
   <version>2.10.2</version>
</dependency>
```

그리고 컨트롤러를 만들어서 특정 객체를 리턴해준다면 자동으로 Jackson 라이브러리가 Serialize를 해줄 것이다.

**PlusApiController.java**

```java
package kr.or.connect.webmvc.controller;

import kr.or.connect.webmvc.dto.PlusResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PlusApiController {
    @GetMapping("/api/plus")
    @ResponseBody
    public PlusResult plus(@RequestParam("value1") int value1, @RequestParam("value2") int value2){
        PlusResult plusResult = new PlusResult();
        plusResult.setValue1(value1);
        plusResult.setValue2(value2);
        plusResult.setResult(value1 + value2);
        return plusResult;
    }
}
```

**실행 결과**

```json
{"value1":10,"value2":20,"result":30}
```



















