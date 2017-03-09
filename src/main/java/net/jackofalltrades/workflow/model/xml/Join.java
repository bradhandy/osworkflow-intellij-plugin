package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;

/**
 * Defines the structure of the "join" element.
 *
 * @author bhandy
 */
public interface Join extends DomElement {

    @Required
    GenericAttributeValue<String> getId();

    ConditionHolder getConditions();

    Result getUnconditionalResult();

}
