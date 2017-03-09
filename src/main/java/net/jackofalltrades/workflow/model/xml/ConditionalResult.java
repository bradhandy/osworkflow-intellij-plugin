package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

/**
 * Defines the structure of the "result" element.
 *
 * @author bhandy
 */
public interface ConditionalResult extends DomElement, Result {

    ConditionHolder getConditions();

}
