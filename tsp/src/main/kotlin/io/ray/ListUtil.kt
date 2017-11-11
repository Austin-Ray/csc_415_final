
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

/**
 * Helper extension to swap two items in a list.
 * @param pos1    First position
 * @param pos2    Second position
 */
fun <E> MutableList<E>.swap(pos1: Int, pos2: Int) {
  val elem2 = this.removeAt(pos2)
  val elem1 = this.removeAt(pos1)

  this.add(pos1, elem2)
  this.add(pos2, elem1)
}