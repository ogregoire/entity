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
package example;

import be.ogregoire.entity.Engine;
import be.ogregoire.entity.EngineFactory;
import be.ogregoire.entity.Entity;

/**
 *
 * @author Olivier Grégoire
 */
public class ExampleGame implements Runnable {

  public static void main(String[] args) {
    new ExampleGame().run();
  }

  @Override
  public void run() {
    Engine engine = new EngineFactory()
        .create();

    while (true) {
      float delta = 0.01f;

      engine.process(delta);
    }

  }

  public void initPlayer(Entity entity) {

  }

}
