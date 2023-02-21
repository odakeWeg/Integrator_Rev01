package weg.net.tester.helper;

import java.sql.Timestamp;

import weg.net.tester.models.database.SessionModel;
import weg.net.tester.utils.FrontEndFeedbackUtil;

//@Todo: Maybe make it non static class
public class SessionHelper {

    public static SessionModel sessionModel;
    private static long initialTime;
    private static long endingTime;

    private static long initialTestTime;
    private static long endingTestTime;

    public static void initiateSession(int cadastro) {
        sessionModel = new SessionModel();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timestampString = String.valueOf(timestamp.getTime());
        initialTime = timestamp.getTime();

        sessionModel.setCadastro(cadastro);
        sessionModel.setTimestamp(timestampString);
    }

    private static void validateLogin(int cadastro, String password) {

    }

    private static void setSessionValues(int cadastro) {

    }

    public static void endSession() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        endingTime = timestamp.getTime();

        sessionModel.setSessionTime(endingTime-initialTime);
    }

    public static void reset() {
        sessionModel = null;    //new SessionModel();
        initialTime = 0;
        endingTime = 0;
    }

    public static void initiateTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        initialTestTime = timestamp.getTime();
    }

    public static void endTest(String[] result) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        endingTestTime = timestamp.getTime();

        long updateTime = sessionModel.getExecutingTestTime() + endingTestTime - initialTestTime;
        sessionModel.setExecutingTestTime(updateTime);

        for (int i = 0; i < result.length; i++) {
            increment(result[i]);
        }
    }

    private static void increment(String result) {
        incrementExecuted();
        if (result.equals(FrontEndFeedbackUtil.OK)) {
            incrementApproved();
        } else {
            if (result.equals(FrontEndFeedbackUtil.CANCELADO)) {
                incrementCanceled();
            } else {
                incrementFailed();
            }
        }
    }

    private static void incrementApproved() {
        sessionModel.setTotalTestApproved(sessionModel.getTotalTestApproved()+1);
    }

    private static void incrementFailed() {
        sessionModel.setTotalTestFailed(sessionModel.getTotalTestFailed()+1);
    }

    private static void incrementCanceled() {
        sessionModel.setTotalTestCanceled(sessionModel.getTotalTestCanceled()+1);
    }

    private static void incrementExecuted() {
        sessionModel.setTotalTestExecuted(sessionModel.getTotalTestExecuted()+1);
    }
}
