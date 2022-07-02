package org.example.baseUtils;

import com.google.gson.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.out;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseUtils implements Colors {

    public static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
        Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }).registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(DateTimeFormatter.ofPattern("d MMM uuuu HH:mm:ss").format(localDateTime))).setPrettyPrinting().create();
    public static Gson gsonWithNulls = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    private final static Scanner readText = new Scanner(System.in);
    private final static Scanner readNumerics = new Scanner(System.in);

    public static String readText() {
        return readText.nextLine();
    }

    public static String readText(String data) {
        print(data, Colors.BLUE);
        return readText.nextLine();
    }


    public static String readText(String data, String color) {
        print(data, color);
        return readText.nextLine();
    }

    public static void print(String data) {
        print(data, Colors.BLUE);
    }

    public static void print(String data, String color) {
        out.print(color + data + Colors.RESET);
    }

    public static void println(Object data) {
        println(data, Colors.BLUE);
    }


    public static void println(Object data, String color) {
        out.println(color + data + Colors.RESET);
    }

    public static void println(String data, Object... args) {
        out.printf((data) + "%n", args);
    }

    public static String otp(int bound) {
        return String.valueOf((new Random().nextInt((int) Math.pow(10, bound - 1), (int) Math.pow(10, bound))));
    }

    public static String encryptWithKey(String key, String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        byte[] KeyData = key.getBytes();
        SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");

        cipher.init(Cipher.ENCRYPT_MODE, KS);
        byte[] bytes = cipher.doFinal(text.getBytes());
        return new String(Base64.getEncoder().encode(bytes));
    }

    public static String decryptWithKey(String key, String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] KeyData = key.getBytes();
        SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, KS);
        byte[] decr = cipher.doFinal(Base64.getDecoder().decode(text.getBytes()));
        return new String(decr);
    }

    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    }

    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
