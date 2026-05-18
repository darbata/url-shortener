package io.darbata.urlshortener;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service
public class UrlLookupService {
    private final UrlShortenerRepository repository;
    private final CircuitBreaker circuitBreaker;
    private final CacheManager cacheManager;

    public UrlLookupService(UrlShortenerRepository repository, CircuitBreakerFactory<?, ?> circuitBreakerFactory, CacheManager cacheManager) {
        this.repository = repository;
        this.circuitBreaker = circuitBreakerFactory.create("redis"); // circuit breaker on redis service
        this.cacheManager = cacheManager;
    }

    public String fetchLongUrl(String code) {
        Cache cache = cacheManager.getCache("urls");

        // check cache
        String cached = circuitBreaker.run(
                () -> cache == null ? null : cache.get(code, String.class),
                throwable -> null
        );

        // return cached value
        if (cached != null) return cached;

        // fall back to database
        String longUrl = repository.getLongUrl(code).orElseThrow(
            () -> new NoMatchingUrlException("No matching url for code " + code)
        );

        // write to cache
        circuitBreaker.run(
                () -> {
                    if (cache != null) cache.put(code, longUrl);
                    return null;
                },
                throwable -> null
        );

        return longUrl;
    }

}