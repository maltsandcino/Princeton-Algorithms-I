/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        // Getting the number of runs, making Queue
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        // Reading in strings
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        // Deque k number oftimes
        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
        // Hi mom
    }
}
