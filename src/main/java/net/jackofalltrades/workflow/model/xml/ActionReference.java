package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

/**
 * Defines the structure of the "common-action" element.
 *
 * @author bhandy
 */
public interface ActionReference extends DomElement {

    @Attribute("id")
    GenericAttributeValue<Action> getAction();

}
