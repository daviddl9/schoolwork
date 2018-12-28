import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.IllegalArgumentException;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * The LabOneB class is the entry point into Lab 1b.
 * See <a href="https://nus-cs2030.github.io/1718-s2/lab1b/index.html">
 *     Lab 1b</a>
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
class LabTwoB {
  /**
   * The main method for Lab 1b. Reads data from either stdin or a file and
   * then run a simulation based on the input data.
   *
   * @param args The first line of input is an integer specifying the number
   *     of servers in the shop. Subsequent lines are doubles with each being the
   *     arrival time of a customer (unordered).
   */
  public static void main(String[] args) {
    //Scanner scanner = createScanner(args);
    Scanner scanner = new Scanner(System.in);
//    if (scanner == null) {
//      return;
//    }
//    try {
      // Read the first line of input as the base seed.
      int seed = scanner.nextInt();
      // Read the second line of input as number of servers in the shop
      int numOfServers = scanner.nextInt();
      // Read the third line of input as the number of self-checkout counters.
      int selfCheckoutCounters = scanner.nextInt();
      // Read the fourth line of input as the max queue length.
      int maxQlength = scanner.nextInt();
      // Read the fifth line of input as the number of customers.
      int numOfCustomers = scanner.nextInt();
      // Read the sixth line of input as the arrival rate.
      double lambda = scanner.nextDouble();
      // Read the seventh line of input as the service rate.
      double mu = scanner.nextDouble();
      // Read the eight line as the resting rate.
      double rho = scanner.nextDouble();
      // Read the ninth line as the probability of a server resting.
      double pRest = scanner.nextDouble();
      // Read the tenth line as the probability of a greedy customer.
      double pGreedy = scanner.nextDouble();
      // Read the eleventh line as the upper time bound for joining the self-
      // checkout queue.
      double tSelf = scanner.nextDouble();

      // Read the tenth line as the
      // Read the next line as the number of Queues.
      //int numQueues = scanner.nextInt();

//      if (numQueues != 1 && numQueues != numOfServers) {
//        throw new IllegalArgumentException("Number of queues invalid");
//      }
      Simulator sim = new Simulator(seed, numOfServers, selfCheckoutCounters,
          maxQlength, numOfCustomers + 1, lambda, mu, rho, pRest, pGreedy,
          tSelf);
      sim.scheduleEvent(new ArrivalEvent(0));
      sim.run();
    // Print the statistics:
    // <avg waiting time> <number of served customer> <number of lost customer>
    System.out.println(Simulator.stats);
//    } catch (IllegalArgumentException e) {
//      System.err.println(e);
//    } catch (InputMismatchException e) {
//      System.err.println("Invalid type of parameters entered.");
//    } finally {
//      scanner.close();
//    }

  }

//  /**
//   * Create and return a scanner. If a command line argument is given,
//   * treat the argument as a file and open a scanner on the file. Else,
//   * create a scanner that reads from standard input.
//   *
//   * @param args The arguments provided for simulation.
//   * @return A scanner or {@code null} if a filename is provided but the file
//   *     cannot be open.
//   */
//  private static Scanner createScanner(String[] args) {
//    Scanner scanner = null;
//
//    try {
//      // Read from stdin if no filename is given, otherwise read from the
//      // given file.
//      if (args.length == 0) {
//        // If there is no argument, read from standard input.
//        scanner = new Scanner(System.in);
//      } else {
//        // Else read from file
//        FileReader fileReader = new FileReader(args[0]);
//        scanner = new Scanner(fileReader);
//      }
//    } catch (FileNotFoundException exception) {
//      System.err.println("Unable to open file " + args[0] + " "
//          + exception);
//    }
//    return scanner;
//  }
}
