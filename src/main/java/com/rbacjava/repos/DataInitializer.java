package com.rbacjava.repos;

import com.rbacjava.models.dao.Permission;
import com.rbacjava.models.dao.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final PermissionRepository permRepo;

    public DataInitializer(RoleRepository roleRepo, PermissionRepository permRepo) {
        this.roleRepo = roleRepo;
        this.permRepo = permRepo;
    }

    @Override
    public void run(String... args) {
        Permission read = permRepo.findByName("USER_READ")
                .orElseGet(() -> permRepo.save(new Permission("USER_READ")));
        Permission write = permRepo.findByName("USER_WRITE")
                .orElseGet(() -> permRepo.save(new Permission("USER_WRITE")));

        Role user = roleRepo.findByName("ROLE_USER")
                .orElseGet(() -> new Role("ROLE_USER"));

        user.getPermissions().clear();
        user.getPermissions().add(read);

        Role admin = roleRepo.findByName("ROLE_ADMIN")
                .orElseGet(() -> new Role("ROLE_ADMIN"));

        admin.getPermissions().clear();
        admin.getPermissions().addAll(Set.of(read, write));

        roleRepo.save(user);
        roleRepo.save(admin);
    }
}
