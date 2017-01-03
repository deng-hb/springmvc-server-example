package com.denghb.server.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by denghb on 2017/1/3.
 */
public class Fu {

    /**
     * @param fileUrl 文件绝对路径或相对路径
     * @return 读取到的缓存图像
     * @throws IOException 路径错误或者不存在该文件时抛出IO异常
     */
    public static BufferedImage getBufferedImage(String fileUrl)
            throws IOException {
        File f = new File(fileUrl);
        return ImageIO.read(f);
    }

    /**
     * @param savedImg 待保存的图像
     * @param saveDir  保存的目录
     * @param fileName 保存的文件名，必须带后缀，比如 "beauty.jpg"
     * @param format   文件格式：jpg、png或者bmp
     * @return
     */
    public static boolean saveImage(BufferedImage savedImg, String saveDir,
                                    String fileName, String format) {
        boolean flag = false;

        // 先检查保存的图片格式是否正确
        String[] legalFormats = {"jpg", "JPG", "png", "PNG", "bmp", "BMP"};
        int i = 0;
        for (i = 0; i < legalFormats.length; i++) {
            if (format.equals(legalFormats[i])) {
                break;
            }
        }
        if (i == legalFormats.length) { // 图片格式不支持
            System.out.println("不是保存所支持的图片格式!");
            return false;
        }

        // 再检查文件后缀和保存的格式是否一致
        String postfix = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (!postfix.equalsIgnoreCase(format)) {
            System.out.println("待保存文件后缀和保存的格式不一致!");
            return false;
        }

        String fileUrl = saveDir + fileName;
        File file = new File(fileUrl);
        try {
            flag = ImageIO.write(savedImg, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flag;
    }

    public static void main(String[] args) throws IOException {

        int maxX = 7;
        int maxY = 10;

        Random r = new Random();
        int px = r.nextInt(maxX);
        int py = r.nextInt(maxY);

        int cx = r.nextInt(maxX);
        int cy = r.nextInt(maxY);

        int fx = r.nextInt(maxX);
        int fy = r.nextInt(maxY);

        // 读取待合并的文件
        BufferedImage xImg = getBufferedImage("1.png");// 笑脸
        BufferedImage yImg = getBufferedImage("2.png");// 心脸

        // 将要生成的图片尺寸
        int width = xImg.getWidth() * 7;
        int height = xImg.getHeight() * 10;

        int w = xImg.getWidth();
        int h = xImg.getHeight();

        BufferedImage targetImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {

                // 从图片中读取RGB
                int[] imageArray = null;
                if ((px == x && py == y) || (cx == x && cy == y) || (fx == x && fy == y)) {

                    imageArray = new int[yImg.getWidth() * yImg.getHeight()];
                    imageArray = yImg.getRGB(0, 0, w, h, imageArray, 0, w);

                } else {
                    imageArray = new int[xImg.getWidth() * xImg.getHeight()];
                    imageArray = xImg.getRGB(0, 0, w, h, imageArray, 0, w);
                }
                // 生成新图片
                targetImg.setRGB(x * w, y * h, w, h, imageArray, 0, w);
            }

        }

        // 保存图像
        saveImage(targetImg, "target/", "luguo.gif", "gif");
        System.out.println("生成完毕!");


    }

}
