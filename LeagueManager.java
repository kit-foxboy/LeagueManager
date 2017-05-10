import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.views.Prompter;
import com.teamtreehouse.views.Menu;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.lang.Integer;

public class LeagueManager 
{
    //class vars
    private static Player[] players;
    private static List<Team> teams = new ArrayList<Team>();
    private static Prompter io = new Prompter();
    private static Menu mainMenu;
    private static Menu organizerMenu;
    
    public static void main(String[] args) 
    {
        //load and sort players
        players = Players.load();
        Arrays.sort(players);
        
        //init display
        Prompter io = new Prompter();
        
        //init menus
        mainMenu = new Menu(new String[] {
            "ORGANIZER",
            "COACH"
        });
        organizerMenu = new Menu(new String[] {
            "CREATE TEAM",
            "ADD PLAYERS TO TEAM",
            "REMOVE PLAYERS FROM TEAM",
            "VIEW TEAM HEIGHT REPORT",
            "LEAGUE BALANCE REPORT"
        });
        
        //main loop
        do
        {
            switch(menuLoop(mainMenu))
            {
                //end program
                case 0:
                    return;
                
                //organizer menu
                case 1:
                    organizer(menuLoop(organizerMenu));
                    break;
                
                //coach menu
                case 2:
                    
                    //check teams
                    if(teams.size() == 0)
                    {
                        io.println("No coaches are registered. Please contact a League Organizer.");
                        break;
                    }
                
                    //build coach menu
                    String[] coaches = new String[teams.size()];
                    for(int i = 0; i < teams.size(); i++)
                    {
                        coaches[i] = "LOG IN AS: " + teams.get(i).getCoach();
                    }
                    Menu coachMenu = new Menu(coaches);
                    
                    coach(menuLoop(coachMenu) - 1);
                    break;
            }
            
        } while(true);
    }
    
    private static int menuLoop(Menu menu)
    {
        //display menu
        int menuChoice = 0;
        do
        {
            //get menu choice
            io.displayMenu(menu);
            menuChoice = io.getIntChoice("Command: ");
            
            //display invalid input if needed
            if(menuChoice > menu.length())
            {
                io.badSelection();
            }
            
            else
            {
                return menuChoice;
            }
            
        } while(menuChoice > 0);
        
        return menuChoice;
    }
  
    private static void organizer(int choice)
    {
        //handle choice
        switch(choice)
        {
            //exit
            case 0:
                return;
            
            //create team
            case 1:
                createTeam();
                break;
            
            //add players to team
            case 2:
                playersToTeams();
                break;
            
            //remove players from team
            case 3:
                playersFromTeam();
                break;
            
            //print team report
            case 4:
                teamReport();
                break;
            
            //print league report
            case 5:
                leagueReport();
                break;
        }
        
        //refresh menu
        if(choice > 0) 
        {
            organizer(menuLoop(organizerMenu));
        }
    }
    
    private static void coach(int choice)
    {
        //handle choice
        if(choice < 0)
        {
            return;
        }
        
        //display team for coach
        Team team = teams.get(choice);
        
        //show roster
        teamRoster(team);
    }
    
    private static void createTeam()
    {
        //check for max players allowed
        if(Team.maxPlayers * (teams.size()+1) > players.length)
        {
            io.println("There are not enough players to add more teams.");
            return;
        }
        
        //get team name
        String teamName = io.getStringChoice("Team name: ");
        
        //get coach name
        String coachName = io.getStringChoice("Coach name: ");
        
        //add and sort team
        teams.add(new Team(teamName, coachName));
        Collections.sort(teams);
        
        //display success
        io.println("Success!");
    }
    
    private static void playersToTeams()
    {
        //check teams
        if(teams.size() == 0)
        {
            io.println("There are no teams. Create one first.");
            return;
        }
        
        //get player
        int playerChoice = menuLoop(addPlayerMenu()) - 1;
        if(playerChoice < 0)
        {
            return;
        }
        
        //check teams
        if(isPlayerOnTeam(players[playerChoice]))
        {
            io.badSelection();
            return;
        }
        
        //get team
        int teamChoice = menuLoop(getTeamMenu()) - 1;
        if(teamChoice < 0)
        {
            return;
        }
        
        //assign player
        teams.get(teamChoice).addPlayer(players[playerChoice]);
        io.println("Success!");
    }
    
