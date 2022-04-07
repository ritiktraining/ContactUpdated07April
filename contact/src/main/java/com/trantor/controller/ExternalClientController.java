package com.trantor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trantor.dto.ContactDto;
import com.trantor.service.ExternalClientService;

@RestController
@RequestMapping("/contact")
public class ExternalClientController {

	@Autowired
	private ExternalClientService externalClientService;

	@GetMapping("/getContactData/{getDatafromDbOrExternalApi}")
	public ResponseEntity<String> fetchAll(@PathVariable("getDatafromDbOrExternalApi") Integer flag) {
		return externalClientService.fetchAll(flag);
	}

	@PostMapping("/postContactData/{postDatatoDbOrExternalApi}")
	public ResponseEntity<ContactDto> postData(@PathVariable("postDatatoDbOrExternalApi") Integer flag,
			@RequestBody ContactDto contactDto) {
		return externalClientService.postData(flag, contactDto);
	}

	@DeleteMapping("/deleteContactData/{deleteDataFromDbOrExternalApi}/{ID}")
	public ResponseEntity<ContactDto> deleteData(@PathVariable("deleteDataFromDbOrExternalApi") Integer flag,
			@PathVariable(value = "ID", required = false) Integer ID) {
		return externalClientService.deleteData(flag, ID);
	}

	@GetMapping("/fetchContactDataByFirstName/name/{fetchFirstNameFromDbOrExternalApi}/{name}")
	public ResponseEntity<ContactDto> fetchbyfirstName(@PathVariable("fetchFirstNameFromDbOrExternalApi") Integer flag,
			@PathVariable("name") String firstName) {
		return externalClientService.findByName(flag, firstName);
	}

}
