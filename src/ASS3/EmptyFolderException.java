class EmptyFolderException extends Exception
{
    EmptyFolderException()
    {
        super("Folder was Empty");
    }

    EmptyFolderException(String s)
    {
        super("The following folder: \"" + s + "\" exists but is empty");
    }

    EmptyFolderException(String s, int i)
    {
        super("The path entered: " + s + " is not a folder");
    }
}
