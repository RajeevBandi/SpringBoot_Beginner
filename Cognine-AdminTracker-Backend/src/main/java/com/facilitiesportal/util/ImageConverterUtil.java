package com.facilitiesportal.util;

import java.util.Base64;

import org.springframework.stereotype.Component;

/**
 * Utility class for converting images between byte arrays and Base64 strings.
 */
@Component
public class ImageConverterUtil {

	/**
     * Converts a byte array representing an image to a Base64 string.
     * 
     * @param byteArray The byte array representing the image.
     * @return The Base64 string representation of the image.
     */
	public static String byteArrayToBase64(byte[] byteArray) {
		return Base64.getEncoder().encodeToString(byteArray);
	}

    /**
     * Converts a Base64 string representing an image to a byte array.
     * 
     * @param base64String The Base64 string representation of the image.
     * @return The byte array representing the image.
     */
	public static byte[] base64ToByteArray(String base64String) {
		return Base64.getDecoder().decode(base64String);
	}
}
