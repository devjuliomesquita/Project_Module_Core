package com.juliomesquita.core.services.audit.entity;

import com.juliomesquita.core.services.audit.listener.RevisionAuditedListener;
import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "revision_audited")
@RevisionEntity(value = RevisionAuditedListener.class)
public class RevisionAuditedEntity {
   @Id
   @RevisionNumber
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
   @SequenceGenerator(name = "id", sequenceName = "id")
   @Column(name = "id", nullable = false)
   private Long id;

   @RevisionTimestamp
   @Column(name = "revision_data", nullable = false)
   private Date revisionData;

   @JsonIgnore
   @Column(name = "action_done_by", nullable = false)
   private UUID actionDoneBy;

   @JsonIgnore
   @Column(name = "action_done_by_ip", nullable = false)
   private String actionDoneByIp;

   public RevisionAuditedEntity() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Date getRevisionData() {
      return revisionData;
   }

   public void setRevisionData(Date revisionData) {
      this.revisionData = revisionData;
   }

   public UUID getActionDoneBy() {
      return actionDoneBy;
   }

   public void setActionDoneBy(UUID actionDoneBy) {
      this.actionDoneBy = actionDoneBy;
   }

   public String getActionDoneByIp() {
      return actionDoneByIp;
   }

   public void setActionDoneByIp(String actionDoneByIp) {
      this.actionDoneByIp = actionDoneByIp;
   }

   @Override
   public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      RevisionAuditedEntity that = (RevisionAuditedEntity) o;
      return Objects.equals(id, that.id) && Objects.equals(
              revisionData, that.revisionData) && Objects.equals(
              actionDoneBy, that.actionDoneBy) && Objects.equals(
              actionDoneByIp, that.actionDoneByIp);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, revisionData, actionDoneBy, actionDoneByIp);
   }
}
