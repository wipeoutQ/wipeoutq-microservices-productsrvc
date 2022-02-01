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

public final class PrincipalContext {

	/**
	 * Instantiates a new TenantContext. Private to prevent instantiation.
	 */
	private PrincipalContext() {
		// Throw an exception if this ever *is* called
		throw new AssertionError("Instantiating utility class.");
	}

	private static ThreadLocal<Principal> principalLocal = new ThreadLocal<Principal>() {
		@Override
		protected Principal initialValue() {
			return new Principal(null);
		}
	};

	public static void setPrincipal(Principal tenant) {
		principalLocal.set(tenant);
	}

	public static Principal getPrincipal() {
		return principalLocal.get();
	}

	public static void clear() {
		principalLocal.remove();
	}

}
