package eu.koerner.classcreation.shells;

import java.util.ArrayList;
import java.util.List;

public class FieldShell extends BaseShell implements ClassMemberShell {
	private List<VariableShell> variables = new ArrayList<>();
	private String type;
	
	public FieldShell(String type) {
		super();
		this.type = type;
	}

	public void setType(String type) {
		this.type = type;
		variables.stream().forEach(var -> var.setType(type));
	}
	
	public void addVariable(String name, String initValue) {
		variables.add(new VariableShell(type, name, initValue));
	}
	
	public String getType() {
		return type;
	}

	public List<VariableShell> getVariables() {
		return variables;
	}
}
