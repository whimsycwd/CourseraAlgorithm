import java.util.Iterator;


/**
 * date   2013/9/6 15:08
 * @author whimsy
 * 
 */



public class Deque<Item> implements Iterable<Item> {

	private Node<Item> head = null;
	private Node<Item> tail = null;
	private int totNumber = 0;

	// construct an empty deque
	public Deque() {
		// sentry
		head = new Node<Item>(null);
		tail = new Node<Item>(null);
		head.setNext(tail);
		tail.setPrev(head);
	}

	public static void main(String[] args) {
		Deque<Integer> queue = new Deque<Integer>();
		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		for (int i = 0; i < arr.length; ++i) {
			if (i % 2 == 1) {
				queue.addFirst(arr[i]);
			} else {
				queue.addLast(arr[i]);
			}
		}
		for (Integer i : queue) {
			System.out.print(i + " ");
		}
	}

	// is the deque empty?
	public boolean isEmpty() {
		return totNumber == 0;
	}

	// return the number of items on the deque
	public int size() {
		return totNumber;
	}

	// insert the item at the front
	public void addFirst(Item item) {
		if (item == null)
			throw new java.lang.NullPointerException();
		Node<Item> cur = new Node<Item>(item);
		Node<Item> tmp = head.getNext();
		head.setNext(cur);
		cur.setPrev(head);
		cur.setNext(tmp);
		tmp.setPrev(cur);
		++totNumber;
	}

	// insert the item at the end
	public void addLast(Item item) {
		if (item == null)
			throw new java.lang.NullPointerException();
		Node<Item> cur = new Node<Item>(item);
		Node<Item> tmp = tail.getPrev();
		tmp.setNext(cur);
		cur.setPrev(tmp);
		cur.setNext(tail);
		tail.setPrev(cur);
		++totNumber;
	}

	// delete and return the item at the front
	public Item removeFirst() {
		if (totNumber == 0)
			throw new java.util.NoSuchElementException();

		Item ret = head.getNext().getSelf();
		Node<Item> tmp = head.getNext().getNext();
		head.setNext(tmp);
		tmp.setPrev(head);
		--totNumber;
		return ret;
	}

	// delete and return the item at the end
	public Item removeLast() {
		if (totNumber == 0)
			throw new java.util.NoSuchElementException();

		Item ret = tail.getPrev().getSelf();
		Node<Item> tmp = tail.getPrev().getPrev();
		tmp.setNext(tail);
		tail.setPrev(tmp);
		--totNumber;
		return ret;
	}

	// return an iterator over items in order from front to end

	public Iterator<Item> iterator() {
		return new ItemIterator();
	}

	private class ItemIterator implements Iterator<Item> {
		private Node<Item> cur;

		public ItemIterator() {

			cur = head.getNext();
		}

		@Override
		public boolean hasNext() {
			return cur != tail;
		}

		@Override
		public Item next() {
			if (hasNext() == false)
				throw new java.util.NoSuchElementException();
			Item ret = cur.getSelf();
			cur = cur.getNext();
			return ret;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}

	private class Node<E> {
		private Node<E> next;
		private E self;
		private Node<E> prev;

		public Node(E cur) {
			prev = null;
			self = cur;
			next = null;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}

		public Node<E> getPrev() {
			return prev;
		}

		public void setPrev(Node<E> prev) {
			this.prev = prev;
		}

		public E getSelf() {
			return self;
		}

		public void setSelf(E self) {
			this.self = self;
		}

	}
}
