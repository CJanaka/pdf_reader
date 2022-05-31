/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdf_reader.controls;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Jana
 */
public class FXMLController implements Initializable {


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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        slider(r1, 150, 1);
        slider(r2, -100, 1.2);
        slider(r3, 100, 1.2);
        slider(r4, -135, 1);
        new SplashScreen().start();
    }

    class SplashScreen extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(6000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(SplashScreen.this.getClass().getResource("FXMLDocument.fxml"));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("Document Counter v1.0  By: CJ INNOVS");
                        Image image = new Image("/pdf_reader/styles/paper.png");
                        stage.getIcons().add(image);
                        stage.show();
                        apane.getScene().getWindow().hide();
                    }
                });
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    private void setRotate(Circle c, boolean reverce, int angle, int duration) {
        RotateTransition rt = new RotateTransition(Duration.seconds(duration), c);

        rt.setAutoReverse(reverce);

        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(4);
        rt.setCycleCount(10);
        rt.play();
    }

    public void slider(Circle r, double a, double duration) {

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
