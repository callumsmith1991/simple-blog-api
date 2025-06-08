package com.example.simple_blog_rest_api;

import org.springframework.boot.SpringApplication;

public class TestSimpleBlogRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(SimpleBlogRestApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
