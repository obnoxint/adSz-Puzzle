package net.obnoxint.adsz.puzzle;

import org.lwjgl.util.Point;

class Box {

    static final int EDGE_TOP = 0;
    static final int EDGE_RIGHT = 1;
    static final int EDGE_BOTTOM = 2;
    static final int EDGE_LEFT = 3;

    private Point upperLeft, lowerRight;

    Box(final Point upperLeft, final Point lowerRight) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
    }

    int getDistanceToPoint(final Point p) {
        final int dx = Math.abs(p.getX() - getUpperLeft().getX());
        final int dy = Math.abs(p.getY() - getUpperLeft().getY());
        return Math.round((float) Math.sqrt((((float) dx * dx) + ((float) dy * dy))));
    }

    Point[] getEdge(final int edge, final int offset) {
        final Point[] r = new Point[2];
        switch (edge) {
        case EDGE_TOP:
            r[0] = new Point(upperLeft.getX() - offset, upperLeft.getY() - offset);
            r[1] = new Point(lowerRight.getX() + offset, upperLeft.getY() - offset);
        break;
        case EDGE_RIGHT:
            r[0] = new Point(lowerRight.getX() + offset, upperLeft.getY() - offset);
            r[1] = new Point(lowerRight.getX() + offset, lowerRight.getY() + offset);
        break;
        case EDGE_BOTTOM:
            r[0] = new Point(upperLeft.getX() - offset, lowerRight.getY() + offset);
            r[1] = new Point(lowerRight.getX() + offset, lowerRight.getY() + offset);
        break;
        case EDGE_LEFT:
            r[0] = new Point(upperLeft.getX() - offset, upperLeft.getY() - offset);
            r[1] = new Point(upperLeft.getX() - offset, lowerRight.getY() + offset);
        break;
        default:
            r[0] = new Point(upperLeft);
            r[1] = new Point(lowerRight);
        }
        return r;
    }

    int getHeight() {
        return lowerRight.getY() - upperLeft.getY();
    }

    Point getLowerRight() {
        return lowerRight;
    }

    Point getUpperLeft() {
        return upperLeft;
    }

    int getWidth() {
        return lowerRight.getX() - upperLeft.getX();
    }

    boolean isInside(final int x, final int y) {
        return isInside(new Point(x, y));
    }

    boolean isInside(final Point point) {
        return (point.getX() >= upperLeft.getX() && point.getX() <= lowerRight.getX() && point.getY() >= upperLeft.getY() && point.getY() <= lowerRight.getY());
    }

    void move(final int x, final int y) {
        final int w = getWidth();
        final int h = getHeight();
        final Point ul = new Point(upperLeft.getX() + x, upperLeft.getY() + y);
        final Point lr = new Point(ul.getX() + w, ul.getY() + h);
        upperLeft = ul;
        lowerRight = lr;
    }

    void moveTo(final int x, final int y) {
        final int w = getWidth();
        final int h = getHeight();
        final Point ul = new Point(x, y);
        final Point lr = new Point(ul.getX() + w, ul.getY() + h);
        upperLeft = ul;
        lowerRight = lr;
    }

    void setLowerRight(final Point lowerRight) {
        this.lowerRight = lowerRight;
    }

    void setUpperLeft(final Point upperLeft) {
        this.upperLeft = upperLeft;
    }

}
