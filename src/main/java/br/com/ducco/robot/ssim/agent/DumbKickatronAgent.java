package br.com.ducco.robot.ssim.agent;

import br.com.ducco.robot.ssim.move.Movement;
import br.com.ducco.robot.ssim.sexpr.SExpression;
import lombok.Getter;

import java.util.Queue;

import static br.com.ducco.robot.ssim.sexpr.RobocupSEXPREntry.GAME_STATE.PLAY_MODE.*;

@Getter
public class DumbKickatronAgent implements KickatronAgent {
    private final String scene = "rsg/agent/nao/nao.rsg";
    private final String uniformNumber;
    private final String teamName;

    private boolean canAct;
    private final Queue<String> dumbKeyFrameMovements;

    @Override
    public void sense(SExpression perceptors) {
        this.canAct = perceptors.contains(KICK_OFF_LEFT, KICK_OFF_RIGHT, PLAYON, FREE_KICK_RIGHT);
    }

    @Override
    public String act() {
        if (canAct) {
            return dumbKeyFrameMovements.poll();
        }

        return null;
    }

    public DumbKickatronAgent(String uniformNumber, String teamName, Movement movement) {
        this.uniformNumber = uniformNumber;
        this.teamName = teamName;
        this.dumbKeyFrameMovements = movement.getFrames();
    }
}
