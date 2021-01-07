## SQL

### SQL(Structured Query Language)

- SQL은 데이터를 보다 쉽게 검색하고 추가, 삭제, 수정 같은 조작을 할 수 있도록 고안된 컴퓨터 언어이다.
- 관계형 데이터베이스에서 데이터를 조작하고 쿼리하는 표준 수단이다.

- DML (Data Manipulation Language): 데이터를 조작하기 위해 사용한다.
  - INSERT, UPDATE, DELETE, SELECT 등이 해당된다.
- DDL (Data Definitin Language): 데이터베이스의 스키마를 정의하거나 조작하기 위해 사용한다.
  - CREATE, DROP, ALTER 등이 해당된다.
- DCL (Data Control Language): 데이터를 제어하는 언어이다. 권한을 관리하고, 데이터의 보안, 무결성등을 정의한다.
  - GRANT, REVOKE 등이 해당된다.

### Database 생성하기

- MySQL 관리자 계정인 root로 DBMS에 접속하겠다.

  ```bash
  mysql -uroot -p
  ```

  맥은 암호가 없다. => 그냥 엔터

- 데이터베이스 생성 (루트 유저는 모든 명령이 가능하다.(리눅스와 비슷))

  ```sql
  create database DB이름;
  ```

### Database 사용자 생성과 권한 주기

- grant (권한주기 및 계정 생성)
  - ex) `grant all privileges on db이름.* to 계정이름@'&' identified by '암호';`
    - db 이름 뒤의 *는 모든 권한을 의미한다.
    - @'%'는 어떤 클라이언트에서든 접근가능하다는 의미이다. @'localhost'는 해당 컴퓨터에서만 접근가능하다는 의미이다.
- flush privileges (DBMS에게 권한 적용)

- **MySQL 8 에서는 다음과 같이 변경되었다.**
  - User생성
    - CREATE USER connectuser@localhost IDENTIFIED BY 'connect123!@#';
  - DB에 권한 부여
    - GRANT ALL PRIVILEGES ON connectdb.* TO 'connectuser'@'localhost';
  - 권한 바로 적용
    - FLUSH PRIVILEGES:

### 생성한 Database에 특정 사용자로 접속하기

- mysql -h호스트명 -uDB계정명 -p 데이터베이스이름
  - ex) `mysql -hlocalhost -uconnectuser -p connectdb;`

### MySQL 콘솔 접속 종료

- quit;
- exit;

### MySQL 버젼 정보 및 현재 날짜 정보 

- ```mysql
  select version(), current_date;
  ```

### 키워드는 대소문자를 구분하지 않는다.

- select 와 SELECT, update와 UPDATE 는 동일하다.

### 여러 문장을 한 줄에 연속으로 붙여서 실행가능하다. (한 문장 끝에 세미콜론이 필요하다.)

- ```sql
  select version(); select current_date;
  ```

### SQL을 입력하는 도중에 취소할 수 있다.

- 긴쿼리를 작성하다 중간에 취소해야 하는 경우에는 즉시 `\c`를 붙혀주면 된다.

### DBMS에 존재하는 데이터베이스 확인하기

- ```sql
  show databases;
  ```

### 사용중인 데이터베이스 전환하기

데이터베이스를 전환하려면, 이미 데이터베이스가 존재해야하며 현재 접속중인 계정이 해당 데이터베이스를 사용할 수 있는 권한이 있어야 한다.

- ```sql
  use 데이터베이스명;
  ```

### 테이블(table)의 구성 요소

- **테이블(Table): RDBMS의 기본적 저장구조**
  - 한 개 이상의 column과 0개 이상의 row로 구성
- **열(Column): 테이블 상에서의 단일 종류의 데이터를 나타낸다.**
  - 특정 데이터 타입 및 크기를 가지고 있다.
- **행(Row): Column들의 값의 조합 (레코드라고도 불린다.)**
  - 기본키(PK)에 의해 구분된다. 기분키는 중복을 허용하지 않으며 없어서는 안된다.
- **Field: Row와 Column의 교차점을 의미한다.**
  - 데이터를 포함할 수 있고 없을 때는 NULL 값을 가지고 있을 수 있다. (제약 가능)

### 현재 데이터베이스에 존재하는 테이블 목록 확인하기

- ```sql
  show tables;
  ```

### SQL 연습을 위한 테이블 생성과 값의 저장

- sql 파일을 특정 데이터베이스에 import

  - mysql -u유저이름 -p 데이터베이스이름 < sql파일경로

  - ```bash
    mysql -uconnectuser -p connectdb < examples.sql
    ```

