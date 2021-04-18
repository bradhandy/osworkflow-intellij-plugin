package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTag;

/**
 * Defines the structure of the "global-conditions" and "restrict-to" elements.
 *
 * @author bhandy
 */
public interface Restriction extends DomElement, Restrictable {

    @SubTag("conditions")
    ConditionHolder getConditions();

    void setConditions(ConditionHolder conditions);

}
