package com.juliomesquita.core.services.audit.listener;

import com.juliomesquita.core.services.audit.entity.RevisionAuditedEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Component
public class RevisionAuditedListener implements RevisionListener {
   @Override
   public void newRevision(Object o) {
      RevisionAuditedEntity revisionAuditedEntity = (RevisionAuditedEntity) o;

      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
         revisionAuditedEntity.setActionDoneBy(null);
      }

      final Object principal = authentication.getPrincipal();

      if (principal instanceof Jwt jwt) {
         final String actionDoneBy = jwt.getClaim("sub") != null ? jwt.getClaim("sub") : jwt.getSubject();
         revisionAuditedEntity.setActionDoneBy(UUID.fromString(actionDoneBy));
      }

      revisionAuditedEntity.setActionDoneByIp(this.getClientIp());
   }

   private String getClientIp() {
      HttpServletRequest request =
              ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

      String ip = request.getHeader("X-Forwarded-For");
      if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getRemoteAddr();
      }
      return ip;
   }
}
