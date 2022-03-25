/*
 * Copyright (c) 2020 wipeoutQ Pvt. Ltd.
 *
 * All Rights Reserved.
 *
 * The software contained on this media is written by  wipeoutQ Pvt. Ltd. and
 * is proprietary to and embodies the confidential technology of wipeoutQ.
 *
 * The possession or receipt of this information does not convey any right to disclose
 * its contents, reproduce it, or use, or license the use, for manufacture or sale,
 * the information or anything described therein. Any use, disclosure, or reproduction
 * without prior written permission of wipeoutQ is strictly prohibited.
 */
package com.wipeoutq.ms.product.endpoint;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonView;
import com.wipeoutq.common.search.Search;
import com.wipeoutq.common.view.View;
import com.wipeoutq.ms.product.dto.ProductSearch;
import com.wipeoutq.ms.product.dto.Response;
import com.wipeoutq.ms.product.entity.Product;
import com.wipeoutq.ms.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("add")
	public ResponseEntity<Response> add(@RequestBody Product product) {
		log.info("Registration user called.");
		Product Create = this.productService.add(product);

		Response response = new Response();
		response.setData(Create);
		response.succeed();
		return ResponseEntity.ok(response);
	}

	@GetMapping("{barcode}")
	@JsonView(View.User.class)
	@ApiOperation(value = "Fetches user details for provided user-id.", response = Response.class)
	public ResponseEntity<Response> get(@PathVariable String barcode) {

		Product product = this.productService.findByBarcode(barcode);

		Response response = new Response();
		response.succeed();
		response.setData(product);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{barcode}")
	@JsonView(View.Admin.class)
	@ApiOperation(value = "Updates user details for provided user-id.", response = Response.class)
	public ResponseEntity<Response> update(@PathVariable String barcode, @Valid @RequestBody Product request) {

		log.info("Product update called.");
		Product product = this.productService.update(barcode, request);

		Response response = new Response();
		response.setData(product);
		response.succeed();

		return ResponseEntity.ok(response);
	}

	@PostMapping("list")
	@JsonView(View.Public.class)
	@ApiOperation(value = "List users with public-view.", response = Response.class)
	public ResponseEntity<Response> listPublic(@RequestBody Search<ProductSearch> search) {
		return list(search);
	}

	private ResponseEntity<Response> list(Search<ProductSearch> search) {
		log.info("User search called.");
		Page<Product> page = this.productService.findAll(search);
		Response response = new Response();
		response.succeed();
		response.setData(page);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("{barcode}")
	@JsonView(View.Public.class)
	@ApiOperation(value = "List users with public-view.", response = Response.class)
	public ResponseEntity<Response> delete(@PathVariable String barcode) {
		String product = this.productService.deleteByBarcode(barcode);
		Response response = new Response();
		response.succeed();
		response.setData(product);
		return ResponseEntity.ok(response);
	}

}
