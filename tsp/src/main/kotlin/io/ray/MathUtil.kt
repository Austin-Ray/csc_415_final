
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
 * Calculate the total distance of a path.
 * @param path    List of TSP nodes.
 * @return        Total distance of the path
 */
fun calculateDistance(path: List<TspNode>) : Double = path.indices
    .sumByDouble { i ->
      if (i != path.lastIndex) distOfTwoPoints(path[i].points, path[i + 1].points) else 0.0
    }

/**
 * Calculate the distance between two-points.
 * @param point1  First point represented by a DoubleArray
 * @param point2  Second point represented by a DoubleArray.
 * @return        Distance between two points.
 */
fun distOfTwoPoints(point1: DoubleArray, point2: DoubleArray) : Double =
    Math.sqrt(Math.pow(point1[0] - point2[0], 2.0) + Math.pow(point1[1] - point2[1], 2.0))
