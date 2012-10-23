package org.monroe.team.glock.mock.factory

/**
 * User: mrjbee
 * Date: 10/23/12
 * Time: 8:45 PM
 */
class MockID {

    public static final DEFAULT = new MockID("DEFAULT");

    private final String ID;

    MockID(String ID) {
        this.ID = ID
    }

    String getID(){
     return ID;
    }
}
