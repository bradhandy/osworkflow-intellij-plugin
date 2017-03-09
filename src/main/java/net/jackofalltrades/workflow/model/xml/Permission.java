package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTag;

/**
 * Defines the structure of the "permission" element.
 *
 * @author bhandy
 */
public interface Permission extends DomElement {

    GenericAttributeValue<String> getId();

    @Required
    GenericAttributeValue<String> getName();

    @SubTag("restrict-to")
    Restriction getRestriction();

}
