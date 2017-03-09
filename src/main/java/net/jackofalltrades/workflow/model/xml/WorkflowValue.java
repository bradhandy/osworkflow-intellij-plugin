package net.jackofalltrades.workflow.model.xml;

import com.intellij.util.xml.Convert;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.Required;
import net.jackofalltrades.workflow.model.converter.WorkflowValueConverter;
import org.jetbrains.annotations.NotNull;

/**
 * Defines the structure of the "meta" tag.
 *
 * @author bhandy
 */
@Convert(WorkflowValueConverter.class)
public interface WorkflowValue extends GenericDomValue<Object>, WorkflowElement {

    @Required
    GenericAttributeValue<String> getName();

    void setName(@NotNull String name);

}
