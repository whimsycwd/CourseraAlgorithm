/**
 * 1.	The reference solution outputs a line segment in the order p¡úq¡úr¡ús but my solution outputs 
 * 		it in the reverse order s¡úr¡úq¡úp. Is that ok? Yes, there are two valid ways to output a line segment.
 * Sol. That mean the order types are restricted to two.
 * 		We should recover the order! 
 * 		and Make output order right!
 * 2.   I'm having trouble avoiding subsegments Fast.java when there are 5 or more points on a line segment. Any advice? 
 * 		Not handling the 5-or-more case is a bit tricky, so don't kill yourself over it.
 * Sol. I use a hash set to record the used slope.  more detail in Fast.WrapperPoint.
 * 
 * date : 2013-09-07
 * @author whimsy
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Fast {

	private class WrapperPoint implements Comparable<WrapperPoint> {
		private Point point = null;
		private Set<Double> hash;
		public final Comparator<WrapperPoint> SLOPE_ORDER = new WrapperPointComparator();

		public WrapperPoint(Point point) {
			this.point = point;
			hash = new HashSet<Double>();

		}

		public void draw() {
			point.draw();
		}

		private class WrapperPointComparator implements
				Comparator<WrapperPoint> {

			@Override
			public int compare(WrapperPoint o1, WrapperPoint o2) {

				return point.SLOPE_ORDER.compare(o1.point, o2.point);
			}

		}

		public void drawTo(WrapperPoint wrapperPoint) {
			point.drawTo(wrapperPoint.point);

		}

		@Override
		public int compareTo(WrapperPoint o) {
			return point.compareTo(o.point);
		}

		@Override
		public String toString() {
			return point.toString();
		}

		public Double slopeTo(WrapperPoint wrapperPoint) {

			return point.slopeTo(wrapperPoint.point);
		}

		public void add(Double slopeTo) {
			hash.add(slopeTo);
		}

		public boolean contain(Double value) {
			return hash.contains(value);
		}

	}

	public static void main(String[] args) {

		String filename = args[0];

		// String filename = "in.txt";
		In in = new In(filename);

		int n = in.readInt();
		int x, y;
		WrapperPoint[] points = new WrapperPoint[n];

		Fast fast = new Fast();

		StdDraw.setScale(0, 32768);

		for (int i = 0; i < n; ++i) {
			x = in.readInt();
			y = in.readInt();
			points[i] = fast.new WrapperPoint(new Point(x, y));
			points[i].draw();
		}
		Arrays.sort(points);
		for (int i = 0; i < n; ++i) {

			Arrays.sort(points, i + 1, n, points[i].SLOPE_ORDER);

			/*
			 * for (int k = 0;k<n;++k){ System.out.println(points[k]); }
			 * System.out
			 * .println("------------------- cut -------------------");
			 */
			int tp = i + 2;
			int length = 1;
			while (tp < n) {
				// System.out.println(points[i].slopeTo(points[tp-1])+","+points[i].slopeTo(points[tp]));
				if (points[i].SLOPE_ORDER.compare(points[tp - 1], points[tp]) == 0) {
					++length;
				} else {
					if (length >= 3) {
						// System.out.println(points[i].slopeTo(points[tp -
						// 1]));

						if (points[tp - 1].contain(points[i]
								.slopeTo(points[tp - 1]))) {
							// if this has been printed, then just ignore it.
						} else {
							System.out.print(points[i]);
							// make the output order right for output
							Arrays.sort(points, tp - length, tp);
							for (int j = tp - length; j < tp; ++j) {
								System.out.print(" -> " + points[j]);
								// add to hash. The hash was used to distinguish
								// repeat count. In fact, just need to change
								// points[tp-1];
								points[j].add(points[i].slopeTo(points[j]));
							}
							System.out.println();
							points[i].drawTo(points[tp - 1]);

						}
					}
					length = 1;
				}
				++tp;
			}
			// count the last one
			if (length >= 3) {

				// System.out.println(points[i].slopeTo(points[tp - 1]));

				if (points[tp - 1].contain(points[i].slopeTo(points[tp - 1]))) {
					// if this has been printed, then just ignore it.
				} else {
					System.out.print(points[i]);
					// make the output order right for output
					Arrays.sort(points, tp - length, tp);
					for (int j = tp - length; j < tp; ++j) {
						System.out.print(" -> " + points[j]);
						// add to hash. The hash was used to distinguish repeat
						// count.In fact, just need to change points[tp-1];
						points[j].add(points[i].slopeTo(points[j]));
					}

					System.out.println();
					points[i].drawTo(points[tp - 1]);
				}
			}

			// recover the order
			Arrays.sort(points);
		}

	}
}
