import java.util.ArrayList;

/**
 * TVShow class
 */
public class TVShow implements Watchable, Cloneable
{
    private static int currentID = 0;
    private static ArrayList<String> showListIDs = new ArrayList<String>(0);
    private String showID, showName;
    private double startTime, endTime;

    /**
     * Default constructor for the TVShow class
     */
    public TVShow()
    {
        showName = "Law & Order";
        startTime = 15.00;
        endTime = 15.30;
        showID = "" + currentID++;
        showListIDs.add(showID);
    }

    /**
     * Parameterized constructor for the TVShow class
     * @param tvshow1 input object of TVShow class
     * @param showID input string which identifies the ID of the show
     */
    public TVShow(TVShow tvshow1, String showID)
    {
        this.showName = tvshow1.showName;
        this.startTime = tvshow1.startTime;
        this.endTime = tvshow1.endTime;
        addID(showID);
    }

    /**
     *  Default constructor for the TVShow class
     * @param showID input string which identifies the ID of the show
     * @param showName input string which identifies the name of the show
     * @param startTime input double which identifies the start time of the show
     * @param endTime input double which identifies the end time of the show
     */
    public TVShow(String showID, String showName, double startTime, double endTime)
    {
        addID(showID);
        this.showName = showName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Method adds ID to the object
     * @param showID input string corresponding to the show ID
     */
    private void addID(String showID)
    {
        boolean idTaken = false;
        for (int i = showListIDs.size() - 1; i > 0; i--)
        {
            if (showListIDs.get(i).equals(showID))
            {
                System.out.print("The ID " + showID + " was taken already, thus a new one will be assigned: " /*+ currentID*/);
                idTaken = true;
            }
        }
        if (idTaken)
        {
            idTaken = false;
            showID = "" + currentID;
            //System.out.println(showID);
            do
            {
                idTaken = false;
                for (int i = showListIDs.size() - 1; i > 0; i--)
                {
                    if (showListIDs.get(i).equals(showID))
                    {
                        idTaken = true;
                        currentID++;
                    }
                }
                if (idTaken)
                    showID = "" + currentID;
            } while(idTaken);
            System.out.println(showID);
        }
        /*
        do
        {
            idTaken = false;
            for (int i = showListIDs.size() - 1; i > 0; i--)
            {
                if (showListIDs.get(i).equals(showID))
                {
                    idTaken = true;
                    currentID++;
                }
            }
            if (idTaken)
                showID = "" + currentID;
        } while(idTaken);
        */
        //if (!idTaken)
            this.showID = showID;
        /*
        else // shouldn't ever be run
        {
            System.out.println("here");
            this.showID = "" + currentID++;
            System.out.println(this.showID);//
        }
        */
        showListIDs.add(this.showID);
        
    }

    /**
     * Parameterized constructor for the TVShow class
     * @param showID input string corresponding to the show ID
     * @param tvshow1 input object corresponding to the TVShow
     */
    TVShow(String showID, TVShow tvshow1)
    {
        this.showName = tvshow1.showName;
        this.startTime = tvshow1.startTime;
        this.endTime = tvshow1.endTime;
        
        boolean idTaken = false;
        for (int i = showListIDs.size() - 1; i > 0; i--)
            if (showListIDs.get(i).equals(showID))
                idTaken = true;
        idTaken = false;
        showID = "" + currentID;
        do
        {
            idTaken = false;
            for (int i = showListIDs.size() - 1; i > 0; i--)
            {
                if (showListIDs.get(i).equals(showID))
                {
                    idTaken = true;
                    currentID++;
                }
            }
            showID = "" + currentID;
        } while(idTaken);
        if (!idTaken)
            this.showID = showID;
        else
            this.showID = "" + currentID++;
        showListIDs.add(this.showID);
    }

    /**
     * Method which returns a boolean depending on the times overlap of the of shows
     * @param time1 the start time
     * @param time2 the end time
     * @return a boolean depending on the times overlap of the of shows
     */
    private boolean sameTime(double time1, double time2)
    {
        return (Math.abs(time1 - time2) < 0.01);
    }

    /**
     * Clone method
     * @return returns a clone of the object
     */
    @Override public TVShow clone()
    {
        return new TVShow(this, "" + currentID++);
    }

    /**
     * personalized toString method
     * @return returns a string with concise and clear information of the object.
     */
    @Override public String toString()
    {
        return ("showID: " + showID + "\nshowName: " + showName +
                "\nstartTime: " + startTime + "\nendTime: " + endTime);
    }

    /**
     * returns a boolean depending on equality fo compared objects
     * @param s1 the TVShow object passed by the method
     * @return a boolean depending on equality fo compared objects
     */
    @Override public boolean equals(Object s1)
    {
        if (s1 == null)
            return false;
        if (s1.getClass() != this.getClass())
            return false;
        return (this.showName.equals(((TVShow)s1).showName) &&
                sameTime(this.startTime, ((TVShow) s1).startTime) &&
                sameTime(this.endTime, ((TVShow) s1).endTime));
    }

    /**
     * Returns a string depending on whether the shows are on the same time or not
     * @param s1  the TVShow object passed by the method
     * @return a string depending on whether the shows are on the same time or not
     */
    public String isOnSameTime(TVShow s1)
    {
        if (sameTime(this.startTime, s1.startTime) && sameTime(this.endTime, s1.endTime))
            return "Same Time";
        if (timeOverlap2(s1))
            return "Some Overlap";
        return "Different Time";
    }

    /**
     * returns a boolean depending on overlap
     * @param s1 the TVShow object passed by the method
     * @return a boolean depending on overlap
     */
    public boolean timeOverlap2(TVShow s1)
    {
        if (endTime <= s1.startTime || s1.endTime <= startTime)
            return false;
        return true;
    }

    /**
     * Method returns a string corresponding to the ShowID
     * @return a string corresponding to the ShowID
     */
    public String getShowID()
    {
        return showID;
    }

    /**
     * Method returns a string corresponding to the Show Name
     * @return a string corresponding to the Show Name
     */
    public String getShowName()
    {
        return showName;
    }

    /**
     *  Method returns a double corresponding to the Start Time
     * @return a double corresponding to the Start Time
     */
    public double getStartTime()
    {
        return startTime;
    }

    /**
     *  Method returns a double corresponding to the End Time
     * @return a double corresponding to the End Time
     */
    public double getEndTime()
    {
        return endTime;
    }

    /**
     * Method sets the ShowID
     * @param showID input user string which will set the Show ID accordingly
     */
    public void setShowID(String showID)
    {
        addID(showID);
    }

    /**
     * Method sets the ShowIDadmin
     * @param showID input user string which will set the Show ID admin accordingly
     */
    void setShowIDadmin(String showID)
    {
        this.showID = showID;
        int size = showListIDs.size() - 1;
        showListIDs.remove(size);
        currentID--;
    }

    /**
     * Method sets the showName
     * @param showName input user string which will set the Show ID accordingly
     */
    public void setShowName(String showName)
    {
        this.showName = showName;
    }

    /**
     *  Method sets the start time
     * @param startTime input user string which will set the start time accordingly
     */
    public void setStartTime(double startTime)
    {
        this.startTime = startTime;
    }

    /**
     *  Method sets the end time
     * @param endTime input user string which will set the end time accordingly
     */
    public void setEndTime(double endTime)
    {
        this.endTime = endTime;
    }

}
