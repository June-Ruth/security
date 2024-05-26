package com.training.security.resourceserver.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class LoginController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/home")
    public String home() {
        return "Home of the application";
    }

    @GetMapping("/admin")
    public String welcomeAdmin() {
        return "Welcome Admin";
    }

    @GetMapping("/editor")
    public String welcomeEditor() {
        return "Welcome Editor";
    }

    @GetMapping("/user")
    public String welcomeUser() {
        return "Welcome User";
    }

    @GetMapping("/default")
    public String getUserInfo(Principal user, @AuthenticationPrincipal OidcUser oidcUser) {
        StringBuffer userInfo = new StringBuffer();
        if (user instanceof UsernamePasswordAuthenticationToken) {
            userInfo.append(getUsernamePasswordLoginInfo(user));
        } else if (user instanceof OAuth2AuthenticationToken) {
            userInfo.append(getOauth2LoginInfo(user, oidcUser));
        }
        return userInfo.toString();
    }

    private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
        StringBuffer usernameInfo = new StringBuffer();

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;

        if (token.isAuthenticated()) {
            User u = (User) token.getPrincipal();
            usernameInfo.append("Welcome ").append(u.getUsername());
        } else {
            usernameInfo.append("NA");
        }

        return usernameInfo;
    }

    private StringBuffer getOauth2LoginInfo(Principal user, OidcUser oidcUser) {
        StringBuffer protectedInfo = new StringBuffer();

        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);

        if (authToken.isAuthenticated()) {
            Map<String, Object> userAttributes = authToken.getPrincipal().getAttributes();
            protectedInfo.append("Welcome ").append(userAttributes.get("name")).append("<br><br>");
            protectedInfo.append("e-mail: ").append(userAttributes.get("email")).append("<br><br>");

            if (oidcUser != null) {
                OidcIdToken idToken = oidcUser.getIdToken();
                if (idToken != null) {
                    protectedInfo.append("Token mapped values <br><br>");
                    Map<String, Object> claims = idToken.getClaims();
                    for (String key : claims.keySet()) {
                        protectedInfo.append("  ").append(key).append(": ").append(claims.get(key)).append("<br>");
                    }
                }
            }

        } else {
            protectedInfo.append("NA");
        }

        return protectedInfo;
    }

}
