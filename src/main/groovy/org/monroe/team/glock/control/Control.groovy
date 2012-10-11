package org.monroe.team.glock.control

/**
 * User: mrjbee
 * Date: 10/11/12
 * Time: 4:08 PM
 */
class Control {

    private final Object mockedObject;

    Control(Object mockedObject) {
        this.mockedObject = mockedObject
    }

    Object getMockObject() {
        mockedObject
    }
}
