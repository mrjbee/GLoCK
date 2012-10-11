package org.monroe.team.glock.matcher

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 4:49 PM
 */
class NoOpArgMatcher implements ArgMatcher{

    static final ALWAYS_TRUE = new NoOpArgMatcher(true);
    static final ALWAYS_FALSE = new NoOpArgMatcher(false);

    private final boolean fixedValue;

    NoOpArgMatcher(boolean fixedValue) {
        this.fixedValue = fixedValue
    }

    @Override
    boolean same(def arg) {

    }
}
