package com.mec.ejb;

import java.io.Closeable;
import java.net.InetAddress;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import com.mec.ejb.inter.Logger;

//@Singleton
//@Startup
//public class AutomaticJBossDBWatcher {
//
//	@Schedule(dayOfWeek="*", hour="*", minute="*/30", second="*", year="*", persistent=false)
//	public void backgroundProcessing(){
//		ModelControllerClient client = null;
//		try{
//			client = ModelControllerClient.Factory.create(InetAddress.getByName("localhost"), 9990);
//			final ModelNode operation = new ModelNode();
//			operation.get("operation").set("read-resource");
//			operation.get("include-runtime").set(true);
//			
//			final ModelNode address = operation.get("address");
//			address.add("subsystem", "datasources");
//			address.add("data-source", "ExampleDS");
//			address.add("statistics", "pool");
//			
//			final ModelNode returnVal = client.execute(operation);
//			final ModelNode resultNode = returnVal.get("result");
//			final String stringActiveCount = resultNode.get("ActiveCount").asString();
//			if("undefined".equals(stringActiveCount)){
//				return;	//Connection unused
//			}
//			int activeCount = Integer.parseInt(stringActiveCount);
//			
//			if(50 < activeCount){
//				alertAdministrator();
//			}
//			
//		}catch(Exception e){
//			logger.error("Exception !", e);
//		}finally{
//			safeClose(client);
//		}
//	}
//	
//	private void safeClose(final Closeable closeable){
//		if(null !=closeable){
//			try{
//				closeable.close();
//			}catch(Exception exp){
//				logger.error("Exception occured while closing the client", exp);
//			}
//		}
//	}
//	
//	public void alertAdministrator(){
//		//To be implemented;
//	}
//	@Inject
//	private Logger logger;
//}
