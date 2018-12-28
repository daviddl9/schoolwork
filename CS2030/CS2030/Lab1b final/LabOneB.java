package cs2030.simulator;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class LabOneB {
  /**
   * This function creates a scanner object and creates events based on the
   * users input.
   *
   * @param args refers to command line arguments passed in during execution
   *             of programme.
   */
  public static void main(String[] args) {
    Scanner s = createScanner(args);
    if (s == null) {
      return;
    }
    //Read the first line of input as the base seed.
    int seed = s.nextInt();
    //Read the second line of input as number of servers.
    int numOfServers = s.nextInt();
    //Read the third line of input as the number of customers.
    int numOfCustomers = s.nextInt();
    //Read the fourth line of input as lambda
    double lambda = s.nextDouble();
    //Read the fifth line of input as mu
    double mu = s.nextDouble();
    s.close();
    Simulator sim = new Simulator(numOfServers);
    // The input file consists of a sequence of arrival timestamp
    // (not necessary in order).
    sim.scheduleEventInSimulator(new Event(0, EventType.CUSTOMER_ARRIVE));
    // Then run the simulator
    sim.runSimulator();
    System.out.println(Shop.stats);
  }

  /**
   * Create and return a scanner.  If a command line arguement is given,
   * treat the argument as a file, and open a scanner on the file.  Else,
   * open a scanner that reads from standard input.
   *
   * @return a scanner or `null` if 0.5a filename is given but cannot be open.
   */
  static Scanner createScanner(String[] args) {
    Scanner s = null;
    try {
      // Read from stdin if no filename is given, otherwise0
      // read from the given file.
      if (args.length == 0) {
        s = new Scanner(System.in);
      } else {
        FileReader f = new FileReader(args[0]);
        s = new Scanner(f);
      }
    } catch (FileNotFoundException ex) {
      System.err.println("Unable to open file " + args[0] + " " + ex + "\n");
    } finally {
      return s;
    }
  }
}
