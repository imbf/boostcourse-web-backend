## 1) Spring Security 개요

### Authentication & Authorization

- **아이디와 암호를 이용해서 로그인 하는 과정을 인증(Authentication)이라고 합니다.**
- **네이버 카페를 사용해보면 로그인을 했다 하더라도, 특정 카페는 회원가입을 하고 어느정도 이상의 등급이 되지 않을 경우 이용을 못하기도 합니다. 이 부분을 우리는 인가(Authorization)라고 합니다.**

**스프링 시큐리티(Spring Security)를 사용하면 편리하게 인증/인가를 구현할 수 있습니다. **



### 1. 스프링 시큐리티(Spring Security)

- **스프링 시큐리티는 스프링 기반의 어플리케이션의 보안(인증과 권한)을 담당하는 프레임워크를 말합니다.**
- 스프링 시큐리티는 보안과 관련해서 체계적으로 많은 옵션들을 지원해줍니다.
- **스프링 시큐리티는 필터(Filter) 기반으로 동작하기 때문에 스프링 MVC 와 분리되어 관리 및 동작합니다.**
- 참고로 스프링 시큐리티 3.2부터는 XML로 설정하지 않고 자바 config 설정으로 간단하게 설정할 수 있도록 지원하고 있습니다.
- **보안 용어 정리**
  -  **접근 주체(Principal)** : 보호된 대상에 접근하는 유저
  -  **인증(Authentication)** : 인증은 '증명하다'라는 의미로 예를 들어, 유저 아이디와 비밀번호를 이용하여 로그인 하는 과정을 말합니다.
  - **인가(Authorization)** : '권한부여'나 '허가'와 같은 의미로 사용됩니다. 즉, 어떤 대상이 특정 목적을 실현하도록 허용(Access) 하는 것을 의미합니다.
  - **권한** : 인증된 주체가 애플리케이션의 동작을 수행할 수 있도록 허락되었는지를 결정할 때 사용합니다.



### 2. 스프링 시큐리티 필터(Spring Security Filter)

- 클라이언트(보통 브라우저)는 요청을 보내고 되고, 그 요청을 서블릿이나 JSP등이 처리하게 됩니다. 스프링 MVC에서는 요청을 가장 먼저 받는 것이 **DispatcherServlet**이라고 했었습니다.
- **이 DispatcherServlet이 요청 받기 전에 다양한 필터들이 있을 수 있습니다.**
  **필터가 하는 역할은 클라이언트와 자원 사이에서 요청과 응답 정보를 이용해 다양한 처리를 하는데 목적이 있습니다.** 

- 어떤 필터는 요청을 받은 후, 클라이언트가 원래 요청한 자원이 아닌 다른 자원으로 리다이렉트 시킬 수도 있습니다. 어떤 필터는 다음 필터에게 요청과 응답을 전달하지 않고, 바로 클라이언트에게 응답하고 끝낼 수도 있습니다.
- **스프링 시큐리티는 다양한 기능을 가진 필터들을 10개 이상 기본적으로 제공합니다. 이렇게 제공되는 필터들을 Security Filter Chain(시큐리티 필터 체인)이라고 말합니다.** 
- 위의 그림은 시큐리티 필터 체인과 각각의 필터에서 사용하는 객체들(Repository, Handler, Manager등)에 대해 잘 표현하고 있습니다.
  ![image-20210108144521932](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210108144521932.png) 

- 먼저 스프링 시큐리티가 제공하는 필터들이 어떤 역할을 담당하는지 정리해보도록 하겠습니다.
  - **SecurityContextPersistenceFilter** : SecurityContextRepository에서 SecurityContext를 가져오거나 저장하는 역할을한다.
  - **LogoutFilter** : 설정된 로그아웃 URL로 오는 요청을 감시하며, 해당 유저를 로그아웃 처리
  - **(UsernamePassword)AuthenticationFilter)** : (아이디와 비밀번호를 사용하는 form 기반 인증) 설정된 로그인 URL로오는 요청을 감시하며, 유저 인증 처리
    1. AuthenticationManager를 통한 인증 실행
    2. 인증 성공 시, 얻은 Authentication 객체를 SecurityContext에 저장 후 AuthenticationSuccessHandler 실행
    3. 인증 실패 시, AuthenticationFailureHandler 실행
  - **DefaultLoginPageGeneratingFilter** : 인증을 위한 로그인폼 URL을 감시한다.
  - **BasicAuthenticationFilter** : HTTP 기본 인증 헤더를 감시하여 처리한다.
  - **RequestCacheAwareFilter** : 로그인 성공 후, 원래 요청 정보를 재구성하기 위해 사용된다.
  - **SecurityContextHolderAwareRequestFilter** : HttpServletRequestWrapper를 상속한 SecurityContextHolderAware RequestWapper 클래스로 HttpServletRequest 정보를 감싼다. Security ContextHolderAwareRequestWrapper 클래스는 필터 체인상의 다음 필터들에게 부가정보를 제공한다.
  - **AnonymousAuthenticationFilter** : 이 필터가 호출되는 시점까지 사용자 정보가 인증되지 않았다면 인증토큰에 사용자가 익명 사용자로 나타난다.
  - **SessionManagementFilter** : 이 필터는 인증된 사용자와 관련된 모든 세션을 추적한다.
  - **ExceptionTranslationFilter** : 이 필터는 보호된 요청을 처리하는 중에 발생할 수 있는 예외를 위임하거나 전달하는 역할을 한다.
  - **FilterSecurityInterceptor** : 이 필터는 AccessDecisionManager 로 권한부여 처리를 위임함으로써 접근 제어 결정을 쉽게해준다.



### 3. 스프링 시큐리티 인증(Authentication)관련 아키텍처

- 아이디와 암호를 입력했을 때 이를 처리하는 필터는 AuthenticationFilter입니다.
- 해당 필터는 다음 그림과 같은 순서로 동작합니다.
  ![image-20210108145129020](/Users/baejongjin/Library/Application Support/typora-user-images/image-20210108145129020.png)
- **인증 흐름**
  1. 클라이언트(유저)가 로그인을 시도합니다.
  2. AuthenticationFilter는 AuthenticationManager, AuthenticationProvider(s), UserDetailsService를 통해 DB에서 사용자 정보를 읽어옵니다. 여기서 중요한 것은 UserDetailsService가 인터페이스라는 것입니다. 해당 인터페이스를 구현한 빈(Bean)을 생성하면 스프링 시큐리티는 해당 빈을 사용하게 됩니다. 즉, 어떤 데이터베이스로 부터 읽어들일지 스프링 시큐리티를 이용하는 개발자가 결정할 수 있게 됩니다.
  3. UserDetailsService는 로그인한 ID에 해당하는 정보를 DB에서 읽어들여 UserDetails를 구현한 객체로 반환합니다. 프로그래머는 UserDetails를 구현한 객체를 만들어야 할 필요가 있을 수 있습니다. UserDetails정보를 세션에 저장하게 됩니다.
  4. 스프링 시큐리티는 인메모리 세션저장소인 SecurityContextHolder 에 UserDetails정보를 저장하게 됩니다.
  5. 클라이언트(유저)에게 session ID(JSESSION ID)와 함께 응답을 하게 됩니다.
  6. 이후 요청에서는 요청 쿠키에서 JSESSION ID정보를 통해 이미 로그인 정보가 저장되어 있는 지 확인합니다. 이미 저장되어 있고 유효하면 인증 처리를 해주게 됩니다.



