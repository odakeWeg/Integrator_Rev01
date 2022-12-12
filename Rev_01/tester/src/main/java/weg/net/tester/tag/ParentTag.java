package weg.net.tester.tag;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.models.CommandLog;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagIdentifierUtil;

@Getter
@Setter
public abstract class ParentTag extends BaseTag {
    protected int id;
    protected String tagIdentifier;
    protected String tagName;

    protected String descricao;
    protected String log;
    protected String action = ActionCommandUtil.EXIBIT_VALUES;
    protected String errorMessage;
    protected String testResult = FailureCodeUtil.NOT_APPLICABLE;
    protected int timeout;
    protected int position;
    protected boolean finished;

    protected long individualTestDurationMillis;

    @JsonIgnore
    protected long startTime;

    public ParentTag() {
        this.setTagName();
    }
    
    @Override
    public CommandLog command() {
        if(TestMetaDataModel.isPositionEnabled[position-1].get()) {
            if(commandInitialSetup()) {
                initiateThread();
                commandEndingSetup();
            }
        } else {
            testResult = FailureCodeUtil.PASSED;
            log = "Teste desabilitado";
        }

        finished = true;
        return new CommandLog(testResult, errorMessage, descricao, log, action, TestMetaDataModel.testName, finished, this.position);
    }

    private void initiateThread() {
        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<Boolean> task = new Callable<Boolean>() {
            public Boolean call() {
                executeCommand();
                return true;
            }
        };
        Future<Boolean> future = executor.submit(task);
        try {
            future.get(timeout, TimeUnit.SECONDS); 
        } catch (TimeoutException | InterruptedException e) {
            log = "Etapa do teste passou do limite de tempo especificado de " + timeout + " segundos";
            setFailureCommandLog(FailureCodeUtil.TIMEOUT, log);
        } catch (ExecutionException e) {
            log = "Falha na execução da rotina";
            setFailureCommandLog(FailureCodeUtil.EXECUTION_FAILURE, log);
        } finally {
            future.cancel(true);
        }
    }
    
    private boolean commandInitialSetup() {
        startTime = System.currentTimeMillis();
        return initialFeedbackToSystem();
    }

    private boolean initialFeedbackToSystem() {
        if (TestMetaDataModel.isMainCommunicationSetted() && nonTrivialOperation()) {
            try {
                TestMetaDataModel.mainCommunication.systemInitialFeedback(this.timeout);
            } catch (CommunicationException e) {
                log = "Falha na comunicação com " + TestMetaDataModel.mainCommunication.getCommunicationName();
                setFailureCommandLog(FailureCodeUtil.FALHA_COMUNICACAO, log);
                return false;
            } catch (InterruptedException e) {
                log = "Etapa do teste passou do limite de tempo especificado de " + timeout + " segundos";
                setFailureCommandLog(FailureCodeUtil.TIMEOUT, log);
                return false;
            }
        }
        return true;
    }

    private boolean nonTrivialOperation() {
        if(tagIdentifier.equals(TagIdentifierUtil.TEST)) {
            return false;
        }
        return true;
    }

    private void commandEndingSetup() {
        long endTime = System.currentTimeMillis();
        individualTestDurationMillis = endTime - startTime;
    }

    protected void setFailureCommandLog(String testResult, String log) {
        this.testResult = testResult;
        this.log = log;
        TestMetaDataModel.isPositionEnabled[this.position-1].set(false);
        TestMetaDataModel.testStep[this.position-1] = this.id;
    }

    protected void setMainCommunicationFailure(String testResult, String log) {
        this.testResult = testResult;
        this.log = log;
        TestMetaDataModel.testStep[this.position-1] = this.id;
        for(int i = 0; i < TestMetaDataModel.isPositionEnabled.length; i++) {
            TestMetaDataModel.isPositionEnabled[i].set(false);
        }
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public String getTagIdentifier() {
        return this.tagIdentifier;
    }

    @Override
    public String getTagName() {
        return this.tagName;
    }
}
