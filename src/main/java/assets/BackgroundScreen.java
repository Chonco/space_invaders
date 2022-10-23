package assets;

import graphics.CustomPaint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundScreen {
    private BufferedImage asset;

    public BufferedImage getAsset(int width, int height) {
        if (asset == null || asset.getWidth() != width || asset.getHeight() != height) {
            asset = generateAsset(width, height);
        }

        return asset;
    }

    private BufferedImage generateAsset(int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setClip(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        CustomPaint customPaint = new CustomPaint(graphics2D);

        customPaint.setColor(AssetsConstants.BACKGROUND_COLOR);
        customPaint.fillRectangle(0, 0, width, height);

        return bufferedImage;
    }
}
