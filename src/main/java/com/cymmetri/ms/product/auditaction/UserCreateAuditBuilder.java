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
package com.cymmetri.ms.product.auditaction;

import com.cymmetri.common.audit.AbstractAuditBuilder;

public class UserCreateAuditBuilder extends AbstractAuditBuilder {

	public UserCreateAuditBuilder() {
		super("USER_CREATE", "USER", "");
	}

	/**
	 * "DESC_FAIL" qualifier.
	 */
	public static final String DESC_FAIL = "User: %s create failed.";

	/**
	 * "DESC_PASS" qualifier.
	 */
	public static final String DESC_PASS = "User: %s create success.";

	private String email;

	public final UserCreateAuditBuilder email(String email) {
		this.email = email;
		return this;
	}

	@Override
	protected void process() {

		String description = (this.isSuccess()) ? DESC_PASS : DESC_FAIL;
		this.description(String.format(description, this.email));
	}

}
