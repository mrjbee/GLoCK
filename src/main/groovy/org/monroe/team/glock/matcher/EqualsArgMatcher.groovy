package org.monroe.team.glock.matcher

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 4:52 PM
 */
class EqualsArgMatcher implements ArgMatcher{

    private final def expectedValue;

    EqualsArgMatcher(expectedValue) {
        this.expectedValue = expectedValue
    }

    @Override
    boolean same(def arg) {
        if (expectedValue == null)
            return arg == null
        else
            return expectedValue.equals(arg)
    }
}
