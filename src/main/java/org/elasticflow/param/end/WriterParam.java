package org.elasticflow.param.end;

/**
 * 
 * @author chengwen
 * @version 1.0
 * @date 2018-11-09 11:36
 */
public class WriterParam {
	private String writeKey;
	/**keyType:scan|unique
		scan,scan update batch record
		unique key update single record
	*/
	private String keyType;
	/**user define field,pass custom value**/
	private String DSL;

	public String getWriteKey() {
		return writeKey;
	}

	public String getKeyType() {
		return keyType;
	}
	
	public String getDSL() {
		return DSL;
	}

	public static void setKeyValue(WriterParam wp, String k, String v) {
		switch (k.toLowerCase()) {
		case "writekey":
			wp.writeKey = v;
			break;
		case "keytype":
			wp.keyType = v;
			break;
		case "dsl":
			wp.DSL = v;
			break;
		}
	}
}
