public class TestClass {
    public static void main(String[] args) {
        MyHashMap<Integer, String> test = new MyHashMap<>();
        test.put(1,"one");
        test.put(2,"two");
        test.put(3,"three");
        test.put(4,"four");
        test.put(5,"five");
        test.put(6,"newFive");
        try {
            test.delete(3);
        }catch(UnsupportedOperationException e) {
            e.printStackTrace();
        }
        for(String k : test) {
            System.out.println(k);
        }
        System.out.println("Get element : " + test.get(3) + "\n" + test.size());

    }
}