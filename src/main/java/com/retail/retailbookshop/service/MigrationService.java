package com.retail.retailbookshop.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;

import com.couchbase.client.java.kv.GetResult;

@Service
public class MigrationService {

	@Autowired
	Environment environment;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${couchbase.url}")
	private String couchurl;

	@Value("${couchbase.username}")
	private String couchusername;

	@Value("${couchbasepassword}")
	private String couchpassword;

	public String migrate(String table, String buck) throws Exception {
		Connection mySQlConnection = null;

		Class.forName("com.mysql.jdbc.Driver");
		mySQlConnection = DriverManager.getConnection(url, username, password);

		Cluster cluster = Cluster.connect(couchurl, couchusername, couchpassword);
		Bucket bucket = cluster.bucket(buck);
		Collection collection = bucket.defaultCollection();

		ResultSet fk = mySQlConnection.getMetaData().getImportedKeys(mySQlConnection.getCatalog(), null, table);

// if there is a foreign key to a table
		if (fk.next()) {

			String refTable = fk.getString("PKTABLE_NAME");
			String foreignKey = fk.getString("FKCOLUMN_NAME");
			String distinctQuery = "SELECT DISTINCT " + foreignKey + " FROM " + table;
			Statement statementFK = mySQlConnection.createStatement();
			ResultSet distinctFK = statementFK.executeQuery(distinctQuery);

			while (distinctFK.next()) {
				JsonArray jsonArray = JsonArray.create();

				String fkQuery = "SELECT * FROM " + table + " WHERE " + foreignKey + " = " + distinctFK.getInt(1);
				Statement statementRole = mySQlConnection.createStatement();
				ResultSet roleRow = statementRole.executeQuery(fkQuery);
				ResultSetMetaData rowMD = roleRow.getMetaData();
				int count = rowMD.getColumnCount();
				while (roleRow.next()) {
					JsonObject jsonObject = JsonObject.create();
					for (int i = 1; i < count; i++) {
						jsonObject.put(rowMD.getColumnName(i), roleRow.getString(i));

					}

					jsonArray.add(jsonObject);
					String documentId = refTable + "_" + distinctFK.getInt(1);

					JsonObject document = null;

					try {
						GetResult getResult = collection.get(documentId);
						document = getResult.contentAsObject();
					}

					catch (Exception e) {
						throw new Exception(environment.getProperty("MIGRATION_SERVICE.REFERENCE_TABLE"));
					}

					document.put(table, jsonArray);

					collection.replace(documentId, document);

				}

			}
			return "Successfully migrated";
		}

// if there is no foriegn key in the table	

		Statement statement = mySQlConnection.createStatement();
		String query = "SELECT * FROM " + table;
		ResultSet row = statement.executeQuery(query);
		ResultSetMetaData rowMD = row.getMetaData();

		int count = rowMD.getColumnCount();

		while (row.next()) {
			JsonObject json = JsonObject.create();
			json.put("_class", environment.getProperty(buck));
			int id = row.getInt(1);

			for (int i = 2; i <= count; i++) {
				json.put(rowMD.getColumnName(i), row.getString(i));

			}

			collection.upsert(table + "_" + id, json);

		}

		return "Successfully Migrated";

	}

}
