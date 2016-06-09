/*
 * Copyright 2016 Olivier Grégoire.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.ogregoire.entity.inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Olivier Grégoire
 */
class ImplementationProvider<T> implements Provider<T> {

  private final Injector2 injector;
  private final Constructor<?> constructor;

  public ImplementationProvider(Injector2 injector, Class<? extends T> implementationType) {
    this.injector = injector;
    Iterator<Constructor<?>> constructors = Arrays.stream(implementationType.getConstructors())
        .filter(c -> c.isAnnotationPresent(Inject.class))
        .iterator();
    if (!constructors.hasNext()) {
      throw new RuntimeException("No constructor annotated with @Inject");
    }
    constructor = constructors.next();
    if (constructors.hasNext()) {
      throw new RuntimeException("Too many constructors annotated with @Inject");
    }
    constructor.setAccessible(true);
  }

  @Override
  public T get() {
    try {
      Class[] parameterTypes = constructor.getParameterTypes();
      Object[] parameters = new Object[parameterTypes.length];
      for (int i = 0; i < parameterTypes.length; i++) {
        parameters[i] = injector.getInstance(parameterTypes[i]);
      }
      return (T) constructor.newInstance(parameters);
    } catch (RuntimeException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
      throw new RuntimeException("Impossible to provision " + constructor.getDeclaringClass().getName(), ex);
    }
  }

}
