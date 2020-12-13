package socket.nio;

import socket.AbstractSocketSystem;
import tester.TestClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

import static java.lang.String.format;

public class NioClient extends AbstractSocketSystem implements TestClient {
    final static Logger log = Logger.getLogger(NioClient.class.getSimpleName());

    @Override
    public void request() {
        try {
            SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", PORT));
            ByteBuffer writeBuf = ByteBuffer.wrap("Hello from client".getBytes());
            channel.write(writeBuf);

            ByteBuffer readBuf = ByteBuffer.allocate(256);
            channel.read(readBuf);
            String response = new String(readBuf.array()).trim();
            log.info(format("[%s] Client response: %s", Thread.currentThread().getName(), response));
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
