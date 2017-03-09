package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;

import java.util.List;

/**
 * Defines the structure of the "results" element.
 *
 * @author bhandy
 */
public interface ResultHolder extends DomElement {

    List<ConditionalResult> getResults();

    ConditionalResult addResult(int index);

    ConditionalResult addResult();

    Result getUnconditionalResult();

}
