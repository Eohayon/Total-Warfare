import java.io.Serializable;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * File Name: SortedLinkedList.java
 * <p>
 * An implementation of LinkedList that adds auto-sorting.
 * <p>
 * This avoids having to brute force random access into a Tree implementation.
 * <p>
 * It allows the program access to the adding and removal speeds of LinkedList along with auto-sorting convenience
 *
 * @author  Ethan Ohayon
 * @version 1.0
 * @since   May 10, 2018
 */

public class SortedLinkedList<E> extends AbstractList<E> implements Serializable {

    private AbstractSequentialList<E> list = new LinkedList<>();

    /**
     * Overrides the add method to sort the list when adding
     *
     * @param position Position in the collection to add to
     * @param e The object to add
     *
     * @author  Ethan Ohayon
     */
    @Override
    public void add(int position, E e) {
        list.add(e);
        Collections.sort(list, null);
    }

    /**
     * Overrides the get() method
     *
     * @param i Element to retrieve
     * @return Object
     * @author  Ethan Ohayon
     */
    @Override
    public E get(int i) {
        return list.get(i);
    }

    /**
     * Overrides the size() method
     *
     * @return Collection size
     * @author  Ethan Ohayon
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Overrides the clear() method
     *
     * @author  Ethan Ohayon
     */
    @Override
    public void clear() {
        list.clear();
    }
}