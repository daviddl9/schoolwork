import java.lang.*;
import java.io.*;
import java.util.*;

/**
 * Solver for Maximum Disc Coverage.
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
public class MaxDiscCover {


  /**
   * Find the unit disc that contains the most points,
   * and return the number of points.
   *
   */
  public static int solve(Point[] points) {
    int max = 0; //initialise max number of points covered to 0
    //Loop through all possible circles with points p and q
    for (Point p: points) {
      for (Point q: points) {
        Circle c = new Circle(p, q, 1);
        //only start checking for points encapsulated by the circle if the circle is valid.
        if (c.isValid()) {
          int numPoints = 0; //initialise number of points covered by unit circle to 0
            //then loop through all points to check if they lie in the circle
            for (Point r: points) {
              if (c.contains(r)) numPoints++; //increment number of points covered by unit circle if the given point lies in circle.
            }
            if (numPoints > max) max = numPoints; //update max
        }
      }
    }
    // Loop through all points
    //  loop through all points
    //    use 2 points to construct a Circle
    //      loop through all points
    //        check how many points are inside the circle
    //          record down max coverage so far


    // Hint: if the code gets too verbose, start think about
    // how you can maintain an abstraction barrier and ecapsulate
    // logics inside each respective class.
    return max;
  }
}
