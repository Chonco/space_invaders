package assets;

import graphics.CustomPaint;

import java.awt.*;
import java.awt.image.BufferedImage;

import static assets.AssetsConstants.OBJECTS_COLOR;
import static assets.AssetsConstants.PIXEL_SIZE;

public class Player implements CollidableAsset {
    private BufferedImage asset;

    public Player() {
        getAsset();
    }

    public BufferedImage getAsset() {
        if (asset == null) {
            asset = generateAsset();
        }

        return asset;
    }

    private BufferedImage generateAsset() {
        BufferedImage bufferedImage = new BufferedImage(PIXEL_SIZE * 9, PIXEL_SIZE * 5, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setClip(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        CustomPaint customPaint = new CustomPaint(graphics2D);

        customPaint.setColor(OBJECTS_COLOR);

        customPaint.fillRectangle(PIXEL_SIZE * 4, 0, PIXEL_SIZE * 5, PIXEL_SIZE * 1);
        customPaint.fillRectangle(PIXEL_SIZE * 3, PIXEL_SIZE * 1, PIXEL_SIZE * 6, PIXEL_SIZE * 2);
        customPaint.fillRectangle(PIXEL_SIZE * 1, PIXEL_SIZE * 2, PIXEL_SIZE * 8, PIXEL_SIZE * 3);
        customPaint.fillRectangle(0, PIXEL_SIZE * 3, PIXEL_SIZE * 9, PIXEL_SIZE * 5);

        return bufferedImage;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(PIXEL_SIZE * 9, PIXEL_SIZE * 5);
    }
}
