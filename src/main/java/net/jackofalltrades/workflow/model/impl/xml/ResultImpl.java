package net.jackofalltrades.workflow.model.impl.xml;

import net.jackofalltrades.workflow.model.xml.Result;

public abstract class ResultImpl extends CommonWorkflowElement implements Result {

    @Override
    public void accept(Visitor visitor) {
        visitor.visitResult(this);
    }

}
