/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf_reader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author Jana
 */
public class FXMLController implements Initializable {
 
    @FXML
    private Circle ro1;
    @FXML
    private Circle ro2;
    @FXML
    private Circle ro3;

    int rotate = 0;
    @FXML
    private Pane vline;
    @FXML
    private Pane hline;
    @FXML
    private AnchorPane apane;
    @FXML
    private Circle r1;
    @FXML
    private Circle r2;
    @FXML
    private Circle r3;
    @FXML
    private Circle r4;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       slider(r1,150,1);
       slider(r2,-100,1.2);
       slider(r3,100,1.2);
       slider(r4,-135,1);
    }    
  
    
    
    private void setRotate(Circle c, boolean reverce, int angle, int duration){
        RotateTransition rt = new RotateTransition(Duration.seconds(duration), c);
        
        rt.setAutoReverse(reverce);
        
        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(4);
        rt.setCycleCount(10);
        rt.play();          
    }
    
    public void slider(Circle r,double a,double duration  ) {

        TranslateTransition tt = new TranslateTransition();

        tt.setByX(a);
        tt.setDuration(Duration.seconds(duration));
        tt.setCycleCount(4);
        tt.setRate(0.8);
        tt.setAutoReverse(true);
        tt.setNode(r);
        tt.play();

    }

}
