package assets;

import graphics.CustomPaint;

import java.awt.*;
import java.awt.image.BufferedImage;

import static assets.AssetsConstants.PIXEL_SIZE;

public class Projectile implements CollidableAsset {
    private BufferedImage asset;

    public BufferedImage getAsset() {
        if (asset == null) {
            asset = generateAsset();
        }

        return asset;
    }

    private BufferedImage generateAsset() {
        BufferedImage bufferedImage = new BufferedImage(PIXEL_SIZE * 1, PIXEL_SIZE * 3, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setClip(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        CustomPaint customPaint = new CustomPaint(graphics2D);

        customPaint.setColor(Color.WHITE);
        customPaint.fillRectangle(0, 0, PIXEL_SIZE, PIXEL_SIZE * 3);

        return bufferedImage;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(PIXEL_SIZE * 1, PIXEL_SIZE * 3);
    }

    @Override
    public Point originPointOfBounds() {
        return new Point(0, 0);
    }
}
