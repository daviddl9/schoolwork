import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Lab 0: Solver for Maximum Disc Coverage.
 * Given a set of points,  find the maximum number of points
 * contained in the unit disc.  The input is read from the standard
 * input: the first line indicates the number of points n.
 * The next n lines contains the x and y coodinates of the points,
 * separated by a space, one point per line.
 *
 * @author Ooi Wei Tsang
 * @author D David Livingston (students: put your name here)
 * @version CS2030 AY17/18 Sem 2 Lab 0
 */

class LabZero {
  /**
  * Read the list of points from standard input with a
  * scanner.
  *
  * @return the list of points in an array.
  */

  public static void main(String[] args) {
    Point[] points = readPoints();
    int max = MaxDiscCover.solve(points);
    System.out.println(max);
  }
  public static Point[] readPoints() {
    // First line of input indicates the number of points.
    Point[] points = new Point[0];
    Scanner s = new Scanner(System.in);
    try {
      int numOfPoint = s.nextInt();
      // Next lines contains the coordinate
      points = new Point[numOfPoint];
      for (int i = 0; i < numOfPoint; i++) {
        double x = s.nextDouble();
        double y = s.nextDouble();
        points[i] = new Point(x, y);
      }
    } catch (InputMismatchException e) {
      System.err.println("Illegal argument type entered.");
    } finally {
      //s.close();
      return points;
    }
  }

}
