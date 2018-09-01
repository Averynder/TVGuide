
import java.util.NoSuchElementException;

/**
 * ShowList class used as list for TV Show objects.
 */
public class ShowList
{
    private ShowNode head;
    private int size;

    /**
     * ShowNode class
     */
    private class ShowNode

    {
        private TVShow ts1;     // this is the value we want itself
        private ShowNode next;  // this is the pointer to the next one

        /**
         * Empty constructor
         */
        ShowNode()
        {
            ts1 = null;
            next = null;
            size++;
        }

        /**
         * Parameterized constructor
         * @param ts2 Tv Show object
         * @param sn2 Tv Show object
         */
        ShowNode(TVShow ts2, ShowNode sn2) // links new node to behind one sent
        {
            if (ts2 != null)
            {
                ts1 = new TVShow(ts2.getShowID(), ts2);//ts2.clone();
                ts1.setShowIDadmin(ts2.getShowID());
            }
            else
                ts1 = null;
            next = sn2;
            size++;
        }

        /**
         * Parameterized constructor
         * @param s1 ShowNode object
         */
        ShowNode(ShowNode s1)
        {
            ts1 = new TVShow(s1.ts1.getShowID(), s1.ts1);
            ts1.setShowIDadmin(s1.ts1.getShowID());
            next = s1.next;
            size++;
        }

        /**
         * Clone method
         * @return a clone
         */
        @Override public ShowNode clone()
        {
            return new ShowNode(this);
        }

        /**
         * gets TV Show object 1
         * @return TV Show object 1
         */
        public TVShow getTs1()
        {
            return ts1;
        }

        /**
         * get the next ShowNode
         * @return the next Show Node
         */
        public ShowNode getNext()
        {
            return next;
        }

        /**
         * sets TV Show object 1
         * @return TV Show object 1
         */
        public void setTs1(TVShow ts1)
        {
            this.ts1 = ts1;
        }

        /**
         * set the next show node
         * @param next the next show node
         */
        public void setNext(ShowNode next)
        {
            this.next = next;
        }
    }

    /**
     * Parameterized constructor
     * @param original given showlist
     */
    public ShowList(ShowList original)
    {
        this(); // my new head now is null;
        ShowNode[] nodes = new ShowNode[original.size];
        ShowNode t = original.head;
        for (int i = 0; i < original.size; i++)
        {
            nodes[i] = t;
            t = t.next;
        }
        // List to be copied all references in nodes[]
        for (int i = original.size -1; i > -1; i--)
        {
            addToStart(nodes[i].ts1);
        }
    }

    /**
     * default constructor
     */
    public ShowList()
    {
        head = null;
        size = 0;
    }

    /**
     * Add to start of show list
     * @param ts given TVShow object
     */
    public void addToStart(TVShow ts)
    {
        head = new ShowNode(ts, head);
    }

    /**
     * method to insert object at an index
     * @param ts TVShow object
     * @param index index integer at which to insert the object
     * @throws NoSuchElementException catch exception
     */
    public void insertAtIndex(TVShow ts, int index) throws NoSuchElementException
    {
        if (index < 0 || index > size - 1)
            throw new NoSuchElementException("The index provided for the Linked List doesn't exist");
        else if (index == 0)
            addToStart(ts);
        else
        {
            if (ts == null)
                System.out.println("Understand that you're adding a NULL (you sent in a null TVShow)");
            ShowNode t = head;
            int count = 0;
            while (count < index - 1)
            {
                t = t.next;
                count++;
            }
            // t is now pointing to the Node just b4
            ShowNode theNewOne = new ShowNode(ts, t.next);
            t.next = theNewOne;
        }
    }

