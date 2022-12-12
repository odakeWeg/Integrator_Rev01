package weg.net.tester.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import weg.net.tester.services.TestExecutor;

@Controller
public class TestRoutineHandlerController {
    private SimpMessagingTemplate template;

    @Autowired
    void WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Autowired
    private  TestExecutor testExecutor;

    @MessageMapping("/log")
    public void onReceivedMessage(String barCode) {
        try {
            testExecutor.setBarCode(barCode.split(","));
            testExecutor.setTemplate(template);
            testExecutor.execute();
        } catch (Exception e) {
            //@Todo: send trace to dataBase
        }
    }
}
