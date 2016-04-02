package sorting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public abstract class BaseDAO {

    protected RandomAccessFile randomAccessFile;

    public BaseDAO(String filename) throws FileNotFoundException {
        randomAccessFile = new RandomAccessFile(filename, "rws");
    }

    public int size() throws IOException {
        return (int) ((randomAccessFile.length() - 4) / 16);
    }

    public boolean close() throws IOException {
        randomAccessFile.close();
        return true;
    }

    public class Node {
        private int prev;
        private int next;
        private double value;

        public Node() {
            this(-1, 0.0, -1);
        }

        public Node(int prev, double value, int next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }

        public int getNext() {
            return next;
        }

        public int getPrev() {
            return prev;
        }

        public double getValue() {
            return value;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public void setPrev(int prev) {
            this.prev = prev;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

}
