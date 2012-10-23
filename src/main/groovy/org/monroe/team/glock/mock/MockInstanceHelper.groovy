package org.monroe.team.glock.mock

import groovy.mock.interceptor.MockFor

/**
 * User: mrjbee
 * Date: 10/23/12
 * Time: 8:49 PM
 */
class MockInstanceHelper {
    public <MockType> MockType getMock(Class<MockType> mockClass,  Object... args = null){
         return MockFor.getInstance(mockClass,args)
    }
}
