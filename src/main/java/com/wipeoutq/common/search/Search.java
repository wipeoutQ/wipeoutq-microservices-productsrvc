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
package com.wipeoutq.common.search;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Data
public class Search<T extends SearchSupport> {

	@ApiModelProperty(notes = "Page Number", required = true, value = "0", example = "0")
	private Integer pageNumber = 0;

	@ApiModelProperty(notes = "Page Size", required = true, value = "10", example = "10")
	private Integer pageSize = 10;

	@ApiModelProperty(notes = "keyword", required = true, value = "", example = "")
	private String keyword;

	@Valid
	private T filter;

	@ApiModelProperty(notes = "Sort On", example = "[\"id\"]")
	private String[] sortOn;

	@ApiModelProperty(notes = "Sort Direction", allowableValues = "ASC, DESC", example = "ASC")
	private SortDirection sortDirection = SortDirection.ASC;

	@JsonIgnore
	public PageRequest getPageRequest() {
		if (this.pageNumber == null || this.pageNumber < 0) {
			this.setPageNumber(0);
		}
		if (this.pageSize == null || this.pageSize < 1) {
			this.setPageSize(10);
		}

		Direction direction = Direction.ASC;
		if (SortDirection.DESC == this.getSortDirection()) {
			direction = Direction.DESC;
		}

		String[] validSortOn = ArrayUtils.isEmpty(this.getSortOn()) ? new String[] { "id" } : this.getSortOn();
		Sort sortBy = Sort.by(direction, validSortOn);

		return PageRequest.of(this.getPageNumber(), this.getPageSize(), sortBy);
	}

}
