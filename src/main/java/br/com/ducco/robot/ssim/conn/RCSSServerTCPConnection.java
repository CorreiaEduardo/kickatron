package br.com.ducco.robot.ssim.conn;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class RCSSServerTCPConnection implements TCPConnection {

    protected Socket socket;

    protected OutputStream out;

    protected InputStream in;

    protected final String host;

    protected final int port;

    public RCSSServerTCPConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void open() throws IOException {
        this.socket = new Socket(host, port);
        this.socket.setTcpNoDelay(true);

        this.in = new BufferedInputStream(socket.getInputStream());
        this.out = new BufferedOutputStream(new DataOutputStream(socket.getOutputStream()));
    }

    @Override
    public void send(byte[] msg) throws IOException {
        System.out.println("S: " + new String(msg));
        byte[] lengthBytes = ByteBuffer.allocate(4).putInt(msg.length).array();
        out.write(lengthBytes);
        out.write(msg);
        out.flush();
    }

    @Override
    public String receive() throws IOException {
        final byte[] lengthBytes = new byte[4];
        in.read(lengthBytes);

        final int msgLength = ByteBuffer.wrap(lengthBytes).getInt();
        final byte[] msgBytes = new byte[msgLength];
        in.read(msgBytes);

        String msg = new String(msgBytes);
        System.out.println("R: " + msg + "\n");
        return msg;
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}
