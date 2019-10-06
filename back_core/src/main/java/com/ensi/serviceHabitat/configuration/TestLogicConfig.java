package com.ensi.serviceHabitat.configuration;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
@ComponentScan({"com.ensi.serviceHabitat.service"})
@Import({ PersistenceConfig.class })
public class TestLogicConfig {
	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertyConfigurer() throws IOException {
		PropertySourcesPlaceholderConfigurer props = new PropertySourcesPlaceholderConfigurer();
		return props;
	}

}
