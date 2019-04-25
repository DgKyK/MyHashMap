import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private MapNode<K, V>[] hashTable;
    private int size = 0;
    private float loadFactor;

    public MyHashMap(){
        hashTable = new MapNode[16];
        loadFactor = hashTable.length * 0.75f;
    }

    @Override
    public boolean put(final K key,final V value) {
        if(size + 1 >= loadFactor){
            loadFactor *= 2;
            hashTableDoubling();
        }

        MapNode<K, V> newNode = new MapNode<>(key, value);
        int index = newNode.hashFnction(hashTable.length);
        if (hashTable[index] == null) {
            return simplePutting(index , newNode);
        }

        List<MapNode<K, V>> nodeList = hashTable[index].getNodes();
        for(MapNode<K, V> k : nodeList){
            if(keyExistButValueNew(k, newNode, value) || collisionSolving(k,newNode,nodeList)){
                return true;
            }
        }
        return false;
    }

    private void hashTableDoubling(){
        MapNode<K, V>[] oldHashTable = hashTable;
        hashTable = new MapNode[hashTable.length * 2];
        size = 0;
        for(MapNode<K, V> current : oldHashTable){
            if(current != null){
                for(MapNode<K, V> k : current.getNodes()){
                    put(k.getKey(), k.getValue());
                }
            }
        }
    }

    private boolean simplePutting(int index, MapNode<K, V> newNode){
        hashTable[index] = newNode; // TODO Chek is your variant do the same !!!
        size++;
        return true;
    }

    private boolean keyExistButValueNew(final MapNode<K, V> nodeFromList,
                                       final MapNode<K, V> newNode,
                                       V value){
        if(newNode.getKey().equals(nodeFromList) && !newNode.getValue().equals(nodeFromList.getValue())){
            nodeFromList.setValue(value);
            return true;
        }
        return false;
    }

    private boolean collisionSolving(final MapNode<K, V> nodeFromList,
                                    final MapNode<K, V> newNode,
                                    final List<MapNode<K, V>> nodes){
        if(newNode.hashCode() == nodeFromList.hashCode() &&
                !Objects.equals(newNode.getKey(),nodeFromList.getKey()) &&
                !Objects.equals(newNode.getValue(),nodeFromList.getValue())){
            nodes.add(newNode);
            size++;
            return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public boolean delete(K key) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }
}
