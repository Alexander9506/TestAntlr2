package eu.koerner.classcreation;

import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import eu.koerner.antlr.Java8Lexer;
import eu.koerner.antlr.Java8Parser;
import eu.koerner.classcreation.shells.BaseShell;
import eu.koerner.classcreation.shells.ClassShell;

public class EntryPoint {
	public static String javaFilePath = "C:\\Users\\alexa\\Documents\\test\\antlrTestClass.java";
	
	
	public static void main(String[] args) {
		Java8Lexer lexer = null;
		try {
			lexer = new Java8Lexer(CharStreams.fromFileName(javaFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		Java8Parser parser = new Java8Parser(tokens);
		ParseTree tree = parser.compilationUnit();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		ClassObservationListener listener = new ClassObservationListener();
		walker.walk(listener, tree);
		
		List<ClassShell> shells = listener.getShells();
		ClassShell proxyClass = ProxyCreator.createNewProxyClass("BausteinModel", shells.get(0));
		
		ClassFileBuilder.createClassFile("C:\\Users\\alexa\\Documents\\test\\"+ proxyClass.getClassName() + ".java", proxyClass);
		
		System.out.println("Proxy erstellt");
	}

}
