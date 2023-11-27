package net.youssfi.bankaccountservice.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class InterceptorOpenFeign implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Principal principal =(Principal) authentication.getPrincipal();
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) principal;
        AccessToken token = keycloakPrincipal.getKeycloakSecurityContext().getToken();
        requestTemplate.header("Authorization","Bearer "+token);
    }
}
