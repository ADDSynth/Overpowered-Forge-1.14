package addsynth.core.util.game;

import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;

public final class ScoreUtil {

  public static final Score getScore(final MinecraftServer server, final String player_name, final String objective_name){
    final Scoreboard scoreboard = server.getScoreboard();
    final ScoreObjective objective = scoreboard.getObjective(objective_name);
    final Score score = scoreboard.getOrCreateScore(player_name, objective);
    return score;
  }

}
