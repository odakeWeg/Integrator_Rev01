package weg.net.tester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import weg.net.tester.helper.SessionHelper;
import weg.net.tester.models.database.LoginModel;
import weg.net.tester.models.web.SessionLog;
import weg.net.tester.repositories.LoginRepository;
import weg.net.tester.repositories.SessionRepository;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.EndPointPathUtil;
import weg.net.tester.utils.FrontEndFeedbackUtil;

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
    @Autowired
    private LoginRepository loginRepository;

    @MessageMapping(EndPointPathUtil.START_SESSION)
    public void startSession(LoginModel login){
        try {
            LoginModel loginModel = loginRepository.findById(login.getCadastro()).get();
            if (loginModel!=null && login.toString().equals(loginModel.toString())) {
                SessionHelper.initiateSession(login.getCadastro());
                sessionLog = new SessionLog(FrontEndFeedbackUtil.OK, ActionCommandUtil.LOGIN);
                this.template.convertAndSend(EndPointPathUtil.CHANNEL, sessionLog);
            } else {
                sessionLog = new SessionLog(FrontEndFeedbackUtil.INVALID_LOGIN, ActionCommandUtil.LOGIN);
                this.template.convertAndSend(EndPointPathUtil.CHANNEL, sessionLog);
            }
            
        } catch (Exception e) { //@Todo: tratar melhor, badaBase exception
            sessionLog = new SessionLog(FrontEndFeedbackUtil.ERRO_INESPERADO, ActionCommandUtil.LOGIN);
            this.template.convertAndSend(EndPointPathUtil.CHANNEL, sessionLog);
        }
    }

    @MessageMapping(EndPointPathUtil.END_SESSION)
    public void endSession(){
        try {
            SessionHelper.endSession();
            sessionRepository.save(SessionHelper.sessionModel);
            SessionHelper.reset();
            sessionLog = new SessionLog(FrontEndFeedbackUtil.OK, ActionCommandUtil.LOGOUT);
            this.template.convertAndSend(EndPointPathUtil.CHANNEL, sessionLog);
        } catch (Exception e) {
            sessionLog = new SessionLog(FrontEndFeedbackUtil.ERRO_INESPERADO, ActionCommandUtil.LOGOUT);
            this.template.convertAndSend(EndPointPathUtil.CHANNEL, sessionLog);
        }
    }
}
