package net.jackofalltrades.workflow.model.impl.xml;

import net.jackofalltrades.workflow.model.xml.ConditionalResult;
import net.jackofalltrades.workflow.model.xml.WorkflowElement;

public abstract class ConditionalResultImpl extends CommonWorkflowElement implements ConditionalResult {

    @Override
    public void accept(WorkflowElement.Visitor visitor) {
        visitor.visitConditionalResult(this);
    }

}
