package org.monroe.team.gock.trace.impl

import org.monroe.team.gock.trace.AbstractTracer
import org.monroe.team.gock.trace.Level

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
