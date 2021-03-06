package org.monroe.team.glock.ext.autowire

import org.monroe.team.glock.mock.factory.MockFactory
import java.lang.annotation.Target
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * User: MisterJBee 
 * Date: 12/14/12 Time: 5:32 PM
 * Open source: MIT Licence 
 * (Do whatever you want with the source code)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface MockFactoryClass {
    Class<? extends org.monroe.team.glock.mock.factory.MockFactory> value();
}
