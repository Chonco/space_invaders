import java.awt.*;
import java.awt.image.BufferedImage;

public class Alien {
    private static final int PIXEL_SIZE = 5;

    private static BufferedImage firstAlienAsset;
    private static BufferedImage secondAlienAsset;

    public static BufferedImage getFirstAlienAsset() {
        if (firstAlienAsset == null) {
            firstAlienAsset = generateFirstAlienAsset();
        }

        return firstAlienAsset;
    }

    public static BufferedImage getSecondAlienAsset() {
        if (secondAlienAsset == null) {
            secondAlienAsset = generateSecondAlienAsset();
        }

        return secondAlienAsset;
    }

    private static BufferedImage generateFirstAlienAsset() {
        BasicCanvas basicCanvas = new BasicCanvas();

        basicCanvas.customPaint.setColor(Color.WHITE);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 5, PIXEL_SIZE * 1, PIXEL_SIZE * 9, PIXEL_SIZE * 2);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 2, PIXEL_SIZE * 2, PIXEL_SIZE * 12, PIXEL_SIZE * 3);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 1, PIXEL_SIZE * 3, PIXEL_SIZE * 13, PIXEL_SIZE * 4);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 1, PIXEL_SIZE * 4, PIXEL_SIZE * 4, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 6, PIXEL_SIZE * 4, PIXEL_SIZE * 8, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 10, PIXEL_SIZE * 4, PIXEL_SIZE * 13, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 1, PIXEL_SIZE * 5, PIXEL_SIZE * 13, PIXEL_SIZE * 6);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 4, PIXEL_SIZE * 6, PIXEL_SIZE * 6, PIXEL_SIZE * 7);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 8, PIXEL_SIZE * 6, PIXEL_SIZE * 10, PIXEL_SIZE * 7);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 3, PIXEL_SIZE * 7, PIXEL_SIZE * 4, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 6, PIXEL_SIZE * 7, PIXEL_SIZE * 8, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 10, PIXEL_SIZE * 7, PIXEL_SIZE * 11, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 4, PIXEL_SIZE * 8, PIXEL_SIZE * 5, PIXEL_SIZE * 9);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 9, PIXEL_SIZE * 8, PIXEL_SIZE * 10, PIXEL_SIZE * 9);

        return basicCanvas.bufferedImage;
    }

    private static BufferedImage generateSecondAlienAsset() {
        BasicCanvas basicCanvas = new BasicCanvas();

        basicCanvas.customPaint.setColor(Color.WHITE);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 5, PIXEL_SIZE * 1, PIXEL_SIZE * 9, PIXEL_SIZE * 2);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 2, PIXEL_SIZE * 2, PIXEL_SIZE * 12, PIXEL_SIZE * 3);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 1, PIXEL_SIZE * 3, PIXEL_SIZE * 13, PIXEL_SIZE * 4);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 1, PIXEL_SIZE * 4, PIXEL_SIZE * 4, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 6, PIXEL_SIZE * 4, PIXEL_SIZE * 8, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 10, PIXEL_SIZE * 4, PIXEL_SIZE * 13, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 1, PIXEL_SIZE * 5, PIXEL_SIZE * 13, PIXEL_SIZE * 6);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 4, PIXEL_SIZE * 6, PIXEL_SIZE * 6, PIXEL_SIZE * 7);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 8, PIXEL_SIZE * 6, PIXEL_SIZE * 10, PIXEL_SIZE * 7);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 3, PIXEL_SIZE * 7, PIXEL_SIZE * 5, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 6, PIXEL_SIZE * 7, PIXEL_SIZE * 8, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 9, PIXEL_SIZE * 7, PIXEL_SIZE * 11, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 1, PIXEL_SIZE * 8, PIXEL_SIZE * 3, PIXEL_SIZE * 9);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 11, PIXEL_SIZE * 8, PIXEL_SIZE * 13, PIXEL_SIZE * 9);

        return basicCanvas.bufferedImage;
    }

    private static class BasicCanvas {
        BufferedImage bufferedImage;
        Graphics2D graphics2D;
        CustomPaint customPaint;

        BasicCanvas() {
            bufferedImage = new BufferedImage(PIXEL_SIZE * 14, PIXEL_SIZE * 10, BufferedImage.TYPE_INT_RGB);
            graphics2D = bufferedImage.createGraphics();
            graphics2D.setClip(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

            customPaint = new CustomPaint(graphics2D);

            customPaint.setColor(Color.BLACK);
            customPaint.fillRectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        }
    }
}