### 생각해보기

1. 스프링 시큐리티에 대한 기본적인 개념에 대해 알아보았습니다. 스프링 시큐리티는 굉장히 다양한 필터를 가지고 있는데요. 왜 한가지 필터로 모두 구현하지 않고, 이렇게 다양한 필터를 제공하는 것일까요?
   - 다양한 필터를 계층적 구조로 구성함으로써 사용자가 추후 인증 및 인가 과정에서 유지보수하기 쉽게 만들어준다.



---

## 2) Spring Security 설정하기

### 1. 프로젝트 생성 및 라이브러리 의존성 설정

Group Id : org.edwith.webbe
Artifact Id : securityexam

**pom.xml**



### 2. 웹 어플리케이션 설정 파일 작성

1. **web.xml 또는 web.xml파일을 대신하는 자바 Config설정 파일**

   - **WebAppInitializer.java 파일을 작성합니다.**

     ```java
     package org.edwith.webbe.securityexam.config;
     
     import javax.servlet.Filter;
     
     import org.springframework.web.filter.CharacterEncodingFilter;
     import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
     
     public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
     
     	// Spring Config 파일을 설정한다.
         @Override
         protected Class<?>[] getRootConfigClasses() {
             return new Class<?>[]{ApplicationConfig.class, SecurityConfig.class};
         }
     
         // Spring WEB Config 파일을 설정한다. WebConfig는 Bean을 RootConfig에서 설정한 곳에서부터 찾는다.
         @Override
         protected Class<?>[] getServletConfigClasses() {
             return new Class<?>[]{MvcConfig.class};
         }
     
         /*
         getServletMapping()은 DispatcherServlet이 매핑되기 위한 하나 혹은 여러 개의 패스를 지정한다.
         위의 코드에서는 애플리케이션 기본 서블릿인 /에만 매핑이 되어 있다. 그리고 이것은 애플리케이션으로 들어오는 모든 요청을 처리한다.
         원래 서블릿에서는 / 을 처리하는 DefaultServlet이 설정되어 있다.
          */
         @Override
         protected String[] getServletMappings() {
             return new String[]{"/"};
         }
     
         /*
         필터를 설정한다.
          */
         @Override
         protected Filter[] getServletFilters() {
             CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
             encodingFilter.setEncoding("UTF-8");
             return new Filter[]{encodingFilter};
         }
     }
     
     ```

     - 스프링 MVC를 사용할 경우에 AbstractAnnotationConfigDispatcherServletInitializer를 상속받아서 구현하면 편리합니다.
     - protected Class\<?>[] getRootConfigClasses() 메소드와 protected Class<?>[] getServletConfigClasses()를 오버로딩 하여 스프링 설정 파일과 스프링 MVC설정 파일들을 리턴하게 합니다. 스프링 컨테이너는 자동으로 2개의 메소드를 호출하여, 리턴 받은 클래스들을 설정파일로 사용하게 됩니다.
     - protected String[] getServletMappings()를 오버로딩하여, DispatcherServlet이 처리해야할 path가 무엇인지 스프링 컨테이너에게 알려주도록 합니다. ‘/’는 모든 경로를 처리할 때 사용하는 path값입니다.
     - protected Filter[] getServletFilters()는 DispatcherServlet 앞에 동작하는 필터를 설정할 때 사용합니다.

2. **스프링 설정파일**

   - **ApplicationConfig.java 파일을 작성합니다.**

     ```java
     package org.edwith.webbe.securityexam.config;
     
     import org.springframework.context.annotation.ComponentScan;
     import org.springframework.context.annotation.Configuration;
     
     // 레이어드 아키텍처에서 Controller가 사용하는 Bea들에 대해 설정을 한다.
     // dao, service를 컴포넌트 스캔하여 찾도록 한다.
     @Configuration
     @ComponentScan(basePackages = {"org.edwith.webbe.securityexam.dao", "org.edwith.webbe.securityexam.service"})
     public class ApplicationConfig{
     
     }
     ```

3. **스프링 MVC설정파일**

   - **MvcConfig.java 클래스를 작성합니다.**

     ```java
     package org.edwith.webbe.securityexam.config;
     
     import org.springframework.context.annotation.ComponentScan;
     import org.springframework.context.annotation.Configuration;
     import org.springframework.web.servlet.config.annotation.*;
     
     @Configuration
     @EnableWebMvc
     @ComponentScan(basePackages = {"org.edwith.webbe.securityexam.controller"})
     public class MvcConfig implements WebMvcConfigurer {
     
     	// default servlet 핸들러를 설정한다.
     	// 원래 서블릿은 '/**' (모든 요청)을 처리하는 default servlet을 제공한다.
     	// 스프링에서 설정한 path는 스프링이 처리하고, 스프링이 처리하지 못한 경로에 대한 처리는 디폴트 서블릿에게 전달하여 처리하게 된다.
     	@Override
     	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
     		configurer.enable();
     	}
     
     	// Spring MVC에서 jsp view 가 위치하는 경로를 설정한다.
     	@Override
     	public void configureViewResolvers(ViewResolverRegistry registry) {
     		registry.jsp("/WEB-INF/view/", ".jsp");
     	}
     
        //    '/' 로 요청이 오면 '/main'으로 리다이렉트 하도록 합니다.
     	@Override
     	public void addViewControllers(ViewControllerRegistry registry) {
     		registry.addRedirectViewController("/", "/main");
     	}
     
        //  '/resources' 경로에 있는 자료들을 '/resources/**'로 접근하게 합니다.
     	@Override
     	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
     		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
     	}
     }
     ```

     - @EnableWebMvc는 어노테이션 기반의 스프링 MVC를 구성할 때 필요한 Bean들을 자동으로 구성해주는 역할을 수행합니다. xml로 설정할 때는 \<mvc:annontation-driven/>이라는 설정을 하게 되는데 같은 설정이라고 생각하면 됩니다.

