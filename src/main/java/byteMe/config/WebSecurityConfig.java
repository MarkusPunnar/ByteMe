package byteMe.config;


import byteMe.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final AuthService authService;

    @Autowired
    public WebSecurityConfig(AuthService authService) {
        this.authService = authService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().defaultsDisabled().cacheControl();
        http.authorizeRequests().antMatchers("/", "/register", "/auth/*", "/about", "/tutorial", "/sitemap", "/loginform",
                "/fonts/*", "/scripts/*", "/css/*", "/images/*").permitAll().anyRequest()
                .authenticated().and().oauth2Login().successHandler(new ByteMeAuthenticationSuccessHandler())
                .defaultSuccessUrl("/auth/redirect").loginPage("/loginform")
                .and().logout().logoutSuccessUrl("/").permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/images/*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/images/**")
                .addResourceLocations("classpath:/static/images/")
                .setCachePeriod(3600 * 24);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoggingInterceptor(authService));
    }
}
