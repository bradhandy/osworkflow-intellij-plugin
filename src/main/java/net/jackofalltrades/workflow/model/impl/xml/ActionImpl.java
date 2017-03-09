package net.jackofalltrades.workflow.model.impl.xml;

import net.jackofalltrades.workflow.model.xml.Action;

public abstract class ActionImpl extends CommonWorkflowElement implements Action {

    @Override
    public void accept(Visitor visitor) {
        visitor.visitAction(this);
    }

}
