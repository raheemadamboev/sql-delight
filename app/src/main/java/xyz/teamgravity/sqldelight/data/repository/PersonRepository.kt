package xyz.teamgravity.sqldelight.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.sqldelight.data.local.PersonDao
import xyz.teamgravity.sqldelight.data.model.PersonModel

class PersonRepository(
    private val dao: PersonDao
) {

    ///////////////////////////////////////////////////////////////////////////
    // Insert
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertPerson(person: PersonModel) {
        dao.insertPerson(person)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Delete
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deletePerson(id: Long) {
        dao.deletePerson(id)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Get
    ///////////////////////////////////////////////////////////////////////////

    fun getAllPerson(): Flow<List<PersonModel>> {
        return dao.getAllPersons()
    }

    suspend fun getPerson(id: Long): PersonModel? {
        return dao.getPerson(id)
    }
}