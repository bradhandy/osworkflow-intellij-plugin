package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines the structure of the "steps" element.
 *
 * @author bhandy
 */
public interface StepHolder extends DomElement {

    List<Step> getSteps();

    Step addStep(int index);

    Step addStep();

}
