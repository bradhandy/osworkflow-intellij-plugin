package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines a container to support a group of VariableRegistrar instances.
 *
 * @author bhandy
 */
public interface VariableRegistrarHolder extends DomElement {

    List<VariableRegistrar> getRegisters();

    VariableRegistrar addRegister(int index);

    VariableRegistrar addRegister();

}
