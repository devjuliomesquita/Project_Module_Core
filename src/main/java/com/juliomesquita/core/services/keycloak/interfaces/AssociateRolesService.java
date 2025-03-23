package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.associateflow.AssociateRolesKeycloak;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.control.Either;

import java.util.List;

public interface AssociateRolesService {
   Either<Notification, Void> associateRoleUser(String userId, List<AssociateRolesKeycloak> listRoles);

   Either<Notification, Void> disassociateRoleUser(String userId, List<AssociateRolesKeycloak> listRoles);

   Either<Notification, Void> associateUserGroup(String userId, String groupId);

   Either<Notification, Void> disassociateUserGroup(String userId, String groupId);

   Either<Notification, Void> associateRoleGroup(String groupId, List<AssociateRolesKeycloak> listRoles);

   Either<Notification, Void> disassociateRoleGroup(String groupId, List<AssociateRolesKeycloak> listRoles);
}
