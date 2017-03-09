package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

/**
 * Base interface for any element having a root "conditions" element.
 *
 * @author bhandy
 */
public interface Restrictable extends DomElement {

    ConditionHolder getConditions();

}
