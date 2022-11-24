package com.cqsd.command.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author caseycheng
 * @date 2022/11/12-15:41
 **/
@Slf4j
public class QRCodeUtils {
	private static final String CHARSET = "utf-8";
	private static final int QRCODE_SIZE = 300;
	private static final Map<EncodeHintType, Object> hints = new Hashtable<>();
	
	static {
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, 1);
	}
	
	public static BufferedImage createQRImage(String content) {
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
		} catch (WriterException e) {
			log.error("[二维码生成异常]{}", e.getMessage());
			throw new RuntimeException(e);
		}
		int width = bitMatrix.getWidth();
		final var height = bitMatrix.getHeight();
		final var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		return image;
	}
	public static void witeImage(String content, OutputStream outputStream){
		final var image = createQRImage(content);
		try {
			ImageIO.write(image,"jpeg",outputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
