/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf_reader;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.TransferMode;

/**
 *
 * @author Jana
 */
public class FXMLDocumentController implements Initializable {  

    @FXML
    private AnchorPane dragPdf;
    @FXML
    private AnchorPane dragFile;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void dragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
        
    }
    

    @FXML
    private void dropEvent(DragEvent event) {
        int i;
        List<File> files = new ArrayList<>(event.getDragboard().getFiles());
//                System.out.println(files);
        for (File file : files) {
            
//            i = file.list().length;
//            System.out.println(file.getName()+" "+i);
            
            if(file.getName().endsWith(".pdf")) {
                System.out.println("there is pdf");
            }
            if (file.isFile()) {
                System.out.println(file.getName());
            }
            
        String[] list = file.list(new FilenameFilter() {
            @Override
            
            public boolean accept(File dir, String name) {
                if (name.endsWith(".pdf")) {
                    System.out.println(name+" jana");
                }else if(name.endsWith(".doc")){
                }else if(name.endsWith(".docx")){
                }else if(name.endsWith(".txt")){
                }else if(name.endsWith(".log")){
                }else if(name.endsWith(".xml")){}
                
                return name.endsWith(".doc");
            }
        });
        
//        File[] listOfFiles = file.listFiles();
//
//        for (int a = 0; a < listOfFiles.length; a++) {
//          if (listOfFiles[a].isFile()) {
//            System.out.println("File " + listOfFiles[a].getName());
//          } else if (listOfFiles[a].isDirectory()) {
//            System.out.println("Directory " + listOfFiles[a].getName());
//          }else if(listOfFiles[a].getName().endsWith(".pdf")){
//            System.out.println("This is pdf");
//          }
//          
//        }
        
        

        
        
    }
    }
}
