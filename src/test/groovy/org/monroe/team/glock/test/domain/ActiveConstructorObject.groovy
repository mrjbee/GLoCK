package org.monroe.team.glock.test.domain

/**
 * User: mrjbee
 * Date: 10/18/12
 * Time: 1:00 AM
 */
class ActiveConstructorObject {

    ActiveConstructorObject(Closure closure) {
        closure.call()
    }
}
