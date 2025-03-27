package com.juliomesquita.core.controllers.managestorage.dtos;

public record UrlSignedResponse(String url) {
   public static UrlSignedResponse with(final String url){
      return new UrlSignedResponse(url);
   }
}
