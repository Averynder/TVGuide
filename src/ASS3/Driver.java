// -----------------------------------------------------
// Assignment: 3
// Question: 1-5
// Written by: Averynder Singh 40058958
// -----------------------------------------------------
// Files once given a string are immutable / can't change
// ArrayLists are a type of OBJ thus passed by reference
// ArrayLists can't be of primitive type
// inputMismatch for nextInt() but receives String
// NumberFormatException for parse but wrong
// https://stackoverflow.com/questions/5124012/examples-of-immutable-classes
// calling pw.close() from a a diff method will still cancel the original one

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileInputStream;

/**
 * @author Avery Singh
 * @version 1.0
 * Small little program to look in a folder, obtain pictures and applying a certain style of 4
 * pics per row, CSS is applied from another file
 */
public class Driver
{
    /**
     * Method that obtains file paths of all files within a directory and adds them to a given ArrayList
     * @param data contains a file with the location of where you want to look (directory)
     * @param filepaths is an ArrayLst you want all the file paths to be stored in
     * @throws SecurityException if the File sent is not supposed to be accessed
     */
    private static void fileList(File data, ArrayList<String> filepaths) throws SecurityException
    {
        File[] stuff = data.listFiles();
        for (int i = 0; i < stuff.length; i++)
        {
            if (stuff[i].isFile())
                filepaths.add("file: " + stuff[i].getAbsolutePath());
            else if (stuff[i].isDirectory())
            {
                filepaths.add("Directory: " + stuff[i].getAbsolutePath());
                File sendy = new File(stuff[i].getAbsolutePath());
                fileList(sendy, filepaths);
            }
        }
    }

    /**
     * Method that creates a ASCII file containing all the information from a sent ArrayList
     * @param filepaths is an ArrayList containing a set of data
     * @param path contains the location of the output file you wish to write to
     */
    private static void logcreator(ArrayList<String> filepaths, String path)
    {
        PrintWriter pw = null;
        try
        {
            pw = new PrintWriter(new FileOutputStream(path));
            for (int i = 0; i < filepaths.size(); i++)
            {
                pw.println(filepaths.get(i));
            }
            System.out.println("The files have been successfully logged:");
        }
        catch(FileNotFoundException fnf)
        {
            System.out.println("The log file was misplaced during run time, please don't move the file");
        }
        finally
        {
            if (pw != null)
                pw.close();
        }
    }

    /**
     * Method made to read contents of an ASCII file from a path
     * @param path contains the path of the ASCII file you wish to read from
     * @param screen boolean containing whether or not you want to print on screen (true = yes)
     * @return an ArrayList if you don't wish to print the results on screen, null if you do
     */
    private static ArrayList<String> readlog(String path, boolean screen)
    {
        Scanner kb = null;
        try
        {
            File check = new File(path);
            if (!check.exists())
                throw new InvalidFileException(check.getName(), check.getAbsolutePath(), 1);
            if (!check.isFile())
                throw new InvalidFileException(check.getName(), 1);
            kb = new Scanner(new FileInputStream(path));
            if (!kb.hasNextLine())
                throw new InvalidFileException(check.getName(), check.getAbsolutePath(), true);
            if (screen)
            {
                while (kb.hasNextLine())
                {
                    System.out.println(kb.nextLine());
                }
            }
            else
            {
                ArrayList<String> filepaths = new ArrayList<String>();
                while (kb.hasNextLine())
                {
                    filepaths.add(kb.nextLine());
                }
                return filepaths;
            }
            System.out.println("The process has finished successfully\n");
            return null;
        }
        catch (FileNotFoundException fnf)
        {
            System.out.println("The log.txt file existed but has been moved and can't be read");
            return null;
        }
        catch (InvalidFileException ife)
        {
            System.out.println(ife.getMessage());
            return null;
        }
        finally
        {
            if (kb != null)
                kb.close();
        }
    }

