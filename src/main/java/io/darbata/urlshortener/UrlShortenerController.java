package io.darbata.urlshortener;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class UrlShortenerController {

    private final UrlShortenerService service;

    UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping
    String handleLongUrl(@RequestBody String url) {
        return service.shortenUrl(url);
    }

    @GetMapping("/{code}")
    String handleShortUrl(@PathVariable String code) {
        return service.fetchLongUrl(code);
    }
}