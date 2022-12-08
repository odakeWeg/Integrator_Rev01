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
        if(TestMetaDataModel.isPositionEnabled[position-1].get() && commandInitialSetup()) {
            initiateThread();
            commandEndingSetup();
        } else {
            testResult = FailureCodeUtil.PASSED;
            log = "Produto em falha, teste desabilitado";
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
            //testResult = FailureCodeUtil.TIMEOUT;
            log = "Etapa do teste passou do limite de tempo especificado de " + timeout + " segundos";
            //TestMetaDataModel.isPositionEnabled[this.position-1] = false; 
            //TestMetaDataModel.testStep[this.position-1] = this.id;
            //@Todo: takeOut
            setFailureCommandLog(FailureCodeUtil.TIMEOUT, log);
        } catch (ExecutionException e) {
            //testResult = FailureCodeUtil.EXECUTION_FAILURE;
            log = "Falha na execução da rotina";
            //TestMetaDataModel.isPositionEnabled[this.position-1] = false; 
            //TestMetaDataModel.testStep[this.position-1] = this.id;
            //@Todo: takeOut
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
        if (TestMetaDataModel.isMainCommunicationSetted()) {
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

    private void commandEndingSetup() {
        long endTime = System.currentTimeMillis();
        individualTestDurationMillis = endTime - startTime;
        //finalFeedbackToSystem();
    }

    /* 
    private void finalFeedbackToSystem() {
        //Get Communication
        //Toogle
        if (TestMetaDataModel.isPositionEnabled[this.position-1].get()) {
            try {
                TestMetaDataModel.getMainCommunication().systemEndFeedback();
            } catch (CommunicationException e) {
                setFailureCommandLog(FailureCodeUtil.FALHA_COMUNICACAO, log);
            } catch (InterruptedException e) {
                setFailureCommandLog(FailureCodeUtil.TIMEOUT, log);
            }
        }
    }
    */

    //@Todo: Metodo problematico,  dados de tag vazando do escopo
    /* 
    protected NodeCommunicationTag getMainCommunication() throws ObjectNotFoundException {
        NodeCommunicationTag communicationTag;
        for (int i = 0; i < TestMetaDataModel.tagList.getList().size(); i++) {
            if (TestMetaDataModel.tagList.getList().get(i).getTagIdentifier().equals(TagIdentifierUtil.COMMUNICATION)) {
                communicationTag = (NodeCommunicationTag) TestMetaDataModel.tagList.getList().get(i);
                if(communicationTag.mainCommunication) {
                    return communicationTag;
                }
            }
        }
        throw new ObjectNotFoundException("Objeto de comunicação não encontrado!");
    }
    */

    protected void setFailureCommandLog(String testResult, String log) {
        this.testResult = testResult;
        this.log = log;
        TestMetaDataModel.isPositionEnabled[this.position-1].set(false);
        TestMetaDataModel.testStep[this.position-1] = this.id;
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
