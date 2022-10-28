package com.mrkresnofatih.mathselfieapp.services;

import com.mrkresnofatih.mathselfieapp.models.BaseFuncResponse;
import com.mrkresnofatih.mathselfieapp.models.WebSocketMessageRequestModel;

public interface IWebSocketService {
    BaseFuncResponse<String> SendMessage(WebSocketMessageRequestModel webSocketMessageRequest);
}
