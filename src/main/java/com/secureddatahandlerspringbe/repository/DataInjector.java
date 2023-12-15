package com.secureddatahandlerspringbe.repository;

import java.io.FileInputStream;

import com.secureddatahandlerspringbe.entity.Book;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataInjector implements CommandLineRunner {
	
	public Logger log = LoggerFactory.getLogger(this.getClass());
	
	private BookRepository bookRepository;
	@Autowired
	public void setEmployeeService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void run(String... args) throws Exception {				// data loader from Excel file...
		log.info("CommandLineRunner -- Data Injector starts >>");

		if (bookRepository.findAll().isEmpty()) {
			String fileLocation = "Books.xlsx";

			Workbook workbook = new XSSFWorkbook(new FileInputStream(fileLocation));
			Sheet sheet = workbook.getSheet("books");

			for (Row row : sheet) {
				log.info(">> data row of " + row.getCell(0).getStringCellValue());
				if (row.getRowNum() != 0) {
					bookRepository.save(
							new Book(
									row.getCell(0).getStringCellValue(),
									row.getCell(1).getStringCellValue(),
									row.getCell(2).getStringCellValue().substring(0, 120) + "...",
									row.getCell(3).getStringCellValue().substring(0, 120) + "...",
									row.getCell(4).getStringCellValue().substring(0, 120) + "..."
							)
					);
				}
			}
		}
		
		
//		// The mere printing of Excel data - as a TEST of it working well
//		for (Row row : sheet) {
//			if (row.getRowNum() == 0) {
//				log.info("-- Header --");
//				log.info(row.toString());
//			} else {
//				for (Cell cell : row) {
//					if (cell.getCellType() == CellType.STRING)
//						log.info(String.valueOf(cell.getColumnIndex())+" - "+cell.getStringCellValue());
//					if (cell.getCellType() == CellType.NUMERIC)
//						log.info(String.valueOf(cell.getColumnIndex())+" - "+String.valueOf((int) cell.getNumericCellValue()));
//				}
//			}
//		}
		
		log.info("CommandLineRunner -- Data Injector ends <<");
	}

}