    /**
     * deletes an object from the start of the list
     */
    public void deleteFromStart()
    {
        ShowNode t = head.next;
        if (t != null)
        {
            head = t;
            size--;
        }
        else if (size == 1)
        {
            head = null;
            size--;
        }
        else if (size == 0)
            System.out.println("Can't delete anything since no elements in the Linked List");
        else
        {
            System.out.println("The size isn't 0 but \"t\" was null meaning head = null");
        }
    }

    /**
     * deletes an object from specified index
     * @param index integer corresponding to index at which to delete the entry
     * @throws NoSuchElementException catch exception
     */
    public void deleteAtIndex(int index) throws NoSuchElementException
    {
        if (index < 0 || index > size - 1)
            throw new NoSuchElementException("The index provided for the Linked List doesn't exist");
        else if (index == 0)
            deleteFromStart();
        else
        {
            ShowNode k;
            ShowNode t = head;
            int count = 0;
            while (count < index)
            {
                t = t.next;
                count++;
            }
            
            // t is now pointing to the Node to delete
            k = t.next;
            t = head;
            count = 0;
            while (count < index-1)
            {
                t = t.next;
                count++;
            }
            t.next = k;
            size--;
        }
    }

    /**
     * method confirming whether an ID is contained within
     * @param showID desired input Show ID
     * @return a boolean confirming whether an ID is contained within
     */
    public boolean contains(String showID)
    {
        if (showID == null)
            return false;
        ShowNode t = head;
        int counter = 0;
        while (counter < size)
        {
            if (t.ts1 != null && t.ts1.getShowID() != null && (t.ts1.getShowID()).equals(showID))
                return true;
            counter++;
            t = t.next;
        }
        return false;
    }

    // If this method were Public, would be a privacy leak
    // since a Node would be able to be modified outside of the safety
    // of the class / my private objects and stuff / encapsulation
    // Use showFind instead to maintain encapsulation :) and no privacy leak

    /**
     * method used to find specific show by the ID
     * @param showID given input string
     * @return a shownode corresponding to the show
     */
    private ShowNode find(String showID)
    {
        int iterations = 0;
        if (showID == null)
        {
            System.out.println("You are searching for null and thus will receive null");
            System.out.println("Please search for a valid String next time");
            return null;
        }
        ShowNode t = head;
        while (iterations < size)
        {
            if (t.ts1 != null && t.ts1.getShowID() != null && (t.ts1.getShowID()).equals(showID))
            {
                System.out.println("iterations: " + (iterations + 1));
                return t;
            }
            iterations++;
            t = t.next;
        }
        System.out.println("iterations: " + (iterations + 1));
        return null;
    }

    /**
     * method displays contents of the show
     */
    public void showContents()
    {
        if (size > 0)
        {
            ShowNode t = head;
            int counter = 0;
            while (counter < size)
            {
                System.out.println("=========================");
                System.out.println(t.ts1);
                System.out.println("=========================\n");
                t = t.next;
                counter++;
            }
        }
        else
            System.out.println("No Elements to display, nothing in List!");
    }

    /**
     * find the show and return the corresponding show
     * @param search string input to search for
     * @return the searched show
     */
    public String showFind(String search)
    {
        if (search == null)
        {
            System.out.println("Please enter a valid String (not null) to search for");
            return null;
        }
        ShowNode t = find(search);
        if (t != null && t.ts1 != null)
            return t.ts1.toString();
        else if (t != null)
            return null;
        else
            System.out.println("Couldn't find the showID: " + search);
        return null;
    }

    /**
     * replace an object at the specified index
     * @param s1 TV Show input
     * @param index index integer at which to replace the Object
     */
    public void replaceAtIndex(TVShow s1, int index)
    {
        if (index < 0 || index > size - 1)
            System.out.println("An Invalid Index was entered, can't perform the replace operation");
        ShowNode t = head;
        int count = 0;
        while (count < index)
        {
            t = t.next;
            count++;
        }
        // t pointing to the right Node
        if (s1 != null)
            t.ts1 = s1.clone();
        else
            t.ts1 = null;
    }
}
