package ch.bbzw.m151.swshop.controller;

import ch.bbzw.m151.swshop.model.Login;
import com.amazonaws.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/token")
public class TokenController {
    private static final String SECRET_KEY = "secret_key";
    private static final String SALT = "salt";
    private static final byte[] iv = SecureRandom.getSeed(16);

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public TokenController(final StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping
    public String generateToken(@RequestBody final Login login) throws Exception {
        final Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        final String result = Base64.encodeAsString(cipher.doFinal(login.toString().getBytes(StandardCharsets.UTF_8)));
        redisTemplate.opsForValue().set(result, result, 10, TimeUnit.SECONDS);
        return result;
    }

    @PutMapping
    public Login getToken(@RequestBody final String token) throws Exception {
        if (redisTemplate.opsForValue().setIfPresent(token, token, 10, TimeUnit.SECONDS)) {
            final Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            final String login = new String(cipher.doFinal(Base64.decode(token)));
            return Login.fromString(login);
        }
        throw new IllegalStateException("Expired token");
    }

    private Cipher getCipher(final int cipherMode) throws Exception {
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        final KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        final SecretKey tmp = factory.generateSecret(spec);
        final SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(cipherMode, secretKey, ivParameterSpec);
        return cipher;
    }

}
