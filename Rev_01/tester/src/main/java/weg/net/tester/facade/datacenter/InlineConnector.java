package weg.net.tester.facade.datacenter;

import org.springframework.context.annotation.Configuration;

import net.weg.soa.serviceclient.wdc.supervisoryevent.StatusRegister;
import net.weg.soa.serviceclient.wdc.supervisoryevent.SupervisoryEvent;
import net.weg.soa.serviceclient.wdc.supervisoryevent.TestResult;
import net.weg.wdc.sic.SicLibraryHelper;
import net.weg.wdc.sic.service.SicLibraryServices;

@Configuration 
public class InlineConnector {
    private String serial;

    public void saveInitialEvent() throws Exception {
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
	}

    public void saveApprovalEvent() throws Exception {
		SupervisoryEvent resultTest = null;
        resultTest = SicLibraryServices.getInstance().FindLastEventBySerialNumber(this.serial);
        resultTest.setStatusRegister(StatusRegister.TEST_FINISHED);
        resultTest.setTestResult(TestResult.APPROVED);
        resultTest.setTestFinalDate(SicLibraryHelper.getXMLGregorianCalendarNow());
        resultTest.setTestFinalHour(SicLibraryHelper.getTimeNow());
        SicLibraryServices.getInstance().SaveEndSupervisoryEvent(resultTest);   
	}

	public void saveDisapprovalEvent(String failureCode) throws Exception {
        SupervisoryEvent resultTest;
        resultTest = SicLibraryServices.getInstance().FindLastEventBySerialNumber(this.serial);
        resultTest.setFaultId(failureCode);
        resultTest.setStatusRegister(StatusRegister.TEST_FINISHED);
        resultTest.setTestResult(TestResult.FAILED);
        resultTest.setTestFinalDate(SicLibraryHelper.getXMLGregorianCalendarNow());
        resultTest.setTestFinalHour(SicLibraryHelper.getTimeNow());
        SicLibraryServices.getInstance().SaveEndSupervisoryEvent(resultTest);
    }

    public void saveCancelEvent(String failureCode) throws Exception {
        SupervisoryEvent resultTest;
        resultTest = SicLibraryServices.getInstance().FindLastEventBySerialNumber(this.serial);
        resultTest.setFaultId(failureCode);
        resultTest.setStatusRegister(StatusRegister.TEST_FINISHED);
        resultTest.setTestResult(TestResult.CANCELED);
        resultTest.setTestFinalDate(SicLibraryHelper.getXMLGregorianCalendarNow());
        resultTest.setTestFinalHour(SicLibraryHelper.getTimeNow());
        SicLibraryServices.getInstance().SaveEndSupervisoryEvent(resultTest);
	}

    public boolean isTestAllowed() throws Exception {
        return SicLibraryServices.getInstance().isWorkcenterOperationAllowed(this.serial);
	}

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
