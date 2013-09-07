import java.util.Arrays;
import java.util.Collections;

/**
 * 
 * Use In to read from file
 * 
 * 
 * date: 2013-09-07
 * 
 * @author whimsy
 * 
 */

public class Brute {
	public static void main(String[] args) {
	
		String filename = args[0];
//		String filename="in.txt";
		In in = new In(filename);
		int n = in.readInt();
		int x, y;
		
		StdDraw.setScale(0, 32768);
		Point[] points = new Point[n];
		for (int i = 0; i < n; ++i) {
			x = in.readInt();
			y = in.readInt();
			points[i] = new Point(x, y);
			points[i].draw();
		}
		
		Arrays.sort(points);
/*		for (int i = 0;i<n;++i){
			System.out.println(points[i]);
		}
		*/
		for (int i1 = 0; i1 < n; ++i1) {
			for (int i2 = i1 + 1; i2 < n; ++i2) {
				for (int i3 = i2 + 1; i3 < n; ++i3) {
					for (int i4 = i3 + 1; i4 < n; ++i4) {
						if (points[i1].slopeTo(points[i2]) == points[i1]
								.slopeTo(points[i3])
								&& (points[i1].slopeTo(points[i2]) == points[i1]
										.slopeTo(points[i4]))) {
							System.out
									.println(points[i1] + " -> " + points[i2]
											+ " -> " + points[i3] + " -> "
											+ points[i4]);
							points[i1].drawTo(points[i4]);
						}
					}
				}
			}
		}
	}
}
