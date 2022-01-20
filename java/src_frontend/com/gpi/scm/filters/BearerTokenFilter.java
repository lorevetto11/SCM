package com.gpi.scm.filters;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;

import com.gpi.scm.authentication.TokenManager;
import com.gpi.scm.delegates.UserDelegate;
import com.gpi.scm.generic.constants.ScmConstants;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.utils.AllowAnonymous;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class BearerTokenFilter implements ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

	private TokenManager validator = new TokenManager();

	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {

		// exclude preflight HTTP OPTION checks
		// Preflight CORS does OPTIONS request without headers.
		// Let's not require Authorization then
		if (ctx.getMethod().equals(HttpMethod.OPTIONS)) {
			return;
		}

		final Method method = resourceInfo.getResourceMethod();
		// If method allows anonymous access, short go on
		if (method.isAnnotationPresent(AllowAnonymous.class)) {
			ctx.setProperty(ScmConstants.ALLOW_ANONYMOUS, true);
			return;
		}

		// Grab the token from the Request
		final String authHeader = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || authHeader.isEmpty()) {
			ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			return;
		}

		try {
			JwtClaims jwtClaims = validator.validateToken(authHeader);
			ctx.setProperty(ScmConstants.JWT_CLAIM, jwtClaims);
			final long userId = validator.getUserId(jwtClaims);
			ctx.setProperty(ScmConstants.USER_ID, userId);
			UserContextHolder.setUser(UserDelegate.findUserById(userId));
		} catch (InvalidJwtException e) {
			ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			return;
		}
	}
}