4. **스프링 시큐리티 설정파일**

   - **SecurityWebApplicationInitializer.java 클래스를 작성합니다.**

     ```java
     package org.edwith.webbe.securityexam.config;
     
     import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
     
     /*
        AbstractSecurityWebApplicationInitializer를 상속받는 클래스를 작성해야 스프링 시큐리티 필터들이 활성화된다.
      */
     public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
     }
     ```

     - 스프링 시큐리티를 사용하려면 AbstractSecurityWebApplicationInitializer를 상속받는 클래스를 반드시 작성해주셔야 합니다. AbstractSecurityWebApplicationInitializer를 상속받는 클래스가 있을 경우 스프링 시큐리티가 제공하는 필터들을 사용할 수 있도록 활성화 해줍니다.

   - **SecurityConfig.java 클래스를 작성합니다.**

     - **스프링 시큐리티를 이용해 로그인/로그아웃/인증/인가 등을 처리하기 위한 설정 파일입니다.**

     ```java
     package org.edwith.webbe.securityexam.config;
     
     import org.springframework.context.annotation.Bean;
     import org.springframework.context.annotation.Configuration;
     import org.springframework.security.config.annotation.web.builders.HttpSecurity;
     import org.springframework.security.config.annotation.web.builders.WebSecurity;
     import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
     import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
     import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
     import org.springframework.security.crypto.password.PasswordEncoder;
     
     @Configuration
     @EnableWebSecurity // Spring Security를 구성하기 위한 기본적인 객체들을 설정
     public class SecurityConfig extends WebSecurityConfigurerAdapter {
     
         // '/webjars/**' 경로에 대한 요청은 인증/인가 처리하지 않도록 무시합니다.
         // '**'는 해당 폴더 하위까지의 모든 path를 의미한다.
         @Override
         public void configure(WebSecurity web) throws Exception {
             web.ignoring().antMatchers(
                     "/webjars/**");
         }
     
         // '/','/main'에 대한 요청은 누구나 할 수 있지만, 그 외의 요청은 모두 인증 후 접근 가능합니다.
         @Override
         protected void configure(HttpSecurity http) throws Exception {
             http
                     .csrf().disable()
                     .authorizeRequests()
                     .antMatchers("/", "/main").permitAll() // 해당 경로에는 누구나 다 접근 가능
                     .anyRequest().authenticated(); // 그 외의 경로는 인증/인가가 필요하다.
         }
     
         // 패스워드 인코더를 빈으로 등록합니다. 암호를 인코딩하거나, 인코딩된 암호와 사용자가 입력한 암호가 같은 지 확인할 때 사용합니다.
         @Bean
         public PasswordEncoder encoder() {
             return new BCryptPasswordEncoder();
         }
     }
     ```

     - @EnableWebSecurity가 붙어 있을 경우 스프링 시큐리티를 구성하는 기본적인 빈(Bean)들을 자동으로 구성해줍니다. WebSecurityConfigurerAdapter를 상속받으면, 특정 메소드를 오버라이딩 함으로써 좀 더 손쉽게 설정할 수 있습니다.
     - public void configure(WebSecurity web) 메소드를 오버라이딩 하는 이유는 인증/인가가 필요 없는 경로를 설정할 필요가 있을 때 오버라이딩을 합니다. 해당 메소드는 스프링 시큐리티 설정이 자동으로 호출이 되는데, 이때 WebSecurity객체를 파라미터로 넣어주게 됩니다.
       - 해당객체의 ignoring()메소드에 무시될 경로를 지정하면 됩니다. 여기에서는 "/webjars/**"를 설정하였습니다.
         - "**"는 특정 경로 이하의 모든 것을 의미합니다. 즉. "/webjars/"로 시작하는 모든 경로를 무시하라는 의미입니다.
     - protected void configure(HttpSecurity http) 메소드를 오버라이딩 한다는 것은 인증/인가에 대한 설정을 한다는 의미입니다. 가장 중요한 메소드라고 말할 수 있습니다.
     - http.csrf().disable()는 csrf()라는 기능을 끄라는 설정입니다. csrf는 보안 설정 중 post방식으로 값을 전송할 때 token을 사용해야하는 보안 설정입니다. csrf은 기본으로 설정되어 있는데요. csrf를 사용하게 되면 보안성은 높아지지만 개발초기에는 불편함이 있다는 단점이 있습니다. 그래서 csrf 기능을 끄도록 한 것입니다. disable()메소드는 http(여기에선 HttpSecurity)를 리턴합니다.

### 3. 보안 설정 테스트를 위한 컨트롤러 클래스 작성하기

**MainController.java**

```java
package org.edwith.webbe.securityexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
   
    // 해당 path는 필터기반의 인증/인가 처리를 하지 않는다.
    @RequestMapping("/main")
    @ResponseBody
    public String main(){
        return "main page";
    }

    @RequestMapping("/securepage")
    @ResponseBody
    public String securitypage(){
        return "secure page";
    }
}
```

**응답 상태 코드가 403인 경우, 해당 경로는 인증을 거친 후에만 접근가능하다는 것을 의미한다.**



### 생각해보기

1. 지금은 2개의 경로만 인증없이 접근하도록 설정해 놓은 상황입니다. 다른 경로를 추가하려면 어떻게 해야 할까요?
   - 다른 경로를 추가하려면 SecurityConfig.java 클래스의 public void configure(WebSecurity web) 메소드에서 필터기반의 인증/인가를 수행하지 않을 경로를 추가 설정한다.



---

## 3) Spring Security를 이용한 로그인 하기

### 1. PasswordEncoder 테스트하기

**PasswordEncoderTest.java**

```java
package org.edwith.webbe.securityexam.service;

import org.edwith.webbe.securityexam.config.ApplicationConfig;
import org.edwith.webbe.securityexam.config.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, SecurityConfig.class})
public class PasswordEncoderTest {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Test
	public void passwordEncode() throws Exception {
		System.out.println(passwordEncoder.encode("1234"));
	}
	
	@Test
	public void passwordTest() throws Exception {
    	String encodePasswd = "$2a$10$USbExG2YOZJqu5rR9eWAqO3NqwjS6c8uI0c695cnURA2gxqRnx41O";
    	String password = "1234";
    	boolean test = passwordEncoder.matches(password, encodePasswd);
    	System.out.println(test);
	}
}

```



### **2. 로그인/로그아웃 처리를 위한 설정 수정하기**

**Security.java**

