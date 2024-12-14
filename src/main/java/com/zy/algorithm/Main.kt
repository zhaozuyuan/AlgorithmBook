package com.zy.algorithm

import com.zy.algorithm.book.Day24_1214

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val array = arrayOf(0,2,3,1,2,3,1)
    //---+--+
    //-+--+--
    //--+--+-
    println(Day24_1214.minJumpCount(array.toIntArray()))
    println(Day24_1214.minSideJumps(array.toIntArray()))
}