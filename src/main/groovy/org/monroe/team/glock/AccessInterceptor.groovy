package org.monroe.team.glock

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 1:57 PM
 */
class AccessInterceptor implements PropertyAccessInterceptor {

    @Override
    Object beforeGet(Object object, String property) {
        throw new IllegalStateException("Not Implemented yet")
    }

    @Override
    void beforeSet(Object object, String property, Object newValue) {
        throw new IllegalStateException("Not Implemented yet")
    }

    @Override
    Object beforeInvoke(Object object, String methodName, Object[] arguments) {
        println ("Call heppends: ")
        println ("  Object:"+object)
        println ("  method:"+methodName)
        println ("  args:"+arguments)
    }

    @Override
    Object afterInvoke(Object object, String methodName, Object[] arguments, Object result) {
        throw new IllegalStateException("Should nevere never happends")
    }

    @Override
    boolean doInvoke() {
        return false
    }
}
