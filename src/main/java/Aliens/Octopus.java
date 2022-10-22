package Aliens;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Octopus extends AbstractAlien {

    @Override
    protected BufferedImage generateFirstAlienAsset(BasicCanvas basicCanvas) {
        basicCanvas.customPaint.setColor(Color.WHITE);

        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 6, PIXEL_SIZE * 1, PIXEL_SIZE * 8, PIXEL_SIZE * 2);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 5, PIXEL_SIZE * 2, PIXEL_SIZE * 9, PIXEL_SIZE * 3);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 4, PIXEL_SIZE * 3, PIXEL_SIZE * 10, PIXEL_SIZE * 4);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 3, PIXEL_SIZE * 4, PIXEL_SIZE * 5, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 6, PIXEL_SIZE * 4, PIXEL_SIZE * 8, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 9, PIXEL_SIZE * 4, PIXEL_SIZE * 11, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 3, PIXEL_SIZE * 5, PIXEL_SIZE * 11, PIXEL_SIZE * 6);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 5, PIXEL_SIZE * 6, PIXEL_SIZE * 6, PIXEL_SIZE * 7);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 8, PIXEL_SIZE * 6, PIXEL_SIZE * 9, PIXEL_SIZE * 7);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 4, PIXEL_SIZE * 7, PIXEL_SIZE * 5, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 6, PIXEL_SIZE * 7, PIXEL_SIZE * 8, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 9, PIXEL_SIZE * 7, PIXEL_SIZE * 10, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 3, PIXEL_SIZE * 8, PIXEL_SIZE * 4, PIXEL_SIZE * 9);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 5, PIXEL_SIZE * 8, PIXEL_SIZE * 6, PIXEL_SIZE * 9);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 8, PIXEL_SIZE * 8, PIXEL_SIZE * 9, PIXEL_SIZE * 9);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 10, PIXEL_SIZE * 8, PIXEL_SIZE * 11, PIXEL_SIZE * 9);

        return basicCanvas.bufferedImage;
    }

    @Override
    protected BufferedImage generateSecondAlienAsset(BasicCanvas basicCanvas) {
        basicCanvas.customPaint.setColor(Color.WHITE);

        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 6, PIXEL_SIZE * 1, PIXEL_SIZE * 8, PIXEL_SIZE * 2);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 5, PIXEL_SIZE * 2, PIXEL_SIZE * 9, PIXEL_SIZE * 3);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 4, PIXEL_SIZE * 3, PIXEL_SIZE * 10, PIXEL_SIZE * 4);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 3, PIXEL_SIZE * 4, PIXEL_SIZE * 5, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 6, PIXEL_SIZE * 4, PIXEL_SIZE * 8, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 9, PIXEL_SIZE * 4, PIXEL_SIZE * 11, PIXEL_SIZE * 5);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 3, PIXEL_SIZE * 5, PIXEL_SIZE * 11, PIXEL_SIZE * 6);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 5, PIXEL_SIZE * 6, PIXEL_SIZE * 6, PIXEL_SIZE * 7);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 8, PIXEL_SIZE * 6, PIXEL_SIZE * 9, PIXEL_SIZE * 7);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 4, PIXEL_SIZE * 7, PIXEL_SIZE * 5, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 9, PIXEL_SIZE * 7, PIXEL_SIZE * 10, PIXEL_SIZE * 8);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 5, PIXEL_SIZE * 8, PIXEL_SIZE * 6, PIXEL_SIZE * 9);
        basicCanvas.customPaint.fillRectangle(PIXEL_SIZE * 8, PIXEL_SIZE * 8, PIXEL_SIZE * 9, PIXEL_SIZE * 9);


        return basicCanvas.bufferedImage;
    }
}