package org.monroe.team.glock.mock.factory

import org.monroe.team.glock.mock.MockInstanceHelper

/**
 * User: mrjbee
 * Date: 10/23/12
 * Time: 8:47 PM
 */
class DefaultMockFactory implements MockFactory {

    private final Map<String, Closure> argsPerMockIdAndClassMap = [:]

    @Override
    public <MockType> MockType getInstance(MockInstanceHelper instanceHelper, MockID mockID, Class<MockType> mockClass) {
        Object[] args = chooseFromCached(mockID, mockClass)
        if (args == null)
            args = fallbackConstructorArgsByClassAndId(mockClass,mockID)
        if (args == null)
            throw new IllegalStateException("There was no predefined arguments for mock with '$mockClass' class and '$mockID' ID")

        return instanceHelper.getMock(mockClass, (Object[])args)
    }
    public void cacheArgsFor(MockID mockId = MockID.DEFAULT, Class mockClass, List argList){
        cacheArgsFor(mockId,mockClass, argList.toArray())
    }
    public void cacheArgsFor(MockID mockId = MockID.DEFAULT, Class mockClass, Object[] args = new Object[0]){
        argsPerMockIdAndClassMap.put(convertToCachedId(mockClass,mockId),{args})
    }

    public void cacheArgsFor(MockID mockId = MockID.DEFAULT, Class mockClass, Closure argsCreationClosure){
        argsPerMockIdAndClassMap.put(convertToCachedId(mockClass,mockId),argsCreationClosure)
    }

    private <MockType> Object[] chooseFromCached(MockID mockID, Class<MockType> mockTypeClass) {
        String cachedId = convertToCachedId(mockTypeClass, mockID)
        Closure closure = argsPerMockIdAndClassMap.get(cachedId)
        if (closure != null){
            return closure.call()
        } else {
            return null
        }
    }

    private <MockType> String convertToCachedId(Class<MockType> mockTypeClass, MockID mockID) {
        return mockTypeClass.getName() + "_" + mockID.getID()
    }

    protected <MockType> Object[] fallbackConstructorArgsByClassAndId(Class<MockType> mockTypeClass, MockID mockID){
        return null
    }
}
