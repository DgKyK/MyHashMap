public class TestClass {
    public static void main(String[] args) {
        MyHashMap<Integer, String> test = new MyHashMap<>();
        test.put(1,"one");
        test.put(2,"two");
        test.put(3,"three");
        test.put(4,"four");
        test.put(5,"five");
        test.put(6,"newFive");
        test.delete(3);
        for(String k : test){
            System.out.println(k);
        }
    }
}