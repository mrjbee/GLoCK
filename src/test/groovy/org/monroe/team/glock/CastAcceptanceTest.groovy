package org.monroe.team.glock

import org.junit.Test
import org.monroe.team.glock.test.domain.IInterface
import org.monroe.team.glock.test.domain.IAbstractClass
import org.monroe.team.glock.test.domain.IClass

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 2:29 PM
 */
class CastAcceptanceTest {

    private GLoCK glock = new GLoCK();

    @Test
    public void shouldAllowMockInterfaces(){
        IInterface anInterface = glock.mock(IInterface.class)
    }

    @Test
    public void shouldAllowMockAbstractClass(){
        IAbstractClass aClass = glock.mock(IAbstractClass.class)
    }

    @Test
    public void shouldAllowMockClass(){
        IClass aClass = glock.mock(IClass.class)
    }

}
