package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;

import java.util.List;

/**
 * Defines the structure of the "split" element.
 *
 * @author bhandy
 */
public interface Split extends DomElement {

    @Required
    GenericAttributeValue<String> getId();

    List<Result> getUnconditionalResults();

    Result addUnconditionalResult(int index);

    Result addUnconditionalResult();

}
