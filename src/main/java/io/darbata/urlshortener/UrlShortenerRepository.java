package io.darbata.urlshortener;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*

CREATE TABLE urls (
    id         BIGSERIAL PRIMARY KEY, // auto increments
    long_url   TEXT         NOT NULL,
    code       VARCHAR(7)  NOT NULL UNIQUE, // represents short url
    created_at TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    hits       BIGINT       NOT NULL DEFAULT 0
);

*/

@Repository
class UrlShortenerRepository {

    private final JdbcClient client;

    UrlShortenerRepository(JdbcClient client) {
        this.client = client;
    }

    void insert(String longUrl, String code) {
        String sql = """
        INSERT INTO urls (long_url, code)
        VALUES (:longUrl, :code);
        """;

        client.sql(sql)
                .param("longUrl", longUrl)
                .param("code", code)
                .update();
    }

    Optional<String> getLongUrl(String code) {
        String sql = """
        UPDATE urls SET hits = hits + 1
        WHERE code = :code
        RETURNING long_url;
        """;

        return client.sql(sql)
                .param("code", code)
                .query(String.class)
                .optional();
    }

}