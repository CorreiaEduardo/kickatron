package br.com.ducco.robot.ssim;

import br.com.ducco.robot.ssim.agent.DumbKickatronAgent;
import br.com.ducco.robot.ssim.conn.RCSSServerTCPConnection;
import br.com.ducco.robot.ssim.conn.TCPConnection;
import br.com.ducco.robot.ssim.move.KickFallRise;
import br.com.ducco.robot.ssim.sexpr.SExpression;

import java.io.IOException;
import java.util.Optional;

public class KickatronClient {
    private static final String EMPTY = "";
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3100;

    private static TCPConnection connection;

    public static void main(String[] args) {
        SExpression perceptors;
        connection = new RCSSServerTCPConnection(SERVER_HOST, SERVER_PORT);

        try {
            connection.open();
            DumbKickatronAgent agent = new DumbKickatronAgent("10", "Kickatron", new KickFallRise());

            sendEffector("(scene " + agent.getScene() + ")");
            perceptors = receivePerceptors();

            sendEffector("(init (unum " + agent.getUniformNumber() + ")(teamname " + agent.getTeamName() + "))");
            perceptors = receivePerceptors();

            sendEffector("(beam -0.15 -0.06 0)");
            perceptors = receivePerceptors();

            for (int clock = 0; true; clock++) {
                agent.sense(perceptors);

                if (clock % 50 == 0) { // each 100ms
                    final String action = Optional.ofNullable(agent.act()).orElse(EMPTY);
                    for (int i = 0; i <= 50; i++) {
                        sendEffector(action);
                    }
                }

                perceptors = receivePerceptors();
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
        connection.send((msg + "(syn)").getBytes());
    }

    public static SExpression receivePerceptors() throws IOException {
        return new SExpression(connection.receive());
    }
}
