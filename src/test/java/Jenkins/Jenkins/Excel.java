package Jenkins.Jenkins;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class Excel implements ExcelPath {
	
	@DataProvider
	public String[][] readexcel() throws Exception {
		System.out.println("I am in excel");
		File fp = new File(excelpath);
		FileInputStream fs = new FileInputStream(fp);
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		XSSFSheet sh = wb.getSheet("Login");
		int rowcount = sh.getLastRowNum();
		int column = sh.getRow(0).getLastCellNum();
		String[][] data = new String[rowcount][column];
		for(int i = 0;i<rowcount;i++)
		{
			XSSFRow row = sh.getRow(i+1);
			for(int j=0;j<column;j++)
			{
				XSSFCell cell = row.getCell(j);
//DataFormatter contains methods for formatting the value stored in an Cell.
//This can be useful for reports and GUI presentations when you need to display data exactly as it appears in Excel.
				DataFormatter formatter = new DataFormatter();
				String val = formatter.formatCellValue(cell);
				data[i][j] = val;
				
			}
		}
		System.out.println("I am out of excel");
		return data;
	}
	
//	XSSFWorkbook wb;
//	public Excel(String excelpath) {
//		try {
//			File ff = new File(excelpath);
//			FileInputStream fs = new FileInputStream(ff);
//			XSSFWorkbook wb = new XSSFWorkbook(fs);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//	
//	XSSFSheet sh;
//	public Object getData(int sheetNumber, int row, int column) {
//		
//		 /*sh=wb.getSheetAt(sheetNumber); 
//		 String data=sh.getRow(row).getCell(column).getStringCellValue();
//		 return data;
//		 */
//		 
//		String data;
//		 sh = wb.getSheetAt(sheetNumber);
//
//		try {
//			data = sh.getRow(row).getCell(column).getStringCellValue();
//			return data;
//		} catch (Exception e) {
//			data = sh.getRow(row).getCell(column).getNumericCellValue() + "";
//			return data;
//		}
//	}
	
	
}
