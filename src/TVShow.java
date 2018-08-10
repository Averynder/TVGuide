import java.util.ArrayList;

public class TVShow implements Watchable
{
    private static int currentID = 0;
    private static ArrayList<String> showListIDs = new ArrayList<String>();
    private String showID, showName;
    private double startTime, endTime;

    public TVShow()
    {
        showName = "Law & Order";
        startTime = 15.00;
        endTime = 15.30;
        showID = "" + currentID++;
        showListIDs.add(showID);
    }

    public TVShow(TVShow tvshow1, String showID)
    {
        this.showName = tvshow1.showName;
        this.startTime = tvshow1.startTime;
        this.endTime = tvshow1.endTime;
        addID(showID);
    }

    public TVShow(String showID, String showName, double startTime, double endTime)
    {
        addID(showID);
        this.showName = showName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void addID(String showID)
    {
        boolean idTaken = false;
        for (int i = showListIDs.size(); i > 0; i--)
        {
            if (showListIDs.get(i).equals(showID))
            {
                System.out.println("The ID was taken already, thus a new one will be assigned");
                idTaken = true;
            }
        }
        if (!idTaken)
            this.showID = showID;
        else
            this.showID = "" + currentID++;
        showListIDs.add(this.showID);
    }

    private boolean sameTime(double time1, double time2)
    {
        return (Math.abs(time1 - time2) < 0.01);
    }

    private double[] initialTimeTests(TVShow s1)
    {
        boolean trialrun = true;
        double start1 = startTime;
        double end1 = endTime;
        double start2 = s1.startTime;
        double end2 = s1.endTime;

        while (trialrun)
        {
            try
            {
                // Negative Issues
                if (start1 < 0)
                    throw new TimeIssueException(1, true, showID);
                if (end1 < 0)
                    throw new TimeIssueException(1, false, showID);
                if (start2 < 0)
                    throw new TimeIssueException(1, true, s1.showID);
                if (end2 < 0)
                    throw new TimeIssueException(1, false, s1.showID);

                // FIX THIS, times < 24 hours should be allowed
                // Times are Greater than 24 hours
                if (start1 > 24.00)
                    throw new TimeIssueException(2, true, showID);
                if (start2 > 24.00)
                    throw new TimeIssueException(2, true, s1.showID);
                if (end1 > 24.00)
                    throw new TimeIssueException(3, true, showID);
                if (end2 > 24.00)
                    throw new TimeIssueException(3, true, s1.showID);

                // Times past 59 minutes
                if (start1 > Math.floor(start1) + 0.59)
                    throw new TimeIssueException(2, false, showID);
                if (start2 > Math.floor(start2) + 0.59)
                    throw new TimeIssueException(2, false, s1.showID);
                if (end1 > Math.floor(end1) + 0.59)
                    throw new TimeIssueException(3, false, showID);
                if (end2 > Math.floor(end2) + 0.59)
                    throw new TimeIssueException(3, false, s1.showID);

                // Gotten Here means no initial issues
                trialrun = false;
            }
            catch (TimeIssueException TIE)
            {
                System.out.println(TIE.getMessage());
                System.out.println("Attempting to fix issue");
                switch(TIE.getCaseNum())
                {
                    case 1:
                        if (TIE.getObjName().equals(showID))
                            if (TIE.getIsTopOrBottum())
                                start1 = Math.abs(start1);
                            else
                                end1 = Math.abs(end1);
                        else
                        if (TIE.getIsTopOrBottum())
                            start2 = Math.abs(start2);
                        else
                            end2 = Math.abs(end2);
                        break;
                    case 2:
                        if (TIE.getObjName().equals(showID))
                            if (TIE.getIsTopOrBottum())
                                while (start1 > 24.00)
                                {
                                    start1 -= 24.00;
                                }
                            else
                                while(start1 - Math.floor(start1) > 0.59)
                                {
                                    start1 += 1.00;
                                    start1 -= 0.60;
                                }
                        else
                        if (TIE.getIsTopOrBottum())
                            while (start2 > 24.00)
                            {
                                start2 -= 24.00;
                            }
                        else
                            while(start2 - Math.floor(start2) > 0.59)
                            {
                                start2 += 1.00;
                                start2 -= 0.60;
                            }
                        break;
                    case 3:
                        if (TIE.getObjName().equals(showID))
                            if (TIE.getIsTopOrBottum())
                                while (end1 > 24.00)
                                {
                                    end1 -= 24.00;
                                }
                            else
                                while(end1 - Math.floor(end1) > 0.59)
                                {
                                    end1 += 1.00;
                                    end1 -= 0.60;
                                }
                        else
                        if (TIE.getIsTopOrBottum())
                            while (end2 > 24.00)
                            {
                                end2 -= 24.00;
                            }
                        else
                            while(end2 - Math.floor(end2) > 0.59)
                            {
                                end2 += 1.00;
                                end2 -= 0.60;
                            }
                }
            }
        }

        double[] results = {start1,end1,start2,end2};
        return results;
    }

    private boolean timeOverlap(TVShow s1)
    {
        double[] results = initialTimeTests(s1);
        /*
        if (results.length == 4)
        {
            startTime = results[0];
            endTime = results[1];
            s1.startTime = results[2];
            s1.endTime = results[3];
        }
        else
        {
            System.out.println("Something went wrong in the initialTimeTests");
            return false;
        }
        */
        // Now it is known that the numbers dealt with are from 0:00 - 23:59
        // Next Step, Sort in order

        System.out.println("===========================");
        System.out.println("results[0]: " + results[0]);
        System.out.println("results[1]: " + results[1]);
        System.out.println("results[2]: " + results[2]);
        System.out.println("results[3]: " + results[3]);
        System.out.println("===========================");

        int current = 1;
        for (int count = 0; count < results.length; count++)
        {
            int j = current + 1;
            int l = current - 1;
            if(j < results.length)
            {
                if (results[current] > results[j])
                {
                    int exchange = current;
                    while (j < results.length & results[exchange] > results[j])
                    {
                        double temp = results[j];
                        results[j] = results[exchange];
                        results[exchange] = temp;
                        exchange = j;
                        j++;
                    }
                }
                else if (results[l] > results[current])
                {
                    int exchange = current;
                    while (l > -1 & results[l] > results[exchange])
                    {
                        double temp = results[exchange];
                        results[exchange] = results[l];
                        results[l] = temp;
                        exchange = l;
                        if (l != 0)
                            l--;
                    }
                }
                else
                    current++;
            }
        }
            /*
            for (int i = 0; i < results.length - 1; i++)
            {
                if (results[i] < results[i+1])
                    counter++;
            }
            
            if (counter == results.length - 2)
                isSorted = true;
            else
                counter = 0;
            System.out.println("Hit me");
            */

        System.out.println("===========================");
        System.out.println("results[0]: " + results[0]);
        System.out.println("results[1]: " + results[1]);
        System.out.println("results[2]: " + results[2]);
        System.out.println("results[3]: " + results[3]);
        System.out.println("===========================");
        // results is now sorted but FORGOT which values are which...

        // positionsInResults [0] = startTime position in results
        // positionsInResults [1] = endTime position in results
        // positionsInResults [2] = s1.startTime position in results
        // positionsInResults [3] = s1.endTime position in results
        // positionsInResults holds position in results       [0]       [1]         [2]          [3]
        int[] positionsInResults = positionFinder(results, startTime, endTime, s1.startTime, s1.endTime);

        // Now it's sorted values in results, and I know where their positions are in the Results array

        if (positionsInResults[0] == 0 || positionsInResults[2] == 0) // first num is a start
        {
            if (positionsInResults[0] == 0)         // first num is start1
                if (positionsInResults[1] == 1)     // 2nd num is start2
                    if (positionsInResults[2] == 2) // 3rd num is end1, thus NO overlapp
                        return false;
                    else                                    // first num is start2
                        if (positionsInResults[3] == 1)     // 2nd num is start2
                            if (positionsInResults[0] == 2) // 3rd num is end1, thus NO overlapp
                                return false;
        }
        else // first num is an end
        {
            if (positionsInResults[1] == 0)         // last num is start1
                if (positionsInResults[2] == 1)     // first num is end1
                    if (positionsInResults[3] == 2) // 2nd num is start2, thus NO overlapp
                        return false;
                    else                                    // first num is end2
                        if (positionsInResults[2] == 3)
                            if (positionsInResults[1] == 2)
                                return false;
        }
        return true;
    }

    private int[] positionFinder(double[] searchy, double...elements)
    {
        System.out.println("===========================");
        System.out.println("elements[0]: " + elements[0]);
        System.out.println("elements[1]: " + elements[1]);
        System.out.println("elements[2]: " + elements[2]);
        System.out.println("elements[3]: " + elements[3]);
        System.out.println("===========================");
        int positionCount = 0; // element I searching for
        boolean[] found = new boolean[searchy.length];
        for (int i = 0; i < found.length; i++)
        {found[i] = false;}

        int[] positionsInResults = new int[searchy.length];
        while (positionCount < searchy.length)
        {
            for (int i = 0; i < elements.length; i++)
            {
                if (elements[positionCount] == searchy[i] && !found[i])
                {
                    positionsInResults[positionCount] = i;
                    found[i] = true;
                    break;
                }
            }
            positionCount++;
        }
        System.out.println("===========================");
        System.out.println("position[0]: " + positionsInResults[0]);
        System.out.println("position[1]: " + positionsInResults[1]);
        System.out.println("position[2]: " + positionsInResults[2]);
        System.out.println("position[3]: " + positionsInResults[3]);
        System.out.println("===========================");
        return positionsInResults;
    }

    @Override protected TVShow clone()
    {
        return new TVShow(this, "" + currentID++);
    }

    @Override public String toString()
    {
        return ("The show's name is: " + showName + " and it's ID is: " + showID +
                ",\nit starts at: " + startTime + ", and ends at: " + endTime);
    }

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

    public String isOnSameTime(TVShow s1)
    {
        if (sameTime(this.startTime, s1.startTime) && sameTime(this.endTime, s1.endTime))
            return "Same Time";
        if (timeOverlap(s1))
            return "Some Overlap";
        return "Different Time";
    }

    public String getShowID()
    {
        return showID;
    }

    public String getShowName()
    {
        return showName;
    }

    public double getStartTime()
    {
        return startTime;
    }

    public double getEndTime()
    {
        return endTime;
    }

    public void setShowID(String showID)
    {
        this.showID = showID;
    }

    public void setShowName(String showName)
    {
        this.showName = showName;
    }

    public void setStartTime(double startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(double endTime)
    {
        this.endTime = endTime;
    }

}