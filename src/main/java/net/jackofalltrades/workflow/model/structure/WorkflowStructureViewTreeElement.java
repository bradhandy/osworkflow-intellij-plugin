package net.jackofalltrades.workflow.model.structure;

import com.google.common.collect.Lists;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.util.ArrayUtil;
import com.intellij.util.xml.DomElementNavigationProvider;
import net.jackofalltrades.workflow.model.xml.Action;
import net.jackofalltrades.workflow.model.xml.Workflow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Implementation of StructureViewTreeElement which handles the root "workflow" element in an OS Workflow file.
 *
 * @author bhandy
 */
public class WorkflowStructureViewTreeElement extends DomElementStructureViewTreeElement<Workflow> {

    public WorkflowStructureViewTreeElement(@NotNull Workflow workflow, @Nullable DomElementNavigationProvider navigationProvider) {
        super(workflow, null, navigationProvider);
    }

    @Override
    public TreeElement[] getChildren() {
        List<TreeElement> children = Lists.newArrayList();

        for (Action action : getDomElement().getInitialActionHolder().getActions()) {
            children.add(new WorkflowActionStructureViewTreeElement(action, getDomElement(), getNavigationProvider()));
        }

        return ArrayUtil.toObjectArray(children, TreeElement.class);
    }

}
