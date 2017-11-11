
/*
 * Copyright (c) 2017.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.ray

import org.junit.Assert.assertTrue
import org.junit.Test

class ListUtilTest {
  @Test
  fun swap() {
    val tempList = mutableListOf("A", "B")

    assertTrue(tempList[0] == "A")
    assertTrue(tempList[1] == "B")

    tempList.swap(0, 1)

    assertTrue(tempList[0] == "B")
    assertTrue(tempList[1] == "A")

    tempList.add("C")

    assertTrue(tempList[tempList.lastIndex] == "C")

    tempList.swap(0, tempList.lastIndex)

    assertTrue(tempList[0] == "C")
    assertTrue(tempList[tempList.lastIndex] == "B")
  }
}