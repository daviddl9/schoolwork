public class MarkNotFoundList extends LinkedList {
    /**
     * Method: search checks whether the key is in the list
     * @param key the integer to search for in the list
     * @return true if key is in the list and false otherwise
     */
    public boolean search(int key) {
        Node node;
        if (head != null) {
            if (head.data == key) return true;
            for (node = head; node.next != null; node = node.next) {
                if (node.next.data == key) {
                    Node temp = node.next;
                    node.next = node.next.next;
                    Node oldHead = this.head;
                    this.head = temp;
                    temp.next = oldHead;
                    return true;
                }
                if (node.next.data == -key) {
                    Node temp = node.next;
                    node.next = node.next.next;
                    Node oldHead = this.head;
                    this.head = temp;
                    temp.next = oldHead;
                    return false;

                }
            }

        }
        Node oldHead = this.head;

        node = new Node(-1 * key, oldHead, false);
        this.head = node;
        return false;
    }
}
