package kr.code.main.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
	
	
	//보안 무시 설정
	@Bean
	public WebSecurityCustomizer configure() {
		return (web)-> web.ignoring()
				.antMatchers( "/**")
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	

}
