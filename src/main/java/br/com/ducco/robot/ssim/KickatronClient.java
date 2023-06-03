package br.com.ducco.robot.ssim;

import br.com.ducco.robot.ssim.agent.DumbKickatronAgent;
import br.com.ducco.robot.ssim.conn.RCSSServerTCPConnection;
import br.com.ducco.robot.ssim.conn.TCPConnection;
import br.com.ducco.robot.ssim.sexpr.SExpression;

import java.io.IOException;

public class KickatronClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3100;

    private static TCPConnection connection;

    public static void main(String[] args) {
        SExpression perceptors;
        connection = new RCSSServerTCPConnection(SERVER_HOST, SERVER_PORT);

        try {
            connection.open();
            DumbKickatronAgent agent = new DumbKickatronAgent("10", "Kickatron");

            sendEffector("(scene " + agent.getScene() + ")");
            perceptors = receivePerceptors();

            sendEffector("(init (unum " + agent.getUniformNumber() + ")(teamname " + agent.getTeamName() + "))");
            perceptors = receivePerceptors();

            sendEffector("(beam -0.1 0.0 -90.0)");
            perceptors = receivePerceptors();


            legacy(agent, perceptors);
//            refactored(agent, perceptors);

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

    private static void legacy(DumbKickatronAgent agent, SExpression perceptors) throws IOException {
        String joint = "(lle1 0)(rle1 0)(lle3 0)(rle3 0)(lae1 0)(lle4 0)(rle4 0)(rae1 0)(lle2 0)(rae2 0)(lae2 0)(lae3 0)(rae3 0)(lle5 0)(rle5 0)";
        int count = 200;
        while (true) {
            if (count >= 200 && count < 250) {
                joint = "(lle3 200)(lle4 -5)";

            } else if (count >= 250 && count < 300) {
                joint = "(lle3 -100)(lle4 5)";

            } else if (count >= 300 && count < 350) {
                joint = "(lle3 0)(lle4 0)(lae2 100)(rae2 -100)";
            } else if (count >= 350 && count < 400) {
                joint = "(lae2 0)(rae2 0)";
            }
            //levantar austin
            else if (count >= 400 && count < 450) {
                joint = "(lle3 19)(rle3 19)" +
                        "(lle5 20)(rle5 20)" +
                        "(lle4 -40)(rle4 -40)" +
                        "(lae1 -100)(rae1 -100)";

            } else if (count >= 450 && count < 500) {
                joint = "(rae2 100)(lae2 -100)" +
                        "(lle4 40)(rle4 40)" +
                        "(lle3 0)(rle3 0)" +
                        "(lle5 0)(rle5 0)" +
                        "(lae1 0)(rae1 0)";

            } else if (count >= 550 && count < 600) {
                joint = "(rae2 0)(lae2 0)" +
                        "(lle4 0)(rle4 0)";

            } else if (count >= 600 && count < 650) { //Posição inicial: sentado
                joint = "(lae1 2)(rae1 2)" + // braços: equilíbrio
                        "(lle5 -100)(rle5 -100)"; //pé: inverter posicionamento

            } else if (count >= 650 && count < 700) {
                joint = "(lae1 0)(rae1 0)" + //braços: parar movimento
                        "(lle1 -1.0)(rle1 -1.0)" + //quadril: aproximar do chão
                        "(lle5 0)(rle5 0)"; //pé: parar movimento

            } else if (count >= 700 && count < 750) {
                joint = "(lle1 0)(rle1 0)" + //quadril: parar movimento
                        "(lle5 -20)(rle5 -20)"; //pé: ajuste para equilíbrio

            } else if (count >= 750 && count < 800) {
                joint = "(lae1 -7)(rae1 -7)" +
                        "(lle1 -80)(rle1 -80)" +
                        "(lle5 1)(rle5 1)" +
                        "(lle4 -100)(rle4 -100)";

            }  else if (count >= 800 && count < 850) {
                joint = "(lae1 0.5)(rae1 0.5)" +
                        "(lle1 -40)(rle1 -40)" +
                        "(lle2 -5)(rle2 5)" + // joelhos: valgo dinâmico
                        "(lle4 0)(rle4 0)" +
                        "(lle5 0)(rle5 0)";

            } else if (count >= 850 && count < 900) { //posição inicial: agachado
                joint = "(lle1 70)(rle1 70)" +
                        "(lle2 0)(rle2 0)" +
                        "(lle3 -50)(rle3 -50)" +
                        "(lle4 30)(rle4 30)" +
                        "";

            } else if (count >= 900 && count < 1000) {
                joint = "(lae1 0)(rae1 0)" +
                        "(lle1 0)(rle1 0)" +
                        "(lle3 0)(rle3 0)" +
                        "(lle4 0)(rle4 0)" +
                        "(lle5 0)(rle5 0)";

            }
            count++;
            sendEffector(joint);
            perceptors = receivePerceptors();
        }

    }

    private static void refactored(DumbKickatronAgent agent, SExpression perceptors) throws IOException {
        for (int clock = 0; true; clock++) {
            agent.sense(perceptors);

            if (clock % 50 == 0) { // each 100ms
                sendEffector(agent.act());
            }

            perceptors = receivePerceptors();
        }
    }

    public static void sendEffector(String msg) throws IOException {
        connection.send((msg + "(syn)").getBytes());
    }

    public static SExpression receivePerceptors() throws IOException {
        return new SExpression(connection.receive());
    }
}
