package net.jackofalltrades.workflow.model.converter;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.Converter;
import com.intellij.util.xml.DomUtil;
import com.intellij.util.xml.ExtendClass;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.PsiClassConverter;
import com.intellij.util.xml.converters.values.GenericDomValueConvertersRegistry;
import net.jackofalltrades.workflow.model.xml.PsiClassRestrictable;
import net.jackofalltrades.workflow.model.xml.WorkflowValue;
import org.jetbrains.annotations.Nullable;

/**
 * Loads custom Converter implementations from an extending plugin via an ExtensionPoint.
 *
 * @author bhandy
 */
public class WorkflowValueConverterRegistry {

  private static final ExtensionPointName<GenericDomValueConvertersRegistry.Provider> VALUE_CONVERTERS_EXTENSION
      = ExtensionPointName.create("net.jackofalltrades.workflow.valueConverter");

  private static final Condition<Pair<PsiType, GenericDomValue>> CLASS_NAME_ARGUMENT_CONDITION =
      new Condition<Pair<PsiType, GenericDomValue>>() {
        @Override
        public boolean value(Pair<PsiType, GenericDomValue> pair) {
          if (pair.getSecond() instanceof WorkflowValue) {
            WorkflowValue value = (WorkflowValue) pair.getSecond();
            return "arg".equals(value.getXmlTag().getName())
                && "class.name".equals(value.getName().getStringValue());
          }

          return false;
        }
      };

  private GenericDomValueConvertersRegistry _registry;

  public WorkflowValueConverterRegistry() {
    _registry = new GenericDomValueConvertersRegistry();

    initConverters();
  }

  public static WorkflowValueConverterRegistry getInstance() {
    return ServiceManager.getService(WorkflowValueConverterRegistry.class);
  }

  public Converter<?> getConverter(GenericDomValue domValue) {
    return _registry.getConverter(domValue, null);
  }

  private void initConverters() {
    _registry.registerConverter(new CustomPsiClassConverter(), CLASS_NAME_ARGUMENT_CONDITION);
    for (GenericDomValueConvertersRegistry.Provider provider : VALUE_CONVERTERS_EXTENSION.getExtensionList()) {
      _registry.registerConverter(provider.getConverter(), provider.getCondition());
    }
  }

  private static class CustomPsiClassConverter extends PsiClassConverter {

    @Override
    protected JavaClassReferenceProvider createClassReferenceProvider(GenericDomValue<PsiClass> domValue,
                                                                      final ConvertContext context, ExtendClass extendClass) {
      JavaClassReferenceProvider provider = new JavaClassReferenceProvider() {
        @Nullable
        @Override
        public GlobalSearchScope getScope(Project project) {
          return CustomPsiClassConverter.this.getScope(context);
        }
      };

      PsiClassRestrictable parent = DomUtil.getParentOfType(domValue, PsiClassRestrictable.class, true);
      if (parent != null) {
        provider.setOption(JavaClassReferenceProvider.EXTEND_CLASS_NAMES,
            new String[]{parent.getBasePsiClassName()});
      }
      provider.setOption(JavaClassReferenceProvider.INSTANTIATABLE, Boolean.TRUE);
      provider.setOption(JavaClassReferenceProvider.CONCRETE, Boolean.TRUE);
      provider.setOption(JavaClassReferenceProvider.NOT_INTERFACE, Boolean.TRUE);
      provider.setOption(JavaClassReferenceProvider.NOT_ENUM, Boolean.TRUE);
      provider.setOption(JavaClassReferenceProvider.JVM_FORMAT, Boolean.TRUE);
      provider.setSoft(true);

      return provider;
    }

  }

}
