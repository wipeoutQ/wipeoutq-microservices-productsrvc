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
package com.wipeoutq.common.session;

import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.WebUtils;

public final class HeaderUtils {

	/** The HTTP {@code Tenant} header field name. */
	public static final String TENANT_HEADER = "Tenant";

	/** The HTTP {@code User} header field name. */
	public static final String USER_HEADER = "UserId";

	/** The HTTP {@code User} header field name. */
	public static final String PRINCIPAL_HEADER = "Principal";

	/** The HTTP {@code Role} header field name. */
	public static final String ROLES_HEADER = "Roles";

	/** The HTTP {@code Role} header field name. */
	public static final String CLIENT_IP_HEADER = "ClientIp";

	/** The HTTP {@code Role} header field name. */
	public static final String DELEGATEE_HEADER = "Delegatee";

	/** The HTTP {@code Role} header field name. */
	public static final String DELEGATEE_ID_HEADER = "DelegateeId";

	/** The HTTP {@code Role} header field name. */
	public static final String DELEGATE_ROLE = "DELEGATE";

	/** The HTTP {@code X-FORWARDED-TENANT} header field name. */
	public static final String X_FORWARDED_TENANT = "X-FORWARDED-TENANT";

	/** The HTTP {@code X-FORWARDED-HOST} header field name. */
	public static final String X_FORWARDED_HOST = "X-FORWARDED-HOST";

	/** The HTTP {@code X_FORWARDED_PROTO} header field name. */
	public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";

	/** The HTTP {@code CORRELATION_HEADER} header field name. */
	public static final String CORRELATION_HEADER = "Correlation";

	private HeaderUtils() {

	}

	public static String getCaseInsensitiveHeader(final HttpServletRequest request, final String lowerCaseHeaderName) {
		String retVal = null;
		if (request.getHeaderNames() != null) {
			final Enumeration<String> names = request.getHeaderNames();
			while (names.hasMoreElements()) {
				final String name = names.nextElement();
				if (name != null) {
					if (name.toLowerCase().equals(lowerCaseHeaderName)) {
						retVal = request.getHeader(name);
						break;
					}
				}
			}
		}
		return retVal;
	}

	public static String getClientIP(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HeaderUtils.CLIENT_IP_HEADER);
	}

	public static String getUserId(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HeaderUtils.USER_HEADER);
	}

	public static String getPrincipal(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HeaderUtils.PRINCIPAL_HEADER);
	}

	public static String getTenantId(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HeaderUtils.TENANT_HEADER);
	}

	public static String getRoles(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HeaderUtils.ROLES_HEADER);
	}

	public static String getUserAgent(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HttpHeaders.USER_AGENT);
	}

	public static String getAuthorization(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
	}

	public static String getDelegatee(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HeaderUtils.DELEGATEE_HEADER);
	}

	public static String getDelegateeId(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HeaderUtils.DELEGATEE_ID_HEADER);
	}

	public static String getXForwardedHost(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HeaderUtils.X_FORWARDED_HOST);
	}

	public static String getXForwardedProtocal(final HttpServletRequest httpRequest) {
		return httpRequest.getHeader(HeaderUtils.X_FORWARDED_PROTO);
	}

	public static String getCorrelationId(final HttpServletRequest httpRequest) {
		String correlationId = httpRequest.getHeader(HeaderUtils.CORRELATION_HEADER);

		if (StringUtils.isBlank(correlationId)) {
			Cookie cookie = WebUtils.getCookie(httpRequest, HeaderUtils.CORRELATION_HEADER);
			if (Objects.nonNull(cookie)) {
				correlationId = cookie.getValue();
			}
		}

		return correlationId;
	}

	public static String getXForwardedTenant(final HttpServletRequest httpRequest) {
		final String tenant = httpRequest.getHeader(HeaderUtils.X_FORWARDED_TENANT);
		if (StringUtils.isEmpty(tenant)) {
			final String xForwardedHost = getXForwardedHost(httpRequest);
			if (StringUtils.isNotEmpty(xForwardedHost)) {
				String[] split = xForwardedHost.split("\\.");
				if (ArrayUtils.getLength(split) >= 0) {
					return split[0];
				}
			}
		}
		return tenant;
	}

}
