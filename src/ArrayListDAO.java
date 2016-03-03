import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * Created by jgecy on 2016-03-03.
 */
public class ArrayListDAO implements Iterable<ArrayListDAO.Node> {

    RandomAccessFile randomAccessFile;
    ByteBuffer buffer;

    public ArrayListDAO(String fileName) {
        try {
            randomAccessFile = new RandomAccessFile(fileName, "rws");
            buffer = ByteBuffer.allocate(16);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int getBegin() {
        try {
            randomAccessFile.seek(0);
            randomAccessFile.read(buffer.array(), 0, 4);
            return buffer.getInt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Node get(int index) {
        try {
            randomAccessFile.seek(16 * index + 4);
            randomAccessFile.read(buffer.array(), 0, 16);
            return new Node(buffer.getInt(0), buffer.getDouble(4), buffer.getInt(12));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void set(int index, Node node){
        try {
            randomAccessFile.seek(16 * index + 4);
            buffer.putInt(0, node.getPrev());
            buffer.putDouble(4, node.getValue());
            buffer.putInt(12, node.getNext());
            randomAccessFile.write(buffer.array(), 0, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterator<Node> iterator() {
        return new DAOIterator();
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

    public class DAOIterator implements Iterator<Node> {

        private Node current = new Node(-1, 0.0, getBegin());

        @Override
        public boolean hasNext() {
            return current != null && current.next != -1;
        }

        @Override
        public Node next() {
            return (current = get(current.next));
        }
    }

}
