package com.t3h.ecommerce.service.Impl;

import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.dto.response.ResponseMessage;
import com.t3h.ecommerce.entities.core.User;
import com.t3h.ecommerce.pojo.dto.user.UserDTO;
import com.t3h.ecommerce.pojo.dto.user.UserResponse;
import com.t3h.ecommerce.repositories.IUserRepository;
import com.t3h.ecommerce.service.IUserService;
import com.t3h.ecommerce.utils.ExcelUtils;
import com.t3h.ecommerce.utils.FileFactory;
import com.t3h.ecommerce.utils.ImportConfig;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository repository;

    @Override
    public Optional<User> findByUsername(String name) {
        return repository.findByUsername(name);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public ResponseEntity<?> getListUserDTO() {
        List<User> userList = repository.getUsers();
        if(!userList.isEmpty() || userList !=  null){
            List<UserDTO>userDTOList =  userList.stream().map(UserDTO::new).collect(Collectors.toList());
            return new ResponseEntity<>(new UserResponse(userDTOList, "success", HttpStatus.OK.value()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ResponseMessage("not success"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> findUser(String textSearch) {
        List<User> userList = repository.findUser(textSearch);
        if(!userList.isEmpty() && userList !=  null){
            List<UserDTO>userDTOList =  userList.stream().map(UserDTO::new).collect(Collectors.toList());
            return new ResponseEntity<>(new UserResponse(userDTOList, "success", HttpStatus.OK.value()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ResponseMessage("not success"), HttpStatus.OK);
        }
    }

    @Override
    public BaseResponse importCustomerData(MultipartFile importFile) {
        BaseResponse baseResponse = new BaseResponse();
        Workbook workbook = FileFactory.getWorkbookStream(importFile);

        List<UserDTO> userDTOList = ExcelUtils.getImportData(workbook, ImportConfig.customerImport);

        if(!CollectionUtils.isEmpty(userDTOList)){
            saveData(userDTOList);
           baseResponse.builder().message("import success").status(HttpStatus.OK.value()).build();
        }
        else{
            baseResponse.builder().message("import failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }

        return baseResponse;
    }


    @Transactional
    void saveData(List<UserDTO> userDTOList) {
        for(UserDTO userDTO: userDTOList){
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setFullName(userDTO.getFullName());
            user.setStatus(userDTO.getStatus());
            user.setPassword("lns0000001");

            repository.save(user);
        }
    }
}
