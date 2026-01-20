package com.example.skinsaathi.service.impl;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;


@Service
public class AppleTokenVerifier {

    private static final String APPLE_ISSUER = "https://appleid.apple.com";
    private static final String JWKS_URL = "https://appleid.apple.com/auth/keys";

    // Allowed audiences
    private static final String EXPO_AUDIENCE = "host.exp.Exponent";
    private static final String IOS_AUDIENCE = "com.mehulsri.yourstrulycare";

    public JWTClaimsSet verify(String identityToken) {

        try {
            SignedJWT signedJWT = SignedJWT.parse(identityToken);

            JWKSet jwkSet = JWKSet.load(new URL(JWKS_URL));
            JWK jwk = jwkSet.getKeyByKeyId(signedJWT.getHeader().getKeyID());

            RSAKey rsaKey = (RSAKey) jwk;
            RSAPublicKey publicKey = rsaKey.toRSAPublicKey();

            JWSVerifier verifier = new RSASSAVerifier(publicKey);

            // ✅ Signature check
            if (!signedJWT.verify(verifier)) {
                throw new RuntimeException("Invalid Apple signature");
            }

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            System.out.println("claims: "+claims);

            // ✅ Issuer check
            if (!APPLE_ISSUER.equals(claims.getIssuer())) {
                throw new RuntimeException("Invalid issuer");
            }

            // ✅ Audience check (Expo + Prod)
            if (
                !claims.getAudience().contains(EXPO_AUDIENCE) &&
                !claims.getAudience().contains(IOS_AUDIENCE)
            ) {
                throw new RuntimeException("Invalid audience");
            }

            // ✅ Expiry check
            if (claims.getExpirationTime().before(new Date())) {
                throw new RuntimeException("Token expired");
            }

            return claims;

        } catch (JOSEException | IOException | RuntimeException | ParseException e) {
            throw new RuntimeException("Apple token verification failed", e);
        }
    }
}
