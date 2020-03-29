package com.koby5i.jwtparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.StringReader;
import java.security.*;
//import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;


@SpringBootApplication
public class JwtparserApplication {

	public static void main(String[] args) throws Exception {
		//SpringApplication.run(JwtparserApplication.class, args);

		String PUBLIC_KEY_PEM = "-----BEGIN PUBLIC KEY-----\n"
				+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQ\n"
				+ "EAk8/XJRIbEW/DW30YxcnjNPFgZjhtsjcatetMP9xxyXDBPQA+jh9xAM4bf1v3QJOJalNuNpHat9k3e9Aa1M+qB1zq8blAZtsSF8jM7okuMHyeU6YXw7rY+d4t1Xigbt9LTaPfb5qCZvMShfjgeSb2DgZRbRlKfh9cVUZ8e7XUTm6UDlEvBI9mQiiwolPVyPCZDFNI8pb35NcXI4Kzh4S15BnAGpyKpiQJEmXX4wifYak8weJVoFuAu9fjjtdeacEqHFafyVZvEdKHTD+ofY9z6/JELdFRcI2N3a8rRa+JM2+CvrAIuOzLHGBZ1WwPKsHe6zhsxC1oODRvzVYOzOtvbQIDAQAB\n"
				+ "-----END PUBLIC KEY-----";

		String PRIVATE_KEY = "-----BEGIN PUBLIC KEY-----\n";

		String realmPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk8/XJRIbEW/DW30YxcnjNPFgZjhtsjcatetMP9xxyXDBPQA+jh9xAM4bf1v3QJOJalNuNpHat9k3e9Aa1M+qB1zq8blAZtsSF8jM7okuMHyeU6YXw7rY+d4t1Xigbt9LTaPfb5qCZvMShfjgeSb2DgZRbRlKfh9cVUZ8e7XUTm6UDlEvBI9mQiiwolPVyPCZDFNI8pb35NcXI4Kzh4S15BnAGpyKpiQJEmXX4wifYak8weJVoFuAu9fjjtdeacEqHFafyVZvEdKHTD+ofY9z6/JELdFRcI2N3a8rRa+JM2+CvrAIuOzLHGBZ1WwPKsHe6zhsxC1oODRvzVYOzOtvbQIDAQAB";
		String accessTokenString = "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJmNDczMzVkNS05ZGEwLTRhZGEtYTk5Zi00ODA1YTU5ZGRiYTQiLCJleHAiOjE0NTczNjY5MDgsIm5iZiI6MCwiaWF0IjoxNDU3MzY2NjA4LCJpc3MiOiJodHRwOi8vbG9naW4uYWNtZS5sb2NhbDo4MDgxL2F1dGgvcmVhbG1zL2FjbWUiLCJhdWQiOiJ2YWFkaW4tYXBwIiwic3ViIjoiNjU5OTJmNzktNjM4Mi00ZDk4LTlhZjItOTBjZjVmNzZmZTkxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoidmFhZGluLWFwcCIsInNlc3Npb25fc3RhdGUiOiIxMmVlOGUwZi1kMzgwLTQxZmEtODMxMS1jMmI0MjM2OGJmZjkiLCJjbGllbnRfc2Vzc2lvbiI6ImY3NTAzYmJkLWFkZTYtNDQ1Ny1iZTIxLTQ3NDRkMWJmMTc4NCIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0Ojc3NzciXSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjbWUtcGV0Y2xpbmljIjp7InJvbGVzIjpbImFkbWluIiwidXNlciJdfSwidmFhZGluLWFwcCI6eyJyb2xlcyI6WyJ1c2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1wcm9maWxlIl19fSwibmFtZSI6IlRoZW8gVGVzdGVyIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidGhlbyIsImdpdmVuX25hbWUiOiJUaGVvIiwiZmFtaWx5X25hbWUiOiJUZXN0ZXIiLCJlbWFpbCI6InRvbSt0aGVvQGxvY2FsaG9zdCJ9.frMCkpDKG4VixXRZhh7KZqjDCxPbZq_6Wrl5X6RhjlGs9hL22Z6pcsVSlIzpincdwbLCpLYpLs3T2LRrlZ-YNUGOnKObnrmlVbMNi8UmGJiAj0bAsIPYWEfA-Ww3wuTitfjo0fgbAb8F_sLsPR9qjE6BcDPVXR2S_SJVWJ1CKb5kwiwKTTzAMUo1H22Ce64hoeSuEdQFM1x1n-M8kTkLPUPnL_lj-mOIpqbLZyrls3_TEL3up0-XYyF2Gt9fDQKXTp_XPLizGUiiY90TQC4rhNye3JPLMB6RZnQFmyJq5I5Cq0ybdMarloeLjvYjc3RyIgZgtFWjk5aNYDaietBJSA";

//		private final static String PRIVATE_KEY =
//				"-----BEGIN PRIVATE KEY-----\n"
//						+ "MIIEpAIBAAKCAQEA3qwdL3v0NGlOvrtHO9qbkoKJa34Nz9u2AUrq8ymzkeTCqm5GDQZhZLJOSomb5sZDfe8V9wfCiMgAQzwGZ2M96+DvdFvI4PfSPkMplWF831TQzfUosaNkcdpSBt/eqKrqGNnNMT12QSsgEx2xqNmzWksps9Xe36X2/+Z1xi+XFFedOa8c/hq0loJhu2oDaR07F5uA07zCDY9xkOeM+wVa09STy6XVJk7JOpZMzcAHu87s85NQ4y/bzL2hKUeQGURemBP3pjxryAxowdsAFhj/6pbbpiQeGMaZOkk8FPWEmH/zLb+ohWxu17HBWGL4Tl/cfDZSc78SWiHkE8dx2qFxbQIDAQABAoIBAQDDKVbrJ1kCFspNoRbt/swbvQ10iSbR51++AkuqlAIu/LEhb0U2ZUE3YI/+BF4ABgVhewtlWAfImoKgaxuxwemjs6Ir+cY2Zv5+U58+rhMkKtJXsWfZSbvfjdoW7F/atPI4layiW5wpwgJ6vqvXsfbgTMBgPjL5Eh1KbFMNQOjJid4BcicXxR+XD2lQtLv0BKmaY6DA4uvsWWNpaPeJVwtYodpTmZHuinU5gkXb4yGJyZy8ekmX8PbVIZHIMd5DvaY4SwaHE3LFkiD3DzapLpCyhatTxyulpmmUpsub8CumhWkuSf7FAPMF4IZLetGv7TDwEeZfZP5+5ksNG05tmKqBAoGBAPjkxUDqqrTD4JyxN6sysbH3JnX0CASq8UmMH5t7y34x8cFx52nvCjO+Dcpp9TVHTcxJoruvbXDOngc0KACfkwet++g1xsmOpd4pBbLggSD6PWq6HSC3Fi2LH3ofB5y/jdMyGB7YxJdEAmTdJq2+Ju5ipBW917SZ/w5GANzJBx7jAoGBAOUHr1kbp6izMGLq+NAdmgozXdE8ZD71EC5ICq89jnwnCGNwwmMxb8fQJT/wFrTgIa/TVtAqOm/QhPjl2Mr+WJYjooxK1ckwrdJLCLnGnOPIdYvn85iiyQaW/NTqrmgaVOO11B6ATXUjyPpy9WwsZP7MZux7YSv1mLx5UTouYU9vAoGBAM+9uvqVJAifGaIPtQRa2sBNJrf7CJpEyAJ4R4VyjYX2T/ADj4D2+Q/5CO+zAu0GZ+QkbgSzxugKPRKMMPtcNc+KI6FNCXAAdWd52/zSsFUDKTIkx90flA5NeedncOYfaFQoEsPkcCpFpK3lfLw1467DfGt9OkbO5nlWaq/dxui7AoGAKMTPb9s+YwdSq6kMFW/PhdVS8/X+Gj7hXHuyezNH88227oyZ7bJVJYpltxXz1Mq49GV4ZKG/uTuzD+NKTggsmL1LKBICMEgcCP0WVouGTf3W/sqRUDBZ/HRQGU6VMS7OwaISWkOYhiuSkRJ5oYHdkIWuJJZp27tiLtqhnOu4or0CgYAhrMggMRbKKiZMkt4JgYQ3ZtRgkHY56V2KVKtYg44+GxwOFcsL1rghTCYG0dOc87vEsa0Zj9b0FRWkr3P+to3h0i4hHKWKhAxGqeJaRHbkbWjKg+ZpYGm9psNOUm74LvJUZMsGG9fcPJI85MrIwWXzBFJRrLDIN+VlB9YmtYjJKw==\n"
//						+ "-----END PRIVATE KEY-----";
//
//		private final static String PUBLIC_KEY_PEM =  "-----BEGIN PUBLIC KEY-----\n"
//				+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3qwdL3v0NGlOvrtHO9qb\n"
//				+ "koKJa34Nz9u2AUrq8ymzkeTCqm5GDQZhZLJOSomb5sZDfe8V9wfCiMgAQzwGZ2M9\n"
//				+ "6+DvdFvI4PfSPkMplWF831TQzfUosaNkcdpSBt/eqKrqGNnNMT12QSsgEx2xqNmz\n"
//				+ "Wksps9Xe36X2/+Z1xi+XFFedOa8c/hq0loJhu2oDaR07F5uA07zCDY9xkOeM+wVa\n"
//				+ "09STy6XVJk7JOpZMzcAHu87s85NQ4y/bzL2hKUeQGURemBP3pjxryAxowdsAFhj/\n"
//				+ "6pbbpiQeGMaZOkk8FPWEmH/zLb+ohWxu17HBWGL4Tl/cfDZSc78SWiHkE8dx2qFx\n"
//				+"bQIDAQAB\n"
//				+ "-----END PUBLIC KEY-----";


		//PublicKey publicKey = decodePublicKey(pemToDer(realmPublicKey));
		PublicKey publicKey = decodePublicKey(pemToDer(PUBLIC_KEY_PEM));

		//This line will throw an exception if it is not a signed JWS (as expected)
//		Claims claims = Jwts.parser()
//				//.setSigningKey(DatatypeConverter.parseBase64Binary(apiKey.getSecret()))
//				.setSigningKey(publicKey)
//				.parseClaimsJws(accessTokenString).getBody();
//


		Claims claims = verifyToken(accessTokenString, publicKey);

		Jws<Claims> claimsJws = Jwts.parser() //
				.setSigningKey(publicKey) //
				.parseClaimsJws(accessTokenString) //
				;

		System.out.println(claimsJws);
		//gives: header={alg=RS256},body={jti=f47335d5-9da0-4ada-a99f-4805a59ddba4, exp=1457366908, nbf=0, iat=1457366608, iss=http://login.acme.local:8081/auth/realms/acme, aud=vaadin-app, sub=65992f79-6382-4d98-9af2-90cf5f76fe91, typ=Bearer, azp=vaadin-app, session_state=12ee8e0f-d380-41fa-8311-c2b42368bff9, client_session=f7503bbd-ade6-4457-be21-4744d1bf1784, allowed-origins=[http://localhost:7777], resource_access={acme-petclinic={roles=[admin, user]}, vaadin-app={roles=[user]}, account={roles=[manage-account, view-profile]}}, name=Theo Tester, preferred_username=theo, given_name=Theo, family_name=Tester, email=tom+theo@localhost},signature=frMCkpDKG4VixXRZhh7KZqjDCxPbZq_6Wrl5X6RhjlGs9hL22Z6pcsVSlIzpincdwbLCpLYpLs3T2LRrlZ-YNUGOnKObnrmlVbMNi8UmGJiAj0bAsIPYWEfA-Ww3wuTitfjo0fgbAb8F_sLsPR9qjE6BcDPVXR2S_SJVWJ1CKb5kwiwKTTzAMUo1H22Ce64hoeSuEdQFM1x1n-M8kTkLPUPnL_lj-mOIpqbLZyrls3_TEL3up0-XYyF2Gt9fDQKXTp_XPLizGUiiY90TQC4rhNye3JPLMB6RZnQFmyJq5I5Cq0ybdMarloeLjvYjc3RyIgZgtFWjk5aNYDaietBJSA

		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());


	}


	private static Claims verifyToken(String token, PublicKey publicKey) {
		Claims claims;
		try {
			//This line will throw an exception if it is not a signed JWS (as expected)
			claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
			System.out.println("ID: " + claims.getId());

		} catch (Exception e) {

			claims = null;
		}
		return claims;
	}

	/**
	 * Decode a PEM string to DER format
	 *
	 * @param pem
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] pemToDer(String pem) throws IOException {
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
		KeyFactory kf = KeyFactory.getInstance("RSA"
				//        , "BC" //use provider BouncyCastle if available.
		);
		return kf.generatePublic(spec);
	}


	public static PrivateKey convertKeySpecToPrivateKey(PKCS8EncodedKeySpec keySpec) {
		KeyFactory kf;
		PrivateKey privKey;
		try {
			kf = KeyFactory.getInstance("RSA");
			privKey = kf.generatePrivate(keySpec);
			return privKey;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	public static PublicKey convertKeySpecToPublicKey(PKCS8EncodedKeySpec keySpec) {
		KeyFactory kf;
		PublicKey pubKey;
		try {
			kf = KeyFactory.getInstance("RSA");
			pubKey = kf.generatePublic(keySpec);
			return pubKey;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	public PrivateKey getPrivateKey(String private_key_pem) throws IOException {
		StringBuilder pkcs8Lines = new StringBuilder();
		BufferedReader rdr = new BufferedReader(new StringReader(private_key_pem));
		String line;
		while ((line = rdr.readLine()) != null) {
			pkcs8Lines.append(line);
		}
		// Remove the "BEGIN" and "END" lines, as well as any whitespace
		String pkcs8Pem = pkcs8Lines.toString();
		System.out.printf("pkcs8Pem:%s", pkcs8Pem);
		pkcs8Pem = pkcs8Pem.replace("-----BEGIN PRIVATE KEY-----", "");
		pkcs8Pem = pkcs8Pem.replace("-----END PRIVATE KEY-----", "");
		pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");

		// Base64 decode the result

		//byte [] pkcs8EncodedBytes = Base64.decode(pkcs8Pem, Base64.DEFAULT);
		byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(new String(pkcs8Pem).getBytes("UTF-8"));

		// extract the private key
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
		PrivateKey privateKey = convertKeySpecToPrivateKey(keySpec);
		System.out.println(privateKey);
		return privateKey;
	}

	public PublicKey getPublicKey(String public_key_pem) throws IOException {
		StringBuilder pkcs8Lines = new StringBuilder();
		BufferedReader rdr = new BufferedReader(new StringReader(public_key_pem));
		String line;
		while ((line = rdr.readLine()) != null) {
			pkcs8Lines.append(line);
		}
		// Remove the "BEGIN" and "END" lines, as well as any whitespace
		String pkcs8Pem = pkcs8Lines.toString();
		System.out.printf("pkcs8Pem:%s", pkcs8Pem);
		pkcs8Pem = pkcs8Pem.replace("-----BEGIN PUBLIC KEY-----", "");
		pkcs8Pem = pkcs8Pem.replace("-----END PUBLIC KEY-----", "");
		pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");

		// Base64 decode the result

		//byte [] pkcs8EncodedBytes = Base64.decode(pkcs8Pem, Base64.DEFAULT);
		byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(new String(pkcs8Pem).getBytes("UTF-8"));

		// extract the private key
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
		PublicKey publicKey = convertKeySpecToPublicKey(keySpec);
		System.out.println(publicKey);
		return publicKey;
	}
}

	//validate token robert  03/27
//    private static Claims validateTokenNew(String token, PublicKey publicKey) {
//        Claims claims;
//        try {
//            //This line will throw an exception if it is not a signed JWS (as expected)
//            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
//            System.out.println("ID: " + claims.getId());
//
//        } catch (Exception e) {
//
//            claims = null;
//        }
//        return claims;
//    }

	//validate token robert  03/27
	/*
	public Boolean validateToken(String token, UserDetails userDetails) throws IOException {
		final String username = getUsernameFromToken(token);
		if (!username.equals(userDetails.getUsername())) {
			System.out.printf("username %s mismatch. %n", username); // TODO This always work in current implementation does not make sense
			return false;
		}
		if (isTokenExpired(token)) {
			System.out.println("validateTokenNew() - TokenExpired");
			return false;
		}

		Claims claims;
		try {
			//This line will throw an exception if it is not a signed JWS (as expected)
			PublicKey publicKey = decodePublicKey(pemToDer(PUBLIC_KEY_PEM));
			claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
			System.out.println("ID: " + claims.getId());
		} catch (Exception e) {
			claims = null;
		}

		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());

		return true;
	} */
