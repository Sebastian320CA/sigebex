package com.usta.sigebex.security;

import com.usta.sigebex.entities.RolEntity;
import com.usta.sigebex.entities.UserEntity;
import com.usta.sigebex.models.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userDao.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("The user does not exist");
        }

        if (!user.isUserState()) {
            throw new UsernameNotFoundException("User is inactive");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRoles(user.getRol())
        );
    }

    private Collection<? extends GrantedAuthority> mapRoles(RolEntity role) {
        return List.of(new SimpleGrantedAuthority(role.getNameRol()));
    }
}
