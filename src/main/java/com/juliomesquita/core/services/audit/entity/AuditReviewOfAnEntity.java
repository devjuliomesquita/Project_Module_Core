package com.juliomesquita.core.services.audit.entity;

public class AuditReviewOfAnEntity<T> {
   private RevisionAuditedEntity revisionAuditedEntity;
   private T entity;

   public AuditReviewOfAnEntity(final RevisionAuditedEntity revisionAuditedEntity, final T entity) {
      this.revisionAuditedEntity = revisionAuditedEntity;
      this.entity = entity;
   }

   public AuditReviewOfAnEntity() {
   }

   public RevisionAuditedEntity getRevisionAuditedEntity() {
      return revisionAuditedEntity;
   }

   public void setRevisionAuditedEntity(RevisionAuditedEntity revisionAuditedEntity) {
      this.revisionAuditedEntity = revisionAuditedEntity;
   }

   public T getEntity() {
      return entity;
   }

   public void setEntity(T entity) {
      this.entity = entity;
   }
}
