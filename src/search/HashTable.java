package search;

import java.util.ArrayList;
import java.util.List;

public class HashTable {
    private DataItem[] hashArray;
    private int arraySize;
    private DeletedItem deleted = new DeletedItem();
    private int num_items = 0;

    public HashTable(int tableSize) {
        arraySize = tableSize;
        hashArray = new DataItem[arraySize];
    }

    private int hashFunc1(int key) {
        if(key < 0){
            key *= -1;
        }
        return key % arraySize;
    }

    private int hashFunc2(int key) {
        if(key < 0){
            key *= -1;
        }
        return key % (arraySize / 5);
    }

    public void insert(DataItem item) {
        if (num_items == arraySize) {
            System.out.println(" Error. Table Full.");
            return;
        } else
            num_items++;

        int hashVal = hashFunc1(item.hashCode());
        int stepSize = hashFunc2(item.hashCode());
        int i = 0;
        while (hashArray[hashVal] != null &&
                hashArray[hashVal] != deleted) {
            hashVal += stepSize;
            hashVal %= arraySize;
            i++;
            if( i % (arraySize / 5) == 0){
                System.out.println("trying to insert " + item.getValue().toString());
            }
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

    public DataItem find(int key) {
        int hashVal = hashFunc1(key);
        int stepSize = hashFunc2(key);

        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].getKey() == key)
                return hashArray[hashVal];
            hashVal += stepSize;
            hashVal %= arraySize;
        }
        return null;
    }

    public List<Student> find(String lastname) {
        List<Student> list = new ArrayList<>();
        int key = lastname.hashCode();
        if (key < 0) {
            key *= -1;
        }
        int hashVal = hashFunc1(key);
        int stepSize = hashFunc2(key);
        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].getKey().equals(key)
                    && ((Student) hashArray[hashVal].getValue()).getLastname().equals(lastname))
                list.add(((Student) hashArray[hashVal].getValue()));
            hashVal += stepSize;
            hashVal %= arraySize;
        }
        return list;
    }
}