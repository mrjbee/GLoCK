package org.monroe.team.glock.control

import org.monroe.team.glock.utils.StringExtractor

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 4:08 PM
 */
class Control {

    private final Object mockedObject;
    private final Map<String, ExpectedMethod> mockedMethodMap = [:];

    Control(Object mockedObject) {
        this.mockedObject = mockedObject
    }

    Object getMockObject() {
        mockedObject
    }

    void addMethod(ExpectedMethod method) {
        List<ExpectedMethod> methodStack = mockedMethodMap.get(method.getName())
        if (methodStack == null){
            methodStack = new ArrayList<ExpectedMethod>()
            mockedMethodMap.put(method.name, methodStack)
        }
        methodStack.add(method)
    }

    Object callMethod(String methodName, Object[] args) {
       List<ExpectedMethod> expectedMethodCallList = mockedMethodMap.get(methodName)
       if (expectedMethodCallList == null || expectedMethodCallList.size() == 0){
           throw new AssertionError("Unexpected call for ${StringExtractor.object(mockedObject)} - '${StringExtractor.method(methodName,args)}'")
       } else {
           ExpectedMethod method = findSuitableMethods(args, expectedMethodCallList)
           if (! method){
               throw new AssertionError("Unexpected call for ${StringExtractor.object(mockedObject)} - '${StringExtractor.method(methodName,args)} \n"
                                        +"expected are: \n${StringExtractor.methodList(expectedMethodCallList)} ")
           }
           return method.exec(args)
       }
    }


    ExpectedMethod findSuitableMethods(Object[] args, List<ExpectedMethod> expectedMethodList) {
      expectedMethodList.find {ExpectedMethod method ->
          (!method.executedOnce && method.matchArguments(args))
      }
    }

    List<ExpectedMethod> getUnUsedMockMethodList() {
        List<ExpectedMethod> answer = [];
        mockedMethodMap.each {key, List<ExpectedMethod> methods ->
            methods.each { ExpectedMethod method ->
                 if (method.isNotUsed()){
                     answer.add(method)
                 }
            }
        }
        return answer
    }
}
