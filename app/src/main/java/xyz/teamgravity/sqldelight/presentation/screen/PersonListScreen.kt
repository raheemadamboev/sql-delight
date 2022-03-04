package xyz.teamgravity.sqldelight.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.teamgravity.sqldelight.data.model.PersonModel
import xyz.teamgravity.sqldelight.presentation.viewmodel.PersonListViewModel

@Composable
fun PersonListScreen() {
    val viewmodel = hiltViewModel<PersonListViewModel>()
    val persons by remember(viewmodel) { viewmodel.persons }.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                items(persons) { person ->
                    PersonCard(
                        person = person,
                        onPersonClick = viewmodel::onGetPerson,
                        onDeleteClick = viewmodel::onDeletePerson,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = viewmodel.firstName,
                    onValueChange = viewmodel::onFirstNameChange,
                    placeholder = {
                        Text(text = "First name")
                    },
                    modifier = Modifier.weight(1F)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = viewmodel.lastName,
                    onValueChange = viewmodel::onLastNameChange,
                    placeholder = {
                        Text(text = "Last name")
                    },
                    modifier = Modifier.weight(1F)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = viewmodel::onInsertPerson) {
                    Icon(
                        imageVector = Icons.Outlined.Done,
                        contentDescription = "Insert person",
                        tint = Color.Black
                    )
                }
            }
        }

        viewmodel.person?.let { person ->
            Dialog(onDismissRequest = viewmodel::onPersonDialogDismiss) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${person.firstName} ${person.lastName}")
                }
            }
        }
    }
}

@Composable
fun PersonCard(
    person: PersonModel,
    onPersonClick: (id: Long) -> Unit,
    onDeleteClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                onPersonClick(person.id!!)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = person.firstName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { onDeleteClick(person.id!!) }) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete person",
                tint = Color.Gray
            )
        }
    }
}