package com.t3h.ecommerce.service.Impl;

import com.t3h.ecommerce.dto.request.admin_product.ProductAdmin;
import com.t3h.ecommerce.dto.request.admin_product.ProductAdminAddRequest;
import com.t3h.ecommerce.dto.request.admin_product.ProductAdminDTO;
import com.t3h.ecommerce.dto.request.admin_product.ProductAdminRequest;

import com.t3h.ecommerce.dto.request.shop_product.ShopProduct;
import com.t3h.ecommerce.dto.request.shop_product.ShopProductDTO;
 
import com.t3h.ecommerce.dto.request.home_product.ProductHomeDTO;
 
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.entities.product.*;
import com.t3h.ecommerce.repositories.*;
import com.t3h.ecommerce.service.ProductService;
import com.t3h.ecommerce.utils.ExcelUtils;
import com.t3h.ecommerce.utils.ExportConfig;
import com.t3h.ecommerce.utils.FileFactory;
import com.t3h.ecommerce.utils.ImportConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {


    private final ProductRepository repository;


    private final CategoryRepository categoryRepository;


    private final ColorRepository colorRepository;


    private final SizeRepository sizeRepository;


    private final DiscountRepository discountRepository;


    private final ImageRepository imageRepository;

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public BaseResponse<?> findProduct(ProductAdminRequest request) {

        if(request == null) return  BaseResponse.builder().message("request not found!").status(HttpStatus.BAD_REQUEST.value()).build();

        try{
            Pageable pageable = PageRequest.of(request.getPageRequest().getPage()-1, request.getPageRequest().getPageSize(),
                    Sort.Direction.fromString(request.getPageRequest().getCondition().equals("asc")? "asc": "desc"),
                    (request.getPageRequest().getSortBy().isEmpty() || request.getPageRequest().getSortBy().equals("createdDate"))?
                    "createdDate": request.getPageRequest().getSortBy());

            Page<ProductAdmin> page = repository.findProduct(pageable,
                    request.getProductName(), request.getQuantity(), request.getCost(), request.getCategoryId(),
                    request.getFilterDate().getCreatedDateStart(), request.getFilterDate().getCreatedDateEnd(),
                    request.getFilterDate().getUpdatedDateStart(), request.getFilterDate().getUpdatedDateEnd());

            if(page == null) return  BaseResponse.builder().message("request not found!").status(HttpStatus.BAD_REQUEST.value()).build();

            List<ProductAdminDTO>  list = page.getContent().stream().map(ProductAdminDTO::new).collect(Collectors.toList());

            return BaseResponse.builder().data(list).message("success").status(HttpStatus.OK.value()).totalRecords(page.getTotalElements()).build();

        }catch (Exception ex){
            log.error("can not call repository in product service");
            return BaseResponse.builder().message("request bad").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public BaseResponse<?> deleteProduct(String Ids) {
        if(Ids == null || Ids.isEmpty()) return BaseResponse.builder().message("delete fail").status(HttpStatus.BAD_REQUEST.value()).build();

        try{
            String[] arr = Ids.split(",");
            List<Long> ids = new ArrayList<>();
            for(int i=0 ;i< arr.length; i++){
                ids.add(Long.parseLong(arr[i]));
            }
            repository.deleteProduct(ids);

            imageRepository.deleteImageByListProduct(ids);
            return BaseResponse.builder().message("delete success").status(HttpStatus.OK.value()).build();
        } catch (Exception ex){
            log.error(ex.getMessage());
            return BaseResponse.builder().message("delete fail").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }


    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public BaseResponse<?> findProductForShop(ProductAdminRequest request) {
        if (request == null){
            return BaseResponse.builder().message("Get request bad").status(HttpStatus.BAD_REQUEST.value()).build();
        }

        try {
            Pageable pageable = PageRequest.of(request.getPageRequest().getPage()-1,request.getPageRequest().getPageSize(),
                    Sort.Direction.fromString(request.getPageRequest().getCondition().equals("asc")? "asc": "desc"),
                    "cost");

            Page<ShopProduct> page = repository.findProductForShopPage(pageable,
                    request.getProductName(),request.getCost(),request.getCategoryId());

            if (page == null){
                return BaseResponse.builder().message("Get request bad").status(HttpStatus.BAD_REQUEST.value()).build();
            }

            List<ShopProductDTO> list = page.getContent().stream().map(ShopProductDTO::new).collect(Collectors.toList());
            return BaseResponse.builder().status(200).message("GET SUCCESS").data(list).totalRecords(page.getTotalElements()).build();

        }catch (Exception e){
            return BaseResponse.builder().message("Get request bad").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }


    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public BaseResponse<?> createOrEditProduct(ProductAdminAddRequest request) {
        try {
            boolean existColor =  colorRepository.existsById(request.getColorId());
            boolean existSize = sizeRepository.existsById(request.getSizeId());
            boolean existDiscount = sizeRepository.existsById(request.getDiscountId());
            boolean existCategory = categoryRepository.existsById(request.getCategoryId());

            if(!existColor || !existCategory || !existSize || !existDiscount){
                return BaseResponse.builder().message("request failed because not exist properties").status(HttpStatus.BAD_REQUEST.value()).build();
            }

            Color color = colorRepository.getReferenceById(request.getColorId());
            Size size = sizeRepository.getReferenceById(request.getSizeId());
            Discount discount = discountRepository.getReferenceById(request.getDiscountId());
            Category category = categoryRepository.getReferenceById(request.getCategoryId());

            Product product;
            if(request.getId() != null && request.getId() == 0){
                 product = new Product(new Date().getTime(), new Date().getTime(),
                        request.getProductName(), request.getShortDescription(), request.getDescription(),
                        request.getUrlImg().get(0), request.getCost(), request.getQuantity(),
                        category, size, discount, color);
                log.warn("da khoi tao doi tuong THEM MOI thanh cong");
            }
            else{
                 product = repository.getReferenceById(request.getId());
                if(product == null){
                    return BaseResponse.builder().message("product not found").status(HttpStatus.NOT_FOUND.value()).build();
                }
                product.setProductName(request.getProductName());
                product.setUpdatedDate(new Date().getTime());
                product.setCost(request.getCost());
                product.setColor(color);
                product.setCategory(category);
                product.setDiscount(discount);
                product.setQuantity(request.getQuantity());
                product.setDescription(request.getDescription());
                product.setShortDescription(request.getShortDescription());
                imageRepository.deleteByProductId(request.getId());
                log.warn("da khoi tao doi tuong CHINH SUA thanh cong");
            }

            List<Image> images = request.getUrlImg().stream()
                    .map(url -> {
                        Image image = new Image(new Date().getTime(), new Date().getTime(), url);
                        image.setProduct(product);
                        return image;
                    })
                    .collect(Collectors.toList());

            product.setImages(images);

            repository.save(product);
            return BaseResponse.builder().message("create success").status(HttpStatus.OK.value()).build();

        }catch (Exception e){
            log.error("call repository failed!");
            log.error(e.getMessage());
            return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public BaseResponse<?> findProductById(String id) {
        if (id == null || id.isEmpty()) {
            return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        try {
            Product product = repository.getReferenceById(Long.parseLong(id.trim()));
            List<Image> images = imageRepository.findByProductId(Long.parseLong(id.trim()));
            List<String> list = new ArrayList<>();
            if (images != null) {
                list = images.stream().map(item -> {
                    String url = item.getUrl();
                    return url;
                }).collect(Collectors.toList());
            }
            if (product != null && list != null && !list.isEmpty()) {
                ProductAdminAddRequest response = ProductAdminAddRequest.builder().productName(product.getProductName())
                        .quantity(product.getQuantity()).cost(product.getCost()).shortDescription(product.getShortDescription())
                        .description(product.getDescription()).colorId(product.getColor().getId())
                        .discountId(product.getDiscount().getId()).sizeId(product.getSize().getId()).categoryId(product.getCategory().getId())
                        .urlImg(list).build();
                return BaseResponse.builder().data(response).message("request success").status(HttpStatus.OK.value()).build();
            }

            return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
        } catch (Exception ex) {
            return BaseResponse.builder().message("request failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }
    }

    @Override
    public BaseResponse<?> findProductForHome(com.t3h.ecommerce.dto.request.PageRequest pageRequest) {
        if(pageRequest.getPage() <=0 || pageRequest.getPageSize() <= 0){
            return BaseResponse.builder().message("request bad").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        try {
            Pageable pageable = PageRequest.of(pageRequest.getPage()-1,pageRequest.getPageSize());

            Page<Product> page = repository.findProductForHome(pageable);
            if (page==null) return BaseResponse.builder().message("request bad").status(HttpStatus.BAD_REQUEST.value()).build();

            List<ProductHomeDTO> list = page.getContent().stream().map(ProductHomeDTO::new).collect(Collectors.toList());

            return BaseResponse.builder().totalRecords(page.getTotalElements()).data(list).message("request success").status(200).build();
        }catch (Exception e){
            return BaseResponse.builder().message("request bad").status(HttpStatus.BAD_REQUEST.value()).build();

        }
    }

    @Override
    public ResponseEntity<Resource> exportExcelProduct() {
        try{
            Page<ProductAdmin> page = repository.findProduct(
                    PageRequest.of(0, 1000000),
                    "", 0l, 0.0, 0l, 0l, 0l,
                    0l, 0l
            );
            if(CollectionUtils.isEmpty(page.getContent())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<ProductAdminDTO> listProducts = page.getContent().stream()
                    .map(ProductAdminDTO::new).collect(Collectors.toList());

            if(!CollectionUtils.isEmpty(listProducts)){
                String fileName = "product_export"+".xlsx";
                ByteArrayInputStream in = ExcelUtils.export(listProducts, ExportConfig.productExport, fileName);
                InputStreamResource inputStreamResource = new InputStreamResource(in);
                return ResponseEntity.ok()
                        .header(
                                HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                        )
                        .contentType(
                                MediaType.parseMediaType("application/vnd.ms-excel; charset=UTF-8")
                        )
                        .body(inputStreamResource);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e) {
            log.info("export fail!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public BaseResponse<?> importExcelProduct(MultipartFile importFile) {
        Workbook workbook = FileFactory.getWorkbookStream(importFile);
        List<ProductAdminDTO> productAdminDTOS = ExcelUtils.getImportData(workbook, ImportConfig.productImport);

        if(!CollectionUtils.isEmpty(productAdminDTOS)){
            saveData(productAdminDTOS);
            return BaseResponse.builder().message("import success").status(HttpStatus.OK.value()).build();
        }
        else{
            return BaseResponse.builder().message("import failed").status(HttpStatus.BAD_REQUEST.value()).build();
        }

    }



    private void saveData(List<ProductAdminDTO> productAdminDTOS) {
        for(ProductAdminDTO productAdminDTO: productAdminDTOS){
            Category category = categoryRepository.getReferenceById(productAdminDTO.getCategoryId());

            Color color = colorRepository.getReferenceById(productAdminDTO.getColorId());
            Size size = sizeRepository.getReferenceById(productAdminDTO.getSizeId());
            Discount discount = discountRepository.getReferenceById(productAdminDTO.getDiscountId());

            if(category == null || color == null || size == null || discount == null){
                log.info("CATEGORY NOT FOUND!");
                return ;
            }
            Product product = Product.builder().productName(productAdminDTO.getProductName())
                    .shortDescription(productAdminDTO.getShortDescription()).cost(productAdminDTO.getCost())
                    .quantity(productAdminDTO.getQuantity()).build();
            product.setCreatedDate(productAdminDTO.getCreatedDate());
            product.setUpdatedDate(productAdminDTO.getUpdatedDate());
            product.setCategory(category);

            product.setColor(color);
            product.setSize(size);
            product.setDiscount(discount);

            repository.save(product);
        }
    }

 
}
