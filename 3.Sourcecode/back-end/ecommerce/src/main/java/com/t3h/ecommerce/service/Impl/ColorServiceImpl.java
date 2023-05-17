package com.t3h.ecommerce.service.Impl;

import com.t3h.ecommerce.dto.request.admin_color.ColorAdminAdd;
import com.t3h.ecommerce.dto.request.admin_color.ColorAdminDTO;
import com.t3h.ecommerce.dto.request.admin_color.ColorAdminRequest;
import com.t3h.ecommerce.entities.product.Color;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.repositories.ColorRepository;
import com.t3h.ecommerce.repositories.ProductRepository;
import com.t3h.ecommerce.service.ColorService;
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
public class ColorServiceImpl implements ColorService {

    private final ColorRepository repository;

    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    @Override
    public BaseResponse<?> getAllColor() {
        try{
            Iterable<Color> iterable= repository.findAll();
            List<Color> list = new ArrayList<>();
            iterable.forEach(x -> list.add(x));
            List<ColorAdminDTO> result = list.stream().map(ColorAdminDTO::new).collect(Collectors.toList());

            return BaseResponse.builder().data(result).message("request success").status(HttpStatus.OK.value()).build();

        }catch (Exception ex){
            return BaseResponse.builder().message("delete fail").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    public BaseResponse<?> findColor(ColorAdminRequest request) {
        if(request == null) return  BaseResponse.builder().message("request not found!").status(HttpStatus.BAD_REQUEST.value()).build();

        try{
            Pageable pageable = PageRequest.of(request.getPageRequest().getPage()-1, request.getPageRequest().getPageSize(),
                    Sort.Direction.fromString(request.getPageRequest().getCondition().equals("asc")? "asc": "desc"),
                    (request.getPageRequest().getSortBy().isEmpty() || request.getPageRequest().getSortBy().equals("createdDate"))?
                            "createdDate": request.getPageRequest().getSortBy());

            Page<Color> page = repository.findColor(pageable, request.getColorName(), request.getColorCode(),
                    request.getFilterDate().getCreatedDateStart(), request.getFilterDate().getCreatedDateEnd(),
                    request.getFilterDate().getUpdatedDateStart(), request.getFilterDate().getUpdatedDateEnd());

            if(page == null)  return BaseResponse.builder().message("request bad").status(HttpStatus.BAD_REQUEST.value()).build();

            List<ColorAdminDTO> list = page.getContent().stream().map(ColorAdminDTO::new).collect(Collectors.toList());
            return BaseResponse.builder().status(HttpStatus.OK.value()).message("success").data(list).totalRecords(page.getTotalElements()).build();

        }catch (Exception e){
            log.error("can not call repository");
            return BaseResponse.builder().message("request bad").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    @Transactional
    public BaseResponse<?> deleteColor(String ids) {
        try{
            List<Long> idss = new ArrayList<>();
            String[] arr = ids.trim().split(",");
            for(int i=0; i< arr.length; i++){
                idss.add(Long.parseLong(arr[i]));
            }
            if(idss == null)  return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();

            repository.deleteColor(idss);
            productRepository.deleteProductByColor(idss);

            return  BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();

        }catch (Exception e){
            log.info("can not call repository");
            return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    @Transactional
    public BaseResponse<?> createOrEdit(ColorAdminAdd request) {
        Color color = null;
        if(request.getId() == 0){
            color = new Color(new Date().getTime(), new Date().getTime(), request.getColorName(), request.getColorCode());
        }
        else{
            color = new Color(new Date().getTime(), new Date().getTime(), request.getId(),
                    request.getColorName(), request.getColorCode());
        }
        if(color != null){
            repository.save(color);
            return  BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();
        }
        return  BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
    }


}
