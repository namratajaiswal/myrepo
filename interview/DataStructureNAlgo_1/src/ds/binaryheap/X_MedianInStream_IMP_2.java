package ds.binaryheap;

import java.util.Arrays;
import java.util.Scanner;

/*
 * 
Input: 6 12 4 5 3 8 7
Output:
12.0
8.0
5.0
4.5
5.0
6.0
 */
public class X_MedianInStream_IMP_2 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		MinHeap minHeap = new MinHeap();
		MaxHeap maxHeap = new MaxHeap();
		float median = 0;

		// TODO important logic
		for (int a_i = 0; a_i < n; a_i++) {

			// TODO Logic to add the running element
			int element = in.nextInt();
			if (minHeap.size == maxHeap.size) {
				if (element <= median) {
					maxHeap.addItem(element);
					median = maxHeap.peek();
				} else {
					// TODO Logic is incorrect 1: remove element from min heap and add it to max
					// heap and add new elemnt in min heap
					minHeap.addItem(element);
					median = minHeap.peek();
				}
			} else if (maxHeap.size > minHeap.size) {
				if (element <= median) {
					minHeap.addItem(maxHeap.poll());
					maxHeap.addItem(element);
				} else {
					minHeap.addItem(element);
				}
				median = (float) (maxHeap.peek() + minHeap.peek()) / 2;
			} else {
				if (element <= median) {
					maxHeap.addItem(element);
				} else {
					maxHeap.addItem(minHeap.poll());
					minHeap.addItem(element);
				}
				median = (float) (maxHeap.peek() + minHeap.peek()) / 2;
			}
			System.out.println(median);
		}
	}

	static abstract class Heap {
		int[] items = new int[10];
		int capacity = 10;
		int size = 0;

		boolean hasLeftChild(int parent) {
			int leftChildIndex = getLeftChildIndex(parent);
			if (leftChildIndex > size - 1) {
				return false;
			}
			return true;
		}

		boolean hasRightChild(int parent) {
			int rightChildIndex = getRightChildIndex(parent);
			if (rightChildIndex > size - 1) {
				return false;
			}
			return true;
		}

		boolean hasParent(int index) {
			if (index > 0) {
				return true;
			} else {
				return false;
			}
		}

		int getLeftChildIndex(int parent) {
			int leftChildIndex = parent * 2 + 1;
			return leftChildIndex;
		}

		int getRightChildIndex(int parent) {
			int rightChildIndex = parent * 2 + 2;
			return rightChildIndex;
		}

		int getParentIndex(int index) {
			int parent = (index - 1) / 2;
			return parent;
		}

		int getLeftChild(int parent) {
			return items[getLeftChildIndex(parent)];
		}

		int getRightChild(int parent) {
			return items[getRightChildIndex(parent)];
		}

		int getParent(int index) {
			return items[getParentIndex(index)];
		}

		// TODO Copying the size of the array
		void ensureCapacity() {
			if (size == capacity) {
				items = Arrays.copyOf(items, capacity * 2);
				capacity = capacity * 2;
			}
		}

		// TODO ensure capacity and heapifyUp, items are added at the end of the array
		void addItem(int item) {
			ensureCapacity();
			items[size] = item;
			size = size + 1;
			heapifyUp();
		}

		// TODO heapifyDown while polling
		int poll() {
			int topItem = items[0];
			items[0] = items[size - 1];
			size = size - 1;
			heapifyDown();
			return topItem;
		}

		int peek() {
			return items[0];
		}

		abstract void heapifyDown();

		abstract void heapifyUp();

		void swap(int indexA, int indexB) {
			int temp = items[indexA];
			items[indexA] = items[indexB];
			items[indexB] = temp;
		}
	}

	static class MinHeap extends Heap {
		void heapifyUp() {
			int index = size - 1;
			while (hasParent(index)) {
				if (getParent(index) <= items[index]) {
					break;
				} else {
					swap(getParentIndex(index), index);
					index = getParentIndex(index);
				}
			}
		}

		void heapifyDown() {
			int parent = 0;
			while (hasLeftChild(parent)) {
				int smallerIndex = getLeftChildIndex(parent);
				if (hasRightChild(parent) && getRightChild(parent) < getLeftChild(parent)) {
					smallerIndex = getRightChildIndex(parent);
				}
				if (items[parent] < items[smallerIndex]) {
					break;
				} else {
					swap(parent, smallerIndex);
				}
				parent = smallerIndex;
			}
		}
	}

	static class MaxHeap extends Heap {
		void heapifyUp() {
			int index = size - 1;
			while (hasParent(index)) {
				if (getParent(index) >= items[index]) {
					break;
				} else {
					swap(getParentIndex(index), index);
					index = getParentIndex(index);
				}
			}
		}

		void heapifyDown() {
			int parent = 0;
			while (hasLeftChild(parent)) {
				int largerIndex = getLeftChildIndex(parent);
				if (hasRightChild(parent) && getRightChild(parent) > getLeftChild(parent)) {
					largerIndex = getRightChildIndex(parent);
				}
				if (items[parent] > items[largerIndex]) {
					break;
				} else {
					swap(parent, largerIndex);
				}
				parent = largerIndex;
			}
		}
	}
}