import java.io.*;
import java.nio.ByteBuffer;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        if (args.length == 0) {
            System.out.println("This program requires params: write, read, sort");
        }
        switch (args[0]) {
            case "write":
                main.write(args[1], Integer.parseInt(args[2]));
                break;
            case "read":
                main.read(args[1]);
                break;
            case "sort":
                main.sort(args[1]);
                break;
            default:
                System.out.println("Command not found. Available: write [filename] [itemCnt], read [filename], sort [filename]");
        }
    }

    private void sort(String file) {
        ArrayListDAO list = new ArrayListDAO(file);
//        for (ArrayListDAO.Node node : list) {
//            System.out.println(node.getPrev() + " " + node.getValue() + " " + node.getNext());
//        }


        int n = list.size();
        boolean swapped;
        int last = 0;
        do {
            swapped = false;
            for(int i = 0; i < n - 1; i++){
                ArrayListDAO.Node node = list.get(i);
                ArrayListDAO.Node node1 = list.get(i + 1);
                if(Double.compare(node.getValue(), node1.getValue()) > 0){
                    list.set(i, node1);
                    list.set(i + 1, node);
                    swapped = true;
                    last = i;
                }
            }
            System.out.print("\rsorted: " + (n - last) + "/" + n);
        } while (swapped);
        System.out.println("");
        for (int i = 0; i < list.size(); i++) {
            ArrayListDAO.Node node = list.get(i);
            System.out.println(node.getPrev() + " " + node.getValue() + " " + node.getNext());
        }
        list.close();

    }

    public void write(String outFile, int count) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
            ByteBuffer buffer = ByteBuffer.allocate(16);
            Random random = new Random();
            buffer.putInt(0);
            outputStream.write(buffer.array(), 0, 4);
            for (int i = 0; i < count; i++) {
                buffer.putInt(0, i - 1);
                buffer.putDouble(4, random.nextDouble() * 5000);
                buffer.putInt(12, i + 1 >= count ? -1 : i + 1);
                outputStream.write(buffer.array());//, (16 * i) + 4, 16
            }
            outputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(String file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream inputStream = new BufferedInputStream(fileInputStream);
            ByteBuffer buffer = ByteBuffer.allocate(16);
            inputStream.read(buffer.array(), 0, 4);
            System.out.println(buffer.getInt(0));
            while (inputStream.available() > 0) {
                inputStream.read(buffer.array(), 0, 16);
                System.out.println(buffer.getInt(0) + " " + buffer.getDouble(4) + " " + buffer.getInt(12));
            }
            inputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
