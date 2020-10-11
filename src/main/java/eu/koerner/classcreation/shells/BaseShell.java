package eu.koerner.classcreation.shells;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseShell {
	private List<String> modifiers = new ArrayList<String>();
	private List<String> annotations = new ArrayList<String>();

	public List<String> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}

	public List<String> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}
}
