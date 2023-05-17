package com.t3h.ecommerce.utils;


import com.t3h.ecommerce.dto.request.admin_product.ProductAdminDTO;
import com.t3h.ecommerce.pojo.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportConfig {

    private int sheetIndex;

    private int headerIndex;

    private int startRow;

    private Class dataClazz;

    private List<CellConfig> cellImportConfigs;

    public static final ImportConfig customerImport;

    static {
        customerImport = new ImportConfig();
        customerImport.setSheetIndex(0);
        customerImport.setHeaderIndex(0);
        customerImport.setStartRow(1);
        customerImport.setDataClazz(UserDTO.class);
        List<CellConfig> customerImportCellConfigs = new ArrayList<>();

        customerImportCellConfigs.add(new CellConfig(0, "id"));
        customerImportCellConfigs.add(new CellConfig(1, "username"));
        customerImportCellConfigs.add(new CellConfig(2, "fullName"));
        customerImportCellConfigs.add(new CellConfig(3, "phoneNumber"));
        customerImportCellConfigs.add(new CellConfig(4, "gender"));
        customerImportCellConfigs.add(new CellConfig(5, "status"));
        customerImportCellConfigs.add(new CellConfig(6, "address"));

        customerImport.setCellImportConfigs(customerImportCellConfigs);
    }

    public static final ImportConfig productImport;
    static {
        productImport = new ImportConfig();
        productImport.setSheetIndex(0);
        productImport.setHeaderIndex(0);
        productImport.setStartRow(1);
        productImport.setDataClazz(ProductAdminDTO.class);
        List<CellConfig> productImportCellConfigs = new ArrayList<>();

        productImportCellConfigs.add(new CellConfig(0, "id"));
        productImportCellConfigs.add(new CellConfig(1, "productName"));
        productImportCellConfigs.add(new CellConfig(2, "shortDescription"));
        productImportCellConfigs.add(new CellConfig(3, "cost"));
        productImportCellConfigs.add(new CellConfig(4, "quantity"));
        productImportCellConfigs.add(new CellConfig(5, "categoryId"));
        productImportCellConfigs.add(new CellConfig(6, "colorId"));
        productImportCellConfigs.add(new CellConfig(7, "sizeId"));
        productImportCellConfigs.add(new CellConfig(8, "discountId"));
        productImportCellConfigs.add(new CellConfig(9, "createdDate"));
        productImportCellConfigs.add(new CellConfig(10, "updatedDate"));

        productImport.setCellImportConfigs(productImportCellConfigs);
    }
}
