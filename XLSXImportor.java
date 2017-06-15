package cn.thinkjoy.qky.salarymanage.common.utils;

import cn.qtone.tools.util.LogUtil;
import cn.qtone.tools.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class XLSXImportor {

	private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.##");
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);;
	private int firstDataRow = 1; // 有效数据起始行索引
	private int dataSize = 0; // 有效数据行数
	private int cellSize = 0; // 有效数据列数
	private boolean resolve = true; // 文档解析是否成功;

	private HSSFWorkbook wb = null;
	private Sheet sheet = null;
	private Workbook book = null;
	/**
	 * 构造方法
	 * @param realPath 文件绝对路径
	 * @param cellSize 有效列数(<=0时,读各行有效列)
	 * @param firstDataRow 起始有效数据行索引
	 */
	public XLSXImportor(String realPath, int cellSize, int firstRow, boolean delRecFile) {
		InputStream fis = null;
		try {
			//为解决excel版本问题
			if(realPath.indexOf(".xlsx")>-1){  
            	book = new XSSFWorkbook(new FileInputStream(realPath));
            } else {  
            	book = new HSSFWorkbook(new FileInputStream(realPath));
            }  
		    sheet = book.getSheet("Sheet1"); // 创建对工作表的引用  
			this.cellSize = cellSize > 0 ? cellSize : this.cellSize;
			firstDataRow = firstRow > 0 ? firstRow : firstDataRow;
			dataSize = sheet.getLastRowNum() - firstDataRow + 2;
		} catch (Exception e) {
			resolve = false;
			LogUtil.excLog(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					LogUtil.excLog(e);
				}
			}

			if (delRecFile) {
				File file = new File(realPath);
				if (file.exists()) {
					file.delete();
				}
			}
		}
	}

	/**
	 * 构造方法
	 * @param realPath 文件绝对路径
	 * @param firstDataRow 起始有效数据行索引
	 */
	public XLSXImportor(String realPath, int firstRow, boolean delRecFile) {
		InputStream fis = null;
		try {
			if(realPath.indexOf(".xlsx")>-1){  
            	//book = new XSSFWorkbook(new FileInputStream(realPath));  
            	book = WorkbookFactory.create(new FileInputStream(realPath));
            } else {  
            	book = new HSSFWorkbook(new FileInputStream(realPath));
            }  
			//sheet = book.getSheet("Sheet1"); // 创建对工作表的引用  
			sheet = book.getSheetAt(0); // 创建对工作表的引用  
			this.cellSize = maxCellSize(sheet.getRow(0).getPhysicalNumberOfCells(),sheet.getRow(0).getLastCellNum());
			firstDataRow = firstRow > 0 ? firstRow : firstDataRow;
			dataSize = sheet.getLastRowNum() - firstDataRow + 2;
		} catch (Exception e) {
			resolve = false;
			LogUtil.excLog(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					LogUtil.excLog(e);
				}
			}

			if (delRecFile) {
				File file = new File(realPath);
				if (file.exists()) {
					file.delete();
				}
			}
		}
	}
	
	/**
	 * 
	 * @return 获得总的列数
	 */
	public Integer getColumnsSize()
	{
		return maxCellSize(sheet.getRow(0).getPhysicalNumberOfCells(),sheet.getRow(0).getLastCellNum());
	}
	
	/**
	 * 
	 * @param rowIndex 行号（从0开始）
	 * @return 返回指定的标题行
	 */
	public String[] getTitleRow(int rowIndex)
	{
		String[] data = new String[cellSize];
		Row row = sheet.getRow(rowIndex - 1);
		if (row != null && row.getPhysicalNumberOfCells() > 0) {
			int minCellSize = minCellSize(row.getLastCellNum());
			if (cellSize == 0) {
				data = new String[minCellSize];
			}
			for (int cellIndex = 0; cellIndex < minCellSize; cellIndex++) {
				data[cellIndex] = getCellValue(row.getCell(cellIndex));
			}
			if (cellSize > minCellSize) {
				data = fixArray(data);
			}
		}

		return data;
	}
	
	/**
	 * 获取所有有效数据行
	 * @return List<String[]>
	 */
	public List<String[]> getAllRow() {
		List<String[]> data = new ArrayList<String[]>();

		for (int rowIndex = 0; rowIndex < dataSize; rowIndex++) {
			data.add(getRow(rowIndex));
		}

		return data;
	}
	
	/**
	 * 获取行
	 * @param rowIndex 行索引(首行为0)
	 * @return String[]
	 */
	public String[] getRow(int rowIndex) {
		String[] data = new String[cellSize];
		Row row = sheet.getRow(firstDataRow + rowIndex - 1);
		if (row != null && row.getPhysicalNumberOfCells() > 0) {
			int minCellSize = minCellSize(row.getLastCellNum());
			if (cellSize == 0) {
				data = new String[minCellSize];
			}
			for (int cellIndex = 0; cellIndex < minCellSize; cellIndex++) {
				data[cellIndex] = getCellValue(row.getCell(cellIndex));
			}
			if (cellSize > minCellSize) {
				data = fixArray(data);
			}
		}

		return data;
	}

	/**
	 * 获取单元格
	 * @param rowIndex 行索引(首行为0)
	 * @param cellIndex 列索引(首列为0)
	 * @return String
	 */
	public String getCell(int rowIndex, int cellIndex) {
		String data = "";
		Row row = sheet.getRow(firstDataRow + rowIndex - 1);
		if (row != null) {
			data = getCellValue(row.getCell(cellIndex));
		}
		return data;
	}

	/**
	 * 获取单元格值
	 * @param cell 单元格
	 * @return String
	 */
	private String getCellValue(Cell cell) {
		String data = "";
		if (cell != null) {
			switch (cell.getCellType()) 
			{
				case HSSFCell.CELL_TYPE_NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						data = formatDate(cell.getDateCellValue());
					} else {
						data = formatNumeric(cell.getNumericCellValue());
					}
					break;
				case HSSFCell.CELL_TYPE_STRING:
					data = cell.getStringCellValue().trim();
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					data = formatNumeric(cell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					data = "";
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					data = StringUtil.toString(cell.getBooleanCellValue());
					break;
				default:
					break;
			}
		}

		return data;
	}
	
	public int titleLength(){
		int length = 0;
		Row row = sheet.getRow(0);
		if (row != null) {
			length = row.getPhysicalNumberOfCells();
		}
		return length;
	}

	/**
	 * 有效数据行数
	 * @return int
	 */
	public int dataSize() {
		return dataSize;
	}

	/**
	 * 文档是否解析成功
	 * @return boolean
	 */
	public boolean isResolve() {
		return resolve;
	}
	
	public int cellSize(){
		return cellSize;
	}

	private int minCellSize(int lastCellNum) {
		return cellSize == 0 || lastCellNum < cellSize ? lastCellNum : cellSize;
	}

	private int maxCellSize(int cellSize1,int cellSize2) {
		return cellSize1 < cellSize2 ? cellSize2 : cellSize1;
	}
	private static String formatDate(Date date) {
		return DATE_FORMAT.format(date);
	}

	private static String formatNumeric(double num) {
		return DECIMAL_FORMAT.format(num);
	}
	
	private boolean hasContent(String[] arr){
		boolean hasContent = false;
		for (String cell : arr) {
			if (null != cell && !"".equals(cell)) {
				hasContent = true;
				break;
			}
		}
		return hasContent;
	}
	
	private String[] fixArray(String[] arr){
		if (hasContent(arr)) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == null) {
					arr[i] = "";
				}
			}
		} else {
			arr = new String[0];
		}
		return arr;
	}
	
	
}
