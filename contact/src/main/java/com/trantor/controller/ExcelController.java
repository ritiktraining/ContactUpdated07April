package com.trantor.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trantor.entity.Contact;
import com.trantor.repository.ContactRepository;
import com.trantor.service.ExcelService;
import com.trantor.service.UserExcelExporterService;

@RestController
public class ExcelController {

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ExcelService excelService;

	@GetMapping("/exportDbToExcel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		excelService.listAll(response);
	}

	@PostMapping("/uploadDataFromExceltoDb")
	public void uploadDataFromExcel() {

		excelService.uploadAll();

	}

}
