package sample.recorder.domain;

import javafx.concurrent.Task;
import javafx.scene.control.TextArea;
import sample.recorder.domain.persistence.Log;

import java.time.LocalDateTime;

public class LogSimulator {

    public void generateLogsIn(TextArea textArea) {
        Task<Void> recordInBackground = new Task<>() {
            @Override
            protected Void call() {
                for (int i = 0; i < 20; i++) {
                    try {
                        if (isCancelled()) {
                            break;
                        }
                        Thread.sleep(1000);
                        Log log = new Log(LocalDateTime.now(), LogType.INFO, "Logging application ... ");
                        textArea.appendText(log.logFormatted() + "\n");
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }
        };
        new Thread(recordInBackground).start();
    }

}
