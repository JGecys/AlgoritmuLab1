import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class ArrayDAO implements Iterable<Double> {

    RandomAccessFile randomAccessFile;
    ByteBuffer buffer;

    public ArrayDAO(String fileName) {
        try {
            randomAccessFile = new RandomAccessFile(fileName, "rws");
            buffer = ByteBuffer.allocate(16);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Double get(int index) {
        try {
            randomAccessFile.seek(16 * index + 4);
            randomAccessFile.read(buffer.array(), 0, 16);
            return buffer.getDouble(4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double set(int index, Double node) {
        try {
            randomAccessFile.seek(16 * index + 4);
            buffer.putInt(0, index - 1);
            buffer.putDouble(4, node);
            buffer.putInt(12, index + 1);
            randomAccessFile.write(buffer.array(), 0, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1.0;
    }

    public int size() {
        try {
            return (int) ((randomAccessFile.length() - 4) / 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(Double aDouble) {
        int size = size();
        try {
            randomAccessFile.setLength(size + 16);
            randomAccessFile.seek(16 * size + 4);
            buffer.putInt(0, size - 1);
            buffer.putDouble(4, aDouble);
            buffer.putInt(12, -1);
            randomAccessFile.write(buffer.array(), 0, 16);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean add(int index, Double aDouble) {
        int size = size();
        try {
            randomAccessFile.setLength(size + 16);
            randomAccessFile.seek(16 * index + 4);
            int size2 = size + 12 - (index * 16);
            byte[] rest = new byte[size2];
            randomAccessFile.readFully(rest);
            buffer.putInt(0, index - 1);
            buffer.putDouble(4, aDouble);
            buffer.putInt(12, index + 1);
            randomAccessFile.write(buffer.array(), 0, 16);
            randomAccessFile.write(rest, 0, size2);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean close(){
        try {
            randomAccessFile.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Iterator<Double> iterator() {
        return new ArrayDAOIterator();
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

    public class ArrayDAOIterator implements Iterator<Double> {

        private int current = 0;
        private int size = size();

        @Override
        public boolean hasNext() {
            return current < size();
        }

        @Override
        public Double next() {
            return get(current++);
        }
    }

}
