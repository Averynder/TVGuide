/**
 * Watchable interface
 */
public interface Watchable
{
    /**
     * Method for string to see if on the same time.
     * @param s input TV Show object.
     * @return a contextual string.
     */
    String isOnSameTime(TVShow s);

    /**
     * Method which returns a boolean depending on context.
     * @param s1 input object.
     * @return a contextual boolean.
     */
    boolean equals(Object s1);
}