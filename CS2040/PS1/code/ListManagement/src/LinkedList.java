/**
 * LinkedList
 * Description:  linked list implementation
 * CS2040 2018
 */


/**
 * Class: LinkedList
 * Description: linked list implementation
 * All the elements in the list are integers >= 0.
 */

public class LinkedList implements List {

    protected class Node {
        protected int data;
        protected Node next;
        protected boolean contains;

        public Node()
        {
            data = 0;
            next = null;
            this.contains = true;
        }

        public Node(int data, Node n, boolean contains) {
            //todo
            this.data = data;
            next = n;
            this.contains = contains;
        }

        public Node(int data) {
            this.data = data;
            this.next = null;
            this.contains = true;
        }

        public String toString() {
            return this.data + "";
        }
    };

    Node head;

    public LinkedList() {
        head = null;
    }


    /**
     * Method: add appends a new integer to the end of the list
     * @param key the integer to add to the list
     * @return true if the add succeeds, and false otherwise
     * Outputs an error if the key is < 0
     */
    public final boolean add(int key) {
        Node node;
        if (key < 0) {
            throw new Error("Illegal argument entered");
        }
        // If list is empty
        if (this.head == null) {
            node = new Node(key);
            this.head = node;
        } else {
            for (node = head; node.next != null; node = node.next) {}
            // Now node is the last node of the list
            Node newNode = new Node(key);
            node.next = newNode;
        }
        return true;
    }


    /**
     * Method: search checks whether the key is in the list
     * @param key the integer to search for in the list
     * @return true if key is in the list and false otherwise
     */
    public boolean search(int key) {
        Node node;
        if (head != null) {
            for (node = head; node != null; node = node.next) {
                if (node.data == key) return true;
            }
        }
        return false;
    }

    /**
     * Method: Converts the list into a printable string
     * @return a string
     */
    public String toString() {
        String output = "";
        if (head != null) {
            Node node;
            for (node = head; node != null; node = node.next) {
                output += node;
                if (node.next != null) {
                    output += " ";
                }
            }
        }

        return output;
    }

}
