package org.monroe.team.glock.trace

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 2:10 PM
 */
abstract class AbstractTracer implements Tracer{

    Level level;

    @Override
    void info(String message) {
        if (Level.OFF == level){
            return
        }
        infoImpl(message);
    }

    @Override
    void debug(String message) {
        if (Level.DEBUG == level){
            debugImpl(message)
        }
    }

    abstract protected void debugImpl(String s)
    abstract protected void infoImpl(String s);

}
