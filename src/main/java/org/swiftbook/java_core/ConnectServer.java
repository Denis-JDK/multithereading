package org.swiftbook.java_core;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectServer {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        Connection connection = Connection.getConnection();

        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.doWork();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        executorService.shutdown();

    }
}
class Connection{
    private static Connection connection = new Connection();
    private int connectionsCount;
    private Connection(){};

    public static Connection getConnection(){
        return connection;
    }

    public void doWork() throws InterruptedException {
        synchronized (this){
            connectionsCount++;
            System.out.println("ConnectionsCount " + connectionsCount);
        }
        Thread.sleep(5000);

        synchronized (this){
            connectionsCount--;
        }

    }
}
