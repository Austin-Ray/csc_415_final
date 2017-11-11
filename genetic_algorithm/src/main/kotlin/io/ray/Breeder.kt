
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
import java.util.concurrent.Callable

/**
 * Helper class for async breeding of paths.
 */
class Breeder(private val parent1: Path, private val parent2: Path) : Callable<Path> {
  override fun call(): Path {
    val path1Nodes = parent1.path.toList()
    val path2Nodes = parent2.path.toList()

    val numFromParent1 = Random().nextInt(path1Nodes.size - 2) + 1
    val newPath = mutableListOf<TspNode>()

    // Breed
    newPath.addAll(path1Nodes.subList(0, numFromParent1))
    newPath.addAll(path2Nodes.subtract(newPath))

    // Mutate
    val swapPos1 = Random().nextInt(newPath.size - 1)
    val swapPos2 = Random().nextInt(newPath.size - 1)

    newPath.swap(swapPos1, swapPos2)

    // Connect path's last point to the first point.
    newPath.add(newPath[0])

    return Path(newPath)
  }
}
