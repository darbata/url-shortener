package io.darbata.urlshortener;

import com.soundicly.jnanoidenhanced.jnanoid.NanoIdUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
class UrlShortenerService {

    private final UrlShortenerRepository repo;

    UrlShortenerService(UrlShortenerRepository urlShortenerRepository) {
        this.repo = urlShortenerRepository;
    }

    public ShortenUrlResponseDto shortenUrl(String longUrl) {
        int count = 0;
        int max = 3;

        while (true) {
            try {
                // generated potential code
                String generated = generateCode();

                // long url may already exist
                // return the final associated code generated or existing
                String associated = repo.insert(longUrl, generated);

                return new ShortenUrlResponseDto(associated);
            } catch (DataIntegrityViolationException e) {
                // small chance we get a collision
                if (++count == max) throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public LongUrlDto fetchLongUrl(String code) {
        String longUrl = repo.getLongUrl(code).orElseThrow(
                () -> new NoMatchingUrlException("No matching url for code " + code)
        );
        return new LongUrlDto(longUrl);
    }

    private String generateCode() {
        return NanoIdUtils.randomNanoId(7);
    }
}