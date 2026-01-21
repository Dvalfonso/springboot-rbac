package com.rbacjava.repos;

import com.rbacjava.models.dao.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
