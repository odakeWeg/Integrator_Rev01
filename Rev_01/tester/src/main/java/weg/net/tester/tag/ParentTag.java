package weg.net.tester.tag;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import weg.net.tester.models.CommandLog;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.FailureCodeUtil;

@XmlAccessorType (XmlAccessType.FIELD)
public abstract class ParentTag extends BaseTag {
    //@Transient
    //protected CommandLog commandLog = new CommandLog();    //

    protected int id;   //
    protected String tagIdentifier; //
    protected String tagName;   //

    protected String descricao; //Became part of an obj -> not
    protected String log;   //Became part of an obj -> not
    protected String action; //Became part of an obj -> not
    protected String errorMessage;  //Became part of an obj -> not
    protected String testResult = FailureCodeUtil.NOT_APPLICABLE;    //Became part of an obj
    protected int timeout;  //
    protected int position; //
    //protected static boolean[] isPositionEnable;  //Became part of an obj
    
    protected long individualTestDurationMillis;    //

    @XmlTransient
    protected long startTime;
    
    //public static List<BaseTag> tagList;  //Became part of an obj
    @XmlTransient
    public static TestMetaDataModel testMetaData;
    
    @Override
    public CommandLog command() {
        if(testMetaData.getIsPositionEnabled()[position-1]) {
            commandInitialSetup();
            initiateThread();
            commandEndingSetup();
        } else {
            //Pass value?? -> Run cancel test | let the PLC protect itself
            //testResult = FailureCodeUtil.PASS_OR_SOMETHING
            //Something on log
        }

        return new CommandLog(testResult, errorMessage, descricao, log, action);
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
            testMetaData.getIsPositionEnabled()[this.position-1] = false; 
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
    public String getTagIdentifier() {
        return this.tagIdentifier;
    }

    @Override
    public String getTagName() {
        return this.tagName;
    }
}
