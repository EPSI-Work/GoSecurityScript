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
        System.out.println(listAgent.get(0).getPathToIdentity());
    }

    public static List<Tool> getListTools(){
        List<Tool> listTools = new ArrayList<>();
        //This var is used to display in error the name of the not founded file.
        String currentLoadedFileName = "";
        try {
            currentLoadedFileName = "liste.txt";
            File listeFile = new File(App.basePath + "liste.txt");
            if(listeFile.exists()){
                Scanner myReader = new Scanner(listeFile);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();

                    String[] listElement = data.split("\\t");

                    Tool tool = new Tool(listElement[0], listElement[1]);
                    listTools.add(tool);
                }
                myReader.close();
            }else{
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier " + currentLoadedFileName + " n'existe pas.");
            e.printStackTrace();
        }
        return listTools;
    }

    private static List<String> getListAgentId(){
        List<String> listAgentId = new ArrayList<>();
        //This var is used to display in error the name of the not founded file.
        String currentLoadedFileName = "";
        try {
            currentLoadedFileName = "staff.txt";
            File staffFile = new File(App.basePath + "staff.txt");
            if(staffFile.exists()){
                Scanner myReader = new Scanner(staffFile);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    listAgentId.add(data);
                }
                myReader.close();
            }else{
                throw new FileNotFoundException();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Le fichier " + currentLoadedFileName + " n'existe pas.");
            e.printStackTrace();
        }
        return listAgentId;
    }

    public static List<Agent> getListAgent(List<Tool> listTools){
        List<Agent> listAgent = new ArrayList<Agent>();
        //This var is used to display in error the name of the not founded file.
        String currentLoadedFileName = "";
        try {
            for(String agentId : getListAgentId()){
                currentLoadedFileName = agentId + ".txt";
                File agentFile = new File(App.basePath + agentId + ".txt");
                if(agentFile.exists()){
                    Scanner myReader = new Scanner(agentFile);
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

                        currentLoadedFileName = agentId + ".jpg";
                        File identifyAgentFile = new File(App.basePath + agentId + ".jpg");
                        if(identifyAgentFile.exists()){
                            //Creation de l'agent
                            listAgent.add(new Agent(dataElements.get(0), dataElements.get(1), dataElements.get(2), dataElements.get(3), agentTools, identifyAgentFile.getPath()));
                        }else{
                            throw new FileNotFoundException();
                        }
                    }else{
                        throw new Exception("Certains champs sont manquant dans le fichier : " + agentId + ".txt");
                    }
                }else{
                    throw new FileNotFoundException();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier " + currentLoadedFileName + " n'existe pas.");
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
