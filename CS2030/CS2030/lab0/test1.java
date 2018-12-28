import java.lang.IllegalArgumentException;
import java.util.Scanner;
import java.util.*;

class test1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		double x = Double.NaN;
		try {
			System.out.println("Enter x: ");
			x = sc.nextDouble();
		} catch (InputMismatchException c) {
			System.err.println("Illegal Arugment entered, please re-enter x: ");
			x = sc.nextDouble();
		} finally {
			sc.close();
			System.out.println("X entered: " + x);
		}
	}
}
