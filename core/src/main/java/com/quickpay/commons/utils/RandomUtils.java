package com.quickpay.commons.utils;


import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class RandomUtils {

    private static final Logger log = LoggerFactory.getLogger(RandomUtils.class);

    private static final String ALPHABETIC_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String ALPHABETIC_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC_CHARS = "0123456789";
    private static final String LOWER_FAULT_CHARS = ALPHABETIC_LOWER + NUMERIC_CHARS ;
    private static final String UPPER_FAULT_CHARS = ALPHABETIC_UPPER + NUMERIC_CHARS ;
    private static final String DEFAULT_CHARS = ALPHABETIC_LOWER + NUMERIC_CHARS + ALPHABETIC_UPPER;

    /**
     * An instance of secure random to ensure randomness is secure.
     */
    private static final SecureRandom RANDOMIZER = getNativeInstance();

    static SecureRandom getNativeInstance() {
        try {
            return SecureRandom.getInstance("SHA1PRNG"); //NativePRNGNonBlocking
        } catch (final NoSuchAlgorithmException e) {
            log.warn("SHA1PRNG algorithm not available. Falling back to default SecureRandom.", e);
            return new SecureRandom();
        }
    }


    /**
     *
     *Generate a random string of specified length, containing only numbers
     * @param length
     * @return
     */
    public static String randomNum(int length) {
        return random(length, NUMERIC_CHARS);
    }


    /**
     *
     *Generate a random string of specified length, which may contain lowercase letters + uppercase letters + numbers
     * @param length
     * @return
     */

    public static String randomAlph(int length) {
        return random(length, DEFAULT_CHARS);
    }

    /**
     *Generate a random string of specified length, which may contain uppercase letters + numbers
     *
     * @param length
     * @return
     */
    public static String randomUpper(int length) {
        return random(length, UPPER_FAULT_CHARS);
    }

    /**
     *
     *Generate a random string of specified length, which may contain lowercase letters + numbers
     * @param length
     * @return
     */
    public static String randomLower(int length) {
        return random(length, LOWER_FAULT_CHARS);
    }


    public static String uuid32(){
        UUID uuid = UUID.randomUUID();

        String uuidString = uuid.toString();

        return uuidString.replace("-", "");
    }


    /**
     *
     *Generate a random string of specified length based on the specified complete set of characters
     * @param length
     * @param chars
     * @return
     */
    private static String random(int length, char[] chars) {
        if (chars == null || chars.length == 0) {
            throw new IllegalArgumentException("random string cannot be generated");
        }
        return random(length, new String(chars));
    }

    /**
     * Generate a random string of specified length based on the specified complete set of characters
     *
     * @return
     */
    private static String random(int length, String chars) {
        return IntStream.range(0, length)
                .map(i -> RANDOMIZER.nextInt(chars.length()))
                .mapToObj(randomInt -> chars.substring(randomInt, randomInt + 1))
                .collect(Collectors.joining());
    }



    /**
     *
     * Generate a random byte array of specified length and convert it to a base64 url encoded string
     *
     * @param size
     * @return
     */
    public static String randomBase64(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Invalid random string length: " + size);
        }
        return Base64.encodeBase64URLSafeString(randomStringBytes(size));
    }

    public static byte[] randomStringBytes(int size) {
        final byte[] randomBytes = new byte[size];
        RANDOMIZER.nextBytes(randomBytes);
        return randomBytes;
    }





}

