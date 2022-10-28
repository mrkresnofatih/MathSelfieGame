package com.mrkresnofatih.mathselfieapp.services;

import com.mrkresnofatih.mathselfieapp.models.BaseFuncResponse;
import com.mrkresnofatih.mathselfieapp.models.WebSocketMessageRequestModel;
import com.pusher.rest.Pusher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService implements IWebSocketService {
    private final Pusher pusher;
    private final Logger logger;

    @Autowired
    public WebSocketService(Pusher pusher) {
        this.pusher = pusher;
        this.logger = LoggerFactory.getLogger(WebSocketService.class);
    }

    @Override
    public BaseFuncResponse<String> SendMessage(WebSocketMessageRequestModel webSocketMessageRequest) {
        logger.info(String.format("Start SendMessage: %s", webSocketMessageRequest.toJsonSerialized()));
        try {
            pusher.trigger(webSocketMessageRequest.getChannelName(), webSocketMessageRequest.getEventName(), "MESSAGE");
            return new BaseFuncResponse<>("Success", null);
        }
        catch (Exception e) {
            logger.error(e.toString());
            return new BaseFuncResponse<>("failed", "Failed when exec. Send Message");
        }
    }
}
