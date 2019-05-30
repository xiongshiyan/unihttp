package top.jfunc.common.http.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import top.jfunc.common.http.interfacing.HttpServiceCreator;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * @author xiongshiyan
 * 
 * @see HttpServiceFactoryBean
 * @since 1.1.2
 */
public class ClassPathHttpServiceScanner extends ClassPathBeanDefinitionScanner {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathHttpServiceScanner.class);

  private HttpServiceCreator httpServiceCreator;
  private Class<? extends Annotation> annotationClass;

  public ClassPathHttpServiceScanner(BeanDefinitionRegistry registry , HttpServiceCreator httpServiceCreator) {
    super(registry, false);
    this.httpServiceCreator = httpServiceCreator;
  }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    /**
   * Configures parent scanner to search for the right interfaces. It can search
   * for all interfaces or just for those that extends a markerInterface or/and
   * those annotated with the annotationClass
   */
  public void registerFilters() {
      // if specified, use the given annotation and / or marker interface
      if (this.annotationClass != null) {
          addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
      }
    // exclude package-info.java
    addExcludeFilter((metadataReader, metadataReaderFactory) -> {
      String className = metadataReader.getClassMetadata().getClassName();
      return className.endsWith("package-info");
    });
  }

  /**
   * Calls the parent search that will search and register all the candidates.
   * Then the registered objects are post processed to set them as
   * MapperFactoryBeans
   */
  @Override
  public Set<BeanDefinitionHolder> doScan(String... basePackages) {
    Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

    if (beanDefinitions.isEmpty()) {
      LOGGER.warn("No HttpService was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
    } else {
      processBeanDefinitions(beanDefinitions);
    }

    return beanDefinitions;
  }

  private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
    GenericBeanDefinition definition;
    for (BeanDefinitionHolder holder : beanDefinitions) {
        definition = (GenericBeanDefinition) holder.getBeanDefinition();
        String beanClassName = definition.getBeanClassName();
        LOGGER.debug("Creating MapperFactoryBean with name '" + holder.getBeanName()
                + "' and '" + beanClassName + "' mapperInterface");

        // the mapper interface is the original class of the bean
        // but, the actual class of the bean is MapperFactoryBean
        ConstructorArgumentValues constructorArgumentValues = definition.getConstructorArgumentValues();
        constructorArgumentValues.addIndexedArgumentValue(0, httpServiceCreator);
        constructorArgumentValues.addIndexedArgumentValue(1, beanClassName);
        definition.setBeanClass(HttpServiceFactoryBean.class);

        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_NO);
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
  }
}
