package br.com.ducco.robot.ssim.move;

import java.util.LinkedList;
import java.util.Queue;

public class KickFallRise implements Movement {
    @Override
    public Queue<String> getFrames() {
        Queue<String> movement = new LinkedList<>();

        // KICK PHASE
        movement.add("(lle3 200)(lle4 -5)"); // Movimentação da perna e pé;
        movement.add("(lle3 -100)(lle4 5)"); // Aceleração contrária na intenção de voltar a posição original
        movement.add("(lle3 0)(lle4 0)(lae2 100)(rae2 -100)"); // Reset nas pernas e abertura dos braços
        movement.add("(lae2 0)(rae2 0)"); // Reset dos braços


        // RISING PHASE
        // Aproximação do joelho ao tronco e rotação interna dos braços, finaliza o movimento deitado de braços abertos
        movement.add("(lle3 19)(rle3 19)" +
                "(lle5 20)(rle5 20)" +
                "(lle4 -40)(rle4 -40)" +
                "(lae1 -100)(rae1 -100)");


        // Extensão do joelho e movimentação dos braços para baixo, finaliza o movimento sentado
        movement.add("(rae2 100)(lae2 -100)" +
                "(lle4 40)(rle4 40)" +
                "(lle3 0)(rle3 0)" +
                "(lle5 0)(rle5 0)" +
                "(lae1 0)(rae1 0)");


        // Reset das juntas utilizadas
        movement.add("(rae2 0)(lae2 0)" +
                "(lle4 0)(rle4 0)");


        // Ajustes dos braços e pés para prover equilibrio
        movement.add("(lae1 2)(rae1 2)" + // braços: equilíbrio
                "(lle5 -100)(rle5 -100)"); //pé: inverter posicionamento


        // Aproximar torso do chão utilizando o quadril
        movement.add("(lae1 0)(rae1 0)" + //braços: parar movimento
                "(lle1 -1.0)(rle1 -1.0)" + //quadril: aproximar do chão
                "(lle5 0)(rle5 0)"); //pé: parar movimento


        // Ajustes dos pés para prover equilibrio, finaliza movimento sentado, com torso próximo ao chão e pernas abertas
        movement.add("(lle1 0)(rle1 0)" + //quadril: parar movimento
                "(lle5 -20)(rle5 -20)"); //pé: ajuste para equilíbrio


        // Ajustes nos braços para prover equilibrio
        // Flexão do joelho e movimentação do quadril, finaliza o movimento em uma tendência para posição de sentado
        movement.add("(lae1 -7)(rae1 -7)" +
                "(lle1 -80)(rle1 -80)" +
                "(lle5 1)(rle5 1)" +
                "(lle4 -100)(rle4 -100)");

        // Ajustes para estabilizar a posição de sentado
        // Movimentação inicial no braço para prover equilibrio
        movement.add("(lae1 0.5)(rae1 0.5)" +
                "(lle1 -40)(rle1 -40)" +
                "(lle2 -5)(rle2 5)" + // joelhos: valgo dinâmico
                "(lle4 0)(rle4 0)" +
                "(lle5 0)(rle5 0)");


        // Extensão dos joelhos, movimentação do quadril e pernas na intenção de levantar
        movement.add("(lle1 70)(rle1 70)" +
                "(lle2 0)(rle2 0)" +
                "(lle3 -50)(rle3 -50)" +
                "(lle4 30)(rle4 30)");


        // Resetando velocidades para estabilização de pé
        movement.add("(lae1 0)(rae1 0)" +
                "(lle1 0)(rle1 0)" +
                "(lle3 0)(rle3 0)" +
                "(lle4 0)(rle4 0)" +
                "(lle5 0)(rle5 0)");


        return movement;
    }
}
