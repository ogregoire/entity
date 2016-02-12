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

/**
 *
 * @author Olivier Grégoire
 */
public class EngineFactory {

  Injector injector = new Injector();

  public EngineFactory() {
    injector.block(Engine.class);
  }

  public EngineFactory register(Object instance) {
    if (instance == null) {
      throw new NullPointerException();
    }
    injector.bind(instance);
    return this;
  }

  public Engine create() {
    Engine engine = new Engine(this);
    injector.bindForce(engine);
    injector.provision();
    return engine;
  }

}
