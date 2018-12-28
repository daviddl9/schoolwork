import java.util.*;

public class OddManOut {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int C;
		int res = 0;
		for (int i = 0; i < N; i++) {
			C = sc.nextInt();
			for (int j = 0; j < C; j++) {
				if (j == 0) res = sc.nextInt();
				else res ^= sc.nextInt(); 
			}
			System.out.println("Case #" + (i+1) + ": " + res); 
		}
	}
}