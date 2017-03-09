package net.jackofalltrades.workflow.model.structure;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of the TreeBasedStructureViewBuilder which handles the view model for OS Workflow files.
 *
 * @author bhandy
 */
public class WorkflowStructureViewBuilder extends TreeBasedStructureViewBuilder {

    private XmlFile _xmlFile;

    public WorkflowStructureViewBuilder(XmlFile xmlFile) {
        _xmlFile = xmlFile;
    }

    @NotNull
    @Override
    public StructureViewModel createStructureViewModel(@Nullable Editor editor) {
        return new WorkflowStructureViewTreeModel(_xmlFile, editor);
    }

    @Override
    public boolean isRootNodeShown() {
        return false;
    }

}
