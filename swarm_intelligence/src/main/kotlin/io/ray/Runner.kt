
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
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

val maxParticles = 100
val maxIterations = 100

val cognitiveCoefficient = 0.7
val socialCoefficient = 0.7

val size = 14

fun main(args: Array<String>) {
  val res = ClassLoader.getSystemClassLoader().getResource("tsp_data")
  val baseData = readTspData(res)

  val threadPool = Executors.newWorkStealingPool()
  val submitted = threadPool.submit(Runner(baseData.subList(0, size)))
}

class Runner(val inputData: List<TspNode>) : Callable<Boolean> {
  override fun call(): Boolean {
    val vertices = inputData.size

    val particles = mutableListOf<Particle>()

    (0 until maxParticles).forEach {
      val basePath = generateRandomPath(inputData)
      particles.add(Particle(basePath))
    }

    for (i in 0 until maxIterations) {
      particles.sortBy { part -> part.bestPath.distance }
      val globBestPart = particles[0]

      particles.forEach { part ->
        val tempVel = mutableListOf<Array<Number>>()

        val globBestCopy = globBestPart.bestPath.path.toMutableList()
        val bestPathNodes = part.bestPath.path.toMutableList()

        val currPathNodes = part.path.path.toMutableList()

        // Generate all swap operators to calculate (pbest - x(t-1))
        for (j in 0 until vertices) {
          if (currPathNodes[j] != bestPathNodes[j]) {
            val swapOp = arrayOf<Number>(j, bestPathNodes.indexOf(currPathNodes[j]), cognitiveCoefficient)
            tempVel.add(swapOp)

            bestPathNodes.swap(swapOp[0] as Int, swapOp[1] as Int)
          }
        }

        // Generates all swap operators to calculate (gbest - x(t-1))
        for (j in 0 until vertices) {
          if (currPathNodes[j] != globBestCopy[j]) {
            val swapOp = arrayOf<Number>(j, globBestCopy.indexOf(currPathNodes[j]), socialCoefficient)
            tempVel.add(swapOp)

            globBestCopy.swap(swapOp[0] as Int, swapOp[1] as Int)
          }
        }

        // Update the velocity.
        part.velocity = tempVel

        // Generate new solutions
        tempVel.forEach { swap ->
          if (Random().nextDouble() <= swap[2] as Double) {
            currPathNodes.swap(swap[0] as Int, swap[1] as Int)
          }
        }

        currPathNodes[currPathNodes.lastIndex] = currPathNodes[0]

        part.path = Path(currPathNodes)

        if (part.path.distance < part.bestPath.distance) {
          part.bestPath = part.path
        }
      }
    }

    particles.forEach { println(it.path) }

    return true
  }
}