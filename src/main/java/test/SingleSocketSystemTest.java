package test;

import socket.SimpleSocketClient;
import socket.SingleSocketServer;
import tester.LoadTester;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.lang.String.format;

public class SingleSocketSystemTest extends AbstractSystemTest {
    final static Logger log = Logger.getLogger(SingleSocketSystemTest.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        Executors.newSingleThreadExecutor().submit(() -> {
            log.info(format("[%s] Server start", Thread.currentThread().getName()));
            new SingleSocketServer().listen();
        });
        Thread.sleep(2000);
        new LoadTester(new SimpleSocketClient()).runTest(2);
        System.exit(0);
    }
}
