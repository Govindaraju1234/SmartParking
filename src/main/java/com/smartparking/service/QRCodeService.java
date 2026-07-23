package com.smartparking.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

@Service
public class QRCodeService {

    public String generateQRCode(String qrData, String fileName)
            throws Exception {

        String folderPath = "src/main/resources/static/qrcodes/";

        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String filePath = folderPath + fileName + ".png";

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                qrData,
                BarcodeFormat.QR_CODE,
                300,
                300
        );

        MatrixToImageWriter.writeToPath(
                bitMatrix,
                "PNG",
                new File(filePath).toPath()
        );

        return "/qrcodes/" + fileName + ".png";
    }
}
