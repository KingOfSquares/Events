package dev.pgm.events.ready;

import dev.pgm.events.config.EventsConfig;
import java.util.HashSet;
import java.util.Set;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.party.Party;

public class ReadyParties {

  private final Set<String> currentReadyParties = new HashSet<>();
  private String currentMatchID;

  public void preconditionsCheckMatch(Match match) {
    if (!match.getId().equals(currentMatchID)) {
      currentMatchID = match.getId();
      currentReadyParties.clear();
    }
  }

  public void ready(Party party) {
    currentReadyParties.add(party.getDefaultName());
  }

  public void unReady(Party party) {
    currentReadyParties.remove(party.getDefaultName());
  }

  public boolean isReady(Party party) {
    return currentReadyParties.contains(party.getDefaultName());
  }

  public boolean allReady(Match match) {
    // 1 due to the queue party always present
    // and 2 for queue party + observers

    int extraParties = EventsConfig.observersMustReady() ? 1 : 2;
    return currentReadyParties.size() == match.getParties().size() - extraParties;
  }
}
