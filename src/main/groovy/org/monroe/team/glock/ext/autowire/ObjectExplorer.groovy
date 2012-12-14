package org.monroe.team.glock.ext.autowire

import org.monroe.team.glock.mock.factory.MockFactory
import java.lang.reflect.Field

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
       Field[] fields = objectUnderDiscover.getClass().getDeclaredFields();
       List<Field> answer = [];
       for (Field field: fields){
            if (field.isAnnotationPresent(Mock)){
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
        field.set(objectUnderDiscover, value)
    }
}
