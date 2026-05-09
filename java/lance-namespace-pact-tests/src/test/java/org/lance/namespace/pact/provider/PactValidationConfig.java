/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lance.namespace.pact.provider;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.metadata.BeanDescriptor;
import jakarta.validation.metadata.ConstructorDescriptor;
import jakarta.validation.metadata.MethodDescriptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Pact-profile-only configuration that disables Bean Validation.
 *
 * <p>Pact consumer tests omit required request body fields (e.g., {@code location} in {@code
 * RegisterTableRequest}) because pact is used to verify server response contracts, not to test
 * input validation. The generated Spring server interfaces apply {@code @Valid} and
 * {@code @NotNull} constraints. This configuration replaces the default JSR-303 validator with a
 * no-op implementation so that pact verification can proceed without triggering Bean Validation
 * failures for missing fields.
 */
@Configuration
@Profile("pact")
public class PactValidationConfig {

  /** No-op {@link Validator} that accepts all inputs without running constraints. */
  @Bean
  public Validator validator() {
    return new NoOpValidator();
  }

  private static final class NoOpValidator implements Validator {

    private static final ExecutableValidator NO_OP_EXECUTABLE = new NoOpExecutableValidator();

    @Override
    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
      return Set.of();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateProperty(
        T object, String propertyName, Class<?>... groups) {
      return Set.of();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateValue(
        Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
      return Set.of();
    }

    @Override
    public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
      return new NoOpBeanDescriptor();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> type) {
      if (type.isInstance(this)) {
        return (T) this;
      }
      return null;
    }

    @Override
    public ExecutableValidator forExecutables() {
      return NO_OP_EXECUTABLE;
    }
  }

  private static final class NoOpExecutableValidator implements ExecutableValidator {

    @Override
    public <T> Set<ConstraintViolation<T>> validateParameters(
        T object, Method method, Object[] parameterValues, Class<?>... groups) {
      return Set.of();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateReturnValue(
        T object, Method method, Object returnValue, Class<?>... groups) {
      return Set.of();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorParameters(
        Constructor<? extends T> constructor, Object[] parameterValues, Class<?>... groups) {
      return Set.of();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorReturnValue(
        Constructor<? extends T> constructor, T createdObject, Class<?>... groups) {
      return Set.of();
    }
  }

  private static final class NoOpBeanDescriptor implements BeanDescriptor {

    @Override
    public boolean isBeanConstrained() {
      return false;
    }

    @Override
    public jakarta.validation.metadata.PropertyDescriptor getConstraintsForProperty(
        String propertyName) {
      return null;
    }

    @Override
    public Set<jakarta.validation.metadata.PropertyDescriptor> getConstrainedProperties() {
      return Set.of();
    }

    @Override
    public MethodDescriptor getConstraintsForMethod(String methodName, Class<?>... parameterTypes) {
      return null;
    }

    @Override
    public Set<MethodDescriptor> getConstrainedMethods(
        jakarta.validation.metadata.MethodType methodType,
        jakarta.validation.metadata.MethodType... methodTypes) {
      return Set.of();
    }

    @Override
    public ConstructorDescriptor getConstraintsForConstructor(Class<?>... parameterTypes) {
      return null;
    }

    @Override
    public Set<ConstructorDescriptor> getConstrainedConstructors() {
      return Set.of();
    }

    @Override
    public boolean hasConstraints() {
      return false;
    }

    @Override
    public Class<?> getElementClass() {
      return Object.class;
    }

    @Override
    public Set<jakarta.validation.metadata.ConstraintDescriptor<?>> getConstraintDescriptors() {
      return Set.of();
    }

    @Override
    public jakarta.validation.metadata.ElementDescriptor.ConstraintFinder findConstraints() {
      return null;
    }
  }
}
