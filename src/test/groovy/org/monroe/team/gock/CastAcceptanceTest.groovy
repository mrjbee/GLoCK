package org.monroe.team.gock

import org.junit.Test
import org.monroe.team.gock.test.domain.IInterface
import org.monroe.team.gock.test.domain.IAbstractClass
import org.monroe.team.gock.test.domain.IClass

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 2:29 PM
 */
class CastAcceptanceTest {

    private GoCK gock = new GoCK();

    @Test
    public void shouldAllowMockInterfaces(){
        IInterface anInterface = gock.mock(IInterface.class)
    }

    @Test
    public void shouldAllowMockAbstractClass(){
        IAbstractClass aClass = gock.mock(IAbstractClass.class)
    }

    @Test
    public void shouldAllowMockClass(){
        IClass aClass = gock.mock(IClass.class)
    }

}
