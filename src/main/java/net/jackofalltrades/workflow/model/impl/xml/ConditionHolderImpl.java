package net.jackofalltrades.workflow.model.impl.xml;

import com.intellij.util.xml.DomUtil;
import net.jackofalltrades.workflow.model.xml.ConditionHolder;

public abstract class ConditionHolderImpl extends CommonWorkflowElement implements ConditionHolder {

    @Override
    public void accept(Visitor visitor) {
        if (DomUtil.getParentOfType(this, ConditionHolder.class, true) == null) {
            visitor.visitRootConditionHolder(this);
        } else {
            visitor.visitConditionHolder(this);
        }
    }

}
