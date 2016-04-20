package sorting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ListDAO extends BaseDAO implements Iterable<BaseDAO.Node> {

    public ListDAO(String filename) throws FileNotFoundException {
        super(filename);
    }

    public Node first() throws IOException {
        randomAccessFile.seek(0);
        return get(randomAccessFile.readInt());
    }

    private Node get(int index) throws IOException {
        if (index == -1) {
            return null;
        }
        return new Node(index);
    }

    public Node getNext(Node node) throws IOException {
        return get(node.getNext());
    }

    @Override
    public Iterator<Node> iterator() {
        return new ListDAOIterator();
    }


    public class ListDAOIterator implements Iterator<Node> {

        private Node current = null;

        @Override
        public boolean hasNext() {
            try {
                return current == null || current.getNext() != -1;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
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

}
