package com.facilitiesportal.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Utility class for security-related operations.
 */
@Component
public class SecurityUtil {

	/**
	 * Retrieves the username of the currently logged-in user.
	 *
	 * @return The username of the logged-in user.
	 */
	public String getLoggedInUsername() {
		Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> attributes = null;

		if (authToken instanceof JwtAuthenticationToken) {
			attributes = ((JwtAuthenticationToken) authToken).getTokenAttributes();
		}
		return attributes.get("name").toString();
	}
}