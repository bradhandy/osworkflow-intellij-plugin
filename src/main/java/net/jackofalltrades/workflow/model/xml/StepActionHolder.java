package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines the structure of the "actions" element.
 *
 * @author bhandy
 */
public interface StepActionHolder extends DomElement, ActionHolder {

    List<ActionReference> getCommonActions();

    ActionReference addCommonAction(int index);

    ActionReference addCommonAction();

}
