package sorting;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.lang.Double.compare;

public class Main {

    public static final String COMMANDS = "write [filename] [itemCnt], read [filename], sort [filename], readlist [filename], sortlist [filename]";

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        if (args.length == 0) {
            println("Available commands: " + COMMANDS);
        }
        switch (args[0].toLowerCase()) {
            case "write":
                main.write(args[1], Integer.parseInt(args[2]));
                break;
            case "read":
                main.read(args[1]);
                break;
            case "readlist":
                main.readList(args[1]);
                break;
            case "sort":
                main.sort(args[1]);
                break;
            case "sortlist":
                main.sortlist(args[1]);
                break;
            default:
                println("Command not found. Available: " + COMMANDS);
        }
    }

    private void sortlist(String file) throws IOException {
        ListDAO list = new ListDAO(file);
        long startTime = System.nanoTime();
        boolean swapped;                                                    //1.
        int n = list.size();                                                //2.
        do {                                                                //
            swapped = false;                                                //3.
            BaseDAO.Node first = list.first();                              //4.
            for (int i = 0; i < n - 1; i++) {                               //5.
                BaseDAO.Node second = list.getNext(first);                  //6.
                if (second != null                                          //
                        && compare(first.getValue(), second.getValue()) > 0)//7.
                {                                                           //
                    double tmp = first.getValue();                          //8.
                    first.setValue(second.getValue());                      //9.
                    second.setValue(tmp);                                   //10.
                    swapped = true;                                         //11.
                }                                                           //
                first = second;                                             //12.
            }                                                               //
        } while (swapped);                                                  //13.
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        print(list);
        println("Soreted in " + duration / 1_000_000f + "ms.");
        list.close();
    }

    private void sort(String file) throws IOException {
        ArrayDAO list = new ArrayDAO(file);
        long startTime = System.nanoTime();
        int n = list.size();                            //1.
        boolean swapped;                                //2.
        do {                                            //3.
            swapped = false;                            //4.
            for (int i = 0; i < n - 1; i++) {           //5.
                double first = list.get(i);             //6.
                double second = list.get(i + 1);        //7.
                if (compare(first, second) > 0) {//8.
                    list.set(i, second);                //9.
                    list.set(i + 1, first);             //10.
                    swapped = true;                     //11.
                }                                       //12.
            }                                           //13.
        } while (swapped);                              //14.
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        println("");
        for (Double value : list) {
            println(value);
        }
        println("Soreted in " + duration / 1_000_000f + "ms.");
        list.close();
    }

    public void write(String outFile, int count) throws IOException {
        Set<Integer> usedValues = new HashSet<>(count);
        RandomAccessFile randomAccessFile = new RandomAccessFile(outFile, "rw");
        randomAccessFile.setLength(getPos(count));
        Random random = new Random();
        int next = getNext(random, count, usedValues);
        int previous = -1;
        randomAccessFile.seek(0);
        randomAccessFile.writeInt(next);
        for (int i = 0; i < count; i++) {
            randomAccessFile.seek(getPos(next));
            randomAccessFile.writeInt(previous);
            previous = next;
            randomAccessFile.writeDouble(random.nextDouble() * 5000);
            randomAccessFile.writeInt(i + 1 >= count ? -1 : (next = getNext(random, count, usedValues)));
        }
        randomAccessFile.close();
    }

    public long getPos(int pos) {
        return 4 + (16 * pos);
    }

    public int getNext(Random random, int count, Set<Integer> used) {
        int next;
        do {
            next = random.nextInt(count);
        } while (used.contains(next));
        used.add(next);
        return next;
    }

    public void read(String file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream inputStream = new BufferedInputStream(fileInputStream);
        ByteBuffer buffer = ByteBuffer.allocate(16);
        inputStream.read(buffer.array(), 0, 4);
        println(buffer.getInt(0));
        int i = 0;
        while (inputStream.available() > 0) {
            inputStream.read(buffer.array(), 0, 16);
            println(i++ + ": " + buffer.getInt(0) + " " + buffer.getDouble(4) + " " + buffer.getInt(12));
        }
        inputStream.close();
        fileInputStream.close();
    }

    public void readList(String file) throws IOException {
        ListDAO listDao = new ListDAO(file);
        print(listDao);
        listDao.close();
    }

    private void print(ListDAO listDao) {
        int i = 0;
        for (BaseDAO.Node node : listDao) {
            println(i++ + ": " + node.toString());
        }
    }

    private static void println(Object sequence) {
        System.out.println(sequence);
    }

    private static void print(Object sequence) {
        System.out.print(sequence);
    }

}
