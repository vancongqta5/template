package com.t3h.ecommerce.service;

import com.t3h.ecommerce.entities.core.Role;
import com.t3h.ecommerce.entities.core.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByRoleName(RoleName name);
}
