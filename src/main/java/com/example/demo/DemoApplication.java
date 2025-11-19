package com.example.demo;

import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public String privateKey = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDLVsdz+LfAvKrm\n" +
            "xMrI3uBqgJ7vxtYUPV0VpV1IXO86VG+tz9UBpfcdAq0GELT0Q+INBTf0pO9wAa4z\n" +
            "WCtGNIF+dZFc5YJH6+w5ry/oyWkmpk/G7Xx+D40tkoJfDSeEG3cfdZ5JsK1UhgHI\n" +
            "RDeEdgQNEU03/nBvaZD6Xknic3GtajFh/ljiigbKgCW2URyPlepVc3xybPW3xqcJ\n" +
            "CpkNdNZxnTZuKbCiqaT1FSgAfR85rs1kwDpfvETVqqA9YP2f/oa7o3LlqV2Wp9Is\n" +
            "XwfYVrYgjzRVZd/nIA/0qyFPC1m2jvQ2EkWrIOxqJry93SeV0/Si83JLZhjH023t\n" +
            "VXIZxufnAgMBAAECggEAEvmVOwpdypbjxqb2Z3OvWUqJMMXxnbAZdIonoARjVjwH\n" +
            "5rfeYD0Z4Znc0vqSVrlsKeivqPaoc1+f+NTRYPrh9Khi+ScsfURHGVDytXq9UCjn\n" +
            "7x/W5LdtzLyY/U0i2WFr83V/emmKAZ+2h3cG/6dSetrlxYP9/2xh/4/2vbK/mNeF\n" +
            "do5gyl1s+D0BrH3HxQ4QNTE8JmALhzF9rQlsDjUqLz2PrvZv88FqARZaeCVD45nQ\n" +
            "4ZVZJ/clR9ZjBuV++xH1+fyojOlQW0vzFUWFNq0OTKKxWDL8mWdOPRpibEOn4Z2W\n" +
            "E9lUqUncXHAgplDZRyQ2FvCVAPya2quznQ+ERiWh4QKBgQD0nF7FQ1FDx0X5J6HG\n" +
            "QHg4T0J5b+adUlcokO6XYC/AsS9/yiHYgRizsy6oYyOsko1P4PR0HBg7RWqnET7s\n" +
            "dp0l9+yhmMF5/W8nx0u5MuJqi0X31i4vAgEAF/5gqu5k66V4FVM4YDtpo4qzm2Js\n" +
            "hgVJrXMXRQgfLNbMJl0BpyOPhwKBgQDUzniAyIyFaNaCpDcUp7kGYZo2EuVv9RNV\n" +
            "UwfFoeIbyrHdJXJb+NacXts6QOfOahhmL8MJZ//1dph4TfReuGVOf8zDT+8KPRJX\n" +
            "BKsw1YofLuPHNP/Xlx7EZbrqqnsXkyo6rdzUkFUEqnN8pwgdlBRaqot4u91/ij6x\n" +
            "+noveIo8oQKBgDyBjYO4AhsE6M8XuZGDkvlCycumHWJe4sZbC1mQZl+AtKIkiri2\n" +
            "Pjj9IpZuZ32hhqjymSU7adcHdbk7r9foAzlIGVVjpsAZK2yohJeb+ZunNbh3V1cz\n" +
            "ZLH0PW0F+xAlpfmNNNemHiwkUwPYdMEpaZdu+yAPaFHl0oAPIURuZoJzAoGAJ5rp\n" +
            "mGY6qNWTHqs7SQ/SApsFnUy1ALomx/l099YiA7QcmCDRAMb2AhIxUM0llyfttI7t\n" +
            "CIIfg+p3g+RWN46v5AUq8aqdvVUSZbmLtsH3O8h5QejeOJx6cGjSebwSvpd4A4W6\n" +
            "FKFsATtL1QYtH3wgsaMN5AFqvsLW2j7YoJ8ZcyECgYEAr3JenEadzhHOZBuHLa25\n" +
            "aFamm2Ggq/Jz9HhOLUwZKlOpF1eRurIZeiaAze+kGRFjVjrccBXaWoVzV8+W+bfg\n" +
            "EN2Eh2CqUdbyAFhkLkOJas1lnqbQ9wtY8HiLEOwoO+Yu3A+7o678XAtFQ0VFFgHH\n" +
            "R4ltkuslz0TWF4d3TXSpeXA=\n" +
            "-----END PRIVATE KEY-----\n";



    // Sử dụng ĐÚNG secret key giống như trong Kong configuration
    private static final String SECRET_KEY = "mobile-secret-key-123";
    private static final long EXPIRATION_TIME = 10 * 60 * 60 * 1000; // 10 hours
    @PostConstruct
    public void generateToken() throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", Arrays.asList("user"));
        claims.put("userId", 12345);
        String token = createToken(claims, "ndvan");
        System.out.println("=== GENERATED TOKEN ===");
        System.out.println("Token: " + token);

        // Verify token locally
        //verifyToken(token);
    }
    private String createToken(Map<String, Object> claims, String subject) throws Exception {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("mobile-app-issuer") // Must match Kong consumer key
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.RS256, loadPrivateKey())
                .compact();
    }

    private void verifyToken(String token) {
        try {
            var claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("=== TOKEN VERIFICATION ===");
            System.out.println("✅ Token verified successfully!");
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issuer: " + claims.getIssuer());
            System.out.println("Expiration: " + claims.getExpiration());

        } catch (Exception e) {
            System.err.println("❌ Token verification failed: " + e.getMessage());
        }
    }


    private PrivateKey loadPrivateKey() throws Exception {
        String  privateKeyContent = privateKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
