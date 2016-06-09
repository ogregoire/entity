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

import java.util.Optional;

/**
 *
 * @author Olivier Grégoire
 */
public class Binder {

  private final Injector2 injector;

  Binder(Injector2 injector) {
    this.injector = injector;
  }

  public <T> void bind(Class<T> key) {
    Class<? extends T> implementation = key;
    //Class<? extends Scope> scope = NoScope.class;

    if (key.isAnnotationPresent(ImplementedBy.class)) {
      Class<?> impl = key.getAnnotation(ImplementedBy.class).value();
      if (key.isAssignableFrom(impl)) {
        implementation = (Class<? extends T>) impl;
      }
    }
    Provider<T> provider = new ImplementationProvider<>(injector, implementation);
    Class<? extends Scope> scope = Optional.ofNullable(key.getAnnotation(Scoped.class))
        .map(a -> a.value())
        .orElse(NoScope.class);
    provider = injector.getScope(scope).scope(key, provider);
    injector.bind(key, provider);
  }
}
