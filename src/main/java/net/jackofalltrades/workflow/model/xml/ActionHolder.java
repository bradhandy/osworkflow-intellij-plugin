package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines the structure of the "global-actions", "initial-actions" and "common-actions" elements.
 *
 * @author bhandy
 */
public interface ActionHolder extends DomElement {

    List<Action> getActions();

    Action addAction(int index);

    Action addAction();

}
