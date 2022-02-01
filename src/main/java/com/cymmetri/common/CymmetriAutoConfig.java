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
package com.cymmetri.common;

import javax.servlet.ServletRequestListener;

import com.cymmetri.common.session.PrincipalContextListener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
@ComponentScan({ "com.cymmetri.common" })
public class CymmetriAutoConfig {

	@Bean
	ServletListenerRegistrationBean<ServletRequestListener> principalContextListener() {
		ServletListenerRegistrationBean<ServletRequestListener> srb = new ServletListenerRegistrationBean<>();
		srb.setListener(new PrincipalContextListener());
		return srb;
	}

}
