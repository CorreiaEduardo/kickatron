package br.com.ducco.robot.ssim.conn;

import java.io.IOException;

public interface TCPConnection {
    void open() throws IOException;
    void close() throws IOException;

    void send(byte[] msg) throws IOException;
    String receive() throws IOException;
}
