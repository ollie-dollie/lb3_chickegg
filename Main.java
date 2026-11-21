public class Main {
    public static void main(String[] args) {
        Result result = new Result();

        Thread chicken = new Thread(new AnimalThread(result, "Курица"));
        Thread egg = new Thread(new AnimalThread(result, "Яйцо"));

        chicken.start();
        egg.start();

        try {
            chicken.join();
            egg.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        // проверка завершения потоков
        if (!chicken.isAlive() && !egg.isAlive()) {
            result.showWinner();
        }
    }
}

// класс для хранения результата спора
class Result {
    private String winner = "";

    // вывод слова и сохранение последнего результата
    public synchronized void printWord(String word) {
        System.out.println(word);
        winner = word;
    }

    // определение победителя
    public void showWinner() {
        if (winner.equals("Курица")) {
            System.out.println("Победила курица!");
        } else {
            System.out.println("Победило яйцо!");
        }
    }
}

// поток для вывода слов
class AnimalThread implements Runnable {
    private final Result result;
    private final String word;

    AnimalThread(Result result, String word) {
        this.result = result;
        this.word = word;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            result.printWord(word);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}