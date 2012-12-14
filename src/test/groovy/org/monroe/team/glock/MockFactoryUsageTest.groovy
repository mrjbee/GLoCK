package org.monroe.team.glock

import org.junit.Test
import org.monroe.team.glock.test.domain.ArgConstructedObject
import org.monroe.team.glock.mock.factory.DefaultMockFactory
import junit.framework.Assert

/**
 * User: mrjbee
 * Date: 10/23/12
 * Time: 9:00 PM
 */
class MockFactoryUsageTest {

    @Test(expected=GroovyRuntimeException)
    public void shouldThrowExceptionSinceNoConstructorWithoutArgsExists(){
        GLoCK glock = new GLoCK()
        glock.charge(ArgConstructedObject)
    }

    @Test(expected=IllegalStateException)
    public void shouldThrowIllegalStateExceptionIfMockFactoryNotProvided(){
        GLoCK glock = new GLoCK()
        glock.chargePredefined(ArgConstructedObject)

    }

    @Test
    public void shouldConstructMockUsingDefaultIDSettings(){
        DefaultMockFactory mockFactory = new DefaultMockFactory()
        mockFactory.cacheArgsFor(ArgConstructedObject,[1,new Object()])
        GLoCK glock = new GLoCK(mockFactory)
        glock.chargePredefined(ArgConstructedObject)

    }

    @Test
    public void shouldConstructTwoMocksUsingDifferentClosuresPerIDs(){
        DefaultMockFactory mockFactory = new DefaultMockFactory()

        boolean defaultClosureExecuted = false;
        boolean testClosureExecuted = false;

        mockFactory.cacheArgsFor(ArgConstructedObject,{
            defaultClosureExecuted = true
            Object[] objs = [1, new Object()]
            return objs
        })
        mockFactory.cacheArgsFor("Test", ArgConstructedObject,{
            testClosureExecuted = true
            Object[] objs = [2, new Object()]
            return objs
        })

        GLoCK glock = new GLoCK(mockFactory)

        ArgConstructedObject mock = glock.chargePredefined(ArgConstructedObject)
        Assert.assertNotNull(mock)
        Assert.assertTrue(defaultClosureExecuted)
        Assert.assertFalse(testClosureExecuted)

        defaultClosureExecuted = false;

        ArgConstructedObject mock2 = glock.chargePredefined(ArgConstructedObject, "Test")
        Assert.assertNotNull(mock2)
        Assert.assertFalse(defaultClosureExecuted)
        Assert.assertTrue(testClosureExecuted)


    }
}
