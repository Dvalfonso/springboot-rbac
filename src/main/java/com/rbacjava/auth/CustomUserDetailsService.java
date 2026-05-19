package com.rbacjava.auth;

import com.rbacjava.models.dao.User;
import com.rbacjava.models.dao.UserDetailsImplementation;
import com.rbacjava.repos.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmailWithRoles(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("=== USER CARGADO: " + user.getEmail());
        System.out.println("=== ROLES AL CARGAR: " + user.getRoles());
        return new UserDetailsImplementation(user);
    }
}
