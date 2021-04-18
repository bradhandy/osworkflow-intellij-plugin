package net.jackofalltrades.spring;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.spring.SpringManager;
import com.intellij.spring.contexts.model.SpringModel;
import com.intellij.spring.model.CommonSpringBean;
import com.intellij.spring.model.SpringBeanPointer;
import com.intellij.spring.model.SpringModelSearchParameters;
import com.intellij.spring.model.utils.SpringBeanUtils;
import com.intellij.spring.model.utils.SpringModelSearchers;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.Converter;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomUtil;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.ResolvingConverter;
import com.intellij.util.xml.converters.values.GenericDomValueConvertersRegistry;
import net.jackofalltrades.workflow.model.xml.ArgumentHolder;
import net.jackofalltrades.workflow.model.xml.FunctionProvider;
import net.jackofalltrades.workflow.model.xml.PsiClassRestrictable;
import net.jackofalltrades.workflow.model.xml.SingleCondition;
import net.jackofalltrades.workflow.model.xml.WorkflowValue;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Implementation of GenericDomValueConvertersRegistry.Provider to support resolving Spring bean
 * references within the workflow.
 *
 * @author bhandy
 */
public class SpringBeanConverterProvider implements GenericDomValueConvertersRegistry.Provider {

  @Override
  public Condition<Pair<PsiType, GenericDomValue>> getCondition() {
    return SpringBeanConditionOrFunctionCondition.INSTANCE;
  }

  @Override
  public Converter getConverter() {
    return SpringBeanReferenceConverter.INSTANCE;
  }

  private static class SpringBeanConditionOrFunctionCondition
      implements Condition<Pair<PsiType, GenericDomValue>> {

    static final SpringBeanConditionOrFunctionCondition INSTANCE =
        new SpringBeanConditionOrFunctionCondition();

    @Override
    public boolean value(Pair<PsiType, GenericDomValue> pair) {
      DomElement element = pair.getSecond();
      if (element instanceof WorkflowValue) {
        GenericAttributeValue<String> argumentName = ((WorkflowValue) element).getName();
        ArgumentHolder holder = DomUtil.getParentOfType(element, ArgumentHolder.class, true);
        if ("bean.name".equals(argumentName.getStringValue())
            && (holder instanceof SingleCondition || holder instanceof FunctionProvider)) {
          GenericAttributeValue<String> type =
              (holder instanceof FunctionProvider)
                  ? ((FunctionProvider) holder).getType()
                  : ((SingleCondition) holder).getType();
          return "spring".equals(type.getStringValue());
        }
      }

      return false;
    }
  }

  private static class SpringBeanReferenceConverter extends ResolvingConverter<CommonSpringBean> {

    static final SpringBeanReferenceConverter INSTANCE = new SpringBeanReferenceConverter();

    @NotNull
    @Override
    public Collection<? extends CommonSpringBean> getVariants(ConvertContext context) {
      SpringModel model = getSpringModel(context);
      if (model == null) {
        return Lists.newArrayList();
      }

      PsiClassRestrictable restrictable =
          DomUtil.getParentOfType(context.getInvocationElement(), PsiClassRestrictable.class, true);
      if (restrictable == null) {
        return null;
      }

      JavaPsiFacade facade = JavaPsiFacade.getInstance(context.getProject());
      PsiClass restrictedClass =
          facade.findClass(
              restrictable.getBasePsiClassName(),
              GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(context.getModule()));
      if (restrictedClass == null) {
        return null;
      }

      Collection<SpringBeanPointer<?>> beans =
          Collections2.filter(
              SpringModelSearchers.findBeans(
                  model,
                  SpringModelSearchParameters.byClass(restrictedClass)
                      .effectiveBeanTypes()
                      .withInheritors()),
              new Predicate<SpringBeanPointer>() {
                @Override
                public boolean apply(@Nullable SpringBeanPointer beanPointer) {
                  return beanPointer != null && !beanPointer.isAbstract();
                }
              });
      Collection<CommonSpringBean> commonBeans =
          Collections2.transform(
              beans,
              new Function<SpringBeanPointer, CommonSpringBean>() {
                @Override
                public CommonSpringBean apply(@Nullable SpringBeanPointer beanPointer) {
                  return (beanPointer == null) ? null : beanPointer.getSpringBean();
                }
              });

      return Collections2.filter(commonBeans, Predicates.notNull());
    }

    @Nullable
    @Override
    public CommonSpringBean fromString(@Nullable @NonNls String name, ConvertContext context) {
      if (name == null) {
        return null;
      }

      SpringModel model = getSpringModel(context);
      if (model == null) {
        return null;
      }

      SpringBeanPointer pointer = SpringBeanUtils.getInstance().findBean(model, name);

      return (pointer == null) ? null : pointer.getSpringBean();
    }

    @Nullable
    @Override
    public String toString(@Nullable CommonSpringBean pointer, ConvertContext context) {
      return (pointer == null) ? null : pointer.getBeanName();
    }

    @Nullable
    private SpringModel getSpringModel(ConvertContext context) {
      Module module = context.getModule();
      SpringManager manager = SpringManager.getInstance(context.getProject());

      if (module == null) {
        return null;
      }

      return manager.getCombinedModel(module);
    }
  }
}
