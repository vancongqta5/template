package com.t3h.ecommerce.repositories;


import com.t3h.ecommerce.entities.core.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name); // Tìm kiếm User có tồn tai trong DB hay không?
    Boolean existsByUsername(String username);  // Kiểm tra xem Username cos tồn tại trong DB không?
    Boolean existsByEmail(String email); //  Kiểm tra xem email cos tồn tại trong DB không?


    @Query(value = "SELECT\n" +
            "    U.*\n" +
            "FROM user U INNER JOIN user_role ur on U.id = ur.user_id\n" +
            "INNER JOIN ROLE R ON ur.role_id = R.id\n" +
            "AND  R.role_name = 'USER'", nativeQuery = true)
    List<User> getUsers();

    @Query(value = "SELECT\n" +
            "    U.*\n" +
            "FROM user U INNER JOIN user_role ur on U.id = ur.user_id\n" +
            "INNER JOIN ROLE R ON ur.role_id = R.id\n" +
            "AND  R.role_name = 'USER'\n" +
            "AND LOWER(U.full_name) LIKE CONCAT('%', LOWER(:textSearch), '%')", nativeQuery = true)
    List<User> findUser(@Param("textSearch") String textSearch);
}
