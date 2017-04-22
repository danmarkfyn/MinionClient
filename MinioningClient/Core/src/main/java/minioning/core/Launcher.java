package minioning.core;

/**
 *
 * @author Jakob
 */
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.ToggleGroup;
import static minioning.common.data.EventData.clearEventData;
import static minioning.common.data.Events.CREATEACCOUNT;
import static minioning.common.data.Events.LOGIN;
import static minioning.common.data.Events.PLAY;
import minioning.common.data.LocalData;
import org.openide.util.Exceptions;

public class Launcher extends Application {

    private final int height = 200;
    private final int width = 310;
    private static final BooleanProperty serverToken = new SimpleBooleanProperty();
    private static StringProperty name = new SimpleStringProperty();
    private final ToggleGroup tg = new ToggleGroup();

    @Override
    public void start(Stage primaryStage) throws Exception {

        serverToken.setValue(Boolean.FALSE);

        LauncherLogic launcher = new LauncherLogic();

        Group root = new Group();

        //***TAB SETUP***
        //Tap pane
        TabPane tp = new TabPane();

        //Tap selector
        SingleSelectionModel<Tab> selectionModel = tp.getSelectionModel();

        // Tap1
        Tab tab1 = new Tab("Login");
        tab1.closableProperty().set(false);

        // Tap2
        Tab tab2 = new Tab("Create Account");
        tab2.closableProperty().set(false);

        // Tap3
        Tab tab3 = new Tab("Create Player");
        tab3.closableProperty().set(false);

        // Tap4
        Tab tab4 = new Tab("Play Minioning");
        tab4.closableProperty().set(false);

        // Pane
        BorderPane pane = new BorderPane();

        // fonts
        Font font1 = Font.font("Serif", 40);
        Font font2 = Font.font("Time New Roman", 12);

        // Buttons
        Button loginBtn = new Button("Login");

        Button logoutBtn = new Button("Logout");

        Button createAvatarBtn = new Button("Create");
        createAvatarBtn.setFont(font2);

        Button playBtn = new Button("Play Minioning");

        Button createAccountBtn = new Button("Create Account");

        // Textfields
        TextField avatarnameField = new TextField();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        TextField createNameField = new TextField();
        TextField createPassField = new TextField();

        // radiobuttons
        RadioButton rb1 = new RadioButton("Local");
        RadioButton rb2 = new RadioButton("Internet");

        rb1.isSelected();

        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);

        // Initial Field Values
        usernameField.setText("hit");
        passwordField.setText("me");

        avatarnameField.setText("Player");

        // Labels
        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password:  ");

        Label createNameLabel = new Label("Username: ");
        Label createPassLabel = new Label("Password:  ");
        Label avatarnameLabel = new Label("Avatar Name:");
        Label titelLabel = new Label("The Minioning");
        Label nameLabel = new Label(name.getValue());
        titelLabel.setFont(font1);

        //Color
        titelLabel.setTextFill(Color.CADETBLUE);

        // ***OnAction***
        // Logout button action
        logoutBtn.setOnAction((v) -> {

            try {
                name.setValue("");
                serverToken.setValue(Boolean.FALSE);
                LocalData.setClientID(null);
                clearEventData();

            } catch (Exception e) {

            }

        });

        // Radio buttons action
        rb1.setOnAction((v) -> {

            LocalData.setInetAddress("localhost");
            System.out.println(LocalData.getInetAddress());
        });

        rb2.setOnAction((v) -> {

            LocalData.setInetAddress("10.126.24.199");
            System.out.println(LocalData.getInetAddress());
        });

        // Login button action
        loginBtn.setOnAction((v) -> {

            String username = launcher.nameCheck(usernameField.getText());

            String password = launcher.nameCheck(passwordField.getText());

            if (username != null && password != null) {
                try {
                    launcher.accountQuery(LOGIN, username, password);
//                    
                    TimeUnit.MILLISECONDS.sleep(2000);

                    if (LocalData.getClientID() != null) {
                        serverToken.setValue(Boolean.TRUE);
                        name.setValue("Logged in as " + username);
                        LocalData.setUser(username);
                        selectionModel.select(2);

                    } else {

                        launcher.promt("Login Timeout or try with different account info", "Error");
                        serverToken.setValue(Boolean.FALSE);
                    }

                } catch (InterruptedException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            usernameField.clear();
            passwordField.clear();
        });

        // Play button action
        playBtn.setOnAction((v) -> {

            launcher.play(LocalData.getClientID(), PLAY);

            primaryStage.close();
        });

        // create account action
        createAccountBtn.setOnAction((v) -> {
            String tempName = launcher.nameCheck(createNameField.getText());

            String tempPassword = launcher.nameCheck(createPassField.getText());

            if (tempName != null && tempPassword != null) {

                launcher.accountQuery(CREATEACCOUNT, tempName, tempPassword);
                createNameField.clear();
                createPassField.clear();

            }

        });

        // create avatar button action
        createAvatarBtn.setOnAction((v) -> {
            String avatarName = avatarnameField.getText();
            String name = launcher.nameCheck(avatarName);

            if (name != null) {
                try {
                    launcher.CreatePlayer(name);
                } catch (Exception e) {
                }
                avatarnameField.clear();
                selectionModel.select(3);
            }
        });

        // setup tab1 content
        HBox t1_hb1 = new HBox(15);
        HBox t1_hb2 = new HBox(15);

        VBox t1_vb1 = new VBox();

        t1_hb1.getChildren().addAll(usernameLabel, usernameField, rb1);
        t1_hb2.getChildren().addAll(passwordLabel, passwordField, rb2);

        t1_vb1.getChildren().addAll(t1_hb1, t1_hb2, loginBtn, nameLabel, logoutBtn);

        // setup tab2 content
        HBox t2_hb1 = new HBox(15);
        HBox t2_hb2 = new HBox(15);

        VBox t2_vb1 = new VBox();

        t2_hb1.getChildren().addAll(createNameLabel, createNameField);
        t2_hb2.getChildren().addAll(createPassLabel, createPassField);

        t2_vb1.getChildren().addAll(t2_hb1, t2_hb2, createAccountBtn);

        // binds
        tab2.disableProperty().bind(serverToken);
        tab3.disableProperty().bind(serverToken.not());
        tab4.disableProperty().bind(serverToken.not());

        usernameField.disableProperty().bind(serverToken);
        passwordField.disableProperty().bind(serverToken);
        rb1.disableProperty().bind(serverToken);
        rb2.disableProperty().bind(serverToken);
        loginBtn.disableProperty().bind(serverToken);

        logoutBtn.disableProperty().bind(serverToken.not());

        nameLabel.textProperty().bind(name);

        // setup tab3 content
        HBox t3_hb1 = new HBox(15);

        VBox t3_vb1 = new VBox();

        t3_hb1.getChildren().addAll(avatarnameLabel, avatarnameField);

        t3_vb1.getChildren().addAll(t3_hb1, createAvatarBtn);

        // setup tab 3 content
        tab1.setContent(t1_vb1);
        tab2.setContent(t2_vb1);
        tab3.setContent(t3_vb1);
        tab4.setContent(playBtn);
        tp.getTabs().addAll(tab1, tab2, tab3, tab4);

        pane.setTop(titelLabel);
        pane.setBottom(tp);

        playBtn.setPrefSize(this.width, 100);

        root.getChildren().addAll(pane);
        Scene scene = new Scene(root, width, height, Color.GAINSBORO);

        primaryStage.setOnCloseRequest((v) -> {
            System.out.println("Closing");
        });

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(this.getClass().getName());

        primaryStage.show();
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        Application.launch(args);
    }
}
