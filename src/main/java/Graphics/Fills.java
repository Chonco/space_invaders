package Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

class Fills {
    private enum FillType {
        FLOOD, SCAN_LINE
    }

    private static final FillType fillType = FillType.FLOOD;

    static void fill(BufferedImage target, int xFrom, int yFrom, Color fill, Color border) {
        switch (fillType) {
            case FLOOD -> flood(target, xFrom, yFrom, fill, border);
            case SCAN_LINE -> scanLine(target, xFrom, yFrom, fill, border);
        }
    }

    private static void flood(BufferedImage target, int xFrom, int yFrom, Color fill, Color border) {
        Stack<Point> callStack = new Stack<>();
        callStack.add(new Point(xFrom, yFrom));
        while (!callStack.isEmpty()) {
            Point point = callStack.pop();
            try {
                if (target.getWidth() > point.x &&
                        target.getHeight() > point.y &&
                        target.getRGB(point.x, point.y) != border.getRGB() &&
                        target.getRGB(point.x, point.y) != fill.getRGB()) {
                    target.setRGB(point.x, point.y, fill.getRGB());
                    callStack.add(new Point(point.x + 1, point.y));
                    callStack.add(new Point(point.x - 1, point.y));
                    callStack.add(new Point(point.x, point.y + 1));
                    callStack.add(new Point(point.x, point.y - 1));
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                exception.printStackTrace();
            }
        }
    }

    private static void scanLine(BufferedImage target, int x, int y, Color fill, Color border) {
        if (target.getRGB(x, y) != border.getRGB()
                && target.getRGB(x, y) != fill.getRGB()) {
            target.setRGB(x, y, fill.getRGB());
            scanLine(target, x + 1, y, fill, border);
            scanLine(target, x - 1, y, fill, border);
            scanLine(target, x, y - 1, fill, border);
            scanLine(target, x, y + 1, fill, border);
        }
    }
}