- show tables를 사용해서 sql 파일들이 잘 import 되었는지 확인

### 테이블 구조를 확인하기 위한 DESCRIBE 명령

table 구조를 확인하기 위해, DESCRIBE 명령을 사용

- ```sql
  DESCRIBE 테이블명;
  ```

---

## 데이터 조작어 (Data Manipulation Language, DML) -1

### 데이터 조작어의 종류

- 데이터 조작어는 모두 동사로 시작합니다. 시작하는 동사에 따라서 다음과 같은 4가지 조작어가 있습니다.
  - **SELECT - 검색**
  - **INSERT - 삽입**
  - **UPDATE - 수정**
  - **DELETE - 삭제**

### SELECT 구문의 기본문형

- SELECT(DISTINCT) 컬럼명(ALIAS) FROM 테이블명;
  - SELECT: 검색하고자 하는 데이터(칼럼)를 나열한다.
  - DISTINCT: 중복행을 제거
  - ALIAS: 나타날 컬럼에 대한 다른 이름 부여
  - FROM: 선택한 컬럼이 있는 테이블을명시한다.

### SELECT 구문 예제(전체 데이터 검색)

- 전체 데이터 검색
- SELECT *를 기술함으로써 나타낼 수 있다.
  - ex) `SELECT * FROM department;`

### 별칭을 활용해서 SELECT

- ```sql
  select Column as ALIAS FROM TABLE
  ```

  - ex) `select empno as 사번, name as 이름, job as 직업 from employee;`

### SELECT 구문 예제 (컬럼의 합성(Concatenation))

- **문자열 결합함수 concat 사용**
  - ex) `select concat(empno, '-', deptno) as '사번-부서번호' FROM employee;`

### SELECT 구문 예제(중복행의 제거)

- 중복되는 행이 출력되는 경우, DISTINCT 키워드로 중복행을 제거
  - ex) `SELECT DISTINCT deptno FROM employee;`

### SELECT 구문 예제(정렬)

- 정렬하고 싶은 경우, ORDER BY 키워드로 정렬
  - ex) `SELECT empno, name FROM employee ORDER BY name DESC;`

### SELECT 구문 예제(특정 행 검색 - where 절)

- ```sql
  SELECT(DISTINCT) 칼럼명(ALIAS) FROM 테이블명 WHERE 조건식 ORDER BY 칼럼이나 표현식 (ASC 또는 DESC)
  ```

  - **조건식 : 컬럼이름이나 표현식의 상수, 연산자로 구성**

    - 산술비교 연산자도 사용가능
    - ex) `select name, hiredate from employee where hiredate < '1981 - 01 - 01';`

  - **IN 키워드**

    - ex) `select * from employee where deptno in (10, 30);`
    - same ex) select * from employee where deptno = 10 or deptno = 30;

  - **LIKE 키워드**

    - **와일드 카드를 사용하여 특정 문자를 포함한 값에 대한 조건을 처리**

    - **%는 0에서부터 여러 개의 문자열을 나타냄**

    - **_는 단 하나의 문자를 나타내는 와일드 카드**

    - employee 테이블에서 이름에 'A'가 포함된 사원의 이름(name)과 직업(job)을 출력하시오

      - ```sql
        SELECT name, job FROM employee WHERE name LIKE '%A%';
        ```

### SELECT 구문 예제 (함수의 사용)

- **UCASE, UPPER** : 대문자로 바꾼다.
  - ex) `SELECT UPPER(name) FROM employee;`
- **LCASE, LOWER :** 소문자로 바꾼다.
  - ex) `SELECT LOWER(name) FROM employee;`

- **substring** : String의 일부분을 출력한다. (index는 1부터 시작)
  - ex) `SELECT SUBSTRING('Happy Day', 3, 2);` 
    - **pp** 출력 
- **LPAD, RPAD** : 공백 문자에 내가 원하는 부분을 채우는 방법
  - ex) `SELECT LPAD('hi', 5, '?');`
    - **???hi** 출력
  - ex) `SELECT RPAD('hi', 5, '?');`
    - **hi???** 출력
- **MOD(x,y)**: x를 y로 나눈 값을 출력
  - ex) `SELECT MOD(3,2);`
    - **1** 출력

### SELECT 구문 (CAST 형변환)

- **CAST 함수는 type을 변경(지정) 하는데 유용하다.**
  - **CAST(expression AS type) 또는 CONVER(expression, type) 또는 CONVERT(expression USING transcoding_name)**사용
    - ex) `SELECT CAST(now() as date);`

