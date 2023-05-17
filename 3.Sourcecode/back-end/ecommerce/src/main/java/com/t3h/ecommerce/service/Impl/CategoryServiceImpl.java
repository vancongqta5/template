package com.t3h.ecommerce.service.Impl;


import com.t3h.ecommerce.dto.request.admin_category.CategoryAddDTO;
import com.t3h.ecommerce.dto.request.admin_category.CategoryAdminRequest;
import com.t3h.ecommerce.dto.request.admin_category.CategoryDTO;
import com.t3h.ecommerce.entities.product.Category;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.repositories.CategoryRepository;
import com.t3h.ecommerce.repositories.ProductRepository;
import com.t3h.ecommerce.service.CategoryService;
import com.t3h.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {



    private final CategoryRepository repository;

    private final ProductRepository productRepository;

    @Override
    public BaseResponse<?> getAllCategory() {
        try{
            Iterable<Category> iterable= repository.findAll();
            List<Category> list = new ArrayList<>();
            iterable.forEach(x -> list.add(x));
            List<CategoryDTO> result = list.stream().map(CategoryDTO::new).collect(Collectors.toList());

            return BaseResponse.builder().data(result).message("request success").status(HttpStatus.OK.value()).build();

        }catch (Exception ex){
            return BaseResponse.builder().message("delete fail").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    public BaseResponse<?> findCategory(CategoryAdminRequest request) {
        if(request.getPageRequest().getPage() <= 0 || request.getPageRequest().getPageSize() <=0){
            return BaseResponse.builder().message("page invalid").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        try{
            Pageable pageable = PageRequest.of(request.getPageRequest().getPage()-1, request.getPageRequest().getPageSize(),
                    Sort.Direction.fromString(request.getPageRequest().getCondition().equals("asc")? "asc": "desc"),
                    (request.getPageRequest().getSortBy().isEmpty() || request.getPageRequest().getSortBy().equals("createdDate"))?
                            "createdDate": request.getPageRequest().getSortBy());
            Page<Category> page = repository.findCategory(
                    pageable, request.getCategoryName(), request.getDescription(),
                    request.getFilterDate().getCreatedDateStart(), request.getFilterDate().getCreatedDateEnd(),
                    request.getFilterDate().getUpdatedDateStart(), request.getFilterDate().getUpdatedDateEnd()
            );
            List<CategoryDTO> result = new ArrayList<>();
            if(page != null){
                result = page.getContent().stream().map(CategoryDTO::new).collect(Collectors.toList());
                return BaseResponse.builder().data(result).message("success").totalRecords(page.getTotalElements())
                        .status(HttpStatus.OK.value()).build();
            }
            else{
                return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
            }

        }catch (Exception e){
            log.info(e.getMessage());
            return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    @Transactional
    public BaseResponse<?> deleteCategory(String ids) {
        try{
            List<Long> idss = new ArrayList<>();
            String[] arr = ids.trim().split(",");
            for(int i=0; i< arr.length; i++){
                idss.add(Long.parseLong(arr[i]));
            }
            if(idss == null)  return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
            repository.deleteCategory(idss);
            productRepository.deleteProductByCategory(idss);
            return BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();

        }catch (Exception e){
            return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    @Transactional
    public BaseResponse<?> createOrEdit(CategoryAddDTO request) {
        Category category = null;
        if(request.getId() == 0){
            category = new Category(new Date().getTime(), new Date().getTime(), request.getCategoryName(), request.getDescription());
        }
        else{
            category = new Category(request.getId(), new Date().getTime(),
                    new Date().getTime(), request.getCategoryName(), request.getDescription());
        }

        if(category != null){
            repository.save(category);
            return BaseResponse.builder().status(HttpStatus.OK.value()).message("success").build();
        }
        return BaseResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("failed").build();
    }
}
