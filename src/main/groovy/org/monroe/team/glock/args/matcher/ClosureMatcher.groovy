package org.monroe.team.glock.args.matcher

/**
 * User: mrjbee
 * Date: 10/17/12
 * Time: 11:03 PM
 */
class ClosureMatcher implements ArgMatcher{

    private final Closure comparator

    ClosureMatcher(Closure comparator) {
        this.comparator = comparator
    }

    @Override
    boolean same(Object arg) {
        return comparator.call(arg)
    }

    @Override
    String toString(){
        return "Comparator"
    }
}
