package com.example.url_shortener.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.url_shortener.entities.ShortenedUrl;
import com.example.url_shortener.repositories.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    
    public String shortenUrl(String originalUrl) {
        String shortCode = generateShortCode();
        ShortenedUrl url = new ShortenedUrl(null, originalUrl, shortCode, LocalDateTime.now());
        urlRepository.save(url);
        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(ShortenedUrl::getOriginalUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"));
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}