### SELECT 구문 (그룹함수)

지금까지 배웠던 것은 단일함수이고, 그룹함수는 전체 row를 연산해서 값을 리턴한다.

**그룹 함수는 grouping(group by) 작업이 필요하다.**

- **COUNT(expr): <u>non-NULL</u>인 row의 숫자를 반환**
  
  - ex) `select count(*) from employee;`
- **COUNT(DISTINCT expr, [expr ...]): non-NULL인 중복되지 않은 row의 숫자를 반환**
- **COUNT(*): row의 숫자를 반환**
- **AVG(expr): expr의 평균값을 반환**
  
  - ex) `select avg(salary) from employee;`
- **MIN(expr): expr의 최소값을 반환**
- **MAX(expr): expr의 최대값을 반환**
- **SUM(expr): expr의 합계를 반환**
- **GROUP_CONCAT(expr): 그룹에서 concatenated한 문자를 반환**
- **VARIANCE(expr): 분산**
- **STDDEV(expr): expr의 표준편차를 반환**

- **Example**

  - employee 테이블에서 부서번호가 30인 직원의 급여 평균과 총 합계를 출력

    - ```sql
      SELECT AVG(salary), SUM(salary) FROM employee where deptno = 30;
      ```

### SELECT 구문 예제 (그룹함수와 groupby 절)

그룹별 어떠한 정보를 가져오고 싶을 때 사용하면 되는 문법이다. (groupby 컬럼명)

- employee 테이블에서 부서별 직원의 부서번호, 급여 평균과 총합계를 출력

  - ```sql
    SELECT deptno, AVG(salary), SUM(salary) FROM employee GROUP BY deptno;
    ```

---

## 데이터 조작어 (Data Manipulation Language, DML) -2

### 데이터 입력 (INSERT 문)

- **INSERT INTO 테이블명(필드1, 필드2, 필드3, ...) VALUES (필드1의 값, 필드2의 값, 필드3의 값, ...)**
  - 필드의 개수와 필드의 값의 개수는 같아야 한다.
  - 필드 타입과 필드의 값의 타입이 잘 맵핑되어야 한다.
- **INSERT INTO 테이블명 VALUES (필드1의 값, 필드2의 값, 필드3의 값, 필드4의 값, ...)**
  - 모든 필드의 값을 채워주어야 한다.
  - 순서대로 필드의 값을 채워주어야 한다.

- **필드명을 지정해주는 방식은 디폴트 값이 세팅되는 필드는 생략할 수 있다.**
- **필드명을 지정해주는 방식은 추후, 피드가 추가/변경/수정 되는 변경에 유연하게 대처 가능하다.**
- **필드명을 생략했을 경우에는 모든 필드 값을 반드시 입력해야 한다.**
- **Examples**
  - ROLE 테이블에 role_id는 200, description에는 'CEO'로 한건의 데이터를 저장하시오.
    - INSERT INTO role(role_id, description) VALUES (200, 'CEO');

> Primary Key에는 NULL 값이 들어갈 수 없기 때문에 값을 주지 않는다면 에러가 발생된다.

### 데이터 입력 (UPDATE 문)

- **UPDATE 테이블명 SET 필드1=필드1의 값, 필드2=필드2의 값, 필드3=필드3의 값, ... WHERE 조건식**
- **조건식을 통해 특정 row만 변경할 수 있다.**
- **조건식을 주지 않으면 전체 row가 영향을 미치니 조심해서 사용하도록 한다.**
- **Examples**
  - ex) `UPDATE ROLE SET description='CTO' WHERE role_id=200;`

### 데이터 삭제 (DELETE 문)

- **DELETE FROM 테이블명 WHERE 조건문**
- **조건식을 통해 특정 row만 삭제할 수 있다.**
- **조건식을 주지 않으면 전체 로우가 영향을 미치니 조심해서 사용하도록 한다.**
- **Examples**
  - ex) `DELETE FROM role WHERE role_id=200;`

### 생각해보기

