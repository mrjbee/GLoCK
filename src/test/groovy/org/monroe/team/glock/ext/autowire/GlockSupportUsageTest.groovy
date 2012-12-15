package org.monroe.team.glock.ext.autowire

import org.monroe.team.glock.test.domain.IClass
import org.junit.Test
import org.monroe.team.glock.test.domain.IClass2
import org.monroe.team.glock.test.domain.Service
import junit.framework.Assert
import groovy.transform.ASTTest

/**
 * User: MisterJBee 
 * Date: 12/14/12 Time: 7:40 PM
 * Open source: MIT Licence 
 * (Do whatever you want with the source code)
 */
class GlockSupportUsageTest extends GlockSupport {

    @Mock @Use("iClassInstance") IClass mock;
    @Mock @Use IClass testMock;
    @Mock @Use IClass2 mock2;

    @UnderTesting Service service;

    boolean defaultCreateMethodUsed = false;

    //==========Pre-defined methods ===============

    @Override
    protected void beforeTestInit() {
        defaultCreateMethodUsed = false;
    }

    @Override
    protected def <T> T createUnderTestingInstance(Class<T> classUnderTesting) {
        defaultCreateMethodUsed = true;
        return super.createUnderTestingInstance(classUnderTesting)
    }


    @Test
    public void shouldPassWithMinimalConfiguration() {
        boolean wasExecuted = false;
        mockWith({mock.methodA()}, {wasExecuted = true});
        reload()
        mock.methodA();
        Assert.assertTrue(wasExecuted)
    }


    @Test
    public void shouldUseFallbackInstantiatingApproach() {
        reload();
        Assert.assertNotNull(service);
        Assert.assertTrue(defaultCreateMethodUsed);
    }

    @Test @CreateMethod("createTestInstance")
    public void shouldUseSpecifiedInstantiatingApproach() {
        reload();
        Assert.assertNotNull(service);
        Assert.assertFalse(defaultCreateMethodUsed);
    }

    Service createTestInstance() {
        return new Service();
    }

    @Test
    public void shouldWorkWithUnderTestingObject() {
        mockWith({mock.methodB(0)}, {"hello"})
        mockWith({testMock.methodB(1)}, {" world"})
        mockWith({mock2.promo()}, {"Hey, "})
        reload();
        Assert.assertEquals("Hey, hello world", service.doSomething())
    }
}
