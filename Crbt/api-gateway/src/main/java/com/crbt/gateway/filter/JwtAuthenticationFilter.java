package com.crbt.gateway.filter;

import java.security.Key;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private static final String SECRET =
            "crbt-secret-key-crbt-secret-key-crbt-secret-key";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String authHeader =
                exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Allow auth APIs
        if (exchange.getRequest().getURI().getPath().startsWith("/auth")) {
            return chain.filter(exchange);
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.error(new RuntimeException("Missing Authorization Header"));
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authHeader.substring(7))
                .getBody();

        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(r -> r
                        .header("X-USER-ID", claims.get("userId").toString())
                        .header("X-ROLE", claims.get("role").toString()))
                .build();

        return chain.filter(modifiedExchange);
    }
}