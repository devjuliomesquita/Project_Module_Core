package com.juliomesquita.core.services.audit.service;

import com.juliomesquita.core.services.audit.dtos.AuditReviewOfAnEntityResponse;
import com.juliomesquita.core.services.audit.entity.RevisionAuditedEntity;
import com.juliomesquita.core.shared.pagination.Pagination;
import com.juliomesquita.core.shared.pagination.SearchQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RevisionAuditServiceImpl<T> implements RevisionAuditService<T> {
   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public Pagination<AuditReviewOfAnEntityResponse<T>> revision(final Class<T> entityClass, final UUID entityId, final SearchQuery searchQuery) {
      final AuditReader auditReader = AuditReaderFactory.get(entityManager);
      final List<Number> revisions = auditReader.getRevisions(entityClass, entityId);

      final int start = searchQuery.currentPage() * searchQuery.itemsPerPage();
      final int end = Math.min(start + searchQuery.itemsPerPage(), revisions.size());
      if (start >= revisions.size()) {
         return null;
      }

      final List<Number> paginatedRevisions = revisions.subList(start, end);

      final List<AuditReviewOfAnEntityResponse<T>> auditList = paginatedRevisions.stream()
              .map(rev -> {
                 final Object[] entityRevision = (Object[]) auditReader.find(entityClass, entityId, rev);
                 final T entity = (T) entityRevision[0];
                 final RevisionType revisionType = (RevisionType) entityRevision[2];
                 final RevisionAuditedEntity revision = auditReader.findRevision(RevisionAuditedEntity.class, rev);
                 return new AuditReviewOfAnEntityResponse<T>(revision, entity, revisionType);
              })
              .toList();

      return Pagination.create(auditList, searchQuery.currentPage(), searchQuery.itemsPerPage(), revisions.size());
   }
}
