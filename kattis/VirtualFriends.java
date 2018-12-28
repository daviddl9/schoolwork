import java.util.*;


// Algorithm: Store a mapping of String to index in array
// Each person is represented by an int, and then with the int array, we
// can do disjoint set addition
// if new person added, add entry and add the number of friends he has
// if no new person added
public class VirtualFriends {
  static int size[] = new int[200005];
  static int parent[] = new int[200005];
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int numOfTests = Integer.parseInt(sc.nextLine());
    int numOfFriendships;
    int firstNameIndex;
    int secondNameIndex;
    int count;
    int nameIndex;
    String[] people;
    Hashtable<String, Integer> map = new Hashtable<>();
    int[] set;
    for (int i = 0; i < numOfTests; i++) {
      count = 0; // The number of friends, per test
      nameIndex = 0; // The index of each person, per test
      numOfFriendships = Integer.parseInt(sc.nextLine());
      // size = new int[numOfFriendships];
      // parent = new int[numOfFriendships];

      for (int j = 0; j < size.length; j++) {
        size[j] = 1;
        parent[j] = j;
      }

      for (int j = 0; j < numOfFriendships; j++) {
        people = sc.nextLine().split(" ");
        String first = people[0];
        String second = people[1];


        if (!map.containsKey(first)) {
          map.put(first, nameIndex);
          firstNameIndex = nameIndex;
          nameIndex++;
        } else {
          firstNameIndex = map.get(first);
        }

        if (!map.containsKey(second)) {
          map.put(second, nameIndex);
          secondNameIndex = nameIndex;
          nameIndex++;
        } else {
          secondNameIndex = map.get(second);
        }

        union(firstNameIndex, secondNameIndex);
      }


    }
  }

  public static void union(int p, int q) {
    while (parent[p] != p) p = parent[p];
    while (parent[q] != q) q = parent[q];

    if (p == q) {
      System.out.println(size[p]);
      return;
    }

    if (size[p] > size[q]) {
      parent[q] = p;
      size[p] = size[p] + size[q];
      System.out.println(size[p]);
    } else {
      parent[p] = q;
      size[q] = size[p] + size[q];
      System.out.println(size[q]);
    }
  }

  public static int findRoot(int p) {
    int root = p;
    int temp;
    while(parent[root] != root) root = parent[root];
    while (parent[p] != p) {
      temp = parent[p];
      parent[p] = root;
      p = temp;
    }
    return root;
  }

}
