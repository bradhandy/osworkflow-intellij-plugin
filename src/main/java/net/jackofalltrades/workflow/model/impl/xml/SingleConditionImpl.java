package net.jackofalltrades.workflow.model.impl.xml;

import net.jackofalltrades.workflow.model.xml.SingleCondition;
import net.jackofalltrades.workflow.model.xml.WorkflowElement;

/**
 * Implementation base class for restricting "condition" in the configuration to implementations of Condition.
 *
 * @author bhandy
 */
public abstract class SingleConditionImpl extends CommonWorkflowElement implements SingleCondition {

    @Override
    public void accept(WorkflowElement.Visitor visitor) {
        visitor.visitSingleCondition(this);
    }

    @Override
    public String getBasePsiClassName() {
        return "com.opensymphony.workflow.Condition";
    }

}
