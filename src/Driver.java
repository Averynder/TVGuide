import java.util.NoSuchElementException;

/**
 * Driver class
 */
public class Driver
{
    /**
     * Main method for the driver class.
     * @param args input arguments
     */
    public static void main(String[] args)
    {
        TVShow s1 = new TVShow();
        TVShow s2 = new TVShow();
        s1.setStartTime(15.30);
        s2.setStartTime(15.30);
        TVShow s3 = new TVShow("2","VMania",0.00,2.30);
        TVShow s10 = s3.clone();
        
        // Addition
        ShowList sl = new ShowList();
        sl.addToStart(s1);
        sl.addToStart(s2);
        sl.addToStart(s3);
        sl.addToStart(s10);
        
        sl.showContents();
        System.out.println("Now adding s4: ");
        TVShow s4 = new TVShow("7","VMania",2.00,3.30);
        
        try
        {
            sl.insertAtIndex(s4, 0);
        }
        catch (NoSuchElementException nsee)
        {
            System.out.println(nsee.getMessage());
        }
        sl.addToStart(null);
        sl.showContents();
        System.out.println(s1.isOnSameTime(s2));
        
        // Deletion
        System.out.println("Deletion:");
        sl.deleteFromStart();
        sl.showContents();
        System.out.println("Deleting from Index:");
        sl.deleteAtIndex(2);
        sl.showContents();
        
        // Finding & Replacing
        System.out.println(sl.contains("2"));
        System.out.println(sl.contains("a"));
        System.out.println();
        System.out.println(sl.showFind("2"));
        System.out.println();
        TVShow s5 = new TVShow();
        System.out.println("Replacing: ");
        sl.replaceAtIndex(s5,2);
        sl.showContents();
        System.out.println("Deleting the item that was just replaced\n");
        sl.deleteAtIndex(2);
        sl.showContents();
        
        // Equivalence
        System.out.println("============ New List ============");
        ShowList sl1 = new ShowList(sl);
        sl1.showContents();
        System.out.println("Deleted: ");
        sl1.deleteFromStart();
        sl1.showContents();
        sl1.deleteFromStart();
        System.out.println("Added");
        sl1.addToStart(s1);
        sl1.showContents();
        System.out.println("Adding again: ");
        sl1.insertAtIndex(s1,0);
        sl1.showContents();
    }
}
