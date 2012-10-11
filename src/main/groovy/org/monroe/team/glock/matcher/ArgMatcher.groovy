package org.monroe.team.glock.matcher

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 4:47 PM
 */
interface ArgMatcher {
    boolean same(def arg)
    String description()
}
