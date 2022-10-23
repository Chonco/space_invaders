package assets.aliens;

import java.awt.*;
import java.awt.image.BufferedImage;

import assets.CollidableAsset;
import graphics.CustomPaint;

import static assets.AssetsConstants.BACKGROUND_COLOR;
import static assets.AssetsConstants.PIXEL_SIZE;

public abstract class AbstractAlien implements CollidableAsset {
    private BufferedImage firstAlienAsset;
    private BufferedImage secondAlienAsset;

    public AbstractAlien() {
        getFirstAlienAsset();
        getSecondAlienAsset();
    }

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

    @Override
    public Rectangle getBounds() {
        return this.firstAlienAsset.getData().getBounds();
    }

    protected static class BasicCanvas {
        BufferedImage bufferedImage;
        Graphics2D graphics2D;
        CustomPaint customPaint;

        BasicCanvas() {
            bufferedImage = new BufferedImage(PIXEL_SIZE * 12, PIXEL_SIZE * 8, BufferedImage.TYPE_INT_RGB);
            graphics2D = bufferedImage.createGraphics();
            graphics2D.setClip(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

            customPaint = new CustomPaint(graphics2D);

            customPaint.setColor(BACKGROUND_COLOR);
            customPaint.fillRectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        }
    }
}
