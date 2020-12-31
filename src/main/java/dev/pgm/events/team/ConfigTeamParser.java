package dev.pgm.events.team;

import dev.pgm.events.Events;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/** Fetches teams from config files TODO: elaborate on how this fetches the files*/
public class ConfigTeamParser {

  private final List<TournamentTeam> teams;

  private static ConfigTeamParser instance;

  private ConfigTeamParser() {
    teams =
        parseTournamentTeams(
            new File(Events.get().getDataFolder(), "teams"),
            new File(Events.get().getDataFolder(), "teams.yml"));
  }

  //TODO: async?
  private static List<TournamentTeam> parseTournamentTeams(File teamsFolder, File teamsFile) {
    if (!teamsFolder.exists()) teamsFolder.mkdirs();

    List<TournamentTeam> teamList = new ArrayList<>();
    for (File child :
        teamsFolder.listFiles((file) -> file.getName().toLowerCase().endsWith(".yml"))) {
      FileConfiguration config = YamlConfiguration.loadConfiguration(child);
      String teamName = config.getString("name");
      List<TournamentPlayer> players =
          config.getStringList("players").stream()
              .map(String::trim)
              .map(UUID::fromString)
              .map(x -> TournamentPlayer.create(x, true))
              .collect(Collectors.toList());

      teamList.add(TournamentTeam.create(teamName, players));
    }

    if (teamsFile.exists()) {
      YamlConfiguration teamsConfig = YamlConfiguration.loadConfiguration(teamsFile);
      for (Object object : teamsConfig.getList("teams")) {
        if (!(object instanceof Map<?, ?>)) {
          System.out.println(
              "Invalid type in teams.yml ("
                  + object.getClass().getName()
                  + ": "
                  + object.toString()
                  + ")! Skipping...");
          continue;
        }

        Map<Object, Object> team = (Map<Object, Object>) object;
        String teamName = (String) team.get("name");
        List<TournamentPlayer> players =
            ((List<String>) team.get("players"))
                .stream()
                    .map(String::trim)
                    .map(UUID::fromString)
                    .map(x -> TournamentPlayer.create(x, true))
                    .collect(Collectors.toList());

        teamList.add(TournamentTeam.create(teamName, players));
      }
    }

    return teamList;
  }

  public TournamentTeam getTeam(String name) {
    for (TournamentTeam team : teams) if (team.getName().equalsIgnoreCase(name)) return team;

    return null;
  }

  public List<TournamentTeam> getTeams() {
    return teams;
  }

  public static ConfigTeamParser getInstance() {
    if (instance == null) instance = new ConfigTeamParser();

    return instance;
  }
}
