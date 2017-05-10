package com.teamtreehouse.views;

public class Menu
{
    //class vars
    private String[] mChoices;
    
    //class constuctors
    public Menu(String[] choices)
    {
        mChoices = choices;
    }
    
    
    //class methods
    public int length()
    {
        return mChoices.length;
    }
    
    public String display()
    {
        String menu = "";
        
        //display exit option
        menu += "===================\n";
        menu += "0: GO BACK\n";
        
        //display menu
        for(int i = 1; i <= mChoices.length ; i++)
        {
            menu += i + ": " + mChoices[i-1] + "\n";
        }
        
        return menu;
    }
}