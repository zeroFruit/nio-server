package common;

import java.util.logging.Logger;

public class GreetingService {
    final static Logger log = Logger.getLogger(GreetingService.class.getSimpleName());

    public String doService() {
        try {
            log.info(String.format("[%s] Start service: ", Thread.currentThread().getName()));
            Thread.sleep(2000);
            log.info(String.format("[%s] Finish service", Thread.currentThread().getName()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello World";
    }
}
