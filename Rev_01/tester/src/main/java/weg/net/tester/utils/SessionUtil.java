package weg.net.tester.utils;

import java.sql.Timestamp;

import weg.net.tester.models.SessionModel;

public class SessionUtil {
    public static SessionModel sessionModel;
    private static long initialTime;
    private static long endingTime;

    

    public static void initiateSession(String cadastro) {
        sessionModel = new SessionModel();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timestampString = String.valueOf(timestamp.getTime());
        initialTime = timestamp.getTime();

        sessionModel.setCadastro(cadastro);
        sessionModel.setTimestamp(timestampString);
    }

    public static void endSession() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        endingTime = timestamp.getTime();

        sessionModel.setSessionTime(endingTime-initialTime);
        //Set Other stuff
        sessionModel.setExecutingTestTime(0);
        sessionModel.setTotalTestApproved(0);
        sessionModel.setTotalTestCanceled(0);
        sessionModel.setTotalTestExecuted(0);
        sessionModel.setTotalTestFailed(0);
    }
}
