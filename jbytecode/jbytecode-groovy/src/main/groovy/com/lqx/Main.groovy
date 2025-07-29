package com.lqx

class Duck {
    def fly() {
        println "Quack!"
    }
}

class AirPlane {
    def fly() {
        println "Flying!"
    }
}

class Whale {
    def swim() {
        println "whale is swimming!"
    }
}

def liftOff(entity) {
    entity.fly()
}


static void main(String[] args) {
    liftOff(new Duck())
    liftOff(new AirPlane())
    liftOff(new Whale())
}