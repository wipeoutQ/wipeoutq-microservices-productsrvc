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

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.ConstraintViolationException;

import com.cymmetri.ms.product.dto.Response;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.error("Exception: ", ex);
		return this.buildResponseEntity(new Response(ex), status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error("Exception: ", ex);
		String message = "DATA_VALIDATION_ERROR";
		log.error("Error message: {} - {}: ", message, ex.getMessage());
		CustomException exception = new InvalidArgumentsException();
		return buildResponseEntity(new Response(exception), exception.getHttpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		log.error("Message not Readable Exception: ", ex);
		CustomException exception = new InvalidArgumentsException();
		return buildResponseEntity(new Response(exception), exception.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> exception(Exception ex) {
		log.error("Exception: ", ex);
		return buildResponseEntity(new Response(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {

		log.error("Constraint violation Exception", ex);
		CustomException exception = new InvalidArgumentsException();
		return buildResponseEntity(new Response(exception), exception.getHttpStatus());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		log.error("IllegalArgumentException Exception", ex);
		CustomException exception = new InvalidArgumentsException();
		return buildResponseEntity(new Response(exception), exception.getHttpStatus());
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<Object> feignException(FeignException ex) {
		log.error("FeignException: ", ex);
		Map<String, Object> map = new JSONObject(ex.contentUTF8()).toMap();

		return ResponseEntity.status(ex.status()).body(map);
	}

	@ExceptionHandler({ feign.FeignException.Unauthorized.class, Unauthorized.class })
	public ResponseEntity<Object> unauthorizedFeignException(final Exception ex) {
		log.error("Exception: ", ex);
		CustomException exception = new UnauthorizedException();
		return buildResponseEntity(new Response(exception), exception.getHttpStatus());
	}

	@ExceptionHandler({ feign.FeignException.Forbidden.class, Forbidden.class })
	public ResponseEntity<Object> forbiddenFeignException(final feign.FeignException.Forbidden ex) {
		log.error("Exception: ", ex);
		CustomException exception = new ForbiddenException();
		return buildResponseEntity(new Response(exception), exception.getHttpStatus());
	}

	@ExceptionHandler({ feign.RetryableException.class, ConnectException.class })
	public ResponseEntity<Object> feignRetryableExceptionException(final Exception ex) {
		log.error("FeignRetryableExceptionException: ", ex);
		CustomException exception = new ConnectionFailedException();
		return buildResponseEntity(new Response(exception), exception.getHttpStatus());
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<Object> restTemplateexception(HttpClientErrorException ex) {
		log.error("Exception: ", ex);
		Map<String, Object> map = new HashMap<String, Object>(0);
		if (!HttpStatus.UNAUTHORIZED.equals(ex.getStatusCode())) {
			map = new JSONObject(ex.getResponseBodyAsString(Charset.defaultCharset())).toMap();
		}
		return ResponseEntity.status(ex.getStatusCode()).body(map);
	}

	@ExceptionHandler({ InvalidKeyException.class, NoSuchAlgorithmException.class, UnsupportedEncodingException.class,
			NoSuchPaddingException.class, IllegalBlockSizeException.class, BadPaddingException.class })
	public ResponseEntity<Object> bcException(Exception ex) {
		log.error("Exception: ", ex);
		CustomException exception = new UnauthorizedException();
		return buildResponseEntity(new Response(exception), exception.getHttpStatus());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<Object> buildResponseEntity(DuplicateKeyException ex) {
		log.error("Error message: {} - {}: ", "DuplicateKeyException", ex.getMessage());
		log.error("Error: ", ex);
		CustomException exception = new DuplicateDataException();
		return buildResponseEntity(new Response(exception), exception.getHttpStatus());
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Object> customException(CustomException ex) {
		log.error("Exception: ", ex);
		return buildResponseEntity(new Response(ex), ex.getHttpStatus());
	}

	private ResponseEntity<Object> buildResponseEntity(Response response, HttpStatus status) {
		return new ResponseEntity<>(response, status);
	}

}
