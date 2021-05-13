package sample.recorder.domain.persistence;

import sample.recorder.domain.LogType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    private LocalDateTime createdAt;
    private LogType logType;
    private String content;

    public Log(LocalDateTime createdAt, LogType logType, String content) {
        this.createdAt = createdAt;
        this.logType = logType;
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LogType getLogType() {
        return logType;
    }

    public String getContent() {
        return content;
    }

    public String logFormatted(){
        return String.format("| %s | - %s | - Time: %s ", logType, content,
                createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss ")));
    }
}
