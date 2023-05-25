package br.com.ducco.robot.ssim.agent;

import br.com.ducco.robot.ssim.sexpr.RobocupSEXPREntry;
import br.com.ducco.robot.ssim.sexpr.SExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static br.com.ducco.robot.ssim.sexpr.RobocupSEXPREntry.GAME_STATE.PLAY_MODE.*;

@RequiredArgsConstructor
@Getter
public class DumbKickatronAgent implements KickatronAgent {
    private final String scene = "rsg/agent/nao/nao.rsg";
    private final String uniformNumber;
    private final String teamName;

    private boolean shouldKick;

    @Override
    public void sense(SExpression perceptors) {
        this.shouldKick = perceptors.contains(KICK_OFF_LEFT, KICK_OFF_RIGHT, PLAYON, FREE_KICK_RIGHT);
    }

    @Override
    public Optional<String> act() {
        if (shouldKick) {
            return Optional.of("(lle3 1)");
        }

        return Optional.empty();
    }
}
