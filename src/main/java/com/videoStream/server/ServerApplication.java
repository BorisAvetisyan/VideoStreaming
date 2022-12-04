package com.videoStream.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx =
				new SpringApplicationBuilder( ServerApplication.class ).headless( false ).web(
						WebApplicationType.NONE ).run( args );
	}

}
