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

/**
 *
 * @author loicb
 */
public class HomePageThread implements Runnable{
    
    private Thread thread;
    private String htmlDirPath;
    private String generatedFilesDirPath;
    private ArrayList<Agent> agentList;

    public HomePageThread(String htmlDirPath, String generatedFilesDirPath, ArrayList<Agent> agentList) {
        this.htmlDirPath = htmlDirPath;
        this.generatedFilesDirPath = generatedFilesDirPath;
        this.agentList = agentList;
    }
    
    public void start(){
        if (this.thread  == null) {
           this.thread = new Thread (this);
           this.thread.start ();
        }
    }
    
    @Override
    public void run() {
        this.createHomePage();
    }
    
    /**
     * Créer la page d'accueil.
     */
    private void createHomePage(){
        try{
            //Récupération du template html
            String logo = "../ressourceFiles/GoSecuri.png";
            String htmlString = Files.readString(Paths.get(this.htmlDirPath+"template.html"));
            
            //Initialisation des variables avec le contenu à ajouter
            String title = "Accueil";
            
            String header = "<div class=\"w3-display-topleft w3-padding-large w3-xlarge\">\n" +
"                               <a href=\"index.html\"><img src=\""+logo+"\"></a>\n" +
"                           </div>";
            
            String htmlAgentList = this.createHtmlAgentList();
            String body = "<div class=\"main w3-display-container w3-animate-opacity w3-text-white\">\n" + header +
                            htmlAgentList+
                            "<div class=\"w3-display-bottomleft w3-padding-large fonct text\">\n" +
                "                Create by Loic BRISON, Tom LABOUR, Melvin ROBIN\n" +
                "            </div>\n" +
                "        </div>";
            
            //Ajoute le contenu dans la page Html
            htmlString = htmlString.replace("$title", title);
            htmlString = htmlString.replace("$body", body);
            
            //Création du fichier Html
            //File newHtmlFile = new File(this.generatedFilesDirPath+"index.html");
            //FileUtils.writeStringToFile(newHtmlFile, htmlString);
            Files.write(Paths.get(this.generatedFilesDirPath+"index.html"),htmlString.getBytes());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Créer une liste Html avec des liens pour chaque agent
     * @return 
     */
    private String createHtmlAgentList(){
        this.agentList.sort((a1,a2)
            -> a1.getIdentity().compareTo(a2.getIdentity()));
        
        String res = "<div class=\"w3-display-middle\">\n" +
        "    <h1 class=\"w3-jumbo w3-animate-top fonct text\">Liste des agents</h1>\n";
        for(Agent agent:this.agentList){
            res +=  "    <hr class=\"w3-border-grey\" style=\"margin:auto;width:40%\">\n" +
        "    <p class=\"w3-large w3-center fonct text\"><a class=\"fonct text\" href=\""+agent.getFileName()+".html\">"+agent.getIdentity()+"</a></p>\n";
        }
        res+="</div>";
        return res;
    }
}
