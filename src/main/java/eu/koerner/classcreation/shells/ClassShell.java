package eu.koerner.classcreation.shells;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassShell extends BaseShell implements ClassMemberShell  {
	
	private String className;
	private List<String> typeParameters = new ArrayList<String>();
	private String superClass;
	private List<String> superInterfaces = new ArrayList<String>();
	
	private List<MethodShell> methods = new ArrayList<MethodShell>();
	private List<FieldShell> fields = new ArrayList<FieldShell>();
	
	
	public ClassShell(String className) {
		this.className = className;
		setModifiers(Arrays.asList(ClassModifier.cPublic.toString()));
	}

	public List<String> getTypeParameters() {
		return typeParameters;
	}

	public void setTypeParameters(List<String> typeParameters) {
		this.typeParameters = typeParameters;
	}

	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public List<String> getSuperInterfaces() {
		return superInterfaces;
	}

	public void setSuperInterfaces(List<String> superInterfaces) {
		this.superInterfaces = superInterfaces;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<MethodShell> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodShell> methods) {
		this.methods = methods;
	}
	public void addMethod(MethodShell shell) {
		if(methods == null) {
			methods = new ArrayList<MethodShell>();
		}
		methods.add(shell);
	}

	public List<FieldShell> getFields() {
		return fields;
	}

	public void setFields(List<FieldShell> fields) {
		this.fields = fields;
	}

	public void addField(FieldShell shell) {
		if(fields == null) {
			fields = new ArrayList<FieldShell>();
		}
		this.fields.add(shell);
	}


	public enum ClassModifier{
		cPublic("public"), cProtected("protected"), cPrivate("private"), cAbstract("abstract"), cStatic("static"), cFinal("final"), cStrictfp("strictfp");
		private String value;

		private ClassModifier(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
	}
}
