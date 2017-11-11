
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

import java.util.concurrent.Executors
import java.util.concurrent.Future

var pops: Int     = 0
var maxPop: Int   = 0
var initPop: Int  = 0
var maxGen: Int   = 0

/**
 * Run the actual program.
 */
fun run(args: List<Int>) {
  printMessage("Spooling")

  val classloader = ClassLoader.getSystemClassLoader()
  val tspData = readTspData(classloader.getResource("tsp_data"))

  pops    = args[0]
  maxPop  = args[1]
  initPop = args[2]
  maxGen  = args[3]

  println("Populations: $pops\n" +
      "Initial population: $initPop\n" +
      "Maximum population: $maxPop\n")

  val populations: MutableList<Population> = mutableListOf()

  (0 until pops).forEach { index ->
    val initPaths = (0 until initPop).map { generateRandomPath(tspData) }.toMutableList()
    val tempPop = Population(index, initPaths, maxPop, maxGen)
    populations.add(tempPop)

    println("Population $index initialized with ${tempPop.entities.size} path(s)")
  }

  printMessage("System initialized")
  printMessage("Running")


  val threadPool = Executors.newWorkStealingPool()
  val futures = mutableListOf<Future<String>>()

  populations.forEach { futures.add(threadPool.submit(it)) }

  futures.forEach { println(it.get()) }

  threadPool.shutdown()
  printMessage("Finished")


  threadPool.shutdown()
  System.exit(0)
}

