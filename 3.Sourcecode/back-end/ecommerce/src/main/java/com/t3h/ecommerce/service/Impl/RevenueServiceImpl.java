package com.t3h.ecommerce.service.Impl;

import com.t3h.ecommerce.dto.request.admin_revenue.AdminRevenueDTO;
import com.t3h.ecommerce.dto.request.admin_revenue.AdminRevenueReq;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.entities.order.Revenue;
import com.t3h.ecommerce.pojo.dto.revenue.RevenueRequestDTO;
import com.t3h.ecommerce.repositories.RevenueRepository;
import com.t3h.ecommerce.service.RevenueService;
import com.t3h.ecommerce.utils.ExcelUtils;
import com.t3h.ecommerce.utils.ExportConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class RevenueServiceImpl implements RevenueService {

    private final RevenueRepository repository;

    @Override
    public BaseResponse<?> findRevenue(AdminRevenueReq req) {
        try {
            Pageable pageable = PageRequest.of(req.getPageRequest().getPage()-1, req.getPageRequest().getPageSize(),
                    Sort.Direction.fromString(req.getPageRequest().getCondition().equals("asc")? "asc": "desc"),
                    (req.getPageRequest().getSortBy().isEmpty() || req.getPageRequest().getSortBy().equals("updatedDate"))?
                            "updatedDate": req.getPageRequest().getSortBy()
                    );

            int checkIds  = (req.getIdsCategory().isEmpty() || req.getIdsCategory() == null)?-1:0;
            Page<Revenue> page = repository.findRevenue(pageable, req.getIdsCategory(),
                    checkIds, req.getProductName(), req.getFilterDate().getCreatedDateStart(),
                    req.getFilterDate().getCreatedDateEnd());

            List<AdminRevenueDTO> result =  page.getContent().stream().map(AdminRevenueDTO::new).collect(Collectors.toList());

            return BaseResponse.builder().data(result).totalRecords(page.getTotalElements())
                    .status(HttpStatus.OK.value()).message("success").build();

        }catch (Exception e){
            log.error(e.getMessage());
            return BaseResponse.builder().status(HttpStatus.BAD_REQUEST.value())
                    .message("request failed").build();
        }
    }

    @Override
    public ResponseEntity<?> exportRevenue() {
        try {
            List<Long> ids = new ArrayList<>();
                    Page<Revenue> page = repository.findRevenue(
                    PageRequest.of(0, 1000000),
                    ids, -1, "", 0l, 0l
            );
            if(CollectionUtils.isEmpty(page.getContent())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<AdminRevenueDTO> rs = page.getContent().stream().map(AdminRevenueDTO::new).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(rs)){
                String fileName = "revenue_export"+".xlsx";
                ByteArrayInputStream in = ExcelUtils.export(rs, ExportConfig.revenueExport, fileName);
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

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
