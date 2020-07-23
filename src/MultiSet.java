public interface MultiSet<T> {

    void add(T x);

    void add(T x, int count);

    void remove(T x);

    void removeElement(T x);

    void remove(T x, int count);

    int getCount(T x);

    T first();

    T last();

    boolean contains(T x);

    MultiSet<T> clone();
}
