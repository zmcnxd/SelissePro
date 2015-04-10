package com.selisse;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class WaterMarkUtils {

	/**
	  * 斜水印,重复水印,文字
	  * @param pressText  文字
	  * @param targetImg  目标图片
	  * @param fontName 字体名称
	  * @param colorStr 字体颜色字符串，格式如：#29944f
	  * @param fontSize  字体大小
	  * @param alpha 透明度(0.1-0.9)
	  * @param carelessness true为字体实心,false为字体空心
	  * @return
	  */
	public static int width;
	public static int height;
	public static BufferedImage pressText(String pressText, String targetImg,
			String fontName, String colorStr, int fontSize, float alpha,
			boolean carelessness) {
		try {

			File file = new File(targetImg);

			Image src = ImageIO.read(file);

			// 图片宽度
			width = src.getWidth(null);
			// 图片高度
			height = src.getHeight(null);

			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);

			Graphics2D g2d = image.createGraphics();
			// 绘原图
			g2d.drawImage(src, 0, 0, width, height, null);
			// 比例
			g2d.scale(1, 1);

			g2d.addRenderingHints(new RenderingHints(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON));
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			// 颜色
			Color color = Color.decode(colorStr);

			// 字体
			Font font = new Font(fontName, Font.PLAIN, fontSize);

			GlyphVector fontGV = font.createGlyphVector(g2d
					.getFontRenderContext(), pressText);
			Rectangle size = fontGV.getPixelBounds(g2d.getFontRenderContext(),
					0, 0);
			Shape textShape = fontGV.getOutline();
			double textWidth = size.getWidth();
			double textHeight = size.getHeight();
			AffineTransform rotate45 = AffineTransform
					.getRotateInstance(Math.PI / 4d);
			Shape rotatedText = rotate45.createTransformedShape(textShape);
			// use a gradient that repeats 4 times
			g2d.setPaint(new GradientPaint(0, 0, color, image.getWidth() / 2,
					image.getHeight() / 2, color));

			// 透明度
			g2d.setStroke(new BasicStroke(alpha));

			// step in y direction is calc'ed using pythagoras + 5 pixel padding
			double yStep = Math.sqrt(textWidth * textWidth / 2) + 5;
			// step over image rendering watermark text
			for (double x = -textHeight * 6; x < image.getWidth(); x += (textHeight * 6)) {
				double y = -yStep;
				for (; y < image.getHeight(); y += yStep) {
					g2d.draw(rotatedText);
					if (carelessness)// 字体实心
					{
						g2d.fill(rotatedText);
					}
					g2d.translate(0, yStep);
				}
				g2d.translate(textHeight * 6, -(y + yStep));
			}

			g2d.dispose();

			return image;
		} catch (IIOException e) {
			// logger.warn(e);
			System.out.print(e.getMessage());
		} catch (Exception e) {
			// logger.warn(e);
			System.out.print(e.getMessage());
		}
		return null;
	}
	
	public static void targetZoomOut(BufferedImage input,String waterFilePath){         //将目标图片缩小成256*256并保存
        try{
        Image big = input.getScaledInstance(width, height,Image.SCALE_DEFAULT);
        BufferedImage inputbig = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
        inputbig.getGraphics().drawImage(input, 0, 0, width, height, null); //画图


            File file2 =new File("e:/");            //此目录保存缩小后的关键图
            if(file2.exists()){
                 System.out.println("多级目录已经存在不需要创建！！");
            }else{
              //如果要创建的多级目录不存在才需要创建。
               file2.mkdirs();
             }
            ImageIO.write(inputbig, "jpg", new File(waterFilePath));   //将其保存在C:/imageSort/targetPIC/下
        } catch (Exception ex) {ex.printStackTrace();}
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WaterMarkUtils.targetZoomOut(WaterMarkUtils.pressText("微信号：zmcnxd", "e:/aaa.jpg",
				"雅黑", "#dddddd", 28, new Float(0.7), true),"e:/afteraaa.jpg");
	}

}
