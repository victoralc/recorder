package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jcodec.api.awt.AWTSequenceEncoder;
import sample.recorder.domain.persistence.Recorder;
import sample.recorder.domain.tasks.ScreenRecordTask;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class RecorderMain extends Application {

    private Button btnStart;
    private TextArea textArea;
    private AWTSequenceEncoder encoder;

    private Recorder recorder;
    private ScreenRecordTask backgroundTask;

    ExecutorService executorService;

    public static void main(String[] args) {
        launch();
    }

    public void init() throws AWTException, IOException {
        this.recorder = new Recorder();
        File file = new File("outputVideo.mp4");
        AWTSequenceEncoder encoder = AWTSequenceEncoder.createSequenceEncoder(file, 24 / 4);
        this.backgroundTask = new ScreenRecordTask(encoder);
    }

    @Override
    public void start(Stage stage) {
        this.textArea = new TextArea();

        VBox vbox = new VBox();
        this.btnStart = new Button("Start Record");
        Button btnPause = new Button("Pause");
        Button btnStop = new Button("Stop");

        btnStart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnPause.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnStop.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        btnStart.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            startRecord();
            btnStart.setDisable(true);
            btnPause.setDisable(false);
            btnStop.setDisable(false);
        });

        btnPause.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            pause();
            btnStart.setDisable(false);
            btnPause.setDisable(true);
            btnStop.setDisable(false);
        });

        btnStop.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            stop();
            btnStart.setDisable(false);
            btnPause.setDisable(false);
            btnStop.setDisable(true);
        });

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Settings");
        Menu menuView = new Menu("View");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);

        Scene scene = new Scene(vbox, 640, 480);

        TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
        tileButtons.setPadding(new Insets(10, 10, 10, 10));
        tileButtons.setHgap(10.0);
        tileButtons.setVgap(10.0);
        tileButtons.getChildren().addAll(btnStart, btnPause, btnStop);

        Label status = new Label("Status");
        status.setPadding(new Insets(10, 10, 10, 10));

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, textArea, tileButtons, status);

        //stage.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> System.out.println("Mouse click..."));
        //stage.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> System.out.println("Mouse drag..."));
        //stage.addEventFilter(MouseEvent.MOUSE_MOVED, e -> System.out.println("Moving mouse..."));

        stage.setScene(scene);
        stage.show();
    }

    public void startRecord() {
        this.recorder.start();
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.submit(this.backgroundTask);
    }

    public void pause() {
        System.out.println("Pausing background task");
        this.recorder.pause();
    }

    public void stop() {
        System.out.println("Stop background task");
        this.recorder.stop();
        this.backgroundTask.finishTask();
        this.executorService.shutdownNow();
    }

}
