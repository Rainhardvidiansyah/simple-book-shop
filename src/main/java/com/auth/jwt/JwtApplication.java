package com.auth.jwt;

import com.auth.jwt.helper.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.Duration;

@SpringBootApplication
@EnableWebMvc
@EnableJpaAuditing(auditorAwareRef = "aware")
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder
				.setConnectTimeout(Duration.ofMillis(3000))
				.setReadTimeout(Duration.ofMillis(3000))
				.build();
	}

	@Bean
	public InternalResourceViewResolver defaultViewResolver() {
		return new InternalResourceViewResolver();
	}

	@Bean
	public AuditorAware<String> aware() {
		return new AuditorAwareImpl();
	}

}
