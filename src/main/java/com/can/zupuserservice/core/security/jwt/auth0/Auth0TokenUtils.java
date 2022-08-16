package com.can.zupuserservice.core.security.jwt.auth0;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.can.zupuserservice.core.result.abstracts.DataResult;
import com.can.zupuserservice.core.result.abstracts.Result;
import com.can.zupuserservice.core.result.concretes.ErrorDataResult;
import com.can.zupuserservice.core.result.concretes.ErrorResult;
import com.can.zupuserservice.core.result.concretes.SuccessDataResult;
import com.can.zupuserservice.core.result.concretes.SuccessResult;
import com.can.zupuserservice.core.data.dto.AccessToken;
import com.can.zupuserservice.core.security.jwt.abstracts.IJWTUtils;
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

    public Auth0TokenUtils(Long expiresAfter, String secretKey) {
        this.expiresAfter = expiresAfter;
        this.algorithm = Algorithm.HMAC256(secretKey);
        verifier = JWT.require(algorithm).build();
    }

    public Auth0TokenUtils(Long expiresAfter, String secretKey, String issuer) {
        this.issuer = issuer;
        this.expiresAfter = expiresAfter;
        this.algorithm = Algorithm.HMAC256(secretKey);
        verifier = JWT.require(algorithm).build();
    }

    @Override
    public DataResult<AccessToken> generateToken(Map<String, Object> claims) {
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
                claims.put(claim.getKey(), claim.getValue().asString());
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TokenPayloadType tokenPayload = mapper.convertValue(claims, tokenPayloadType);

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
