import java.util.Arrays;
import java.util.List;

//public class MyFastList extends ArrayList {
//    boolean isSorted;
//    /**
//     * Constructor
//     *
//     * @param length the length of the list
//     *               Creates a new list of specified length.
//     *               Initializes array m_list, setting every slot to -1.
//     */
//    public MyFastList(int length) {
//        super(length);
//        this.isSorted = false;
//    }
//
//    public MyFastList() {
//        super(ListSimulator.LISTSIZE);
//        this.isSorted = false;
//    }
//
//    public boolean search(int key) {
//        // First, quicksort the input
//        // Then, search for the key using binary search
//        if (!this.isSorted) {
//            Arrays.sort(super.m_list);
//            this.isSorted = true;
//        }
//        int index = Arrays.binarySearch(super.m_list, key);
//        if (index >= 0) return true;
//        else return false;
//
//    }
//
//}

public class MyFastList extends ArrayList {
  public MyFastList(int length) {
    super(length);
  }

  public boolean search(int key) {
    int index = 0;
    while (index < super.m_length) {
      if (super.m_list[index] == key && index != 0) {
        int temp = super.m_list[index-1];
        super.m_list[index-1] = super.m_list[index];
        super.m_list[index] = temp;
        return true;
      } else if (super.m_list[index] == key) {
        return true;
      }
      index++;
    }
    return false;
  }

}

