package com.example.jwtgenerate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    //private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;


    private final static String PUBLIC_KEY_PEM =  "-----BEGIN PUBLIC KEY-----\n"
            + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3qwdL3v0NGlOvrtHO9qb\n"
            + "koKJa34Nz9u2AUrq8ymzkeTCqm5GDQZhZLJOSomb5sZDfe8V9wfCiMgAQzwGZ2M9\n"
            + "6+DvdFvI4PfSPkMplWF831TQzfUosaNkcdpSBt/eqKrqGNnNMT12QSsgEx2xqNmz\n"
            + "Wksps9Xe36X2/+Z1xi+XFFedOa8c/hq0loJhu2oDaR07F5uA07zCDY9xkOeM+wVa\n"
            + "09STy6XVJk7JOpZMzcAHu87s85NQ4y/bzL2hKUeQGURemBP3pjxryAxowdsAFhj/\n"
            + "6pbbpiQeGMaZOkk8FPWEmH/zLb+ohWxu17HBWGL4Tl/cfDZSc78SWiHkE8dx2qFx\n"
            +"bQIDAQAB\n"
            + "-----END PUBLIC KEY-----";


    //retrieve username from jwt token
    public String getUsernameFromToken(String token)  {
        final Claims claims = getAllClaimsFromToken(token);
        if (claims != null) {
            String username = (String) claims.get("username");
            String email = (String) claims.get("email");
            String fullname = (String) claims.get("fullname");
            String sub = (String) claims.get("sub");
            return username;
        } else {
            return null;
        }
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    /**
     * Decode a PEM string to DER format
     *
     * @param pem
     * @return
     * @throws IOException
     */
    public static byte[] pemToDer(String pem)  {
        return Base64.getDecoder().decode(stripBeginEnd(pem));
    }

    public static String stripBeginEnd(String pem) {
        String stripped = pem.replaceAll("-----BEGIN (.*)-----", "");
        stripped = stripped.replaceAll("-----END (.*)----", "");
        stripped = stripped.replaceAll("\r\n", "");
        stripped = stripped.replaceAll("\n", "");
        return stripped.trim();
    }


    public static PublicKey decodePublicKey(byte[] der) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(der);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        //KeyFactory kf = KeyFactory.getInstance("RSA" , "BC");        //use provider BouncyCastle if available.
        return kf.generatePublic(spec);
    }

    private Claims getAllClaimsFromToken(String token)  {
        Claims claims;
        try {
            //This line will throw an exception if it is not a signed JWS (as expected)
            PublicKey publicKey = decodePublicKey(pemToDer(PUBLIC_KEY_PEM));
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
            System.out.println("ID: " + claims.getId());
            System.out.println("SUCCESS: Jwts.parser().setSigningKey(publicKey) JWT Token validated with PUBLIC_KEY");
        } catch (Exception e) {
            claims = null;
            System.out.println("ERROR: Jwts.parser().setSigningKey(publicKey)" + e);
        }
        return claims;
    }

/*
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userName);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, "secret").compact();
    }
*/

}