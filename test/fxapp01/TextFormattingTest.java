/*
 * Copyright 2015 serg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fxapp01;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.text.MessageFormat;

/**
 *
 * @author serg
 */
public class TextFormattingTest {
    
    public TextFormattingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void StringConcatenationTiming() {
        String s;
        long time1 = System.currentTimeMillis();
        for (int i=0; i<1000000; i++) {
            s = "abc"+i+"def"+(i+1);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("String concatenation. time="+(time2-time1));
        StringBuilder sb = new StringBuilder();
        time1 = System.currentTimeMillis();
        for (int i=0; i<1000000; i++) {
            sb.append("abc").append(i).append("def").append(i+1);
        }
        time2 = System.currentTimeMillis();
        System.out.println("StringBuilder.append. time="+(time2-time1));
        time1 = System.currentTimeMillis();
        for (int i=0; i<1000000; i++) {
            s = MessageFormat.format("abc{0}def{1}", i, i+1);
        }
        time2 = System.currentTimeMillis();
        System.out.println("MessageFormat.format. time="+(time2-time1));
    }
}
