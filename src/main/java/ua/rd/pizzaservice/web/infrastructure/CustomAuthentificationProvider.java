package ua.rd.pizzaservice.web.infrastructure;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;


public class CustomAuthentificationProvider implements AuthenticationProvider {
    @Override
    //vernut role, имя, желательно без пароля
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if ("user".equals(username) && "password".equals(password)) {
        Authentication auth=new UsernamePasswordAuthenticationToken
                (username,"", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_ADMIN")));
            return auth;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return true;
    }
}
