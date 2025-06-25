package JPA.Spring.Api_Livrarias.config;


import JPA.Spring.Api_Livrarias.Security.JwtCustomAutehticationFilter;
import JPA.Spring.Api_Livrarias.Security.LoginSocialSuccessHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain
            (HttpSecurity http,
             LoginSocialSuccessHandle loginSocialSuccessHandle,
             JwtCustomAutehticationFilter jwtCustomAutehticationFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(cofigurer -> {
                    cofigurer.loginPage("/login");
                })
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();
                    authorize.anyRequest().authenticated();
                } )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler(loginSocialSuccessHandle))
                .oauth2ResourceServer( oauth2RS -> oauth2RS.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAutehticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(
                "/v2/api-docs/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**",
                "/actuator/**"
        );
    }


    //Configura o prefixo da role
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return  new GrantedAuthorityDefaults("");
    }

// Configura  no token jwt o no prefixo scope
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var authorithsConverter = new JwtGrantedAuthoritiesConverter();
        authorithsConverter.setAuthorityPrefix("");

        var jwtConveter = new JwtAuthenticationConverter();

        jwtConveter.setJwtGrantedAuthoritiesConverter(authorithsConverter);

        return  jwtConveter;
    }



}
