package com.costcommission.service;

import com.costcommission.dto.TrialBalanceR11DTO;
import com.costcommission.entity.Category;
import com.costcommission.entity.CostCenter;
import com.costcommission.entity.SubCategory;
import com.costcommission.repository.CategoryRepository;
import com.costcommission.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CostPlusTemplateR11Service {

    @Autowired
    private CostCenterService costCenterService;

    @Autowired
    private CategoryRepository categoryRepository;

    public void generate() throws IOException {
        generatePandL();
    }

    public Object generatePandL() throws IOException {

        Map<String, String> costCenterColumnMap = new HashMap<>();
        List<Category> categories = categoryRepository.findAll();
        String templateFile = "C:\\Demo\\D7433000.xlsx";
        InputStream file = new FileInputStream(templateFile);
        String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        XSSFWorkbook workbookinput = new XSSFWorkbook(file);
        //XSSFWorkbook workbookoutput = workbookinput;
        String name = workbookinput.getSheetName(3);
        Sheet pAndLsheet = workbookinput.getSheet(name);
        String tbSheetName = workbookinput.getSheetName(2);
        Sheet tbSheet = workbookinput.getSheet(tbSheetName);
        CellStyle style = workbookinput.createCellStyle();
        Font font = workbookinput.createFont();

        int categoryStartRowIndex = 3;
        int subcategoryStartRowIndex = 5;
        int costCenterNameStartRowIndex = 6;
        int costCenterCodeStartRowIndex = 7;
        int categoryStartColumnIndex = 3;
        int subcategoryStartColumnIndex = 3;
        int costCenterStartColumnIndex = 3;
        List<TrialBalanceR11DTO> trialBalanceR11DTOS = getTbSheet();
        createTbSheet(trialBalanceR11DTOS, tbSheet, style);
        for (Category category : categories) {
            int subcategoryIndex = 0;
            for (SubCategory subCategory : category.getSubCategories()) {
                subcategoryIndex = subcategoryIndex + subCategory.getCostCenters().size();
                for (CostCenter costCenter : subCategory.getCostCenters()) {
                    Row costCenterNameRow = pAndLsheet.getRow(costCenterNameStartRowIndex);
                    setCellValue(costCenterNameRow, style, costCenterStartColumnIndex, costCenter.getName());
                    Row costCenterCodeRow = pAndLsheet.getRow(costCenterCodeStartRowIndex);
                    setCellValue(costCenterCodeRow, style, costCenterStartColumnIndex, costCenter.getCode());
                    Cell cell = costCenterCodeRow.getCell(costCenterStartColumnIndex);
                    String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
                    applyFormulaForCostCenterAccounting(pAndLsheet, costCenterStartColumnIndex, columnLetter);
                    costCenterColumnMap.put(costCenter.getCode(), columnLetter);
                    costCenterStartColumnIndex = costCenterStartColumnIndex + 1;
                }
                Row categoryRow = pAndLsheet.getRow(subcategoryStartRowIndex);
                setCellValue(categoryRow, style, subcategoryStartColumnIndex, subCategory.getName());
                subcategoryStartColumnIndex = costCenterStartColumnIndex;
            }
            Row categoryRow = pAndLsheet.getRow(categoryStartRowIndex);
            setCellValue(categoryRow, style, categoryStartColumnIndex, category.getName());
            categoryStartColumnIndex = subcategoryStartColumnIndex = costCenterStartColumnIndex = costCenterStartColumnIndex + 1;
        }

//        Row row = sheet.getRow(5);
//        row.setHeight((short) 700);
//        int rowNumber = row.getRowNum();
//        int lastColumn=listOfFrontOffice.size()+2;
//        Cell cell1=row.createCell(3);
//
//        Row row7=sheet.getRow(6);
//        row7.setHeight((short) 1000);
//        int rowNum7=row7.getRowNum();


//        ExcelUtil.border(rowNumber,rowNumber,3,lastColumn,sheet);


//        for (int rowid = rowNum7; rowid == rowNum7; rowid++) {
//            String[] arr = listOfFrontOffice.toArray(new String[0]);
//            int cellid = 3;
//            for (String obj : arr) {
//                Cell cell = row7.createCell(cellid);
//                style.setAlignment(HorizontalAlignment.CENTER);
//                style.setWrapText(true);
//                cell.setCellValue(obj);
//                font.setBold(true);
//                style.setFont(font);
//                cell.setCellStyle(style);
//                cellid++;
//            }
//        }
        applyFormulaForAllocationOfCostPerCarrier(pAndLsheet);
        applyFormulaForRemunerationCalculation(pAndLsheet);
        FileOutputStream out = new FileOutputStream("C:\\Demo\\" + fileName + ".xlsx");
        workbookinput.write(out);
        out.close();
        return "Success";


    }

 //   public void create

    public void createTbSheet(List<TrialBalanceR11DTO> trialBalanceR11DTOS, Sheet sheet, CellStyle style) {
        try {
            for(int i = 2; i < trialBalanceR11DTOS.size()-1; i++) {
                int j=i+1;
                Row row = sheet.createRow(i);
                ExcelUtil.formula(row, 0, "VLOOKUP(J"+j+",mapping!A:C,3,0)", sheet);
                ExcelUtil.formula(row, 1, "VLOOKUP(J"+j+",mapping!A:O,15,0)", sheet);
                ExcelUtil.formula(row, 2, "IF(LEFT(L"+j+",3)=\"CTR\",\"CTRT\",IFERROR(HLOOKUP(L"+j+",'Template cost center P&L'!$C$8:$Z$8,1,FALSE),\"old cost center\"))", sheet);
                ExcelUtil.formula(row, 3, "B"+j+"&C"+j, sheet);
                setCellValue(row, style,5,trialBalanceR11DTOS.get(j).getCompany());
                setCellValue(row, style,6,trialBalanceR11DTOS.get(j).getSite());
                setCellValue(row, style,7,trialBalanceR11DTOS.get(j).getAccount());
                setCellValue(row, style,8,trialBalanceR11DTOS.get(j).getAccountDescription());
                setCellValue(row, style,9,trialBalanceR11DTOS.get(j).getNature());
                setCellValue(row, style,10,trialBalanceR11DTOS.get(j).getNatureDescription());
                setCellValue(row, style,11,trialBalanceR11DTOS.get(j).getCostCenter());
                setCellValue(row, style,12,trialBalanceR11DTOS.get(j).getCostCenterDescription());
                setCellValue(row, style,13,trialBalanceR11DTOS.get(j).getPartner());
                setCellValue(row, style,14,trialBalanceR11DTOS.get(j).getPartnerDescription());
                setCellValue(row, style,15,trialBalanceR11DTOS.get(j).getCurrencyCode());
                setCellValue(row, style,16,trialBalanceR11DTOS.get(j).getBeginBalance());
                setCellValue(row, style,17,trialBalanceR11DTOS.get(j).getPeriodNetDebit());
                setCellValue(row, style,18,trialBalanceR11DTOS.get(j).getPeriodNetCredit());
                setCellValueAsNumeric(row, style,19,trialBalanceR11DTOS.get(j).getClosingBalance());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<TrialBalanceR11DTO> getTbSheet() throws IOException {
        List<TrialBalanceR11DTO> trialBalanceR11DTOS = new ArrayList<>();
        try {
            InputStream r11File = new FileInputStream("C:\\Demo\\R11.xlsx");
            XSSFWorkbook workbookinput = new XSSFWorkbook(r11File);
            XSSFWorkbook workbookoutput = workbookinput;
            String name = workbookinput.getSheetName(0);
            Sheet sheet = workbookoutput.getSheet(name);
            Row row = sheet.getRow(0);
            Integer lastRowIndex = sheet.getLastRowNum();
            for (int i = 18; i < lastRowIndex; i++) {
                Row rowItr = sheet.getRow(i);
                Integer totalNoOfCols = rowItr.getLastCellNum() - 0;
                TrialBalanceR11DTO trialBalanceR11DTO = new TrialBalanceR11DTO();
                for (int j = 0; j < totalNoOfCols; j++) {
                    switch (j) {
                        case 0:
                            trialBalanceR11DTO.setCompany(getCellValue(rowItr, j));
                            break;
                        case 1:
                            trialBalanceR11DTO.setSite(getCellValue(rowItr, j));
                            break;
                        case 2:
                            trialBalanceR11DTO.setAccount(getCellValue(rowItr, j));
                            break;
                        case 3:
                            trialBalanceR11DTO.setAccountDescription(getCellValue(rowItr, j));
                            break;
                        case 4:
                            trialBalanceR11DTO.setNature(getCellValue(rowItr, j));
                            break;
                        case 5:
                            trialBalanceR11DTO.setNatureDescription(getCellValue(rowItr, j));
                            break;
                        case 6:
                            trialBalanceR11DTO.setCostCenter(getCellValue(rowItr, j));
                            break;
                        case 7:
                            trialBalanceR11DTO.setCostCenterDescription(getCellValue(rowItr, j));
                            break;
                        case 8:
                            trialBalanceR11DTO.setPartner(getCellValue(rowItr, j));
                            break;
                        case 9:
                            trialBalanceR11DTO.setPartnerDescription(getCellValue(rowItr, j));
                            break;
                        case 10:
                            trialBalanceR11DTO.setCurrencyCode(getCellValue(rowItr, j));
                            break;
                        case 11:
                            trialBalanceR11DTO.setBeginBalance(getCellValue(rowItr, j));
                            break;
                        case 12:
                            trialBalanceR11DTO.setPeriodNetDebit(getCellValue(rowItr, j));
                            break;
                        case 13:
                            trialBalanceR11DTO.setPeriodNetCredit(getCellValue(rowItr, j));
                            break;
                        case 14:
                            trialBalanceR11DTO.setClosingBalance(Double.valueOf(getCellValue(rowItr, j)));
                            break;
                    }
                }
                trialBalanceR11DTOS.add(trialBalanceR11DTO);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return trialBalanceR11DTOS;
    }




    public String getCellValue(Row row, int column) {
        Cell cell = row.getCell(column);
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    public void applyFormulaForCostCenterAccounting(Sheet sheet, int columnIndex, String columnLetter) {
        Row row9 = sheet.getRow(8);
        ExcelUtil.formula(row9, columnIndex, "SUMIF('TB cost center + cost+ section'!$D:$D,'Template cost center P&L'!$B9&'Template cost center P&L'!" + columnLetter + "$8,'TB cost center + cost+ section'!$T:$T)", sheet);
        Row row10 = sheet.getRow(9);
        ExcelUtil.formula(row10, columnIndex, columnLetter + "75", sheet);
        Row row11 = sheet.getRow(10);
        ExcelUtil.formula(row11, columnIndex, "SUM(" + columnLetter + "9:" + columnLetter + "10)", sheet);
        Row row13 = sheet.getRow(12);
        ExcelUtil.formula(row13, columnIndex, "SUMIF('TB cost center + cost+ section'!$D:$D,'Template cost center P&L'!$B13&'Template cost center P&L'!" + columnLetter + "$8,'TB cost center + cost+ section'!$T:$T)", sheet);
        Row row14 = sheet.getRow(13);
        ExcelUtil.formula(row14, columnIndex, "SUMIF('TB cost center + cost+ section'!$D:$D,'Template cost center P&L'!$B14&'Template cost center P&L'!" + columnLetter + "$8,'TB cost center + cost+ section'!$T:$T)", sheet);
        Row row15 = sheet.getRow(14);
        ExcelUtil.formula(row15, columnIndex, "SUMIF('TB cost center + cost+ section'!$D:$D,'Template cost center P&L'!$B15&'Template cost center P&L'!" + columnLetter + "$8,'TB cost center + cost+ section'!$T:$T)", sheet);
        Row row17 = sheet.getRow(16);
        ExcelUtil.formula(row17, columnIndex, "SUM(" + columnLetter + "13:" + columnLetter + "15)", sheet);
        Row row19 = sheet.getRow(18);
        ExcelUtil.formula(row19, columnIndex, "SUM(" + columnLetter + "17," + columnLetter + "11)", sheet);
        Row row22 = sheet.getRow(21);
        ExcelUtil.formula(row22, columnIndex, "SUMIF('TB cost center + cost+ section'!$D:$D,'Template cost center P&L'!$B22&'Template cost center P&L'!" + columnLetter + "$8,'TB cost center + cost+ section'!$T:$T)", sheet);
        Row row24 = sheet.getRow(23);
        ExcelUtil.formula(row24, columnIndex, "SUM(" + columnLetter + "22," + columnLetter + "19)", sheet);
        Row row26 = sheet.getRow(25);
        ExcelUtil.formula(row26, columnIndex, "SUMIF('TB cost center + cost+ section'!$D:$D,'Template cost center P&L'!$B26&'Template cost center P&L'!" + columnLetter + "$8,'TB cost center + cost+ section'!$T:$T)", sheet);
        Row row28 = sheet.getRow(27);
        ExcelUtil.formula(row28, columnIndex, "SUM(" + columnLetter + "26," + columnLetter + "24)", sheet);
    }

    public void applyFormulaForAllocationOfCostPerCarrier(Sheet sheet) {
        applyFormulaForFrontOffice(sheet);
        applyFormulaForFrontOfficeMutualized(sheet);
        applyFormulaForDocumentation(sheet);
        applyFormulaForSupportToOperation(sheet);
        applyFormulaForSupportFunctions(sheet);
        applyFormulaForIndirectCosts(sheet);
        applyFormulaForManagementItFees(sheet);
        applyFormulaForOpeningCostBase(sheet);
    }

    public void applyFormulaForFrontOffice(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row51 = sheet.getRow(51);
            Cell cell = row51.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row51, i, "SUM(" + columnLetter + "13," + columnLetter + "14)", sheet);
        }
    }

    public void applyFormulaForFrontOfficeMutualized(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row52 = sheet.getRow(52);
            Cell cell = row52.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            try {
                ExcelUtil.formula(row52, i, "SUM($J$13:$J$14)*" + columnLetter + "46", sheet);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public void applyFormulaForDocumentation(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row53 = sheet.getRow(53);
            Cell cell = row53.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row53, i, "SUM($K$13:$K$14)*" + columnLetter + "45", sheet);
        }
    }

    public void applyFormulaForSupportToOperation(Sheet sheet) {
        Row row54 = sheet.getRow(54);
        ExcelUtil.formula(row54, 3, "D47*SUM($M$13:$M$14)+SUM(L13:L14+N13:N14)*(D42/SUM(D42+I42))", sheet);
        for (int i = 4; i < 8; i++) {
            Cell cell = row54.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row54, i, columnLetter + "47*SUM($M$13:$M$14)", sheet);
        }
        ExcelUtil.formula(row54, 8, "I47*SUM($M$13:$M$14)+SUM(L13:L14+N13:N14)*(I42/SUM(D42+I42))", sheet);
    }

    public void applyFormulaForSupportFunctions(Sheet sheet) {
        Row row55 = sheet.getRow(55);
        ExcelUtil.formula(row55, 3, "D47*SUM($R$13:$R$14)+((SUM(O13:O14)*95%)*(D42/SUM(D42+I42)))+((SUM(P13:P14)-(SUM(P13:P14)*H41/D48))*D42/SUM(D42+I42))+(SUM(Q13:Q14)*(D42/SUM(D42+I42)))", sheet);
        for (int i = 4; i < 7; i++) {
            Cell cell8 = row55.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell8.getColumnIndex());
            ExcelUtil.formula(row55, i, columnLetter + "47*(SUM($P$13:$R$14)+(SUM(O13:O14)*95%))", sheet);
        }
        ExcelUtil.formula(row55, 7, "(H47*SUM($R$13:$R$14))+(SUM(O13:O14)*5%)+(SUM(P13:P14)*H41/D48)", sheet);
        ExcelUtil.formula(row55, 8, "(I47*SUM($R$13:$R$14))+((SUM(O13:O14)*95%)*(I42/SUM(D42+I42)))+((SUM(P13:P14)-(SUM(P13:P14)*H41/D48))*I42/SUM(D42+I42))+(SUM(Q13:Q14)*(I42/SUM(D42+I42)))", sheet);
    }

    public void applyFormulaForIndirectCosts(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row56 = sheet.getRow(56);
            Cell cell = row56.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row56, i, columnLetter + "47*SUM($S$13:$S$14)", sheet);
        }
    }

    public void applyFormulaForManagementItFees(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row57 = sheet.getRow(57);
            Cell cell = row57.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row57, i, columnLetter + "15+SUM($J$15:$S$15)*" + columnLetter + "47", sheet);
        }
    }

    public void applyFormulaForOpeningCostBase(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row59 = sheet.getRow(59);
            Cell cell = row59.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row59, i, "SUM(" + columnLetter + "52:" + columnLetter + "58)", sheet);
        }
    }

    public void applyFormulaForRemunerationCalculation(Sheet sheet) {
        applyFormulaForRCFrontOffice(sheet);
        applyFormulaForRCFrontOfficeMutualized(sheet);
        applyFormulaForRCDocumentation(sheet);
        applyFormulaForRCSupportToOperation(sheet);
        applyFormulaForRCSupportFunctions(sheet);
        applyFormulaForRCIndirectCosts(sheet);
        applyFormulaForRCManagementItFees(sheet);
        applyFormulaForRCCarrierRenumeration(sheet);
    }

    public void applyFormulaForRCFrontOffice(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row66 = sheet.getRow(66);
            Cell cell = row66.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row66, i, "-" + columnLetter + "52*(1+$D$65)-" + columnLetter + "9", sheet);
        }
    }

    public void applyFormulaForRCFrontOfficeMutualized(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row67 = sheet.getRow(67);
            Cell cell = row67.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row67, i, "-" + columnLetter + "53*(1+$D$65)-$J$9*" + columnLetter + "$46", sheet);
        }
    }

    public void applyFormulaForRCDocumentation(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row68 = sheet.getRow(68);
            Cell cell = row68.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row68, i, "-" + columnLetter + "54*(1+$D$65)-$K$9*" + columnLetter + "$45", sheet);
        }
    }

    public void applyFormulaForRCSupportToOperation(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row69 = sheet.getRow(69);
            Cell cell = row69.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row69, i, "-" + columnLetter + "55*(1+$D$65)-SUM($L$9:$N$9)*" + columnLetter + "$47", sheet);
        }
    }

    public void applyFormulaForRCSupportFunctions(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row70 = sheet.getRow(70);
            Cell cell = row70.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row70, i, "-" + columnLetter + "56*(1+$D$65)-SUM($O$9:$R$9)*" + columnLetter + "$47", sheet);
        }
    }

    public void applyFormulaForRCIndirectCosts(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row71 = sheet.getRow(71);
            Cell cell = row71.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row71, i, "-" + columnLetter + "57*(1+$D$65)-$S$9*" + columnLetter + "$47", sheet);
        }
    }

    public void applyFormulaForRCManagementItFees(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row72 = sheet.getRow(72);
            Cell cell = row72.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row72, i, "-" + columnLetter + "58", sheet);
        }
    }

    public void applyFormulaForRCCarrierRenumeration(Sheet sheet) {
        for (int i = 3; i < 9; i++) {
            Row row74 = sheet.getRow(74);
            Cell cell = row74.getCell(i);
            String columnLetter = CellReference.convertNumToColString(cell.getColumnIndex());
            ExcelUtil.formula(row74, i, "SUM(" + columnLetter + "67:" + columnLetter + "73)", sheet);
        }
    }


    public void setCellValue(Row row, CellStyle style, int column, String value) {
        style.setVerticalAlignment(VerticalAlignment.DISTRIBUTED);
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
    }

    public void setCellValueAsNumeric(Row row, CellStyle style, int column, Double value) {
        style.setVerticalAlignment(VerticalAlignment.DISTRIBUTED);
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
    }

}
