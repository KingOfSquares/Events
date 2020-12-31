package dev.pgm.events.commands.providers;

import dev.pgm.events.TournamentManager;
import dev.pgm.events.format.Tournament;
import dev.pgm.events.format.rounds.format.FormatRound;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import org.bukkit.command.CommandSender;
import tc.oc.pgm.lib.app.ashcon.intake.argument.ArgumentException;
import tc.oc.pgm.lib.app.ashcon.intake.argument.CommandArgs;
import tc.oc.pgm.lib.app.ashcon.intake.bukkit.parametric.provider.BukkitProvider;
import tc.oc.pgm.lib.app.ashcon.intake.parametric.ProvisionException;

public class TournamentProvider implements BukkitProvider<Tournament> {

  private final TournamentManager tournamentManager;

  public TournamentProvider(TournamentManager tournamentManager) {
    this.tournamentManager = tournamentManager;
  }

  @Override
  public boolean isProvided() {
    return true;
  }

  @Override
  public Tournament get(
      CommandSender commandSender, CommandArgs commandArgs, List<? extends Annotation> list)
      throws ArgumentException, ProvisionException {
    Optional<Tournament> tournamentFormat = tournamentManager.currentTournament();
    if (tournamentFormat.isPresent()) {
      Tournament format = tournamentFormat.get();
      if (format.currentRound() == null) return format;

      if (format.currentRound() instanceof FormatRound)
        format = ((FormatRound) format.currentRound()).formatTournament();

      if (format == null)
        format = tournamentFormat.get(); // FormatTournamentImpl = null after round ends

      return format;
    }

    throw new ArgumentException("No tournament is currently running!");
  }
}
