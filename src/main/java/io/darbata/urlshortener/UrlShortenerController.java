package io.darbata.urlshortener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class UrlShortenerController {

    private static final Logger log = LoggerFactory.getLogger(UrlShortenerController.class);
    private final UrlShortenerService service;

    UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<ShortenUrlResponseDto> handleLongUrl(@RequestBody ShortenUrlRequest body) {
        ShortenUrlResponseDto response = service.shortenUrl(body.url());
        log.info("Shortening URL {}, to Code {}", body.url(), response.code());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{code}")
    ResponseEntity<LongUrlDto> handleShortUrl(@PathVariable String code) {
        // no domain yet, so just return the code
        LongUrlDto dto = service.fetchLongUrl(code);
        log.info("Retrieving URL {}, from Code, {}", dto.url(), code);
        return ResponseEntity.ok().body(dto);
    }
}