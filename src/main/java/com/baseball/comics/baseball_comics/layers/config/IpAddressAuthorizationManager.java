package com.baseball.comics.baseball_comics.layers.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import java.util.function.Supplier;

public class IpAddressAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final IpAddressMatcher ipAddressMatcher;

    public IpAddressAuthorizationManager(String ipAddress) {
        this.ipAddressMatcher = new IpAddressMatcher(ipAddress);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        boolean granted = ipAddressMatcher.matches(request);
        return new AuthorizationDecision(granted);
    }
}
