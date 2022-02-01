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
package com.cymmetri.ms.product.exception;

public enum ErrorCode {

	/**
	 * {@code UNKNOWN}.
	 */
	UNKNOWN,

	/**
	 * {@code FORBIDDEN}.
	 */
	FORBIDDEN,

	/**
	 * {@code FORBIDDEN}.
	 */
	UNAUTHORIZED,

	/**
	 * {@code CONNECTION_FAILED}.
	 */
	CONNECTION_FAILED,

	/**
	 * {@code INVALID_ARGUMENTS}.
	 */
	INVALID_ARGUMENTS,

	/**
	 * {@code ALREADY_EXISTS}.
	 */
	ALREADY_EXISTS,

	/**
	 * {@code USER_NOT_FOUND}.
	 */
	USER_NOT_FOUND,

	/**
	 * {@code EAMIL_EXISTS}.
	 */
	EAMIL_EXISTS,

	/**
	 * {@code EAN_EXISTS}.
	 */
	BARCODE_EXISTS,
	/**
	 * {@code ALREADY_ACTIVATED}.
	 */
	ALREADY_ACTIVATED,

	/**
	 * {@code ALREADY_DEACTIVATED}.
	 */
	ALREADY_DEACTIVATED;

	/**
	 * servicePrefix is prefix for all the error-codes from current module, it will help
	 * to identify service-module from API error-code.
	 */
	private String servicePrefix = "SAMPLESRVC";

	public String code() {

		StringBuilder errorCode = new StringBuilder();
		// @formatter:off
		errorCode
		.append(this.servicePrefix)
		.append('.')
		.append(this);
		// @formatter:on
		return errorCode.toString();
	}

}