    /**
     * The main method runs a menu containing 3 options, produce a file list, produce html files from
     * a file list and exit
     * @param args could be a sent in array of Strings in the case of using it when called by a web
     *             app
     */
    public static void main(String[] args)
    {
        // Menu and Links you wanna use
        String menu = "(1) List Files\n(2) Process Files\n(3) Exit\n";
        Scanner kb = new Scanner(System.in);
        boolean atMenu = true;
        String path = "./src/Data";                 // Search Directory
        String path2 = "./src/log.txt";             // Log File Created
        String path3 = "./src/assignment3.css";     // Which CSS file you want to use
        String path4 = "./src/template.html";       // Location of where template file is

        while (atMenu)
        {
            System.out.println("Please Enter a number from 1-3:\n");
            System.out.println(menu);
            try
            {
                String inputS = (kb.nextLine()).trim();
                int input = Integer.parseInt(inputS);
                switch (input)
                {
                    case 1:
                        // List Files
                        ArrayList<String> filepaths = new ArrayList<String>();
                        try
                        {
                            File data = new File(path);
                            if (!data.exists())
                                throw new InvalidFileException(data.getName(), path);
                            if (!data.isDirectory())
                                throw new EmptyFolderException(path, 1);
                            if (data.listFiles().length == 0)
                            {
                                throw new EmptyFolderException(data.getName());
                            }
                            fileList(data, filepaths);
                            logcreator(filepaths, path2);
                            readlog(path2, true);
                        }
                        catch (NullPointerException npe)
                        {
                            System.out.println("One of the folders has been moved during the process");
                        }
                        catch (InvalidFileException ife)
                        {
                            System.out.println(ife.getMessage());
                        }
                        catch (EmptyFolderException efe)
                        {
                            System.out.println(efe.getMessage());
                        }
                        catch (SecurityException se)
                        {
                            System.out.println("The files you are trying to access are hidden and/or you are not supposed to be accessing");
                        }
                        break;
                    case 2:
                        htmlProcess(path3, path2, path4, path);
                        break;
                    case 3:
                        atMenu = false;
                        break;
                    default:
                        throw new NumberFormatException(inputS);
                }
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("The number sent: " + nfe.getMessage() + " is NOT a number from 1 - 3");
            }
        }

    }

    /**
     *
     * @param path contains the CSS file location
     * @param path2 contains the location of the ASCII file containing file paths
     * @param templet contains the template html file expected to produce copies
     * @param ogFolder contains the BASE directory will the HTML files will be produced
     */
    private static void htmlProcess(String path, String path2, String templet, String ogFolder)
    {

        try
        {
            // All files are there and exist
            File css = new File(path);
            File template = new File(templet);
            File base = new File(ogFolder);
            File listy = new File(path2);
            if (!css.isFile() | !css.exists())
                throw new InvalidFileException(css.getName(), path, 1);
            if (!template.isFile() | !template.exists())
                throw new InvalidFileException(template.getName(), templet, 1);
            if (!base.isDirectory())
                throw new InvalidFileException(base.getName(), base.getAbsolutePath());
            if (!listy.exists() | !listy.isFile())
                throw new InvalidFileException(listy.getName(), listy.getAbsolutePath() ,1);
            if (css.length() == 770 && template.length() == 201)
            {
                // 1) Initialize, template file and the file list
                //    as well as remove any nussences from file paths gotten
                ArrayList<String> filepaths = readlog(path2, false);
                boolean madeDefault = false;
                for (int i = 0; i < filepaths.size(); i++)
                {
                    String pathy = null;
                    if (filepaths.get(i).contains("Directory: "))
                    {
                        pathy = filepaths.get(i).substring(11);
                    }
                    else if (filepaths.get(i).contains("file: "))
                    {
                        pathy = filepaths.get(i).substring(6);
                    }
                    else
                        throw new NullPointerException("The file: \"log.txt\" does NOT contain the original data detailing if the next path is a file or directory");

                    pathy = dotRemover(pathy);

                    // 2) Current is now the current listing, either a file or Directory
                    //    Folder: make a new HTMl file and output and input streams point to it
                    File current = new File(pathy);
                    if (!current.exists())
                        throw new InvalidFileException(current.getName(), current.getPath(), 1);
                    int wDirectory = (base.getPath()).indexOf(base.getName());
                    String writePath = ogFolder.substring(0, wDirectory);
                    File createDefaultHTML = new File(writePath + "default");
                    File defaultHTML = new File(writePath + "default.html");
                    if (current.isDirectory())
                    {
                        makeHTMLfile(writePath, current, templet);
                    }
                    else if (current.isFile() && ((current.getName().toLowerCase()).contains(".jpeg")))
                    {
                        int directory = (current.getPath()).indexOf(current.getName());
                        String filePath = pathy.substring(0, directory);
                        File folder = new File(filePath);
                        File folderHTML = new File(writePath + folder.getName() + ".html");

                        if(folder.getName().equals(base.getName()) && /*defaultHTML.exists() &&*/ madeDefault)
                        {
                            writeToHTMLfile(defaultHTML.getAbsolutePath(), current.getAbsolutePath(), base.getName());
                        }
                        else if ((folder.getName().equals(base.getName()) && !defaultHTML.exists()) || !folderHTML.exists() && folderHTML.getAbsolutePath().equals(base.getAbsolutePath() + ".html"))
                        {
                            if (!defaultHTML.exists() || !madeDefault)
                            {
                                madeDefault = true;
                                makeHTMLfile(writePath, createDefaultHTML, templet);
                                writeToHTMLfile(defaultHTML.getAbsolutePath(), current.getAbsolutePath(), base.getName());
                            }
                            else
                                makeHTMLfile(filePath, folder, templet);
                        }
                        else
                        {
                            writeToHTMLfile(folderHTML.getAbsolutePath(), current.getAbsolutePath(), base.getName());
                        }
                    }
                    else
                        System.out.println("Won't add: " + current.getName() + " since it is not a JPEG file");
                }
                System.out.println("The process has finished successfully");
            }
            else
                System.out.println("The default files may have been tampered with and thus no html files will be produced");
        }
        catch (FileNotFoundException fnf)
        {
            System.out.println("FileNotFound: " + fnf.getMessage());
            fnf.printStackTrace();
            System.out.println("The process has been terminated abruptly, returning to the main menu");
        }
        catch (InvalidFileException ife)
        {
            System.out.println(ife.getMessage());
            System.out.println("The process has been terminated abruptly, returning to the main menu");
        }
        catch(NullPointerException npe)
        {
            System.out.println("NPE: " + npe.getMessage());
            npe.printStackTrace();
            System.out.println("The process has been terminated abruptly, returning to the main menu");
        }
    }

