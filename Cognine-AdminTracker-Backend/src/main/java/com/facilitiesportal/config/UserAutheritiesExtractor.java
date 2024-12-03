package com.facilitiesportal.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import com.facilitiesportal.dao.Emp_FacilitiesPortalRolesRepo;
import com.facilitiesportal.model.Emp_FacilitiesPortalRoles;

/**
 * Converter class to extract user authorities from JWT.
 */
public class UserAutheritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {

	private final Emp_FacilitiesPortalRolesRepo emp_FacilitiesPortalRolesRepo;

	/**
	 * Constructor for UserAuthoritiesExtractor.
	 * 
	 * @param empFPRolesRepo Repository for employee roles
	 */
	public UserAutheritiesExtractor(Emp_FacilitiesPortalRolesRepo emp_FacilitiesPortalRolesRepo) {

		this.emp_FacilitiesPortalRolesRepo = emp_FacilitiesPortalRolesRepo;

	}

	/**
	 * Convert JWT granted authorities to a collection of custom granted
	 * authorities.
	 * 
	 * @param jwt JWT token
	 * @return Collection of custom GrantedAuthority
	 */
	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {

		List<GrantedAuthority> authorities = new ArrayList<>();

		try {

			List<Emp_FacilitiesPortalRoles> emp_FacilitiesPortalRoles = emp_FacilitiesPortalRolesRepo
					.findAllByEmployee_Email(jwt.getClaims().get("unique_name").toString());
			for (Emp_FacilitiesPortalRoles emp_FacilitiesPortalRole : emp_FacilitiesPortalRoles) {
				authorities
						.add(new SimpleGrantedAuthority(emp_FacilitiesPortalRole.getFacilitiesPortalRole().getRole()));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return authorities;
	}
}