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
import weg.net.tester.models.CommandLog;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FailureCodeUtil;

@Getter
@Setter
public abstract class ParentTag extends BaseTag {
    //@Transient
    //protected CommandLog commandLog = new CommandLog();    //

    protected int id;   //
    protected String tagIdentifier; //
    protected String tagName;   //

    protected String descricao; //Became part of an obj -> not
    protected String log;   //Became part of an obj -> not
    protected String action = ActionCommandUtil.EXIBIT_VALUES; //Became part of an obj -> not
    protected String errorMessage;  //Became part of an obj -> not
    protected String testResult = FailureCodeUtil.NOT_APPLICABLE;    //Became part of an obj
    protected int timeout;  //
    protected int position; //
    protected boolean finished; //
    //protected static boolean[] isPositionEnable;  //Became part of an obj
    
    protected long individualTestDurationMillis;    //

    @JsonIgnore
    protected long startTime;
    
    //public static List<BaseTag> tagList;  //Became part of an obj
    //@JsonIgnore
    //public TestMetaDataModel testMetaData;
    
    @Override
    public CommandLog command() {
        if(TestMetaDataModel.isPositionEnabled[position-1]) {
            commandInitialSetup();
            initiateThread();
            commandEndingSetup();
        } else {
            //Pass value?? -> Run cancel test | let the PLC protect itself
            //testResult = FailureCodeUtil.PASS_OR_SOMETHING
            //Something on log
        }

        finished = true;
        return new CommandLog(testResult, errorMessage, descricao, log, action, TestMetaDataModel.testName, finished);
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
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            testResult = FailureCodeUtil.TIMEOUT;
            log = "Etapa do teste passou do limite de tempo especificado de " + timeout + " segundos";
            TestMetaDataModel.isPositionEnabled[this.position-1] = false; 
            TestMetaDataModel.testStep[this.position-1] = this.id;
        } finally {
            future.cancel(true);
        }
    }
    
    private void commandInitialSetup() {
        startTime = System.currentTimeMillis();
    }

    private void commandEndingSetup() {
        long endTime = System.currentTimeMillis();
        individualTestDurationMillis = endTime - startTime;
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
