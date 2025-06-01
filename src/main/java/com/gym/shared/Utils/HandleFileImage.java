package com.gym.shared.Utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Component
public class HandleFileImage {

    public byte[] stringToByteArray(String file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String base64Image = file.split(",")[1];
        return Base64.getDecoder().decode(base64Image);
    }

    public String getImageType(byte[] imageBytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            return String.valueOf(ImageIO.read(bais).getType());
        } catch (IOException e) {
            throw new RuntimeException("Error al determinar el tipo de imagen", e);
        }
    }
}
