package com.juliomesquita.core.shared.pagination;

import java.util.List;
import java.util.function.Function;

public record Pagination<T>(
        List<T> items,
        int currentPage,
        int itemsPerPage,
        long totalItems,
        int totalPages
) {
   public <R> Pagination<R> map(final Function<T, R> mapper) {
      final List<R> aNewList = this.items.stream()
              .map(mapper)
              .toList();
      return new Pagination<>(aNewList, currentPage(), itemsPerPage(), totalItems(), totalPages());
   }

   public static <Y> Pagination<Y> create(
           final List<Y> items,
           final int currentPage,
           final int itemsPerPage,
           final long totalItems
   ) {
      return new Pagination<>(
              items,
              currentPage,
              itemsPerPage,
              totalItems,
              (int) totalItems / itemsPerPage
      );
   }

   public <Z> Pagination<Z> convert(final List<Z> items) {
      return new Pagination<>(
              items,
              this.currentPage,
              this.itemsPerPage,
              this.totalItems,
              (int) this.totalItems / this.itemsPerPage
      );
   }
}
