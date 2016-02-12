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

import java.util.Arrays;
import java.util.function.Supplier;

/**
 *
 * @author Olivier Grégoire
 * @param <C>
 */
public class Mapper<C extends Component> {

  private static final int INITIAL_SIZE = 32;

  private C[] components = (C[]) new Component[INITIAL_SIZE];
  private final Supplier<C> supplier;

  Mapper(Supplier<C> supplier) {
    this.supplier = supplier;
  }

  Mapper(Class<C> type) {
    this.supplier = () -> {
      try {
        return type.newInstance();
      } catch (IllegalAccessException | InstantiationException e) {
        throw new RuntimeException(e);
      }
    };
  }

  public C mapFrom(Entity entity) {
    final int e = entity.id;
    C[] c = components;
    return e < c.length
        ? c[e]
        : null;
  }

  public C mapFromOrCreateFor(Entity entity) {
    final int e = entity.id;
    C[] c = components;
    if (e >= c.length) {
      components = c = Arrays.copyOf(c, Integer.highestOneBit(e) << 1);
    }
    C comp = c[e];
    if (comp == null) {
      c[e] = comp = supplier.get();
    }
    return comp;
  }

  public boolean isContainedIn(Entity entity) {
    final int e = entity.id;
    return e < components.length && components[e] != null;
  }
}
