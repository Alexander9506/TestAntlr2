package eu.koerner.classcreation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import eu.koerner.antlr.Java8Parser;
import eu.koerner.antlr.Java8Parser.ClassDeclarationContext;
import eu.koerner.antlr.Java8Parser.FieldDeclarationContext;
import eu.koerner.antlr.Java8Parser.FieldModifierContext;
import eu.koerner.antlr.Java8Parser.FormalParameterContext;
import eu.koerner.antlr.Java8Parser.MethodDeclarationContext;
import eu.koerner.antlr.Java8Parser.MethodHeaderContext;
import eu.koerner.antlr.Java8Parser.NormalClassDeclarationContext;
import eu.koerner.antlr.Java8Parser.VariableDeclaratorListContext;
import eu.koerner.classcreation.shells.ClassShell;
import eu.koerner.classcreation.shells.FieldShell;
import eu.koerner.classcreation.shells.MethodShell;
import eu.koerner.classcreation.shells.VariableShell;

public class ShellFactory {

	public static ClassShell createClassShell(ClassDeclarationContext cdc) {
		if(cdc.enumDeclaration() != null) {
			return _CreateEnumShell(cdc);
		}else if(cdc.normalClassDeclaration() != null){
			return _CreateClassShell(cdc);
		}
		return null;
	}
	
	private static ClassShell _CreateClassShell(ClassDeclarationContext cdc) {
		NormalClassDeclarationContext ncd = cdc.normalClassDeclaration(); 
		ClassShell cs = new ClassShell(ncd.Identifier().getText());
		
		
		cs.setModifiers(ncd.classModifier().stream().map(mod -> mod.classModifierWithoutAnnotations().getText()).collect(Collectors.toList()));
		
		cs.setAnnotations(ncd.classModifier().stream().map(mod -> mod.annotation() == null ? null : mod.annotation().getText()).filter(Objects::nonNull).collect(Collectors.toList()));
		
		if(ncd.typeParameters() != null) {
			cs.setTypeParameters( ncd.typeParameters().typeParameterList().typeParameter().stream().map(tp -> tp.getText()).collect(Collectors.toList()));
		}
		
		if(ncd.superclass() != null) {
			cs.setSuperClass(ncd.superclass().classType().getText());
		}
		if(ncd.superinterfaces() != null) { 
			cs.setSuperInterfaces(ncd.superinterfaces().interfaceTypeList().interfaceType().stream().map(it -> it.getText()).collect(Collectors.toList()));
		}
		
		return cs;
	}
	
	private static ClassShell _CreateEnumShell(ClassDeclarationContext cdc) {
		
		
		return null;
	}
	
	public static FieldShell createFieldShell(FieldDeclarationContext fdc) {
		FieldShell fs = new FieldShell(fdc.unannType().getText());
		
		if(fdc.fieldModifier() != null) {
			fs.setModifiers(fdc.fieldModifier().stream().map(mod -> mod.fieldModifierWithoutAnnotations() == null ? null : mod.fieldModifierWithoutAnnotations().getText()).filter(Objects::nonNull).collect(Collectors.toList()));
			fs.setAnnotations(fdc.fieldModifier().stream().map(mod -> mod.annotation() == null ? null : mod.annotation().getText()).filter(Objects::nonNull).collect(Collectors.toList()));
		}
		
		VariableDeclaratorListContext variableDList = fdc.variableDeclaratorList();
		variableDList.variableDeclarator().stream().forEach(v -> fs.addVariable(v.variableDeclaratorId().getText() , v.variableInitializer() == null ? null : v.variableInitializer().getText()));
		
		return fs;
	}
	

	public static MethodShell createMethodShell(MethodDeclarationContext mdc) {
		MethodShell ms = new MethodShell();
		
		ms.setModifiers(mdc.methodModifier().stream().map(mod -> mod.methodModifierWithoutAnnotations() == null ? null : mod.methodModifierWithoutAnnotations().getText()).filter(Objects::nonNull).collect(Collectors.toList()));
		ms.setAnnotations(mdc.methodModifier().stream().map(mod -> mod.annotation() == null ? null : mod.annotation().getText()).filter(Objects::nonNull).collect(Collectors.toList()));
		
		MethodHeaderContext methodHeaderC = mdc.methodHeader();

		ms.setResultTyp(methodHeaderC.result().getText());
		ms.setName(methodHeaderC.methodDeclarator().Identifier().getText());

		if(methodHeaderC.throws_() != null) {
			ms.setThrowsQuery(methodHeaderC.throws_().getText());
		}
		
		if(methodHeaderC.typeParameters() != null) {
			ms.setTypeParameters( methodHeaderC.typeParameters().typeParameterList().typeParameter().stream().map(tp -> tp.getText()).collect(Collectors.toList()));
		}
		
		if(methodHeaderC.annotation() != null) {
			ms.setAnnotations(methodHeaderC.annotation().stream().map(annotation -> annotation.getText()).collect(Collectors.toList()));
		}
		
		if(mdc.methodBody() != null) {
			ms.setBody(mdc.methodBody().getText());
		}
		
		if(methodHeaderC.methodDeclarator().formalParameterList() != null) {
			List<VariableShell> methodParamList = new ArrayList<VariableShell>();
			List<FormalParameterContext> parameters = new ArrayList<Java8Parser.FormalParameterContext>();
			
			if(methodHeaderC.methodDeclarator().formalParameterList().formalParameters().formalParameter() != null) {
				parameters.addAll(methodHeaderC.methodDeclarator().formalParameterList().formalParameters().formalParameter());
			}else if(methodHeaderC.methodDeclarator().formalParameterList().formalParameters().formalParameter(). //Hier iwas mit last != null){
				parameters.addAll(methodHeaderC.methodDeclarator().formalParameterList().formalParameters().formalParameter());
			}
			
			
			for (FormalParameterContext param : parameters) {
				VariableShell varShell = new VariableShell(param.unannType().getText(), param.variableDeclaratorId().getText(), null);
				if(param.variableModifier() != null) {
					varShell.setModifiers(param.variableModifier().stream()
							.map(v -> v.variableModifierWithoutAnnotations() == null ? null : v.variableModifierWithoutAnnotations().getText())
							.filter(Objects::nonNull)
							.collect(Collectors.toList()));
					varShell.setAnnotations(param.variableModifier().stream()
							.map(v -> v.annotation() == null ? null : v.annotation().getText())
							.filter(Objects::nonNull)
							.collect(Collectors.toList()));
				}
				
				methodParamList.add(varShell);
			}
		}
		
		return ms;
	}
}
