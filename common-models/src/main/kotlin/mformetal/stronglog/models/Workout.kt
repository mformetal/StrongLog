package mformetal.stronglog.models

class Workout(val title: String,
              val muscles: Muscles,
              val classification: Classification?,
              val gifUrl: String,
              val videoUrl: String)