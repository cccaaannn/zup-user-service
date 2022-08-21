package com.can.zupuserservice.core.security.jwt.auth0;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.utilities.result.concretes.ErrorDataResult;
import com.can.zupuserservice.core.utilities.result.concretes.ErrorResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.can.zupuserservice.core.data.dto.AccessToken;
import com.can.zupuserservice.core.security.jwt.abstracts.IJWTUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Auth0TokenUtils implements IJWTUtils {

    private final Algorithm algorithm;
    private final Long expiresAfter;
    private final JWTVerifier verifier;
    private String issuer = "SYSTEM";
    private final ObjectMapper jacksonObjectMapper;

    public Auth0TokenUtils(Long expiresAfter, String secretKey) {
        this.expiresAfter = expiresAfter;
        this.algorithm = Algorithm.HMAC256(secretKey);
        verifier = JWT.require(algorithm).build();
        jacksonObjectMapper = new ObjectMapper();
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Auth0TokenUtils(Long expiresAfter, String secretKey, String issuer) {
        this(expiresAfter, secretKey);
        this.issuer = issuer;
    }

    @Override
    public DataResult<AccessToken> generateTokenRaw(Map<String, Object> claims) {
        try {
            String token = JWT.create()
                    .withIssuer(issuer)
                    .withPayload(claims)
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            AccessToken accessToken = new AccessToken(token);
            return new SuccessDataResult<>(accessToken);
        }
        catch (Exception e) {
            return new ErrorDataResult<>(e.getMessage());
        }
    }

    @Override
    public <TokenPayloadType> DataResult<AccessToken> generateToken(TokenPayloadType tokenPayloadType) {
        Map<String, Object> claims = jacksonObjectMapper.convertValue(tokenPayloadType, new TypeReference<Map<String, Object>>() {});
        try {
            String token = JWT.create()
                    .withIssuer(issuer)
                    .withPayload(claims)
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            AccessToken accessToken = new AccessToken(token);
            return new SuccessDataResult<>(accessToken);
        }
        catch (Exception e) {
            return new ErrorDataResult<>(e.getMessage());
        }
    }

    @Override
    public Result verifyToken(AccessToken accessToken) {
        try {
            verifier.verify(accessToken.getToken());
            return new SuccessResult();
        }
        catch (JWTDecodeException e) {
            return new ErrorResult();
        }
    }

    @Override
    public DataResult<Map<String, ?>> verifyAndGetTokenRaw(AccessToken accessToken) {
        try {
            DecodedJWT jwt = verifier.verify(accessToken.getToken());
            return new SuccessDataResult<>(jwt.getClaims());
        }
        catch (JWTDecodeException e) {
            return new ErrorDataResult<>();
        }
    }

    @Override
    public <TokenPayloadType> DataResult<TokenPayloadType> verifyAndGetToken(AccessToken accessToken, Class<TokenPayloadType> tokenPayloadType) {
        try {
            DecodedJWT jwt = verifier.verify(accessToken.getToken());

            Map<String, String> claims = new HashMap<>();
            for(Map.Entry<String, Claim> claim: jwt.getClaims().entrySet()) {
                claims.put(claim.getKey(), claim.getValue() != null ? claim.getValue().toString(): null);
            }
            TokenPayloadType tokenPayload = jacksonObjectMapper.convertValue(claims, tokenPayloadType);

            return new SuccessDataResult<>(tokenPayload);
        }
        catch (JWTDecodeException e) {
            return new ErrorDataResult<>();
        }
    }

    private Date getExpirationDate(){
        return new Date(System.currentTimeMillis() + this.expiresAfter);
    }

}
