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
        //Reset all expectations
        glock.newClip()
        //Create mock
        IClass mockInstance = glock.mock(IClass.class)

        //Setup mock behaviour
        boolean wasExecuted = false
        glock.charge(mockInstance.methodA(),{
            wasExecuted = true
        })

        //Trigger mock into execute state
        glock.reload();

        //Call mocked method
        mockInstance.methodA();

        //Validate that method was run
        Assert.assertTrue(wasExecuted)

        //Verify that there is no more expectations which was no executed
        glock.verifyClip()
    }
}
