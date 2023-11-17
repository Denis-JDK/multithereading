package org.swiftbook.java_core;

import java.util.concurrent.*;

public class ConnectServer {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        Connection connection = Connection.getConnection();

        for (int i = 0; i < 200; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.work();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

    }
}
class Connection{
    private static Connection connection = new Connection();
    private int connectionsCount;
    Semaphore semaphore = new Semaphore(10);
    private Connection(){};

    public static Connection getConnection(){
        return connection;
    }

    public void work() throws InterruptedException {
        semaphore.acquire(); //останавливаем 11 поток, ждет пока отработает semaphore.release();
        try {
            doWork(); // работаем с ограниченным ресурсом в приватном методе, чтоб не было возможности его вызвать без семафора
        } finally {
            semaphore.release();//finally чтоб в любом случае мы вернули разрешение на работу с ресурсом в семафор, даже при исключении при коннекте с сервером.
        }
    }

    private void doWork() throws InterruptedException {
        synchronized (this){
            connectionsCount++;
            System.out.println("ConnectionsCount " + connectionsCount);
        }
        Thread.sleep(1000);

        synchronized (this){
            connectionsCount--;
        }

    }
}
