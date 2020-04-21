package com.example.jwtgenerate;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@SpringBootApplication
public class JwtgenerateApplication {

	private final static int accesstoken_validity_seconds = 3600;

	private final static String PRIVATE_KEY_PEM_PCS1 =
			"-----BEGIN RSA PRIVATE KEY-----\n" +
			"MIIEpAIBAAKCAQEA3qwdL3v0NGlOvrtHO9qbkoKJa34Nz9u2AUrq8ymzkeTCqm5G\n" +
			"DQZhZLJOSomb5sZDfe8V9wfCiMgAQzwGZ2M96+DvdFvI4PfSPkMplWF831TQzfUo\n" +
			"saNkcdpSBt/eqKrqGNnNMT12QSsgEx2xqNmzWksps9Xe36X2/+Z1xi+XFFedOa8c\n" +
			"/hq0loJhu2oDaR07F5uA07zCDY9xkOeM+wVa09STy6XVJk7JOpZMzcAHu87s85NQ\n" +
			"4y/bzL2hKUeQGURemBP3pjxryAxowdsAFhj/6pbbpiQeGMaZOkk8FPWEmH/zLb+o\n" +
			"hWxu17HBWGL4Tl/cfDZSc78SWiHkE8dx2qFxbQIDAQABAoIBAQDDKVbrJ1kCFspN\n" +
			"oRbt/swbvQ10iSbR51++AkuqlAIu/LEhb0U2ZUE3YI/+BF4ABgVhewtlWAfImoKg\n" +
			"axuxwemjs6Ir+cY2Zv5+U58+rhMkKtJXsWfZSbvfjdoW7F/atPI4layiW5wpwgJ6\n" +
			"vqvXsfbgTMBgPjL5Eh1KbFMNQOjJid4BcicXxR+XD2lQtLv0BKmaY6DA4uvsWWNp\n" +
			"aPeJVwtYodpTmZHuinU5gkXb4yGJyZy8ekmX8PbVIZHIMd5DvaY4SwaHE3LFkiD3\n" +
			"DzapLpCyhatTxyulpmmUpsub8CumhWkuSf7FAPMF4IZLetGv7TDwEeZfZP5+5ksN\n" +
			"G05tmKqBAoGBAPjkxUDqqrTD4JyxN6sysbH3JnX0CASq8UmMH5t7y34x8cFx52nv\n" +
			"CjO+Dcpp9TVHTcxJoruvbXDOngc0KACfkwet++g1xsmOpd4pBbLggSD6PWq6HSC3\n" +
			"Fi2LH3ofB5y/jdMyGB7YxJdEAmTdJq2+Ju5ipBW917SZ/w5GANzJBx7jAoGBAOUH\n" +
			"r1kbp6izMGLq+NAdmgozXdE8ZD71EC5ICq89jnwnCGNwwmMxb8fQJT/wFrTgIa/T\n" +
			"VtAqOm/QhPjl2Mr+WJYjooxK1ckwrdJLCLnGnOPIdYvn85iiyQaW/NTqrmgaVOO1\n" +
			"1B6ATXUjyPpy9WwsZP7MZux7YSv1mLx5UTouYU9vAoGBAM+9uvqVJAifGaIPtQRa\n" +
			"2sBNJrf7CJpEyAJ4R4VyjYX2T/ADj4D2+Q/5CO+zAu0GZ+QkbgSzxugKPRKMMPtc\n" +
			"Nc+KI6FNCXAAdWd52/zSsFUDKTIkx90flA5NeedncOYfaFQoEsPkcCpFpK3lfLw1\n" +
			"467DfGt9OkbO5nlWaq/dxui7AoGAKMTPb9s+YwdSq6kMFW/PhdVS8/X+Gj7hXHuy\n" +
			"ezNH88227oyZ7bJVJYpltxXz1Mq49GV4ZKG/uTuzD+NKTggsmL1LKBICMEgcCP0W\n" +
			"VouGTf3W/sqRUDBZ/HRQGU6VMS7OwaISWkOYhiuSkRJ5oYHdkIWuJJZp27tiLtqh\n" +
			"nOu4or0CgYAhrMggMRbKKiZMkt4JgYQ3ZtRgkHY56V2KVKtYg44+GxwOFcsL1rgh\n" +
			"TCYG0dOc87vEsa0Zj9b0FRWkr3P+to3h0i4hHKWKhAxGqeJaRHbkbWjKg+ZpYGm9\n" +
			"psNOUm74LvJUZMsGG9fcPJI85MrIwWXzBFJRrLDIN+VlB9YmtYjJKw==\n" +
			"-----END RSA PRIVATE KEY-----";

