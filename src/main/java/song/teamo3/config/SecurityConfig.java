package song.teamo3.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import song.teamo3.security.authentication.handler.LoginFailureHandler;
import song.teamo3.security.authentication.handler.LoginSuccessHandler;
import song.teamo3.security.authentication.userdetails.service.UserDetailsServiceImpl;
import song.teamo3.security.filter.JwtFilter;
import song.teamo3.security.filter.UsernamePasswordLoginFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration configuration) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/chatroom/**").authenticated()
                        .requestMatchers("/comment/**").authenticated()
                        .requestMatchers("/favorite/**").authenticated()
                        .requestMatchers("/post/**").authenticated()
                        .requestMatchers("/user/userInfo/**", "/user/delete").authenticated()
                        .requestMatchers("/project/create/**", "/project/modify/**", "/project/delete/**").authenticated()
                        .requestMatchers("/study/create", "/study/modify/**", "/study/delete/**", "/study/apply/**", "/study/exit/**", "/study/*/applicationList", "/study/changeStatus/**", "/study/bumpUp/**", "/study/createChatRoom/**").authenticated()
                        .requestMatchers("/studyApplication/**").authenticated()
                        .requestMatchers("/studyCalendar/**").authenticated()
                        .requestMatchers("/studyMember/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin(login -> login
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginPage("/user/login")
//                        .successHandler(loginSuccessHandler(objectMapper))
//                        .failureHandler(loginFailureHandler(objectMapper))
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            String referer = request.getHeader("Referer");
                            response.sendRedirect(referer != null ? referer : "/");
                        })
                        .permitAll())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(new UsernamePasswordLoginFilter(authenticationManager(configuration), loginSuccessHandler(objectMapper), loginFailureHandler(objectMapper)), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new JwtFilter(userDetailsService), UsernamePasswordLoginFilter.class)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(ObjectMapper objectMapper) {
        return new LoginSuccessHandler(objectMapper);
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler(ObjectMapper objectMapper) {
        return new LoginFailureHandler(objectMapper);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
