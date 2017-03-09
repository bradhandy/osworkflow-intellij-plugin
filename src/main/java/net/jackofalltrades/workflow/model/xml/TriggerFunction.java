package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

/**
 * Defines the structure of the "trigger-function" tag.
 *
 * @author bhandy
 */
public interface TriggerFunction extends DomElement {

    GenericAttributeValue<String> getId();

    FunctionProvider getFunction();

}
