package org.monroe.team.glock.trace.impl

import org.monroe.team.glock.trace.AbstractTracer
import org.monroe.team.glock.trace.Level

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 2:14 PM
 */
class ConsoleTracer extends AbstractTracer{

    ConsoleTracer(Level initLevel) {
        setLevel(initLevel)
    }

    @Override
    protected void debugImpl(String s) {
        println("<DEBUG> "+s)
    }

    @Override
    protected void infoImpl(String s) {
        println("<INFO> "+s)
    }
}
