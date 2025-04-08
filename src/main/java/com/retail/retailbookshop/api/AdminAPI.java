package com.retail.retailbookshop.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.retailbookshop.dto.UserCredentialDTO;
import com.retail.retailbookshop.dto.UserDTO;
import com.retail.retailbookshop.exception.RetailBookShopException;
import com.retail.retailbookshop.service.UserService;

@RestController
@RequestMapping("/user/admin")
public class AdminAPI {

	@Autowired
	Environment environment;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	static Log logger = LogFactory.getLog(AdminAPI.class);

	@PostMapping("/login")
	public ResponseEntity<UserDTO> authenticateAdmin(@RequestBody UserCredentialDTO userCredentialDTO)
			throws RetailBookShopException {

		UserDTO userDTO = userService.authenticateUser(userCredentialDTO.getEmail(), userCredentialDTO.getPassword(),
				"ADMIN");
		logger.info(environment.getProperty("AdminApi.USER_LOGGED_IN_SUCCESSFULLY") + userDTO.getEmail());

		return new ResponseEntity<>(userDTO, HttpStatus.OK);

	}

}
