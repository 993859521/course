package com.course.business;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan("com.course")
@MapperScan("com.course.service.dto")

public class BusinessApplication {

	private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BusinessApplication.class);
		Environment env = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("System地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
	}

}
