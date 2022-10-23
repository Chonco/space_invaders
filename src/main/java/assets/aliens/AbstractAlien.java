package assets.aliens;

import java.awt.*;
import java.awt.image.BufferedImage;

import graphics.CustomPaint;

import static assets.AssetsConstants.PIXEL_SIZE;

abstract class AbstractAlien {
    private BufferedImage firstAlienAsset;
    private BufferedImage secondAlienAsset;

    public BufferedImage getFirstAlienAsset() {
        if (firstAlienAsset == null) {
            firstAlienAsset = generateFirstAlienAsset(new BasicCanvas());
        }

        return firstAlienAsset;
    }

    public BufferedImage getSecondAlienAsset() {
        if (secondAlienAsset == null) {
            secondAlienAsset = generateSecondAlienAsset(new BasicCanvas());
        }

        return secondAlienAsset;
    }

    protected abstract BufferedImage generateFirstAlienAsset(BasicCanvas basicCanvas);

    protected abstract BufferedImage generateSecondAlienAsset(BasicCanvas basicCanvas);

    public abstract Rectangle getBounds();

    public abstract Point originPointOfBounds();

    protected static class BasicCanvas {
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
