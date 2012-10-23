package org.monroe.team.glock.mock.factory

import org.monroe.team.glock.mock.MockInstanceHelper

/**
 * User: mrjbee
 * Date: 10/23/12
 * Time: 8:44 PM
 */
interface MockFactory {
   public <MockType> MockType getInstance(MockInstanceHelper instanceHelper, MockID mockID, Class<MockType> mockClass)
}
