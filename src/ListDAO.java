import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;

public class ListDAO extends BaseDAO implements Iterable<Double> {

    public ListDAO(String filename) throws FileNotFoundException {
        super(filename);
    }

    public Node first() throws IOException {
        randomAccessFile.seek(0);
        return get(randomAccessFile.readInt());
    }

    private Node get(int index) throws IOException {
        randomAccessFile.seek(16 * index + 4);
        return new Node(randomAccessFile.readInt(), randomAccessFile.readDouble(), randomAccessFile.readInt());
    }

    private void set(int index, Node node) throws IOException {
        randomAccessFile.seek(16 * index + 4);
        randomAccessFile.writeInt(node.getPrev());
        randomAccessFile.writeDouble(node.getValue());
        randomAccessFile.writeInt(node.getNext());
    }

    @Override
    public Iterator<Double> iterator() {
        return new ListDAOIterator();
    }


    public class ListDAOIterator implements Iterator<Double> {

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
