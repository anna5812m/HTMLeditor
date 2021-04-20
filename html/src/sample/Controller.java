package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    public HTMLEditor htmleditor;
    public TextArea textarea;
    public WebView webview;
    private Window mainStage;

    public void onOpen(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Document");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(mainStage);
        webview.getEngine().load(file.toURI().toString());
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                textarea.appendText(input.nextLine());
            }
        }
        htmleditor.setHtmlText(textarea.getText());
    }

    public void onChange(ActionEvent actionEvent) {
        textarea.setText(htmleditor.getHtmlText());
        webview.getEngine().loadContent(htmleditor.getHtmlText());
    }

    public void onSave(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Document");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(mainStage);
        SaveFile(textarea.getText(), file);
    }

    private void SaveFile(String text, File file) throws IOException {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.close();
    }
    
    public void onNew(ActionEvent actionEvent) {
        textarea.clear();
        webview.getEngine().load("");
        htmleditor.setHtmlText(" ");
    }
}
