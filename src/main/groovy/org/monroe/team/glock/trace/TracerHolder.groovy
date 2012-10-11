package org.monroe.team.glock.trace

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 2:18 PM
 */
class TracerHolder {

    @Delegate
    Tracer tracer

    TracerHolder(Tracer tracer) {
        this.tracer = tracer
    }

}
