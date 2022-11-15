package weg.net.tester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import weg.net.tester.models.SessionModel;
import weg.net.tester.repositories.SessionRepository;
import weg.net.tester.utils.SessionUtil;

@Controller
public class SessionController {
    private SimpMessagingTemplate template;

    @Autowired
    void WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }
    @Autowired
    private SessionRepository sessionRepository;

    @MessageMapping("/startSession")
    public void startSession(SessionModel session){
        try {
            SessionUtil.sessionModel = session;
            this.template.convertAndSend("/feedback", "Ok");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            this.template.convertAndSend("/feedback", "Not ok");
        }
    }

    @MessageMapping("/endSession")
    public void endSession(){
        try {
            sessionRepository.save(SessionUtil.sessionModel);
            this.template.convertAndSend("/feedback", "Ok");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            this.template.convertAndSend("/feedback", "Not ok");
        }
    }
}
