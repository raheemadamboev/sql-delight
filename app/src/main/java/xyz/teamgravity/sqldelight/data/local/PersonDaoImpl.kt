package xyz.teamgravity.sqldelight.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import xyz.teamgravity.sqldelight.PersonDatabase
import xyz.teamgravity.sqldelight.data.model.PersonModel
import xyz.teamgravity.sqldelight.data.model.toPerson

class PersonDaoImpl(
    db: PersonDatabase
) : PersonDao {

    private val queries = db.table_personQueries

    override suspend fun insertPerson(person: PersonModel) {
        withContext(Dispatchers.IO) {
            queries.insertPerson(id = person.id, firstName = person.firstName, lastName = person.lastName)
        }
    }

    override suspend fun deletePerson(id: Long) {
        withContext(Dispatchers.IO) {
            queries.deletePerson(id)
        }
    }

    override fun getAllPersons(): Flow<List<PersonModel>> {
        return queries.getAllPerson { id, firstName, lastName -> PersonModel(id, firstName, lastName) }.asFlow().mapToList()
    }

    override suspend fun getPerson(id: Long): PersonModel? {
        return withContext(Dispatchers.IO) {
            queries.getPerson(id).executeAsOneOrNull()?.toPerson()
        }
    }
}