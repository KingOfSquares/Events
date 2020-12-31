package dev.pgm.events.format.rounds.format;

import dev.pgm.events.format.Tournament;
import dev.pgm.events.format.rounds.RoundSettings;
import dev.pgm.events.format.rounds.TournamentRound;
import java.util.ArrayList;
import java.util.List;

public class FormatRoundSettings extends RoundSettings {

  private String name;
  private List<RoundSettings> rounds;
  private int bestOf;

  public FormatRoundSettings(String id, String name, List<RoundSettings> rounds, int bestOf) {
    super(id, true, false);
    this.name = name;
    this.rounds = rounds;
    this.bestOf = bestOf;
  }

  public String name() {
    return name;
  }

  public List<RoundSettings> roundSettings() {
    return rounds;
  }

  public int bestOf() {
    return bestOf;
  }

  @Override
  public TournamentRound newRound(Tournament format) {
    return new FormatRound(format, this);
  }

  @Override
  public TournamentRound newRound(Tournament format, String id) {
    return new FormatRoundSettings(id, name, new ArrayList<RoundSettings>(rounds), bestOf)
        .newRound(format);
  }
}
