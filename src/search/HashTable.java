package search;

public class HashTable<T> {
    private DataItem[] hashArray;
    private int arraySize;
    private DeletedItem deleted = new DeletedItem();
    private int num_items = 0;

    public HashTable(int tableSize) {
        arraySize = tableSize;
        hashArray = new DataItem[arraySize];
    }

    public int hashFunc1(int key) {
        return key % arraySize;
    }

    public int hashFunc2(int key) {
        return 5 - key % 5;
    }

    public void insert(int key, DataItem item, int size) {

        if (num_items == size){
            System.out.println(" Error. Table Full.");
            return;
        }
        else
            num_items++;

        int hashVal = hashFunc1(key);
        int stepSize = hashFunc2(key);
        while (hashArray[hashVal] != null &&
                hashArray[hashVal] != deleted) {
            hashVal += stepSize;
            hashVal %= arraySize;
        }
        hashArray[hashVal] = item;
    }

    public DataItem delete(int key) {
        int hashVal = hashFunc1(key);
        int stepSize = hashFunc2(key);

        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].getKey() == key) {
                num_items--;
                DataItem temp = hashArray[hashVal];
                hashArray[hashVal] = deleted;
                return temp;
            }
            hashVal += stepSize;
            hashVal %= arraySize;
        }
        return null;
    }

    public DataItem find(int key)
    {
        int hashVal = hashFunc1(key);
        int stepSize = hashFunc2(key);

        while (hashArray[hashVal] != null)
        {
            if (hashArray[hashVal].getKey() == key)
                return hashArray[hashVal];
            hashVal += stepSize;
            hashVal %= arraySize;
        }
        return null;
    }
}