```java
package org.edwith.webbe.securityexam.config;

import org.edwith.webbe.securityexam.service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/webjars/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/main", "/memembers/loginerror", "/members/joinform", "/members/join", "/members/welcome").permitAll()
                .antMatchers("/securepage", "/members/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/members/loginform")
                    .usernameParameter("userId")
                    .passwordParameter("password")
                    .loginProcessingUrl("/authenticate")
                    .failureForwardUrl("/members/loginerror?login_error=1")
                    .defaultSuccessUrl("/",true)
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**Security Config 코드에 대한 설명**

- ```java
  @Override
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.userDetailsService(customUserDetailsService);
      }
  ```

  - WebSecurityConfigurerAdapter가 가지고 있는 void configure(AuthenticationManagerBuilder auth)를 오버라이딩 하고 있습니다. 해당 메소드를 오버라이딩 한 후 UserDetailsService인터페이스를 구현하고 있는 객체를 auth.userDetailsService()메소드의 인자로 전달하고 있습니다.

  - 스프링 시큐리티 개요를 배울 때 AuthenticationFilter가 아이디/암호를 입력해서 로그인 할 때 처리해주는 필터이고 **아이디에 해당하는 정보를 데이터베이스에서 읽어 들일 때 UserDetailsService를 구현하고 있는 객체를 이용한다고 배웠습니다.** **UserDetailsService는 인터페이스이고 해당 인터페이스를 구현하고 있는 빈을 사용한다고 했었는데요.**

  - ```java
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    ```

    - 위와 같이 주입된 CustomUserDetailsService객체를 auth.userDetailsService(customUserDetailsService)로 설정하고 있습니다. 
      이렇게 설정된 객체는 아이디/암호를 입력 받아 로그인을 처리하는 AuthenticationFilter에서 사용하게 됩니다.

- ```java
  .authorizeRequests()
     .antMatchers("/", "/main", "/memembers/loginerror", "/members/joinform", "/members/join", "/members/welcome").permitAll()
  ```

  - 로그인 과정없이 사용할 수 있는 경로에 "/members/loginerror", "/members/joinform", "/members/join", "/members/welcome" 을 더 추가하였습니다.

- ```java
  .antMatchers("/securepage", "/members/**").hasRole("USER")
  ```

  - "/securepage", "/members/**"는 로그인도 되어 있어야 하고 "USER"권한도 가지고 있어야 접근할 수 있도록 설정하고 있습니다.

- ```java
  .and()
     .formLogin()
     .loginPage("/members/loginform")
     .usernameParameter("userId")
     .passwordParameter("password")
     .loginProcessingUrl("/authenticate")
     .failureForwardUrl("/members/loginerror?login_error=1")
     .defaultSuccessUrl("/",true)
     .permitAll()
  ```

  - 로그인 폼에 대해 설정하고 있습니다. 로그인 폼은 "/members/loginform"이 경로라는 것을 의미합니다.
  - 해당 경로가 요청 왔을 때 로그인 폼을 보여주는 컨트롤러 메소드를 작성해 줘야 합니다. 로그인 폼에서 input태그의 이름은 "userId", "password"이어야 한다는 설정을 하고 있습니다.
  - 로그인 처리를 해주는 경로는 "/authenticate"라고 설정하고 있습니다.
    - 우리가 구현하는 것은 아니지만 Spring Security필터가 자동으로 로그인 처리를 해준다.
  - 만약 로그인 처리가 실패하게 되면 "/loginerror?login_error=1"로 포워딩 됩니다. 
    - 해당 경로를 처리하는 컨트롤러 메소드는 개발자가 작성해줘야 합니다.
  - 로그인을 성공하게 되면 "/"로 리다이렉트 하게 됩니다.
  - 설정에 permitAll()이 붙어 있다는 것은 해당 로그인 폼이 아무나 접근 가능하다는 것을 의미합니다.

- ```java
  .and()
     .logout()
     .logoutUrl("/logout")
     .logoutSuccessUrl("/");
  ```

  - "/logout"요청이 오면 세션에서 로그인 정보를 삭제한 후 "/"로 리다이렉트 합니다.

### 

### 3. 로그인/로그아웃 처리를 위한 클래스 작성하기

**아이디와 암호를 전달받아 로그인을 처리하는 것은 AuthenticationFilter입니다.** AuthenticationFilter는 아이디에 해당하는 정보를 읽어 들이기 위해 <u>UserDetailsService인터페이스를 구현하는 빈(Bean)을 사용</u>합니다.

- UserDetailsService 인터페이스는 스프링 시큐리티에서 제공합니다. 해당 인터페이스를 구현한다는 것은 스프링 시큐리티와 밀접한 연관을 맺는다는 것을 의미합니다.

**로그인 아이디와 암호 정보를 가지고 있는 UserEntity객체를 생성합니다.**

**UserEntity.java**

```java
package org.edwith.webbe.securityexam.service.security;

public class UserEntity {
    private String loginUserId;
    private String password;

