package com.costcommission.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;



public class ExcelUtil {

    public static void border(int firstRow, int lastRow, int firstColumn, int lastColumn, Sheet sheet) {

        CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn);
        RegionUtil.setBorderBottom(BorderStyle.THICK, cellRangeAddress, sheet);
        RegionUtil.setBorderTop(BorderStyle.THICK, cellRangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THICK, cellRangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THICK, cellRangeAddress, sheet);
    }
    public static void blueBackgroundColourAndValue(Row row, int cellNumber, String cellValue, CellStyle cellStyle) {
        cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        Cell cell = row.createCell(cellNumber);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cellValue);
    }
    public static void setColourToNumOfCell(Row row,int startingCell,int lastColumn,CellStyle style){
        for(int i=startingCell;i<=lastColumn;i++) {
            style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Cell cell = row.createCell(i);
        }
    }
        public static void formula(Row row, int cellValue, String formula, Sheet sheet) {
        Cell cell = row.createCell(cellValue);
        cell.setCellFormula(formula);
        cell.setCellType(CellType.FORMULA);
    }
}
