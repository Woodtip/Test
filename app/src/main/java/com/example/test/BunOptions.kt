package com.example.test

enum class BunOptions(val description: String) {
    Brioche("Brioche Bun"),
    Sesame("Sesame Seed Bun"),
    Kaiser("Kaiser Roll"),
    Lettuce("Lettuce Wrap");

    companion object {
        fun convertSpinnerText(spinnerText: String): BunOptions {
            return when(spinnerText){
                Brioche.description -> Brioche
                Sesame.description -> Sesame
                Kaiser.description -> Kaiser
                else -> Lettuce
            }
        }
    }

}