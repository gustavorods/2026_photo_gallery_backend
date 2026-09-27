package com.gustavo.photo_gallery.dto;

public record PhotoFile(
        byte[] data,
        String contentType
) {
}
