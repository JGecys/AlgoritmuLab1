import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;

public class ListDAO extends BaseDAO implements Iterable<Double> {


    public ListDAO(String filename) {
        super(filename);
    }

    public Node first() {
        try {
            randomAccessFile.seek(0);
            randomAccessFile.read(buffer.array(), 0, 4);
            return get(buffer.getInt(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Node get(int index) {
        try {
            randomAccessFile.seek(16 * index + 4);
            randomAccessFile.read(buffer.array(), 0, 16);
            return new Node(buffer.getInt(0), buffer.getDouble(4), buffer.getInt(12));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void set(int index, Node node) {
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
    protected int bufferSize() {
        return 16;
    }

    @Override
    public Iterator<Double> iterator() {
        return new ListDAOIterator();
    }


    public class ListDAOIterator implements  Iterator<Double> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Double next() {
            return null;
        }
    }

}
