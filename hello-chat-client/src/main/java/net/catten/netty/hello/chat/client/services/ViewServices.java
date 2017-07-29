package net.catten.netty.hello.chat.client.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.catten.netty.hello.chat.client.MainApp;
import net.catten.netty.hello.chat.client.common.EventTarget;
import net.catten.netty.hello.chat.client.common.Services;
import net.catten.netty.hello.chat.client.ui.MainViewController;

import java.io.IOException;

/**
 * Created by Catten Linger on 2017/7/29.
 */
public class ViewServices implements EventTarget<String> {

    private MainViewController mainViewController;
    private Stage mainStage;

    public void setMainStage(Stage stage) throws IOException {
        mainViewController = new MainViewController();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(MainViewController.VIEW_NAME));
        fxmlLoader.setRoot(mainViewController);
        fxmlLoader.setController(mainViewController);
        mainStage = stage;
        mainStage.setScene(new Scene(fxmlLoader.load()));

        ((NetworkServices) Services.Network.get()).registerEventListener(this);
    }

    public void launchMainWindow(){
        mainStage.show();
    }

    public void closeMainWindow(){
        mainStage.close();
    }

    public void putMessageToView(String message){
        mainViewController.sendTextToTextArea(message + "\n");
    }

    public Stage getMainStage(){
        return mainStage;
    }

    @Override
    public void trig(String o) {
        putMessageToView(o);
    }
}
