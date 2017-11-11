
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

import java.util.*

/**
 * Representation of a single node in the TSP problem.
 * @param label   Label of the node
 * @param points  Coordinates of the point.
 */
data class TspNode(private val label: String, val points: DoubleArray) {
  /**
   * Auto-generated code.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TspNode

    if (label != other.label) return false
    if (!Arrays.equals(points, other.points)) return false

    return true
  }

  /**
   * Auto-generated code.
   */
  override fun hashCode(): Int {
    var result = label.hashCode()
    result = 31 * result + Arrays.hashCode(points)
    return result
  }

  override fun toString(): String = label
}