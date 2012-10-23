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

    @Test
    public void shouldAllowMockSameClassByFewMockInstances(){
        glock.newClip()
        IClass mockInstance = glock.charge(IClass.class)
        IClass mockInstance2 = glock.charge(IClass.class)

        //Setup charge behaviour
        glock.mockWith(mockInstance.methodB(1),{
            return true
        })
        glock.mockWith(mockInstance2.methodB(1),{
            return false
        })

        //Trigger charge into execute state
        glock.reload();

        //Call mocked method
        //Validate that method was run
        Assert.assertTrue(!mockInstance2.methodB(1))
        Assert.assertTrue(mockInstance.methodB(1))
        //Verify that there is no more expectations which was no executed
        glock.verifyClip()
    }

    @Test(expected = PointedAssertionError)
    public void shouldThrowAssertionErrorWhenNoMethodsWereExpected(){
        glock.newClip()
        IClass mockInstance = glock.charge(IClass)
        glock.reload()

        execWithCaution {mockInstance.methodA()}

        failIfExecuteThisCode()
    }

    private void failIfExecuteThisCode() {
        Assert.fail("Should nit be here")
    }

    @Test (expected = PointedAssertionError)
    public void shouldThrowAssertionErrorWhenNotTheSameMethodWasExpected(){
        glock.newClip()
        IClass mockInstance = glock.charge(IClass)
        glock.mockWith(mockInstance.methodA(), glock.doNothing())
        glock.reload()

        execWithCaution {mockInstance.methodB(2)}

        failIfExecuteThisCode()
    }

    @Test (expected = PointedAssertionError)
    public void shouldThrowAssertionErrorWhenMethodWasCalledWithUnexpectedArgumentsValues(){
        glock.newClip()
        IClass mockInstance = glock.charge(IClass)
        glock.mockWith(mockInstance.methodB(1), glock.doNothing())
        glock.reload()
        execWithCaution {mockInstance.methodB(2)}
        failIfExecuteThisCode()
    }


    @Test(expected = PointedAssertionError)
    public void shouldThrowAssertionBecauseOfVerificationOneExpectedCallOfTheSameMethodNotHappens(){
        glock.newClip()
        IClass mockInstance = glock.charge(IClass)
        //Two expectations
        glock.mockWith(mockInstance.methodB(1), glock.doNothing())
        glock.mockWith(mockInstance.methodB(2), glock.doNothing())

        glock.reload()
        //One usage
        mockInstance.methodB(1)

        execWithCaution {glock.verifyClip()}
        failIfExecuteThisCode()
    }

    @Test(expected = PointedAssertionError)
    public void shouldThrowAssertionBecauseOfVerificationOneExpectedCallOfTheOtherMethodNotHappens(){
        glock.newClip()
        IClass mockInstance = glock.charge(IClass)
        //Two expectations
        glock.mockWith(mockInstance.methodB(1), glock.doNothing())
        glock.mockWith(mockInstance.methodA(), glock.doNothing())

        glock.reload()
        //One usage
        mockInstance.methodB(1)

        execWithCaution {glock.verifyClip()}
        failIfExecuteThisCode()
    }

    @Test(expected = PointedAssertionError)
    public void shouldByPassAssertionOutsideFromMockedMethod(){
        glock.newClip()
        IClass mockInstance = glock.charge(IClass)
        //Two expectations
        glock.mockWith(mockInstance.methodB(1), {int arg->
            Assert.assertEquals(arg, 2)
        })

        glock.reload()
        //Should re trow assertion exception
        execWithCaution {mockInstance.methodB(1)}
        failIfExecuteThisCode()
    }

    private void execWithCaution(Closure exec){
        try{
            exec.call()
        } catch (AssertionError ae){
            throw new PointedAssertionError(ae)
        }
    }
}
