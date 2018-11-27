package com.dppware.organizefiles.viewControllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.dppware.organizefiles.events.core.IFileEventListener;
import com.dppware.organizefiles.services.OrganizeFilesService;
import com.dppware.organizefiles.utils.Constants;
import com.dppware.organizefiles.utils.FileType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class MainViewController implements Initializable, IFileEventListener {
	 
	   @FXML
	   private Button startButton;
	   
	   @FXML
	   private Button sourceDirFileChooseButton;
	   
	   @FXML
	   private Button destinationDirFileChooseButton;
	   
	   @FXML
	   private TextField sourceDirText;
	   
	   @FXML
	   private TextField destDirText;
	  
	   @FXML
	   private CheckBox includeImagesCheck;
	   
	   @FXML
	   private CheckBox includeVideoCheck;

	   @FXML
	   private CheckBox includeAudioCheck;
	   
	   @FXML
	   private TextArea eventTextArea;
	   
	   
	   private OrganizeFilesService organizeFilesService = new OrganizeFilesService();
	  
	   @Override
	   public void initialize(URL location, ResourceBundle resources) {
		   	//only for programming listener, instead defined on .fxml file 
		   organizeFilesService.addListener(this);
	   }
	 
	   
	   /**
	    * When is selected the OutPut directory
	    * @param event
	    */
	   public void destinationDirFileChoose(ActionEvent event) {
		   DirectoryChooser dirChooser = new DirectoryChooser();
		   dirChooser.setTitle("Output Directory");
		   File selected = dirChooser.showDialog(destinationDirFileChooseButton.getScene().getWindow());
		   destDirText.setText(selected.getAbsolutePath());
		   
	   }
	   
	   /**
	    * EventHandler for sourceDirFileChooseButton click
	    * @param event
	    */
	   public void sourceDirFileChoose(ActionEvent event) {
		   DirectoryChooser dirChooser = new DirectoryChooser();
		   dirChooser.setTitle("Open Resource Directory");
		   File selected = dirChooser.showDialog(sourceDirFileChooseButton.getScene().getWindow());
		   sourceDirText.setText(selected.getAbsolutePath());
	   }
	   
	   /**
	    * Show file chooser
	    * @param event
	    */
	   public void showFileChooser(ActionEvent event) {
		   System.out.println("hay click");
		   FileChooser fileChooser = new FileChooser();
		   fileChooser.setTitle("Open Resource File");
		   fileChooser.showOpenDialog(sourceDirText.getScene().getWindow());
	   }
	   
	   /**
	    * Event Handler for startButton click
	    */
	   public void start() {
		   try {
			   Alert alert = new Alert(AlertType.CONFIRMATION, "Lets Start!", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			   alert.showAndWait();
	
			   if (alert.getResult() == ButtonType.YES) {
				   organizeFilesService.setSourceFilesRootDir(sourceDirText.getText());
				   organizeFilesService.setDestinationRootDir(destDirText.getText());
				   if(includeImagesCheck.isSelected())organizeFilesService.addAcceptedFileTypes(FileType.IMAGE);
				   if(includeVideoCheck.isSelected())organizeFilesService.addAcceptedFileTypes(FileType.VIDEO);
				   if(includeAudioCheck.isSelected())organizeFilesService.addAcceptedFileTypes(FileType.AUDIO);
				   
				   organizeFilesService.start();
			   }else {
				   System.out.println(alert.getResult());
			   }
		   }catch (Exception e) {
			   e.printStackTrace();
			   Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
			   alert.showAndWait();
		   }
	   }
	   /**
	    * Event handler for isAvailableCheck
	    * @param event
	    */
	   public void includeFileTypes(ActionEvent event) {
		   
		   String msgNotSelectedtmpl = "Any %s File will be managed. Are you sure?";
		   String msg = "";
		   CheckBox node = (CheckBox)event.getSource();
		   if(!node.isSelected()) {
			   if (node.getId().equals(includeImagesCheck.getId())) {
				   msg = String.format(msgNotSelectedtmpl, "image");
				   
			   }else if (node.getId().equals(includeVideoCheck.getId())) {
				   msg = String.format(msgNotSelectedtmpl, "video");
				   
			   }else if (node.getId().equals(includeAudioCheck.getId())) {
				   msg = String.format(msgNotSelectedtmpl, "audio");
				   
			   } 
			   
			   Alert alert = new Alert(AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.CANCEL);
			   alert.showAndWait();
			   if(alert.getResult() == ButtonType.CANCEL) {
				   node.setSelected(!node.isSelected()); //toogle UNDO
			   }
		   }
		   
		   
	   }


	   /**
	    * Add new line to text area log info
	    */
		@Override
		public void onFileGeneratedEvent(String log) {
			eventTextArea.appendText(log+Constants.newLineChar);
		}

		/**
		 * show alert warning
		 */
		@Override
		public void onError(String log) {
			Alert alert = new Alert(AlertType.WARNING, log, ButtonType.CLOSE);
			alert.showAndWait();
		}


		@Override
		public void onFinish() {
			Alert alert = new Alert(AlertType.WARNING, "The proccess is finish.", ButtonType.CLOSE);
			alert.showAndWait();
		}
	   


}
