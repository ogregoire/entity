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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Olivier Grégoire
 */
public final class Injector2 {

  private final Map<Class<?>, Provider<?>> bindings = new HashMap<>();
  private final Map<Class<? extends Scope>, Scope> scopes = new HashMap<>();

  Injector2() {
  }

  <T> void bind(Class<T> key, Provider<T> provider) {
    if (bindings.containsKey(key)) {
      throw new RuntimeException("Binding for " + key.getName() + " already set");
    }
    bindings.put(key, provider);
  }

  Scope getScope(Class<? extends Scope> scope) {
    return scopes.computeIfAbsent(scope, this::newScope);
  }

  private Scope newScope(Class<? extends Scope> scopeType) {
    try {
      return scopeType.newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      throw new RuntimeException("Unable to instanciate scope " + scopeType.getName(), ex);
    }
  }

  private void init(Set<Module> modules) {
    Binder binder = new Binder(this);
    for (Module module : modules) {
      module.configure(binder);
    }
  }

  public <T> T getInstance(Class<T> type) {
    return (T) bindings.get(type).get();
  }

  public static final class Builder {

    final Set<Module> modules = new LinkedHashSet<>();

    public Builder module(Module module) {
      modules.add(module);
      return this;
    }

    public Injector2 build() {
      Injector2 injector = new Injector2();
      injector.init(modules);
      return injector;
    }

  }

}
