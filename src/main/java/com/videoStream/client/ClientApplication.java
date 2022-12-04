package com.videoStream.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class ClientApplication {


    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
                new SpringApplicationBuilder( Client.class ).headless( false ).web(
                        WebApplicationType.NONE ).run( args );
    }

}
