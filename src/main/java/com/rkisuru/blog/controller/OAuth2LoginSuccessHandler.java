package com.rkisuru.blog.controller;

import com.rkisuru.blog.entity.User;
import com.rkisuru.blog.service.UserService;
import com.rkisuru.blog.type.RegistrationSource;
import com.rkisuru.blog.type.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User principal = (DefaultOAuth2User) oauthToken.getPrincipal();
        Map<String, Object> attributes = principal.getAttributes();
        String email = attributes.getOrDefault("email", "").toString();
        String name = attributes.getOrDefault("name", "").toString();
        String sub = attributes.getOrDefault("sub", "").toString();

        userService.findByEmail(email)
                .ifPresentOrElse(user -> {
                    DefaultOAuth2User userPrincipal = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())), attributes, "id");
                    Authentication newAuthentication = new OAuth2AuthenticationToken(userPrincipal, List.of(new SimpleGrantedAuthority(user.getRole().name())), ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId());
                    SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                }, ()-> {
                    User user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setSub(sub);
                    user.setRole(Role.ROLE_USER);
                    user.setRegistrationSource(RegistrationSource.OAUTH2);
                    userService.saveUser(user);

                    DefaultOAuth2User userPrincipal = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())), attributes, "id");
                    Authentication newAuthentication = new OAuth2AuthenticationToken(userPrincipal, List.of(new SimpleGrantedAuthority(user.getRole().name())), ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId());
                    SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                });

        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
