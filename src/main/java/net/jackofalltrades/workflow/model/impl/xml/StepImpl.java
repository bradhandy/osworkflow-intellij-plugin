package net.jackofalltrades.workflow.model.impl.xml;

import com.intellij.util.xml.DomElement;
import net.jackofalltrades.workflow.model.xml.Step;
import net.jackofalltrades.workflow.model.xml.WorkflowElement;

public abstract class StepImpl extends CommonWorkflowElement implements Step {

    @Override
    public void accept(WorkflowElement.Visitor visitor) {
        visitor.visitStep(this);
    }

    @Override
    public boolean isSame(DomElement element) {
        if (element instanceof Step) {
            Step stepElement = (Step) element;
            Integer stepId = stepElement.getId() == null ? 0 : stepElement.getId().getValue();
            Integer myId = getId() == null ? 0 : getId().getValue();

            return (stepId > 0 && myId > 0 && stepId.compareTo(myId) == 0);
        }

        return false;
    }
}
