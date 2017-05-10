package com.teamtreehouse.model;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Team implements Comparable<Team>
{
    //class vars
    private String mTeamName;
    private String mCoach;
    public static final int maxPlayers = 11;
    private Map<Integer,Player> mPlayers = new TreeMap<Integer,Player>();
    
    //class properites
    public String getTeamName() 
    { 
        return mTeamName; 
    }
    public void setTeamName(String newValue) 
    { 
        mTeamName = newValue; 
    }
    public String getCoach() 
    { 
        return mCoach; 
    }
    public Player getPlayer(int key) 
    { 
        return mPlayers.get(key); 
    }
    public List<Player> playersAsList(boolean sortByHeight)
    {
        //get list
        List players = new ArrayList<Player>(mPlayers.values());
        
        //handle sorting option
        if(sortByHeight)
        {
            Collections.sort(players, new Comparator<Player>()
            {
                public int compare(Player p1, Player p2)
                {
                    return p1.getHeightInInches() - p2.getHeightInInches();
                }
            });
        }       
        else
        {
            Collections.sort(players);
        }
        
        return players;
    }
    public boolean checkForPlayer(Player player) 
    {
        return mPlayers.containsValue(player); 
    }
    public int playerCount() 
    { 
        return mPlayers.size();
    }
    public void addPlayer(Player player) 
    { 
        mPlayers.put(player.hashCode(), player); 
    }
    public Player removePlayer(Player player) 
    { 
        return mPlayers.remove(player.hashCode()); 
    }
    public boolean hasPlayers()
    {
        return (mPlayers.size() > 0);
    }
    public boolean isTeamFull() 
    { 
        return (mPlayers.size() == maxPlayers); 
    }
    
    //class constructors
    public Team(String teamName, String coach)
    {
        mTeamName = teamName;
        mCoach = coach;
    }
    
    
    //class methods
    @Override
    public int compareTo(Team other) 
    {
    
        //sort by team name
        int nameComp = mTeamName.compareTo(other.getTeamName());
        if(nameComp != 0)
            return nameComp;
          
        //sort by coach name
        return mCoach.compareTo(other.getCoach());
    }
    
    @Override
    public String toString()
    {
        return "Team: " + mTeamName + ", Coach: " + mCoach;
    }
}