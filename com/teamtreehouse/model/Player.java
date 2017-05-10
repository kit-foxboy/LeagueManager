package com.teamtreehouse.model;

import java.io.Serializable;
import java.lang.Integer;

public class Player implements Comparable<Player>, Serializable 
{
  //class vars
  private static final long serialVersionUID = 1L;
    
  private String mFirstName;
  private String mLastName;
  private int mHeightInInches;
  private boolean mPreviousExperience;
    
  //class constructors
  public Player(String firstName, String lastName, int heightInInches, boolean previousExperience) 
  {
    mFirstName = firstName;
    mLastName = lastName;
    mHeightInInches = heightInInches;
    mPreviousExperience = previousExperience;
  }
  
    //class propertiess
    public String getPlayerName()
    {
        return mFirstName + " " + mLastName;
    }
  public String getFirstName() 
  {
    return mFirstName;
  }

  public String getLastName() 
  {
    return mLastName;
  }

  public int getHeightInInches() 
  {
    return mHeightInInches;
  }

  public boolean isPreviousExperience() 
  {
    return mPreviousExperience;
  }
    
  //class methods
  @Override
  public int compareTo(Player other) 
  {
    //sort by last name
    int nameComp = mLastName.compareTo(other.getLastName());
    if(nameComp != 0)
        return nameComp;
      
    //sort by first name
    return mFirstName.compareTo(other.getFirstName());
  }

  @Override
  public boolean equals(Object o) 
  {
    if (this == o) return true;
    if (!(o instanceof Player)) return false;

    Player player = (Player) o;

    if (mHeightInInches != player.getHeightInInches()) return false;
    if (mPreviousExperience != player.isPreviousExperience()) return false;
    if (!mFirstName.equals(player.getFirstName())) return false;
    return mLastName.equals(player.getLastName());

  }

  @Override
  public int hashCode() 
  {
    int result = mFirstName.hashCode();
    result = 31 * result + mLastName.hashCode();
    result = 31 * result + mHeightInInches;
    result = 31 * result + (mPreviousExperience ? 1 : 0);
    
     //System.out.println("hash code: " + result); 
     return result;
  }
}
