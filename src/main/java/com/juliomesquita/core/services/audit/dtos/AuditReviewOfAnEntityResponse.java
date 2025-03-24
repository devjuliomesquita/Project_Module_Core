package com.juliomesquita.core.services.audit.dtos;

import com.juliomesquita.core.services.audit.entity.RevisionAuditedEntity;
import org.hibernate.envers.RevisionType;

public record AuditReviewOfAnEntityResponse<T>(
        RevisionAuditedEntity revisionAuditedEntity,
        T entity,
        RevisionType revisionType
) {
}
