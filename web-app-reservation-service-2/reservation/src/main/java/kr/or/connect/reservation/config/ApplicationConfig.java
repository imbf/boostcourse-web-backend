package kr.or.connect.reservation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({DBConfig.class})
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"kr.or.connect.reservation.service, kr.or.connect.reservation.dao"})
public class ApplicationConfig {
}
