package eu.koerner.classcreation.shells;

import java.util.ArrayList;
import java.util.List;

public class MethodShell extends BaseShell implements ClassMemberShell {
	private String resultTyp;
	private String name;
	private String throwsQuery;
	private List<String> typeParameters = new ArrayList<String>();
	private List<String> annotations = new ArrayList<String>();
	private List<VariableShell> parameters = new ArrayList<VariableShell>();
	private String body;
	
	
	public String getResultTyp() {
		return resultTyp;
	}
	public void setResultTyp(String resultTyp) {
		this.resultTyp = resultTyp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getThrowsQuery() {
		return throwsQuery;
	}
	public void setThrowsQuery(String throwsQuery) {
		this.throwsQuery = throwsQuery;
	}
	public List<String> getTypeParameters() {
		return typeParameters;
	}
	public void setTypeParameters(List<String> typeParameters) {
		this.typeParameters = typeParameters;
	}
	public List<String> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<VariableShell> getParameters() {
		return parameters;
	}
	public void setParameters(List<VariableShell> parameters) {
		this.parameters = parameters;
	}
	
}
