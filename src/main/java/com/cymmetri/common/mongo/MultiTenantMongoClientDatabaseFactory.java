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
package com.cymmetri.common.mongo;

import com.cymmetri.common.session.Principal;
import com.cymmetri.common.session.PrincipalContext;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Slf4j
public class MultiTenantMongoClientDatabaseFactory extends SimpleMongoClientDatabaseFactory {

	private MongoProperties mongoProperties;

	private String tenantDBPrefix;

	public MultiTenantMongoClientDatabaseFactory(MongoProperties mongoProperties, String tenantDBPrefix) {
		super(mongoClient(mongoProperties), mongoProperties.getMongoClientDatabase());

		this.mongoProperties = mongoProperties;
		this.tenantDBPrefix = tenantDBPrefix;
	}

	@Override
	public MongoDatabase getMongoDatabase() throws DataAccessException {
		Principal principal = PrincipalContext.getPrincipal();
		String tenantId = principal.getTenantId();

		String databaseName;
		if (StringUtils.isBlank(tenantId)) {

			databaseName = this.mongoProperties.getMongoClientDatabase();
		}
		else {
			databaseName = StringUtils.lowerCase(StringUtils.join(this.tenantDBPrefix, tenantId.trim()));
		}

		log.debug("getMongoDatabase: {}", databaseName);

		return this.getMongoDatabase(databaseName);
	}

	private static MongoClient mongoClient(final MongoProperties properties) {

		String uri = properties.getUri();
		char[] password = properties.getPassword();
		String databaseName = properties.getMongoClientDatabase();
		String username = StringUtils.defaultIfBlank(properties.getUsername(), "");

		MongoClientSettings.Builder builder = MongoClientSettings.builder();

		if (StringUtils.isNotBlank(username) && ArrayUtils.isNotEmpty(password)) {
			MongoCredential mongoCredential = MongoCredential.createCredential(username, databaseName, password);
			builder.credential(mongoCredential);
		}

		builder.applyConnectionString(new ConnectionString(uri));

		MongoClientSettings settings = builder.build();

		return MongoClients.create(settings);
	}

}
