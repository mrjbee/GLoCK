package org.monroe.team.glock

import org.monroe.team.glock.trace.Tracer
import org.monroe.team.glock.trace.impl.ConsoleTracer
import org.monroe.team.glock.trace.Level
import org.monroe.team.glock.trace.TracerHolder
import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.MockProxyMetaClass
import org.monroe.team.glock.control.Control
import org.monroe.team.glock.matcher.ArgMatcher
import org.monroe.team.glock.matcher.NoOpArgMatcher
import org.monroe.team.glock.matcher.EqualsArgMatcher
import org.monroe.team.glock.control.ExpectedMethod

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 1:56 PM
 */
class GLoCK {

    private final TracerHolder tracerHolder = new TracerHolder(new ConsoleTracer(Level.OFF))
    private final AccessInterceptor accessInterceptor = new AccessInterceptor(this)
    private boolean chargingMode = true
    private List<Control> controls =  new ArrayList<Control>()
    private Map currentExpectation = [:]
    private Map anyValuePerClass = [:]

    void setTracer(Tracer tracer){
       tracerHolder.setTracer(tracer)
    }

    Tracer getTracer(){
       tracerHolder.tracer
    }

    public <MockType> MockType any(Object[] args = null, Class<MockType> clazz){
        if (anyValuePerClass.containsKey(clazz)){
            return anyValuePerClass.get(clazz)
        }
        def instance = MockFor.getInstance(clazz, args)
        def thisProxy = MockProxyMetaClass.make(clazz)
        instance.metaClass = thisProxy
        anyValuePerClass.put(clazz,instance)
        return instance
    }

        /**
     * Create mock for class, abstract class or interface
     * @param args - current limitation, you should by pass arguments to match existing constructor if any
     * @param clazz - mocked instance class
     * @return mock, which you could use for testing
     */
    public <MockType> MockType mock(Object[] args = null, Class<MockType> clazz){
        def instance = MockFor.getInstance(clazz, args)
        def thisProxy = MockProxyMetaClass.make(clazz)
        thisProxy.interceptor = accessInterceptor
        instance.metaClass = thisProxy

        return instance
    }

    public <AnyType> void charge(AnyType expectedMethod, Closure bullet){
        //TODO: add args pre-validation
        Control control = findControlFor(currentExpectation.obj);
        List<ArgMatcher> argMatcherList = createArgMatcherList()
        control.addMethod(new ExpectedMethod(currentExpectation.method, argMatcherList, bullet))
    }

    private List<ArgMatcher> createArgMatcherList() {
        List<ArgMatcher> argMatcherList = [];
        Object[] args = currentExpectation.args
        if (!args) {
            args.each { Object arg ->
                if (isAnyMarkerObject(arg)) {
                    argMatcherList.add(NoOpArgMatcher.ALWAYS_TRUE)
                } else {
                    argMatcherList.add(new EqualsArgMatcher(arg))
                }
            }
        }
        argMatcherList
    }

    private boolean isAnyMarkerObject(arg) {
        if (arg == null) return false
        Class argClazz = arg.getClass()
        if (anyValuePerClass.containsKey(argClazz)) {
            if (anyValuePerClass.get(argClazz).is(arg))
                return true;
            else
                return false;
        }
    }

    private Control findControlFor(Object o) {
        controls.find {Control it->
            it.getMockObject().equals(o)
        }
    }

    void reload() {
        chargingMode = false
    }

    /**
     * Drop all expectations and stop watching any of created mock
     */
    void newClip() {
        chargingMode = true
        controls.clear()
        anyValuePerClass.clear()
    }

    void verifyClip() {
    }

    boolean isChargingMode(){
        chargingMode
    }

    void addExecutionExpectation(Object mock, String methodName, Object[] arguments) {
        currentExpectation=[
            "obj":mock,
            "method":methodName,
            "args":arguments
        ]
    }

    Object exec(Object object, String methodName, Object[] args) {
      Control control = findControlFor(object)
      if (control == null){
          throw new IllegalArgumentException("""Unexpected mock instance was used.
                                Perhaps from old execution. Object = """+ object)
      } else {
          control.callMethod(methodName, args)
      }
    }
}
