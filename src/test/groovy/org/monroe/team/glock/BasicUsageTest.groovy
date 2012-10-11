package org.monroe.team.glock

import org.junit.Test
import org.monroe.team.glock.test.domain.IClass
import junit.framework.Assert

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 3:54 PM
 */
class BasicUsageTest {

    private GLoCK glock = new GLoCK()

    @Test
    public void shouldExecuteChargedClosureInsteadOfVoidMethod(){

        glock.newClip()

        IClass mockInstance = glock.mock(IClass.class)

        boolean wasExecuted = false

        glock.charge(mockInstance.methodA(),{
            wasExecuted = true
        })

        glock.reload();

        mockInstance.methodA();

        Assert.assertTrue(wasExecuted)

        glock.verifyClip()
    }
}
