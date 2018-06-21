package pl.com.pollub.holo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@Slf4j
public class FrameHandler extends TextWebSocketHandler {

    private static final String CLOSE = "CLOSE";

    private WebSocketSession session;

    public void frameCallback(BufferedImage inputImage) {

        if (session != null && session.isOpen()) {
            try {
                session.setTextMessageSizeLimit(200000);
                session.sendMessage(new TextMessage("{\"value\": \"" + convertImageToBase64(inputImage) + "\"}"));
            } catch (Exception e) {
                log.debug("Exception during sending message: {}", e.getMessage());
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Connection established {}", session.getId());
        this.session = session;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        if (CLOSE.equalsIgnoreCase(message.getPayload())) {
            session.close();
        } else {
            log.info("Received:" + message.getPayload());
        }
    }

    private String convertImageToBase64(BufferedImage inputImage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(inputImage, "png", byteArrayOutputStream);
        } catch (IOException e) {
            log.debug("Exception during converting image: {}", e.getMessage());
        }
        return DatatypeConverter.printBase64Binary(byteArrayOutputStream.toByteArray());
    }
}
