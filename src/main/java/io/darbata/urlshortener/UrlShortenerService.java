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

    public String shortenUrl(String longUrl) {
        int count = 0;
        int max = 3;

        while (true) {

            try {

                String code = generateCode();
                repo.insert(longUrl, code);
                return code;

            } catch (DataIntegrityViolationException e) {

                // small chance we get a collision
                if (++count == max) throw e;

            } catch (Exception e) {

                throw new RuntimeException(e);

            }

        }

    }

    public String fetchLongUrl(String code) {
        return repo.getLongUrl(code).orElseThrow(
                () -> new NoMatchingUrlException("No matching url for code " + code)
        );
    }

    private String generateCode() {
        return NanoIdUtils.randomNanoId(7);
    }
}