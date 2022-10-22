package Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomPaint {

    private final BufferedImage buffer;
    private Graphics2D graphics2D;
    private Color color;
    private int stroke;

    private static class Constants {
        final static Color DEFAULT_COLOR = Color.BLACK;
        final static int DEFAULT_STROKE = 1;

        enum StrokeDirection {
            X_AXIS, Y_AXIS
        }
    }

    public CustomPaint(Graphics2D graphics2D) {
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        this.graphics2D = graphics2D;
        this.color = Constants.DEFAULT_COLOR;
        this.stroke = Constants.DEFAULT_STROKE;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setStroke(int stroke) {
        if (stroke > 0) {
            this.stroke = stroke;
        } else {
            this.stroke = Constants.DEFAULT_STROKE;
        }
    }

    public void drawPixel(int x, int y) {
        buffer.setRGB(0, 0, this.color.getRGB());
        graphics2D.drawImage(buffer, x, y, null);
    }

    public void drawLine(int x0, int y0, int x1, int y1) {
        drawLine(x0, y0, x1, y1, new int[]{1});
    }

    public void drawLine(int x0, int y0, int x1, int y1, int[] mask) {
        final int dx = x1 - x0;
        final int dy = y1 - y0;

        int steps;
        Constants.StrokeDirection strokeDirection;
        double A, B, p;

        if (Math.abs(dx) > Math.abs(dy)) {
            steps = Math.abs(dx);
            strokeDirection = Constants.StrokeDirection.Y_AXIS;
            A = (double) 2 * dy;
            B = A + (2 * dx);
            p = A + dx;
        } else {
            steps = Math.abs(dy);
            strokeDirection = Constants.StrokeDirection.X_AXIS;
            A = (double) 2 * dx;
            B = A + (2 * dy);
            p = A + dy;
        }

        final double xInc = (double) dx / steps;
        final double yInc = (double) dy / steps;

        double x = x0;
        double y = y0;

        if (mask[0] != 0)
            drawStrokedLineSegment(x0, y0, strokeDirection);

        for (int k = 1; k < steps; k++) {
            switch (strokeDirection) {
                case X_AXIS -> y += yInc;
                case Y_AXIS -> x += xInc;
            }

            if (p < 0) {
                p += A;
            } else {
                switch (strokeDirection) {
                    case X_AXIS -> x += xInc;
                    case Y_AXIS -> y += yInc;
                }
                p += B;
            }

            if (mask[k % mask.length] != 0)
                drawStrokedLineSegment((int) x, (int) y, strokeDirection);
        }
    }

    private void drawStrokedLineSegment(int x, int y, Constants.StrokeDirection direction) {
        final int pixelsAbove = this.stroke >> 1;
        if (direction == Constants.StrokeDirection.X_AXIS) {
            for (int i = 1; i <= pixelsAbove; i++) {
                drawPixel(x - i, y);
            }
            for (int i = 0; i < this.stroke - pixelsAbove; i++) {
                drawPixel(x + i, y);
            }
        } else {
            for (int i = 1; i <= pixelsAbove; i++) {
                drawPixel(x, y - i);
            }
            for (int i = 0; i < this.stroke - pixelsAbove; i++) {
                drawPixel(x, y + i);
            }
        }
    }

    public void fillRectangle(int x0, int y0, int x1, int y1) {
        var bounds = this.graphics2D.getClipBounds();

        BufferedImage bufferedImage = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D temp = this.graphics2D;
        this.graphics2D = bufferedImage.createGraphics();

        drawRectangle(x0, y0, x1, y1);
        Fills.fill(bufferedImage, x0 + (x1 - x0) / 2, y0 + (y1 - y0) / 2, this.color, this.color);
        this.graphics2D = temp;
        this.graphics2D.drawImage(bufferedImage, 0, 0, null);
    }

    public void drawRectangle(int x0, int y0, int x1, int y1) {
        int xFrom, xTo, yFrom, yTo;

        if (x0 < x1) {
            xFrom = x0;
            xTo = x1;
        } else {
            xFrom = x1;
            xTo = x0;
        }

        if (y0 < y1) {
            yFrom = y0;
            yTo = y1;
        } else {
            yFrom = y1;
            yTo = y0;
        }

        drawLine(xFrom, yFrom, xTo, yFrom);
        drawLine(xTo, yFrom, xTo, yTo);
        drawLine(xTo, yTo, xFrom, yTo);
        drawLine(xFrom, yTo, xFrom, yFrom);
    }

    public void drawRectangleFromCenter(int x, int y, int width, int height) {
        final int midWidth = width / 2;
        final int midHeight = height / 2;
        drawLine(x - midWidth, y - midHeight, x + midWidth, y - midHeight);
        drawLine(x + midWidth, y - midHeight, x + midWidth, y + midHeight);
        drawLine(x + midWidth, y + midHeight, x - midWidth, y + midHeight);
        drawLine(x - midWidth, y + midHeight, x - midWidth, y - midHeight);
    }

    public void fillCircle(int xCenter, int yCenter, int r) {
        var bounds = this.graphics2D.getClipBounds();

        BufferedImage bufferedImage = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D temp = this.graphics2D;
        this.graphics2D = bufferedImage.createGraphics();

        drawCircle(xCenter, yCenter, r);
        Fills.fill(bufferedImage, xCenter, yCenter, this.color, this.color);
        this.graphics2D = temp;
        this.graphics2D.drawImage(bufferedImage, 0, 0, null);
    }

    public void drawCircle(int xCenter, int yCenter, int r) {
        drawCircle(xCenter, yCenter, r, new int[]{1});
    }

    public void drawCircle(int xCenter, int yCenter, int r, int[] mask) {
        int xOuter = r + this.stroke - 1;
        int xInner = r;
        int y = 0;
        int errOuter = 1 - xOuter;
        int errInner = 1 - xInner;

        while (xOuter >= y) {
            if (mask[y % mask.length] != 0) {
                drawVerticalLine(xCenter + y, yCenter + xInner, yCenter + xOuter); // SSE
                drawHorizontalLine(xCenter + xInner, xCenter + xOuter, yCenter - y); // ENE
                drawVerticalLine(xCenter - y, yCenter - xOuter, yCenter - xInner); // NNO
                drawHorizontalLine(xCenter - xOuter, xCenter - xInner, yCenter + y); // OSO
            }

            if (mask[mask.length - (y % mask.length) - 1] != 0) {
                drawHorizontalLine(xCenter + xInner, xCenter + xOuter, yCenter + y); // ESE
                drawVerticalLine(xCenter - y, yCenter + xInner, yCenter + xOuter); // SSO
                drawHorizontalLine(xCenter - xOuter, xCenter - xInner, yCenter - y); // ONO
                drawVerticalLine(xCenter + y, yCenter - xOuter, yCenter - xInner); //NNE
            }


            y++;

            if (errOuter < 0) {
                errOuter += 2 * y + 1;
            } else {
                xOuter--;
                errOuter += 2 * (y - xOuter) + 1;
            }

            if (y > r) {
                xInner = y;
            } else if (errInner < 0) {
                errInner += 2 * y + 1;
            } else {
                xInner--;
                errInner += 2 * (y - xInner) + 1;
            }
        }
    }

    private void drawHorizontalLine(int x0, int x1, int y) {
        while (x0 <= x1)
            drawPixel(x0++, y);
    }

    private void drawVerticalLine(int x, int y0, int y1) {
        while (y0 <= y1)
            drawPixel(x, y0++);
    }

    public void fillEllipse(int rx, int ry, int xc, int yc) {
        var bounds = this.graphics2D.getClipBounds();

        BufferedImage bufferedImage = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D temp = this.graphics2D;
        this.graphics2D = bufferedImage.createGraphics();

        drawEllipse(rx, ry, xc, yc);
        Fills.fill(bufferedImage, xc, yc, this.color, this.color);
        this.graphics2D = temp;
        this.graphics2D.drawImage(bufferedImage, 0, 0, null);
    }

    public void drawEllipse(int rx, int ry, int xc, int yc) {
        int x = 0;
        int y = ry;

        double d1 = (ry * ry) - (rx * rx * ry) + (0.25f * rx * rx);
        double dx = 0;
        double dy = 2 * rx * rx * y;

        while (dx < dy) {
            drawPixel(xc + x, yc + y);
            drawPixel(xc - x, yc + y);
            drawPixel(xc + x, yc - y);
            drawPixel(xc - x, yc - y);

            x++;
            dx += 2 * ry * ry;

            if (d1 < 0) {
                d1 += dx + (ry * ry);
            } else {
                y--;
                dy -= 2 * rx * rx;
                d1 += dx - dy + (ry * ry);
            }
        }

        double d2 = ((ry * ry) * ((x + 0.5f) * (x + 0.5f)))
                + ((rx * rx) * ((y - 1) * (y - 1)))
                - (rx * rx * ry * ry);

        while (y >= 0) {
            drawPixel(xc + x, yc + y);
            drawPixel(xc - x, yc + y);
            drawPixel(xc + x, yc - y);
            drawPixel(xc - x, yc - y);

            y--;
            dy -= 2 * rx * rx;

            if (d2 > 0) {
                d2 += (rx * rx) - dy;
            } else {
                x++;
                dx += 2 * ry * ry;
                d2 += dx - dy + (rx * rx);
            }
        }
    }

}
