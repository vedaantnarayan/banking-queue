/**
 * 
 */
package com.turvo.bankingqueue.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author vedantn
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
	private Environment env;

	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(Boolean.parseBoolean(env.getProperty("swagger.enable")))
				.select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
}
