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

import java.util.Set;
import java.util.TreeSet;

import com.cymmetri.common.exception.service.ErrorCodeService;

import org.springframework.stereotype.Service;

@Service
public class ErrorCodeServiceImpl implements ErrorCodeService {

	@Override
	public Set<String> list() {

		Set<String> errorcodes = new TreeSet<String>();
		for (ErrorCode er : ErrorCode.values()) {

			errorcodes.add(er.code());
		}

		return errorcodes;
	}

}
