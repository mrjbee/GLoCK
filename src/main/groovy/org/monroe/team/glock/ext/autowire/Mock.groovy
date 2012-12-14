package org.monroe.team.glock.ext.autowire

import org.monroe.team.glock.mock.factory.MockID
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
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Mock {
    public String id();
}
