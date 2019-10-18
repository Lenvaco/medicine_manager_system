package com.medicine.manager.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lenvaco
 * @date 2019/9/27 8:21
 */
@Slf4j
public class VerifyCodeUtil {
	private static String RANDOM_CODE = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";

	/*@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void captcha(HttpServletResponse response, HttpSession session) throws IOException {

		//清除缓存，每次访问该页面时都从服务器端读取
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		String verifyCode = drawCaptchaImage(byteArrayOutputStream);

		session.setAttribute("captcha", verifyCode);
		ServletOutputStream out = response.getOutputStream();
		byteArrayOutputStream.writeTo(out);
	}*/

	/**
	 * 生成对应图片
	 * @param output
	 * @return
	 */
	public static  String drawCaptchaImage(ByteArrayOutputStream output) {

		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

		// 设置验证码图片的长度和高度。
		int width = 70;
		int height = 30;
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//画图画板
		Graphics2D graphics = bufferedImage.createGraphics();
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		graphics.setFont(font);
		//设置画板背景颜色
		graphics.setColor(new Color(160, 120, 80));
		graphics.setBackground(new Color(255, 255, 255));
		graphics.clearRect(0, 0, width, height);
		//画线干扰
		for (int i = 0; i < 50; i++) {
			int x = threadLocalRandom.nextInt(width);
			int y = threadLocalRandom.nextInt(height);
			int x1 = threadLocalRandom.nextInt(15);
			int y1 = threadLocalRandom.nextInt(15);
			graphics.drawLine(x, y, x + x1, y + y1);
		}

		graphics.setColor(new Color(80, 80, 180));
		String randomCaptcha = getRandomCaptcha();

		FontRenderContext context = graphics.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(randomCaptcha, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		graphics.drawString(randomCaptcha, (int) x, (int) baseY);
		graphics.dispose();
		try {
			ImageIO.write(bufferedImage, "jpeg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return randomCaptcha;
	}

	/**
	 * 获得随机4位数
	 * @return
	 */
	private static String getRandomCaptcha() {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		//取4位长度验证码
		String randomCaptcha = "";
		for (int i = 0; i < 4; i++) {
			randomCaptcha += RANDOM_CODE.charAt(threadLocalRandom.nextInt(RANDOM_CODE.length()));
		}
		return randomCaptcha;
	}
}
