package com.t3h.ecommerce.repositories;

import com.t3h.ecommerce.entities.core.Role;
import com.t3h.ecommerce.entities.core.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName name);
}
