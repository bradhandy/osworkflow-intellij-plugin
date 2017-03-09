package net.jackofalltrades.workflow.model.converter;

import com.intellij.util.xml.Converter;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.WrappingConverter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of WrappingConverter which uses the WorkflowValueConverterRegistry to determine when to use converter
 * when parsing the OS Workflow configuration files.
 *
 * @author bhandy
 */
public class WorkflowValueConverter extends WrappingConverter {

    @Nullable
    @Override
    public Converter getConverter(@NotNull GenericDomValue domElement) {
        return WorkflowValueConverterRegistry.getInstance().getConverter(domElement);
    }

}
