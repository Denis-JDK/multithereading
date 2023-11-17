package org.swiftbook.java_core;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3); //ограничивает доступ потоков к ограниченному ресурсу

        try {//если свободных разрешений нет, то поток ждет метод semaphore.release(); пока какой то поток освободит в нем разрешение на ресурс в семафоре
            semaphore.acquire(); // забираем одно разрешение из семафора на использование ограниченного ресурса
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        semaphore.release(); //отпускает одно из выданных семафором разрешений на использование ограниченного ресурса

        System.out.println(semaphore.availablePermits());//количество свободных разрешений в семафоре


    }
}