	@Value("${jwt.privateKeyPEM}")
	private static String PRIVATE_KEY_PEM_PCS8;

//	private final static String PRIVATE_KEY_PEM_PCS8 =
//			"-----BEGIN PRIVATE KEY-----\n" +
//			"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDerB0ve/Q0aU6+\n" +
//			"u0c72puSgolrfg3P27YBSurzKbOR5MKqbkYNBmFksk5KiZvmxkN97xX3B8KIyABD\n" +
//			"PAZnYz3r4O90W8jg99I+QymVYXzfVNDN9Sixo2Rx2lIG396oquoY2c0xPXZBKyAT\n" +
//			"HbGo2bNaSymz1d7fpfb/5nXGL5cUV505rxz+GrSWgmG7agNpHTsXm4DTvMINj3GQ\n" +
//			"54z7BVrT1JPLpdUmTsk6lkzNwAe7zuzzk1DjL9vMvaEpR5AZRF6YE/emPGvIDGjB\n" +
//			"2wAWGP/qltumJB4Yxpk6STwU9YSYf/Mtv6iFbG7XscFYYvhOX9x8NlJzvxJaIeQT\n" +
//			"x3HaoXFtAgMBAAECggEBAMMpVusnWQIWyk2hFu3+zBu9DXSJJtHnX74CS6qUAi78\n" +
//			"sSFvRTZlQTdgj/4EXgAGBWF7C2VYB8iagqBrG7HB6aOzoiv5xjZm/n5Tnz6uEyQq\n" +
//			"0lexZ9lJu9+N2hbsX9q08jiVrKJbnCnCAnq+q9ex9uBMwGA+MvkSHUpsUw1A6MmJ\n" +
//			"3gFyJxfFH5cPaVC0u/QEqZpjoMDi6+xZY2lo94lXC1ih2lOZke6KdTmCRdvjIYnJ\n" +
//			"nLx6SZfw9tUhkcgx3kO9pjhLBocTcsWSIPcPNqkukLKFq1PHK6WmaZSmy5vwK6aF\n" +
//			"aS5J/sUA8wXghkt60a/tMPAR5l9k/n7mSw0bTm2YqoECgYEA+OTFQOqqtMPgnLE3\n" +
//			"qzKxsfcmdfQIBKrxSYwfm3vLfjHxwXHnae8KM74Nymn1NUdNzEmiu69tcM6eBzQo\n" +
//			"AJ+TB6376DXGyY6l3ikFsuCBIPo9arodILcWLYsfeh8HnL+N0zIYHtjEl0QCZN0m\n" +
//			"rb4m7mKkFb3XtJn/DkYA3MkHHuMCgYEA5QevWRunqLMwYur40B2aCjNd0TxkPvUQ\n" +
//			"LkgKrz2OfCcIY3DCYzFvx9AlP/AWtOAhr9NW0Co6b9CE+OXYyv5YliOijErVyTCt\n" +
//			"0ksIucac48h1i+fzmKLJBpb81OquaBpU47XUHoBNdSPI+nL1bCxk/sxm7HthK/WY\n" +
//			"vHlROi5hT28CgYEAz726+pUkCJ8Zog+1BFrawE0mt/sImkTIAnhHhXKNhfZP8AOP\n" +
//			"gPb5D/kI77MC7QZn5CRuBLPG6Ao9Eoww+1w1z4ojoU0JcAB1Z3nb/NKwVQMpMiTH\n" +
//			"3R+UDk1552dw5h9oVCgSw+RwKkWkreV8vDXjrsN8a306Rs7meVZqr93G6LsCgYAo\n" +
//			"xM9v2z5jB1KrqQwVb8+F1VLz9f4aPuFce7J7M0fzzbbujJntslUlimW3FfPUyrj0\n" +
//			"ZXhkob+5O7MP40pOCCyYvUsoEgIwSBwI/RZWi4ZN/db+ypFQMFn8dFAZTpUxLs7B\n" +
//			"ohJaQ5iGK5KREnmhgd2Qha4klmnbu2Iu2qGc67iivQKBgCGsyCAxFsoqJkyS3gmB\n" +
//			"hDdm1GCQdjnpXYpUq1iDjj4bHA4VywvWuCFMJgbR05zzu8SxrRmP1vQVFaSvc/62\n" +
//			"jeHSLiEcpYqEDEap4lpEduRtaMqD5mlgab2mw05Sbvgu8lRkywYb19w8kjzkysjB\n" +
//			"ZfMEUlGssMg35WUH1ia1iMkr\n" +
//			"-----END PRIVATE KEY-----";

