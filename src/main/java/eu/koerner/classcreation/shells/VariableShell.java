package eu.koerner.classcreation.shells;

public class VariableShell extends BaseShell {
	private String type;
	private String variableName;
	private String variableInit;
	
	public VariableShell(String type, String variableName, String variableInit) {
		super();
		this.type = type;
		
		this.variableName = variableName;
		this.variableInit = variableInit;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getVariableInit() {
		return variableInit;
	}
	public void setVariableInit(String variableInit) {
		this.variableInit = variableInit;
	}
}
