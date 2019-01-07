package pl.com.pollub.holo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@EnableScheduling
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private FrameHandler frameHandler;

    @Autowired
    private FrameHandler1 frameHandler1;

    @Autowired
    private FrameHandler2 frameHandler2;

    @Autowired
    private FrameHandler3 frameHandler3;

    @Override // K.Mazur
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(frameHandler, "/frame"); // ustawienie endpointu dla websocket na adres /frame
        registry.addHandler(frameHandler1, "/frame1"); // ustawienie endpointu dla websocket na adres /frame1
        registry.addHandler(frameHandler2, "/frame2"); // ustawienie endpointu dla websocket na adres /frame2
        registry.addHandler(frameHandler3, "/frame3"); // ustawienie endpointu dla websocket na adres /frame3
    }

}
