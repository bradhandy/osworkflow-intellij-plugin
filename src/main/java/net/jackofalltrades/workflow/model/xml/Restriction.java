package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

/**
 * Defines the structure of the "global-conditions" and "restrict-to" elements.
 *
 * @author bhandy
 */
public interface Restriction extends DomElement, Restrictable {

    ConditionHolder getConditions();

    void setConditions(ConditionHolder conditions);

}
