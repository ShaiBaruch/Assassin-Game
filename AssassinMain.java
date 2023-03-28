/**
 * Authors: Giovanni Agredano, Shai Baruch, Ruben Calderon.
 * 
 * This program is the client's interface for playing the game Assassin while using the methods from the manager program
 * it will provide the user with a list of names from a txt file called names.txt and then will prompt the user to choose 
 * a victim until there's only one player left in the kill ring, which in this case, this player will be declared as the winner.
 * with each step, the program will use the manager's methods to display the history of the game, who is in the graveyard and who is in the kill ring.
 */
import java.io.*;
import java.util.*;
public class AssassinMain//Giovanni Agredano
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to - Among Us");
        Scanner file = new Scanner(new File("names.txt"));//put your file path here
        Set<String> names = new TreeSet<String>();
        List<String> names2 = new ArrayList<String>();
        while(file.hasNextLine())                      //updates the list of names from the file into the set and the list
        {
            String name = file.nextLine();
            if (name.length() > 0 && !names.contains(name)) 
            {
                names.add(name);
                names2.add(name);
            }
        }
        AssassinManager Game = new AssassinManager(names2);
        System.out.println();
        //this is the start of the game where the input scanner will prompt the user for victims in a loop.
        while(!Game.isGameOver())//ends game when one person is left
        {
            Game.printKillRing();
            Game.printGraveyard();
            System.out.print("next victim? ");
            String VictimName = input.nextLine();
            if(Game.graveyardContains(VictimName))//if the name of the preson is dead
            {
                System.out.println(VictimName + " is already dead.");
            } 
            else if(!Game.killRingContains(VictimName))//if name not in file 
            {
                System.out.println("Unknown person.");
            } 
            else                                //if the name is in the list and not dead
            {
                Game.kill(VictimName);
            }
        }
        //print winner of game 
        System.out.println();
        System.out.println("Game was won by " + Game.winner());  //declaring the winner of the game.
        System.out.println();
        Game.printGraveyard();         //printing the final graveyard list.
    }
}