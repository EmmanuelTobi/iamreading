package org.exceptos.iamreading.screens.book_lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.widgets.TextWidget
import iamreading.composeapp.generated.resources.Res
import iamreading.composeapp.generated.resources.ic_add
import iamreading.composeapp.generated.resources.ic_arrow_left

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBook(
    onNavigateBack: () -> Unit = {}
) {

    var bookListViewModel: BookListsViewModel = remember { BookListsViewModel() }

    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var totalPages by remember { mutableStateOf("") }
    var currentPage by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf(BookStatus.WANT_TO_READ) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TextWidget(
                    "Add New Book",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_arrow_left),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_add),
                        contentDescription = "Add Image",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        "Tap to add cover",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = RoundedCornerShape(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = totalPages,
                    onValueChange = { totalPages = it },
                    label = { Text("Total Pages (optional)") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = currentPage,
                    onValueChange = { currentPage = it },
                    label = { Text("Current Page (optional)") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // Status selector (similar to BookDetails)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Reading Status (can be changed later)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    BookStatus.values().forEach { status ->
                        val isSelected = selectedStatus == status
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            ),
                            onClick = { selectedStatus = status }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedStatus = status }
                                )
                                Spacer(Modifier.width(12.dp))
                                Text(
                                    text = status.name.replace('_', ' '),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (title.isNotBlank() && author.isNotBlank()) {
                        bookListViewModel.addBook(
                            title = title,
                            author = author,
                            description = description,
                            imageUrl = null,
                            status = selectedStatus,
                            totalPages = totalPages.toIntOrNull() ?: 0,
                            currentPage = currentPage.toIntOrNull() ?: 0
                        )

                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(56.dp),
            ) {
                TextWidget(
                    "Add Book",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}

