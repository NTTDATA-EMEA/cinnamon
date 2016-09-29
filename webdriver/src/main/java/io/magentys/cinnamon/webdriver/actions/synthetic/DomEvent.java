package io.magentys.cinnamon.webdriver.actions.synthetic;

public enum DomEvent {

    CLICK("click", "MouseEvents"), DOUBLECLICK("dblclick", "MouseEvents"), MOUSEDOWN("mousedown", "MouseEvents"), MOUSEENTER("mouseenter",
            "MouseEvents"), MOUSELEAVE("mouseleave", "MouseEvents"), MOUSEMOVE("mousemove", "MouseEvents"), MOUSEOUT("mouseout",
            "MouseEvents"), MOUSEOVER("mouseover", "MouseEvents"), MOUSEUP("mouseup", "MouseEvents"), RESIZE("resize", "UIEvents"), SCROLL("scroll",
            "UIEvents"), SELECT("select", "UIEvents");

    private final String name;
    private final String type;

    DomEvent(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
