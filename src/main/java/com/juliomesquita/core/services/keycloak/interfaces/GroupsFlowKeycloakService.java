package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.groupflow.GroupDataKeycloak;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.control.Either;

import java.util.List;

public interface GroupsFlowKeycloakService {
   Either<Notification, String> createGroup(String groupName);

   Either<Notification, Void> updateGroup(String id, String groupName);

   Either<Notification, Void> deleteGroup(String id);

   Either<Notification, List<GroupDataKeycloak>> findGroups();
}
