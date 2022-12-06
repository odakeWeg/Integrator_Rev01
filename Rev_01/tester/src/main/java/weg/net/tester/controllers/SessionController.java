package weg.net.tester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import weg.net.tester.repositories.SessionRepository;
import weg.net.tester.utils.FrontEndFeedbackUtil;
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
    public void startSession(String cadastro){
        try {
            SessionUtil.initiateSession(cadastro);
            this.template.convertAndSend("/feedback", FrontEndFeedbackUtil.OK);
        } catch (Exception e) {
            this.template.convertAndSend("/feedback", FrontEndFeedbackUtil.ERRO_INESPERADO);
        }
    }

    @MessageMapping("/endSession")
    public void endSession(){
        try {
            SessionUtil.endSession();
            sessionRepository.save(SessionUtil.sessionModel);
            SessionUtil.reset();
            this.template.convertAndSend("/feedback", FrontEndFeedbackUtil.OK);
        } catch (Exception e) {
            this.template.convertAndSend("/feedback", FrontEndFeedbackUtil.ERRO_INESPERADO);
        }
    }
}
