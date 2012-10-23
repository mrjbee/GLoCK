package org.monroe.team.glock

import groovy.mock.interceptor.MockProxyMetaClass
import org.monroe.team.glock.control.Control
import org.monroe.team.glock.args.matcher.ArgMatcher

import org.monroe.team.glock.args.matcher.EqualsArgMatcher
import org.monroe.team.glock.control.ExpectedMethod
import org.monroe.team.glock.utils.StringExtractor
import org.monroe.team.glock.args.matcher.ClosureMatcher
import org.monroe.team.glock.core.AccessInterceptor
import org.monroe.team.glock.args.ArgumentComparator
import org.monroe.team.glock.mock.MockInstanceHelper
import org.monroe.team.glock.mock.factory.MockFactory
import org.monroe.team.glock.mock.factory.MockID
import org.codehaus.groovy.runtime.DefaultGroovyMethods

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 1:56 PM
 */
class GLoCK {

    private static Object ANY_ARGS = new Object()
    private static Object ANY_ARGS_BUT_ARGS = new Object()
    private static List<ArgMatcher> ANY_ARGS_MATCHER_LIST = []
    private static List<ArgMatcher> ANY_ARGS_BUT_ARGS_MATCHER_LIST = []

    private final AccessInterceptor accessInterceptor = new AccessInterceptor(this)
    private boolean chargingMode = true
    private List<Control> controls =  new ArrayList<Control>()
    private Map currentExpectation = [:]
    private final static ArgumentComparator ARGUMENT_COMPARATOR_ANY = new ArgumentComparator({true})
    private final static MockInstanceHelper mockInstanceHelper = new MockInstanceHelper();

    private final MockFactory mockFactory

    GLoCK(MockFactory mockFactory) {
        this.mockFactory = mockFactory
    }

    GLoCK() {
        this.mockFactory = null
    }

    public ArgumentComparator any(){
        return ARGUMENT_COMPARATOR_ANY
    }

        /**
     * Create charge for class, abstract class or interface
     * @param args - current limitation, you should by pass arguments to match existing constructor if any
     * @param clazz - mocked instance class
     * @return charge, which you could use for testing
     */
    public <MockType> MockType charge(Class<MockType> clazz, Object... args = null){
        def instance = mockInstanceHelper.getMock(clazz, args)
        return chargeImpl(clazz, instance)
    }

    private <MockType> MockType chargeImpl(Class<MockType> clazz, MockType instance) {
        def thisProxy = MockProxyMetaClass.make(clazz)
        thisProxy.interceptor = accessInterceptor
        instance.metaClass = thisProxy
        Control control = new Control(instance)
        controls.add(control)
        //Add stub to class
        stubWith(instance.getClass(), {clazz})
        return instance as MockType
    }

    public <MockType> MockType chargePredefined(Class<MockType> clazz, MockID mockId = MockID.DEFAULT){
        if (!mockFactory) throw new IllegalStateException("No mock factory configured")
        def instance = mockFactory.getInstance(mockInstanceHelper,mockId,clazz)
        return chargeImpl(clazz,instance)
    }

    public void stubWith(def expectedMethod, Closure bullet){
        execIfClosure(expectedMethod)

        //TODO: add args pre-validation
        Control control = findControlFor(currentExpectation.obj);
        List<ArgMatcher> argMatcherList = createArgMatcherList()
        control.addMethod(new ExpectedMethod(currentExpectation.method, true,  argMatcherList, bullet))
        currentExpectation = null
    }

    public void mockWith(def expectedMethod, Closure bullet){
        execIfClosure(expectedMethod)

        //TODO: add args pre-validation
        Control control = findControlFor(currentExpectation.obj);
        List<ArgMatcher> argMatcherList = createArgMatcherList()
        control.addMethod(new ExpectedMethod(currentExpectation.method, false, argMatcherList, bullet))
        currentExpectation = null
    }

    private void execIfClosure(def expectedMethod) {
        if (expectedMethod instanceof Closure) {
            expectedMethod.call()
        }
    }

    private List<ArgMatcher> createArgMatcherList() {

        List<ArgMatcher> argMatcherList = [];
        Object[] args = currentExpectation.args

        List<ArgMatcher> predefinedList = probePreDefinedLists(args)
        if (predefinedList != null) return predefinedList;

        if (args) {
            args.each { Object arg ->
                if (arg instanceof ArgumentComparator){
                    argMatcherList.add(new ClosureMatcher(arg.argClosure))
                } else {
                    argMatcherList.add(new EqualsArgMatcher(arg))
                }
            }
        }
        argMatcherList
    }

    private List<ArgMatcher> probePreDefinedLists(Object[] args) {
        if (args && args.size() == 1) {
            if (ANY_ARGS.is(args[0])) {
                return ANY_ARGS_MATCHER_LIST;
            } else if (ANY_ARGS_BUT_ARGS.is(args[0])) {
                return ANY_ARGS_BUT_ARGS_MATCHER_LIST;
            }
        }
        return null
    }

    private Control findControlFor(Object o) {
        controls.find {Control it->
            return DefaultGroovyMethods.is(it.getMockObject(),o)
        }
    }

    void reload() {
        chargingMode = false
    }

    /**
     * Drop all expectations and stop watching any of created charge
     */
    void newClip() {
        chargingMode = true
        controls.clear()
     }

    void verifyClip() {
        StringBuilder builder = new StringBuilder()
        controls.each {Control control ->
           List<ExpectedMethod> unUsedExpectedMethodList = control.getUnUsedMockMethodList()
           if (!unUsedExpectedMethodList.empty){
               builder.append("-for mock = ${StringExtractor.object(control.mockObject)} : \n")
               unUsedExpectedMethodList.each {
                    builder.append("--"+StringExtractor.method(it)+"\n")
               }
           }
        }
        if (builder.length() !=0){
            throw new AssertionError("Unused mocks expactation \n"+builder.toString()+"\n")
        }
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
          throw new IllegalArgumentException("""Unexpected charge instance was used.
                                Perhaps from old execution. Object = """+ object)
      } else {
          control.callMethod(methodName, args)
      }
    }

    Closure doNothing() {
        return {}
    }

    Closure answerWith(def answer){
        return {
            answer
        }
    }

    def anyArgs() {
        return GLoCK.ANY_ARGS
    }

    def anyArgsButArgs() {
        return GLoCK.ANY_ARGS_BUT_ARGS
    }


}
