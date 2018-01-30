package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.board.Board;
import main.crossword.Advanced;
import main.crossword.Crossword;
import main.operations.DisplayCrossword;
import main.operations.SaveCrossword;

import javax.swing.*;
import java.io.FileInputStream;
import java.util.LinkedList;



//Klasa odpowiedzialna za wyświetlenie krzyżówki - składa się z trzech scen: scena główna (startowa),
//scena Advance (gdzie generowana jest skomplikowana krzyżówka) oraz scena Basic (odpowiadająca za wyświetlenie podstawowej wersji krzyżówki)

public class View {

    int size = 10;
    public void Scene1(Stage stage, Model model){
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Pane root = new Pane();
        root.setStyle("-fx-background-color: white;");
        stage.setTitle("Crossword Generator");

        Text hi = new Text("CROSSWORD GENERATOR");
        hi.setLayoutX(120);
        hi.setLayoutY(270);
        hi.setFill(Color.BLACK);
        hi.setStyle("-fx-font: 48 ubuntu;");
        root.getChildren().add(hi);

        /*Image image;
        try{
            image = new Image(new FileInputStream("/home/kartytko/Pulpit/JAVA/CrosswordGenerator/src/main/noback_black.png"));
            final ImageView selectedImage = new ImageView();
            selectedImage.setImage(image);
            selectedImage.setLayoutX(180);
            selectedImage.setLayoutY(180);
            root.getChildren().add(selectedImage);
        }catch(Exception e){
            e.getStackTrace();
        }*/


        Button basic = new Button();
        basic.setLayoutX(300);
        basic.setLayoutY(300);
        basic.setText("Basic");
        basic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Basic(stage, new Model(10));
            }
        });

        Button advanced = new Button();
        advanced.setLayoutX(450);
        advanced.setLayoutY(300);
        advanced.setText("Advanced");
        advanced.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Advanced(stage, new Model(10));
            }
        });

        Button uploadCrossword = new Button();
        uploadCrossword.setLayoutX(580);
        uploadCrossword.setLayoutY(550);
        uploadCrossword.setText("Upload your own crossword");
        uploadCrossword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                f.showSaveDialog(null);

                System.out.println(f.getSelectedFile().toString());
                String path = f.getSelectedFile().toString();
                DisplayCrossword displayCrossword = new DisplayCrossword(path);
                Crossword chosen_crossword = displayCrossword.read();

                if (chosen_crossword.getROEntryIter().hasNext()){
                    if(displayCrossword.strat.equals(DisplayCrossword.strategy.BASIC)){
                        Model new_model = new Model(displayCrossword.size);
                        new_model.setCrossword(chosen_crossword);
                        Basic(stage, new_model);
                    }else if(displayCrossword.strat.equals(DisplayCrossword.strategy.ADVANCED)){
                        Model new_model = new Model(displayCrossword.size);
                        new_model.setCrossword(chosen_crossword);
                        Advanced(stage, new_model);
                    }
                }else{
                    System.out.println("coś nie pykło");
                }
            }
        });


        root.getChildren().addAll(basic, advanced, uploadCrossword);
        Scene scene1 = new Scene(root, 800, 600);
        stage.setScene(scene1);
        stage.show();
    }








    public void Basic(Stage stage, Model model){
        Pane root = new Pane();
        //root.setStyle("-fx-background-color: mintcream;");

        LinkedList<Rectangle> list = model.GenerateBasic();
        for(int i=0; i<list.size(); i++){
            root.getChildren().add(list.get(i));
        }

        LinkedList<Text> list_of_texts = model.list_of_texts_basic;
        for(int i=0; i<list_of_texts.size();i++){
            root.getChildren().add(list_of_texts.get(i));
        }


        Button resolve = new Button();
        resolve.setText("Resolve");
        resolve.setLayoutX(1060);
        resolve.setLayoutY(750);
        resolve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(root.getChildren().contains(model.resolve_basic.get(0))){
                    for(int i = 0; i<model.resolve_basic.size(); i++){
                        root.getChildren().remove(model.resolve_basic.get(i));
                    }
                }else{
                    for(int i = 0; i<model.resolve_basic.size(); i++){
                        root.getChildren().add(model.resolve_basic.get(i));
                    }
                }
            }
        });


        Button back = new Button();
        back.setText("Back");
        back.setLayoutX(1130);
        back.setLayoutY(750);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene1(stage, model);
            }
        });

        Button save_crossword = new Button();
        save_crossword.setText("Save Crossword");
        save_crossword.setLayoutX(855);
        save_crossword.setLayoutY(750);
        root.getChildren().add(save_crossword);
        save_crossword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                f.showSaveDialog(null);

                String path_to_chosen_dir = f.getSelectedFile().toString();
                SaveCrossword save = new SaveCrossword(path_to_chosen_dir);
                Text cw_saved;
                if(save.write(model.crossword)){
                    cw_saved = new Text("Crossword has been saved successfully");
                }else{
                    cw_saved = new Text("An error has occured");
                }

                cw_saved.setLayoutX(850);
                cw_saved.setLayoutY(730);
                root.getChildren().add(cw_saved);
            }
        });


        ObservableList<String> items = FXCollections.observableArrayList (
                "8", "9", "10", "11", "12");
        ChoiceBox<String> choiceBoz = new ChoiceBox<>(items);
        choiceBoz.setPrefWidth(40);
        choiceBoz.setPrefHeight(25);
        choiceBoz.getSelectionModel().select(2);
        root.getChildren().add(choiceBoz);
        choiceBoz.setLayoutX(1130);
        choiceBoz.setLayoutY(710);

        Text setSize = new Text("Set Size");
        setSize.setLayoutX(1130);
        setSize.setLayoutY(700);
        root.getChildren().add(setSize);

        Button chose_your_dict = new Button();
        chose_your_dict.setText("Select your dictionary");
        chose_your_dict.setLayoutX(695);
        chose_your_dict.setLayoutY(750);
        root.getChildren().add(chose_your_dict);
        chose_your_dict.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                f.showSaveDialog(null);

                model.path_to_selected_dictionary = f.getSelectedFile().toString();
            }
        });



        Button generate = new Button();
        generate.setText("Generate");
        generate.setLayoutX(980);
        generate.setLayoutY(750);
        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!choiceBoz.getValue().isEmpty()){
                    size = Integer.parseInt(choiceBoz.getValue());
                }else{
                    size = 10;
                }
                Model new_model = new Model(size);

                if(!model.path_to_selected_dictionary.equals("")){
                    new_model.path_to_selected_dictionary = model.path_to_selected_dictionary;
                }

                Basic(stage, new_model);
            }
        });



        Button print = new Button();
        print.setText("Print");
        print.setLayoutX(1125);
        print.setLayoutY(650);
        print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PrinterJob job = PrinterJob.createPrinterJob();
                if(job != null){
                    boolean success = job.printPage(root);
                    if(success){
                        job.endJob();
                    }
                }
            }
        });


        root.getChildren().add(print);
        root.getChildren().add(resolve);
        root.getChildren().add(back);
        root.getChildren().add(generate);
        Scene basic = new Scene(root, 1200, 800);
        stage.setScene(basic);
        stage.show();
    }










    public void Advanced(Stage stage, Model model){
        Pane root = new Pane();
        //root.setStyle("-fx-background-color: mintcream;");
        LinkedList<Rectangle> list = model.GenerateAdvance();
        for(int i=0; i<list.size(); i++){
            root.getChildren().add(list.get(i));
        }

        LinkedList<Text> list_of_texts = model.list_of_texts;
        for(int i=0; i<list_of_texts.size();i++){
            root.getChildren().add(list_of_texts.get(i));
        }


        Button resolve = new Button();
        resolve.setText("Resolve");
        resolve.setLayoutX(1060);
        resolve.setLayoutY(750);
        resolve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(root.getChildren().contains(model.resolve.get(0))){
                    for(int i = 0; i<model.resolve.size(); i++){
                        root.getChildren().remove(model.resolve.get(i));
                    }
                }else{
                    for(int i = 0; i<model.resolve.size(); i++){
                        root.getChildren().add(model.resolve.get(i));
                    }
                }
            }
        });


        Button back = new Button();
        back.setText("Back");
        back.setLayoutX(1130);
        back.setLayoutY(750);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene1(stage, model);
            }
        });

        Button save_crossword = new Button();
        save_crossword.setText("Save Crossword");
        save_crossword.setLayoutX(855);
        save_crossword.setLayoutY(750);
        root.getChildren().add(save_crossword);
        save_crossword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                f.showSaveDialog(null);

                String path_to_chosen_dir = f.getSelectedFile().toString();
                SaveCrossword save = new SaveCrossword(path_to_chosen_dir);
                Text cw_saved;
                if(save.write(model.crossword)){
                    cw_saved = new Text("Crossword has been saved successfully");
                }else{
                    cw_saved = new Text("An error has occured");
                }

                cw_saved.setLayoutX(850);
                cw_saved.setLayoutY(730);
                root.getChildren().add(cw_saved);
            }
        });



        ObservableList<String> items = FXCollections.observableArrayList (
                "8", "9", "10", "11", "12", "13");
        ChoiceBox<String> choiceBoz = new ChoiceBox<>(items);
        choiceBoz.setPrefWidth(40);
        choiceBoz.setPrefHeight(25);
        choiceBoz.getSelectionModel().select(2);
        root.getChildren().add(choiceBoz);
        choiceBoz.setLayoutX(1130);
        choiceBoz.setLayoutY(710);

        Text setSize = new Text("Set Size");
        setSize.setLayoutX(1130);
        setSize.setLayoutY(700);
        root.getChildren().add(setSize);



        Button chose_your_dict = new Button();
        chose_your_dict.setText("Select your dictionary");
        chose_your_dict.setLayoutX(695);
        chose_your_dict.setLayoutY(750);
        root.getChildren().add(chose_your_dict);
        chose_your_dict.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                f.showSaveDialog(null);

                model.path_to_selected_dictionary = f.getSelectedFile().toString();
            }
        });




        Button generate = new Button();
        generate.setText("Generate");
        generate.setLayoutX(980);
        generate.setLayoutY(750);
        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!choiceBoz.getValue().isEmpty()){
                    size = Integer.parseInt(choiceBoz.getValue());
                }else{
                    size = 10;
                }
                Model new_model = new Model(size);

                if(!model.path_to_selected_dictionary.equals("")){
                    new_model.path_to_selected_dictionary = model.path_to_selected_dictionary;
                }

                Advanced(stage, new_model);
            }
        });


        Button print = new Button();
        print.setText("Print");
        print.setLayoutX(1125);
        print.setLayoutY(650);
        print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PrinterJob job = PrinterJob.createPrinterJob();
                if(job != null){
                    boolean success = job.printPage(root);
                    if(success){
                        job.endJob();
                    }
                }
            }
        });


        root.getChildren().add(print);
        root.getChildren().add(resolve);
        root.getChildren().add(back);
        root.getChildren().add(generate);
        //WritableImage image = root.snapshot(new SnapshotParameters(), null);
        Scene advanced = new Scene(root, 1200, 800);
        stage.setScene(advanced);
        stage.show();
    }






}
