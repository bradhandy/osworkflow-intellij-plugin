package net.jackofalltrades.workflow.model.impl.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomElementVisitor;
import net.jackofalltrades.workflow.model.xml.WorkflowElement;

/**
 * Base implementation to support the
 * {@link WorkflowElement#accept(net.jackofalltrades.workflow.model.xml.WorkflowElement.Visitor) accept} and
 * {@link WorkflowElement#acceptChildren(net.jackofalltrades.workflow.model.xml.WorkflowElement.Visitor) acceptChildren}
 * methods.
 *
 * @author bhandy
 */
public abstract class CommonWorkflowElement implements WorkflowElement {

    @Override
    public void accept(Visitor visitor) {
        visitor.visitWorkflowElement(this);
    }

    @Override
    public void acceptChildren(final Visitor visitor) {
        acceptChildren(new DomElementVisitor() {
            @Override
            public void visitDomElement(DomElement element) {
                if (element instanceof WorkflowElement) {
                    ((WorkflowElement) element).accept(visitor);
                }
            }
        });
    }

}
