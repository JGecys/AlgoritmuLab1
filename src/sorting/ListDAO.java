package sorting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ListDAO extends BaseDAO implements Iterable<BaseDAO.Node> {

    public ListDAO(String filename) throws FileNotFoundException {
        super(filename);
    }

    private Node first() throws IOException {
        randomAccessFile.seek(0);
        return get(randomAccessFile.readInt());
    }

    private Node get(int index) throws IOException {
        if (index == -1) {
            return null;
        }
        randomAccessFile.seek(getPos(index));
        return new Node(index, randomAccessFile.readInt(), randomAccessFile.readDouble(), randomAccessFile.readInt());
    }

    public Node getNext(Node node) throws IOException {
        return get(node.getNext());
    }

    private void set(int index, Node node) throws IOException {
        randomAccessFile.seek(getPos(index));
        randomAccessFile.writeInt(node.getPrev());
        randomAccessFile.writeDouble(node.getValue());
        randomAccessFile.writeInt(node.getNext());
    }

    public void set(Node node) throws IOException {
        set(node.getPosition(), node);
    }

    @Override
    public Iterator<Node> iterator() {
        return new ListDAOIterator();
    }


    public class ListDAOIterator implements Iterator<Node> {

        private Node current = null;

        @Override
        public boolean hasNext() {
            return current == null || current.getNext() != -1;
        }

        @Override
        public Node next() {
            try {
                if (current == null) {
                    return (current = first());
                }
                return (current = get(current.getNext()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private long getPos(int next) {
        return 4 + (16 * next);
    }

}
