package org.monroe.team.glock

import org.junit.Test
import org.monroe.team.glock.test.domain.IClass
import org.monroe.team.glock.test.domain.IAbstractClass2
import junit.framework.Assert
import org.monroe.team.glock.control.ExpectedMethod

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 10:56 PM
 */
class AnyArgumentUsageTest {

    private GLoCK glock = new GLoCK()

    @Test
    public void shouldAllowToUseAnyForObjectMatching(){
        IClass testArgument = new IClass()
        glock.newClip()
        IAbstractClass2 testInstance = glock.charge(IAbstractClass2)
        glock.mockWith({testInstance.doSomethingAbstractWith(glock.any())},{IClass arg ->
            Assert.assertEquals(testArgument, arg)
        })
        glock.mockWith({testInstance.doSomethingAbstractWith(testArgument)},{IClass arg ->
            Assert.assertEquals(testArgument, arg)
        })
        glock.reload()

        //Call method with concrete argument
        testInstance.doSomethingAbstractWith(testArgument)
        testInstance.doSomethingAbstractWith(testArgument)

        glock.verifyClip()
    }

    @Test
    public void shouldAllowToUseAnyForPrimitiveArgMatchingWithInt(){

        glock.newClip()
        IAbstractClass2 testInstance = glock.charge(IAbstractClass2)
        glock.mockWith({testInstance.doSomethingWith(glock.any())},{int arg ->
            Assert.assertEquals(2, arg)
        })
        glock.mockWith({testInstance.doSomethingWith(1)},{int arg ->
        })
        glock.reload()

        //Call method with concrete argument
        testInstance.doSomethingWith(2)
        testInstance.doSomethingWith(1)
        glock.verifyClip()

    }


    @Test(expected = PointedAssertionError)
    public void shouldAllowToUseAnyForPrimitiveArgMatchingWithBoolean(){
        glock.newClip()
        IAbstractClass2 testInstance = glock.charge(IAbstractClass2)
        //Specify any to be boolean true
        glock.mockWith({testInstance.doSomethingAbstractWithBoolean(glock.any())},{boolean arg ->
        })

        glock.reload()

        //Call method with concrete argument

        testInstance.doSomethingAbstractWithBoolean(false)
        execWithCaution {testInstance.doSomethingAbstractWithBoolean(false) }

        glock.verifyClip()
        Assert.fail("Should never come")

    }

    @Test
    public void shouldAllowToMockWithoutArgumentsDeclaring(){
        IClass testArgument = new IClass()
        glock.newClip()
        def testInstance = glock.charge(IAbstractClass2)
        glock.mockWith({testInstance.doSomethingAbstractWithMultipleArguments(glock.any(),glock.any(),glock.any())},
                {boolean arg1, int arg2, Object arg3 ->
                    Assert.assertEquals(arg1, true)
                    Assert.assertEquals(arg2, 1)
                    Assert.assertEquals(arg3, testArgument)
                })
        glock.mockWith({testInstance.doSomethingAbstractWithMultipleArguments(glock.anyArgs())},
                {boolean arg1, int arg2, Object arg3 ->
                    Assert.assertEquals(arg1, false)
                    Assert.assertEquals(arg2, 2)
                    Assert.assertEquals(arg3, testArgument)
                })
        glock.mockWith({testInstance.doSomethingAbstractWithMultipleArguments(glock.anyArgsButArgs())},
                {boolean arg1, int arg2, Object arg3 ->
                    Assert.assertEquals(arg1, false)
                    Assert.assertEquals(arg2, 2)
                    Assert.assertEquals(arg3, testArgument)
                })

        glock.reload()

        testInstance.doSomethingAbstractWithMultipleArguments(true, 1, testArgument)
        testInstance.doSomethingAbstractWithMultipleArguments(false, 2, testArgument)
        testInstance.doSomethingAbstractWithMultipleArguments(false, 2, testArgument)

        glock.verifyClip()
    }

    @Test (expected=PointedAssertionError)
    public void shouldFailSinceNoArgsNotAllowed(){
        IClass testArgument = new IClass()
        glock.newClip()
        def testInstance = glock.charge(IAbstractClass2)
        glock.mockWith({testInstance.doSomething(glock.anyArgs())},glock.doNothing())
        glock.mockWith({testInstance.doSomething(glock.anyArgs())},glock.doNothing())

        glock.reload()

        testInstance.doSomething()
        execWithCaution {testInstance.doSomething()}

        glock.verifyClip()
    }

    private void execWithCaution(Closure exec){
        try{
            exec.call()
        } catch (AssertionError ae){
            throw new PointedAssertionError(ae)
        }
    }

}
