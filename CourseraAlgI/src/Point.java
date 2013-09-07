/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 * 
 * date: 2013-09-07
 * @author whimsy 
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

	// compare points by slope
	public final Comparator<Point> SLOPE_ORDER = new PointComparator(); // YOUR DEFINITION HERE

	private final int x; // x coordinate
	private final int y; // y coordinate
	
	private static final double EPS = 1e-8;

	// create the point (x, y)
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	// plot this point to standard drawing
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	// draw line between this point and that point to standard drawing
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// slope between this point and that point
	public double slopeTo(Point that) {
		/* YOUR CODE HERE */

		// degenerate line segment(between a point and itself)
		if (this.x == that.x && this.y == that.y)
			return Double.NEGATIVE_INFINITY;

		// the slope of vertical line segment
		if (this.x == that.x)
			return Double.POSITIVE_INFINITY;

		// the slope of a horizontal line segment
		if (that.y == this.y)
			return +0;

		// normal one
		return (double) (that.y - this.y) / (that.x - this.x);

	}

	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		if (this.y < that.y)
			return -1;
		if (this.y > that.y)
			return 1;
		if (this.x < that.x)
			return -1;
		if (this.x > that.x)
			return 1;
		return 0;
	}

	// return string representation of this point
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	private class PointComparator implements Comparator<Point> {

		@Override
		public int compare(Point o1, Point o2) {
			if (Point.this.slopeTo(o1) < Point.this.slopeTo(o2) - EPS)
				return -1;
			if (Point.this.slopeTo(o1) > Point.this.slopeTo(o2) + EPS)
				return 1;
			return 0;
		}

	}

	// unit test
	public static void main(String[] args) {
		/* YOUR CODE HERE */
	}
}