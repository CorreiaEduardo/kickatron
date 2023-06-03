package br.com.ducco.robot.ssim.agent;

import br.com.ducco.robot.ssim.sexpr.SExpression;

import java.util.Optional;

public interface KickatronAgent {
    String getScene();
    String getUniformNumber();
    String getTeamName();

    void sense(SExpression perceptors);
    String act();

}
