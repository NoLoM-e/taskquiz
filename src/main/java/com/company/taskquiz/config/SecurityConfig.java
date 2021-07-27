package com.company.taskquiz.config;

import com.company.taskquiz.Routs;
import com.company.taskquiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final MyBasicAuthenticationEntryPoint authenticationEntryPoint;


    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder, MyBasicAuthenticationEntryPoint authenticationEntryPoint) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder.encode("user1Pass"))
                .authorities("ROLE_USER");
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // open static resources
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // open swagger-ui
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                // allow user registration and seeing all users
                .antMatchers(HttpMethod.POST, Routs.USERS).permitAll()
                .antMatchers(HttpMethod.GET, Routs.USERS).permitAll()
                // admin can register new admins
                .antMatchers(HttpMethod.POST,  Routs.USERS + "/admins").hasAuthority("ADMIN")
                // only admin can patch users
                .antMatchers(HttpMethod.PATCH, Routs.USERS + "/{id:\\d+}/**").hasAuthority("ADMIN")
                // regular users can view basic user info for other users
                .antMatchers(HttpMethod.GET,Routs.USERS + "/{id:\\d+}").authenticated()
                // authorised users have access to courses
                .antMatchers(HttpMethod.GET, Routs.COURSES).authenticated()
                .antMatchers(HttpMethod.POST, Routs.COURSES).authenticated()
                .antMatchers(HttpMethod.PUT, Routs.COURSES).authenticated()
                .antMatchers(HttpMethod.GET,Routs.COURSES + "/{id:\\d+}").authenticated()
                .antMatchers(HttpMethod.PATCH,Routs.COURSES + "/{id:\\d+}").authenticated()
                // only admin can delete course
                .antMatchers(HttpMethod.DELETE,Routs.COURSES + "/{id:\\d+}").hasAuthority("ADMIN")
                // everyone has access to leaderboard
                .antMatchers(Routs.LEADERS).permitAll()
                // admin can use Actuator endpoints
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority("ADMIN")
                // by default, require authentication
                .anyRequest().authenticated()
                .and().httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                // for unauthorized requests return 401
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                // allow cross-origin requests for all endpoints
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable();
                // this disables session creation on Spring Security
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


}
