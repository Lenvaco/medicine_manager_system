package com.medicine.manager.config;

import com.medicine.manager.service.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author lenvaco
 * @date 2019/9/22 23:27
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.auth.path}")
    private String loginPath;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 自定义基于JWT的安全过滤器
     */
    @Autowired
    private JwtAuthorizationTokenFilter authenticationTokenFilter;

    @Autowired
    private JwtUserService jwtUserService;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // Remove the ROLE_ prefix
        return new GrantedAuthorityDefaults("");
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		//同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
		corsConfiguration.addAllowedOrigin("*");
		//header，允许哪些header，
		corsConfiguration.addAllowedHeader("*");
		//允许的请求方法，PSOT、GET等
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setAllowCredentials(true);
		//配置允许跨域访问的url
		((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration);
		return source;
	}

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
				.cors().and()
                // 禁用 CSRF
                .csrf().disable()
                // 授权异常
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 不创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // 过滤请求
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).anonymous()

                .antMatchers( HttpMethod.POST,"/auth/"+loginPath).anonymous()
                .antMatchers("/auth/captcha").anonymous()
                .antMatchers("/api/medicine").anonymous()
               // swagger start
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                // swagger end
                .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()
                 // 所有请求都需要认证
                .anyRequest().authenticated()
                // 防止iframe 造成跨域
                .and().headers().frameOptions().disable();

        httpSecurity
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
