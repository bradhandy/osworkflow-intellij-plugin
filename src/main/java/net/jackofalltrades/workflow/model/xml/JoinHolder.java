package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines the structure of the "joins" element.
 *
 * @author bhandy
 */
public interface JoinHolder extends DomElement {

    List<Join> getJoins();

    Join addJoin(int index);

    Join addJoin();

}
