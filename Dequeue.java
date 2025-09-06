/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

/*
Because we have some pretty strict memory and run-time constrictions, I won't be
Implementing this with an array. We are actually going to use Nodes to point to
items in the list.

This means, that I won't have to actually facilitate a Dynamic Array Resizing (which could
add overhead to not only space but time).

This also will likely require fewer variables to keep track of.
 */

import java.util.Iterator;

public class Dequeue<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private Node first;
    private Node last;
    private int size;

    public Dequeue() {
        first = null;
        last = null;
        size = 0;
    }

    public void addFirst(Item item) {
        // Make a new node. This holds the item, points to null, and points to first
        // If the list is empty, last is also this node.
        // Otherwise, we take the current first node, and set its pointer to point back to this node
        // We set the first node to actually point to this new node instead.
        // Increment size
        if (item == null) throw new IllegalArgumentException();

        Node newNode = new Node();
        newNode.item = item;
        newNode.next = first;
        newNode.prev = null;

        if (isEmpty()) {
            last = newNode;
        }
        else {
            first.prev = newNode;
        }

        first = newNode;
        size++;

    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        // Opposite logic as above
        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = last;
        newNode.next = null;

        if (isEmpty()) {
            first = newNode;
        }
        else {
            last.next = newNode;
        }

        last = newNode;
        size++;
    }

    public Item removeFirst() {
        // Check
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        size--;

        // Second Check
        if (isEmpty()) {
            last = null;
        }
        else {
            first.prev = null;
        }
        if (first != null) first.prev = null;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        size--;

        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }

        if (last != null) last.next = null;

        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {
        private Node cur = first;

        public boolean hasNext() {
            return cur != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        System.out.println("Creating new Dequeue...");
        Dequeue<String> dequeue = new Dequeue<>();

        System.out.println("Is Dequeue empty? " + dequeue.isEmpty());
        System.out.println("Size: " + dequeue.size());

        System.out.println("\nAdding items to front and back...");
        dequeue.addFirst("A");
        dequeue.addLast("B");
        dequeue.addFirst("C");
        dequeue.addLast("D");

        System.out.println("Size after additions: " + dequeue.size());
        System.out.println("Is Dequeue empty? " + dequeue.isEmpty());

        System.out.println("\nIterating over Dequeue:");
        for (String s : dequeue) {
            System.out.println(s);
        }

        System.out.println("\nRemoving items from front and back...");
        System.out.println("Removed from front: " + dequeue.removeFirst());
        System.out.println("Removed from back: " + dequeue.removeLast());

        System.out.println("Size after removals: " + dequeue.size());

        System.out.println("\nTesting corner cases:");

        try {
            System.out.println("Attempting to add null to front...");
            dequeue.addFirst(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException");
        }

        try {
            System.out.println("Attempting to add null to back...");
            dequeue.addLast(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException");
        }

        System.out.println("Clearing Dequeue...");
        dequeue.removeFirst();
        dequeue.removeFirst(); // should now be empty

        try {
            System.out.println("Attempting to remove from empty Dequeue (front)...");
            dequeue.removeFirst();
        }
        catch (java.util.NoSuchElementException e) {
            System.out.println("Caught expected NoSuchElementException");
        }

        try {
            System.out.println("Attempting to remove from empty Dequeue (back)...");
            dequeue.removeLast();
        }
        catch (java.util.NoSuchElementException e) {
            System.out.println("Caught expected NoSuchElementException");
        }

        System.out.println("\nTesting iterator corner cases:");
        Iterator<String> it = dequeue.iterator();
        try {
            System.out.println("Calling next() on empty iterator...");
            it.next();
        }
        catch (java.util.NoSuchElementException e) {
            System.out.println("Caught expected NoSuchElementException");
        }

        try {
            System.out.println("Calling remove() on iterator...");
            it.remove();
        }
        catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        }
    }
}
