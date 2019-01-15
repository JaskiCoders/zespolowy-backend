package pl.com.pollub.holo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private Base64Converter converter;

    // metoda wysylajaca informacje przy pomocy WebSc K.Mazur
    public void frameCallback(BufferedImage inputImage) {

        if (session != null && session.isOpen()) { // jezeli polaczenie jest otwarte
            try {
                session.setTextMessageSizeLimit(200000); // ustaw maksymalny limit wiadomosci
                session.sendMessage(new TextMessage("{\"value\": \"" + converter.convertImageToBase64(inputImage) +
                        "\" " + ",\"timestamp\": \"" + System.currentTimeMillis() + "\"}")); // wyslij wiadomosc zamieniajac obrazek na Base64
            } catch (Exception e) {
                log.debug("Exception during sending message: {}", e.getMessage()); // zaloguj wyjatek
            }
        }
    }


    @Override // metoda wykonywana po zestawieniy polaczenia K.Mazur
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Connection established {}", session.getId());
        this.session = session;
    }

    @Override // metoda wywolywana w momencie, gdy do naszego serwera dojdzie wiadomosc przez kanal WebSocket K.Mazur
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        if (CLOSE.equalsIgnoreCase(message.getPayload())) {
            session.close();
        } else {
            log.info("Received:" + message.getPayload());
        }
    }

}
