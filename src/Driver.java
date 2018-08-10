public class Driver
{
    public static void main(String[] args)
    {
        TVShow s1 = new TVShow();
        TVShow s2 = new TVShow();
        s2.setStartTime(15.30);
        System.out.println(s2.isOnSameTime(s1));
    }
}