	@Value("${jwt.publicKeyPEM}")
	private static String PUBLIC_KEY_PEM;

//	private final static String PUBLIC_KEY_PEM =  "-----BEGIN PUBLIC KEY-----\n"
//			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3qwdL3v0NGlOvrtHO9qb\n"
//			+ "koKJa34Nz9u2AUrq8ymzkeTCqm5GDQZhZLJOSomb5sZDfe8V9wfCiMgAQzwGZ2M9\n"
//			+ "6+DvdFvI4PfSPkMplWF831TQzfUosaNkcdpSBt/eqKrqGNnNMT12QSsgEx2xqNmz\n"
//			+ "Wksps9Xe36X2/+Z1xi+XFFedOa8c/hq0loJhu2oDaR07F5uA07zCDY9xkOeM+wVa\n"
//			+ "09STy6XVJk7JOpZMzcAHu87s85NQ4y/bzL2hKUeQGURemBP3pjxryAxowdsAFhj/\n"
//			+ "6pbbpiQeGMaZOkk8FPWEmH/zLb+ohWxu17HBWGL4Tl/cfDZSc78SWiHkE8dx2qFx\n"
//			+"bQIDAQAB\n"
//			+ "-----END PUBLIC KEY-----";

	public static void main(String[] args) {
		//SpringApplication.run(JwtgenerateApplication.class, args);


		Map<String, Object> m = new HashMap<>();
		m.put("typ", "JWT");


		byte[] derPrv = pemToDer(PRIVATE_KEY_PEM_PCS8);
		PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(derPrv);
		PrivateKey privateKey2 = convertKeySpecPKCS8ToPrivateKey(keySpecPKCS8);  //PKCS#8 standard for private key encoding
		String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey2.getEncoded());
		System.out.println(privateKey2);
		System.out.println("Private Key (from properties) 100% after PEM -> Decode -> Encode -> PEM [putty: not recognised]"); //TODO check why
		System.out.printf("-----BEGIN PRIVATE KEY-----%n%s%n-----END PRIVATE KEY-----%n", encodedPrivateKey);

        /*  produces java.security.InvalidKeyException: IOException : algid parse error, not a sequence
		derPrv = pemToDer(PRIVATE_KEY_PEM_PCS1);
		keySpecPKCS8 = new PKCS8EncodedKeySpec(derPrv);
		privateKey2 = convertKeySpecPKCS8ToPrivateKey(keySpecPKCS8);  //PKCS#8 standard for private key encoding
		encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey2.getEncoded());
		System.out.println(privateKey2);
		System.out.println("Private Key (from properties) NO MATCH after PEM -> Decode -> Encode -> PEM [putty: 'ASN.1 decoding failure']"); //TODO check why
		System.out.printf("-----BEGIN PRIVATE KEY-----%n%s%n-----END PRIVATE KEY-----%n", encodedPrivateKey);
        */

