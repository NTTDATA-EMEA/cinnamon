package io.magentys.cinnamon.webdriver;

public enum ReadyState {

    UNINITIALIZED("uninitialized"), LOADING("loading"), INTERACTIVE("interactive"), COMPLETE("complete");

    private final String loadingStatus;

    ReadyState(String loadingStatus) {
        this.loadingStatus = loadingStatus;
    }

    public String getLoadingStatus() {
        return loadingStatus;
    }

}
