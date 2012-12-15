package org.monroe.team.glock.ext.autowire

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * User: MisterJBee 
 * Date: 12/14/12 Time: 5:32 PM
 * Open source: MIT Licence 
 * (Do whatever you want with the source code)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface CreateMethod {
    String value() default "[nAn]";
}
