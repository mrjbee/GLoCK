package org.monroe.team.glock.ext.autowire

import org.monroe.team.glock.GLoCK

import org.junit.Before
import org.monroe.team.glock.mock.factory.MockFactory
import java.lang.reflect.Field
import org.monroe.team.glock.mock.factory.MockID
import org.monroe.team.glock.mock.factory.DefaultMockFactory
import org.junit.After

/**
 * User: MisterJBee 
 * Date: 12/14/12 Time: 5:34 PM
 * Open source: MIT Licence 
 * (Do whatever you want with the source code)
 */
class GlockSupport {

    private GLoCK glockInstance = null;
    private ObjectExplorer objectExplorer = new ObjectExplorer(this);

    @Before
    public void doNotTouchThisBeforeMethod(){
        instantiateGlockIfRequired()
        initMockFields()
        beforeTest();
    }

    @After
    public void doNotTouchThisAfterMethod(){
        glockInstance.verifyClip();
        afterTest();
    }



    private void initMockFields() {
        glockInstance.newClip();
        List<List> mockFields = objectExplorer.mockFields();
        for (Field mockField : mockFields) {
            initMockField(mockField);
        }
    }

    private void initMockField(Field field) {
        Class mockClass = field.getClass();
        MockID mockID = objectExplorer.getMockIDFor(field);                        T
        Object mockValue = glockInstance.chargePredefined(mockClass, mockID);
        objectExplorer.setFieldValue(field, mockValue);
    }

    private void instantiateGlockIfRequired() {
       if (glockInstance == null) {
            glockInstance = createGlockInstance();
       }
    }

    protected void afterTest() {}
    protected void beforeTest() {}

    protected GLoCK createGlockInstance() {
        if (objectExplorer.isFactoryDefined()){
            MockFactory factory = objectExplorer.getFactoryClass().newInstance();
            return new GLoCK(factory);
        } else{
            return new GLoCK(new DefaultMockFactory())
        }
    }

    public void mockWith(Closure whatToMock, Closure mockWithWhat){
       glockInstance.mockWith(whatToMock, mockWithWhat);
    }

    public void stubWith(Closure whatToMock, Closure mockWithWhat){
        glockInstance.stubWith(whatToMock, mockWithWhat);
    }

    public void reload(){
        glockInstance.reload()
    }


}
