package xyz.teamgravity.sqldelight.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import xyz.teamgravity.sqldelight.data.model.PersonModel
import xyz.teamgravity.sqldelight.data.repository.PersonRepository
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val repository: PersonRepository
) : ViewModel() {

    val persons: Flow<List<PersonModel>> = repository.getAllPerson()

    var person by mutableStateOf<PersonModel?>(null)
        private set

    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    fun onInsertPerson() {
        if (firstName.isBlank() || lastName.isBlank()) return

        viewModelScope.launch {
            repository.insertPerson(
                PersonModel(
                    id = null,
                    firstName = firstName,
                    lastName = lastName
                )
            )
        }
    }

    fun onDeletePerson(id: Long) {
        viewModelScope.launch {
            repository.deletePerson(id)
        }
    }

    fun onGetPerson(id: Long) {
        viewModelScope.launch {
            person = repository.getPerson(id)
        }
    }

    fun onFirstNameChange(value: String) {
        firstName = value
    }

    fun onLastNameChange(value: String) {
        lastName = value
    }

    fun onPersonDialogDismiss() {
        person = null
    }
}