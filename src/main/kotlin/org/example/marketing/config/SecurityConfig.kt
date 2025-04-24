package org.example.marketing.config

import org.example.marketing.security.CustomAuthenticationEntryPoint
import org.example.marketing.security.JwtFilterChain
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtFilterChain: JwtFilterChain,
        customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
    ): SecurityFilterChain {
        http
            .exceptionHandling { config ->
                config.authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/test/**").permitAll()
                    .requestMatchers("/valid-token").permitAll()
            }
            .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}