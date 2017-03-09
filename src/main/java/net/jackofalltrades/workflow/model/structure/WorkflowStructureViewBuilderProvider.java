package net.jackofalltrades.workflow.model.structure;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.xml.XmlStructureViewBuilderProvider;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of XmlStructureViewBuilderProvider to creating the structure view of the workflow files.
 *
 * @author bhandy
 */
public class WorkflowStructureViewBuilderProvider implements XmlStructureViewBuilderProvider {

    @Nullable
    @Override
    public StructureViewBuilder createStructureViewBuilder(@NotNull XmlFile file) {
        return new WorkflowStructureViewBuilder(file);
    }

}
