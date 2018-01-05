
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
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorCompletionService
import java.util.concurrent.Executors

val maxParticles = 100
val maxIterations = 100

val cognitiveCoefficient = 0.7
val socialCoefficient = 0.7

val size = 500

private val tp = Executors.newWorkStealingPool()
private val cs = ExecutorCompletionService<Boolean>(tp)

fun main(args: Array<String>) {
  val res = ClassLoader.getSystemClassLoader().getResource("tsp_data")
  val baseData = readTspData(res)
  val data: MutableList<String> = mutableListOf()
  val rt: Runtime = Runtime.getRuntime()
  val osmxb: OperatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean

  val particles = mutableSetOf<Particle>()
  while (particles.size < maxParticles) {
    val basePath = generateRandomPath(baseData.subList(0, size))
    particles.add(Particle(basePath))
  }

  val threadPool = Executors.newSingleThreadExecutor()
  val submitted = threadPool.submit(Runner(baseData.subList(0, size), particles.toMutableList()))

  while (!submitted.isDone) {
    data.add(recordBenchmark(rt, osmxb))
    Thread.sleep(1)
  }

  data.add(submitted.get())

  stringWriter(data, "/home/aray/temp/swarm_intell_$size.txt")

  System.exit(0)
}


class Runner(private val inputData: List<TspNode>, private val particles: MutableList<Particle>)
  : Callable<String> {

  override fun call(): String {
    val vertices = inputData.size
    var globalBest: Particle

    (0 until maxIterations).forEach {
      globalBest = particles.minBy { particle -> particle.bestPath.distance }!!

      val f = particles.map { part -> cs.submit(AsyncFlocker(part, globalBest, vertices))}
          .toMutableList()

      while (f.size > 0) f.remove(cs.take())
    }

    println("Done baby.")

    tp.shutdown()

    return particles.minBy { particle -> particle.bestPath.distance }!!.bestPath.toString()
  }
}