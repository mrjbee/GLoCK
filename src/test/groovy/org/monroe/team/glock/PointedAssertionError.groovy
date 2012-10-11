package org.monroe.team.glock

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 9:35 PM
 */
class PointedAssertionError extends RuntimeException {
    PointedAssertionError(Throwable throwable) {
        super(throwable)
    }
}
