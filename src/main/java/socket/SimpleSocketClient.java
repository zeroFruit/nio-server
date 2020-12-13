package socket;

import tester.TestClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

import static java.lang.String.format;

public class SimpleSocketClient extends AbstractSocketSystem implements TestClient {
    final static Logger log = Logger.getLogger(SimpleSocketClient.class.getSimpleName());

    public void request() {
        try {
            Socket socket = new Socket("localhost", PORT);
            InputStream stream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));

            String response = br.readLine();
            log.info(format("[%s] Client response: %s", Thread.currentThread().getName(), response));
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
