package weg.net.tester.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import weg.net.tester.helper.SessionHelper;
import weg.net.tester.models.database.StackTraceLogModel;
import weg.net.tester.services.TestExecutor;
import weg.net.tester.utils.EndPointPathUtil;

@Controller
public class TestRoutineHandlerController {
    private SimpMessagingTemplate template;

    @Autowired
    void WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Autowired
    private  TestExecutor testExecutor;

    @MessageMapping(EndPointPathUtil.LOG)
    public void onReceivedMessage(String barCode) {
        try {
            testExecutor.setBarCode(barCode.split(","));
            testExecutor.setTemplate(template);
            testExecutor.execute();
        } catch (Exception e) {
            //@Todo: send trace to dataBase -> instantiate reposito and stuff like that
            //@Todo: every unknown error should throw an exception to end up here
            StackTraceLogModel stackTraceLogModel = new StackTraceLogModel(e.getMessage(), Long.toString(System.currentTimeMillis()), SessionHelper.sessionModel.getCadastro());
            
        }
    }
}
