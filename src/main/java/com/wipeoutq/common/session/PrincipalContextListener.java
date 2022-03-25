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

import java.util.Map;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;

import org.springframework.http.HttpHeaders;

@Slf4j
@WebListener
@RequiredArgsConstructor
public class PrincipalContextListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
		PrincipalContext.clear();
	}

	@Override
	public void requestInitialized(ServletRequestEvent servletRequestEvent) {

		HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();

		String roles = HeaderUtils.getRoles(request);
		String userId = HeaderUtils.getUserId(request);
		String principal = HeaderUtils.getPrincipal(request);
		String tenantId = HeaderUtils.getTenantId(request);
		String ipAddress = HeaderUtils.getClientIP(request);
		String userAgent = HeaderUtils.getUserAgent(request);
		String authorization = HeaderUtils.getAuthorization(request);
		String delegatee = HeaderUtils.getDelegatee(request);
		String delegateeId = HeaderUtils.getDelegateeId(request);
		String xForwardedTenant = HeaderUtils.getXForwardedTenant(request);
		String xForwardedHost = HeaderUtils.getXForwardedHost(request);
		String xForwardedProtocal = HeaderUtils.getXForwardedProtocal(request);
		String correlationId = HeaderUtils.getCorrelationId(request);

		Map<String, String> headers = new HashedMap<>(0);
		headers.put(HeaderUtils.USER_HEADER, userId);
		headers.put(HeaderUtils.PRINCIPAL_HEADER, principal);
		headers.put(HeaderUtils.ROLES_HEADER, roles);
		headers.put(HeaderUtils.TENANT_HEADER, tenantId);
		headers.put(HeaderUtils.CLIENT_IP_HEADER, ipAddress);
		headers.put(HttpHeaders.USER_AGENT, userAgent);
		headers.put(HttpHeaders.AUTHORIZATION, authorization);
		headers.put(HeaderUtils.DELEGATEE_HEADER, delegatee);
		headers.put(HeaderUtils.DELEGATEE_ID_HEADER, delegateeId);
		headers.put(HeaderUtils.X_FORWARDED_TENANT, xForwardedTenant);
		headers.put(HeaderUtils.X_FORWARDED_HOST, xForwardedHost);
		headers.put(HeaderUtils.X_FORWARDED_PROTO, xForwardedProtocal);
		headers.put(HeaderUtils.CORRELATION_HEADER, correlationId);

		Principal principalObj = new Principal(tenantId);
		principalObj.setRoles(roles);
		principalObj.setUserId(userId);
		principalObj.setPrincipal(principal);
		principalObj.setHeaders(headers);
		principalObj.setIpAddress(ipAddress);
		principalObj.setUserAgent(userAgent);
		principalObj.setXForwardedHost(xForwardedHost);
		principalObj.setCorrelationId(correlationId);

		boolean isDelegate = ArrayUtils.contains(principalObj.getRoles(), HeaderUtils.DELEGATE_ROLE);

		if (isDelegate) {
			principalObj.setDelegatee(delegatee);
			principalObj.setDelegateeId(delegateeId);
			principalObj.setIsDelegate(Boolean.TRUE);
		}

		PrincipalContext.setPrincipal(principalObj);

		log.debug("Request arrived for tenant: {}", principalObj.getTenantId());
	}

}
