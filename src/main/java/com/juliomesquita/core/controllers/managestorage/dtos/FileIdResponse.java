package com.juliomesquita.core.controllers.managestorage.dtos;

public record FileIdResponse(String fileId) {
   public static FileIdResponse with(final String id) {
      return new FileIdResponse(id);
   }
}
