package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;
import com.intellij.util.xml.SubTagsList;

import java.util.List;

/**
 * Defines the structure of the "conditions" element.
 *
 * @author bhandy
 */
public interface ConditionHolder extends DomElement, Condition, WorkflowElement {

    GenericAttributeValue<String> getType();

    @SubTagsList({"conditions", "condition"})
    List<Condition> getConditions();

    @SubTagsList(value = {"conditions", "condition"}, tagName = "condition")
    SingleCondition addSingleCondition(int index);

    @SubTagsList(value = {"conditions", "condition"}, tagName = "condition")
    SingleCondition addSingleCondition();

    @SubTagsList(value = {"conditions", "condition"}, tagName = "conditions")
    ConditionHolder addConditionHolder(int index);

    @SubTagsList(value = {"conditions", "condition"}, tagName = "conditions")
    ConditionHolder addConditionHolder();

    @SubTagList("condition")
    List<SingleCondition> getSingleConditions();

    @SubTagList("conditions")
    List<ConditionHolder> getConditionHolders();

}
