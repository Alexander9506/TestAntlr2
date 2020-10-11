package eu.koerner.classcreation;

import java.util.ArrayList;
import java.util.List;

import eu.koerner.antlr.Java8BaseListener;
import eu.koerner.antlr.Java8Parser.ClassDeclarationContext;
import eu.koerner.antlr.Java8Parser.FieldDeclarationContext;
import eu.koerner.antlr.Java8Parser.MethodDeclarationContext;
import eu.koerner.classcreation.shells.ClassShell;
import eu.koerner.classcreation.shells.FieldShell;
import eu.koerner.classcreation.shells.MethodShell;

public class ClassObservationListener extends Java8BaseListener{
	private List<ClassShell> shells = new ArrayList<>();
	private ClassShell currentShell = null;
	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		if(currentShell == null) {
			ClassShell cs = ShellFactory.createClassShell(ctx);
			currentShell = cs;
		}else {
			System.out.println("NICHT IMPLEMENTIERT");
		}
	}
	
	@Override
	public void enterFieldDeclaration(FieldDeclarationContext ctx) {
		if(currentShell != null) {
			FieldShell fs = ShellFactory.createFieldShell(ctx);
			currentShell.addField(fs);
		}
	}
	
	@Override
	public void enterMethodDeclaration(MethodDeclarationContext ctx) {
		if(currentShell != null) {
			MethodShell ms = ShellFactory.createMethodShell(ctx);
			currentShell.addMethod(ms);
		}
	}
	
	@Override
	public void exitClassDeclaration(ClassDeclarationContext ctx) {
		shells.add(currentShell);
		currentShell = null;
	}

	public List<ClassShell> getShells() {
		return shells;
	}
}
