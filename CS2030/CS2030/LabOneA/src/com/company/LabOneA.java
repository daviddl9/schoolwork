package com.company;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class LabOneA {
  /**
   * This function creates a scanner and creates events based on the users input.
   * @param args refers to command line arguments passed in during execution of programme.
   */
  public static void main(String[] args) {
    Simulator sim = new Simulator();
    Scanner s = createScanner(args);
    if (s == null) {
      return;
    }

    // The input file consists of a sequence of arrival timestamp
    // (not necessary in order).
    while (s.hasNextDouble()) {
      Event e = new Event(s.nextDouble(), Event.CUSTOMER_ARRIVE);
      boolean ok = sim.scheduleEventInSimulator(e);
      if (!ok) {
        System.err.printf("warning: too many events.  Skipping the rest.");
        s.close();
        break;
      }
    }
    s.close();

    // Then run the simulator
    sim.runSimulator();

    // Print stats as three numbers:
    // <avg waiting time> <number of served customer> <number of lost customer>
    System.out.printf("%.3f %d %d\n", sim.totalWaitingTime / sim.totalNumOfServedCustomer,
        sim.totalNumOfServedCustomer, sim.totalNumOfLostCustomer);
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
      // Read from stdin if no filename is given, otherwise
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
