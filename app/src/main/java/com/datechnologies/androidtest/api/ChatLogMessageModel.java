package com.datechnologies.androidtest.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A data model that represents a chat log message fetched from the D & A Technologies Web Server.
 */

public class ChatLogMessageModel
{

    @SerializedName("data")
    @Expose
    private List<ChatLogMessage> data;

    public List<ChatLogMessage> getData() {
        return data;
    }

    public void setData(List<ChatLogMessage> chatLogMessages) {
        this.data = chatLogMessages;
    }
}