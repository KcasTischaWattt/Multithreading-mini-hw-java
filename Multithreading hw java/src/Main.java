import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

    // Дань уважения осям, не бейте за кодстайл...
    static final int bufsiz = 10;

    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(bufsiz);

        Random rand = new Random(System.currentTimeMillis());

        Runnable producer = () -> {
            for (int i = 0; i < rand.nextInt(5, 10) * bufsiz; i++) {
                try {
                    int ranVal = rand.nextInt(0, 100);
                    System.out.println("ДОКЛАДЫВАЕТ ПЕРВЫЙ ПОТОК. Я сгенерировал число " + ranVal + "!");
                    buffer.put(ranVal);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };

        Runnable consumer = () -> {
            while (true) {
                try {
                    System.out.println("ГОВОРИТ ВТОРОЙ ПОТОК. Я получил число " + buffer.take() + "!");
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}