package com.juliomesquita.core.shared.pagination;

public record SearchQuery(
        int currentPage,
        int itemsPerPage,
        String terms,
        String sort,
        String direction
) {
}
