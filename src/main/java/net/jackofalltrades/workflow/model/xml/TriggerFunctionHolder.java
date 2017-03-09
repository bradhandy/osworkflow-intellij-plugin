package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines the structure of the "trigger-functions" element.
 *
 * @author bhandy
 */
public interface TriggerFunctionHolder extends DomElement {

    List<TriggerFunction> getTriggerFunctions();

    TriggerFunction addTriggerFunction(int index);

    TriggerFunction addTriggerFunction();

}
