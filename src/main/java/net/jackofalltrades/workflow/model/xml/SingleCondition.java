package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;

/**
 * Defines the structure of the "condition" element.
 *
 * @author bhandy
 */
public interface SingleCondition extends DomElement, Condition, ArgumentHolder, PsiClassRestrictable, WorkflowElement {

    GenericAttributeValue<String> getId();

    @Required
    GenericAttributeValue<String> getType();

    GenericAttributeValue<Boolean> getNegate();

    GenericAttributeValue<String> getName();

}
