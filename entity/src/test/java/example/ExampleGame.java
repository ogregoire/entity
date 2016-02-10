/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import be.ogregoire.entity.Engine;
import be.ogregoire.entity.EngineBuilder;
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
    Engine engine = new EngineBuilder()
        
        .build();
    
    while (true) {
      float delta = 0.01f;
      
      engine.process(delta);
    }
    
  }
  
  
  
  public void initPlayer(Entity entity) {
    
  }
      
}
