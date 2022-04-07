package com.trantor.service;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.trantor.dto.ContactDto;
import com.trantor.entity.Contact;

@Service
public class ExternalClientService {

	@Value("${uri}")
	private String uri;

	@Autowired
	private ContactServiceImpl contactServiceImpl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<String> fetchAll(Integer flag) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			if (flag == 1) {
				return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			} else if (flag == 0) {
				return new ResponseEntity(contactServiceImpl.fetchContactList(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return restTemplate.exchange("http://10.50.2.206:9292/findAll", HttpMethod.GET, entity, String.class);
		}

		return new ResponseEntity<>("Please enter either 0 or 1", HttpStatus.OK);

	}

	public ResponseEntity<ContactDto> postData(Integer flag, @RequestBody ContactDto contactDto) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		Contact contact = modelMapper.map(contactDto, Contact.class);

		HttpEntity<Contact> entity = new HttpEntity<>(contact, headers);

		if (flag == 1) {
			return restTemplate.exchange("http://10.50.2.204:8080/contact/saveDataApi/0", HttpMethod.POST, entity,
					ContactDto.class);
		} else if (flag == 0) {
			return new ResponseEntity<ContactDto>(contactServiceImpl.saveContact(contactDto), HttpStatus.OK);
		}

		return new ResponseEntity<ContactDto>(HttpStatus.OK);
	}

	public ResponseEntity<ContactDto> deleteData(Integer flag, Integer ID) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		if (flag == 1) {
			return restTemplate.exchange("http://10.50.2.204:8080/contact/delete/0/" + ID, HttpMethod.DELETE, entity,
					ContactDto.class);
		} else if (flag == 0) {
			return new ResponseEntity<ContactDto>(contactServiceImpl.deleteContact(ID), HttpStatus.OK);
		}

		return null;
	}

	public ResponseEntity<ContactDto> findByName(Integer flag, String firstName) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		if (flag == 1) {
			return restTemplate.exchange("http://10.50.2.204:8080/contact/" + firstName, HttpMethod.GET, entity,
					ContactDto.class);
		} else if (flag == 0) {
			return new ResponseEntity<ContactDto>(contactServiceImpl.fetchbyfirstName(firstName), HttpStatus.OK);
		}

		return null;
	}

}
