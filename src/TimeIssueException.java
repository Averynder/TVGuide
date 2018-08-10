public class TimeIssueException extends Exception
{
    private String message, objName;
    private int caseNum;
    private boolean topOrBottum;

    public int getCaseNum()
    {
        return caseNum;
    }

    public boolean getIsTopOrBottum()
    {
        return topOrBottum;
    }

    public String getObjName()
    {
        return objName;
    }

    public TimeIssueException()
    {
        message = "A Time Exception has occurred";
        caseNum = 0;
        topOrBottum = false;
        objName = "";
    }

    public TimeIssueException(String s1)
    {
        message = s1;
        caseNum = 0;
        topOrBottum = false;
        objName = "";
    }

    /**
     * i = 1, negative time, b = true => start, b = false => end
     * @param i
     * @param b
     * @param s
     */
    public TimeIssueException(int i, boolean b, String s)
    {
        this();
        caseNum = i;
        topOrBottum = b;
        objName = s;

        switch (i)
        {
            case 1:
                if (b)
                    message = "The Start time was given as a negative value";
                else
                    message = "The End time was given as a negative value";
                break;
            case 2:
                if (b)
                    message = "The Start time's hours have surpassed 23";
                else
                    message = "The Start time's minutes have surpassed 59";
                break;
            case 3:
                if (b)
                    message = "The End time's hours have surpassed 23";
                else
                    message = "The End time's minutes have surpassed 59";
                break;
        }
        message += " for the object: " + s;
    }

    @Override public String getMessage()
    {
        return message;
    }
}