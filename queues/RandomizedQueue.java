import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int count, capacity;

    public RandomizedQueue() {   // construct an empty randomized queue
        capacity = 1;             
        items = (Item[])(new Object[capacity]);
        count = 0;
    }

    public boolean isEmpty() {                // is the randomized queue empty?
        return count == 0;
    }

    public int size() {                       // return the number of items on the randomized queue
        return count;
    }

    public void enqueue(Item item) { 
        if (item == null) throw new IllegalArgumentException("Item to add is null");
        items[count++] = item;

        if (count == capacity) {
            resizeArray(capacity * 2);
        }
    }

    public Item dequeue() {                   // remove and return a random item
        if (count == 0) throw new NoSuchElementException("The queue is empty");

        swapRandom();

        Item item = items[--count];
        items[count] = null;
        
        if (count == (capacity / 4)) {
            resizeArray(capacity / 2);
        }

        return item;
    }

    public Item sample() {                    // return a random item (but do not remove it)
        if (count == 0) throw new NoSuchElementException("The queue is empty");
        
        return items[StdRandom.uniform(count)];
    }

    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }

    private void resizeArray(int newSize) {
        capacity = newSize;
        Item[] newArray = (Item[])(new Object[newSize]);

        for(int i = 0; i < count; i++) {
            newArray[i] = items[i];
        }

        items = newArray;
    }

    private void swapRandom() {
        int rdnIndex = StdRandom.uniform(count);

        Item temp = items[rdnIndex];
        items[rdnIndex] = items[count-1];
        items[count - 1] = temp;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int[] sequence;
        int traversalIndex = 0;

        public RandomizedQueueIterator() {
            sequence = new int[count];

            for(int i = 1; i < count; i++) {
                sequence[i] = i;

                int rdnIndex = StdRandom.uniform(i+1);

                int temp = sequence[rdnIndex];
                sequence[rdnIndex] = sequence[i];
                sequence[i] = temp;
            }
        }

        public boolean hasNext() {
            return traversalIndex < count;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("The iterator is empty");
            return items[sequence[traversalIndex++]];
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from rdn queue inside iterator.");
        }
    }
}