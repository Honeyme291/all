package com.project.all;

import com.project.all.config.NettyConfig;
import com.project.all.service.WebSocketNettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AllApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AllApplication.class, args);
    }
    @Autowired
    private WebSocketNettyServer webSocketNettyServer;

    @Autowired
    private NettyConfig nettyConfig;

    @Override
    public void run(String... args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                webSocketNettyServer.start(nettyConfig.getPort());
            }
        }).start();
    }

}
