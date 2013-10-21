package kloSpringServer.service;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Created with IntelliJ IDEA.
 * User: Joona
 * Date: 5.11.2012
 * Time: 18:57
 */
@Service
public class PasswordEncryption {
    private static final int HASH_BITS = 160;
    private static final int HASH_ROUNDS = 200;

    public byte[] encryptPassword(String password, byte[] salt) {
//        System.out.println("Encrypting password " + password + " with salt " + Arrays.toString(salt));
        byte[] hash = null;

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, HASH_ROUNDS, HASH_BITS);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public byte[] createSalt() {
        byte[] salt = null;
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            salt = new byte[8];
            random.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return salt;
    }
}
