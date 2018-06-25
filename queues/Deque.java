import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int count;
    private Node<Item> first, last;
    
    public Deque() {
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item to add is null");
        Node<Item> newNode = new Node<Item>();
        newNode.value = item;
        
        if(count == 0) {
            first = newNode;
            last = newNode;
        } else {
            newNode.next = first;            
            first.prev = newNode;
            first = newNode;
        }

        count++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item to add is null");
        Node<Item> newNode = new Node<Item>();
        newNode.value = item;
        
        if(count == 0) {
            first = newNode;
            last = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;         
        }
        
        count++;
    }

    public Item removeFirst() {
        if (count == 0) throw new NoSuchElementException("The deque is empty");

        Item item = first.value;
        
        if(last == first) last = null;
        
        first = first.next;
        if(first != null) {
            first.prev = null;
        }

        count--;

        return item;

    }

    public Item removeLast() {
        if (count == 0) throw new NoSuchElementException("The deque is empty");

        Item item = last.value;
        
        if(last == first) first = null;
        
        last = last.prev;
        if(last != null) {
            last.next = null;
        }

        count--;

        return item;
    }

    public Iterator<Item> iterator() {
        return new NodeIterator();
    }

    private class NodeIterator implements Iterator<Item> {
        Node<Item> cursor;

        public NodeIterator() {
            cursor = first;
        }

        public boolean hasNext() {
            return cursor != null;
        }

        public Item next() {
            if (cursor == null) throw new NoSuchElementException("The iterator is empty");
            Item item = cursor.value;
            cursor = cursor.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from deque inside iterator.");
        }
    }

    private class Node<Item> {
        private Item value;
        private Node<Item> next;
        private Node<Item> prev;
    }
 }