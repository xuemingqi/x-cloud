package com.x.framework.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.URLUtil;
import com.x.framework.common.dto.VerifyImage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

/**
 * @author : xuemingqi
 * @since : 2024/8/12 上午10:04
 */
@Slf4j
public class VerifyImageUtil {
    /**
     * 抠图的边框宽度
     */
    private static final int SLIDER_IMG_OUT_PADDING = 1;


    /**
     * 获取滑块验证图片
     *
     * @param file 图片文件
     * @return 验证图片对象
     */
    public static VerifyImage getVerifyImage(File file) {
        try {
            BufferedImage srcImage = ImageIO.read(file);
            int cutWidth = srcImage.getWidth() / 5;
            int cutHeight = srcImage.getHeight() / 5;
            int locationX = cutWidth + new Random().nextInt(srcImage.getWidth() - cutWidth * 3);
            int locationY = (srcImage.getHeight() - cutHeight) / 2;
            BufferedImage cutImage = new BufferedImage(cutWidth, cutHeight, BufferedImage.TYPE_4BYTE_ABGR);
            int[][] data = getBlockData(cutWidth, cutHeight);
            cutImgByTemplate(srcImage, cutImage, data, locationX, locationY, cutWidth, cutHeight);
            return new VerifyImage().setSrcImage(getImageBASE64(srcImage))
                    .setCutImage(getImageBASE64(cutImage))
                    .setXPosition(locationX)
                    .setYPosition(locationY);
        } catch (Exception e) {
            log.error("获取滑块验证图片失败:{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * 获取裁剪块数据
     *
     * @return 裁剪块数据
     */
    private static int[][] getBlockData(int cutWidth, int cutHeight) {
        int[][] data = new int[cutWidth][cutHeight];
        Random random = new Random();
        int circleR = cutHeight / 8;
        int rectanglePadding = cutWidth / 8;

        // 确定上方凸出区域的圆心坐标
        double x1 = rectanglePadding + (cutWidth - 2 * rectanglePadding) / 2.0 - 5 + random.nextInt(10);
        double y1 = rectanglePadding - random.nextInt(3);

        // 确定左侧凹进区域的圆心坐标
        double x2 = rectanglePadding + circleR - 2 - random.nextInt(2 * circleR - 4);
        double y2 = rectanglePadding + (cutHeight - 2 * rectanglePadding) / 2.0 - 4 + random.nextInt(10);

        // 确定右侧凸出区域的圆心坐标
        double x3 = cutWidth - rectanglePadding + random.nextInt(3);
        double y3 = rectanglePadding + (cutHeight - 2 * rectanglePadding) / 2.0 - 5 + random.nextInt(10);

        double po = Math.pow(circleR, 2);

        // 填充滑块的形状数据
        for (int i = 0; i < cutWidth; i++) {
            for (int j = 0; j < cutHeight; j++) {
                boolean fill;
                if ((i >= rectanglePadding && i < cutWidth - rectanglePadding)
                        && (j >= rectanglePadding && j < cutHeight - rectanglePadding)) {
                    data[i][j] = 1;
                    fill = true;
                } else {
                    data[i][j] = 0;
                    fill = false;
                }
                double d3 = Math.pow(i - x1, 2) + Math.pow(j - y1, 2);
                if (d3 < po) {
                    data[i][j] = 1;
                } else {
                    if (!fill) {
                        data[i][j] = 0;
                    }
                }
                double d4 = Math.pow(i - x2, 2) + Math.pow(j - y2, 2);
                if (d4 < po) {
                    data[i][j] = 0;
                }
                double d5 = Math.pow(i - x3, 2) + Math.pow(j - y3, 2);
                if (d5 < po) {
                    data[i][j] = 1;
                }
            }
        }

        // 添加滑块的边界阴影
        for (int i = 0; i < cutWidth; i++) {
            for (int j = 0; j < cutHeight; j++) {
                for (int k = 1; k == SLIDER_IMG_OUT_PADDING; k++) {
                    if (i >= rectanglePadding - k && i < rectanglePadding
                            && ((j >= rectanglePadding - k && j < rectanglePadding)
                            || (j >= cutHeight - rectanglePadding - k && j < cutHeight - rectanglePadding + 1))) {
                        data[i][j] = 2;
                    }
                    if (i >= cutWidth - rectanglePadding + k - 1 && i < cutWidth - rectanglePadding + 1) {
                        for (int n = 1; n == SLIDER_IMG_OUT_PADDING; n++) {
                            if (((j >= rectanglePadding - n && j < rectanglePadding)
                                    || (j >= cutHeight - rectanglePadding - n && j <= cutHeight - rectanglePadding))) {
                                data[i][j] = 2;
                                break;
                            }
                        }
                    }
                }
                if (data[i][j] == 1 && j - SLIDER_IMG_OUT_PADDING > 0 && data[i][j - SLIDER_IMG_OUT_PADDING] == 0) {
                    data[i][j - SLIDER_IMG_OUT_PADDING] = 2;
                }
                if (data[i][j] == 1 && j + SLIDER_IMG_OUT_PADDING < cutHeight && data[i][j + SLIDER_IMG_OUT_PADDING] == 0) {
                    data[i][j + SLIDER_IMG_OUT_PADDING] = 2;
                }
                if (data[i][j] == 1 && i - SLIDER_IMG_OUT_PADDING > 0 && data[i - SLIDER_IMG_OUT_PADDING][j] == 0) {
                    data[i - SLIDER_IMG_OUT_PADDING][j] = 2;
                }
                if (data[i][j] == 1 && i + SLIDER_IMG_OUT_PADDING < cutWidth && data[i + SLIDER_IMG_OUT_PADDING][j] == 0) {
                    data[i + SLIDER_IMG_OUT_PADDING][j] = 2;
                }
            }
        }
        return data;
    }

    /**
     * 裁剪区块
     * 根据生成的滑块形状，对原图和裁剪块进行变色处理
     *
     * @param oriImage    原图
     * @param targetImage 裁剪图
     * @param blockImage  滑块
     * @param x           裁剪点x
     * @param y           裁剪点y
     */
    private static void cutImgByTemplate(BufferedImage oriImage, BufferedImage targetImage, int[][] blockImage, int x, int y, int cutWidth, int cutHeight) {
        for (int i = 0; i < cutWidth; i++) {
            for (int j = 0; j < cutHeight; j++) {
                int x1 = x + i;
                int y1 = y + j;
                int rgbFlg = blockImage[i][j];
                int rgb_ori = oriImage.getRGB(x1, y1);
                if (rgbFlg == 1) {
                    targetImage.setRGB(i, j, rgb_ori);
                    oriImage.setRGB(x1, y1, Color.LIGHT_GRAY.getRGB());
                } else if (rgbFlg == 2) {
                    targetImage.setRGB(i, j, Color.WHITE.getRGB());
                    oriImage.setRGB(x1, y1, Color.GRAY.getRGB());
                } else if (rgbFlg == 0) {
                    targetImage.setRGB(i, j, rgb_ori & 0x00ffffff);
                }
            }
        }
    }

    /**
     * 将图片转换为Base64字符串
     *
     * @param image 图片
     * @return Base64字符串
     */
    public static String getImageBASE64(BufferedImage image) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", out);
            return URLUtil.getDataUriBase64("image/png", Base64.encode(out.toByteArray()));
        } catch (Exception e) {
            log.error("图片转换为Base64字符串失败:{}", ExceptionUtils.getStackTrace(e));
            return null;
        }
    }
}
