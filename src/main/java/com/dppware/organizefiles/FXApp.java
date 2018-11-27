package com.dppware.organizefiles;


import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * Main entrance for FX GUI execution
 * 
 * https://gamedevelopment.tutsplus.com/es/tutorials/introduction-to-javafx-for-game-development--cms-23835
 * @author dpena
 *
 */
public class FXApp extends Application {
    
  
    @Override
    public void start(Stage primaryStage) {
        try {
            //Main Conatiner preparation
        	primaryStage.setTitle("File Organizator");
        	
        	// Read file fxml and draw interface.
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        	loader.setResources(ResourceBundle.getBundle("lang/messages", new Locale("es", "ES")));
            Parent mainView = loader.load();
            
            //Icon execution
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            Image logo  = new Image( classloader.getResourceAsStream("images/logo.png"));
            primaryStage.getIcons().add(logo);
            //Message Bundle
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setResources(ResourceBundle.getBundle("lang/messages", new Locale("es", "ES")));
//            fxmlLoader.getResources().getString("welcome");
            
            
            primaryStage.setScene(new Scene(mainView));
            primaryStage.show();
         
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}


