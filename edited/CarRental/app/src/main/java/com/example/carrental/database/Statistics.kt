package com.example.carrental.database

data class Statistics(
    val Income: Long =0L,
    val Outcome: Long = 0L
)
{
    val advantage: Long = Income - Outcome
}