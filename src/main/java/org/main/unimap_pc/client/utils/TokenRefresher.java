package org.main.unimap_pc.client.utils;

import lombok.AllArgsConstructor;
import org.main.unimap_pc.client.services.JWTService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class TokenRefresher {
    private final JWTService jwtService;
    private final ScheduledExecutorService scheduler;

    public TokenRefresher(JWTService jwtService) {
        this.jwtService = jwtService;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void startTokenRefreshTask() {
        scheduler.scheduleAtFixedRate(() -> {
            jwtService.refreshTokenService();
        }, 0, 15, TimeUnit.MINUTES);
    }

    public void stopTokenRefreshTask() {
        scheduler.shutdown();
    }
}