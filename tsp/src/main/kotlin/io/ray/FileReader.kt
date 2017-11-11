
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

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.streams.toList

val delim = ','

/**
 * Read in the TSP data from a given file.
 *
 * This is a function implementation that goes through every line,
 * maps the line to an array, then maps each element in the array to
 * it's appropriate position in the TspNode construction. Finally,
 * the map is turned into a list and returned.
 */
fun readTspData(path: URL) : List<TspNode> = BufferedReader(InputStreamReader(path.openStream()))
    .lines()
    .map { line -> line.split(delim)}
    .map { elems -> TspNode(elems[2], doubleArrayOf(elems[0].toDouble(), elems[1].toDouble()))}
    .toList()
