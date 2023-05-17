package com.t3h.ecommerce.utils;

import com.t3h.ecommerce.dto.request.admin_product.ProductAdminDTO;
import com.t3h.ecommerce.entities.core.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.t3h.ecommerce.utils.FileFactory.PATH_TEMPLATE;

@Component
@Slf4j
public class ExcelUtils {

    // export data excel
    public static ByteArrayInputStream export(List<?> data, ExportConfig typeExport, String fileName) throws Exception {
        // get file -> not found ==> create file
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        File file;
        FileInputStream fileInputStream;
        try{
            file = ResourceUtils.getFile(PATH_TEMPLATE+ fileName);
            fileInputStream = new FileInputStream(file);
        }catch (Exception e){
              log.info("FILE NOT FOUND!");
              file = FileFactory.createFile(fileName, xssfWorkbook);
              fileInputStream = new FileInputStream(file);
        }
        //create freeze pane in excel file
        XSSFSheet newSheet = xssfWorkbook.createSheet("sheet1");
        newSheet.createFreezePane(4, 2, 4, 2);
        //create front for title
        XSSFCellStyle titleCellStyle = createAndSetStyleTitle(xssfWorkbook);
        // create font for data
        XSSFCellStyle dataCellStyle = createAndSetStyleCellData(xssfWorkbook);
        //insert fieldName as title to excel
        insertFieldNameAsTitleToWorkBook(typeExport.getCellExportConfigList(), newSheet, titleCellStyle);
        // insert data of fieldName to excel
        insertDataToWorkBook(xssfWorkbook, typeExport, data, dataCellStyle);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        xssfWorkbook.write(outputStream);
        //close resouce
        outputStream.close();
       //return
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    private static XSSFCellStyle createAndSetStyleCellData(XSSFWorkbook xssfWorkbook) {
        XSSFFont dataFont = xssfWorkbook.createFont();
        dataFont.setFontName("Arial");
        dataFont.setBold(false);
        dataFont.setFontHeightInPoints((short) 10);

        // create style for cell data and apply front data to cell
        XSSFCellStyle dataCellStyle = xssfWorkbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setFont(dataFont);
        dataCellStyle.setWrapText(true);
        return dataCellStyle;
    }
    private static XSSFCellStyle createAndSetStyleTitle(XSSFWorkbook xssfWorkbook) {
        XSSFFont titleFont = xssfWorkbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);

        // create style for cell of title
        XSSFCellStyle titleCellStyle = xssfWorkbook.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
        titleCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        titleCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        titleCellStyle.setBorderRight(BorderStyle.MEDIUM);
        titleCellStyle.setBorderTop(BorderStyle.MEDIUM);
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setWrapText(true);
        return titleCellStyle;
    }

    private static <T> void insertDataToWorkBook(Workbook workbook, ExportConfig exportConfig, List<T> datas,
                                                 XSSFCellStyle dataCellStyle){

        int startRowIndex = exportConfig.getStartRow(); // 2
        int sheetIndex = exportConfig.getSheetIndex(); // 1

        Class clazz = exportConfig.getDataClazz();

        List<CellConfig> cellConfigs = exportConfig.getCellExportConfigList();

        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int currentRowIndex = startRowIndex;

        for(T data : datas){
           Row currentRow = sheet.getRow(currentRowIndex);

           if(ObjectUtils.isEmpty(currentRow)){
               currentRow = sheet.createRow(currentRowIndex);
           }
           // insert data to row

            insertDataToCell(data, currentRow, cellConfigs, clazz, sheet, dataCellStyle);
           currentRowIndex++;


        }
    }

    private static <T> void insertFieldNameAsTitleToWorkBook(
                                                             List<CellConfig> cellConfigs,
                                                             Sheet sheet,
                                                             XSSFCellStyle titleCellStyle){

        // title -> first row of excel  -> get top row
         int currentRow = sheet.getTopRow();

         //create row
         Row row = sheet.createRow(currentRow);
         int i =0;

         // resize fix text excel in each excel
         sheet.autoSizeColumn(currentRow);

         // insert filed name to cell
         for(CellConfig cellConfig: cellConfigs){
             Cell currentCell = row.createCell(i);
             String fieldName = cellConfig.getFieldName();


             currentCell.setCellValue(fieldName);
             currentCell.setCellStyle(titleCellStyle);
             sheet.autoSizeColumn(i);
             i++;
         }
    }

    private static <T> void insertDataToCell(T data, Row currentRow, List<CellConfig> cellConfigs,
                                             Class clazz, Sheet sheet, XSSFCellStyle dataStyle){

        for(CellConfig cellConfig: cellConfigs){
            Cell currentCell = currentRow.getCell(cellConfig.getColumnIndex());

            if(ObjectUtils.isEmpty(currentCell)){
                currentCell = currentRow.createCell(cellConfig.getColumnIndex());
            }

            // get data for cell
            String cellValue = getCellValue(data, cellConfig, clazz);

            // set data
            currentCell.setCellValue(cellValue);
            sheet.autoSizeColumn(cellConfig.getColumnIndex());
            currentCell.setCellStyle(dataStyle);
        }
    }

