package io.darbata.urlshortener;

import jakarta.validation.Valid;
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
    ResponseEntity<ShortenUrlResponseDto> handleLongUrl(@RequestBody @Valid ShortenUrlRequest body) {
        ShortenUrlResponseDto response = service.shortenUrl(body.url());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{code}")
    ResponseEntity<LongUrlDto> handleShortUrl(@PathVariable String code) {
        // no domain yet, so just return the code
        LongUrlDto dto = service.handleCode(code);
        return ResponseEntity.ok().body(dto);
    }
}