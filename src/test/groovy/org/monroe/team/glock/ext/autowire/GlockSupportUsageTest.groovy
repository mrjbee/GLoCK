package org.monroe.team.glock.ext.autowire

import org.monroe.team.glock.test.domain.IClass
import org.junit.Test
import org.monroe.team.glock.mock.factory.MockID

/**
 * User: MisterJBee 
 * Date: 12/14/12 Time: 7:40 PM
 * Open source: MIT Licence 
 * (Do whatever you want with the source code)
 */
class GlockSupportUsageTest extends GlockSupport {

  @Mock(id="sad")
  IClass mock;

  @Test
  public void shouldWork(){
    boolean wasExecuted = false;
    mockWith({mock.methodA()}, {wasExecuted = true});
  }

}
