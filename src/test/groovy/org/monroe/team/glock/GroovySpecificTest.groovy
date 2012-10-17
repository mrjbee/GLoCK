package org.monroe.team.glock

import org.junit.Test
import org.monroe.team.glock.test.domain.IAbstractClass2

/**
 * User: mrjbee
 * Date: 10/17/12
 * Time: 11:14 PM
 */
class GroovySpecificTest {

    private GLoCK glock = new GLoCK();

    @Test
    public void shouldAllowToUseAnyForPrimitiveArgMatchingWithDefaultValue(){

        glock.newClip()
        IAbstractClass2 testInstance = glock.charge(IAbstractClass2)
        //Threaded as without args
        glock.mockWith({testInstance.doSomethingWithDefaultValueEqualsTwo()},{ ->

        })
        glock.reload()

        //Call method with concrete argument
        testInstance.doSomethingWithDefaultValueEqualsTwo()

        glock.verifyClip()

    }

}
