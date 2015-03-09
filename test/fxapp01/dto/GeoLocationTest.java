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
package fxapp01.dto;

import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author serg
 */
public class GeoLocationTest {
    
    private final ILogger log = LogMgr.getLogger(this.getClass()); 
    private final double locationPrecision = 0.000001;

    public GeoLocationTest() {
        //fail("The test case is a prototype.");
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

    private GeoLocation GeoLocationZero() {
        return new GeoLocation(0, 0);
    }
    
    /**
     * Test of getLatitude method, of class GeoLocation.
     */
    @Test
    public void testGetLatitude() {
        log.debug("getLatitude");
        GeoLocation instance = GeoLocationZero();
        double expResult = 0.0;
        double result = instance.getLatitude();
        assertEquals(expResult, result, locationPrecision);
    }

    /**
     * Test of setLatitude method, of class GeoLocation.
     */
    @Test
    public void testSetLatitude() {
        log.debug("setLatitude");
        double lat = 1.0;
        GeoLocation instance = GeoLocationZero();
        instance.setLatitude(lat);
        double result = instance.getLatitude();
        assertEquals(lat, result, locationPrecision);
    }

    /**
     * Test of getLongitude method, of class GeoLocation.
     */
    @Test
    public void testGetLongitude() {
        log.debug("getLongitude");
        GeoLocation instance = GeoLocationZero();
        double expResult = 0.0;
        double result = instance.getLongitude();
        assertEquals(expResult, result, locationPrecision);
    }

    /**
     * Test of setLongitude method, of class GeoLocation.
     */
    @Test
    public void testSetLongitude() {
        log.debug("setLongitude");
        double lon = 1.0;
        GeoLocation instance = GeoLocationZero();
        instance.setLongitude(lon);
        double result = instance.getLongitude();
        assertEquals(lon, result, locationPrecision);
    }

    /**
     * Test of distanceTo method, of class GeoLocation.
     */
    @Test
    public void testDistanceTo() {
        log.debug("distanceTo");
        //Москва, нулевой км
        GeoLocation a =  new GeoLocation(55.755831, 37.617673);
        //Москва, храм Василия Блаженного
        GeoLocation b =  new GeoLocation(55.752569,37.623435);
        double expResult = 510;
        double result = a.distanceTo(b);
        assertEquals(expResult, result, 10);
        //аэропорт Шереметьево, округ Химки, Московская область, Россия
        b =  new GeoLocation(55.973185,37.415936);
        expResult = 27300;
        result = a.distanceTo(b);
        assertEquals(expResult, result, 500);
        //г. Волгоград
        b =  new GeoLocation(48.712527, 44.513586);
        expResult = 912000;
        result = a.distanceTo(b);
        assertEquals(expResult, result, 1000);
        //г. Сингапур
        b =  new GeoLocation(1.238506, 103.833232);
        expResult = 8426000;
        result = a.distanceTo(b);
        assertEquals(expResult, result, 1000);
        //г. Мельбурн
        b =  new GeoLocation(-37.868556, 144.909511);
        expResult = 14415000;
        result = a.distanceTo(b);
        assertEquals(expResult, result, 10000);
        //г. Гонолулу (США, Гаваи)
        b =  new GeoLocation(21.299549, -157.883331);
        expResult = 11315000;
        result = a.distanceTo(b);
        assertEquals(expResult, result, 10000);
        //архипелаг Огненная Земля (Аргентина)
        b =  new GeoLocation(-54.670221, -65.153145);
        expResult = 15365000;
        result = a.distanceTo(b);
        assertEquals(expResult, result, 10000);
    }
}
