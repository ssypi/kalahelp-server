package kloSpringServer.service;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PasswordEncryptionTest {
//    @Autowired
    PasswordEncryption encryption;

    @Before
    public void setUp() throws Exception {
        encryption = new PasswordEncryption();
    }

    @Test
    public void testEncryptPassword() throws Exception {
        String password = "testPassword";
        byte[] expectedHash = {43, 96, -83, -10, -8, -96, 43, 108, 99, 65,
                99, -52, 96, -24, -11, 119, 31, -64, 51, 116};

        byte[] salt = {-44, 56, 64, -8, 31, -5, -42, 79};
        byte[] salt2 = {-22, 23, 34, -1, 30, -2, -11, 12};

        byte[] hash = encryption.encryptPassword(password, salt);
        byte[] hash2 = encryption.encryptPassword(password, salt2);

        assertTrue(hash.length == 20); // 160 bits / 20 bytes
        assertTrue(hash2.length == 20); // 160 bits / 20 bytes

        assertThat(hash, not(equalTo(hash2)));
        assertThat(hash, equalTo(expectedHash));

//        System.out.println(Arrays.toString(hash));
//        System.out.println(encrypted.length);
    }

    @Test
    public void testCreateSalt() throws Exception {
        byte[] salt = encryption.createSalt();
        assertTrue(salt.length == 8);
    }
}
