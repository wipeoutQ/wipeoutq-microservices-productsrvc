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
package com.cymmetri.ms.product;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SampleApplication.class);
	}

}