    public UserEntity(String loginUserId, String password) {
        this.loginUserId = loginUserId;
        this.password = password;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

**로그인 아이디와 권한(Role)정보를 가지는 UserRoleEntity클래스를 생성합니다.**

**UserRoleEntity.java**

```java
package org.edwith.webbe.securityexam.service.security;

public class UserRoleEntity {
    private String userLoginId;
    private String roleName;

    public UserRoleEntity(String userLoginId, String roleName) {
        this.userLoginId = userLoginId;
        this.roleName = roleName;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
```

**UserDbService인터페이스를 작성합니다. 로그인한 사용자 id를 파라미터로 받아들여서 UserEntity와 List\<UserRoleEntity>를 리턴하는 메소드를 가지고 있습니다.**

**UserDbService.java**

```java
package org.edwith.webbe.securityexam.service.security;

import java.util.List;

public interface UserDbService {
    public UserEntity getUser(String loginUserId);
    public List<UserRoleEntity> getUserRoles(String loginUserId);
}
```

**<u>데이터베이스에서 읽어 들인 로그인 정보는 UserDetails인터페이스를 구현하고 있는 객체에 저장</u>되어야 한다고 했습니다. UserDetails를 구현하고 있는 CustomUserDetails클래스를 생성합니다.**

**CustomUserDetails.java**

```java
package org.edwith.webbe.securityexam.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private Collection<? extends GrantedAuthority>authorities ;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
```

**UserDetailsService인터페이스를 구현하는 CustomUserDetailsService를 생성합니다.**

UserDetailsService인터페이스는 1개의 메소드인 public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException를 선언합니다.

사용자가 로그인을 할 때 아이디를 입력하면 해당 아이디를 loadUserByUsername()메소드의 인자로 전달합니다. 해당 아이디에 해당하는 정보가 없으면 UsernameNotFoundException이 발생합니다.

**정보가 있을 경우엔 UserDetails인터페이스를 구현한 객체를 리턴 하게 됩니다.**

**데이터베이스에서 로그인 아이디에 해당하는 정보를 읽어 들이기 위해서 UserDbService를 구현한 객체를 주입받고 있습니다.**
**UserDbService도 인터페이스이니 구현한 객체가 필요합니다.**

**<u>이렇게 조금은 복잡하게 구현된 이유는 데이터베이스에서 읽어 들이는 코드와 스프링 시큐리티에서 사용되는 코드를 분리하기 위함입니다.</u>**

**CustomUserDetailsService.java**

```java
package org.edwith.webbe.securityexam.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // CustomUserDbService는 인터페이스다. 해당 인터페이스를 구현하고 있는 객체가 Bean으로 등록되어 있어야 한다.
    @Autowired
    UserDbService userdbService;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        // loginId에 해당하는 정보를 데이터베이스에서 읽어 CustomUser객체에 저장한다.
        // 해당 정보를 CustomUserDetails객체에 저장한다.
        UserEntity customUser = userdbService.getUser(loginId);
        if(customUser == null)
            throw new UsernameNotFoundException("사용자가 입력한 아이디에 해당하는 사용자를 찾을 수 없습니다.");

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(customUser.getLoginUserId());
        customUserDetails.setPassword(customUser.getPassword());

        List<UserRoleEntity> customRoles = userdbService.getUserRoles(loginId);
        // 로그인 한 사용자의 권한 정보를 GrantedAuthority를 구현하고 있는 SimpleGrantedAuthority객체에 담아
        // 리스트에 추가한다. MemberRole 이름은 "ROLE_"로 시작되야 한다.
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(customRoles != null) {
            for (UserRoleEntity customRole : customRoles) {
                authorities.add(new SimpleGrantedAuthority(customRole.getRoleName()));
            } //for
        } //if

        // CustomUserDetails객체에 권한 목록 (authorities)를 설정한다.
        customUserDetails.setAuthorities(authorities);
        customUserDetails.setEnabled(true);
        customUserDetails.setAccountNonExpired(true);
        customUserDetails.setAccountNonLocked(true);
        customUserDetails.setCredentialsNonExpired(true);
        return customUserDetails;
    }
}
```

**UserDbService인터페이스를 상속받는 MeberService인터페이스를 다음과 같이 작성합니다.** UserDbService는 스프링 시큐리티에서 필요로하는 정보를 가지고 오는 메소드를 가지고 있습니다.

MemberService는 앞으로 회원과 관련된 모든 정보를 처리하는 서비스가 될 예정입니다. 예를 들어 회원 등록과 관련된 메소드는 MemberService에 추가되게 됩니다.

**MemberService.java**

```java
package org.edwith.webbe.securityexam.service;

import org.edwith.webbe.securityexam.service.security.UserDbService;

public interface MemberService extends UserDbService {

}
```

**MemberServiceImpl클래스는 MeberService인터페이스를 구현합니다. MemberService는 회원과 관련된 기능을 가지게 됩니다.**

**MemberServiceImpl.java**

```java
package org.edwith.webbe.securityexam.service;

import org.edwith.webbe.securityexam.service.security.UserEntity;
import org.edwith.webbe.securityexam.service.security.UserRoleEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Override
    public UserEntity getUser(String loginUserId) {
       
       //데이터베이스를 읽어들이지 않고 loginUserId가 무엇이든지 간에 "carami"라는 정보를 가진 UserEntity를 리턴하고 있습니다.
        return new UserEntity("carami", "$2a$10$G/ADAGLU3vKBd62E6GbrgetQpEKu2ukKgiDR5TWHYwrem0cSv6Z8m");
    }

    @Override
    public List<UserRoleEntity> getUserRoles(String loginUserId) {
        List<UserRoleEntity> list = new ArrayList<>();
        list.add(new UserRoleEntity("carami", "ROLE_USER"));
        return list;
    }
}
```



### 4. 로그인 처리를 위한 컨트롤러와 뷰 작성하기

 로그인 처리를 위해 로그인 폼을 보여주는 컨트롤러 클래스

**MemberController.java**

```java
package org.edwith.webbe.securityexam.controller;

import org.edwith.webbe.securityexam.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/members")
public class MemberController {
    // 스프링 컨테이너가 생성자를 통해 자동으로 주입한다.
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/loginform")
    public String loginform(){
        return "members/loginform";
    }

    @RequestMapping("/loginerror")
    public String loginerror(@RequestParam("login_error")String loginError){
        return "members/loginerror";
    }

}
```

아이디와 암호를 입력 받는 뷰

**loginform.jsp**

```jsp
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
  <html>
    <head>
      <title>로그인 </title>
    </head>
  <body>
    <div>
      <div>
        <form method="post" action="/securityexam/authenticate">
          <div>
            <label>ID</label>
            <input type="text" name="userId">
          </div>
          <div>
            <label>암호</label>
            <input type="password" name="password">
          </div>
          <div>
            <label></label>
            <input type="submit" value="로그인">
          </div>
        </form>
      </div>
    </div>
  </body>
</html>
```

로그인 오류가 발생할 경우 보여줄 화면을 작성합니다.

**loginerror.jsp**

```jsp
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <title>로그인 오류</title>
  </head>
  <body>
    <h1>로그인 오류가 발생했습니다. id나 암호를 다시 입력해주세요.</h1>
    <a href="/securityexam/members/loginform">login</a>
  </body>
</html>
```



### 생각해보기

1. 회원 정보와 그 회원의 권한정보가 파일에 저장되어 있다면 로그인 처리를 위해서 어떤 부분을 수정해주어야 할까요?
   - 회원 정보와 그 회원의 권한 정보가 파일에 저장되어 있다면 CustomUserDetailsService의 로직을 수정해주어야 한다. 하지만 CustomUserDetailsService 로직을 보면 회원의 정보를 가져오는 로직은 MemberServiceImpl에서 구현되어 있음으로 MemberServiceImpl의 로직을 수정해주면 된다.



---

## 4) Spring Security db 정보를 이용하여 로그인하기

### 1. 데이터 베이스 모델링, 테이블 생성, 데이터 추가하기

**ddl.sql**

테이블을 생성하기 위해 아래와 같은 sql을 사용합니다. 기존에 테이블이 있을 경우 삭제합니다.

```sql
drop table if exists member_role;
drop table if exists member;


-- -----------------------------------------------------
-- Table `member`
-- -----------------------------------------------------
CREATE TABLE `member` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'member id',
  `name` VARCHAR(255) NOT NULL COMMENT 'member name',
  `password` VARCHAR(255) NOT NULL COMMENT '암호회된 password',
  `email` VARCHAR(255) NOT NULL UNIQUE COMMENT 'login id, email',
  `create_date` DATETIME NULL DEFAULT NULL COMMENT '등록일',
  `modify_date` DATETIME NULL DEFAULT NULL COMMENT '수정일',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `member_role`
-- -----------------------------------------------------
CREATE TABLE `member_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'role id',
  `member_id` INT(11) NOT NULL COMMENT 'member id fk',
  `role_name` VARCHAR(100) NOT NULL COMMENT 'role 이름 ROLE_ 로 시작하는 값이어야 한다.',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`member_id`)
  REFERENCES `member` (`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

**dml.sql**

만들어진 테이블에 회원 정보와 권한 정보를 저장합니다.

```sql
insert into member (id, name, password, email, create_date, modify_date) values ( 1, '강경미', '$2a$10$G/ADAGLU3vKBd62E6GbrgetQpEKu2ukKgiDR5TWHYwrem0cSv6Z8m', 'carami@example.com', now(), now());
insert into member (id, name, password, email, create_date, modify_date) values ( 2, '이정주', '$2a$10$G/ADAGLU3vKBd62E6GbrgetQpEKu2ukKgiDR5TWHYwrem0cSv6Z8m', 'toto@example.com', now(), now());

insert into member_role (id, member_id, role_name) values (1, 1, 'ROLE_USER');
insert into member_role (id, member_id, role_name) values (2, 1, 'ROLE_ADMIN');
insert into member_role (id, member_id, role_name) values (3, 2, 'ROLE_USER');
```



### 2. 데이터베이스 사용을 위해 pom.xml 파일 수정하기

pom.xml 파일에 Spring JDBC와 관련된 라이브러리를 추가합니다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
		http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.edwith.webbe</groupId>
    <artifactId>securityexam</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>war</packaging>

    
    <properties>
    <!-- eclipse에서 웹 어플리케이션 프로젝트 작성시 web.xml파일을 작성하지 않고 java-config로 설정할 경우 아래의 설정이 있어야 합니다.-->
        <failOnMissingWebXml>false</failOnMissingWebXml>
        <!-- spring 5.2.3이 나오는 시점에 spring security는 5.2.2가 최신버전이라서 5.2.2.RELEASE로 설정함 -->
        <spring.version>5.2.2.RELEASE</spring.version>
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
        
        <!-- MySQL Connection을 위한 JDBC Driver -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.19</version>
        </dependency>
        
		<!-- 커넥션 풀 라이브러리를 추가한다. spring boot 2의 경우는 hikariCP가 기본으로 사용된다.-->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-dbcp2</artifactId>
            <version>2.6.0</version>
        </dependency>
        
		<!-- DataSource, Transaction등을 사용하려면 추가한다. spring-tx를 자동으로 포함시킨다.-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>${spring.version}</version>
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

        <!-- Spring Security Core -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-core</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring Security Config -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-config</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring Security Web -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-web</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring Security JSP Custom Tags -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-taglibs</artifactId>
            <version>${spring.version}</version>
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



### **3. 데이터베이스 사용을 위해 설정 파일 수정하기**

기존의 스프링 설정 파일인 ApplicationConfig.java 파일에 DataSource와 Transaction에 대한 설정을 합니다.

**ApplicationConfig.java**

```java
package org.edwith.webbe.securityexam.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

// 레이어드 아키텍처에서 Controller가 사용하는 Bea들에 대해 설정을 한다.
// dao, service를 컴포넌트 스캔하여 찾도록 한다.
// 어노테이션으로 트랜잭션을 관리하기 위해 @EnableTransactionManagement를 설정하였다.
@Configuration
@ComponentScan(basePackages = {"org.edwith.webbe.securityexam.dao", "org.edwith.webbe.securityexam.service"})
@EnableTransactionManagement
public class ApplicationConfig implements TransactionManagementConfigurer {
	
	// mysql Driver 클래스를 메모리에 로드 시키기 위한 변수 지정
	private String driverClassName = "com.mysql.cj.jdbc.Driver";

	// DB와 커넥션 맺기 위한 정보
    private String url = "spring.datasource.url=jdbc:mysql://localhost:3306/reservation?serverTimezone=UTC";
    private String username = "connectuser";
    private String password = "connect123!@#";
    
    
    // 커넥션 풀을 잘 조정할 DataSource Bean 등록
    @Bean
    public DataSource dataSource() {
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName(driverClassName);
    	dataSource.setUrl(url);
    	dataSource.setUsername(username);
    	dataSource.setPassword(password);
    	
    	return dataSource;
    }
    
    // 애노테이션 기반의 트랜잭션 관리자를 설정
    @Bean
    public PlatformTransactionManager transactionManager() {
    	return new DataSourceTransactionManager(dataSource());
    }
    
	@Override
	public TransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}

}

```



### 4. 데이터베이스로부터 읽어들이기 위한 DTO와 DAO 작성하기

**회원 정보 한 건의 정보를 저장하는 Member DTO 클래스를 다음과 같이 작성합니다.**

**Member.java**

```java
package org.edwith.webbe.securityexam.dto;

import java.util.Date;

public class Member {
    private Long id;
    private String name;
    private String password;
    private String email;
    private Date createDate;
    private Date modifyDate;

