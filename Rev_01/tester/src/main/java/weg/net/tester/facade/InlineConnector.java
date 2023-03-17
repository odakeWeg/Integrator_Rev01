package weg.net.tester.facade;

import org.apache.commons.collections.comparators.BooleanComparator;
import org.springframework.context.annotation.Configuration;

import net.weg.soa.serviceclient.wdc.supervisoryevent.StatusRegister;
import net.weg.soa.serviceclient.wdc.supervisoryevent.SupervisoryEvent;
import net.weg.soa.serviceclient.wdc.supervisoryevent.TestResult;
import net.weg.wdc.sic.SicLibraryHelper;
import net.weg.wdc.sic.service.SicLibraryServices;
import weg.net.tester.exception.InlineException;
import weg.net.tester.tag.LeafInlineSetupTag;
import weg.net.tester.tag.TagList;
import weg.net.tester.tag.TestMetaDataModel;
import weg.net.tester.utils.InlineFeddbackUtil;
import weg.net.tester.utils.TagNameUtil;

@Configuration 
public class InlineConnector {
    private String serial;
    private boolean inlineEnabled;

    public void saveInitialEvent() throws InlineException {
        if(inlineEnabled) {
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
    }

    public void saveApprovalEvent() throws InlineException {
        if(inlineEnabled) {
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
    }

	public void saveRepprovalEvent(String failureCode) throws InlineException {
        if(inlineEnabled) {
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
    }

    public void saveCancelEvent(String failureCode) throws InlineException {
        if(inlineEnabled) {
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
    }

    public String isTestAllowed(TagList tagList) throws InlineException {
        if(isInlineEnabled(tagList))  {
            try {
                if(SicLibraryServices.getInstance().isWorkcenterOperationAllowed(this.serial)) {
                    return InlineFeddbackUtil.ALLOWED;
                } else {
                    return InlineFeddbackUtil.NOT_ALLOWED;
                }
            } catch (Exception e) {
                throw new InlineException("Falha na integração com o inline");
            }
        } else {
            return InlineFeddbackUtil.NOT_ENABLED;
        }
        
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setInlineEnabled(boolean inlineEnabled) {
        this.inlineEnabled = inlineEnabled;
    }

    private boolean isInlineEnabled(TagList tagList) {
        //TagList tagList = TestMetaDataModel.tagList;
        LeafInlineSetupTag inlineSetup;
        for(int i = 0; i < tagList.getList().size(); i++) {
            if(tagList.getList().get(i).getTagName().equals(TagNameUtil.INLINE)) {
                inlineSetup = (LeafInlineSetupTag) tagList.getList().get(i);
                inlineEnabled = inlineSetup.isEnableInline();
            }
        }
        return inlineEnabled;
    }
}
