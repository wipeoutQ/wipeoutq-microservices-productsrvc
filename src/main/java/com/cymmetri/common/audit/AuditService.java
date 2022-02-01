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
package com.cymmetri.common.audit;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.cymmetri.common.session.Principal;
import com.cymmetri.common.session.PrincipalContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

	@Autowired
	private AuditClient auditClient;

	public void log(Audit audit) {

		Principal principal = PrincipalContext.getPrincipal();

		Map<String, String> source = new HashMap<>(0);
		source.put("userAgent", principal.getUserAgent());
		source.put("ipAddress", principal.getIpAddress());
		source.put("forwardedHost", principal.getXForwardedHost());

		audit.setSourceAttributes(source);
		audit.setRequestorId(principal.getPrincipal());
		audit.setPerformedAt(LocalDateTime.now());
		audit.setCorrelationId(principal.getCorrelationId());

		if (principal.getIsDelegate()) {
			audit.setRequestorId(principal.getDelegatee());
			audit.setBehalfOf(principal.getPrincipal());
			audit.setIsDelegate(Boolean.TRUE);
		}

		this.auditClient.log(audit);
	}

}
