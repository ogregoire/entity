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
public abstract class Processor {

  protected void initialize() {
  }

  final void doProcess() {
    if (mustProcess()) {
      beforeProcessing();
      process();
      afterProcessing();
    }
  }

  protected boolean mustProcess() {
    return true;
  }
  
  protected void beforeProcessing() {
  }
  
  protected abstract void process();
  
  protected void afterProcessing() {
  }
}
