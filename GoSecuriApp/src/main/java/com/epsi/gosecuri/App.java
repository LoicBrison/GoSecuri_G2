/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.gosecuri;

import com.epsi.gosecuri.Threads.AgentFilePageThread;
import com.epsi.gosecuri.Threads.HomePageThread;
import com.sun.tools.javac.Main;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author loicb
 */
public class App {
    
    public static void main (String[] args) throws IOException{
        Generator gene = new Generator();
        gene.initHtpasswd();
        gene.readStuffFile();
        gene.readStaffFile();
        gene.readAgentFile();
        HomePageThread hpThread = new HomePageThread(gene.getHtmlDirPath(),gene.getGeneratedFilesDirPath(),gene.getAgentList());
        AgentFilePageThread afpThread = new AgentFilePageThread(gene.getAgentList(),gene.getHtmlDirPath(),gene.getGeneratedFilesDirPath(),gene.getStuffList());
        hpThread.start();
        afpThread.start();
    }
    
}
