package br.com.ducco.robot.ssim;

import br.com.ducco.robot.ssim.agent.DumbKickatronAgent;
import br.com.ducco.robot.ssim.conn.RCSSServerTCPConnection;
import br.com.ducco.robot.ssim.conn.TCPConnection;
import br.com.ducco.robot.ssim.sexpr.SExpression;

import java.io.IOException;
import java.util.Optional;

public class KickatronClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3100;

    private static TCPConnection connection;

    public static void main(String[] args) {
        connection = new RCSSServerTCPConnection(SERVER_HOST, SERVER_PORT);

        try {
            connection.open();
            DumbKickatronAgent agent = new DumbKickatronAgent("10", "Kickatron");

            sendEffector("(scene " + agent.getScene() + ")");
            receivePerceptors();

            sendEffector("(init (unum " + agent.getUniformNumber() + ")(teamname " + agent.getTeamName() + "))");
            receivePerceptors();

            sendEffector("(beam -0.1 0.0 -90.0)");

            while (true) {
                SExpression currentPerceptions = receivePerceptors();
                agent.sense(currentPerceptions);
                sendEffector(agent.act().orElse(""));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendEffector(String msg) throws IOException {
        connection.send((msg).getBytes());
    }

    public static SExpression receivePerceptors() throws IOException {
        return new SExpression(connection.receive());
    }
}
