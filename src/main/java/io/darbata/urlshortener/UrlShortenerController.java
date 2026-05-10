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
    ResponseEntity<?> handleLongUrl(@RequestBody String url) {
        String code = service.shortenUrl(url);
        log.info("Shortening URL {}, to Code {}", url, code);
        return ResponseEntity.ok().body(code);
    }

    @GetMapping("/{code}")
    ResponseEntity<?> handleShortUrl(@PathVariable String code) {
        // no domain yet, so just return the code
        String url = service.fetchLongUrl(code);
        log.info("Retrieving URL {}, from Code, {}", url, code);
        return ResponseEntity.ok().body(url);
    }
}