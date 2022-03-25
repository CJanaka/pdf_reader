/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf_reader;

import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.CustomTextField;

/**
 *
 * @author Jana
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane dragPdf;
    @FXML
    private AnchorPane dragFile;
    @FXML
    private Text containBox;
    @FXML
    private JFXComboBox<?> pagePerSideBox;
    @FXML
    private JFXComboBox<?> sideCountBox;
    @FXML
    private CustomTextField pageCountBox;
    @FXML
    private ImageView img;

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
            if (file.getName().endsWith(".pdf")) {
                System.out.println("there is pdf");
            }
            if (file.isFile()) {
                System.out.println(file.getName());
            }

           file.list((File dir, String name) -> {
                if (name.endsWith(".pdf")) {
                    System.out.println(name + " jana");
                } else if (name.endsWith(".doc")) {
                } else if (name.endsWith(".docx")) {
                } else if (name.endsWith(".txt")) {
                } else if (name.endsWith(".log")) {
                } else if (name.endsWith(".xml")) {
                }
                
                return name.endsWith(".doc");
            });

//            File[] listOfFiles = file.listFiles();
//
//            for (File listOfFile : listOfFiles) {
//                if (listOfFile.isFile()) {
//                    System.out.println("File " + listOfFile.getName());
//                } else if (listOfFile.isDirectory()) {
//                    System.out.println("Directory " + listOfFile.getName());
//                } else if (listOfFile.getName().endsWith(".pdf")) {
//                    System.out.println("This is pdf");
//                }
//            }
        }
    }

    @FXML
    private void dragEnter(DragEvent event) {
        dragPdf.setStyle(" -fx-opacity: 0.2");
    }

    @FXML
    private void dragExit(DragEvent event) {
         dragPdf.setStyle(" -fx-opacity: 1");
    }

    @FXML
    private void dragEnterS(DragEvent event) {
        dragFile.setStyle(" -fx-opacity: 0.2");
    }

    @FXML
    private void dragExitS(DragEvent event) {
        dragFile.setStyle(" -fx-opacity: 1");
    }
}
