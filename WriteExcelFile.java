package cn.thinkjoy.qky.salarymanage.common.utils;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WriteExcelFile {
	private static WritableCellFormat dateFormat;
	private static WritableCellFormat menuTitleFormat;
	private static WritableCellFormat titleFormat;

	/**
	 * 优化后的方法
	 * 写excel文件，如果数据超过5000，则分表
	 * @param data
	 * @param sheetHead
	 * @param sheetName
	 * @param filePath
	 * @param tempdir 临时文件的路径,即所在的目录（可以有效避免内存溢出）
	 * @return
	 */
	public static boolean writeExcelFile(List<String[]> data,String[] sheetHead, String sheetName, String filePath,String tempdir) {
		WritableWorkbook book = null;
		try {
			File f=new File(filePath);
			
			WorkbookSettings wbSetting = new WorkbookSettings();     												
			wbSetting.setUseTemporaryFileDuringWrite(true);     										
			wbSetting.setTemporaryFileDuringWriteDirectory(new File(tempdir));//设定临时文件的位置，可以有效的避免内存溢										
			book = Workbook.createWorkbook(f,wbSetting); 
			
			setSheetStyle();// 设置样式；
			//判断数据量，确定分表的数量
			long maxRow=40000;//Excel表的每张表的最大记录数,当数量达到时，文件将写到临时文件中
			WritableSheet sheet = WriteExcelFile.createSheet(book, sheetName+"(0)",sheetHead,0);// 创建表头
			int sheetNum=1;//表数量
			int row = 1;
			for (String[] rowData : data) {
				if(row>maxRow){					
					sheet = WriteExcelFile.createSheet(book, sheetName+"("+sheetNum+")",sheetHead,sheetNum);// 创建表头	
					sheetNum++;	
					row=1;//重新从新表的第二行开始写记录
				}
				WriteExcelFile.writeData(sheet, rowData, row);
				row++;
			}
			book.write();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			data.clear();
			data=null;//及时释放资源
			if (book != null) {
				try {
					book.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// 设置样式
	private static void setSheetStyle() {
		jxl.write.WritableFont titleFont = new jxl.write.WritableFont(
				WritableFont.createFont("宋体"), 12, WritableFont.BOLD);// 字体
		titleFormat = new WritableCellFormat(titleFont); // 表头格式；
		dateFormat = new WritableCellFormat(); // 内容格式
		menuTitleFormat = new WritableCellFormat();// 标题格式
		try {
			dateFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
			dateFormat.setAlignment(Alignment.CENTRE); // 水平居中

			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
			titleFormat.setAlignment(Alignment.CENTRE); // 水平居中

			menuTitleFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
			menuTitleFormat.setAlignment(Alignment.LEFT); // 水平居中

		} catch (WriteException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 创建表及设置表头
	 * @param book
	 * @param sheetName
	 * @param head
	 * @param sheetNum
	 * @return
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private static WritableSheet createSheet(WritableWorkbook book,String sheetName, String[] head,int sheetNum) throws RowsExceededException,
			WriteException {
		// 生成第一张表及设置表名
		WritableSheet sheet = book.createSheet(sheetName, sheetNum);
		for (int i = 0; i < head.length; i++) {
			Label label = new Label(i, 0, head[i], titleFormat);
			sheet.addCell(label);
		}
		return sheet;
	}

	/**
	 * 写数据
	 * 
	 * @param sheet
	 * @param data
	 *            数据；
	 * @param row
	 *            行数（表示数据写入的目标行);
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private static void writeData(WritableSheet sheet, String[] data, int row)
			throws RowsExceededException, WriteException {
		for (int i = 0; i < data.length; i++) {
			sheet.setColumnView(i, 20);
			Label label = new Label(i, row, data[i], dateFormat);
			sheet.addCell(label);
		}
	}
	
	public static void  main(String[] args){
	
	}
}

