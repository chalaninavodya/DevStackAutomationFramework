package com.devstack.automation.constants;

public enum SeleniumErrorMessages {
    CLICK("No such element to click "),
    TYPE("No such element to type ")
    ;

    private final String message;

    SeleniumErrorMessages(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
