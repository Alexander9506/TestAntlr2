package eu.koerner.test;

public abstract class ModelBase {

	private long iD;

	public long getiD() {
		return iD;
	}

	public void setiD(long iD) {
		this.iD = iD;
	}
	
	public abstract long addIdToSelf() ;
}
