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
package com.cymmetri.ms.product.auditaction;

import com.cymmetri.common.audit.AbstractAuditBuilder;

public class UserActivateAuditBuilder extends AbstractAuditBuilder {

	public UserActivateAuditBuilder() {
		super("USER_ACTIVATE", "USER", "");
	}

	/**
	 * "DESC_FAIL" qualifier.
	 */
	public static final String DESC_FAIL = "User: %s activation failed.";

	/**
	 * "DESC_PASS" qualifier.
	 */
	public static final String DESC_PASS = "User: %s successfully activated.";

	private String email;

	public final UserActivateAuditBuilder email(String email) {
		this.email = email;
		return this;
	}

	@Override
	protected void process() {

		String description = (this.isSuccess()) ? DESC_PASS : DESC_FAIL;
		this.description(String.format(description, this.email));
	}

}