    private static void playersFromTeam()
    {
        //check teams
        if(teams.size() == 0)
        {
            io.println("There are no teams. Create one first.");
            return;
        }
        
        //get team
        int teamChoice = menuLoop(getTeamMenu()) - 1;
        if(teamChoice < 0)
        {
            return;
        }
        
        //check players
        if(!teams.get(teamChoice).hasPlayers())
        {
            io.println("This team has no players. Create one first.");
            return;
        }
        
        //get player
        List<Player> playerList = teams.get(teamChoice).playersAsList(false);
        int playerChoice = menuLoop(removePlayerMenu(playerList)) - 1;
        if(playerChoice < 0)
        {
            return;
        }
        
        //remove player from team
        teams.get(teamChoice).removePlayer(playerList.get(playerChoice));
    }
    
    private static void teamReport()
    {
        //check teams
        if(teams.size() == 0)
        {
            io.println("There are no teams. Create one first.");
            return;
        }
        
        //get team
        int teamChoice = menuLoop(getTeamMenu()) - 1;
        if(teamChoice < 0)
        {
            return;
        }
        
        //check players
        if(teams.get(teamChoice).playerCount() == 0)
        {
            io.println("There are no players on this team. Add some first.");
            return;
        }
        
        //print report
        for(Player player : teams.get(teamChoice).playersAsList(true))
        {
            io.println(String.format("%s, height: %d, has experience: %b", 
                player.getPlayerName(), player.getHeightInInches(), player.isPreviousExperience()));
        }
    }
    
    private static void leagueReport()
    {
        //check teams
        if(teams.size() == 0)
        {
            io.println("There are no teams. Create one first.");
            return;
        }
        
        //print all players by team
        for(Team team : teams)
        {
            //track numbers
            int expCount = 0;
            int noExpCount = 0;
            
            //iterate players for experience
            for(Player player : team.playersAsList(false))
            {
                if(player.isPreviousExperience())
                    expCount++;
                
                else
                    noExpCount++;
            }
            
            //display report
            io.println(String.format("===%s===", team.getTeamName()));
            io.println(String.format("Players with experience: %s", expCount));
            io.println(String.format("Players without experience: %s\n", noExpCount));
        }  
    }
    
    private static void teamRoster(Team team)
    {
        //check players
        if(team.playerCount() == 0)
        {
            io.println("There are no players on this team. Please contact an organizer.");
            return;
        }
        
        //print report
        for(Player player : team.playersAsList(false))
        {
            io.println(String.format("%s, height: %d, has experience: %b", 
                player.getPlayerName(), player.getHeightInInches(), player.isPreviousExperience()));
        }
    }
    
    private static Menu addPlayerMenu()
    {
        //create new String array
        String[] playerNames = new String[players.length];
        
        //populate menu
        for(int i = 0; i < players.length; i++)
        {
            if(isPlayerOnTeam(players[i]))
            {
                playerNames[i] = String.format("%s, height: %d, has experience: %b (UNAVAILABLE)", 
                                           players[i].getPlayerName(), players[i].getHeightInInches(), players[i].isPreviousExperience());
            }
            else
            {
                playerNames[i] = String.format("%s, height: %d, has experience: %b", 
                                           players[i].getPlayerName(), players[i].getHeightInInches(), players[i].isPreviousExperience());
            }
        }
        
        return new Menu(playerNames);
    }
    
    private static Menu removePlayerMenu(List<Player> playerList)
    {
        //create new String array
        String[] playerNames = new String[playerList.size()];
        
        //populate menu
        for(int i = 0; i < playerList.size(); i++)
        {
            playerNames[i] = String.format("%s, height: %d, has experience: %b", 
                                           players[i].getPlayerName(), players[i].getHeightInInches(), players[i].isPreviousExperience());
        }
        
        return new Menu(playerNames);
    }
    
    private static Menu getPlayerMenu(List<Player> playerList)
    {
        String[] playerNames = new String[playerList.size()];
        for(int i = 0; i < players.length; i++)
        {
            playerNames[i] = String.format("%s, height: %d, has experience: %b", 
                                           players[i].getPlayerName(), players[i].getHeightInInches(), players[i].isPreviousExperience());
        }
        
        return new Menu(playerNames);
    }
    
    private static Menu getTeamMenu()
    {
        String[] teamNames = new String[teams.size()];
        for(int i = 0; i < teams.size(); i++)
        {
            teamNames[i] = teams.get(i).getTeamName();
        }
        
        return new Menu(teamNames);
    }
    
    private static boolean isPlayerOnTeam(Player player)
    {
        //search for players team
        for(Team team : teams)
        {
            if(team.checkForPlayer(player))
            {
                return true;
            }
        }
        
        return false;
    }
}
