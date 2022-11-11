package com.can.zupuserservice.core.security.jwt.auth0;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.can.zupuserservice.core.security.jwt.abstracts.IJWTUtils;
import com.can.zupuserservice.core.security.jwt.data.dto.JWTToken;
import com.can.zupuserservice.core.security.jwt.data.dto.JWTTokenPayloadCore;
import com.can.zupuserservice.core.security.jwt.exceptions.JWTException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Map;

/**
 * <h2>Implementation for jwt utils with <i>auth0.jwt</i>.</h2>
 * <hr/>
 *
 * @author Can Kurt
 * @version 1.0
 * @since 2022-11-11
 */
public class JWTUtils implements IJWTUtils {

    private final Algorithm algorithm;
    private final Long expiresAfter;
    private final JWTVerifier verifier;
    private final ObjectMapper jacksonObjectMapper;
    private String issuer = "SYSTEM";
    private String payloadDataFieldName = "data";

    public JWTUtils(Long expiresAfter, String secretKey) {
        this.expiresAfter = expiresAfter;

        // Init jwt lib
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.verifier = JWT.require(this.algorithm).build();

        // Init object mapper
        this.jacksonObjectMapper = new ObjectMapper();
        this.jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public void setPayloadDataFieldName(String payloadDataFieldName) {
        this.payloadDataFieldName = payloadDataFieldName;
    }

    private JWTToken generateTokenBase(Map<String, Object> claims) {
        long currentMillis = System.currentTimeMillis();
        Date issuedAt = new Date(currentMillis);
        Date expiresAt = new Date(currentMillis + this.expiresAfter);

        String token = JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withPayload(Map.of(payloadDataFieldName, claims))
                .sign(algorithm);
        return new JWTToken(token);
    }

    @Override
    public JWTToken generateToken(Map<String, Object> claims) {
        return generateTokenBase(claims);
    }

    @Override
    public <TokenPayloadType> JWTToken generateToken(TokenPayloadType tokenPayloadType) {
        Map<String, Object> claims = jacksonObjectMapper.convertValue(tokenPayloadType, new TypeReference<>() {
        });
        return generateTokenBase(claims);
    }

    @Override
    public Map<String, Object> getDataAsMap(JWTToken jwtToken) throws JWTException {
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken.getToken());
            return decodedJWT.getClaims().get(payloadDataFieldName).asMap();
        } catch (JWTVerificationException e) {
            throw new JWTException(e.getMessage());
        }
    }

    @Override
    public <TokenPayloadType> TokenPayloadType getDataAsObject(JWTToken jwtToken, Class<TokenPayloadType> tokenPayloadType) throws JWTException {
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken.getToken());
            return decodedJWT.getClaims().get(payloadDataFieldName).as(tokenPayloadType);
        } catch (JWTVerificationException e) {
            throw new JWTException(e.getMessage());
        }
    }

    @Override
    public JWTTokenPayloadCore<Map<String, Object>> getPayloadAsMap(JWTToken jwtToken) throws JWTException {
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken.getToken());
            return JWTTokenPayloadCore.<Map<String, Object>>builder()
                    .issuer(decodedJWT.getIssuer())
                    .expiresAt(decodedJWT.getExpiresAt())
                    .issuedAt(decodedJWT.getIssuedAt())
                    .data(decodedJWT.getClaims().get(payloadDataFieldName).asMap())
                    .build();
        } catch (JWTVerificationException e) {
            throw new JWTException(e.getMessage());
        }
    }

    @Override
    public <TokenPayloadType> JWTTokenPayloadCore<TokenPayloadType> getPayloadAsObject(JWTToken jwtToken, Class<TokenPayloadType> tokenPayloadType) throws JWTException {
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken.getToken());
            return JWTTokenPayloadCore.<TokenPayloadType>builder()
                    .issuer(decodedJWT.getIssuer())
                    .expiresAt(decodedJWT.getExpiresAt())
                    .issuedAt(decodedJWT.getIssuedAt())
                    .data(decodedJWT.getClaims().get(payloadDataFieldName).as(tokenPayloadType))
                    .build();
        } catch (JWTVerificationException e) {
            throw new JWTException(e.getMessage());
        }
    }

    @Override
    public boolean isValid(JWTToken jwtToken) {
        try {
            verifier.verify(jwtToken.getToken());
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

}

