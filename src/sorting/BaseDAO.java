package sorting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public abstract class BaseDAO {

    protected RandomAccessFile randomAccessFile;

    public BaseDAO(String filename) throws FileNotFoundException {
        randomAccessFile = new RandomAccessFile(filename, "rw");
    }

    public int size() throws IOException {
        if (randomAccessFile.length() == 0) {
            return 0;
        }
        return (int) ((randomAccessFile.length() - 4) / 16);
    }

    public boolean close() throws IOException {
        randomAccessFile.close();
        return true;
    }

    protected long getPos(int next) {
        return 4 + (16 * next);
    }

    protected void seek(int position) throws IOException {
        long pos = getPos(position);
        if (pos != randomAccessFile.getFilePointer()) {
            randomAccessFile.seek(pos);
        }
    }

    protected void seek(int position, int offset) throws IOException {
        long pos = getPos(position) + offset;
        if (pos != randomAccessFile.getFilePointer()) {
            randomAccessFile.seek(pos);
        }
    }

    public class Node {

        private final int position;
        private Integer prev = null;
        private Double value = null;
        private Integer next = null;

        public Node(int position) {
            this.position = position;
        }

        public int getNext() throws IOException {
            if(next == null){
                seek(position, 12);
                next = randomAccessFile.readInt();
            }
            return next;
        }

        public int getPrev() throws IOException {
            if(prev == null){
                seek(position);
                prev = randomAccessFile.readInt();
            }
            return prev;
        }

        public double getValue() throws IOException {
            if(value == null){
                seek(position, 4);
                value = randomAccessFile.readDouble();
            }
            return value;
        }

        public void setNext(int next) throws IOException {
            this.next = next;
            seek(position, 12);
            randomAccessFile.writeInt(next);
        }

        public void setPrev(int prev) throws IOException {
            this.prev = prev;
            seek(position);
            randomAccessFile.writeInt(prev);
        }

        public void setValue(double value) throws IOException {
            this.value = value;
            seek(position, 4);
            randomAccessFile.writeDouble(value);
        }

        public int getPosition() {
            return position;
        }

        @Override
        public String toString() {
            try {
                return Integer.toString(position) + ": " + getPrev() + " " + getValue() + " " + getNext();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Integer.toString(position);
        }
    }

}
