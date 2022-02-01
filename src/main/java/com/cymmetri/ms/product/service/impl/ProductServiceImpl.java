/*
 * Copyright (c) 2020 Unotech Software Pvt. Ltd.
 *
 * All Rights Reserved.
 *
 * The software contained on this media is written by  Unotech Software Pvt. Ltd. and
 * is proprietary to and embodies the confidential technology of Unotech Software.
 *
 * The possession or receipt of this information does not convey any right to disclose
 * its contents, reproduce it, or use, or license the use, for manufacture or sale,
 * the information or anything described therein. Any use, disclosure, or reproduction
 * without prior written permission of Unotech Software is strictly prohibited.
 */
package com.cymmetri.ms.product.service.impl;

import java.util.Optional;

import com.cymmetri.common.search.Search;
import com.cymmetri.ms.product.dto.ProductSearch;
import com.cymmetri.ms.product.entity.Product;
import com.cymmetri.ms.product.entity.QProduct;
import com.cymmetri.ms.product.exception.BarcodeExistsException;
import com.cymmetri.ms.product.exception.InvalidArgumentsException;
import com.cymmetri.ms.product.repository.ProductRepository;
import com.cymmetri.ms.product.service.ProductService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product add(Product product) {
		log.info("[add] method called.");

		String barcode = product.getBarcode();
		if (this.barcodeExists(barcode)) {
			log.error("[add] '%s' barcode already exists.", barcode);
			throw new BarcodeExistsException();
		}
		return this.productRepository.save(product);

	}

	private boolean barcodeExists(String barcode) {
		return this.barcodeExists(barcode, null);
	}

	private boolean barcodeExists(String barcode, String id) {

		BooleanBuilder builder = new BooleanBuilder();
		QProduct qProduct = QProduct.product;

		if (StringUtils.isNotBlank(id)) {
			builder.and(qProduct.id.ne(id));
		}

		if (StringUtils.isNotBlank(barcode)) {
			builder.and(qProduct.barcode.eq(barcode));
		}

		Predicate predicate = builder.getValue();

		if (null == predicate) {

			return Boolean.FALSE;
		}

		return this.productRepository.exists(predicate);
	}

	@Override
	public Product findByBarcode(String barcode) {
		log.info("[findbyId] id: '{}' called.", barcode);

		if (StringUtils.isBlank(barcode)) {
			log.error("[findbyId] Invalid user id: '%s'.", barcode);
			throw new InvalidArgumentsException();
		}

		Optional<Product> optional = this.productRepository.findByBarcode(barcode);

		if (optional.isEmpty()) {
			log.error("[findbyId] barcode with id: '%s' not found.", barcode);
			throw new BarcodeExistsException();
		}

		return optional.get();
	}

	public Product findById(String id) {
		log.info("[findbyId] id: '{}' called.", id);

		if (StringUtils.isBlank(id)) {
			log.error("[findbyId] Invalid user id: '%s'.", id);
			throw new InvalidArgumentsException();
		}

		Optional<Product> optional = this.productRepository.findById(id);

		if (optional.isEmpty()) {
			log.error("[findbyId] ean with id: '%s' not found.", id);
			throw new BarcodeExistsException();
		}

		return optional.get();
	}

	@Override
	@Transactional
	public Product update(String barcode, Product product) {
		log.info("[update] method called.");

		try {
			Product savedProduct = this.findByBarcode(barcode);
			savedProduct.copy(product);
			savedProduct = this.productRepository.save(savedProduct);

			return savedProduct;
		}
		catch (Exception exception) {
			throw exception;
		}
	}

	@Override
	public Page<Product> findAll(Search<ProductSearch> search) {
		Pageable pageable = search.getPageRequest();

		Predicate predicate = this.buildPredicate(search.getFilter());

		if (null == predicate) {
			return this.productRepository.findAll(pageable);
		}
		return this.productRepository.findAll(predicate, pageable);
	}

	private Predicate buildPredicate(ProductSearch filter) {
		BooleanBuilder builder = new BooleanBuilder();

		if (filter != null) {
			QProduct qProduct = QProduct.product;

			if (StringUtils.isNotBlank(filter.getBarcode())) {
				builder.and(qProduct.barcode.eq(filter.getBarcode()));
			}
			if (StringUtils.isNotBlank(filter.getBrand())) {
				builder.and(qProduct.brand.containsIgnoreCase(filter.getBrand()));
			}
			if (StringUtils.isNotBlank(filter.getCategory())) {
				builder.and(qProduct.category.containsIgnoreCase(filter.getCategory()));
			}
			if (StringUtils.isNotBlank(filter.getManufacturer())) {
				builder.and(qProduct.manufacturer.containsIgnoreCase(filter.getManufacturer()));
			}
			if (StringUtils.isNotBlank(filter.getTitle())) {
				builder.and(qProduct.title.containsIgnoreCase(filter.getTitle()));
			}

		}
		return builder.getValue();
	}

	@Override
	public String deleteByBarcode(String barcode) {
		if (ObjectUtils.isNotEmpty(this.productRepository.findByBarcode(barcode))) {
			Product data = this.findByBarcode(barcode);
			this.productRepository.deleteById(data.getId());
			return "Product is deleted";
		}
		return null;
	}

}
