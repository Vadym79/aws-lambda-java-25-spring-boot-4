package software.amazonaws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypesScanner;
import org.springframework.core.io.ResourceLoader;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;


@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement
public class PersistenceJPAConfig {
	
	private static final String AURORA_DSQL_CLUSTER_ENDPOINT = System.getenv("AURORA_DSQL_CLUSTER_ENDPOINT");
	
	private static final String JDBC_URL = "jdbc:aws-dsql:postgresql://"
			+ AURORA_DSQL_CLUSTER_ENDPOINT
			+ ":5432/postgres?sslmode=verify-full&sslfactory=org.postgresql.ssl.DefaultJavaSSLFactory"
			+ "&token-duration-secs=900";

	private static final String PACKAGE_TO_SCAN_FOR_ENTITIES= "software.amazonaws.example.product.entity";

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(PersistenceManagedTypes persistenceManagedTypes, DataSource dataSource) {
		var em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(PACKAGE_TO_SCAN_FOR_ENTITIES);
		em.setManagedTypes(persistenceManagedTypes);

		var vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		return em;
	}

	@Bean
	public DataSource dataSource() {
		/*
		var dataSource = new DriverManagerDataSource();
		dataSource.setUrl(JDBC_URL);
		dataSource.setUsername("admin");
		return dataSource,
		*/
	
		var config = new HikariConfig();
		config.setUsername("admin");
		config.setJdbcUrl(JDBC_URL);
		config.setMaxLifetime(1500 * 1000); // pool connection expiration time in milli seconds, default 30
		config.setMaximumPoolSize(1); // default is 10
		return new HikariDataSource(config);
	}

	
    @Bean
    public PersistenceManagedTypes persistenceManagedTypes(ResourceLoader resourceLoader) {
        return new PersistenceManagedTypesScanner(resourceLoader)
                .scan(PACKAGE_TO_SCAN_FOR_ENTITIES);
    }
	
	@Bean
	public PlatformTransactionManager transactionManager(PersistenceManagedTypes persistenceManagedTypes, DataSource datasource) {
		var transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory(persistenceManagedTypes, datasource).getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}