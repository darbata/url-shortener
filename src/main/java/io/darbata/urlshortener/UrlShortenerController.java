package io.darbata.urlshortener;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class UrlShortenerController {

    private final UrlShortenerService service;

    UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<?> handleLongUrl(@RequestBody String url) {
        String code = service.shortenUrl(url);
        return ResponseEntity.ok().body(code);
    }

    @GetMapping("/{code}")
    ResponseEntity<?> handleShortUrl(@PathVariable String code) {
        // no domain yet, so just return the code
        String url = service.fetchLongUrl(code);
        return ResponseEntity.ok().body(url);
    }
}