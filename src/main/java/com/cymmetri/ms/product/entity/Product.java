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
package com.cymmetri.ms.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "product")
public class Product {

	@Id
	private String id;

	private String barcode;

	private String barcode_Type;

	private String title;

	private String brand;

	private String part_Number;

	private String asin;

	private String model;

	private String format;

	private String color;

	private String manufacturer;

	private String features;

	private String energy_Efficiency;

	private String gender;

	private String material;

	private String pattern;

	private String category;

	private String contributors;

	private String release_Date;

	private String description;

	private String images;

	private String reviews;

	private String stores;

	private String retail_Price;

	private String size;

	private String dimensions;

	private String weight;

	private String multipack;

	private String ingredients;

	private String nutrition_Facts;

	private String age_Group;

	private String price;

	public void copy(Product product) {
		this.setTitle(StringUtils.trim(product.getTitle()));
		this.setColor(StringUtils.trim(product.getColor()));
		this.setBarcode_Type(StringUtils.trim(product.getBarcode_Type()));
		this.setBrand(StringUtils.trim(product.getBrand()));
		this.setAsin(StringUtils.trim(product.getAsin()));
		this.setPart_Number(StringUtils.trim(product.getPart_Number()));
		this.setModel(StringUtils.trim(product.getModel()));
		this.setFormat(StringUtils.trim(product.getFormat()));
		this.setManufacturer(StringUtils.trim(product.getManufacturer()));
		this.setFeatures(StringUtils.trim(product.getFeatures()));
		this.setEnergy_Efficiency(StringUtils.trim(product.getEnergy_Efficiency()));
		this.setGender(StringUtils.trim(product.getGender()));
		this.setMaterial(StringUtils.trim(product.getMaterial()));
		this.setPattern(StringUtils.trim(product.getPattern()));
		this.setCategory(StringUtils.trim(product.getCategory()));
		this.setContributors(StringUtils.trim(product.getContributors()));
		this.setRelease_Date(StringUtils.trim(product.getRelease_Date()));
		this.setDescription(StringUtils.trim(product.getDescription()));
		this.setImages(StringUtils.trim(product.getImages()));
		this.setReviews(StringUtils.trim(product.getReviews()));
		this.setStores(StringUtils.trim(product.getStores()));
		this.setRetail_Price(StringUtils.trim(product.getRetail_Price()));
		this.setSize(StringUtils.trim(product.getSize()));
		this.setDimensions(StringUtils.trim(product.getDimensions()));
		this.setWeight(StringUtils.trim(product.getWeight()));
		this.setMultipack(StringUtils.trim(product.getMultipack()));
		this.setIngredients(StringUtils.trim(product.getIngredients()));
		this.setNutrition_Facts(StringUtils.trim(product.getNutrition_Facts()));
		this.setAge_Group(StringUtils.trim(product.getAge_Group()));

	}

}
