package com.retail.retailbookshop;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

import com.retail.retailbookshop.document.Order;
import com.retail.retailbookshop.document.User;

@Configuration
public class CouchBaseConfig extends AbstractCouchbaseConfiguration {

	@Autowired
	Environment environment;

	@Autowired
	private ApplicationContext context;

	@Override
	public String getConnectionString() {

		return environment.getProperty("couchbase.url");
	}

	@Override
	public String getUserName() {

		return environment.getProperty("couchbase.username");
	}

	@Override
	public String getPassword() {

		return environment.getProperty("couchbase.password");
	}

	@Override
	public String getBucketName() {

		return environment.getProperty("couchbase.bucket.bookinventory");
	}

	@Override
	protected void configureRepositoryOperationsMapping(RepositoryOperationsMapping mapping) {
		try {
			mapping.mapEntity(Order.class, getCouchbaseTemplate(environment.getProperty("couchbase.bucket.order")));
			mapping.mapEntity(User.class, getCouchbaseTemplate(environment.getProperty("couchbase.bucket.user")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private CouchbaseTemplate getCouchbaseTemplate(String bucketName) throws Exception {

		CouchbaseTemplate template = new CouchbaseTemplate(couchbaseClientFactory(bucketName),
				mappingCouchbaseConverter(couchbaseMappingContext(customConversions()),
						new CouchbaseCustomConversions(Collections.emptyList())));

		template.setApplicationContext(context);
		return template;
	}

	private CouchbaseClientFactory couchbaseClientFactory(String bucketName) {

		return new SimpleCouchbaseClientFactory(couchbaseCluster(couchbaseClusterEnvironment()), bucketName,
				this.getScopeName());

	}

}
