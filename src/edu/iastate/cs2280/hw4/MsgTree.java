package edu.iastate.cs2280.hw4;

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
    public MsgTree(String encodingString){
        if (encodingString == null || encodingString.isEmpty()) {
            throw new IllegalArgumentException("Encoding string cannot be null or empty");
        }
        if(staticCharIdx >= encodingString.length()){
            return;
        }
        char currentChar = encodingString.charAt(staticCharIdx);
        staticCharIdx++;
        if(currentChar == '^'){
            left = new MsgTree(encodingString);
            right = new MsgTree(encodingString);
        }
        else{
            payloadChar = currentChar;
        }

    }

    //Constructor for a single node with null children
    public MsgTree(char payloadChar){
        this.payloadChar = payloadChar;
    }

    //method to print characters and their binary codes
    public static void printCodes(MsgTree root, String code){
        if(code.equals("")){
            System.out.println("character code\n-------------------------");
        }
        if(root == null) return;
        if(root.left == null && root.right == null){
            System.out.println("   " + root.payloadChar + "       " + code);
        }
        else{
            printCodes(root.left, code + "0");
            printCodes(root.right, code + "1");
        }
    }

    //method to decode a message using the provided encoding tree
    public void decode(MsgTree codes, String msg){
        //codes is the root
        MsgTree current = codes;
        System.out.println("MESSAGE:");

        for(int i = 0; i < msg.length(); i++){
            char bit = msg.charAt(i);
            if(bit == '0') current = current.left;
            else if (bit == '1') current = current.right;
            if(current.left == null && current.right == null){
                System.out.print(current.payloadChar);
                current = codes; //reset to root
            }
        }
        System.out.println();
    }

    public static void main (String[] args){
        Scanner scnr = new Scanner(System.in);
        System.out.println("Please enter a filename to decode:");
        String filename = scnr.nextLine();
        scnr.close();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File does not exist");
            return;
        }
        String fullContent = "";
        try {
            Scanner fileScnr = new Scanner(file);
            // Read everything into one string, preserving newlines
            while (fileScnr.hasNextLine()) {
                fullContent += fileScnr.nextLine() + "\n";
            }
            fileScnr.close();
        } catch (Exception e) {
            System.out.println("Error reading file");
            return;
        }

        staticCharIdx = 0;

        MsgTree root = new MsgTree(fullContent);
        String code = "";
        if (staticCharIdx < fullContent.length()) {
            // .trim() removes the extra newlines between the tree and the bits
            code = fullContent.substring(staticCharIdx).trim();
        }

        printCodes(root, "");
        root.decode(root, code);

    }
}
