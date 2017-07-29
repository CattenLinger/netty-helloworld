package net.catten.netty.hello.chat.client.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import net.catten.netty.hello.chat.client.common.Services;
import net.catten.netty.hello.chat.client.services.NetworkServices;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Catten Linger on 2017/7/29.
 */
public class MainViewController extends BorderPane {
    public final static String VIEW_NAME = "/main-window.fxml";

    @FXML
    private TextArea taHistory;

    @FXML
    private TextField tfMessage;

    @FXML
    private Button btnSend;

    private NetworkServices networkServices;

    public MainViewController(){
        networkServices = Services.Network.get();
    }

    @FXML
    public void initialize(){
        btnSend.setOnAction(e -> onBtnSend());
        tfMessage.setOnKeyReleased(e ->{
            btnSend.setDisable(StringUtils.isEmpty(tfMessage.getText()));
            if(e.getCode().equals(KeyCode.ENTER)) btnSend.fire();
        });
        btnSend.setDisable(true);
    }

    public void onBtnSend(){
        String message = tfMessage.getText();
        if(StringUtils.isEmpty(message)) return;

        networkServices.sendMessage(message + "\n");
        tfMessage.clear();
    }

    public void sendTextToTextArea(String text){
        taHistory.appendText(text);
        taHistory.setScrollTop(Double.MAX_VALUE);
    }
}
