package com.t3h.ecommerce.service.Impl;


import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.entities.core.User;
import com.t3h.ecommerce.entities.product.*;
import com.t3h.ecommerce.pojo.dto.product_detail.*;
import com.t3h.ecommerce.repositories.*;
import com.t3h.ecommerce.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository repository;

    private final ColorRepository colorRepository;

    private final SizeRepository sizeRepository;

    private final ImageRepository imageRepository;

    private final ReviewRepository reviewRepository;

    private final IUserRepository userRepository;

    private final ProductRepository productRepository;


    @Override
    public BaseResponse<?> findProductDetail(String id) {
        if(id == null || id.isEmpty()){
            return BaseResponse.builder().message("request invalid").status(HttpStatus.BAD_REQUEST.value()).build();
        }

        try{
            ProductDetail productDetail = repository.findProduct(Long.parseLong(id.trim()));
            List<Color> colorList = colorRepository.findColorByProductId(Long.parseLong(id.trim()));
            List<Size> sizeList = sizeRepository.findSizeByProductId(Long.parseLong(id.trim()));
            List<Image> imageList = imageRepository.findByProductId(Long.parseLong(id.trim()));

            if(productDetail == null || colorList == null || sizeList == null || imageList == null){
                return BaseResponse.builder().message("product not found").status(HttpStatus.BAD_REQUEST.value()).build();
            }

            List<ColorDTO> colorDTOList = colorList.stream().map(ColorDTO::new).collect(Collectors.toList());
            List<SizeDTO> sizeDTOList = sizeList.stream().map(SizeDTO::new).collect(Collectors.toList());
            List<ImageDTO> imageDTOList = imageList.stream().map(ImageDTO::new).collect(Collectors.toList());

            ProductDetailDTO productDetailDTO =  ProductDetailDTO.builder().productName(productDetail.getProductName())
                    .description(productDetail.getDescription()).cost(productDetail.getCost()).categoryName(productDetail.getCategoryName())
                    .id(productDetail.getId()).shortDescription(productDetail.getShortDescription())
                    .colorDTOList(colorDTOList).sizeDTOList(sizeDTOList).imageDTOList(imageDTOList).build();
            return BaseResponse.builder().data(productDetailDTO).message("success").status(HttpStatus.OK.value()).build();
        }catch (Exception e){
            return BaseResponse.builder().message("request invalid").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }


    @Override
    public BaseResponse<?> findReview(ProductReviewReq req){
        if(req.getId() == null){
            return BaseResponse.builder().message("request invalid").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        try{
            List<ProductReviewDTO> result = new ArrayList<>();

            Pageable pageable = PageRequest.of(req.getPageRequest().getPage()-1, req.getPageRequest().getPageSize(),
                    Sort.Direction.fromString("desc"), "createdDate" );

            Page<Review> reviewList = reviewRepository.findReviewByProductId( pageable,req.getId());

            if(reviewList == null || reviewList.isEmpty()){
                return BaseResponse.builder().message("request invalid").status(HttpStatus.BAD_REQUEST.value()).build();
            }

            for(int i=0; i< reviewList.getContent().size(); i++){
                Long userId = reviewList.getContent().get(i).getUserId();
                Optional<User> user = userRepository.findById(userId);
                if(user.isPresent()){
                    ProductReviewDTO reviewDTO = ProductReviewDTO.builder().comment(reviewList.getContent().get(i).getComment())
                            .rating( reviewList.getContent().get(i).getRating()).createdDate(reviewList.getContent().get(i).getCreatedDate())
                            .updatedDate(reviewList.getContent().get(i).getUpdatedDate())
                            .fullName(user.get().getFullName()).urlAvt(user.get().getAvatar()).build();
                    result.add(reviewDTO);
                }
            }
            if(result != null){
                return BaseResponse.builder().status(HttpStatus.OK.value()).data(result).message("success").totalRecords(reviewList.getTotalElements()).build();
            }
            return BaseResponse.builder().status(HttpStatus.OK.value()).message("result empty!").build();
        }catch (Exception e){
            return BaseResponse.builder().message("request invalid").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }


    @Override
    @Transactional
    public BaseResponse<?> createReview(ProductReviewAdd req){
        if(req.getProductId() == null ||  req.getUserId() == null){
            return BaseResponse.builder().message("request invalid").status(HttpStatus.BAD_REQUEST.value()).build();
        }
       try{
           Optional<Product> product = productRepository.findById(req.getProductId());
           Optional<User> user = userRepository.findById(req.getUserId());

           if(product == null || user == null){
               return BaseResponse.builder().message("user or product not found!").status(HttpStatus.BAD_REQUEST.value()).build();
           }
           Review review = new Review(new Date().getTime(), new Date().getTime(), 0.0f, req.getComment(), req.getUserId());
           review.setProduct(product.get());
           reviewRepository.save(review);
           return BaseResponse.builder().message("success").status(HttpStatus.OK.value()).build();
       }catch (Exception e){
           return BaseResponse.builder().message("request invalid").status(HttpStatus.BAD_REQUEST.value()).build();
       }
    }
}
