package eu.koerner.classcreation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import eu.koerner.classcreation.shells.ClassShell;
import eu.koerner.classcreation.shells.FieldShell;
import eu.koerner.classcreation.shells.MethodShell;
import eu.koerner.classcreation.shells.VariableShell;

public class ClassFileBuilder {
	public static void createClassFile(String path, ClassShell classShell)  {
		StringBuilder builder = new StringBuilder();
		
		renderClass(builder, classShell);
		
		File file = new File(path);
		BufferedWriter writer = null;
		try {
		    writer = new BufferedWriter(new FileWriter(file));
		    writer.write(builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	private static void renderClass(StringBuilder builder, ClassShell shell) {
		addAnnotations(builder, shell.getAnnotations());
		shell.getModifiers().stream().forEach(mod -> builder.append(mod));
		builder.append(" ");
		builder.append(shell.getClassName());
		builder.append(" ");
		addSuperClasse(builder, shell.getSuperClass());
		builder.append(" ");
		addSuperInterfaces(builder, shell.getSuperInterfaces());
		
		builder.append("{");
		builder.append(System.lineSeparator());
		
		addFields(builder, shell.getFields());
		addMethods(builder, shell.getMethods());
		builder.append(System.lineSeparator());
		builder.append("}");
	}

	private static void renderMethod(StringBuilder builder, MethodShell shell) {
		addAnnotations(builder, shell.getAnnotations());
		shell.getModifiers().stream().forEach(mod -> builder.append(mod));
		builder.append(" ");
		addTypeParameters(builder, shell.getTypeParameters());
		
		builder.append(" ");
		builder.append(shell.getResultTyp());
		
		builder.append(" ");
		builder.append(shell.getName());

		//Parameter
		builder.append("(");
		addVariables(builder, shell.getParameters());
		builder.append(")");
		
		//Body
		builder.append("{");
		builder.append(System.lineSeparator());
		builder.append(shell.getBody());
		builder.append(System.lineSeparator());
		builder.append("}");
	}
	
	private static void renderField(StringBuilder builder, FieldShell shell) {
		addAnnotations(builder, shell.getAnnotations());
		shell.getModifiers().stream().forEach(mod -> builder.append(mod));
		builder.append(" ");
		builder.append(shell.getType());
		builder.append(" ");
		addVariables(builder, shell.getVariables());
		builder.append(";");
	}
	
	
	private static void addFields(StringBuilder builder, List<FieldShell> fields) {
		for (FieldShell field : fields) {
			renderField(builder, field);
			builder.append(System.lineSeparator());
		}
	}
	
	private static void addMethods(StringBuilder builder, List<MethodShell> methods) {
		for (MethodShell method : methods) {
			renderMethod(builder, method);
			builder.append(System.lineSeparator());
		}
	}

	private static void addSuperInterfaces(StringBuilder builder, List<String> superInterfaces) {
		
		if(superInterfaces.size() > 0) {
			builder.append("implements ");
		}
		
		for(int i = 0; i < superInterfaces.size(); i++) {
			boolean isLast = i + 1 == superInterfaces.size();

			builder.append(superInterfaces.get(i));
			
			if(!isLast) {
				builder.append(", ");
			}
		}
		
	}

	private static void addSuperClasse(StringBuilder builder, String superClass) {
		if(superClass != null && !superClass.isEmpty()) {
			builder.append("extends ");
			builder.append(superClass);
		}
	}

	
	
	private static void addVariables(StringBuilder builder, List<VariableShell> variables) {
		for(int i = 0; i < variables.size(); i++) {
			VariableShell var = variables.get(i);
			boolean isLast = i + 1 == variables.size();
			
			builder.append(var.getVariableName());
			if(var.getVariableInit() != null) {
				builder.append("=");
				builder.append(var.getVariableInit());
			}
			
			if(!isLast) {
				builder.append(", ");
			}
		}
	}

	private static void addAnnotations(StringBuilder builder, List<String> annotations) {
		for (String annotation : annotations) {
			builder.append(annotation);
			builder.append(System.lineSeparator());
		}
	}
	
	private static void addTypeParameters(StringBuilder builder, List<String> typeParameters) {
		if(typeParameters.size() > 0) {
			builder.append("<");
			
			for(int i = 0; i < typeParameters.size(); i++) {
				boolean isLast = i + 1 == typeParameters.size();
				
				builder.append(typeParameters.get(i));
				
				if(!isLast) {
					builder.append(", ");
				}
			}
			builder.append(">");
		}
	}

	

	

	
}
