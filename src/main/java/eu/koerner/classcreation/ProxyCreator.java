package eu.koerner.classcreation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import eu.koerner.classcreation.shells.ClassShell;
import eu.koerner.classcreation.shells.ClassShell.ClassModifier;
import eu.koerner.classcreation.shells.MethodShell;

public class ProxyCreator {
	public static String PROXY_SUPER_CLASS = "BaseProxy";
	public static String PROXY_OBJECT_NAME = "sourceObject";
	
	public static ClassShell createNewProxyClass(String newClassName, ClassShell parentShell) {
		
		ClassShell proxyShell =  new ClassShell(newClassName);
		proxyShell.setModifiers(Arrays.asList(ClassModifier.cPublic.toString()));
		proxyShell.setSuperClass(PROXY_SUPER_CLASS+"<" + parentShell.getClassName() +">");
		
		List<MethodShell> parentGetter = getGetterFromParent(parentShell);
		List<MethodShell> parentSetter = getSetterFromParent(parentShell);
		
		List<MethodShell> proxyGetter = createProxyGetterFromParentGetters(parentGetter);
		List<MethodShell> proxySetter = createProxySetterFromParentSetters(parentSetter);

		proxyGetter.stream().forEach(method -> proxyShell.addMethod(method));
		proxySetter.stream().forEach(method -> proxyShell.addMethod(method));

		return proxyShell;
	}

	private static List<MethodShell> createProxySetterFromParentSetters(List<MethodShell> parentSetters) {
		List<MethodShell> proxyMethods = new ArrayList<MethodShell>();
		for (MethodShell parentSetter : parentSetters) {
			MethodShell proxyMethod = new MethodShell();
			proxyMethod.setModifiers(parentSetter.getModifiers());
			proxyMethod.setName(parentSetter.getName());
			proxyMethod.setResultTyp(parentSetter.getResultTyp());
			proxyMethod.setParameters(parentSetter.getParameters());
			proxyMethod.setBody(
					"this." + PROXY_OBJECT_NAME + " = " + parentSetter.getParameters().get(0).getVariableName() + ";");
			
			proxyMethods.add(proxyMethod);
		}
		return proxyMethods;
	}

	private static List<MethodShell> createProxyGetterFromParentGetters(List<MethodShell> parentGetters) {
		List<MethodShell> proxyMethods = new ArrayList<MethodShell>();
		for (MethodShell parentGetter : parentGetters) {
			MethodShell proxyMethod = new MethodShell();
			proxyMethod.setModifiers(parentGetter.getModifiers());
			proxyMethod.setName(parentGetter.getName());
			proxyMethod.setResultTyp(parentGetter.getResultTyp());
			proxyMethod.setBody(
					"return this."+PROXY_OBJECT_NAME+parentGetter.getName()+"();"
					);
			
			proxyMethods.add(proxyMethod);
		}
		return proxyMethods;
	}

	private static List<MethodShell> getGetterFromParent(ClassShell parentShell) {
		//Only getter with 0 parameters
		return filterMethodsFromParent(parentShell.getMethods(),
				m -> m.getName().toLowerCase().startsWith("get") && m.getParameters().size() == 0);
	}

	private static List<MethodShell> getSetterFromParent(ClassShell parentShell) {
		
		return filterMethodsFromParent(parentShell.getMethods(),
				m -> m.getName().toLowerCase().startsWith("set") && m.getParameters().size() == 1);
	}
	
	private static List<MethodShell> filterMethodsFromParent(List<MethodShell> methods, Predicate<MethodShell> predicate) {
		return methods.stream()
				.filter(predicate)
				.collect(Collectors.toList());
	}
}
