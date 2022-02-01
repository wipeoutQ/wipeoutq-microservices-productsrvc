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
package com.cymmetri.common.session;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.HttpHeaders;

@Data
public class Principal implements Serializable {

	public Principal(String tenantId) {
		this.tenantId = StringUtils.lowerCase(tenantId);
	}

	private String userId;

	private String principal;

	private String roles;

	private final String tenantId;

	private String userAgent;

	private String ipAddress;

	private String xForwardedHost;

	private String delegatee;

	private String delegateeId;

	private Boolean isDelegate = Boolean.FALSE;

	private String correlationId;

	Map<String, String> headers;

	public String getToken() {

		if (MapUtils.isNotEmpty(this.headers)) {
			String authorization = this.headers.getOrDefault(HttpHeaders.AUTHORIZATION, "");
			return StringUtils.remove(authorization, "Bearer ");
		}

		return "";
	}

	public String[] getRoles() {
		String str = RegExUtils.removeAll(this.roles, "\\[");
		str = RegExUtils.removeAll(str, "\\]");
		str = RegExUtils.removeAll(str, "\\s");
		str = RegExUtils.removeAll(str, "\\\"");
		return StringUtils.split(str, ",");
	}

}
