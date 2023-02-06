package library;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;


public class Record<T> implements List<T> {
    private final ArrayList<T> record;
    public Record() {
        record = new ArrayList<>();
    }

    @Override
    public boolean add(T element) {
        return record.add(element);
    }

    @Override
    public T get(int index){
        return record.get(index);
    }
    @Override
    public int size() {
        return record.size();
    }

    @Override
    public boolean isEmpty() {
        return record.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return record.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return record.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        record.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return record.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return record.toArray(a);
    }

    @Override
    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        return record.toArray(generator);
    }

    @Override
    public boolean remove(Object o) {
        return record.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return record.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return record.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return record.addAll(index,c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return record.removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return record.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return record.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        record.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        record.sort(c);
    }

    @Override
    public void clear() {
        record.clear();
    }
    @Override
    public T set(int index, T element) {
        return record.set(index,element);
    }

    @Override
    public void add(int index, T element) {
        record.add(index,element);
    }

    @Override
    public T remove(int index) {
        return record.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return record.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return record.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return record.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return record.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return record.subList(fromIndex,toIndex);
    }

    @Override
    public Spliterator<T> spliterator() {
        return record.spliterator();
    }

    @Override
    public Stream<T> stream() {
        return record.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return record.parallelStream();
    }
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for(int counter = 0; counter < record.size(); counter++){
            if(counter == record.size()-1)
                text.append(record.get(counter));
            else
                text.append(record.get(counter)).append(",");
        }
        return text.toString();
    }

}
