package com.sfs.metahive.view;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.sfs.metahive.model.DataGrid;

/**
 * The Class ExcelTemplateView.
 */
public class ExcelTemplateView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DataGrid dataGrid = (DataGrid) model.get("dataGrid");
		
		String sheetName = "Sheet 1";
		if (StringUtils.isNotBlank(dataGrid.getTitle())) {
			sheetName = dataGrid.getTitle();
		}
		
		HSSFSheet sheet = workbook.createSheet(sheetName);

		int rowNum = 0;
		int maxColumnCount = 0;
		
		if (dataGrid.getHeaderFields().size() > 0) {
			HSSFRow header = sheet.createRow(rowNum);
			rowNum++;
			
			maxColumnCount = dataGrid.getHeaderFields().size();			
			
			int i = 0;
			for (String field : dataGrid.getHeaderFields()) {
				header.createCell(i).setCellValue(field);
				i++;
			}		
		}

		for (int y = 0; y < dataGrid.getRowCount(); y++) {
			HSSFRow row = sheet.createRow(rowNum++);
			
			List<String> rowData = dataGrid.getRowFields(y);
			
			if (rowData.size() > maxColumnCount) {
				maxColumnCount = rowData.size();
			}
			
			int x = 0;
					
			for (String data : rowData) {
				row.createCell(x).setCellValue(data);
				x++;
			}
		}
		
		for (int i = 0; i < maxColumnCount; i++) {
			sheet.autoSizeColumn(i);
		}
	}
}