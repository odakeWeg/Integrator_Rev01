package weg.net.tester.tag;

import java.util.List;
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
import weg.net.tester.utils.FailureCodeUtil;

@XmlAccessorType (XmlAccessType.FIELD)
public abstract class ParentTag implements BaseTag {
    protected CommandLog commandLog;    //

    protected int id;   //
    protected String tagIdentifier; //

    protected String descricao; //
    protected String log;   //
    protected String action; //
    protected String errorMessage;  //
    protected String testResult = FailureCodeUtil.NOT_APPLICABLE;    //
    protected int timeout;  //
    protected int position; //
    protected static boolean[] isPositionEnable;  //

    protected long individualTestDurationMillis;

    @XmlTransient
    private long startTime;
    @XmlTransient
    protected List<ParentTag> tagList;  //
    
    @Override
    public CommandLog command() {
        if(isPositionEnable[position-1]) {
            commandInitialSetup();
            initiateThread();
            commandEndingSetup();
        }

        return commandLog;
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
            isPositionEnable[this.position-1] = false; 
        } finally {
            future.cancel(true);
        }
    }
    
    private void commandInitialSetup() {

    }

    private void commandEndingSetup() {
        
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String getTagIdentifier() {
        return this.tagIdentifier;
    }
}
