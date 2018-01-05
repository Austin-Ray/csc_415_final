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

fun recordBenchmark(rt: Runtime, osmxb: OperatingSystemMXBean): String =
    "${System.currentTimeMillis()} \t ${getCpuUsage(osmxb)} \t ${bytesToKiloBytes(getMemoryUsage(rt))}"

/**
 * Calculate the memory usage of a Runtime.
 * @param rt    Runtime of the object
 * @return      Memory used by the runtime in bytes.
 */
fun getMemoryUsage(rt: Runtime) : Long = rt.totalMemory() - rt.freeMemory()

/**
 * Get the CPU usage of the process.
 * @param osmxb   OperatingSystemMXBean that contains the process.
 */
fun getCpuUsage(osmxb: OperatingSystemMXBean) : Double = osmxb.processCpuLoad

fun bytesToKiloBytes(bytes: Long) = bytes / 1024

fun bytesToMegaBites(bytes: Long) = bytesToKiloBytes(bytes) / 1024
