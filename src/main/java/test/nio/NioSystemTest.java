package test.nio;

import socket.nio.NioClient;
import socket.nio.NioServer;
import test.AbstractSystemTest;
import tester.LoadTester;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.lang.String.format;

public class NioSystemTest extends AbstractSystemTest {
    final static Logger log = Logger.getLogger(NioSystemTest.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        Executors.newSingleThreadExecutor().submit(() -> {
            log.info(format("[%s] Server start", Thread.currentThread().getName()));
            new NioServer().listen();
        });
        Thread.sleep(2000);
        new LoadTester(new NioClient()).runTest(2);
        System.exit(0);
    }
}
