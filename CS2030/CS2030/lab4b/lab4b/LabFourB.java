import cs2030.simulator.Simulator;
import cs2030.simulator.Event;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The LabOFourB class is the entry point into Lab 4b.
 *
 * @author atharvjoshi
 * @author weitsang
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */
class LabFourB {
  /**
   * The main method for Lab 4b. Reads data from file and
   * then run a simulation based on the input data.
   *
   * @param args two arguments, first an integer specifying number of servers
   *     in the shop. Second a file containing a sequence of double values, each
   *     being the arrival time of a customer (in any order).
   */
  public static void main(String[] args) {
    Optional<Scanner> scanner = createScanner(args);
    scanner.ifPresentOrElse(sc -> {
      int numOfServers = sc.nextInt();
      List<Event> eventList = sc.tokens().mapToDouble(Double::parseDouble)
          .mapToObj((double time) -> new Event(time, s -> s.simulateArrival(time)))
          .collect(Collectors.toList());
          sc.close();
          Simulator sim = new Simulator(numOfServers, eventList);
          System.out.println(sim.run());
    }, () -> {

      }
    );
  }

  /**
   * Create and return a scanner. If a command line argument is given,
   * treat the argument as a file and open a scanner on the file. Else,
   * create a scanner that reads from standard input.
   *
   * @param args The arguments provided for simulation.
   * @return A scanner or {@code null} if a filename is provided but the file
   *     cannot be open.
   */
  private static Optional<Scanner> createScanner(String[] args) {
    Optional<Scanner> sc = Optional.empty();

    try {
      // Read from stdin if no filename is given, otherwise read from the
      // given file.
      if (args.length == 0) {
        // If there is no argument, read from standard input.
        Scanner scanner = new Scanner(System.in);
        sc = Optional.of(scanner);
      } else {
        // Else read from file
        FileReader fileReader = new FileReader(args[0]);
        Scanner scanner = new Scanner(fileReader);
        sc = Optional.of(scanner);
      }
    } catch (FileNotFoundException exception) {
      System.err.println("Unable to open file " + args[0] + " "
          + exception);
    }
    return sc;
  }
}
