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

import be.ogregoire.entity.inject.Injector;
import be.ogregoire.entity.inject.Inject;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Olivier Grégoire
 */
public class InjectorTest {

  public InjectorTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testProvision() {
    Car car = new Car();
    Wheel wheel = new Wheel("red");
    Injector injector = new Injector();
    injector.bind(car);
    injector.bind(wheel);

    assertThat(car.wheel, is(nullValue()));

    injector.provision();

    assertThat(car.wheel, is(notNullValue()));
    assertThat(car.wheel.color, is(equalTo("red")));
  }

  private static class Car {

    @Inject
    Wheel wheel;
  }

  private static class Wheel {

    private String color;

    Wheel(String color) {
      this.color = color;
    }
  }

}
