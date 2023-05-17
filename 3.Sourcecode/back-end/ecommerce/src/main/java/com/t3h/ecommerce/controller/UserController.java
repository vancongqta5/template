package com.t3h.ecommerce.controller;

import com.t3h.ecommerce.dto.request.admin_product.ProductAdminDTO;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.dto.response.ResponseMessage;
import com.t3h.ecommerce.entities.core.User;
import com.t3h.ecommerce.pojo.dto.user.UserDTO;
import com.t3h.ecommerce.repositories.IUserRepository;
import com.t3h.ecommerce.service.IUserService;
import com.t3h.ecommerce.utils.ExcelUtils;
import com.t3h.ecommerce.utils.ExportConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("admin/users")
    public ResponseEntity<?> getListUsers(){
        return  userService.getListUserDTO();
    }

    @GetMapping("find/user")
    public ResponseEntity<?> findUser(@RequestParam("textSearch") String textSearch){
        return userService.findUser(textSearch);
    }


    @GetMapping("find/users")
    public ResponseEntity<?> findUserByUserName(@RequestParam("username") String username){
        try{
            User user = userService.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found!"));
            UserDTO userDTO = mapper.map(user, UserDTO.class);

            return new ResponseEntity<>(userDTO, HttpStatus.OK);

        } catch (UsernameNotFoundException ex){
            return new ResponseEntity<>(new ResponseMessage("Username not found"), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("status/user")
    public ResponseEntity<?> changeStatus(@RequestBody @Valid UserDTO userDTO){
        try{
            User user = userService.findByUsername(userDTO.getUsername()).orElseThrow(()-> new UsernameNotFoundException("Username not found!"));
            user.setStatus(userDTO.getStatus());
            userService.save(user);

            return  userService.getListUserDTO();
        }catch (Exception ex){

            return new ResponseEntity<>(new ResponseMessage("Username not found"), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("user/import")
    public ResponseEntity<BaseResponse> importCustomerData(@RequestParam("file")MultipartFile importFile){
        BaseResponse baseResponse = userService.importCustomerData(importFile);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }



}