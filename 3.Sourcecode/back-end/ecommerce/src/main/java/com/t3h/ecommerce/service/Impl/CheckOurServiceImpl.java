package com.t3h.ecommerce.service.Impl;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.entities.order.Revenue;
import com.t3h.ecommerce.entities.product.Category;
import com.t3h.ecommerce.entities.product.Product;
import com.t3h.ecommerce.pojo.dto.checkout.CheckOutResponse;
import com.t3h.ecommerce.pojo.dto.checkout.CheckoutDTO;
import com.t3h.ecommerce.pojo.dto.checkout.CheckoutRequest;
import com.t3h.ecommerce.pojo.dto.revenue.RevenueRequestDTO;
import com.t3h.ecommerce.repositories.CategoryRepository;
import com.t3h.ecommerce.repositories.ProductRepository;
import com.t3h.ecommerce.repositories.RevenueRepository;
import com.t3h.ecommerce.service.CheckOutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckOurServiceImpl implements CheckOutService {

    private final ProductRepository repository;

    private final CategoryRepository categoryRepository;


    private final RevenueRepository revenueRepository;


    @Override
    public BaseResponse<?> getAllProduct(CheckoutRequest request) {
        if(request== null){
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        try {
            List<Long> ids;
            ids = request.getRequest().stream().map(
                    x -> x.getProductId()
            ).collect(Collectors.toList());
            if(ids == null){
                return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
            }
            List<Product> list = repository.getAllProduct(ids);
            CheckOutResponse response = new CheckOutResponse();

            if(list == null){
                return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
            }
            for(int i=0 ;i< list.size(); i++){
               CheckoutDTO checkoutDTO  = new CheckoutDTO();

               checkoutDTO.setProductName(list.get(i).getProductName());
               checkoutDTO.setCount(request.getRequest().get(i).getCount());
               checkoutDTO.setTotalMoneySingle(
                       list.get(i).getCost()
               );
               response.getProducts().add(checkoutDTO);
           }
           if(response == null){
               return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
           }
           Double totalMoney = response.getProducts().stream().mapToDouble(x -> x.getTotalMoneySingle()*x.getCount()).sum();
           response.setTotalMany(totalMoney);

           return BaseResponse.builder().data(response).message("success").status(HttpStatus.OK.value()).build();
        }catch (Exception e){
            log.error(e.getMessage());
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    @Transactional
    public BaseResponse<?> addRevenue(RevenueRequestDTO requestDTO) {
        if(requestDTO == null){
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }

        try {
            List<Revenue> list = new ArrayList<>();
            for(int i= 0; i < requestDTO.getRequest().size(); i++){
                Product product = repository.getReferenceById(requestDTO.getRequest().get(i).getProductId());
                if(product == null){
                    return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
                }
                Category category = categoryRepository.getReferenceById(product.getCategory().getId());
                Revenue revenue1 = revenueRepository.findRevenueByProductId(requestDTO.getRequest().get(i).getProductId());
                Revenue revenue = new Revenue();
                if(revenue1 != null){
                     revenue = new Revenue( revenue1.getId(),new Date().getTime(), new Date().getTime(),
                            (long) (requestDTO.getRequest().get(i).getCount() * product.getCost()), product.getId(), category.getId(), category.getCategoryName(),
                            product.getProductName(), product.getQuantity(), requestDTO.getRequest().get(i).getCount()+ revenue1.getQuantitySold(),
                            product.getQuantity() - (requestDTO.getRequest().get(i).getCount() + revenue1.getQuantitySold()) );
                }
                else {
                     revenue = new Revenue(new Date().getTime(), new Date().getTime(),
                            (long) (requestDTO.getRequest().get(i).getCount() * product.getCost()), product.getId(), category.getId(), category.getCategoryName(),
                            product.getProductName(), product.getQuantity(), requestDTO.getRequest().get(i).getCount(),
                            product.getQuantity() - requestDTO.getRequest().get(i).getCount());
                }
                if(revenue != null) {
                    revenueRepository.save(revenue);
                }
                else{
                    return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
                }
            }
            return BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();

        }catch (Exception e){
            log.error(e.getMessage());
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }
}
