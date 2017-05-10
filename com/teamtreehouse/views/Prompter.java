package com.teamtreehouse.views;
import java.util.Scanner;

public class Prompter
{
    //class vars
    private Scanner in = new Scanner(System.in);
    
    
    //class constructors
    public Prompter()
    {
        in.useDelimiter("\\n");
    }
    
    
    //class methods
    public void displayMenu(Menu menu)
    {
        //display menu
        System.out.println(menu.display());
    }
    
    public void print(String str)
    {
        //display message
        System.out.print(str);
    }
    
    public void println(String str)
    {
        //display message
        System.out.println(str);
    }
    
    public int getIntChoice(String str)
    {    
        //display request
        System.out.print(str);
        
        return in.nextInt();
    }
    
    public String getStringChoice(String str)
    {    
        //display request
        System.out.print(str);
        
        return in.next();
    }
    
    public void badSelection()
    {
        System.out.println("Invalid Selection. Please try again.");
    }
}