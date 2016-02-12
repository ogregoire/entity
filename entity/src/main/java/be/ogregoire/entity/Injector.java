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
package be.ogregoire.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Olivier Grégoire
 */
class Injector {

  private final Map<Class, Object> bindings = new HashMap<>();
  private final List<Provisioner> provisioners = new ArrayList<>();

  void block(Class type) {
    bindings.put(type, null);
  }

  void bind(Object instance) {
    Class type = instance.getClass();
    if (bindings.containsKey(type)) {
      throw new IllegalArgumentException("Can't register twice the same class");
    }
    bindings.put(type, instance);
    for (Class c = type; c != Object.class; c = c.getSuperclass()) {
      Arrays.stream(c.getDeclaredFields())
          .filter(this::isInjectableField)
          .forEach((f) -> {
            provisioners.add(new Provisioner(instance, f));
          });
    }
  }

  void bindForce(Object instance) {
    bindings.remove(instance.getClass());
    bind(instance);
  }

  void provision() {
    provisioners.forEach(Provisioner::provision);
  }

  private boolean isInjectableField(Field field) {
    return field.isAnnotationPresent(Inject.class)
        && !Modifier.isStatic(field.getModifiers())
        && !Modifier.isFinal(field.getModifiers());
  }

  private class Provisioner {

    private final Object provisionable;
    private final Field field;

    private Provisioner(Object instance, Field field) {
      this.provisionable = instance;
      this.field = field;
    }

    private void provision() throws RuntimeException {
      try {
        Object injected = bindings.get(field.getType());
        if (injected == null) {
          String message = String.format("Field %s is marked to be injected but no object fits", field);
          throw new RuntimeException(message);
        }
        field.setAccessible(true);
        field.set(provisionable, injected);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
