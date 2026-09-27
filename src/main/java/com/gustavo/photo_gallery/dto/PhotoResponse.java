package com.gustavo.photo_gallery.dto;

public record PhotoResponse(
        String key,
        String contentType,
        Long size,
        String url
        ) {
}
