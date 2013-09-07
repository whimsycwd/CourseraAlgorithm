import java.util.Iterator;

/**
 * date 2013/9/6 15:08
 * 
 * @author whimsy
 * 
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] arr;
	private int totNumber;

	// construct an empty randomized queue
	public RandomizedQueue() {
		arr = (Item[]) new Object[2];
	}

	/*
	 * public static void main(String[] args) { RandomizedQueue<Integer> queue =
	 * new RandomizedQueue<Integer>(); for (int i = 0;i<10;++i){
	 * queue.enqueue(i); } for (Integer item : queue){ System.out.print(item); }
	 * System.out.println(); for (Integer item : queue){ System.out.print(item);
	 * } System.out.println(); for (Integer item : queue){
	 * System.out.print(item); } System.out.println(); for (Integer item :
	 * queue){ System.out.print(item); } System.out.println();
	 * 
	 * for (int i = 0;i<10;++i){ System.out.print(queue.dequeue()+" "); } for
	 * (Integer item : queue){ System.out.print(item); } }
	 */
	// is the queue empty?
	public boolean isEmpty() {
		return totNumber == 0;
	}

	// return the number of items on the queue
	public int size() {
		return totNumber;

	}

	// add the item
	public void enqueue(Item item) {
		if (item == null)
			throw new java.lang.NullPointerException();
		arr[totNumber++] = item;
		if (totNumber == arr.length) {
			resize(2 * arr.length);
		}
	}

	// delete and return a random item
	public Item dequeue() {
		if (totNumber == 0)
			throw new java.util.NoSuchElementException();
		swap(totNumber - 1, StdRandom.uniform(totNumber));
		Item ret = arr[--totNumber];
		/*
		 * if don't use 'arr[totNumber]=null' then some reference will be kept by this class,and Memory won't be collect by
		 * JVM garbage collection.... it cause the failure.
		 * \
		 * Test 6: Checking for loitering by adding 100 strings and deleting
		 * them: loitering detected on 93 of 100 deletions 
		 * ==> FAILED
		 */
		arr[totNumber] = null;
	
		if (totNumber > 8 && totNumber <= arr.length / 4) {
			resize(arr.length / 2);
		}
		return ret;
	}

	private void swap(int l, int r) {
		Item tmp = arr[l];
		arr[l] = arr[r];
		arr[r] = tmp;
	}

	// return (but do not delete) a random item
	public Item sample() {
		if (totNumber == 0)
			throw new java.util.NoSuchElementException();
		swap(totNumber - 1, StdRandom.uniform(totNumber));
		return arr[totNumber - 1];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new ItemIterator();
	}

	private void resize(int capacity) {
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < totNumber; ++i) {
			temp[i] = arr[i];
		}
		arr = temp;
	}

	private class ItemIterator implements Iterator<Item> {

		private int cur = 0;
		private Item[] backup;

		public ItemIterator() {
			cur = 0;
			backup = (Item[]) new Object[totNumber];
			for (int i = 0; i < totNumber; ++i) {
				backup[i] = arr[i];
			}
			StdRandom.shuffle(backup);
		}

		@Override
		public boolean hasNext() {
			return cur != totNumber;
		}

		@Override
		public Item next() {
			if (hasNext() == false)
				throw new java.util.NoSuchElementException();
			return backup[cur++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}
}