/**
 * ngx-distributed-shm
 * Copyright (C) 2018  Flu.Tech
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.grrolland.hcshm;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Global Protocol Test Case
 */
public class DeleteTestCase extends  AbstractHCSHMGetTestCase {

    /**
     * Test deleting a key
     */
    @Test
    public void testDelete() {

        try {
            getWriter().write("SET key 0 10\r\n");
            getWriter().write("1234567890");
            getWriter().flush();
            String res = getReader().readLine();
            Assert.assertEquals("LEN 10", res);
            res = getReader().readLine();
            Assert.assertEquals("1234567890", res);
            res = getReader().readLine();
            Assert.assertEquals("DONE", res);

            getWriter().write("DELETE key\r\n");
            getWriter().flush();
            res = getReader().readLine();
            Assert.assertEquals("DONE", res);

            getWriter().write("GET key\r\n");
            getWriter().flush();
            res = getReader().readLine();
            Assert.assertEquals("ERROR not_found", res);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Test deleting a key in a region
     */
    @Test
    public void testRegionDelete() {

        try {
            getWriter().write("SET region:key 0 10\r\n");
            getWriter().write("1234567890");
            getWriter().flush();
            String res = getReader().readLine();
            Assert.assertEquals("LEN 10", res);
            res = getReader().readLine();
            Assert.assertEquals("1234567890", res);
            res = getReader().readLine();
            Assert.assertEquals("DONE", res);

            getWriter().write("DELETE region:key\r\n");
            getWriter().flush();
            res = getReader().readLine();
            Assert.assertEquals("DONE", res);

            getWriter().write("GET region:key\r\n");
            getWriter().flush();
            res = getReader().readLine();
            Assert.assertEquals("ERROR not_found", res);

        }
        catch (IOException e)
        {
            Assert.fail(e.getMessage());
        }

    }

    /**
     * Test getting a non existent key
     */
    @Test
    public void testDeleteMalformed() {

        try {
            getWriter().write("DELETE notexists bababi\r\n");
            getWriter().flush();
            String res = getReader().readLine();
            Assert.assertEquals("ERROR malformed_request", res);

        }
        catch (IOException e)
        {
            Assert.fail(e.getMessage());
        }

    }

}
