/**
 * MoveToFrontArrayList
 * Description: MoveToFrontArrayList is a subclass of the arraylist. 
 * CS2040
 * 
 * You need to create a constructor and implement search.
 */

/**
 * 
 */
public class MoveToFrontArrayList extends ArrayList {

    MoveToFrontArrayList(int length){
        super(length);
    }

    // Algorithm: Linearly search through arraylist
    // Find index 
    // Remove index
    // Add to front 
    public boolean search(int key){
    	int index = 0;
    	int num;
    	int i;
    	while (index < super.m_length && super.m_list[index] >= 0) {
    		if (super.m_list[index] == key) {
    			for (i=index; i>=1; i--) {
    				super.m_list[i] = super.m_list[i-1];
					}
					super.m_list[0] = key;
    			return true;
    		}
				index++;
			}
    	return false;
    }

//    @Override
//		public String toString() {
//    	String x = super.toString();
//    	x = x.replaceAll(",","");
//    	x = x.replace("[", "");
//    	x = x.replace("]", "");
//			return x;
//		}

    
}
