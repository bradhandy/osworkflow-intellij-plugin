package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTag;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/**
 * Defines the structure of the "workflow" element.
 *
 * @author bhandy
 */
public interface Workflow extends DomElement {

    @SubTagList("meta")
    List<WorkflowValue> getProperties();

    @SubTagList("meta")
    WorkflowValue addProperty(int index);

    @SubTagList("meta")
    WorkflowValue addProperty();

    @SubTag("registers")
    VariableRegistrarHolder getRegisterHolder();

    @SubTag("trigger-functions")
    TriggerFunctionHolder getTriggerFunctionHolder();

    @SubTag("global-conditions")
    Restriction getGlobalConditions();

    @SubTag("initial-actions")
    ActionHolder getInitialActionHolder();

    @SubTag("global-actions")
    ActionHolder getGlobalActionHolder();

    @SubTag("common-actions")
    ActionHolder getCommonActionHolder();

    @SubTag("steps")
    StepHolder getStepHolder();

    @SubTag("splits")
    SplitHolder getSplitHolder();

    @SubTag("join")
    JoinHolder getJoinHolder();

}
