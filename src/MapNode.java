import java.util.LinkedList;
import java.util.List;

public class MapNode<K, V> {
    private List<MapNode<K, V>> nodes;
    private int hash;
    private K key;
    private V value;

    public MapNode(K key, V value){
        this.key =key;
        this.value = value;
        this.nodes = new LinkedList<>();
    }

    public int hashFnction(int tableLength){
        return hashCode() % tableLength;
    }
    /*Getters*/

    public List<MapNode<K, V>> getNodes() {
        return nodes;
    }

    public int getHash() {
        return hash;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    /*Setters*/

    public void setNodes(List<MapNode<K, V>> nodes) {
        this.nodes = nodes;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public int hashCode(){
        int prime = 31;
        int result = 1;
        return prime * result + key.hashCode() + value.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        MapNode<K, V> temp = (MapNode<K, V>) obj;
        return (this.hash == temp.hash && this.value.equals(temp.value) &&
                this.key.equals(temp.key));

    }
}
