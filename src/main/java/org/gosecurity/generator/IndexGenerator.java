package org.gosecurity.generator;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.gosecurity.src.model.Agent;
import org.gosecurity.src.model.Tool;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    public File generateIndex(){
        File indexFile = null;
        try {
            indexFile = new File(this.websitePath + "/index.html");

            URL resource = getClass().getClassLoader().getResource("/template/indexMainGenerator.html");
            if (resource == null) {
                throw new IllegalArgumentException("file not found!");
            }
            InputStream is = this.getFileFromResourceAsStream("/template/indexMainGenerator.html");

            String content = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
            content = this.setValue(content, "agentNumber", String.valueOf(listAgents.size()));
            content = this.setValue(content, "materielAvailablePercent", "50");

            content = this.setValue(content, "agentTable", this.createAgentRowDataTable(listAgents));
            content = this.setValue(content, "materielTable", this.createMaterielRowDataTable(listTools));

            indexFile.createNewFile();
            Files.write(indexFile.toPath(), content.getBytes(Charsets.UTF_8));
            System.out.println("File created");
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
