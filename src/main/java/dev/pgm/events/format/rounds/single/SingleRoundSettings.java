package dev.pgm.events.format.rounds.single;

import dev.pgm.events.format.Tournament;
import dev.pgm.events.format.rounds.RoundSettings;
import dev.pgm.events.format.rounds.TournamentRound;
import java.time.Duration;

public class SingleRoundSettings extends RoundSettings {

  private final Duration cycleCountdown;
  private final Duration startCountdown;

  private final String map;
  private final int score;

  public SingleRoundSettings(
      String id,
      Duration cycleCountdown,
      Duration startCountdown,
      String map,
      int score,
      boolean scoring,
      boolean showInHistory) {
    super(id, scoring, showInHistory);
    this.score = score;
    this.cycleCountdown = cycleCountdown;
    this.startCountdown = startCountdown;
    this.map = map;
  }

  @Override
  public TournamentRound newRound(Tournament format) {
    return new SingleRound(format, this);
  }

  @Override
  public TournamentRound newRound(Tournament format, String id) {
    return new SingleRoundSettings(
            id, cycleCountdown, startCountdown, map, score(), scoring(), showInHistory())
        .newRound(format);
  }

  public Duration cycleCountdown() {
    return cycleCountdown;
  }

  public Duration startCountdown() {
    return startCountdown;
  }

  public String map() {
    return map;
  }

  public int score() {
    return score;
  }
}
