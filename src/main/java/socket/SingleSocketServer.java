package socket;

import common.GreetingService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import static java.lang.String.format;

public class SingleSocketServer extends AbstractSocketSystem implements SocketServer {

    final static Logger log = Logger.getLogger(SingleSocketServer.class.getSimpleName());

    public static void main(String[] args) {
        new SingleSocketServer().listen();
    }

    public void listen() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    OutputStream stream = socket.getOutputStream();
                    stream.write(new GreetingService().doService().getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    socket.close();
                    log.info(format("[%s] Server socket closed.", Thread.currentThread().getName()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
