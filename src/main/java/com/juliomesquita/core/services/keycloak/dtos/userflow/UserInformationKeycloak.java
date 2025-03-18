package com.juliomesquita.core.services.keycloak.dtos.userflow;

public record UserInformationKeycloak(
		String id,
		String username,
		String firstName,
		String lastName,
		String email,
		Boolean emailVerified,
		Long createdTimestamp,
		Boolean enabled
) {
}
