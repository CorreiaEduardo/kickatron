package br.com.ducco.robot.ssim.sexpr;

public interface RobocupSEXPREntry {
    interface GAME_STATE {
        String key = "GS";
        interface PLAY_MODE {
            String KICK_OFF_RIGHT = "KickOff_Right";
            String KICK_OFF_LEFT = "KickOff_Left";
            String PLAYON = "PlayOn";
            String FREE_KICK_RIGHT = "free_kick_right";
        }
    }
}
