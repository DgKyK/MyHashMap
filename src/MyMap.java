public interface MyMap<K,V> {
    boolean put(K key, V value);
    V get(K key);
    boolean delete(K key);
    int size();
}
