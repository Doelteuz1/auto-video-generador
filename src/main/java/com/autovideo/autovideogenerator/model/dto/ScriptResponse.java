package com.autovideo.autovideogenerator.model.dto;

public class ScriptResponse {
    private String title;
    private String scriptText;

    // Constructor vacío
    public ScriptResponse() {
    }

    // Constructor con los dos parámetros
    public ScriptResponse(String title, String scriptText) {
        this.title = title;
        this.scriptText = scriptText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScriptText() {
        return scriptText;
    }

    public void setScriptText(String scriptText) {
        this.scriptText = scriptText;
    }
}