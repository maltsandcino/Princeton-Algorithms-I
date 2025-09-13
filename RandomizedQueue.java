/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Iterator;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;
    private int arrSize;


    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == queue.length) {
            Item[] new_queue;
            new_queue = (Item[]) new Object[2 * queue.length];
            for (int i = 0; i < size; i++) {
                new_queue[i] = queue[i];
            }
            queue = new_queue;
        }
        queue[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Random rand = new Random();
        int index = rand.nextInt(size);
        Item item = queue[index];
        queue[index] = queue[size - 1];
        queue[size - 1] = null;
        size--;

        if (size > 0 && size == queue.length / 4) {
            Item[] new_queue;
            new_queue = (Item[]) new Object[queue.length / 2];
            for (int i = 0; i < size; i++) {
                new_queue[i] = queue[i];
            }
            queue = new_queue;
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Random rand = new Random();
        int index = rand.nextInt(size);
        Item item = queue[index];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueue.RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] shuffled;
        private int current;

        public RandomizedQueueIterator() {
            shuffled = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                shuffled[i] = queue[i];
            }
            // Shuffle the array to ensure random order
            // This basically iterates down the arr, and
            // swaps the index with a random index.
            for (int i = size - 1; i > 0; i--) {
                int randIndex = new Random().nextInt(i + 1);
                Item temp = shuffled[i];
                shuffled[i] = shuffled[randIndex];
                shuffled[randIndex] = temp;
            }
            current = 0;
        }

        public boolean hasNext() {
            return current < shuffled.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return shuffled[current++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("Running RandomizedQueue tests...");

        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        // Test isEmpty and size
        System.out.println("Initial isEmpty(): " + rq.isEmpty()); // true
        System.out.println("Initial size(): " + rq.size());       // 0

        // Test enqueue
        rq.enqueue(10);
        rq.enqueue(20);
        System.out.println("After enqueue, size(): " + rq.size()); // 2
        System.out.println("After enqueue, isEmpty(): " + rq.isEmpty()); // false

        // Test sample
        int sample = rq.sample();
        System.out.println("Sampled item (should not remove): " + sample);
        System.out.println("Size after sample(): " + rq.size()); // still 2

        // Test dequeue
        int removed = rq.dequeue();
        System.out.println("Dequeued item: " + removed);
        System.out.println("Size after dequeue(): " + rq.size()); // 1

        // Test iterator randomness
        rq.enqueue(30);
        rq.enqueue(40);
        rq.enqueue(50);

        System.out.print("Iterator 1: ");
        for (int item : rq) {
            System.out.print(item + " ");
        }
        System.out.println();

        System.out.print("Iterator 2: ");
        for (int item : rq) {
            System.out.print(item + " ");
        }
        System.out.println();

        // Test exception for dequeue on empty queue
        RandomizedQueue<String> emptyQueue = new RandomizedQueue<>();
        try {
            emptyQueue.dequeue();
        }
        catch (java.util.NoSuchElementException e) {
            System.out.println("Caught expected exception on dequeue from empty queue.");
        }

        // Test exception for enqueue null
        try {
            rq.enqueue(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception on enqueue(null).");
        }

        // Test iterator exception
        Iterator<Integer> it = rq.iterator();
        while (it.hasNext()) {
            it.next();
        }
        try {
            it.next();
        }
        catch (java.util.NoSuchElementException e) {
            System.out.println("Caught expected exception on iterator.next() with no elements.");
        }

        System.out.println("All tests completed.");

    }

}
