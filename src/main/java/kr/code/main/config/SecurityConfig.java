package kr.code.main.config;

import kr.code.main.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

	private final UserService userService;

	@Value("${jwt.token.secret}")
	private String secretKey;
	
	//보안 무시 설정
	@Bean
	public WebSecurityCustomizer configure() {
		return (web)-> web.ignoring()
				.antMatchers( "/**")
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	

//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//		return http.httpBasic( basic -> basic.disable() )
//				.csrf( csrf -> csrf.disable() )
//				.cors( cors -> cors.disable() )
//				.authorizeHttpRequests()
//				.antMatchers("/user/join", "/user/login", "/index").permitAll()
//				.antMatchers("/api/v1/**", "/customer/**", "/user/admin", "/user/authModify").authenticated()
//				.and()
//				.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt 사용하는 경우 씀
//				.and()
//				.addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
//				.build();
//	}
}
