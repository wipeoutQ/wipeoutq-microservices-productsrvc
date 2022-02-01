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

package com.cymmetri.common.audit;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractAuditBuilder {

	private final String action;

	private final String sourceType;

	private final String targetType;

	private String sourceId;

	private String targetId;

	private Object oldObject;

	private Object newObject;

	private String description;

	private AuditLogResult result = AuditLogResult.FAIL;

	private Map<String, Object> eventAttributes;

	public AbstractAuditBuilder(String action, String sourceType, String targetType) {
		super();

		if (StringUtils.isBlank(action)) {

			new RuntimeException("INVALID_AUDIT_ACTION");
		}

		if (StringUtils.isBlank(sourceType)) {
			new RuntimeException("INVALID_AUDIT_SOURCE_TYPE");
		}

		if (Objects.isNull(targetType)) {
			new RuntimeException("INVALID_AUDIT_TARGET_TYPE");
		}

		this.action = action;
		this.sourceType = sourceType;
		this.targetType = targetType;

	}

	public final AbstractAuditBuilder sourceId(String sourceId) {
		this.sourceId = sourceId;
		return this;
	}

	public final AbstractAuditBuilder targetId(String targetId) {
		this.targetId = targetId;
		return this;
	}

	public final AbstractAuditBuilder oldObject(Object oldObject) {
		this.oldObject = oldObject;
		return this;
	}

	public final AbstractAuditBuilder newObject(Object newObject) {
		this.newObject = newObject;
		return this;
	}

	public final AbstractAuditBuilder description(String description) {
		this.description = description;
		return this;
	}

	public final AbstractAuditBuilder eventAttributes(Map<String, Object> eventAttributes) {
		this.eventAttributes = eventAttributes;
		return this;
	}

	public final AbstractAuditBuilder fail() {
		this.result = AuditLogResult.FAIL;
		return this;
	}

	public final AbstractAuditBuilder succeed() {
		this.result = AuditLogResult.SUCCESS;
		return this;
	}

	public final Audit build() {

		this.process();

		Audit audit = new Audit();

		audit.setAction(this.action);
		audit.setResult(this.result);

		audit.setSourceId(this.sourceId);
		audit.setSourceType(this.sourceType);

		audit.setTargetId(this.targetId);
		audit.setTargetType(this.targetType);

		audit.setOldObject(this.oldObject);
		audit.setNewObject(this.newObject);

		audit.setDescription(this.description);
		audit.setEventAttributes(this.eventAttributes);

		return audit;
	}

	public final boolean isSuccess() {
		return (AuditLogResult.SUCCESS == this.result);
	}

	public final boolean isFail() {
		return (AuditLogResult.FAIL == this.result);
	}

	protected abstract void process();

}
