package net.jackofalltrades.workflow.model.xml;

import com.google.common.base.Strings;
import com.intellij.ide.presentation.Presentation;
import com.intellij.ide.presentation.PresentationProvider;
import com.intellij.util.xml.Documentation;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.NameValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Defines the structure of the "step" element.
 *
 * @author bhandy
 */
@Presentation(provider = Step.StepPresentationProvider.class)
public interface Step extends DomElement, WorkflowElement {

    @Required
    @NameValue
    GenericAttributeValue<Integer> getId();

    @Required
    @Documentation
    GenericAttributeValue<String> getName();

    @SubTag("meta")
    List<WorkflowValue> getProperties();

    FunctionProvider getPreFunctions();

    @SubTag("external-permissions")
    PermissionHolder getExternalPermissionsHolder();

    @SubTag("actions")
    StepActionHolder getActionHolder();

    FunctionProvider getPostFunctions();

    boolean isSame(DomElement element);

    public static final class StepPresentationProvider extends PresentationProvider<Step> {

        @Nullable
        @Override
        public String getName(Step step) {
            GenericAttributeValue<Integer> id = step.getId();
            GenericAttributeValue<String> name = step.getName();

            if (Strings.isNullOrEmpty(id.getStringValue())) {
                return null;
            }

            String nameValue = (Strings.isNullOrEmpty(name.getStringValue())) ? "No Name" : name.getStringValue();
            return String.format("%s  (%d)", nameValue, id.getValue());
        }

        @Nullable
        @Override
        public String getTypeName(Step step) {
            return "OS Workflow Step";
        }

    }
}
