package xyz.teamgravity.sqldelight.data.model

import person.Table_person

data class PersonModel(
    val id: Long?,
    val firstName: String,
    val lastName: String
)

fun Table_person.toPerson() = PersonModel(
    id = id,
    firstName = firstName,
    lastName = lastName
)
