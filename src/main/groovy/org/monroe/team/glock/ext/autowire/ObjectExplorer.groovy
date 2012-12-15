package org.monroe.team.glock.ext.autowire

import org.monroe.team.glock.mock.factory.MockFactory
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * User: MisterJBee 
 * Date: 12/14/12 Time: 5:45 PM
 * Open source: MIT Licence 
 * (Do whatever you want with the source code)
 */
class ObjectExplorer {

    private final Object objectUnderDiscover;

    ObjectExplorer(Object objectUnderDiscover) {
        this.objectUnderDiscover = objectUnderDiscover
    }

    boolean isFactoryDefined() {
        Class<? extends MockFactory> mockFactoryClass = getFactoryClass()
        return mockFactoryClass != null;
    }

    Class<? extends MockFactory> getFactoryClass() {
        GlockTest testDescriptor = objectUnderDiscover.getClass().getAnnotation(GlockTest);
        if (testDescriptor == null){
            return null;
        }
        return testDescriptor.factory;
    }

    List<Field> mockFields() {
       return fiendFieldsAnnotatedWith(Mock)
    }

    List<Field> underTestingFields() {
        return fiendFieldsAnnotatedWith(UnderTesting)
    }

    private ArrayList<Field> fiendFieldsAnnotatedWith(Class<?> annClass) {
        Field[] fields = objectUnderDiscover.getClass().getDeclaredFields();
        List<Field> answer = [];
        for (Field field : fields) {
            if (field.isAnnotationPresent(annClass)) {
                answer.add(field)
            }
        }
        return answer;
    }

    String getMockIDFor(Field field) {
        Mock mock = field.getAnnotation(Mock);
        return mock.mockId();
    }

    void setFieldValue(Field field, Object value) {
        // set accessible true
        boolean wasAccessibly = field.isAccessible()
        field.setAccessible(true)
        field.set(objectUnderDiscover, value)
        field.setAccessible(wasAccessibly)
    }

    boolean isCreateMethodSpecified(String testMethodName) {
        CreateMethod createMethod = extractCreationMethodDeclaration(testMethodName)
        return createMethod != null && createMethod.value() != "[nAn]"
    }

    Method getCreationMethodSpecifiedFor(String testMethodName) {
        CreateMethod createMethod = extractCreationMethodDeclaration(testMethodName)
        String creationMethodName = createMethod.value();
        return objectUnderDiscover.getClass().getDeclaredMethod(creationMethodName)
    }

    private CreateMethod extractCreationMethodDeclaration(String methodName) {
        Method testMethod = objectUnderDiscover.getClass().getDeclaredMethod(methodName);
        if (testMethod == null) {
            throw new IllegalStateException("Can`t be exception")
        }
        return testMethod.getAnnotation(CreateMethod)
    }

    boolean isUnderTestingSpecified() {
        return !fiendFieldsAnnotatedWith(UnderTesting).isEmpty()
    }



    Object exec(Method method) {
        return method.invoke(objectUnderDiscover)
    }
}
