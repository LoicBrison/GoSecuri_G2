/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.gosecuri.Threads;

import com.epsi.gosecuri.Agent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author loicb
 */
public class AgentFilePageThread implements Runnable{
    
    private Thread thread;
    private ArrayList<Agent> agentList;
    private String htmlDirPath;
    private String generatedFilesDirPath;
    private HashMap<String,String> stuffList;

    public AgentFilePageThread(ArrayList<Agent> agentList, String htmlDirPath, String generatedFilesDirPath, HashMap<String, String> stuffList) {
        this.agentList = agentList;
        this.htmlDirPath = htmlDirPath;
        this.generatedFilesDirPath = generatedFilesDirPath;
        this.stuffList = stuffList;
    }
    
    public void start(){
        if (this.thread  == null) {
           this.thread = new Thread (this);
           this.thread.start ();
        }
    }
    
    @Override
    public void run() {
        this.createAgentFilePage();
    }
    
     /**
     * Créer les différentes fiches d'agents
     */
    private void createAgentFilePage(){
        for(Agent agent:this.agentList){
            try{
                //Récupération du template html
                String logo = "../ressourceFiles/GoSecuri.png";
                String htmlString = Files.readString(Paths.get(this.htmlDirPath+"template.html"));

                //Initialisation des variables avec le contenu à ajouter
                String title = agent.getIdentity()+" - Fiche agent";

                String header = "<div class=\"w3-display-topleft w3-padding-large w3-xlarge\">\n" +
"                               <a href=\"index.html\"><img src=\""+logo+"\"></a>\n" +
"                           </div>";

                 String body = "<div class=\"main w3-display-container w3-animate-opacity w3-text-white\">\n" + header +
                            this.createAndLoadStuffList(agent)+
                            "<div class=\"w3-display-bottomleft w3-padding-large fonct text\">\n" +
                "                Create by Loic BRISON, Tom LABOUR, Melvin ROBIN\n" +
                "            </div>\n" +
                "        </div>";

                //Ajoute le contenu dans la page Html
                htmlString = htmlString.replace("$title", title);
                htmlString = htmlString.replace("$body", body);

                //Création du fichier Html
                Files.write(Paths.get(this.generatedFilesDirPath+agent.getFileName()+".html"),htmlString.getBytes());
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
     /**
     * Créer une liste de checkBox initialiser 
     * avec les valeurs de l'agent passer en paramètre
     * @param agent
     * @return 
     */
    private String createAndLoadStuffList(Agent agent){
        String htmlString=agent.generateAgentFile();
        ArrayList<String> agentStuffList = agent.getStuffList();
        String stuffList="<ul>";
        int index=0;
        for(String stuff:this.stuffList.keySet()){
            if(agentStuffList.contains(this.stuffList.get(stuff))){
                stuffList += "<li>\n" +
"                        \n" +
"                        <input class=\"form-check-input bg-success \" type=\"checkbox\" value=\"\" id=\"defaultCheck"+index+"\" disabled checked>\n" +
"                        <label class=\"form-check-label fonct text\" for=\"defaultCheck"+index+"\">\n" +
"                            "+this.stuffList.get(stuff)+"\n" +
"                        </label>\n" +
"                          \n" +
"                    </li>";
            }else{
                stuffList += "<li>\n" +
"                        \n" +
"                        <input class=\"form-check-input bg-success \" type=\"checkbox\" value=\"\" id=\"defaultCheck"+index+"\" disabled>\n" +
"                        <label class=\"form-check-label fonct text\" for=\"defaultCheck"+index+"\">\n" +
"                            "+this.stuffList.get(stuff)+"\n" +
"                        </label>\n" +
"                          \n" +
"                    </li>";
            }
            index++;
        }
        htmlString = htmlString.replace("$stufflist", stuffList);
        return htmlString;
    }
}