    private static <T> String getCellValue(T data, CellConfig cellConfig, Class clazz) {
        String fieldName = cellConfig.getFieldName();

        try{
            Field field = getDeclaredField(clazz, fieldName);

            if(!ObjectUtils.isEmpty(field)){
                field.setAccessible(true);
                return !ObjectUtils.isEmpty(field.get(data))? field.get(data).toString(): "";
            }
            return "";
        }catch (Exception e){
            log.info(e.getMessage());
            return "";
        }
    }

    private static Field getDeclaredField(Class clazz, String fieldName) {
        if(ObjectUtils.isEmpty(clazz) || ObjectUtils.isEmpty(fieldName)){
              return null;
        }
        do{
            try{
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return  field;
            } catch (NoSuchFieldException e) {
                log.info(e.getMessage());
            }
        }while ((clazz = clazz.getSuperclass()) != null);

        return null;
    }


    // import data excel
    public static <T> List<T> getImportData(Workbook workbook, ImportConfig importConfig){
          List<T> list = new ArrayList<>();

          List<CellConfig> cellConfigs = importConfig.getCellImportConfigs();

          int countSheet =0;
          for(Sheet sheet: workbook){
              if(countSheet != importConfig.getSheetIndex()){
                  countSheet++;
                  continue;
              }
              int countRow =0;
              for(Row row: sheet){
                  if(countRow < importConfig.getStartRow()){
                      countRow++;
                      continue;
                  }

                  T rowData = getRowData(row, cellConfigs, importConfig.getDataClazz());
                  list.add(rowData);
                  countRow++;
              }
              countSheet++;
          }
          return list;
    }

    private static <T> T getRowData(Row row, List<CellConfig> cellConfigs, Class dataClazz) {
        T instance = null;
        try{
            instance = (T) dataClazz.getDeclaredConstructor().newInstance();

            for(int i=0; i< cellConfigs.size(); i++){
                CellConfig currentCell = cellConfigs.get(i);

                try {
                    Field field = getDeclaredField(dataClazz, currentCell.getFieldName());
                    Cell cell = row.getCell(currentCell.getColumnIndex());

                    if(!ObjectUtils.isEmpty(cell)){
                        cell.setCellType(CellType.STRING);

                        Object cellValue = cell.getStringCellValue();

                        setFieldValue(instance, field, cellValue);
                    }

                }catch ( Exception e){
                         e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return instance;
    }

    private static <T> void setFieldValue(Object instance, Field field, Object cellValue) {
        if(ObjectUtils.isEmpty(instance) || ObjectUtils.isEmpty(field)){
            return;
        }

        Class clazz = field.getType();

        Object valueConverted = parseValueByClass(clazz, cellValue);
        field.setAccessible(true);

        try{
            field.set(instance, valueConverted);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Object parseValueByClass(Class clazz, Object cellValue) {
        if(ObjectUtils.isEmpty(clazz) || ObjectUtils.isEmpty(cellValue)){
            return null;
        }

        String clazzName = clazz.getSimpleName();

        switch (clazzName){
            case "char":
                cellValue = parseChar(cellValue);
                break;
            case "String":
                cellValue = ObjectUtils.isEmpty(cellValue)?null: cellValue.toString().trim();
                break;
            case "boolean":
            case "Boolean":
                cellValue = parseBoolean(cellValue);
                break;
            case "byte":
            case "Byte":
                cellValue = ObjectUtils.isEmpty(cellValue)?null: (Byte) cellValue;
                break;
            case "short":
            case "Short":
                cellValue = ObjectUtils.isEmpty(cellValue)?null: Short.parseShort(cellValue.toString().trim());
                break;
            case "int":
            case "Integer":
                cellValue = ObjectUtils.isEmpty(cellValue)?null: Integer.parseInt(cellValue.toString().trim());
                break;
            case "long":
            case "Long":
                cellValue = ObjectUtils.isEmpty(cellValue)?null: Long.parseLong(cellValue.toString().trim());
                break;
            case "float":
            case "Float":
                cellValue = ObjectUtils.isEmpty(cellValue)?null: Float.parseFloat(cellValue.toString().trim());
                break;
            case "double":
            case "Double":
                cellValue = ObjectUtils.isEmpty(cellValue)?null: Double.parseDouble(cellValue.toString().trim());
                break;
            case "Date":
                cellValue = parseToDate(cellValue);
                break;
            default:
                break;
        }
        return cellValue;
    }

    private static Object parseToDate(Object cellValue) {
        String[] formatDate = {"yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy"};

        if(ObjectUtils.isEmpty(cellValue)) return null;

        String dateStr = cellValue.toString();
        for(String format: formatDate){
            Date date = null;

            try{
                DateFormat dateFormat = new SimpleDateFormat(format);
                date = dateFormat.parse(dateStr);

            }catch (Exception e){
                e.printStackTrace();
            }
            if(ObjectUtils.isEmpty(date)){
                return date;
            }
        }
        try{
            Date date = (Date) cellValue;
            return date;
        }catch (Exception e){
            e.printStackTrace();
            return new Date();
        }

    }

    private static Object parseBoolean(Object cellValue) {
        return ObjectUtils.isEmpty(cellValue)?null: (char) cellValue;
    }

    private static Object parseChar(Object cellValue) {
        return ObjectUtils.isEmpty(cellValue)?null: (Boolean) cellValue;
    }

}
