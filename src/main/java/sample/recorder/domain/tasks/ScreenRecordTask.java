package sample.recorder.domain.tasks;

import org.jcodec.api.awt.AWTSequenceEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenRecordTask extends TimerTask {

    private final AWTSequenceEncoder encoder;
    private final Robot robot;
    private BufferedImage image;

    private boolean isStarted = false;

    public ScreenRecordTask(AWTSequenceEncoder encoder) throws AWTException {
        this.encoder = encoder;
        robot = new Robot();
    }

    @Override
    public void run() {
        RecordTimer.start();
        System.out.println("Encoding screen...");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        isStarted = true;
        while (isStarted) {
            Rectangle screenRectangle = new Rectangle(screenSize);
            image = robot.createScreenCapture(screenRectangle); //capture current screen

            try {
                encoder.encodeImage(image); //encoding image files
            } catch (IOException ex) {
                Logger.getLogger(ScreenRecordTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void finishTask() {
        isStarted = false;
        try {
            this.encoder.finish();
        } catch (IOException e) {
            throw new RuntimeException("Failed during finish task: " + e);
        }
        this.cancel();
    }


}
