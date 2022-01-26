package org.gosecurity;

import org.gosecurity.src.model.Tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        getListTools("C:\\Users\\TechnoCraft\\Documents\\Dev\\EPSI\\JAVA\\GoSecurity\\liste.txt");
    }

    public static List<Tool> getListTools(String pathFile){
        List<Tool> listTools = new ArrayList<>();
        try {
            File myObj = new File(pathFile);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String[] listElement = data.split("\\t");

                Tool tool = new Tool(listElement[0], listElement[1]);
                listTools.add(tool);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return listTools;
    }
}
