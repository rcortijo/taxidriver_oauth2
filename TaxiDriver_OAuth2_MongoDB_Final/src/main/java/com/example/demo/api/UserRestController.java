package com.example.demo.api;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.UserController;
import com.example.demo.application.UserDTO;
import com.example.demo.utilities.ConstantUtilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@CrossOrigin
@RequestMapping(value="/users")
public class UserRestController {
	
	public UserRestController() throws Exception {
		new UserController().createAdminUser();
	}

	protected String toJson(Object o) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(o);
	}

	protected HttpHeaders initHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}

	@PreAuthorize("hasRole('" + ConstantUtilities.ADMIN_ROLE + "')")
	@RequestMapping(value = "/customers",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> registerCustomer(@RequestBody String jUser) throws Exception {
		Gson gson = new Gson();
		UserDTO user = gson.fromJson(jUser, UserDTO.class);

		new UserController().registCustomer(user);

		return new ResponseEntity<String>(initHeader(), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('" + ConstantUtilities.ADMIN_ROLE + "')")
	@RequestMapping(value = "/drivers",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> registerDriver(@RequestBody String jUser) throws Exception {
		Gson gson = new Gson();
		UserDTO user = gson.fromJson(jUser, UserDTO.class);

		new UserController().registDriver(user);

		return new ResponseEntity<String>(initHeader(), HttpStatus.CREATED);
	}


	@PreAuthorize("hasAnyRole('" + ConstantUtilities.CUSTOMER_ROLE + "','" + ConstantUtilities.DRIVER_ROLE + "')")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> getAllUsers() throws Exception {

		List<UserDTO> users = new UserController().getAllUsers();

		return new ResponseEntity<String>(toJson(users), initHeader(), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('" + ConstantUtilities.CUSTOMER_ROLE + "','" + ConstantUtilities.DRIVER_ROLE + "')")
	@RequestMapping(value = "/customers",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> getAllCustomers() throws Exception {

		List<UserDTO> users = new UserController().getAllCustomers();

		return new ResponseEntity<String>(toJson(users), initHeader(), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('" + ConstantUtilities.CUSTOMER_ROLE + "','" + ConstantUtilities.DRIVER_ROLE + "')")
	@RequestMapping(value = "/drivers",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> getAllDrivers() throws Exception {

		List<UserDTO> users = new UserController().getAllDrivers();

		return new ResponseEntity<String>(toJson(users), initHeader(), HttpStatus.CREATED);
	}

}
