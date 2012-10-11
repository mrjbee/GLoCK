package org.monroe.team.glock.control

import org.monroe.team.glock.matcher.ArgMatcher

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 5:40 PM
 */
class ExpectedMethod {

    private final String name
    private List<ArgMatcher> argMatcherList;
    private Closure body;
    private boolean executedOnce = false;
    private final boolean stub;

    ExpectedMethod(String name, boolean stub, List<ArgMatcher> argMatcherList, Closure body) {
        this.name = name
        this.argMatcherList = argMatcherList
        this.body = body
        this.stub = stub
    }

    String getName(){
        name
    }

    List<ArgMatcher> getArgMatcherList() {
        return argMatcherList
    }

    boolean getExecutedOnce() {
        return executedOnce
    }

    boolean matchArguments(Object[] args) {
        if (args == null || args.length == 0){
            return argMatcherList.empty;
        } //TODO have to check that with default values. aka (int i = 0)
        else if (args.length != argMatcherList.size()){
            return false
        } else {
            boolean answer = true;
            argMatcherList.eachWithIndex {ArgMatcher argMatcher,int index->
                if (!argMatcher.same(args[index])){
                    answer = false;
                    return answer
                }
            }
            return answer;
        }
    }

    boolean isNotUsed() {
        return !executedOnce && !stub
    }

    Object exec(Object[] arguments) {
        executedOnce = true
        body.call(arguments)
    }
}
