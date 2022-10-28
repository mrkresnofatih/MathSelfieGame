package com.mrkresnofatih.mathselfieapp.configs;

import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class PusherConfig {
    @Value("${infra.pusher.app-id}")
    private String pusherAppId;

    @Value("${infra.pusher.key}")
    private String pusherKey;

    @Value("${infra.pusher.secret}")
    private String pusherSecret;

    @Value("${infra.pusher.region}")
    private String pusherRegion;

    @Value("${infra.pusher.encrypted}")
    private String pusherEncryption;

    @Bean
    public Pusher pusherClient() {
        var pusher = new Pusher(pusherAppId, pusherKey, pusherSecret);
        pusher.setCluster(pusherRegion);
        pusher.setEncrypted(Objects.equals(pusherEncryption, "true"));
        return pusher;
    }
}
