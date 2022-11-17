package com.datechnologies.androidtest.api;


import com.datechnologies.androidtest.api.ChatLogMessage;
import com.datechnologies.androidtest.api.ChatLogMessageModel;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;


public interface ChatApiInterface {

    @GET("chat_log.php")
    Call<ChatLogMessageModel> getChatData();

}
