package sample.recorder.domain.persistence;

import sample.recorder.domain.State;

import java.time.LocalDateTime;

public class Recorder {

    private Long id;
    private String version;
    private LocalDateTime startedAt, pausedAt, stoppedAt, resumedAt;
    private State state;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getStoppedAt() {
        return stoppedAt;
    }

    public LocalDateTime getResumedAt() {
        return resumedAt;
    }

    public LocalDateTime getPausedAt() {
        return pausedAt;
    }

    public State getState() {
        return state;
    }

    public boolean is(State state) {
        return this.state.equals(state);
    }

    public void setState(State state) {
        this.state = state;
    }

    public void start(){
        this.startedAt = LocalDateTime.now();
        this.state = State.STARTED;
    }

    public void pause(){
        this.pausedAt = LocalDateTime.now();
        this.state = State.PAUSED;
    }

    public void stop() {
        this.stoppedAt = LocalDateTime.now();
        this.state = State.STOPPED;
    }

    public void resume(){
        this.stoppedAt = LocalDateTime.now();
        this.state = State.CAPTURING;
    }

}