/*
	PublicKey publicKey = (PublicKey) kp.getPublic();
	PrivateKey privateKey = (PrivateKey) kp.getPrivate();
	String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
	String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            System.out.println("Public Key:");
            System.out.printf("-----BEGIN PUBLIC KEY-----%n%s%n-----END PUBLIC KEY-----%n", encodedPublicKey);
            System.out.println("Private Key:");
            System.out.printf("-----BEGIN RSA PRIVATE KEY-----%n%s%n-----END RSA PRIVATE KEY-----%n", encodedPrivateKey);
	//SSH.com does not use ASN.1 in its key format, but OpenSSL/OpenSSH does, for unencrypted RSA keys
            System.out.println("GREAT STUFF BUT THIS IS NOT WHAT I WANT TO USE. ALSO PUTTY CAN NOT IMPORT IT.");

	StringBuilder pkcs8Lines = new StringBuilder();
	BufferedReader rdr = new BufferedReader(new StringReader(PRIVATE_KEY));
	String line;
            while ((line = rdr.readLine()) != null) {
		pkcs8Lines.append(line);
	}
	// Remove the "BEGIN" and "END" lines, as well as any whitespace
	String pkcs8Pem = pkcs8Lines.toString();
            System.out.printf("pkcs8Pem:%s", pkcs8Pem);
	pkcs8Pem = pkcs8Pem.replace("-----BEGIN PRIVATE KEY-----", "");
	pkcs8Pem = pkcs8Pem.replace("-----END PRIVATE KEY-----", "");
	pkcs8Pem = pkcs8Pem.replaceAll("\\s+","");

	// Base64 decode the result

	//byte [] pkcs8EncodedBytes = Base64.decode(pkcs8Pem, Base64.DEFAULT);
	byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(new String(pkcs8Pem).getBytes("UTF-8"));

	// extract the private key

	PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
	//KeyFactory kf = KeyFactory.getInstance("RSA");
	//KeyFactory kf = getKF();
	//PrivateKey privKey = kf.generatePrivate(keySpec);
	PrivateKey privateKey2 = convertKeySpecToPrivateKey(keySpec);
            System.out.println(privateKey2);
	String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey2.getEncoded());
            System.out.println("Private Key (OUR preloaded key but different after encoding why  and putty does not like it ASN.1 decoding failure):");
            System.out.printf("-----BEGIN RSA PRIVATE KEY-----%n%s%n-----END RSA PRIVATE KEY-----%n", encodedPrivateKey);

	UUID uuid = UUID.randomUUID();
	String jti = uuid.toString();

            System.out.printf("JWT jti: %s %n", jti); // TODO REMOVE when done
            System.out.printf("JWT private key: %s %n", privateKey2); // TODO REMOVE when done
            System.out.printf("JWT seconds: %s %n", accesstoken_validity_seconds); // TODO REMOVE when done

}
*/