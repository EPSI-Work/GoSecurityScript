package org.gosecurity.generator;

import com.google.common.hash.Hashing;
import org.gosecurity.src.model.Agent;
import org.gosecurity.src.model.Tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class PasswordGenerator {
    private List<Agent> listAgents;
    private String websitePath;

    public PasswordGenerator(List<Agent> listAgents, String websitePath){
        this.listAgents = listAgents;
        this.websitePath = websitePath;
    }

    public File generateAgentPassword(){
        File indexFile = null;
        try {
            indexFile = new File(this.websitePath + "/.htpassword");

            String content = this.createAgentPasswordLine(this.listAgents);

            indexFile.createNewFile();
            Files.write(indexFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
            System.out.println("File created");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return indexFile;
    }

    public String createAgentPasswordLine(List<Agent> listAgents){
        String content = "";
            for (Agent agent:
                    listAgents) {
                content += agent.getId() + ":" + agent.getPassword() + "\n";
            }
        return content;
    }

}
