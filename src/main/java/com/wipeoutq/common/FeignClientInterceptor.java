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
package com.wipeoutq.common;

import java.util.Map;

import com.wipeoutq.common.session.Principal;
import com.wipeoutq.common.session.PrincipalContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.collections4.MapUtils;

import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {

		Principal principal = PrincipalContext.getPrincipal();
		Map<String, String> headers = MapUtils.emptyIfNull(principal.getHeaders());

		for (Map.Entry<String, String> header : headers.entrySet()) {

			requestTemplate.header(header.getKey(), header.getValue());
		}
	}

}
