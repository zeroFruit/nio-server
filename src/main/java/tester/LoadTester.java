package tester;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.springframework.util.StopWatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.lang.String.format;

public class LoadTester {
    final static Logger log = Logger.getLogger(LoadTester.class.getSimpleName());

    static AtomicInteger counter = new AtomicInteger(0);

    private final TestClient testClient;

    public LoadTester(TestClient testClient) {
        this.testClient = testClient;
    }

    public void runTest(int nClients) throws BrokenBarrierException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(nClients);

        CyclicBarrier barrier = new CyclicBarrier(nClients + 1);


        for (int i = 0; i < nClients; i += 1) {
            es.submit(() -> {
                int idx = counter.addAndGet(1);

                barrier.await();
                log.info(format("[%s] [%d] Thread start ", Thread.currentThread().getName(), idx));

                StopWatch innerWatch = new StopWatch();
                innerWatch.start();

                testClient.request();
                innerWatch.stop();
                log.info(format("[%s] [%d] Elapsed (%d ms)", Thread.currentThread().getName(), idx, innerWatch.getTotalTimeMillis()));

                return null; // compiler가 callable로 인식하고 barrier.await()의 exception을 밖으로 던짐
            });
        }
        barrier.await();
        StopWatch watch = new StopWatch();
        watch.start();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);

        watch.stop();
        log.info(format("[%s] Total: %d ms", Thread.currentThread().getName(), watch.getTotalTimeMillis()));
    }
}
