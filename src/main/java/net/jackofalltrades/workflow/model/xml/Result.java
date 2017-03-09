package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.Resolve;
import net.jackofalltrades.workflow.model.converter.WorkflowStepSelfReferenceResolveConverter;

/**
 * Defines the structure of the "unconditional-result" and the base structure of the "result" tags.
 *
 * @author bhandy
 */
public interface Result extends DomElement, WorkflowElement {

    GenericAttributeValue<String> getId();

    GenericAttributeValue<String> getDisplayName();

    GenericAttributeValue<String> getDueDate();

    @Required
    GenericAttributeValue<String> getOldStatus();

    GenericAttributeValue<String> getStatus();

    @Convert(WorkflowStepSelfReferenceResolveConverter.class)
    GenericAttributeValue<Step> getStep();

    GenericAttributeValue<String> getOwner();

    @Resolve(Join.class)
    GenericAttributeValue<Join> getJoin();

    @Resolve(Split.class)
    GenericAttributeValue<Split> getSplit();

    ValidatorHolder getValidators();

    FunctionProviderHolder getPreFunctions();

    FunctionProviderHolder getPostFunctions();

}
