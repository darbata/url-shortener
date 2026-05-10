package io.darbata.urlshortener;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ShortenUrlRequest (
    @NotBlank(message = "URL can't be empty")
    @Pattern(regexp = "^https?://.+", message = "Must be a valid HTTP or HTTPS URL")
    String url
) { }