import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> ,Iterable<V> {
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
        int index = hashFunction(hashTable.length, newNode.getKey());
        if (hashTable[index] == null) {
            return simplePutting(index , newNode);
        }

        List<MapNode<K, V>> nodeList = hashTable[index].getNodes();
        for(MapNode<K, V> nodeFromList : nodeList){
            if(keyExistButValueNew(nodeFromList, newNode, value) || collisionSolving(nodeFromList,newNode,nodeList)){
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

    private int hashFunction(int tableLength, K key){
        int result = 31;
        int prime = 7;
        result = result * prime + key.hashCode();
        return result % tableLength;
    }

    private boolean simplePutting(int index, MapNode<K, V> newNode){
        hashTable[index] = new MapNode<>(null, null);
        hashTable[index].getNodes().add(newNode);
        size++;
        return true;
    }

    private boolean keyExistButValueNew(final MapNode<K, V> nodeFromList,
                                       final MapNode<K, V> newNode,
                                       V value){
        if(newNode.getKey().equals(nodeFromList.getKey()) && !newNode.getValue().equals(nodeFromList.getValue())){
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
        int index = hashFunction(hashTable.length, key);
        if(hashTable[index] != null){
            List<MapNode<K, V>> nodeList = hashTable[index].getNodes();
            for(MapNode<K, V> currentNode : nodeList){
                if(currentNode.getKey().equals(key)) {
                    return currentNode.getValue();
                }
            }
        }

        return null;
    }

    @Override
    public boolean delete(K key) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<>() {
            int counter = 0;
            MapNode<K, V>[] existValue = new MapNode[size];

            @Override
            public boolean hasNext(){
                checkAndSetExistValue();
                return counter < existValue.length;
            }

            private void checkAndSetExistValue(){
                int i = 0;
                for(MapNode<K, V> currentNode : hashTable){
                    if(currentNode != null) {
                        existValue[i] = currentNode;
                        i++;
                    }
                }
            }

            @Override
            public V next() {
                return existValue[counter++].getNodes().get(0).getValue();
            }
        };
    }
}