		// PUBLIC_KEY_PEM not used here just for demo purpose
		byte[] derPub = pemToDer(PUBLIC_KEY_PEM);
		X509EncodedKeySpec  keySpecX509  = new X509EncodedKeySpec(derPub);
		PublicKey  publicKey1  = convertKeySpecX509ToPublicKey(keySpecX509);    //X509 standard for public key encoding
		String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey1.getEncoded());
		System.out.println(publicKey1);
		System.out.println("Public Key (from properties) 100% match after PEM -> Decode -> Encode -> PEM ");
		System.out.printf("-----BEGIN PUBLIC KEY-----%n%s%n-----END PUBLIC KEY-----%n", encodedPublicKey);


		UUID uuid = UUID.randomUUID();
		String jti = uuid.toString();

		System.out.printf("JWT jti: %s %n", jti);
		System.out.printf("JWT seconds: %s %n", accesstoken_validity_seconds);


		String token = Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + accesstoken_validity_seconds * 1000))
				.setHeader(m)
				.setIssuer("koby5i@google.com [not used]")
				.claim("groups", new String[] { "ski", "snowboard" })
				.claim("sub","Subject")
				.claim("email","bob@snowbird.com")
				.claim("authorities", new String[] { "ROLE_ADMIN", "ROLE_USER", "ROLE_CREATE" })
				.claim("jti", jti)
				//.signWith(SignatureAlgorithm.HS512, "secret").compact();
				.signWith(SignatureAlgorithm.RS256, privateKey2).compact();

		System.out.println("token generated:" + token);
		System.out.printf("token generated check https://jwt.io/: %s %n", token); // TODO REMOVE when done


	}

	// PEM:
	//   "-----BEGIN PRIVATE KEY-----
	//    MIIEpAIBAAKCAQEA3qwd ...
	//    L3v0NGlOvrtHO9qbkoKJa
	//    -----END PRIVATE KEY-----"
	// 1. strip BEGIN and END
	// 2. replace "\n" with ""
	// 3. convert pem (Base64 Encoded string) to der (bytes)
	public static byte[] pemToDer(String pem)  {

		String stripped = pem.replaceAll("-----BEGIN (.*)-----", "");
		stripped = stripped.replaceAll("-----END (.*)----", "");
		stripped = stripped.replaceAll("\r\n", "");
		stripped = stripped.replaceAll("\n", "");
		stripped = stripped.trim();

		byte[] der = Base64.getDecoder().decode(stripped);
		return der;
		//return Base64.getDecoder().decode(stripBeginEnd(stripped));
	}

	public static PrivateKey convertKeySpecPKCS8ToPrivateKey(PKCS8EncodedKeySpec keySpec){
		KeyFactory kf;
		PrivateKey privateKey;
		try {
			kf = KeyFactory.getInstance("RSA");
			privateKey = kf.generatePrivate(keySpec);
			return privateKey;
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.out.println("ERROR: in  convertKeySpecPKCS8ToPrivateKey(PKCS8EncodedKeySpec keySpec) " + e);
			throw new RuntimeException(e);
		}
	}

	public static PublicKey convertKeySpecX509ToPublicKey(X509EncodedKeySpec keySpec)  {

		KeyFactory kf;
		PublicKey publicKey;
		try {
			kf = KeyFactory.getInstance("RSA");
			// kf = KeyFactory.getInstance("RSA", "BC"); //use provider BouncyCastle if available.[not now]
			publicKey = kf.generatePublic(keySpec);
			return publicKey;
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.out.println("ERROR: in  convertKeySpecX509ToPublicKey(X509EncodedKeySpec keySpec) " + e);
			throw new RuntimeException(e);
		}
	}


}
