package com.gpi.scm.authentication;

import java.security.Key;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;

import com.gpi.scm.generic.constants.ScmConstants;
import com.gpi.scm.generic.exceptions.SystemException;
import com.gpi.scm.generic.utils.ScmUtil;

public class TokenManager {

	public String generateToken(String userId) {
		final String system = ScmUtil.resolveParamName(ScmConstants.JWT_SYSTEM);
		JwtClaims claims = new JwtClaims();
		claims.setGeneratedJwtId();
		claims.setIssuer(system);
		claims.setAudience(ScmUtil.resolveParamName(ScmConstants.JWT_AUDIENCE));
		claims.setIssuedAtToNow();
		claims.setExpirationTimeMinutesInTheFuture(ScmUtil.resolveFloatParam(ScmConstants.JWT_EXPIRE));
		claims.setSubject(userId);
		claims.setClaim(ScmConstants.CLAIM_SYSTEM, system);
		return serializeToken(claims);
	}

	public JwtClaims validateToken(String token) throws InvalidJwtException {
		Key key = new AesKey(ScmUtil.resolveParamName(ScmConstants.JWT_PASSWORD_TOKEN).getBytes());
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
				.setRequireSubject()
				.setDisableRequireSignature()
				.setExpectedAudience(ScmUtil.resolveParamName(ScmConstants.JWT_AUDIENCE))
				.setDecryptionKey(key).build();
		return jwtConsumer.processToClaims(bonificaToken(token));
	}

	public String refreshToken(JwtClaims jwt) {
		jwt.setExpirationTimeMinutesInTheFuture(ScmUtil.resolveFloatParam(ScmConstants.JWT_EXPIRE));
		return serializeToken(jwt);
	}

	private String serializeToken(JwtClaims jwt) {
		Key key = new AesKey(ScmUtil.resolveParamName(ScmConstants.JWT_PASSWORD_TOKEN).getBytes());
		JsonWebEncryption jwe = new JsonWebEncryption();
		jwe.setPayload(jwt.toJson());
		jwe.setKey(key);
		jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
		jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
		try {
			return jwe.getCompactSerialization();
		} catch (JoseException e) {
			throw new SystemException(e);
		}
	}

	public Long getUserId(JwtClaims claims) {
		try {
			return Long.valueOf(claims.getSubject());
		} catch (Exception e) {
			throw new SecurityException("Invalid JWT Token");
		}
	}

	public String bonificaToken(String token) {
		return token.substring(ScmConstants.TOKEN_BEARER.length());
	}
}
