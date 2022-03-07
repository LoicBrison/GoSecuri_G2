/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.gosecuri;

import com.epsi.gosecuri.Threads.AgentFilePageThread;
import com.epsi.gosecuri.Threads.HomePageThread;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Formatter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author loicb
 */
public class Generator {
    
    //Chemin relatif pour le dossier ressource
    private final String ressourceDirPath = "src/main/java/com/epsi/gosecuri/ressourceFiles/";
    private final String htmlDirPath = "src/main/java/com/epsi/gosecuri/htmlFiles/";
    private final String generatedFilesDirPath = "src/main/java/com/epsi/gosecuri/generatedFiles/";
    private final String staffFile = "staff.txt";
    private final String stuffFile = "liste.txt";
    
    private ArrayList<Agent> agentList = new ArrayList<>();
    private ArrayList<String> agentNameList = new ArrayList<>();
    private HashMap<String,String> stuffList = new HashMap<>();
    
    public Generator() throws IOException{
        
    }
    
    /**
     * Créer la liste d'agent
     */
    public void readAgentFile(){
        //Variable pour la création d'un agent
        String nom = "";
        String prenom = "";
        String password = "";
        String mission= "";
        ArrayList<String> stuffList;
        
        //On créer un agent pour chaque Nom dans la liste this.agentNameList
        for(String name:this.agentNameList){
            try{
                stuffList = new ArrayList<>();
                
                // Le fichier d'entrée
                File lcAgentFile = new File(this.ressourceDirPath+name+".txt"); // Pour release
                
                // Créer l'objet File Reader
                FileReader fr = new FileReader(lcAgentFile);
                // Créer l'objet BufferedReader        
                BufferedReader br = new BufferedReader(fr);

                String line;
                int index = 0;
                //On boucle jusqu'a la fin du fichier
                while((line = br.readLine()) != null)
                {
                    //On saute les lignes vides
                    if(line != null || !line.isEmpty() || line.trim().isEmpty()){
                        //On rempli les différentes variables avec les 4 premières lignes
                        // et créer une liste de matériel avec les lignes restantes
                        switch(index){
                            case 0:
                                nom = line;
                                break;
                            case 1:
                                prenom = line;
                                break;
                            case 2:
                                mission = line;
                                break;
                            case 3:
                                password = line;
                                break;
                            default:
                                stuffList.add(this.stuffList.get(line.trim()));
                                break;
                        }
                        index++;
                    }
                }
                fr.close(); 
                Agent agent = new Agent(nom,prenom,password,mission,name,stuffList);
                this.generateHtpasswd(agent);
                this.agentList.add(agent);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        
        this.agentList.sort((a1,a2)
           -> a1.getIdentity().compareTo(a2.getIdentity()));
        
        //Ligne ci dessous uniquement pour debug
        System.out.println(this.agentList.toString());
    }
    
    /**
     * Créer la liste des différents matériels
     */
    public void readStuffFile(){
        try{
            // Le fichier d'entrée
            File lcStaffFile = new File(this.ressourceDirPath+this.stuffFile); // Pour releas
            // Créer l'objet File Reader
            FileReader fr = new FileReader(lcStaffFile);
            // Créer l'objet BufferedReader        
            BufferedReader br = new BufferedReader(fr);

            String line;
            //On boucle jusqu'a la fin du fichier
            while((line = br.readLine()) != null)
            {
                String name = line.split("\\s+")[0].trim();
                String value = "";
                
                //Si le nom complet du matériel est séparé par des espaces
                //on boucle jusqu'au dernier mot de la ligne
                //sinon on prend le mot après l'identifiant 
                if(line.split("\\s+").length>2){
                    for(int i=1;i<line.split("\\s+").length;i++){
                        value += line.split("\\s+")[i]+" ";
                    }
                }
                else{
                    value = line.split("\\s+")[1];
                }
                this.stuffList.put(name, value);
            }
            fr.close(); 
            
            //Ligne ci dessous uniquement pour debug
            System.out.println(this.stuffList.toString());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Créer la liste des différents agents
     */
    public void readStaffFile(){
        try{
            // Le fichier d'entrée
            File lcStaffFile = new File(this.ressourceDirPath+this.staffFile); // Pour release
            // Créer l'objet File Reader
            FileReader fr = new FileReader(lcStaffFile);
            // Créer l'objet BufferedReader        
            BufferedReader br = new BufferedReader(fr);

            String line;
            //On boucle jusqu'a la fin du fichier
            while((line = br.readLine()) != null)
            {
                this.agentNameList.add(line);
            }
            fr.close(); 
            
            //Ligne ci dessous uniquement pour debug
            System.out.println(this.agentNameList.toString());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void initHtpasswd(){
        String line = "admin"+":{SHA}"+Base64.getEncoder().encodeToString(DigestUtils.sha1("admin"));
        try {
            Files.writeString(Paths.get(this.generatedFilesDirPath+".htpasswd"),line+"\n");
        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void generateHtpasswd(Agent agent){
        //Création / ou Ecriture du fichier htpasswd
        String line = agent.getUsername()+":{SHA}"+Base64.getEncoder().encodeToString(DigestUtils.sha1(agent.getPassword()));
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(this.generatedFilesDirPath+".htpasswd",true));  //clears file every time
            output.write(line);
            output.newLine();
            output.close();
            //Files.writeString(Paths.get(this.generatedFilesDirPath+".htpasswd"),line);
        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public String getRessourceDirPath() {
        return ressourceDirPath;
    }

    public String getHtmlDirPath() {
        return htmlDirPath;
    }

    public String getGeneratedFilesDirPath() {
        return generatedFilesDirPath;
    }

    public String getStaffFile() {
        return staffFile;
    }

    public String getStuffFile() {
        return stuffFile;
    }

    public ArrayList<Agent> getAgentList() {
        return agentList;
    }

    public ArrayList<String> getAgentNameList() {
        return agentNameList;
    }

    public HashMap<String, String> getStuffList() {
        return stuffList;
    }
}
