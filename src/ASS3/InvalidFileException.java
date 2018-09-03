class InvalidFileException extends Exception
{
    InvalidFileException()
    {
        super("The file could not be located");
    }

    InvalidFileException(String s, String s1, int i)
    {
        super("The file: \"" + s + "\" could not be found at: " + s1);
    }

    InvalidFileException(String s, String s1)
    {
        super("The folder \"" + s + "\" could not be found at: " + s1);
    }

    InvalidFileException(String s, int i)
    {
        super("\"" + s + "\" is not a file");
    }

    InvalidFileException(String s, String s1, boolean b)
    {
        super("The file: \"" + s + "\" exists but is empty");
    }
}
