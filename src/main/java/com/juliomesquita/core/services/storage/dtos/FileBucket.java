package com.juliomesquita.core.services.storage.dtos;

import java.nio.ByteBuffer;

public record FileBucket(
        String fieldName,
        String originalName,
        String mimeType,
        Long size,
        ByteBuffer buffer
) {
}
