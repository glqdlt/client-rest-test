package com.glqdlt.tdd;

import com.glqdlt.tdd.clientresttest.ClientRestTemplateTest;
import com.glqdlt.tdd.clientresttest.ClientRestTestApplicationTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Jhun
 * 2019-02-15
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {ClientRestTemplateTest.class,
        ClientRestTestApplicationTests.class}
        )
public class ClientRestTestSuite {
}
