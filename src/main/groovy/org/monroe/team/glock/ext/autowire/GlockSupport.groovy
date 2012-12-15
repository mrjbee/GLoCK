package org.monroe.team.glock.ext.autowire

import org.monroe.team.glock.GLoCK

import org.junit.Before
import org.monroe.team.glock.mock.factory.MockFactory
import java.lang.reflect.Field
import org.monroe.team.glock.mock.factory.DefaultMockFactory
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestName
import java.lang.reflect.Method

/**
 * User: MisterJBee 
 * Date: 12/14/12 Time: 5:34 PM
 * Open source: MIT Licence 
 * (Do whatever you want with the source code)
 */
class GlockSupport {

    @Rule public TestName name = new TestName();

    private GLoCK glockInstance = null;
    private ObjectExplorer objectExplorer = new ObjectExplorer(this);

    @Before
    public void doNotTouchThisBeforeMethod(){
        beforeTestInit()
        instantiateGlockIfRequired()
        initTestInstanceDependencies()
        customizeTestInstanceDependencies()
        Object testInstance = initTestInstance();
        if (testInstance != null){
           initUnderTestingFields(testInstance)
           customizeTestInstance()
        }
        afterTestInit()
    }




    protected void afterTestInit() {}
    protected void customizeTestInstanceDependencies() {}
    protected void customizeTestInstance() {}
    protected void beforeTestInit() {}

    @After
    public void doNotTouchThisAfterMethod(){
        glockInstance.verifyClip();
        afterTest();
    }

    private void initUnderTestingFields(Object testInstance) {
        List<Field> useFields = objectExplorer.useFields();
        Map<String, Object> valuePerFieldNameMap = [:];
        List<Field> toBeDiscoveredByTypeFieldList = [];
        ObjectExplorer objectExplorerBuf = objectExplorer;
        useFields.each {Field field ->

           String fieldName = objectExplorerBuf.getUseAsFieldName(field);
           if (fieldName != null){
              Object value = objectExplorerBuf.getFieldValue(field)
              valuePerFieldNameMap.put(fieldName, value);
           } else {
              toBeDiscoveredByTypeFieldList.add(field);
           }
        }
        updateValuePerFieldMapWithDecisionsByType(valuePerFieldNameMap, toBeDiscoveredByTypeFieldList)
        updateTestInstanceWithValues(testInstance, valuePerFieldNameMap)
    }

    void updateTestInstanceWithValues(Object testInstance, Map<String, Object> stringObjectAbstractMap) {
        ObjectExplorer testInstanceExplorer = new ObjectExplorer(testInstance);
        stringObjectAbstractMap.each {String key, Object value ->
            testInstanceExplorer.setFieldValueByFieldName(key, value)
        }
    }

    void updateValuePerFieldMapWithDecisionsByType(Map<String, Object> stringObjectLinkedHashMap, List<Field> fields) {
    }

    private Object initTestInstance() {

        if (!objectExplorer.isUnderTestingSpecified()){
            //Since there no under testing specified nothing to do
            return null;
        }

        Field underTestingField = extractFieldUnderTesting()
        Object newInstance = null;
        //create instance using create method if exists
        if (objectExplorer.isCreateMethodSpecified(name.methodName)){
            Method creationMethod = objectExplorer.getCreationMethodSpecifiedFor(name.methodName)
            newInstance = objectExplorer.exec(creationMethod);
        } else {
            //fallback with template
            newInstance = createUnderTestingInstance(underTestingField.getType())
        }
        objectExplorer.setFieldValue(underTestingField, newInstance)
        return newInstance;
    }

    private Field extractFieldUnderTesting() {
        List<Field> underTestingFields = objectExplorer.underTestingFields()
        if (underTestingFields.size() != 1) {
            throw new IllegalStateException("Multiple under testing objects declared:" + underTestingFields.size())
        }
        return underTestingFields.get(0)
    }

    protected <T> T createUnderTestingInstance(Class<T> classUnderTesting) {
        return classUnderTesting.newInstance();
    }


    private void initTestInstanceDependencies() {
        glockInstance.newClip();
        List<List> mockFields = objectExplorer.mockFields();
        for (Field mockField : mockFields) {
            initMockField(mockField);
        }
    }

    private void initMockField(Field field) {
        Class mockClass = field.getType();
        String mockID = objectExplorer.getMockIDFor(field);
        Object mockValue = glockInstance.chargePredefined(mockClass, mockID);
        objectExplorer.setFieldValue(field, mockValue);
    }

    private void instantiateGlockIfRequired() {
       if (glockInstance == null) {
            glockInstance = createGlockInstance();
       }
    }

    protected void afterTest() {}

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
