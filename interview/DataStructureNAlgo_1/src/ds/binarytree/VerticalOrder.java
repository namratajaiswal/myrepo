package ds.binarytree;

/*Given a binary tree, print it vertically. The following example illustrates vertical order traversal.

   1
/    \ 
2      3
/ \   /   \
4   5  6   7
    /  \ 
   8   9 
    
   
The output of print this tree vertically will be:
4
2
1 5 6
3 8
7
9
*/

//Java program for printing vertical order of a given binary tree 
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

public class VerticalOrder {
	static class Node {
		int key;
		Node left;
		Node right;

		Node(int data) {
			key = data;
			left = null;
			right = null;
		}
	}

	// Utility function to store vertical order in map 'm' 'hd' is horizontal
	// distance of current node from root. 'hd' is initially passed as 0
	static void getVerticalOrder(Node root, int hd, TreeMap<Integer, Vector<Integer>> m) {

		if (root == null)
			return;

		Vector<Integer> value = m.get(hd);

		if (value == null) {
			value = new Vector<>();
			value.add(root.key);
		} else
			value.add(root.key);

		m.put(hd, value);

		getVerticalOrder(root.left, hd - 1, m);

		getVerticalOrder(root.right, hd + 1, m);
	}

	static void printVerticalOrder(Node root) {
		// Create a map and store vertical oder in map using function getVerticalOrder()
		TreeMap<Integer, Vector<Integer>> m = new TreeMap<>();
		int hd = 0;
		getVerticalOrder(root, hd, m);

		// Traverse the map and print nodes at every horigontal distance (hd)
		for (Entry<Integer, Vector<Integer>> entry : m.entrySet()) {
			System.out.println(entry.getValue());
		}
	}

	public static void main(String[] args) {

		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.right.left = new Node(6);
		root.right.right = new Node(7);
		root.right.left.right = new Node(8);
		root.right.right.right = new Node(9);
		System.out.println("Vertical Order traversal is");
		printVerticalOrder(root);
	}
}