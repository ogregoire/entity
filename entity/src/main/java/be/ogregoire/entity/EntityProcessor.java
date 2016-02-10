/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ogregoire.entity;

/**
 *
 * @author Olivier Grégoire
 */
public abstract class EntityProcessor extends Processor {

  final Iterable<Entity> entities() {
    return null;
  }
  
  @Override
  protected final void process() {
    entities().forEach(this::process);
  }
  
  protected abstract void process(Entity entity);
  
}
