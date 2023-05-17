package com.t3h.ecommerce.service.Impl;


import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.entities.core.User;
import com.t3h.ecommerce.entities.order.OrderDetail;
import com.t3h.ecommerce.entities.order.Orders;
import com.t3h.ecommerce.entities.order.PaymentType;
import com.t3h.ecommerce.entities.order.Revenue;
import com.t3h.ecommerce.entities.product.Product;
import com.t3h.ecommerce.pojo.dto.cart.CarrModelRequest;
import com.t3h.ecommerce.pojo.dto.cart.CartDB;
import com.t3h.ecommerce.pojo.dto.cart.CartDTO;
import com.t3h.ecommerce.repositories.OrderDetailRepository;
import com.t3h.ecommerce.repositories.OrderRepository;
import com.t3h.ecommerce.repositories.ProductRepository;
import com.t3h.ecommerce.repositories.RevenueRepository;
import com.t3h.ecommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository repository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final RevenueRepository revenueRepository;

    @Override
    public BaseResponse<?> findAllCard(com.t3h.ecommerce.dto.request.PageRequest request) {

        try {
            Pageable pageable = PageRequest.of(request.getPage() -1, request.getPageSize());
            Page<CartDB> page = repository.findAllCart(pageable);
            if(page == null) {
                return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
            }
            List<CartDTO> res = page.getContent().stream().map(CartDTO::new).collect(Collectors.toList());
            res.forEach(x ->{
                x.setCost(x.getCost() * x.getQuantity());
            });

            return BaseResponse.builder().data(res).totalRecords(page.getTotalElements())
                    .message("success").status(HttpStatus.OK.value()).build();

        }catch (Exception e){
            log.error(e.getMessage());
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    public BaseResponse<?> findMoney(String id, String count) {
        if(id.isEmpty() || count.isEmpty()){
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        try {
            Product product = productRepository.getReferenceById(Long.parseLong(id.trim()));

            if(product == null){
                return BaseResponse.builder().message("failed").status(HttpStatus.NOT_FOUND.value()).build();
            }

            return BaseResponse.builder().data(product.getCost()* Long.parseLong(count.trim()))
                    .message("success").status(HttpStatus.OK.value()).build();

        }catch (Exception e){
            log.error(e.getMessage());
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    public BaseResponse<?> delete(String id) {
        if(id.isEmpty()){
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        try{
            repository.deleteByProductId(Long.parseLong(id.trim()));
            return BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();

        }catch (Exception e){
            log.error(e.getMessage());
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    public BaseResponse<?> create(String id) {
        if(id.isEmpty()){
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        try {
            Product product = productRepository.getReferenceById(Long.parseLong(id.trim()));

            if(product == null){
                return BaseResponse.builder().message("failed").status(HttpStatus.NOT_FOUND.value()).build();
            }
            Orders orders = orderRepository.getReferenceById(1l);
            Revenue revenue = revenueRepository.getReferenceById(1l);

            OrderDetail orderDetail = repository.findByProductId(product.getId());
            OrderDetail orderDetail1 = new OrderDetail();
            if(orderDetail != null){
                orderDetail1.setId(orderDetail.getId());
                orderDetail1.setProduct(product);
                orderDetail1.setQuantity(orderDetail.getQuantity()+1);
                orderDetail1.setOrders(orders);
                orderDetail1.setRevenue(revenue);

                repository.save(orderDetail1);
            }
            else{
                orderDetail1.setProduct(product);
                orderDetail1.setQuantity(1l);
                orderDetail1.setOrders(orders);
                orderDetail1.setRevenue(revenue);
                repository.save(orderDetail1);
            }
            return BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();

        }catch (Exception e){
            log.error(e.getMessage());
            return BaseResponse.builder().message("failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }
}
