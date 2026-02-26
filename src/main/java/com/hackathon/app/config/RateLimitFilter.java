package com.hackathon.app.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

    private final Map<String, Long> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> requestTimestamps = new ConcurrentHashMap<>();
    private static final long MAX_REQUESTS = 100;
    private static final long TIME_WINDOW = 60000; // 1 minute

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String clientIp = req.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        requestTimestamps.putIfAbsent(clientIp, currentTime);
        requestCounts.putIfAbsent(clientIp, 0L);

        if (currentTime - requestTimestamps.get(clientIp) > TIME_WINDOW) {
            requestTimestamps.put(clientIp, currentTime);
            requestCounts.put(clientIp, 0L);
        }

        long count = requestCounts.get(clientIp);
        if (count >= MAX_REQUESTS) {
            res.setStatus(429); // Too Many Requests
            res.getWriter().write("Rate limit exceeded. Try again later.");
            return;
        }

        requestCounts.put(clientIp, count + 1);
        chain.doFilter(request, response);
    }
}
