package com.t3h.ecommerce.service.Impl;

import com.t3h.ecommerce.dto.request.admin_discount.DiscountAdminAddDTO;
import com.t3h.ecommerce.dto.request.admin_discount.DiscountAdminDTO;
import com.t3h.ecommerce.dto.request.admin_discount.DiscountAdminRequest;
import com.t3h.ecommerce.entities.product.Discount;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.repositories.DiscountRepository;
import com.t3h.ecommerce.repositories.ProductRepository;
import com.t3h.ecommerce.service.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class DiscountServiceImpl implements DiscountService {
     private final DiscountRepository repository;

     private final ProductRepository productRepository;

     private final ModelMapper mapper;

    @Override
    public BaseResponse<?> getAllDiscount() {
        try{
            Iterable<Discount> iterable= repository.findAll();
            List<Discount> list = new ArrayList<>();
            iterable.forEach(x -> list.add(x));
            List<DiscountAdminDTO> result = list.stream().map(DiscountAdminDTO::new).collect(Collectors.toList());

            return BaseResponse.builder().data(result).message("request success").status(HttpStatus.OK.value()).build();

        }catch (Exception ex){
            return BaseResponse.builder().message("delete fail").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    public BaseResponse<?> findDiscount(DiscountAdminRequest request) {
        if(request == null) return  BaseResponse.builder().message("request not found!").status(HttpStatus.BAD_REQUEST.value()).build();
        try {
            Pageable pageable = PageRequest.of(request.getPageRequest().getPage()-1, request.getPageRequest().getPageSize(),
                    Sort.Direction.fromString(request.getPageRequest().getCondition().equals("asc")? "asc": "desc"),
                    (request.getPageRequest().getSortBy().isEmpty() || request.getPageRequest().getSortBy().equals("createdDate"))?
                            "createdDate": request.getPageRequest().getSortBy());

            Page<Discount> page = repository.findDiscount(pageable,
                    request.getDiscountName(), request.getDiscountPercent(),
                    request.getFilterDate().getCreatedDateStart(), request.getFilterDate().getCreatedDateEnd(),
                    request.getFilterDate().getUpdatedDateStart(), request.getFilterDate().getUpdatedDateEnd());
            if(page == null){
                return  BaseResponse.builder().message("request not found!").status(HttpStatus.BAD_REQUEST.value()).build();
            }

            List<DiscountAdminDTO> res = page.getContent().stream().map(DiscountAdminDTO::new).collect(Collectors.toList());
            return BaseResponse.builder().data(res).message("success").status(HttpStatus.OK.value()).totalRecords(page.getTotalElements()).build();

        }catch (Exception e){
            log.info("can not call repository");
            return  BaseResponse.builder().message("request not found!").status(HttpStatus.BAD_REQUEST.value()).build();
        }

    }

    @Override
    @Transactional
    public BaseResponse<?> deleteDiscount(String ids) {
        try{
            List<Long> idss = new ArrayList<>();
            String[] arr = ids.trim().split(",");
            for(int i=0; i< arr.length; i++){
                idss.add(Long.parseLong(arr[i]));
            }
            if(idss == null)  return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
            repository.deleteDiscount(idss);
            productRepository.deleteProductByDiscount(idss);
            return BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();
        }catch (Exception e){
            log.info(e.getMessage());
            return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    @Transactional
    public BaseResponse<?> createOrEdit(DiscountAdminAddDTO req) {
        Discount discount = null;
        if(req.getId() == 0){
            discount = new Discount(new Date().getTime(), new Date().getTime(),
                    req.getDiscountName(), req.getDiscountPercent());
        }
        else{
            discount = new Discount(new Date().getTime(),new Date().getTime(),req.getId(),
                    req.getDiscountName(), req.getDiscountPercent());
        }

        if(discount == null){
            return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        repository.save(discount);
        return BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();
    }
}
