package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileDescription;
import com.intellij.util.xml.DomUtil;
import com.intellij.util.xml.GenericDomValue;
import org.jetbrains.annotations.NotNull;

/**
 * Registers the OS Workflow configuration files with IntelliJ for parsing.
 *
 * @author bhandy
 */
public class WorkflowDomFileDescription extends DomFileDescription<Workflow> {

    public WorkflowDomFileDescription() {
        super(Workflow.class, "workflow", "http://www.opensymphony.com/osworkflow/workflow_2_8.dtd");
    }

    @NotNull
    @Override
    public DomElement getResolveScope(GenericDomValue<?> reference) {
        if (reference instanceof ActionReference) {
            Workflow workflow = DomUtil.getParentOfType(reference, Workflow.class, true);
            if (workflow != null) {
                return workflow.getCommonActionHolder();
            }
        }

        return super.getResolveScope(reference);
    }

    @Override
    public boolean acceptsOtherRootTagNames() {
        return true;
    }

}
