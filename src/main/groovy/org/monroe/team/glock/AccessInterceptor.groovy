package org.monroe.team.glock

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 1:57 PM
 */
class AccessInterceptor implements PropertyAccessInterceptor {

    private final GLoCK ownInstance

    AccessInterceptor(GLoCK ownInstance) {
        this.ownInstance = ownInstance
    }

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
        if (ownInstance.isChargingMode()){
           ownInstance.addExecutionExpectation(object,methodName, arguments)
        } else {

        }
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
