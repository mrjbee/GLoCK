package org.monroe.team.glock.control

import org.monroe.team.glock.matcher.ArgMatcher
import org.monroe.team.glock.GLoCK

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

    //TODO: method mathing should be fully placed in a method itself
    boolean matchArguments(Object[] args) {

        if (GLoCK.ANY_ARGS_MATCHER_LIST.is(argMatcherList)){
            return true;
        }

        if (GLoCK.ANY_ARGS_BUT_ARGS_MATCHER_LIST.is(argMatcherList)){
            if (args == null || args.length == 0) return false
                else return true
        }

        if (args == null || args.length == 0){
            return argMatcherList.empty;
        }

        if (args.length != argMatcherList.size()){
            return false
        }



        boolean answer = true;
        argMatcherList.eachWithIndex {ArgMatcher argMatcher,int index->
            if (!argMatcher.same(args[index])){
                answer = false;
                return answer
            }
        }
        return answer;

    }

    boolean isNotUsed() {
        return !executedOnce && !stub
    }

    Object exec(Object[] arguments) {
        executedOnce = true
        try {
            return  body.getMetaClass().invokeMethod(body,"doCall",arguments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
