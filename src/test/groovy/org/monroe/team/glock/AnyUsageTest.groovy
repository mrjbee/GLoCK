package org.monroe.team.glock

import org.junit.Test
import org.monroe.team.glock.test.domain.IClass
import org.monroe.team.glock.test.domain.IAbstractClass2
import junit.framework.Assert

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 10:56 PM
 */
class AnyUsageTest {

    private GLoCK glock = new GLoCK()

    @Test
    public void shouldAllowToUseAnyForObjectMatching(){
        IClass testArgument = new IClass()
        glock.newClip()
        IAbstractClass2 testInstance = glock.charge(IAbstractClass2)
        glock.mockWith(testInstance.doSomethingAbstractWith(glock.any(IClass)),{IClass arg ->
            Assert.assertEquals(testArgument, arg)
        })
        glock.reload()

        //Call method with concrete argument
        testInstance.doSomethingAbstractWith(testArgument)

        glock.verifyClip()
    }

    @Test
    public void shouldAllowToUseAnyForPrimitiveArgMatching(){

        glock.newClip()
        IAbstractClass2 testInstance = glock.charge(IAbstractClass2)
        glock.mockWith(testInstance.doSomethingWith(glock.any(Integer,1000)),{int arg ->
            Assert.assertEquals(2, arg)
        })
        glock.reload()

        //Call method with concrete argument
        testInstance.doSomethingWith(2)

        glock.verifyClip()

    }

}
