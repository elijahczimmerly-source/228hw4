import java.util.Scanner;
import java.io.File;

public class MsgTree {
    public char payloadChar;
    public MsgTree left;
    public MsgTree right;

    /*Can use a static char idx to the tree string for recursive
    solution, but it is not strictly necessary*/
    private static int staticCharIdx = 0;

    //Constructor building the tree from a string
    public MsgTree(String encodingString){}

    //Constructor for a single node with null children
    public MsgTree(char payloadChar){}

    //method to print characters and their binary codes
    public static void printCodes(MsgTree root, String code){}

    //method to decode a message using the provided encoding tree
    public void decode(MsgTree codes, String msg){}

    public static void main (String[] args){
        Scanner scnr = new Scanner(System.in);
        System.out.println("Please enter a filename to decode:");
        String filename = scnr.nextLine();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File does not exist");
            return;
        }
        String encodingString = "";
        String code = "";
        Scanner fileScnr = null;
        try {
            fileScnr = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Error reading file");
            return;
        }

        if (fileScnr.hasNextLine()) {
            encodingString = fileScnr.nextLine();
        }
        // Handle cases where tree spans multiple lines (twocities.arch)
        if (fileScnr.hasNextLine()) {
            String next = fileScnr.nextLine();
            if (!(next.contains("0") || next.contains("1"))){
                encodingString += "\n" + next;
            }
        }
        MsgTree root = new MsgTree(encodingString);

        while(fileScnr.hasNextLine()){
            code += fileScnr.nextLine();
        }
        printCodes(root, code);
        root.decode(root, code);




    }
}
