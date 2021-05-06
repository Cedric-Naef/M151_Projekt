package ch.bbzw.m151.swshop.auth;

import ch.bbzw.m151.swshop.model.UserGroup;
import ch.bbzw.m151.swshop.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PgAuthenticationProvider implements AuthenticationProvider {

    private final LoginService loginService;

    @Autowired
    public PgAuthenticationProvider(final LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        final String username = authentication.getName();
        final String userPassword = authentication.getCredentials().toString();

        if (loginService.login(username, userPassword).isPresent()) {
            final UserGroup userGroup = loginService.login(username, userPassword).get();
            return new UsernamePasswordAuthenticationToken(
                    username,
                    userPassword,
                    Collections.singletonList(new SimpleGrantedAuthority(userGroup.toString())));
        }
        return null;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
