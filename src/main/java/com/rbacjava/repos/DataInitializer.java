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
        Permission read = permRepo.save(new Permission("USER_READ"));
        Permission write = permRepo.save(new Permission("USER_WRITE"));

        Role user = new Role("ROLE_USER");
        user.getPermissions().add(read);

        Role admin = new Role("ROLE_ADMIN");
        admin.getPermissions().addAll(Set.of(read, write));

        roleRepo.save(user);
        roleRepo.save(admin);
    }
}
