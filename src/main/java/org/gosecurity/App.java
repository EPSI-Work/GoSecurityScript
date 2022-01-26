package org.gosecurity;

import org.gosecurity.src.model.Agent;
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
    private static String basePath;

    public static void main( String[] args )
    {
        App.basePath = "C:\\Users\\TechnoCraft\\Documents\\Dev\\EPSI\\JAVA\\GoSecurity\\";
        List<Tool> listTool = getListTools();
        List<Agent> listAgent = getListAgent(listTool);
        System.out.println(listAgent.get(0).getTools().get(2).getLabel());
    }

    public static List<Tool> getListTools(){
        List<Tool> listTools = new ArrayList<>();
        try {
            System.out.println(App.basePath + "liste.txt");
            File myObj = new File(App.basePath + "liste.txt");
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

    private static List<String> getListAgentId(){
        List<String> listAgentId = new ArrayList<>();
        try {
            File myObj = new File(App.basePath + "staff.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                listAgentId.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return listAgentId;
    }

    public static List<Agent> getListAgent(List<Tool> listTools){
        List<Agent> listAgent = new ArrayList<Agent>();
        try {
            for(String agentId : getListAgentId()){
                File myObj = new File(App.basePath + agentId + ".txt");
                Scanner myReader = new Scanner(myObj);
                List<String> dataElements = new ArrayList<String>();
                while (myReader.hasNextLine()) {
                    //On récupere chaque ligne du fichier
                     dataElements.add(myReader.nextLine());
                }
                myReader.close();

                if(dataElements.size() > 5){
                    //Création de la liste de tools de l'agent courrant.
                    List<Tool> agentTools = new ArrayList<Tool>();
                   int nbTools = dataElements.size() - 5;
                   for (int i = 4; nbTools >= 0; i++){
                       nbTools--;
                       Tool findedTool = getToolById(listTools, dataElements.get(i));
                       if(findedTool != null){
                           agentTools.add(findedTool);
                       }else{
                           throw new Exception("Le nom du materiel : " + dataElements.get(i) + " est inconnu.");
                       }
                   }

                   //Creation de l'agent
                    listAgent.add(new Agent(dataElements.get(0), dataElements.get(1), dataElements.get(2), dataElements.get(3), agentTools));
                }else{
                    throw new Exception("Certains champs sont manquant dans le fichier : " + agentId + ".txt");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return listAgent;
    }


    private static Tool getToolById(List<Tool> listTools, String toolId){
        Tool findedTool = null;

        for(Tool tool : listTools){
            if(tool.getId().equals(toolId)){
                findedTool = tool;
            }
        }
        return findedTool;
    }
}
