package net.jackofalltrades.workflow.model.structure;

import com.google.common.collect.Lists;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.util.ArrayUtil;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomElementNavigationProvider;
import com.intellij.util.xml.GenericAttributeValue;
import net.jackofalltrades.workflow.model.xml.Action;
import net.jackofalltrades.workflow.model.xml.Result;
import net.jackofalltrades.workflow.model.xml.Step;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bhandy
 * Date: 12/10/12
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkflowActionStructureViewTreeElement extends DomElementStructureViewTreeElement<Action> {

    public WorkflowActionStructureViewTreeElement(@NotNull Action action, @NotNull DomElement parent,
                                                  DomElementNavigationProvider navigationProvider) {
        super(action, parent, navigationProvider);
    }

    @Nullable
    @Override
    public String getPresentableText() {
        if (!getDomElement().isValid()) {
            return super.getPresentableText();
        }

        String actionName = getDomElement().getName().getStringValue();
        String actionId = getDomElement().getId().getStringValue();
        if (actionName == null) {
            actionName = "<unknown action>";
        }
        if (actionId == null) {
            actionId = "[no id]";
        }

        return String.format("%s (%s)", actionName, actionId);
    }

    @Override
    public TreeElement[] getChildren() {
        List<TreeElement> children = Lists.newArrayList();
        List<Result> results = Lists.<Result>newArrayList(getDomElement().getResults().getResults());

        if (getDomElement().getResults().getUnconditionalResult() != null) {
            results.add(getDomElement().getResults().getUnconditionalResult());
        }

        for (Result result : results) {
            GenericAttributeValue<Step> stepValue = result.getStep();
            Step step = (stepValue == null) ? null : stepValue.getValue();
            if (step != null) {
                children.add(new WorkflowStepStructureViewTreeElement(step, getDomElement(), getNavigationProvider()));
            }
        }

        return ArrayUtil.toObjectArray(children, TreeElement.class);
    }

}
