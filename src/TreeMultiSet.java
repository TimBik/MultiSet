import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

//сортирует по количеству вхождений элемента
/*
пример:
есть массив 1 1 1 2 2 3 3 3 3 4 4
тогда будем иметь MultiSet с
элементами:

элемент - количество
2 - 2
4 - 2
1 - 3
3 - 4

T first()
вернет элемент 2
так как в массиве только 2 двойки
и 2 < 4(зависит от T compareTo)

T last()
вернет элемент 3
так как в массиве 4 тройки
 */
public class TreeMultiSet<T extends Comparable<T>> implements MultiSet<T> {
    Map<T, Pair<Integer, T>> map = new HashMap<>();
    TreeSet<Pair<Integer, T>> set = new TreeSet<>();

    @Override
    public void add(T x) {
        add(x, 1);
    }

    private void ifNoneCreate(T x) {
        if (!map.containsKey(x)) {
            Pair<Integer, T> p = new Pair(0, x);
            set.add(p);
            map.put(x, p);
        }
    }

    @Override
    public void add(T x, int count) {
        ifNoneCreate(x);
        Pair<Integer, T> old = map.get(x);
        Pair<Integer, T> p = new Pair(old.getKey() + count, x);
        set.remove(old);
        set.add(p);
        map.replace(x, p);
    }

    @Override
    public void remove(T x) {
        remove(x, 1);
    }

    @Override
    public void remove(T x, int count) {
        add(x, -count);
        ifBadRemove(x);
    }

    private void ifBadRemove(T x) {
        if (map.get(x).getKey() <= 0) {
            removeElement(x);
        }
    }

    @Override
    public void removeElement(T x) {
        map.remove(x);
        set.remove(x);
    }

    @Override
    public int getCount(T x) {
        return map.get(x).getKey();
    }

    @Override
    public T first() {
        return set.first().getValue();
    }

    @Override
    public T last() {
        return set.last().getValue();
    }

    @Override
    public boolean contains(T x) {
        return map.containsKey(x);
    }

    @Override
    public MultiSet<T> clone() {
        MultiSet<T> multiSet = new TreeMultiSet();
        for (Pair<Integer, T> p : set) {
            multiSet.add(p.getValue(), p.getKey());
        }
        return multiSet;
    }

    private static class Pair<T extends Comparable<T>, R extends Comparable<R>> implements Comparable<Pair<T, R>> {
        private T x;
        private R y;

        public Pair(T x, R y) {
            this.x = x;
            this.y = y;
        }

        public T getKey() {
            return x;
        }

        public R getValue() {
            return y;
        }

        @Override
        public int compareTo(Pair<T, R> o) {
            if (x.compareTo(o.x) == 0) return y.compareTo(o.y);
            return x.compareTo(o.x);
        }
    }
}
