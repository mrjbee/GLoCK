package org.monroe.team.glock.utils

import org.monroe.team.glock.control.ExpectedMethod

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 6:29 PM
 */
class StringExtractor {

    static String method(ExpectedMethod method) {
        return " ${method.name} [executed = ${method.executedOnce}] arg matchers "+method.argMatcherList
    }

    static String method(String methodName, Object[] args) {
        StringBuilder builder = new StringBuilder()
        builder.append(methodName + " (")
        if (args != null) {
            args.each {
                if (it != null) {
                    builder.append("${object(it)},")
                } else {
                    builder.append("null,")
                }
            }
            if (args.length != 0) {
                builder.deleteCharAt(builder.length - 1)
            }
        }
        builder.append(")")
        return builder.toString()
    }

    static String object(Object obj) {
        return "${obj.getClass().getName()}:${obj}"
    }

    static String methodList(List<ExpectedMethod> expectedMethods) {
        StringBuilder builder = new StringBuilder()
        expectedMethods.each {
            builder.append(method(it)+"\n")
        }
        return builder.toString()
    }
}
