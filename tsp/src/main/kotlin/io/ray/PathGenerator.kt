
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
 * Generate a path based on input data
 * @return randomized path.
 */
fun generateRandomPath(points: List<TspNode>) : Path {
  val tempPath: MutableList<TspNode> = mutableListOf()
  val tempPoints = points.toMutableList()

  Collections.shuffle(tempPoints)
  tempPath.addAll(tempPoints)
  tempPath.add(tempPath[0])

  return Path(tempPath)
}