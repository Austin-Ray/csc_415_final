
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

import com.sun.management.OperatingSystemMXBean
import java.lang.management.ManagementFactory
import java.util.concurrent.Executors
import java.util.concurrent.Future

val pops: Int     = 1
val maxPop: Int   = 100
val initPop: Int  = 100
val maxGen: Int   = 1

val size = 500

/**
 * Run the actual program.
 */
fun main(args: Array<String>) {
  val classloader = ClassLoader.getSystemClassLoader()
  val baseData = readTspData(classloader.getResource("tsp_data"))
  val tspData = baseData.subList(0, size)
  val data: MutableList<String> = mutableListOf()
  val rt: Runtime = Runtime.getRuntime()
  val osmxb: OperatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean


  val populations: MutableList<Population> = mutableListOf()

  val threadPool = Executors.newWorkStealingPool()

  (0 until pops).forEach { index ->
    val initPaths = (0 until initPop).map { generateRandomPath(tspData) }.toMutableList()
    val tempPop = Population(initPaths, maxPop, maxGen)
    populations.add(tempPop)
  }


  val futures = mutableListOf<Future<String>>()

  val future = threadPool.submit(populations[0])

  while (!future.isDone) {
    data.add(recordBenchmark(rt, osmxb))
    Thread.sleep(1)
  }

  data.add(future.get())

  stringWriter(data, "/home/aray/temp/ga_$size.txt")

  threadPool.shutdown()

  System.exit(0)
}