    public Member() {
        createDate = new Date();
        modifyDate = new Date();
    }

    public Member(Long id, String name, String password, String email) {
        this();
        this.name = name;
        this.password = password;
        this.email = email;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
```


Member DAO에서 읽어들일 SQL정보를 가지고 있는 MemberDaoSqls클래스를 다음과 같이 작성합니다.
email정보와 일치하는 회원 정보를 읽어들이는 것을 알 수 있습니다.

**MemberDaoSqls.java**

```java
package org.edwith.webbe.securityexam.dao;

public class MemberDaoSqls {
	public static final String SELECT_ALL_BY_EMAIL = "SELECT id, name, password, email, create_date, modify_date FROM member WHERE email = :email";
}
```

MemberDao클래스를 작성합니다. 데이터베이스의 정보를 읽어 들이는 레이어에 속하기 때문에 @Component가 아니라 @Repository가 붙은 것을 확인할 수 있습니다. 설명은 주석문으로 대신합니다.

**MemberDao.java**

```java
package org.edwith.webbe.securityexam.dao;

import org.edwith.webbe.securityexam.dto.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberDao {
	private NamedParameterJdbcTemplate jdbc;
	// BeanPropertyRowMapper는 Role클래스의 프로퍼티를 보고 자동으로 칼럼과 맵핑해주는 RowMapper객체를 생성한다.
	// roleId 프로퍼티는 role_id 칼럼과 맵핑이 된다.
	// 만약 프로퍼티와 칼럼의 규칙이 맞아 떨어지지 않는다면 직접 RowMapper객체를 생성해야 한다.
	// 생성하는 방법은 아래의 rowMapper2를 참고한다.
	private RowMapper<Member> rowMapper = BeanPropertyRowMapper.newInstance(Member.class);

	public MemberDao(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public Member getMemberByEmail(String email){
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);

		return jdbc.queryForObject(MemberDaoSqls.SELECT_ALL_BY_EMAIL, map, rowMapper);
	}
}
```

회원의 권한(Role)정보를 저장하기 위한 MemberRole DTO클래스를 다음과 같이 작성합니다.

**MemberRole.java**

```java
package org.edwith.webbe.securityexam.dto;

public class MemberRole {
	private Long id;
	private Long memberId;
	private String roleName;

	public MemberRole() {
	}

	public MemberRole(Long memberId, String roleName) {
		this.memberId = memberId;
		this.roleName = roleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
```

email에 해당하는 권한 정보를 읽어들이기 위해서 member테이블과 member_role테이블을 조인(JOIN)하여 결과를 얻는 SQL을 가진 MemberRoleDaoSqls 클래스를 다음과 같이 작성합니다.

**MemberRoleDaoSqls.java**

```java
package org.edwith.webbe.securityexam.dao;

public class MemberRoleDaoSqls {
	public static final String SELECT_ALL_BY_EMAIL = "SELECT mr.id, mr.member_id, mr.role_name FROM member_role mr JOIN member m ON mr.member_id = m.id WHERE m.email = :email";
}
```

권한 정보를 읽어들이는 MemberRoleDao 클래스를 다음과 같이 작성합니다.

**MemberRoleDao.java**

```java
package org.edwith.webbe.securityexam.dao;

import org.edwith.webbe.securityexam.dto.MemberRole;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberRoleDao {
	private NamedParameterJdbcTemplate jdbc;
	// BeanPropertyRowMapper는 Role클래스의 프로퍼티를 보고 자동으로 칼럼과 맵핑해주는 RowMapper객체를 생성한다.
	// roleId 프로퍼티는 role_id 칼럼과 맵핑이 된다.
	// 만약 프로퍼티와 칼럼의 규칙이 맞아 떨어지지 않는다면 직접 RowMapper객체를 생성해야 한다.
	// 생성하는 방법은 아래의 rowMapper2를 참고한다.
	private RowMapper<MemberRole> rowMapper = BeanPropertyRowMapper.newInstance(MemberRole.class);

	public MemberRoleDao(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<MemberRole> getRolesByEmail(String email){
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);

		return jdbc.query(MemberRoleDaoSqls.SELECT_ALL_BY_EMAIL, map, rowMapper);
	}
}
```

데이터베이스 접속, MemberDao, MemberRoleDao클래스가 알맞게 동작하는지 테스트 클래스를 작성합니다.
해당 클래스를 실행했을 때 아무 문제도 발생하지 않으면 다음 단계로 진행합니다.

**MemberDaoTest.java**

```java
package org.edwith.webbe.securityexam.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import org.edwith.webbe.securityexam.config.ApplicationConfig;
import org.edwith.webbe.securityexam.dto.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class MemberDaoTest {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	MemberDao memberDao;
	
	@Autowired
	MemberRoleDao memberRoleDao;
	
	@Test
	public void configTest() throws Exception {
		// Spring Config Test를 위한 Test
	}
	
	@Test
	public void connectionTest() throws Exception {
		Connection connection = dataSource.getConnection();
		Assert.assertNotNull(connection); // Connection 객체가 Not Null인지 => 즉, Connection이 잘 되는지 테스트한다.
	}
	
	@Test
	public void getUser() throws Exception {
		Member member = memberDao.getMemberByEmail("carami@example.com");
		Assert.assertNotNull(member);
		Assert.assertEquals("강경미", member.getName());
	}
}

```

carami사용자에 대해 무조건 리턴하던 MemberServiceImpl클래스를 다음과 같이 수정합니다.

**MemberServiceImpl.java**

```java
package org.edwith.webbe.securityexam.service;

import org.edwith.webbe.securityexam.dao.MemberDao;
import org.edwith.webbe.securityexam.dao.MemberRoleDao;
import org.edwith.webbe.securityexam.dto.Member;
import org.edwith.webbe.securityexam.dto.MemberRole;
import org.edwith.webbe.securityexam.service.security.UserEntity;
import org.edwith.webbe.securityexam.service.security.UserRoleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    // 생성자에 위해 주입되는 객체이고, 해당 객체를 초기화할 필요가 이후에 없기 때문에 final로 선언하였다.
    // final로 선언하고 초기화를 안한 필드는 생성자에서 초기화를 해준다.
    private final MemberDao memberDao;
    private final MemberRoleDao memberRoleDao;

    // @Service가 붙은 객체는 스프링이 자동으로 Bean으로 생성하는데
    // 기본생성자가 없고 아래와 같이 인자를 받는 생성자만 있을 경우 자동으로 관련된 타입이 Bean으로 있을 경우 주입해서 사용하게 된다.
    public MemberServiceImpl(MemberDao memberDao, MemberRoleDao memberRoleDao) {
        this.memberDao = memberDao;
        this.memberRoleDao = memberRoleDao;
    }

    @Override
    @Transactional
    public UserEntity getUser(String loginUserId) {
        Member member = memberDao.getMemberByEmail(loginUserId);
        return new UserEntity(member.getEmail(), member.getPassword());
    }

    @Override
    @Transactional
    public List<UserRoleEntity> getUserRoles(String loginUserId) {
        List<MemberRole> memberRoles = memberRoleDao.getRolesByEmail(loginUserId);
        List<UserRoleEntity> list = new ArrayList<>();

        for(MemberRole memberRole : memberRoles) {
            list.add(new UserRoleEntity(loginUserId, memberRole.getRoleName()));
        }
        return list;
    }

}
```

### 생각해보기

- 미리 입력(insert)해 둔 정보로 회원 가입을 하였습니다. 회원 가입 후 로그인을 하려면 데이터베이스에 저장하는 기능을 구현해야 합니다. 데이터베이스에 회원 정보를 저장하려면 어떻게 해야할까요?
  - 회원 가입 페이지를 만들어 회원 정보를 데이터베이스에 저장해야 합니다. 컨트롤러에 회원 정보 생성 요청이 들어왔을 때 MemberService를 사용해서 해당 요청을 서비스 레이어로 내리고, 서비스 레이어에서 Dao를 이용해 관련 테이블에 insert 쿼리를 수행해야 합니다.



---

## 5) Spring Security를 이용한 회원 가입하기

회원 가입 요청을 처리할 Controller 수정하기

**MemberController.java**

```java
package org.edwith.webbe.securityexam.controller;

import org.edwith.webbe.securityexam.dto.Member;
import org.edwith.webbe.securityexam.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path="/members")
public class MemberController {
	// 스프링 컨테이너가 생성자를 통해 자동으로 주입한다.
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder){
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }
	
	@GetMapping("/loginform")
	public String loginform() {
		return "members/loginform";
	}
	
	@RequestMapping("/loginerror")
	public String loginerror(@RequestParam("login_error") String loginError) {
		return "members/loginerror";
	}
	
	@GetMapping("/joinform")
	public String joinform() {
		return "members/joinform";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "members/welcome";
	}
	
	@PostMapping("/join")
	public String join(@ModelAttribute Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberService.addMember(member, false);
		return "redirect:/members/welcome";
	}
	
}
```

회원 가입 로직을 처리할 MemberService 및 MemberServiceImpl 로직 수정하기

**MemberService.java**

```java
package org.edwith.webbe.securityexam.service;

import org.edwith.webbe.securityexam.dto.Member;
import org.edwith.webbe.securityexam.service.security.UserDbService;

public interface MemberService extends UserDbService {

