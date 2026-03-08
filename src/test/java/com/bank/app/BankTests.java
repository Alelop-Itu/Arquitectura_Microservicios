package com.bank.app;

import com.intuit.karate.junit5.Karate;

class BankTests {
    @Karate.Test
    Karate testAll() {
        return Karate.run("BankTests").relativeTo(getClass());
    }
}