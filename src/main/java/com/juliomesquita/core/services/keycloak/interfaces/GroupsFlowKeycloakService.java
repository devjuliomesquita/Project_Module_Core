package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.groupflow.GroupDataKeycloak;

import java.util.List;

public interface GroupsFlowKeycloakService {
   void createGroup(String groupName);

   void updateGroup(String id, String groupName);

   void deleteGroup(String id);

   List<GroupDataKeycloak> findGroups();
}
