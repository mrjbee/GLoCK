package org.monroe.team.glock.ext.autowire

import org.monroe.team.glock.mock.factory.MockFactory

/**
 * User: MisterJBee 
 * Date: 12/14/12 Time: 5:32 PM
 * Open source: MIT Licence 
 * (Do whatever you want with the source code)
 */
@interface GlockTest {
    Class<? extends MockFactory> factory;
}
