package com.user.task.util;

import java.util.Base64;

public class EncodingUtil {
    public static String encodeString(String input) {
        //encoding using base64 encoder
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes());
        return new String(encodedBytes);
    }

    public static String decodeString(String encodedInput) {
        //decoding using base64 decoder
        byte[] decodedBytes = Base64.getDecoder().decode(encodedInput);
        return new String(decodedBytes);
    }

}