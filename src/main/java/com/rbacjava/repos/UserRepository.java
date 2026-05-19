package com.rbacjava.repos;

import com.rbacjava.models.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByUsername(String name);
    public boolean existsByEmail(String email);

    // findByEmail → Spring Data genera la query automáticamente solo con el nombre del método.
    // Trae el User, pero los roles y permisos se cargan en queries separadas (N+1).
    // A veces Hibernate los carga fuera de la sesión activa y devuelve colecciones vacías.
    // Trae problemas con getAuthorities
    public Optional<User> findByEmail(String email);

    // Trae el User, sus roles y sus permisos en UNA SOLA query con joins.
    // Garantiza que todo esté cargado dentro de la misma sesión de Hibernate.
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.permissions WHERE u.email = :email")
    public Optional<User> findByEmailWithRoles(@Param("email") String email);
}
