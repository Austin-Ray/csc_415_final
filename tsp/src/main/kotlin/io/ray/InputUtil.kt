
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
 * Get input from a user as an integer.
 * @param prompt    Prompt to display to the user.
 * @param defVal    Default value
 * @return          Input parsed as an integer.
 */
fun getInputAsInt(prompt: String, defVal: Any?) : Int {
  return getInput(prompt, defVal).toInt()
}

/**
 * Get input from a user.
 * @param prompt    Prompt to display.
 * @param defVal    Default value
 * @return          User input
 */
private fun getInput(prompt: String, defVal: Any?) : String {
  print("$prompt [Default $defVal]: ")
  val line = readLine()!!
  return if (line.isNotEmpty()) line else defVal.toString()
}
