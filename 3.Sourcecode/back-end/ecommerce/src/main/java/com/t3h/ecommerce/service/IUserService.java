package com.t3h.ecommerce.service;

import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.entities.core.User;
import com.t3h.ecommerce.pojo.dto.user.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String name); // Tìm kiếm User có tồn tai trong DB hay không?
    Boolean existsByUsername(String username);  // Kiểm tra xem Username cos tồn tại trong DB không?
    Boolean existsByEmail(String email); //  Kiểm tra xem email cos tồn tại trong DB không?


    User save(User user);

    ResponseEntity<?> getListUserDTO();


    ResponseEntity<?> findUser(String textSearch);

    BaseResponse importCustomerData(MultipartFile importFile);
}
