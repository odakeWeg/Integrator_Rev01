package weg.net.tester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import weg.net.tester.models.web.SessionLog;
import weg.net.tester.repositories.SessionRepository;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.EndPointPathUtil;
import weg.net.tester.utils.FrontEndFeedbackUtil;
import weg.net.tester.utils.SessionUtil;

@Controller
public class SessionController {
    private SimpMessagingTemplate template;
    private SessionLog sessionLog;

    @Autowired
    void WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Autowired
    private SessionRepository sessionRepository;

    @MessageMapping(EndPointPathUtil.START_SESSION)
    public void startSession(String cadastro){
        try {
            SessionUtil.initiateSession(cadastro);
            sessionLog = new SessionLog(FrontEndFeedbackUtil.OK, ActionCommandUtil.LOGIN);
            this.template.convertAndSend(EndPointPathUtil.CHANNEL, sessionLog);
        } catch (Exception e) {
            sessionLog = new SessionLog(FrontEndFeedbackUtil.ERRO_INESPERADO, ActionCommandUtil.LOGIN);
            this.template.convertAndSend(EndPointPathUtil.CHANNEL, sessionLog);
        }
    }

    @MessageMapping(EndPointPathUtil.END_SESSION)
    public void endSession(){
        try {
            SessionUtil.endSession();
            sessionRepository.save(SessionUtil.sessionModel);
            SessionUtil.reset();
            sessionLog = new SessionLog(FrontEndFeedbackUtil.OK, ActionCommandUtil.LOGOUT);
            this.template.convertAndSend(EndPointPathUtil.CHANNEL, sessionLog);
        } catch (Exception e) {
            sessionLog = new SessionLog(FrontEndFeedbackUtil.ERRO_INESPERADO, ActionCommandUtil.LOGOUT);
            this.template.convertAndSend(EndPointPathUtil.CHANNEL, sessionLog);
        }
    }
}
