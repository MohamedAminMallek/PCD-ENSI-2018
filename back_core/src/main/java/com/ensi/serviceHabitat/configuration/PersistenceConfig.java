package com.ensi.serviceHabitat.configuration;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:persistence.properties")
@ComponentScan("com.ensi.serviceHabitat.persistence")
@EnableJpaRepositories("com.ensi.serviceHabitat.persistence")
@EnableTransactionManagement
public class PersistenceConfig {
	
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(Environment env) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory(env).getObject());
		return transactionManager;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(Environment env) {
		return new JdbcTemplate(dataSource(env));
	}

	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource(Environment env) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("database.driver"));
		dataSource.setUrl(env.getProperty("database.url"));
		dataSource.setUsername(env.getProperty("database.username"));
		dataSource.setPassword(env.getProperty("database.password"));
		return dataSource;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(Environment env) {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource(env));
		entityManagerFactoryBean.setPackagesToScan(new String[] { "com.ensi.serviceHabitat.entity" });
		entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setJpaProperties(hibernateProperties(env));
		return entityManagerFactoryBean;
	}

	@SuppressWarnings("serial")
	Properties hibernateProperties(final Environment env) {
		return new Properties() {
			{
				//setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
				setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));	
				setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
				setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
				setProperty("hibernate.use_sql_comments", env.getProperty("hibernate.use_sql_comments"));
				setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
				
			}
		};
	}
	

}
