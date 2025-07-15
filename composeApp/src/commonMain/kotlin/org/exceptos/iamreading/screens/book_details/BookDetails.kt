package org.exceptos.iamreading.screens.book_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.model.BookNotes
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.views.BookItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetails(
    bookId: Int,
    onNavigateBack: () -> Unit = {},
    viewModel: BookDetailsViewModel = remember { BookDetailsViewModel() }
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showAddNoteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var currentPage by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    LaunchedEffect(bookId) {
        viewModel.loadBook(bookId)
        viewModel.loadBookNotes()
    }

    val book by viewModel.book.collectAsState()
    val bookNotes by viewModel.bookNotes.collectAsState()
    val currentBookPage by viewModel.currentPage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(book?.title ?: "Book Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(Icons.Default.Edit, "Edit")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, "Delete")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                book?.let { currentBook ->
                    BookItem(
                        drawableResource = null,
                        title = currentBook.title,
                        description = currentBook.description,
                        author = currentBook.author
                    )
                }
            }

            item {
                AiInsightSection(
                    book = book,
                    notes = bookNotes,
                    modifier = Modifier.fillMaxWidth(),
                    viewModel = viewModel
                )
            }

            item {
                Text(
                    "Reading Status",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                BookStatusSection(book?.status, viewModel::updateBookStatus)
            }

            if (book?.status == BookStatus.CURRENTLY_READING.toString()) {
                item {
                    Column {
                        Text(
                            "Current Page",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = currentPage,
                            onValueChange = { 
                                currentPage = it
                                it.toIntOrNull()?.let { page ->
                                    viewModel.updateCurrentPage(page)
                                }
                            },
                            label = { Text("Page Number") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Notes",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Button(onClick = { showAddNoteDialog = true }) {
                                Text("Add Note")
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }

                items(bookNotes) { note ->
                    NoteItem(note)
                }
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Book") },
                text = { Text("Are you sure you want to delete this book?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteBook()
                            showDeleteDialog = false
                            onNavigateBack()
                        }
                    ) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (showAddNoteDialog) {
            AlertDialog(
                onDismissRequest = { showAddNoteDialog = false },
                title = { Text("Add Note") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = noteText,
                            onValueChange = { noteText = it },
                            label = { Text("Note") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = currentPage,
                            onValueChange = { currentPage = it },
                            label = { Text("Page Number") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            currentPage.toIntOrNull()?.let { page ->
                                viewModel.addBookNote(noteText, page)
                            }
                            showAddNoteDialog = false
                            noteText = ""
                            currentPage = ""
                        }
                    ) { Text("Add") }
                },
                dismissButton = {
                    TextButton(onClick = { showAddNoteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
private fun BookStatusSection(
    currentStatus: String?,
    onStatusUpdate: (BookStatus) -> Unit
) {
    Column {
        BookStatus.values().forEach { status ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentStatus == status.toString(),
                    onClick = { onStatusUpdate(status) }
                )
                Spacer(Modifier.width(8.dp))
                Text(status.toString().replace('_', ' '))
            }
        }
    }
}

@Composable
private fun NoteItem(note: BookNotes) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                note.description,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Page ${note.noteFromPage}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Added on ${note.dateAdded}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun AiInsightSection(
    book: Book?,
    notes: List<BookNotes>,
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel
) {
    var showAiDialog by remember { mutableStateOf(false) }
    var analyzeNotes by remember { mutableStateOf(false) }
    var question by remember { mutableStateOf("") }
    val aiResponse by viewModel.aiResponse.collectAsState()

    Column(modifier = modifier) {
        Text(
            "AI Insights",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { 
                    analyzeNotes = false
                    showAiDialog = true 
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Analyze Book")
            }
            Button(
                onClick = { 
                    analyzeNotes = true
                    showAiDialog = true 
                },
                modifier = Modifier.weight(1f),
                enabled = notes.isNotEmpty()
            ) {
                Text("Analyze Notes")
            }
        }

        aiResponse?.let { response ->
            Spacer(Modifier.height(16.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    response,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    if (showAiDialog) {
        AlertDialog(
            onDismissRequest = { showAiDialog = false },
            title = { Text(if (analyzeNotes) "Analyze Notes" else "Analyze Book") },
            text = {
                Column {
                    Text(if (analyzeNotes) 
                        "What would you like to know about your notes?" 
                        else "What would you like to know about this book?"
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = question,
                        onValueChange = { question = it },
                        label = { Text("Ask anything...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.analyzeBookWithAI(question, analyzeNotes)
                        showAiDialog = false
                        question = ""
                    },
                    enabled = question.isNotEmpty()
                ) {
                    Text("Ask AI")
                }
            },
            dismissButton = {
                TextButton(onClick = { 
                    showAiDialog = false
                    question = ""
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}