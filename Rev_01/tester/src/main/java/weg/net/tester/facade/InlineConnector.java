package weg.net.tester.facade;

import org.springframework.context.annotation.Configuration;

import net.weg.soa.serviceclient.wdc.supervisoryevent.StatusRegister;
import net.weg.soa.serviceclient.wdc.supervisoryevent.SupervisoryEvent;
import net.weg.soa.serviceclient.wdc.supervisoryevent.TestResult;
import net.weg.wdc.sic.SicLibraryHelper;
import net.weg.wdc.sic.service.SicLibraryServices;
import weg.net.tester.exception.InlineException;

@Configuration 
public class InlineConnector {
    private String serial;

    public void saveInitialEvent() throws InlineException {
        try {
            SupervisoryEvent supervisoryEvent = new SupervisoryEvent();
            supervisoryEvent.setWorkCenter(SicLibraryServices.getInstance().getWorkCenterConfiguration().getNumber());
            supervisoryEvent.setTestStation(SicLibraryServices.getInstance().getWorkCenterConfiguration().getTestStationNumber());
            supervisoryEvent.setOperation(SicLibraryServices.getInstance().getWorkCenterConfiguration().getProductionStep());
            supervisoryEvent.setPlant(SicLibraryServices.getInstance().getWorkCenterConfiguration().getPlant());
            supervisoryEvent.setTestResult(TestResult.EXECUTING);
            supervisoryEvent.setTestInitialDate(SicLibraryHelper.getXMLGregorianCalendarNow());
            supervisoryEvent.setTestInitialHour(SicLibraryHelper.getTimeNow());
            supervisoryEvent.setTestFinalDate(SicLibraryHelper.getXMLGregorianCalendarNow());
            supervisoryEvent.setTestFinalHour(SicLibraryHelper.getTimeNow());
            supervisoryEvent.setStatusRegister(StatusRegister.TEST_EXECUTING);
            SicLibraryServices.getInstance().SaveBeginSupervisoryEvent(supervisoryEvent);
        } catch (Exception e) {
            //throw new InlineException("Falha no evento inicial inline");
        }
    }

    public void saveApprovalEvent() throws InlineException {
        try {
            SupervisoryEvent resultTest = null;
            resultTest = SicLibraryServices.getInstance().FindLastEventBySerialNumber(this.serial);
            resultTest.setStatusRegister(StatusRegister.TEST_FINISHED);
            resultTest.setTestResult(TestResult.APPROVED);
            resultTest.setTestFinalDate(SicLibraryHelper.getXMLGregorianCalendarNow());
            resultTest.setTestFinalHour(SicLibraryHelper.getTimeNow());
            SicLibraryServices.getInstance().SaveEndSupervisoryEvent(resultTest);   
        } catch (Exception e) {
            //throw new InlineException("Falha no evento de aprovação inline");
        }
    }

	public void saveRepprovalEvent(String failureCode) throws InlineException {
        try {
            SupervisoryEvent resultTest;
            resultTest = SicLibraryServices.getInstance().FindLastEventBySerialNumber(this.serial);
            resultTest.setFaultId(failureCode);
            resultTest.setStatusRegister(StatusRegister.TEST_FINISHED);
            resultTest.setTestResult(TestResult.FAILED);
            resultTest.setTestFinalDate(SicLibraryHelper.getXMLGregorianCalendarNow());
            resultTest.setTestFinalHour(SicLibraryHelper.getTimeNow());
            SicLibraryServices.getInstance().SaveEndSupervisoryEvent(resultTest);
        } catch (Exception e) {
            //throw new InlineException("Falha no evento de reprovacao inline");
        }
    }

    public void saveCancelEvent(String failureCode) throws InlineException {
        try {
            SupervisoryEvent resultTest;
            resultTest = SicLibraryServices.getInstance().FindLastEventBySerialNumber(this.serial);
            resultTest.setFaultId(failureCode);
            resultTest.setStatusRegister(StatusRegister.TEST_FINISHED);
            resultTest.setTestResult(TestResult.CANCELED);
            resultTest.setTestFinalDate(SicLibraryHelper.getXMLGregorianCalendarNow());
            resultTest.setTestFinalHour(SicLibraryHelper.getTimeNow());
            SicLibraryServices.getInstance().SaveEndSupervisoryEvent(resultTest);
        } catch (Exception e) {
            //throw new InlineException("Falha no evento cancelamento inline");
        }
    }

    public boolean isTestAllowed() throws InlineException {
        try {
            return SicLibraryServices.getInstance().isWorkcenterOperationAllowed(this.serial);
        } catch (Exception e) {
            //throw new InlineException("Falha na checagem inline");
            return true;
            //Uncomment this stuff
        }
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
