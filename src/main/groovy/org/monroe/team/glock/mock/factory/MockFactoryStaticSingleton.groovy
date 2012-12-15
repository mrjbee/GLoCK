package org.monroe.team.glock.mock.factory

import org.monroe.team.glock.mock.MockInstanceHelper

/**
 * User: MisterJBee 
 * Date: 12/15/12 Time: 5:42 PM
 * Open source: MIT Licence 
 * (Do whatever you want with the source code)
 */
abstract class MockFactoryStaticSingleton implements MockFactory {

    private static MockFactory singleton;

    @Override
    public <MockType> MockType getInstance(MockInstanceHelper instanceHelper, String mockID, Class<MockType> mockClass) {
        return get(this).getInstance(instanceHelper, mockID, mockClass);
    }

    private static MockFactory get(MockFactoryStaticSingleton factoryContainer) {
        if (singleton == null){
            synchronized (MockFactoryStaticSingleton){
                singleton = factoryContainer.createSingleton();
            }
        }
        return singleton
    }

    abstract MockFactory createSingleton();


}
