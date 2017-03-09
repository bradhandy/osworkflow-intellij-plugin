package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.NameValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTag;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Defines the structure of the "action" element.
 *
 * @author bhandy
 */
public interface Action extends DomElement, WorkflowElement {

    @Required
    @NameValue
    GenericAttributeValue<Integer> getId();

    void setId(@NotNull Integer id);

    @Required
    GenericAttributeValue<String> getName();

    void setName(@NotNull String name);

    GenericAttributeValue<String> getView();

    void setView(@NotNull String view);

    GenericAttributeValue<Boolean> getAuto();

    void setAuto(@NotNull Boolean auto);

    GenericAttributeValue<Boolean> getFinish();

    void setFinish(@NotNull Boolean finish);

    @SubTagList("meta")
    List<WorkflowValue> getProperties();

    @SubTagList("meta")
    WorkflowValue addProperty(int index);

    @SubTagList("meta")
    WorkflowValue addProperty();

    @SubTag("restrict-to")
    Restriction getRestriction();

    ValidatorHolder getValidators();

    FunctionProviderHolder getPreFunctions();

    @Required
    ResultHolder getResults();

    FunctionProviderHolder getPostFunctions();

}
