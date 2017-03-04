package collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ArrayCollection<T> implements Collection<T> {

    private T[] m = (T[])new Object[2];

    private int size = 0;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (m[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public Object[] toArray() {
        final T[] newM = (T[])new Object[this.size()];
        System.arraycopy(m, 0, newM, 0, this.size());
        return newM;
    }

    @Override
    /*This method may prove to be too difficult.
    he test is not covered.*/
    public <T1> T1[] toArray(T1[] a) {
        if (a.length > this.size()) {
            System.arraycopy(m,0, a, 0, size());
            return a;
        } else {
            return (T1[])this.toArray();
        }
    }

    @Override
    public boolean add(T t) {
        if (size == m.length) {
            m = Arrays.copyOf(m, m.length * 2);
        }
        m[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for(int i = 0; i < size(); i++) {
            if (m[i].equals(o)) {
                /*Это что-то вроде оптимизации.
                Что бы не запускать ресурсоемкый метод arraycopy*/
               if (i == this.size() - 1) {
                    size--;
                    return true;
                }
                System.arraycopy(m, i + 1, m, i , this.size() - i -1);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll( final Collection<?> c) {
        for (final Object cell : c) {
            if (!this.contains(cell)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T cell : c) {
            add(cell);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object cell : c) {
            if (contains(cell)) remove(cell);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        for (int i = 0; i < size(); i++) {
           if (!c.contains(m[i])) {
              /*Счетчик цикла необходимо уменьшать при каждом удалении.
              Потому что удаление элемента из коллекции не просто уменшает size,
               а еще и сдвигает элементы в массиве.*/
               remove(m[i--]);
           }
        }
        return true;
    }

    @Override
    public void clear() {
        m = (T[])new Object[1];
        size = 0;
    }

    private class ElementsIterator implements Iterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return ArrayCollection.this.size() > index;
        }


        @Override
        public T next() {
            return ArrayCollection.this.m[index++];
        }
    }
}
