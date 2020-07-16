package com.jg.FindFafik.security;

import com.jg.FindFafik.persistence.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MyAuthenticationService {

    public String getUserName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof  MyUserPrincipal) {
            final MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
            final String userName = principal.getUsername();
            return userName;
        }
        return "Gościu";
    }

    public User getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof  MyUserPrincipal) {
            final MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
            final User user = principal.getUser();
            return user;
        }
        return new User();
    }

}
