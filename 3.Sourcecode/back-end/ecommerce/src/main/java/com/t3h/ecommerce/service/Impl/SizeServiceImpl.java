package com.t3h.ecommerce.service.Impl;


import com.t3h.ecommerce.dto.request.admin_size.SizeAdminAddDTO;
import com.t3h.ecommerce.dto.request.admin_size.SizeAdminDTO;
import com.t3h.ecommerce.dto.request.admin_size.SizeAdminRequest;
import com.t3h.ecommerce.entities.product.Size;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.repositories.ProductRepository;
import com.t3h.ecommerce.repositories.SizeRepository;
import com.t3h.ecommerce.service.SizeService;
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
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class SizeServiceImpl implements SizeService {

    private final SizeRepository repository;

    private final ProductRepository productRepository;

    private final ModelMapper mapper;


    @Override
    public BaseResponse<?> getAllSize() {
        try{
            Iterable<Size> iterable= repository.findAll();
            List<Size> list = new ArrayList<>();
            iterable.forEach(x -> list.add(x));
            List<SizeAdminDTO> result = list.stream().map(SizeAdminDTO::new).collect(Collectors.toList());

            return BaseResponse.builder().data(result).message("request success").status(HttpStatus.OK.value()).build();

        }catch (Exception ex){
            return BaseResponse.builder().message("delete fail").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    public BaseResponse<?> findSize(SizeAdminRequest request) {
        if(request == null) return  BaseResponse.builder().message("request not found!").status(HttpStatus.BAD_REQUEST.value()).build();

        try{
            Pageable pageable = PageRequest.of(request.getPageRequest().getPage()-1, request.getPageRequest().getPageSize(),
                    Sort.Direction.fromString(request.getPageRequest().getCondition().equals("asc")? "asc": "desc"),
                    (request.getPageRequest().getSortBy().isEmpty() || request.getPageRequest().getSortBy().equals("createdDate"))?
                            "createdDate": request.getPageRequest().getSortBy());

            Page<Size> page = repository.findSize(pageable, request.getSizeName(), request.getSizeCode(),
                    request.getFilterDate().getCreatedDateStart(), request.getFilterDate().getCreatedDateEnd(),
                    request.getFilterDate().getUpdatedDateStart(), request.getFilterDate().getUpdatedDateEnd());

            if(page != null){
                List<SizeAdminDTO> res = page.getContent().stream().map(SizeAdminDTO::new).collect(Collectors.toList());
                return BaseResponse.builder().message("success").status(HttpStatus.OK.value())
                        .totalRecords(page.getTotalElements()).data(res).build();
            }
            return BaseResponse.builder().message("request bad").status(HttpStatus.BAD_REQUEST.value()).build();

        }catch (Exception exception){
            log.error("can not call repository");
            return BaseResponse.builder().message("request bad").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    @Transactional
    public BaseResponse<?> deleteSize(String ids) {
        try{
            List<Long> idss = new ArrayList<>();
            String[] arr = ids.trim().split(",");
            for(int i=0; i< arr.length; i++){
                idss.add(Long.parseLong(arr[i]));
            }
            if(idss == null)  return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();

            repository.deleteSize(idss);
            productRepository.deleteProductBySize(idss);
            return  BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();

        }catch (Exception e){
            log.info(e.getMessage());
            return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    @Transactional
    public BaseResponse<?> createOrEdit(SizeAdminAddDTO request) {
        Size size = null;
        if(request.getId() == 0){
            size = new Size(new Date().getTime(), new Date().getTime(),
                    request.getSizeCode(), request.getSizeName());
        }
        else{
            size = new Size(new Date().getTime(), new Date().getTime(), request.getId(),
                    request.getSizeCode(), request.getSizeName());
        }
        if(size != null){
            repository.save(size);
            return  BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();

        }
        return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
    }


}
