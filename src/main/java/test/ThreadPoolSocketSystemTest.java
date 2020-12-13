package test;

import socket.SimpleSocketClient;
import socket.ThreadPoolSocketServer;
import tester.LoadTester;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.lang.String.format;

public class ThreadPoolSocketSystemTest extends AbstractSystemTest {
    final static Logger log = Logger.getLogger(ThreadPoolSocketSystemTest.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        Executors.newSingleThreadExecutor().submit(() -> {
            log.info(format("[%s] Server start", Thread.currentThread().getName()));
            new ThreadPoolSocketServer(10).listen();
        });
        Thread.sleep(2000);
        new LoadTester(new SimpleSocketClient()).runTest(20);
        System.exit(0);
    }
}
