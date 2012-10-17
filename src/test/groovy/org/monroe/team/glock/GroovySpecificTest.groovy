package org.monroe.team.glock

import org.junit.Test
import org.monroe.team.glock.test.domain.IAbstractClass2
import org.monroe.team.glock.test.domain.ActiveConstructorObject
import junit.framework.Assert

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

    @Test
    public void constructorWillBeCalled(){
        glock.newClip()
         //If constructor executed then
        boolean constructorCallClosure = false
        ActiveConstructorObject testInstance = glock.charge(ActiveConstructorObject,{
            constructorCallClosure = true
        })
        Assert.assertTrue(constructorCallClosure)
        glock.reload()
        glock.verifyClip()
    }

}
