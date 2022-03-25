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
package com.wipeoutq.ms.product;

import com.wipeoutq.common.WipeoutqAutoConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ WipeoutqAutoConfig.class })
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

}
