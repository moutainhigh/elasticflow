package org.elasticflow.reader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.concurrent.NotThreadSafe;

import org.elasticflow.flow.Flow;
import org.elasticflow.model.reader.DataPage;
import org.elasticflow.model.reader.PipeDataUnit;
import org.elasticflow.param.pipe.ConnectParams;
import org.elasticflow.task.JobPage;

/**
 * 
 * @author chengwen
 * @version 2.0
 * @date 2018-10-12 14:28
 */
@NotThreadSafe
public abstract class ReaderFlowSocket extends Flow{  

	protected DataPage dataPage = new DataPage(); 
	
	protected LinkedList<PipeDataUnit> dataUnit = new LinkedList<>(); 
	
	public final Lock lock = new ReentrantLock();   
	
	@Override
	public void INIT(ConnectParams connectParams) {
		this.connectParams = connectParams; 
		this.poolName = connectParams.getWhp().getPoolName(connectParams.getL1Seq()); 
	} 
	 
	public abstract DataPage getPageData(final JobPage JP,int pageSize);

	public abstract ConcurrentLinkedDeque<String> getPageSplit(final HashMap<String, String> param,int pageSize);
	
	public void freeJobPage() {
		this.dataPage.clear(); 
		this.dataUnit.clear();  
	} 
}
