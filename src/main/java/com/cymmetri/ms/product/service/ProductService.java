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
package com.cymmetri.ms.product.service;

import com.cymmetri.common.search.Search;
import com.cymmetri.ms.product.dto.ProductSearch;
import com.cymmetri.ms.product.entity.Product;

import org.springframework.data.domain.Page;

public interface ProductService {

	Product add(Product product);

	Product findByBarcode(String id);

	Product update(String ean, Product request);

	Page<Product> findAll(Search<ProductSearch> search);

	String deleteByBarcode(String barcode);

}
