package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/**
 * Root interface for any type which can accept argument definitions via the "arg" tag.
 *
 * @author bhandy
 */
public interface ArgumentHolder extends DomElement {

    @SubTagList("arg")
    List<WorkflowValue> getArguments();

    @SubTagList("arg")
    WorkflowValue addArgument(int index);

    @SubTagList("arg")
    WorkflowValue addArgument();

}