    /**
     * Method that makes a new html file by copying the template and saving it as a new file
     * @param filePath location of where to save HTML file
     * @param newFile a File containing the name of which you want to name your .html file (directory names used)
     * @param input location of the template file to copy
     * @return String containing the last location of a Stream that was written too
     * @throws FileNotFoundException if no file can be found from input
     */
    private static String makeHTMLfile(String filePath, File newFile, String input) throws FileNotFoundException
    {
        String lastWritePath = filePath + newFile.getName() + ".html";
        /*
        String initialWritePath = filePath + newFile.getName();
        File test = new File(lastWritePath);
        if (test.exists())
        {
            int j = 0;
            File p;
            initialWritePath += "(" + j + ")";
            do
            {
                p = new File(initialWritePath + ".html");
                j++;
                initialWritePath = initialWritePath.substring(0,initialWritePath.length()-3) + "(" + j + ")";
            }
            while(p.exists());
            lastWritePath = initialWritePath + ".html";
        }
        */
        PrintWriter output = new PrintWriter(new FileOutputStream(lastWritePath));
        Scanner template = new Scanner(new FileInputStream(input));
        while (template.hasNextLine())
        {
            output.println(template.nextLine());
        }
        output.close();
        template.close();
        return lastWritePath;
    }

    /**
     * Method that adds Images in rows of 4 to the HTML file corresponding to its Directory
     * @param destination contains the HTML file where this file will be attatched to (as <img.../>)
     * @param location contains the path of the .HTML file
     * @throws FileNotFoundException if destination file doesn't exist
     */
    private static void writeToHTMLfile(String destination, String location, String base) throws FileNotFoundException
    {
        // 3) Add the .jpeg files to the .html file
        PrintWriter pw;
        destination = dotRemover(destination);
        location = dotRemover(location);
        boolean windows;
        if (location.contains(base + "/"))
            windows = false;
        else
            windows = true;
        if (location.contains(base))
        {
            int basePath = location.indexOf(base);
            location = location.substring(basePath);
        }
        Scanner sc1 = new Scanner(new FileInputStream(destination));
        int commentLine = 0;
        int bodyendLine = 0;
        int currentrowLine = 0;
        int numrows = 0;
        int currentcolumnLine = 0;
        int numcolumns = 0;
        for (int j = 0; sc1.hasNextLine(); j++)
        {
            String currentLine = sc1.nextLine();
            if (currentLine.contains("<!--"))
                commentLine = j;
            if (currentLine.contains("<div class=\"row\">"))
            {
                currentrowLine = j;
                numrows++;
            }
            if (currentLine.contains("<div class=\"column\">"))
            {
                currentcolumnLine = j;
                numcolumns++;
            }
            if (currentLine.contains("</body>"))
                bodyendLine = j;
        }
        sc1.close();
        if (commentLine == 0 || bodyendLine == 0)
            System.out.println("Returning the files have been tampered with");
        else if (numrows == 0)
        {
            ArrayList<String> contents = new ArrayList<String>();
            sc1 = new Scanner(new FileInputStream(destination));
            for (int j = 0; sc1.hasNextLine(); j++)
            {
                if (j == (commentLine + 1))
                {
                    contents.add("<div class=\"row\">");
                    contents.add("<div class=\"column\">");
                    contents.add("<img src=\"" + location + "\"/>");
                    contents.add("</div>");
                    contents.add("</div>");
                }
                contents.add(sc1.nextLine());
            }
            pw = new PrintWriter(new FileOutputStream(destination));
            for (int i = 0; i < contents.size(); i++)
            {
                pw.println(contents.get(i));
            }
            sc1.close();
            pw.close();
        }
        else if (numcolumns%4 == 0)
        {
            ArrayList<String> contents = new ArrayList<String>();
            sc1 = new Scanner(new FileInputStream(destination));
            for (int j = 0; sc1.hasNextLine(); j++)
            {
                if (j == (bodyendLine - 1) && !windows)
                {
                    contents.add("<div class=\"row\">");
                    contents.add("<div class=\"column\">");
                    contents.add("<img src=\"" + location + "\"/>");
                    contents.add("</div>");
                    contents.add("</div>");
                }
                else if (j == (bodyendLine) && windows)
                {
                    contents.add("<div class=\"row\">");
                    contents.add("<div class=\"column\">");
                    contents.add("<img src=\"" + location + "\"/>");
                    contents.add("</div>");
                    contents.add("</div>");
                }
                contents.add(sc1.nextLine());
            }
            pw = new PrintWriter(new FileOutputStream(destination));
            for (int i = 0; i < contents.size(); i++)
            {
                pw.println(contents.get(i));
            }
            sc1.close();
            pw.close();
        }
        else if (numcolumns > 0)
        {
            ArrayList<String> contents = new ArrayList<String>();
            sc1 = new Scanner(new FileInputStream(destination));
            for (int j = 0; sc1.hasNextLine(); j++)
            {
                if (j == (currentcolumnLine + 3))
                {
                    contents.add("<div class=\"column\">");
                    contents.add("<img src=\"" + location + "\"/>");
                    contents.add("</div>");
                }
                contents.add(sc1.nextLine());
            }
            pw = new PrintWriter(new FileOutputStream(destination));
            for (int i = 0; i < contents.size(); i++)
            {
                pw.println(contents.get(i));
            }
            sc1.close();
            pw.close();
        }
    }
    
    /**
     * Method that removes any paths containing a folder moved back from previously
     * @param input is a string containing the AbsoluteFilePath
     * @return a String with the new path without the "."
     */
    private static String dotRemover(String input)
    {
        int annoyingDot1 = input.indexOf("/./");
        int annoyingDot2 = input.indexOf("\\.\\");
        if (annoyingDot1 != -1)
        {
            String part1 = input.substring(0, annoyingDot1);
            String part2 = input.substring(annoyingDot1 + 2);
            input = part1 + part2;
        }
        else if (annoyingDot2 != -1)
        {
            String part1 = input.substring(0, annoyingDot2);
            String part2 = input.substring(annoyingDot2 + 2);
            input = part1 + part2;
        }
        return input;
    }
}
