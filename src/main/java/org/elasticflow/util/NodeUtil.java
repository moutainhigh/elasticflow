package org.elasticflow.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.elasticflow.config.GlobalParam;
import org.elasticflow.config.InstanceConfig;
import org.elasticflow.model.reader.ScanPosition;
import org.elasticflow.yarn.Resource;

public final class NodeUtil {

	/**
	 * init node start parameters
	 * 
	 * @param instanceConfig
	 */
	public static void initParams(InstanceConfig instanceConfig) {
		String instance = instanceConfig.getName();
		String[] L1seqs = Common.getL1seqs(instanceConfig, true);
		for (String L1seq : L1seqs) {
			Resource.FLOW_STATUS.set(instance, L1seq, GlobalParam.JOB_TYPE.FULL.name(), new AtomicInteger(1));
			Resource.FLOW_STATUS.set(instance, L1seq, GlobalParam.JOB_TYPE.INCREMENT.name(), new AtomicInteger(1));
			String path = Common.getTaskStorePath(instance, L1seq, GlobalParam.JOB_INCREMENTINFO_PATH);
			byte[] b = ConfigStorer.getData(path, true);
			if (b != null && b.length > 0) {
				String str = new String(b);
				GlobalParam.SCAN_POSITION.put(Common.getMainName(instance, L1seq),
						new ScanPosition(str, instance, L1seq));
			} else {
				GlobalParam.SCAN_POSITION.put(Common.getMainName(instance, L1seq), new ScanPosition(instance, L1seq));
			}
		}
	}

	public static void runShell(String path) {
		Process pc = null;
		try {
			Common.LOG.info("Start Run Script " + path);
			pc = Runtime.getRuntime().exec(path);
			pc.waitFor();
		} catch (InterruptedException e) {
			Common.LOG.warn("progress is killed!");
		} catch (Exception e) {
			Common.LOG.error("restartNode Exception", e);
		} finally {
			if (pc != null) {
				pc.destroy();
			}
		}
	}
}
