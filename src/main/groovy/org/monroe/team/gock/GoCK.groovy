package org.monroe.team.gock

import org.monroe.team.gock.trace.Tracer
import org.monroe.team.gock.trace.impl.ConsoleTracer
import org.monroe.team.gock.trace.Level
import org.monroe.team.gock.trace.TracerHolder
import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.MockProxyMetaClass

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 1:56 PM
 */
class GoCK {

    private TracerHolder tracerHolder = new TracerHolder(new ConsoleTracer(Level.OFF))

    void setTracer(Tracer tracer){
       tracerHolder.setTracer(tracer)
    }

    Tracer getTracer(){
       tracerHolder.tracer
    }

    public <MockType> MockType mock(Object[] args = null, Class<MockType> clazz){
        def instance = MockFor.getInstance(clazz, args)
        def thisProxy = MockProxyMetaClass.make(clazz)
        thisProxy.interceptor = new AccessInterceptor()
        instance.metaClass = thisProxy
        return instance
    }

}
