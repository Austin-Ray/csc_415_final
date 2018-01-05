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

val inertia: Double = 0.1

class AsyncFlocker(private val part: Particle, private val globalBest: Particle,
                   private val vertices: Int) : Callable<Boolean>  {
  override fun call(): Boolean {

    val globBestCopy = globalBest.path.path.toMutableList()
    val bestPathNodes = part.bestPath.path.toMutableList()

    val currPathNodes = part.path.path.toMutableList()
    val personalSwaps = mutableListOf<SwapOp>()
    val globalSwaps = mutableListOf<SwapOp>()

    // Generate all swap operators to calculate (pbest - x(t-1))
    for (j in 0 until vertices) {
      if (currPathNodes[j] != bestPathNodes[j]) {
        val swapOp = SwapOp(j, bestPathNodes.indexOf(currPathNodes[j]), cognitiveCoefficient * Random().nextDouble())
        personalSwaps.add(swapOp)

        bestPathNodes.swap(swapOp.pos1, swapOp.pos2)
      }
    }

    for (j in 0 until vertices) {
      if (currPathNodes[j] != globBestCopy[j]) {
        val swapOp = SwapOp(j, globBestCopy.indexOf(currPathNodes[j]), socialCoefficient * Random().nextDouble())
        globalSwaps.add(swapOp)

        globBestCopy.swap(swapOp.pos1, swapOp.pos2)
      }
    }

    val tempVel = HashSet<SwapOp>()
    tempVel.addAll(tempVel)
    tempVel.addAll(personalSwaps)
    tempVel.addAll(globalSwaps)

    // Update the velocity.
    part.velocity = tempVel.toList()

    return true
  }
}

data class SwapOp(val pos1: Int, val pos2: Int, val coeff: Double) {
  override fun equals(other: Any?): Boolean {
    if (other is SwapOp) {
      return (pos1 == other.pos1 || pos1 == other.pos2)
          && (pos2 == other.pos1 || pos2 == other.pos1)
          && coeff != inertia
    }

    return false
  }

  override fun hashCode(): Int {
    var result = pos1
    result = 31 * result + pos2
    result = 31 * result + coeff.hashCode()
    return result
  }
}