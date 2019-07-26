package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.Map;

public abstract class RtsAbstractInstruction {
	public abstract boolean execute(ArrayList<String> args, @SuppressWarnings("rawtypes") Map<String, RtsTemplateVariable> variables);
}
