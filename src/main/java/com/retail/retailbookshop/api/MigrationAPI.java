package com.retail.retailbookshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.retailbookshop.service.MigrationService;

@RestController
@RequestMapping("migration")
public class MigrationAPI {

	@Autowired
	MigrationService migrationService;

	@PostMapping()
	public ResponseEntity<String> migration(@RequestBody List<String> data) throws Exception {

		String msg = migrationService.migrate(data.get(0), data.get(1));

		return new ResponseEntity<>(msg, HttpStatus.OK);

	}

}
