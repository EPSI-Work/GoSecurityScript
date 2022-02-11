package org.gosecurity.generator;

import org.gosecurity.App;
import org.gosecurity.src.model.Agent;
import org.gosecurity.src.model.Tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class IndexGenerator {
    private List<Agent> listAgents;
    private List<Tool> listTools;
    private String websitePath;

    public IndexGenerator(List<Agent> listAgents, List<Tool> listTools, String websitePath){
        this.listAgents = listAgents;
        this.listTools = listTools;
        this.websitePath = websitePath;
    }

    private String setValue(String content, String varName, String value){
        return content.replaceAll("\\$\\{" + varName +"?\\}", value);
    }

    public File generateIndex(){
        File indexFile = null;
        try {

            File directory = new File("generatedFile");
            if (!directory.exists()){
                directory.mkdir();
                // If you require it to make the entire directory path including parents,
                // use directory.mkdirs(); here instead.
            }
            indexFile = new File(this.websitePath + "/index.html");
            Path path = Paths.get(System.getProperty("user.dir") + "/template/indexMainGenerator.html");
            Charset charset = StandardCharsets.UTF_8;

            String content = new String(Files.readAllBytes(path), charset);
            content = this.setValue(content, "agentNumber", String.valueOf(listAgents.size()));
            content = this.setValue(content, "materielAvailablePercent", "50");

            content = this.setValue(content, "agentTable", this.createAgentRowDataTable(listAgents));
            content = this.setValue(content, "materielTable", this.createMaterielRowDataTable(listTools));

            indexFile.createNewFile();
            Files.write(indexFile.toPath(), content.getBytes(charset));
        } catch (java.io.IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return indexFile;
    }

    public String createAgentRowDataTable(List<Agent> listAgents){
        String dataTableRowHTML = "";
        try {
            Path path = Paths.get(System.getProperty("user.dir") + "/template/indexAgentTableGenerator.html");
            Charset charset = StandardCharsets.UTF_8;

            for (Agent agent:
                    listAgents) {
                String content = new String(Files.readAllBytes(path), charset);
                content = this.setValue(content, "agentLastname", agent.getLastname());
                content = this.setValue(content, "agentFirstname", agent.getFirstname());
                content = this.setValue(content, "agentPageId", agent.getId());

                dataTableRowHTML = dataTableRowHTML + content;
            }

        } catch (java.io.IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return dataTableRowHTML;
    }

    public String createMaterielRowDataTable(List<Tool> listTools){
        String dataTableRowHTML = "";
        try {
            Path path = Paths.get(System.getProperty("user.dir") + "/template/indexAgentTableGenerator.html");
            Charset charset = StandardCharsets.UTF_8;

            for (Tool tool:
                    listTools) {
                String content = new String(Files.readAllBytes(path), charset);
                content = this.setValue(content, "materielName", tool.getLabel());

                dataTableRowHTML = dataTableRowHTML + content;
            }

        } catch (java.io.IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return dataTableRowHTML;
    }
}
