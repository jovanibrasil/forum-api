package com.jovanibrasil.forum.configurations.swagger;

import java.util.Arrays;

import org.omg.Dynamic.Parameter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jovanibrasil.forum.models.Usuario;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {

	@Bean
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.jovanibrasil.forum"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.ignoredParameterTypes(Usuario.class) // for security
				.globalOperationParameters(Arrays.asList(
						new ParameterBuilder()
							.name("Authorization")
							.description("Header para token JWT")
							.modelRef(new ModelRef("string"))
							.parameterType("header")
							.required(false)
							.build())); // add jwt header parameter
	}
	
}
