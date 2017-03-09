package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;

/**
 * Defines the structure of the "function" element.
 *
 * @author bhandy
 */
public interface FunctionProvider extends DomElement, ArgumentHolder, PsiClassRestrictable, WorkflowElement {

    GenericAttributeValue<String> getId();

    @Required
    GenericAttributeValue<String> getType();

    GenericAttributeValue<String> getName();

}
