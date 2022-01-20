package com.gpi.scm.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.jose4j.jwt.JwtClaims;

import com.gpi.scm.authentication.TokenManager;
import com.gpi.scm.generic.constants.ScmConstants;

@Provider
public class RefreshTokenFilter implements ContainerResponseFilter {

	private TokenManager validator = new TokenManager();

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		JwtClaims jtwClaims = (JwtClaims) requestContext.getProperty(ScmConstants.JWT_CLAIM);
		if (jtwClaims != null) {
			responseContext.getHeaders().add(ScmConstants.TOKEN, validator.refreshToken(jtwClaims));
		}

	}
}