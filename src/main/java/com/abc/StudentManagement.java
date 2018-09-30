package com.abc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableJpaAuditing
@EnableAutoConfiguration()
@EntityScan( basePackages = {"com.abc.model"} )
public class StudentManagement extends SpringBootServletInitializer {

		@Autowired
		private DataSource dataSource;
//
//		@Autowired
//		private PlatformTransactionManager transactionManager;
		
		@Override
		 protected SpringApplicationBuilder configure(SpringApplicationBuilder
		 application) {
		 return application.sources(StudentManagement.class);
		 }
		
		public static void main(String[] args) {
			System.out.println("Started application");
			SpringApplication.run(StudentManagement.class, args);
		}

}
