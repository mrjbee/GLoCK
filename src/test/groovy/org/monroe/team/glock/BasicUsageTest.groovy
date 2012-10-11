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
    public void shouldExecuteClosureInsteadOfVoidMethod(){
        //Reset all expectations
        glock.newClip()
        //Create charge
        IClass mockInstance = glock.charge(IClass.class)

        //Setup charge behaviour
        boolean wasExecuted = false
        glock.mockWith(mockInstance.methodA(),{
            wasExecuted = true
        })

        //Trigger charge into execute state
        glock.reload();

        //Call mocked method
        mockInstance.methodA();

        //Validate that method was run
        Assert.assertTrue(wasExecuted)

        //Verify that there is no more expectations which was no executed
        glock.verifyClip()
    }

    @Test
    public void shouldExecuteClosureAndReturnMockedValue(){
        //Reset all expectations
        glock.newClip()
        //Create charge
        IClass mockInstance = glock.charge(IClass.class)

        //Setup charge behaviour
        glock.mockWith(mockInstance.methodB(1),{
            return true
        })

        //Trigger charge into execute state
        glock.reload();

        //Call mocked method
        def returnValue = mockInstance.methodB(1);

        //Validate that method was run
        Assert.assertTrue(returnValue)

        //Verify that there is no more expectations which was no executed
        glock.verifyClip()
    }

    @Test(expected = AssertionError)
    public void shouldThrowAssertionErrorWhenNoMethodsWereExpected(){
        glock.newClip()
        IClass mockInstance = glock.charge(IClass)
        glock.reload()
        try{
            mockInstance.methodA()
        }catch (AssertionError ae){
            ae.printStackTrace()
            throw ae
        }
        fail()
    }

    private void fail() {
        //Could not use Assert.fail since it also produce AssertionError
        throw new IllegalArgumentException("It should never go here")
    }

    @Test (expected = AssertionError)
    public void shouldThrowAssertionErrorWhenNotTheSameMethodWasExpected(){
        glock.newClip()
        IClass mockInstance = glock.charge(IClass)
        glock.mockWith(mockInstance.methodA(), glock.doNothing())
        glock.reload()
        try{
            mockInstance.methodB(2)
        }catch (AssertionError ae){
            ae.printStackTrace()
            throw ae
        }
        fail()
    }

}
