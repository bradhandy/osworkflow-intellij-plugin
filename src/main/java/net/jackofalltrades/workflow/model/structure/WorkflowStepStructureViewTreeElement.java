package net.jackofalltrades.workflow.model.structure;

import com.google.common.collect.Lists;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.util.ArrayUtil;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomElementNavigationProvider;
import com.intellij.util.xml.GenericAttributeValue;
import net.jackofalltrades.workflow.model.xml.Action;
import net.jackofalltrades.workflow.model.xml.Result;
import net.jackofalltrades.workflow.model.xml.ResultHolder;
import net.jackofalltrades.workflow.model.xml.Step;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bhandy
 * Date: 12/11/12
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkflowStepStructureViewTreeElement extends DomElementStructureViewTreeElement<Step> {

    public WorkflowStepStructureViewTreeElement(@NotNull Step domElement, @NotNull DomElement parent,
                                                DomElementNavigationProvider navigationProvider) {
        super(domElement, parent, navigationProvider);
    }

    @Override
    public TreeElement[] getChildren() {
        List<Result> results = Lists.newArrayList();
        for (Action action : getDomElement().getActionHolder().getActions()) {
            ResultHolder resultsHolder = action.getResults();
            results.addAll(resultsHolder.getResults());
            if (resultsHolder.getUnconditionalResult() != null) {
                results.add(resultsHolder.getUnconditionalResult());
            }
        }

        List<TreeElement> children = Lists.newArrayList();
        for (Result result : results) {
            GenericAttributeValue<Step> stepValue = result.getStep();
            Step step = (stepValue == null) ? null : stepValue.getValue();

            if (step != null && !getDomElement().isSame(step)) {
                children.add(new WorkflowStepStructureViewTreeElement(step, getDomElement(), getNavigationProvider()));
            }
        }

        return ArrayUtil.toObjectArray(children, TreeElement.class);
    }

}
