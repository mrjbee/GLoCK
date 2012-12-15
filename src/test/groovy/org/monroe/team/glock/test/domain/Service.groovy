package org.monroe.team.glock.test.domain

/**
 * User: MisterJBee 
 * Date: 12/14/12 Time: 9:28 PM
 * Open source: MIT Licence 
 * (Do whatever you want with the source code)
 */
class Service {

    private IClass iClassInstance;
    private IClass iClassInstance2;
    private IClass2 iClass2Instance;

    public String doSomething(){
        String result = iClassInstance.methodB(0) + iClassInstance2.methodB(1)
        return iClass2Instance.promo() + result;
    }

}