	void addMember(Member member, boolean b);

}
```

**MemberServiceImpl.java**

```java
package org.edwith.webbe.securityexam.service;

import org.edwith.webbe.securityexam.dao.MemberDao;
import org.edwith.webbe.securityexam.dao.MemberRoleDao;
import org.edwith.webbe.securityexam.dto.Member;
import org.edwith.webbe.securityexam.dto.MemberRole;
import org.edwith.webbe.securityexam.service.security.UserEntity;
import org.edwith.webbe.securityexam.service.security.UserRoleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
	
    // 생성자에 위해 주입되는 객체이고, 해당 객체를 초기화할 필요가 이후에 없기 때문에 final로 선언하였다.
    // final로 선언하고 초기화를 안한 필드는 생성자에서 초기화를 해준다.
    private final MemberDao memberDao;
    private final MemberRoleDao memberRoleDao;

    // @Service가 붙은 객체는 스프링이 자동으로 Bean으로 생성하는데
    // 기본생성자가 없고 아래와 같이 인자를 받는 생성자만 있을 경우 자동으로 관련된 타입이 Bean으로 있을 경우 주입해서 사용하게 된다.
    public MemberServiceImpl(MemberDao memberDao, MemberRoleDao memberRoleDao) {
        this.memberDao = memberDao;
        this.memberRoleDao = memberRoleDao;
    }
    
    @Override
    @Transactional
    public UserEntity getUser(String loginUserId) {
        Member member = memberDao.getMemberByEmail(loginUserId);
        return new UserEntity(member.getEmail(), member.getPassword());
    }

    @Override
    @Transactional
    public List<UserRoleEntity> getUserRoles(String loginUserId) {
        List<MemberRole> memberRoles = memberRoleDao.getRolesByEmail(loginUserId);
        List<UserRoleEntity> list = new ArrayList<>();

        for(MemberRole memberRole : memberRoles) {
            list.add(new UserRoleEntity(loginUserId, memberRole.getRoleName()));
        }
        return list;
    }

