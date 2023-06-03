package br.com.ducco.robot.ssim.agent;

import br.com.ducco.robot.ssim.sexpr.SExpression;
import lombok.Getter;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import static br.com.ducco.robot.ssim.sexpr.RobocupSEXPREntry.GAME_STATE.PLAY_MODE.FREE_KICK_RIGHT;
import static br.com.ducco.robot.ssim.sexpr.RobocupSEXPREntry.GAME_STATE.PLAY_MODE.KICK_OFF_LEFT;
import static br.com.ducco.robot.ssim.sexpr.RobocupSEXPREntry.GAME_STATE.PLAY_MODE.KICK_OFF_RIGHT;
import static br.com.ducco.robot.ssim.sexpr.RobocupSEXPREntry.GAME_STATE.PLAY_MODE.PLAYON;

@Getter
public class DumbKickatronAgent implements KickatronAgent {
    private final String scene = "rsg/agent/nao/nao.rsg";
    private final String uniformNumber;
    private final String teamName;

    private boolean inCorrectPlayMode;
    private final Queue<String> dumbKeyFrameMovements = new LinkedList<>();
    private static final String RESET_JOINTS = "(lle1 0)(rle1 0)(lle3 0)(rle3 0)(lae1 0)(lle4 0)(rle4 0)(rae1 0)(lle2 0)(rae2 0)(lae2 0)(lae3 0)(rae3 0)(lle5 0)(rle5 0)";

    public DumbKickatronAgent(String uniformNumber, String teamName) {
        this.uniformNumber = uniformNumber;
        this.teamName = teamName;

        // KICK PHASE
        this.dumbKeyFrameMovements.add("(lle3 200)(lle4 -5)"); //KICK
        this.dumbKeyFrameMovements.add("(lle3 -100)(lle4 5)"); //INVERT JOINTS
        this.dumbKeyFrameMovements.add("(lle3 0)(lle4 0)(lae2 100)(rae2 -100)"); // RESET LEGS + RISE ARMS
        this.dumbKeyFrameMovements.add("(lae2 0)(rae2 0)"); // RESET ARMS

        // RISING PHASE
        this.dumbKeyFrameMovements.add("(lle3 19)(rle3 19) (lle4 -40)(rle4 -40) (lle5 20)(rle5 20) (lae1 -100)(rae1 -100) (lae3 -100)(rae3 100) (lae4 -10)(rae4 10)");
        this.dumbKeyFrameMovements.add("(lae2 -100)(rae2 100) (lle3 -100)(rle3 -100) (lle3 -500)(rle3 -500) (lle4 -100)(rle4 -100) (he2 -100)");
        this.dumbKeyFrameMovements.add("(lle4 100)(rle4 100)(lle5 -20)(rle5 -20)");
    }

    @Override
    public void sense(SExpression perceptors) {
        this.inCorrectPlayMode = perceptors.contains(KICK_OFF_LEFT, KICK_OFF_RIGHT, PLAYON, FREE_KICK_RIGHT);
    }

    @Override
    public String act() {
        if (inCorrectPlayMode) {
            return Optional.ofNullable(dumbKeyFrameMovements.poll())
                    .orElse(RESET_JOINTS);
        }

        return RESET_JOINTS;
    }
}
