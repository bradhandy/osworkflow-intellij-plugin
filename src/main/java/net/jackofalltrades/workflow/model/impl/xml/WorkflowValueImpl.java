package net.jackofalltrades.workflow.model.impl.xml;

import com.intellij.util.xml.DomElement;
import net.jackofalltrades.workflow.model.xml.FunctionProvider;
import net.jackofalltrades.workflow.model.xml.SingleCondition;
import net.jackofalltrades.workflow.model.xml.WorkflowValue;

public abstract class WorkflowValueImpl extends CommonWorkflowElement implements WorkflowValue {

    @Override
    public void accept(Visitor visitor) {
        DomElement parent = getParent();

        if (parent instanceof FunctionProvider) {
            visitor.visitFunctionProviderArgument(this);
        } else if (parent instanceof SingleCondition) {
            visitor.visitConditionArgument(this);
        } else {
            visitor.visitWorkflowElement(this);
        }
    }

}
