package com.t3h.ecommerce.repositories;

import com.t3h.ecommerce.dto.request.admin_customer.CustomerAdmin;
import com.t3h.ecommerce.entities.core.RoleName;
import com.t3h.ecommerce.entities.core.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<User, Long> {

    @Query("select u.id as id, u.fullName as fullName, u.username as userName, u.status as status, u.address as address, u.email as email," +
            "u.phoneNumber as phoneNumber, u.gender as gender, u.createdDate as createdDate, u.updatedDate as updatedDate" +
            " from User u join u.roles r on r.roleName =:roleName and " +
            "u.phoneNumber like concat('%', :phoneNumber, '%') and " +
            "(u.status =:status or :status=-1) and u.fullName like concat('%', :fullName, '%')")
    Page<CustomerAdmin> findCustomer(Pageable pageable,
                                     @Param("roleName") RoleName roleType,
                                     @Param("phoneNumber") String phoneNumber,
                                     @Param("status") Integer status,
                                     @Param("fullName") String fullName);


    @Modifying
    @Query("UPDATE User  u set u.status =:status, u.updatedDate =:updatedDate where u.id in :ids")
    void changeStatus( @Param("status") Integer status,
                       @Param("ids") List<Long> ids,
                       @Param("updatedDate") Long updatedDate);
}
