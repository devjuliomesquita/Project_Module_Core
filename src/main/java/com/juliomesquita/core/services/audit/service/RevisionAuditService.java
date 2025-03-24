package com.juliomesquita.core.services.audit.service;

import com.juliomesquita.core.services.audit.dtos.AuditReviewOfAnEntityResponse;
import com.juliomesquita.core.shared.pagination.Pagination;
import com.juliomesquita.core.shared.pagination.SearchQuery;

import java.util.UUID;

public interface RevisionAuditService<T> {
   Pagination<AuditReviewOfAnEntityResponse<T>> revision(Class<T> tipo, UUID id, SearchQuery searchQuery);
}
