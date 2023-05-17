package com.t3h.ecommerce.service.Impl;

import com.t3h.ecommerce.entities.core.Role;
import com.t3h.ecommerce.entities.core.RoleName;
import com.t3h.ecommerce.repositories.IRoleRepository;
import com.t3h.ecommerce.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {


    private final IRoleRepository repository;

    @Override
    public Optional<Role> findByRoleName(RoleName name) {
        return repository.findByRoleName(name);
    }
}
