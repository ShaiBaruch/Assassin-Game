
/**
 * Authors: Giovanni Agredano, Shai Baruch, Ruben Calderon.
 * 
 * This class is the storage program for a given name from the list in the file
 * and it will change every time the main program prompts the user for a name.
 * These variables will be used by the manager methods of which are called by the main program.
 * Front which represents the killer of a victim is initialized to null and will be replaced
 * by the name of the killer for each specific victim. 
 */
public class AssassinNode
{
    public String name;//name
    public String front;//representing the killer of String name.
    public AssassinNode next;//next name according to the list.
    public AssassinNode(String name)//constructs node with a name
    {
        this(name, null);
    }
    //constructs node with name, next (according to list) and null for front.
    public AssassinNode(String name, AssassinNode next)
    {
        this.name = name;
        this.next = next;
        this.front = null;
    }
}
