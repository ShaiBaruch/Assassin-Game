
/**
 * Author: Shai Baruch
 * 
 * This program is the back end of the game Assassin, and contains all the methods needed to operate the game.
 * each method will be called from the main program that is used as the game's interface.
 * The methods are responsible of displaying the development of the game, like who is in the kill ring, who is in 
 * the graveyard, who wins, if the game is over or not, and if the graveyard and kill ring contains a certin player.
 */
import java.util.*;
public class AssassinManager
{

    //storing front nodes for the kill ring and the graveyard 
    private AssassinNode frontOfKillRing;
    private AssassinNode frontOfGraveyard;

    /** 
     * forming the kill ring from a list of names.
     * pre condition - checking to see that the list of names is not empty.
     * post condition - forming the kill ring of linked nodes that contains names from the list in the same order. 
     */
    public AssassinManager(List<String> names){
        if(names == null || names.size() == 0){          // checking if the list is not empty.
            throw new IllegalArgumentException("The list of names is empty.");
        }     
        for(int i = 0; i < names.size(); i++){           //getting the names from the list 
            String name = names.get(i);
            AssassinNode assassin = new AssassinNode(name);

            if(frontOfKillRing == null){                 //ordering the names in the kill ring by the list
                frontOfKillRing = assassin;
            } else {
                AssassinNode current = frontOfKillRing;
                while(current.next != null){
                    current = current.next;
                }
                current.next = assassin;
            }
        }
    }

    /**
     * prints the players that are currently in the kill ring and who they are stalking.
     */
    public void printKillRing(){
        System.out.println("Current kill ring:");
        AssassinNode current = frontOfKillRing;
        while(current != null){
            if(current.next == null){
                System.out.println("  " + current.name + " is stalking " + frontOfKillRing.name);
            } else {
                System.out.println("  " + current.name + " is stalking " + current.next.name);            
            }
            current = current.next; // updating current.
        }
    }

    /**
     * prints the players that are currently in the graveyard and who they were killed by.
     */
    public void printGraveyard(){
        if(!isGameOver()){ // checking if there's more than one player left in the kill ring
            System.out.println("Current graveyard:");
        } else {           // changing the headliner to represent the final graveyard list.
            System.out.println("Final graveyard is as follows:");
        }

        AssassinNode current = frontOfGraveyard;
        while(current != null){
            if(current.next == null){
                System.out.println("  " + current.name + " was killed by " + current.front);
            } else {
                System.out.println("  " + current.name + " was killed by " + current.front);            
            }
            current = current.next; // updating current.
        }
        System.out.println();
    }

    /**
     * returns true if the name that was inserted is in the kill ring  
     * and false if it is not in the kill ring.
     */
    public boolean killRingContains(String name){
        AssassinNode current = frontOfKillRing;
        while(current != null){
            if(current.name.equalsIgnoreCase(name)){   //ignoring case so the comparison will not fail due to upper/lower cases.
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * returns true if the name that was inserted is in the graveyard  
     * and false if it is not in the graveyard.
     */
    public boolean graveyardContains(String name){
        AssassinNode current = frontOfGraveyard;
        while(current != null){
            if(current.name.equalsIgnoreCase(name)){   //ignoring case so the comparison will not fail due to upper/lower cases.
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // returning true if the game is over and false if not by checking if there's only one player left in kill ring. 
    public boolean isGameOver(){
        return (frontOfKillRing.next == null);    // if frontOfKillRing is empty that means that there's only one player in the kill ring.
    }

    //returning the last name in the kill ring or null if the game is not over.
    public String winner(){           
        if(!isGameOver()){
            return null;               //returning null since there are more than one player in the kill ring.
        }
        return frontOfKillRing.name;
    }  

    /**
     * ignoring case andrecording the killing of the person with the given name and transferring 
     * the person from the kill ring to the front of the graveyard. 
     * if game is over or if the given name is not in the kill ring - throwing an exception. 
     */ 
    public void kill(String name){
        if(isGameOver()){                          //pre condition - cannot use this method when the game is over. 
            throw new IllegalStateException();
        }
        if(!killRingContains(name)){               // pre condition - checking if the name that is given exist in the kill ring
            throw new IllegalArgumentException("This player is not in the kill ring.");
        } else {
            AssassinNode victim = null;

            // post condition - removing victim from the kill ring and ordering the kill ring according to the instructions for the next round.
            if(frontOfKillRing.name.equalsIgnoreCase(name)){     //if victim is at the front of the kill ring
                String assassin = null;
                AssassinNode current = frontOfKillRing;
                while(current != null){ 
                    if(current.next == null){
                        assassin = current.name;
                    }
                    current = current.next;
                }
                victim = frontOfKillRing;
                victim.front = assassin;
                frontOfKillRing = frontOfKillRing.next;

            } else {                                        //if victim is not at the front of the kill ring
                AssassinNode current = frontOfKillRing;
                while(current.next != null){
                    if(current.next.name.equalsIgnoreCase(name)){
                        String assassin = current.name;
                        victim = current.next;
                        victim.front = assassin;
                        if(current.next.next != null){         //if somebody is after the victim 
                            current.next = current.next.next;

                            break;
                        } else {                               //if nobody is after the victim
                            current.next = null;
                            break;
                        }
                    }
                    current = current.next;
                }
            }

            // Puts victim at the front of the graveyard
            if(frontOfGraveyard != null){
                victim.next = frontOfGraveyard;
            } else {
                victim.next = null;
            }
            frontOfGraveyard = victim; 
        }
    }
}
