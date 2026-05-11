package io.darbata.urlshortener;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final UrlShortenerRepository repository;

    public CacheService(UrlShortenerRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value="urls", key="#code")
    public String fetchLongUrl(String code) {
        return repository.getLongUrl(code).orElseThrow(
                () -> new NoMatchingUrlException("No matching url for code " + code)
        );
    }
}