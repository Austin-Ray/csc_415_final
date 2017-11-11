
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

import java.lang.Math.exp
import java.lang.Math.pow
import java.lang.Math.sqrt

import kotlin.DoubleArray as DA

/**
 * Calculate the distance between two points represented by a DoubleArray.
 *
 * @param p1  First point
 * @param p2  Second point
 * @return    Distance between point 1 and point 2
 */
fun distance(p1: DA, p2: DA) : Double = sqrt(pow(p1[0] - p2[0], 2.0) + pow(p1[1] - p2[1], 2.0))

fun sigmoid(y: Double) : Double = 1.0 / (1.0 + exp(-1.0 * y))
