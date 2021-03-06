package dev.pgm.events.format;

import dev.pgm.events.format.winner.TournamentWinnerCalculation;
import dev.pgm.events.team.TournamentTeam;
import java.time.Duration;

public class TournamentRoundOptions {

  private final boolean shutdown;
  private final boolean broadcastScore;
  private final boolean announceWinner;
  private final Duration shutdownDuration;

  private final Duration defaultStartDuration;
  private final Duration defaultCycleDuration;

  private final TournamentWinnerCalculation<TournamentTeam> winnerCalculation;

  public TournamentRoundOptions(
      boolean shutdown,
      boolean broadcastScore,
      boolean announceWinner,
      Duration shutdownDuration,
      Duration defaultStartDuration,
      Duration defaultCycleDuration,
      TournamentWinnerCalculation<TournamentTeam> winnerCalculation) {
    this.shutdown = shutdown;
    this.broadcastScore = broadcastScore;
    this.announceWinner = announceWinner;
    this.shutdownDuration = shutdownDuration;
    this.defaultStartDuration = defaultStartDuration;
    this.defaultCycleDuration = defaultCycleDuration;
    this.winnerCalculation = winnerCalculation;
  }

  public boolean shouldShutdownOnEnd() {
    return shutdown;
  }

  public Duration shutdownDuration() {
    return shutdownDuration;
  }

  public boolean shouldBroadcastScore() {
    return broadcastScore;
  }

  public boolean shouldAnnounceWinner() {
    return announceWinner;
  }

  public TournamentWinnerCalculation<TournamentTeam> winnerCalculation() {
    return winnerCalculation;
  }

  public Duration defaultStartDuration() {
    return defaultStartDuration;
  }

  public Duration defaultCycleDuration() {
    return defaultCycleDuration;
  }
}