1. 사원의 이름과 그 사원이 속한 부서명을 구하려면 어떻게 해야할까요?

   이 경우엔 EMPLOYEE 테이블과 DEPARTMENT 테이블을 조인(Join)해야 합니다. 

   select문과 join에 대해 알아보세요.

   - **SQL JOIN 사용 예시**

     - ```sql
       SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate
       FROM Orders (INNER or RIGHT or LEFT or FULL) JOIN Customers ON Orders.CustomerID=Customers.CustomerID;
       ```

   - **SQL Join이란 2개 이상의 테이블로부터 관련있는 컬럼에 기초하여 행을 결합하는데 사용한다.**

     - **INNER JOIN이란 FROM 테이블과 JOIN 테이블에서 ON 조건식이 만족하는 레코드를 선택한다.**

       ON 조건식이 만족하지 않는다면 어느 두 테이블의 필드도 결과로서 나오지 않으므로 레코드의 기존 필드 값이 NULL이 아닌 이상 NULL을 볼 수 없다.

     - **LEFT JOIN이란 FROM 테이블의 모든 레코드와 JOIN 테이블의 ON 조건식이 만족하는 레코드를 반환한다.** 

       일치하는 항목이 없다면 JOIN 테이블의 필드 값은 NULL이 됩니다.

     - **RIGHT JOIN이란 JOIN 테이블의 모든 레코드와 FROM 테이블의 ON 조건식이 만족하는 레코드를 반환합니다.** 

       일치하는 항목이 없다면 FROM 테이블 필드 값은 NULL이 됩니다.

     - **FULL JOIN이란 FROM과 JOIN 테이블의 모든 레코드를 반환한다.**

       ON 조건식을 만족하는 레코드가 없다면 관련 테이블의 필드 값은 NULL이 된다.

   - **우리는 INNER JOIN을 사용해서 사원의 이름과 그 사원이 속한 부서명을 구할 수 있으며 쿼리문은 아래와 같다.**

     ```sql
     SELECT employee.name, department.name FROM employee INNER JOIN department ON employee.deptno = department.deptno;
     ```

   ---

   ## 데이터 정의어 (Data Definition Language, DDL)

   ### MySQL 데이터 타입

   - **TINYINT(M)**
     - 부호 있는 수는 -128 ~ +127 까지.
     - 부호 없는 수는 0 ~ 255 까지 표현. (1 바이트)
   - **SMALLINT(M)**
     - 부호 있는 수는 -32768 ~ +32768 까지.
     - 부호 없는 수는 0 ~ 65535 까지 표현. (2 바이트)
   - **MEDIUMINT(M)**
     - 부호 있는 수는 -8388608 ~ +8388607 까지.
     - 부호 없는 수는 0 ~ 16777215 까지 표현. (3 바이트)
   - **INT(M) or INTEGER(M)**
     - 부호 있는 수는 -2147483648 ~ +2147483647 까지.
     - 부호 없는 수는 0 ~ 4294967295 까지. (4 바이트)
   - **BIGINT(M)**
     - 부호 있는 수는 -92233720036854775808 ~ +92233720036854775808 까지.
     - 부호 없는 수는 0 ~ 18446744073709551615 까지.
   - **FLOAT(M, D)**
     - 부동 소수점을 나타낸다. 언제나 부호 있는 수임. (-3.402823466E + 38 ~ 3.402823466E + 38)
   - **DOUBLE(M, D)**
     - 2배 정밀도를 가진 부동 소수점. (-1.79769313486231517E + 308 ~ 6931348623157E + 308)
   - **DATE**
     - 날짜를 표현하는 타입. '9999-12-31'. (3 바이트)
   - **DATETIME**
     - 날짜와 시간을 같이 나타내는 타입. '9999-12-31 23:59:59'. (8 바이트)
   - **TIMESTAMP**
     - '1970-01-01 00:00:00' 부터 2037년까지 나타낼 수 있다. (4 바이트)
   - **TIME**
     - 시간을 나타낸다. '839:59:59' 부터 '839:59:59'까지 나타낼 수 있다.
   - **YEAR**
     - 년도를 나타낸다. 1901년부터 2155년, 0000년을 나타낼 수 있다.
   - **CHAR(M)**
     - 고정 길이를 갖는 문자열을 저장할 수 있따. M은 1부터 255까지이다.
   - **VARCHAR(M)**
     - CHAR은 고정 길이인 반면 VARCHAR은 가변 길이이다.
   - **TINYBLOB, TINYTEXT**
     - 255개의 문자를 저장할 수 있다.
   - **BLOB, TEXT**
     - 65,535개의 문자를 저장할 수 있다.
   - **MEDIUMBLOB, MEDIUMTEXT**
     - 16,777,215개의 문자를 저장할 수 있따.
   - **LONGBLOB, LONGTEXT**
     - 4,294,967,295(4기가)개의 문자를 저장할 수 있다.

### 테이블 생성

- ```sql
  create table 테이블명(
  	필드명1 타입 [NULL | NOT NULL][DEFAULT][AUTO_INCREMENT],
     필드명2 타입 [NULL | NOT NULL][DEFAULT][AUTO_INCREMENT],
     필드명3 타입 [NULL | NOT NULL][DEFAULT][AUTO_INCREMENT],
     ..........
     PRIMARY KEY(필드명)
  );
  ```