	@Override
	@Transactional(readOnly = false)
	public void addMember(Member member, boolean admin) {
		memberDao.addMember(member);
		
		Member selectedMember = memberDao.getMemberByEmail(member.getEmail());
		Long memberId = selectedMember.getId();
		if(admin) {
			memberRoleDao.addAdminRole(memberId);
		}
		memberRoleDao.addUserRole(memberId);
	}
}
```

직접 데이터베이스와 통신할 Dao 및 Sqls 수정하기

**MemberDao.java**

```java
package org.edwith.webbe.securityexam.dao;

import org.edwith.webbe.securityexam.dto.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberDao {
	private NamedParameterJdbcTemplate jdbc;
	// BeanPropertyRowMapper는 Role클래스의 프로퍼티를 보고 자동으로 칼럼과 맵핑해주는 RowMapper객체를 생성한다.
	// roleId 프로퍼티는 role_id 칼럼과 맵핑이 된다.
	// 만약 프로퍼티와 칼럼의 규칙이 맞아 떨어지지 않는다면 직접 RowMapper객체를 생성해야 한다.
	// 생성하는 방법은 아래의 rowMapper2를 참고한다.
	private RowMapper<Member> rowMapper = BeanPropertyRowMapper.newInstance(Member.class);

	public MemberDao(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public Member getMemberByEmail(String email){
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);

		return jdbc.queryForObject(MemberDaoSqls.SELECT_ALL_BY_EMAIL, map, rowMapper);
	}

	public void addMember(Member member) {
		Map<String, Object> params = new HashMap<>();
		params.put("name", member.getName());
		params.put("password", member.getPassword());
		params.put("email", member.getEmail());
		params.put("createDate", member.getCreateDate());
		params.put("modifyDate", member.getModifyDate());
		
		// Insert Query를 위해서 update method를 사용했다.
		jdbc.update(MemberDaoSqls.INSERT_MEMBER, params);
		
	}
}
```

**MemberDaoSqls.java**

```java
package org.edwith.webbe.securityexam.dao;

public class MemberDaoSqls {
	public static final String SELECT_ALL_BY_EMAIL = "SELECT id, name, password, email, create_date, modify_date FROM member WHERE email = :email";
	public static final String INSERT_MEMBER =
			"INSERT INTO member(name, password, email, create_date, modify_date) "
			+ "VALUES (:name, :password, :email, :createDate, :modifyDate);";
}
```

**MemberRoleDao.java**

```java
package org.edwith.webbe.securityexam.dao;

import org.edwith.webbe.securityexam.dto.MemberRole;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberRoleDao {
	private NamedParameterJdbcTemplate jdbc;
	// BeanPropertyRowMapper는 Role클래스의 프로퍼티를 보고 자동으로 칼럼과 맵핑해주는 RowMapper객체를 생성한다.
	// roleId 프로퍼티는 role_id 칼럼과 맵핑이 된다.
	// 만약 프로퍼티와 칼럼의 규칙이 맞아 떨어지지 않는다면 직접 RowMapper객체를 생성해야 한다.
	// 생성하는 방법은 아래의 rowMapper2를 참고한다.
	private RowMapper<MemberRole> rowMapper = BeanPropertyRowMapper.newInstance(MemberRole.class);

	public MemberRoleDao(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<MemberRole> getRolesByEmail(String email){
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);

		return jdbc.query(MemberRoleDaoSqls.SELECT_ALL_BY_EMAIL, map, rowMapper);
	}

	public void addAdminRole(Long memberId) {
		Map<String, Object> params = Collections.singletonMap("memberId", memberId);
		jdbc.update(MemberRoleDaoSqls.INSERT_ADMIN_ROLE, params);
	}

	public void addUserRole(Long memberId) {
		Map<String, Object> params = Collections.singletonMap("memberId", memberId);
		jdbc.update(MemberRoleDaoSqls.INSERT_USER_ROLE, params);
	}
}
```

**MemberRoleDaoSqls.java**

```java
package org.edwith.webbe.securityexam.dao;

public class MemberRoleDaoSqls {
	public static final String SELECT_ALL_BY_EMAIL = "SELECT mr.id, mr.member_id, mr.role_name FROM member_role mr JOIN member m ON mr.member_id = m.id WHERE m.email = :email";
	public static final String INSERT_ADMIN_ROLE = 
			"INSERT INTO member_role(member_id, role_name) "
			+ "VALUES (:memberId, \"ROLE_ADMIN\");";
	public static final String INSERT_USER_ROLE = 
			"INSERT INTO member_role(member_id, role_name) "
			+ "VALUES (:memberId, \"ROLE_USER\");";
}
```

회원가입을 하기 위한 회원가입 jsp 작성하기

**joinform.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
</head>
<body>
	<form method="post" action="/securityexam/members/join">
	  <div>
	    <label>이름</label>
	    <input type="text" name="name">
	  </div>
	  <div>
	    <label>비밀번호</label>
	    <input type="password" name="password">
	  </div>
	  <div>
	    <label>이메일</label>
	    <input type="text" name="email">
	  </div>
	  <div>
	    <label></label>
	    <input type="submit" value="회원가입">
	  </div>
	</form>
</body>
</html>
```

회원 가입이 완료된 사용자에게 환영 메시지를 보여주는 jsp 작성하기

**welcome.jsp**

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
	안녕하세요 여러분 security exam 사이트에 오신걸 환영합니다.
</body>
</html>
```



---

## 6) 로그인한 사용자 정보 조회하기

### 1. 로그인한 사용자의 정보를 보여주는 웹 패이지 작성하기

사용자가 로그인을 한 상태라면, 스프링 MVC는 컨트롤러 메소드에 회원정보를 저장하고 있는 Principal객체를 파라미터로 받을 수 있습니다.

public String memberInfo(Principal principal, ModelMap modelMap) 메소드는 로그인한 회원정보를 받기 위해 Principal을 파라미터로 선언하고 있습니다.

String loginId = principal.getName();

위의 코드를 통해 로그인 아이디를 구할 수 있습니다. 로그인 아이디를 구했다면, 해당 아이디를 이용해 데이터베이스로부터 회원 정보를 읽어 들여 뷰에게 전달 할 수 있을 것입니다.

**MemberController.java**

```java
package org.edwith.webbe.securityexam.controller;

import org.edwith.webbe.securityexam.dto.Member;
import org.edwith.webbe.securityexam.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(path = "/members")
public class MemberController {
    // 스프링 컨테이너가 생성자를 통해 자동으로 주입한다.
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder){
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/loginform")
    public String loginform(){
        return "members/loginform";
    }

    @GetMapping("/loginerror")
    public String loginerror(@RequestParam("login_error")String loginError){
        return "members/loginerror";
    }

    @GetMapping("/joinform")
    public String joinform(){
        return "members/joinform";
    }

    // 사용자가 입력한 name, email, password가 member에 저장된다.
    @PostMapping("/join")
    public String join(@ModelAttribute Member member){
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberService.addMember(member, false);
        return "redirect:/members/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "members/welcome";
    }

    @GetMapping("/memberinfo")
    public String memberInfo(Principal principal, ModelMap modelMap){
        String loginId = principal.getName();
        Member member = memberService.getMemberByEmail(loginId);
        modelMap.addAttribute("member", member);

        return "members/memberinfo";
    }
}
```

회원정보를 보여주는 jsp파일

**members/memberinfo.jsp**

```jsp
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
  <head>
    <title>회원 가입폼 </title>
  </head>
  <body>
    <div>
      <div>
        <h1>회원정보</h1>
        <p>로그인한 회원 정보를 표기합니다.</p>
      </div>

        <div>
          <label>id</label>
          <p>${member.id}</p>
        </div>
        <div>
          <label>이름</label>
          <p>${member.name}</p>
        </div>
        <div>
          <label>암호</label>
          <p>${member.password}</p>
        </div>
        <div>
          <label>등록일</label>
          <p>${member.createDate}</p>
        </div>
        <div>
          <label>수정일</label>
          <p>${member.modifyDate}</p>
        </div>

    </div>
  </body>
</html>
```



### 이슈

- <%@ page isELIgnored="false" %>
  - isELIgnored는 Default로 false이지만, 해당 jsp에는 적용이 안되더라... 그래서 직접 EL을 사용하겠다는 태그를 추가했다.



### 생각해보기

암호는 암호화되어 있더라도 외부에 유출이 되면 안됩니다. 위의 예제에서 암호 부분이 ***** 로 출력되도록 하려면 어떻게 해야 할까요?

- jsp 에게 모델 객체를 넘겨주기 전에 모델 객체의 password를 인코딩 값 대신 "*****..." 의 문자열로 바꾸어서 전달한다.
- JavaScript를 활용해서 인코딩 값 대신 "..."를 출력한다.















