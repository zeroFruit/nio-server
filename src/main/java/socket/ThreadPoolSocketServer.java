package socket;

import common.GreetingService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.lang.String.format;

public class ThreadPoolSocketServer extends AbstractSocketSystem implements SocketServer {
    final static Logger log = Logger.getLogger(ThreadPoolSocketServer.class.getSimpleName());

    private final ExecutorService es;

    public ThreadPoolSocketServer(int nThreads) {
        this.es = Executors.newFixedThreadPool(nThreads);
    }

    public void listen() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    es.execute(new SocketRunnable(socket));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SocketRunnable implements Runnable {
        Socket socket;

        SocketRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                OutputStream stream = socket.getOutputStream();
                stream.write(new GreetingService().doService().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    log.info(format("[%s] Server socket closed.", Thread.currentThread().getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