- **데이터 형 외에도 속성값의 빈 값 허용 여부는 NULL 또는 NOT NULL로 설정**

- **DEFAULT 키워드와 함께 입력하지 않았을 때의 초기값을 지정할 수 있다.**

- **입력하지 않고 자동적으로 1씩 증가하는 번호를 위한 AUTO_INCREMENT**

- ```sql
  CREATE TABLE EMPLOYEE2(   
             empno      INTEGER NOT NULL PRIMARY KEY,  
             name       VARCHAR(10),   
             job        VARCHAR(9),   
             boss       INTEGER,   
             hiredate   VARCHAR(12),   
             salary     DECIMAL(7, 2),   
             comm       DECIMAL(7, 2),   
             deptno     INTEGER);
  ```

### 테이블 수정 (컬럼 추가 / 삭제)

- 컬럼 추가

  ```sql
  ALTER TABLE 테이블명 ADD 필드명 타입[NULL|NOT NULL][DEFAULT][AUTO_INCREMENT];
  ```

  - ex) `ALTER TABLE BOOK ADD author VARCHAR(20) NOT NULL;`

- 컬럼 삭제

  ```sql
  ALTER TABLE 테이블명 DROP 필드명;
  ```

  - ex) `ALTER TABLE book DROP price;`

### 테이블 수정 (컬럼 수정)

- ```sql
  ALTER TABLE 테이블명 CHANGE 필드명 새필드명 타입 [NULL|NOT NULL][DEFAULT][AUTO_INCREMENT]
  ```

### 테이블 이름 변경

- ```sql
  ALTER TABLE 테이블명 RENAME 변경이름
  ```

  - ex) `ALTER TABLE employee2 RENAME employee3`

### 테이블 삭제하기

- ```sql
  DROP TABLE 테이블이름;
  ```

- **제약 조건이 있을 경우에는 drop table 명령으로도 테이블이 삭제되지 않을 수 있다. 그럴 경우는 테이블을 생성한 반대 순서로 삭제를 해야 한다.** (FOREIGN KEY 제약 조건)

### 생각해보기

1. 칼럼의 길이가 10인데, 해당 칼럼에 값이 저장되어 있습니다. 이 때 칼럼의 길이를 5로 바꾼다면 어떤 일이 벌어질까요?

   - 데이터가 잘리기 때문에 "ERROR 1265 (01000): Data truncated for column 'name' at row 1"와 같은 에러가 발생하고 해당 명령은 실행되지 않습니다.

2. **문자열을 저장하는 데이터 타입인 CHAR와 VARCHAR 차이점에 대해 알아보고 어떤 상황에서 CHAR 또는 VARCHAR 를 선택하는 것이 효율적인지 생각해봅시다.**

   - CHAR 데이터 타입은 길이가 고정되기 때문에 저장 및 검색이 빠르지만 저장공간을 낭비할 수 있고, VARCHAR 데이터 타입은 길이가 가변적이기 때문에 저장 및 검색이 느리지만 저장공간을 낭비하지 않습니다.

     CHAR 데이터 타입은 고정된 데이터인 성별, 주민번호 등을 저장 및 연산할 때 쓰이면 좋고, VARCHAR은 가변적인 데이터인 Content, Address 등을 저장 및 연산할 때 쓰이면 좋습니다.

3. 문자열 데이터 타입에는 문자셋을 지정할 수 있습니다. 문자셋에 따라 해당 필드가 차지하는 공간 크기를 한번 계산해보자. (예: VARCHAR(10) CHARACTER SET UTF8; 은 몇 Byte 크기를 차지할까요? ASCII 일때는 또 몇 Byte 크기를 가질까요?

   - https://dev.mysql.com/doc/refman/8.0/en/charset-charsets.html 를 통해서 MySQL이 지원하는 character sets를 볼 수 있으며 VARCHAR(10) UTF8은 최대 30바이트의 크기를 가지며 ASCII일 때는 10바이트의 크기를 가집니다.

### 프로젝트 구현시 이슈 사항

**시간 이슈**

- Timestamp 자료형을 통해 DB에서 시간을 저장하고, Util.Date와 sql.Date간에 변환하면 해결된다.

**Like 문 이슈**

- Like문에서 사용하는 문자열 자체를 ?로 두어서 PreparedStatement 객체를 이용하여 sql문을 조작하면 해결된다.



























