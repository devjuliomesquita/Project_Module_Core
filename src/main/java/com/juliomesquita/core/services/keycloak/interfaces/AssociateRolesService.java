package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.associateflow.AssociateRolesKeycloak;

import java.util.List;

public interface AssociateRolesService {
   void associateRoleUser(String userId, List<AssociateRolesKeycloak> listRoles);

   void disassociateRoleUser(String userId, List<AssociateRolesKeycloak> listRoles);

   void associateUserGroup(String userId, String groupId);

   void disassociateUserGroup(String userId, String groupId);

   void associateRoleGroup(String groupId, List<AssociateRolesKeycloak> listRoles);

   void disassociateRoleGroup(String groupId, List<AssociateRolesKeycloak> listRoles);

}
