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
package com.cymmetri.ms.product.dto;

import java.util.Date;

import com.cymmetri.ms.product.exception.CustomException;
import com.cymmetri.ms.product.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Response {

	public Response() {
		this.timestamp = new Date();
	}

	public Response(Throwable exception) {
		this();
		this.fail();

		String errorCodeString = ErrorCode.UNKNOWN.code();

		if (exception instanceof CustomException) {

			CustomException ex = (CustomException) exception;
			ErrorCode errorCode = ex.getErrorCode();

			errorCodeString = errorCode.code();
		}

		this.setErrorCode(errorCodeString);
	}

	public Response(Throwable exception, String message) {
		this(exception);
		this.setMessage(message);
	}

	private Boolean success;

	private Object data;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy hh:mm:ss")
	private Date timestamp;

	// @JsonInclude(Include.NON_EMPTY)
	private String message;

	// @JsonInclude(Include.NON_EMPTY)
	private String errorCode;

	public Boolean getSuccess() {
		return this.success;
	}

	private void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	private void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void fail() {
		this.setSuccess(Boolean.FALSE);
	}

	public void succeed() {
		this.setSuccess(Boolean.TRUE);
	}

}
