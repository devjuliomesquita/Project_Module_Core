package com.juliomesquita.core.shared.utils;

import java.util.function.Function;

public interface UtilsMethods {

   Function<String, String> extractId = input ->
           input.substring(input.lastIndexOf("/") + 1);

   Function<String, String> createFilter = input ->
      input.replace(',','&');

}
