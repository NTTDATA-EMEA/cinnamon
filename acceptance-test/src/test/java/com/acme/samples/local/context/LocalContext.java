package com.acme.samples.local.context;

import com.google.common.base.Stopwatch;

public class LocalContext {

    private Stopwatch stopWatch;
    private boolean alertPresent;

    public void setTimer(Stopwatch stopWatch) {
        this.stopWatch = stopWatch;
    }

    public Stopwatch getTimer() {
        return stopWatch;
    }

    public void setAlertPresent(boolean alertPresent) {
        this.alertPresent = alertPresent;
    }

    public boolean isAlertPresent() {
        return alertPresent;
    }
}
