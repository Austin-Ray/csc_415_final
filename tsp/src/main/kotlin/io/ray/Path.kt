
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

/**
 * Holder for a path.
 * Reduces amount of manual managing of List<TspNode>
 * @param path  Mutable list of TSP nodes for the path.
 */
data class Path(val path: List<TspNode>) : Comparable<Path> {

  // Get the distance. If it's not calculated, calculate then save for later.
  var distance = -1.0
    get() {
      if (field == -1.0) {
        field = calculateDistance(path.toList())
      }

      return field
    }

  override fun compareTo(other: Path): Int {
    val dist = distance.minus(other.distance)

    return when {
      dist > 0 -> 1
      dist < 0 -> -1
      else -> 0
    }
  }

  override fun toString(): String  = "[ Distance: ${distance.format(2)}, " +
      "${path.joinToString("->")}]"
}