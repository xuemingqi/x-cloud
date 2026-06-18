package com.x.work.test;

import com.x.work.WorkApplication;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

/**
 * @author : xuemingqi
 * @since : 2024/3/6 11:43
 */
@Slf4j
@SpringBootTest(classes = WorkApplication.class)
public class OrcTest {

    @Resource
    private Tesseract tesseract;

    @Test
    public void testOcr() throws Exception {
        FileInputStream inputStream = new FileInputStream("D:\\微信图片_20240306141759.jpg");
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        System.out.println(tesseract.doOCR(bufferedImage));
    }
}
