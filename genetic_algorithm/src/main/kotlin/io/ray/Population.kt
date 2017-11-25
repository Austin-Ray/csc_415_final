
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

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Self-contained runner of the genetic algorithm.
 *
 * Written to run on it's own thread to improve result density.
 */
class Population(private val num: Int, val entities: MutableList<Path>, private val maxSize: Int,
                 private val maxGens: Int) : Callable<String> {

  private val breederPool: ExecutorService = Executors.newCachedThreadPool()
  private var lastBest = entities[0]

  override fun call(): String {
    var bestCount = 0

    // Loop until it hits the max generations cap.
    (0 until maxGens).forEach {
      if (entities.size > maxSize) cull()

      // Track the best update if necessary.
      if (lastBest == entities[0]) {
        bestCount++
      } else {
        lastBest = entities[0]
        bestCount = 0
      }

      breed()
    }

    breederPool.shutdown()

    return entities[0].toString()
  }

  /**
   * Breed the paths to hopefully yield a better result.
   */
  private fun breed() {
    val elitismCount: Int = if (entities.size > 20) 20 else entities.size
    val eliteCopies = entities.subList(0, elitismCount).toList()

    val count = if (entities.size % 2 == 0) entities.size else entities.size - 1


    val futures = mutableListOf<Future<Path>>()

    (0 until count step 2).forEach { index ->
      futures.add(breederPool.submit(Breeder(entities[index], entities[index + 1])))
      futures.add(breederPool.submit(Breeder(entities[index + 1], entities[index])))
    }

    entities.clear()

    futures.forEach { entities.add(it.get()) }

    entities.addAll(eliteCopies)
    entities.sort()
  }

  /**
   * Cull a population if it exceeds the max limit to speed up execution time.
   * Most poor paths when breed with other poor paths will not yield good paths.
   */
  private fun cull() {
    val subList = entities.subList(0, maxSize).toList()
    entities.clear()
    entities.addAll(subList)
  }
}

