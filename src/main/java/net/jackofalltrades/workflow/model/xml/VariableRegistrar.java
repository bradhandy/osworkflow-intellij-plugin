package net.jackofalltrades.workflow.model.xml;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;

/**
 * Defines the structure of the "register" element.
 *
 * @author bhandy
 */
public interface VariableRegistrar extends DomElement, ArgumentHolder {

    GenericAttributeValue<String> getId();

    @Required
    GenericAttributeValue<PsiClass> getType();

    @Required
    GenericAttributeValue<String> getVariableName();

}
