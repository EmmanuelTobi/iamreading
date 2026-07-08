package org.exceptos.iamreading.screens.book_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import org.jetbrains.compose.resources.painterResource
import iamreading.composeapp.generated.resources.Res
import iamreading.composeapp.generated.resources.ic_add
import iamreading.composeapp.generated.resources.ic_arrow_left
import iamreading.composeapp.generated.resources.ic_auto_awesome
import iamreading.composeapp.generated.resources.ic_delete
import iamreading.composeapp.generated.resources.ic_edit
import iamreading.composeapp.generated.resources.ic_info
import iamreading.composeapp.generated.resources.ic_sticky_note
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.model.BookNotes
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.themes.AccentGreen
import org.exceptos.iamreading.themes.Primary
import org.exceptos.iamreading.views.BookItem
import org.jetbrains.compose.resources.painterResource

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

    LaunchedEffect(currentBookPage) {
        currentPage = currentBookPage?.toString() ?: ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (book?.status == BookStatus.CURRENTLY_READING.toString()) "Reading Progress" else "Book Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_arrow_left),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_edit),
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_delete),
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Book Info
            item {
                book?.let { currentBook ->
                    BookItem(
                        drawableResource = null,
                        title = currentBook.title,
                        description = currentBook.description,
                        author = currentBook.author,
                        status = currentBook.status
                    )
                }
            }

            // Reading Progress Section (only for currently reading)
            if (book?.status == BookStatus.CURRENTLY_READING.toString()) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Hero card with progress
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Primary.copy(alpha = 0.08f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {
                                // Placeholder image area (like the tree/reading image)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                painter = painterResource(Res.drawable.ic_info),
                                contentDescription = null,
                                tint = Primary.copy(alpha = 0.4f),
                                modifier = Modifier.size(60.dp)
                            )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Progress percentage
                val currentPageNum = currentBookPage ?: 0
                val totalPages = book?.totalPages ?: 200
                val progress = if (totalPages > 0) currentPageNum.toFloat() / totalPages else 0f

                                Text(
                                    text = "${(progress * 100).toInt()}%",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Progress bar
                                LinearProgressIndicator(
                                    progress = { progress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = Primary,
                                    trackColor = Primary.copy(alpha = 0.15f)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Page info
                                Text(
                                    text = "Page $currentPageNum of ${book?.totalPages ?: 200}",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }

                        // Current Page Input
                        Column {
                            Text(
                                "Current Page",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
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
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )
                        }
                    }
                }
            }

            // Reading Status Section
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Reading Status",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    BookStatusSection(book?.status, viewModel::updateBookStatus)
                }
            }

            // Notes Section (only for currently reading)
            if (book?.status == BookStatus.CURRENTLY_READING.toString()) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Notes",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Button(
                                onClick = { showAddNoteDialog = true },
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_add),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text("Add Note", fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }

                items(bookNotes) { note ->
                    NoteItem(note)
                }
            }

            // Statistics Section (only for currently reading)
            if (book?.status == BookStatus.CURRENTLY_READING.toString()) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "Statistics",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        // Statistics items
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatItem(
                                label = "Total Pages Read",
                                value = "${currentBookPage ?: 0}"
                            )
                            StatItem(
                                label = "Average Reading Pace",
                                value = "20 pages/day"
                            )
                        }
                    }
                }
            }

            // AI Insights Section
            item {
                AiInsightSection(
                    book = book,
                    notes = bookNotes,
                    modifier = Modifier.fillMaxWidth(),
                    viewModel = viewModel
                )
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
private fun StatItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun BookStatusSection(
    currentStatus: String?,
    onStatusUpdate: (BookStatus) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        BookStatus.values().forEach { status ->
            val isSelected = currentStatus == status.toString()
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                onClick = { onStatusUpdate(status) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { onStatusUpdate(status) }
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = status.toString().replace('_', ' '),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
private fun NoteItem(note: BookNotes) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_sticky_note),
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(24.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = note.description,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Page ${note.noteFromPage}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
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

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            "AI Insights",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    analyzeNotes = false
                    showAiDialog = true
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_auto_awesome),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("Analyze Book")
            }
            Button(
                onClick = {
                    analyzeNotes = true
                    showAiDialog = true
                },
                modifier = Modifier.weight(1f),
                enabled = notes.isNotEmpty(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_sticky_note),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("Analyze Notes")
            }
        }

        aiResponse?.let { response ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AccentGreen.copy(alpha = 0.08f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_auto_awesome),
                            contentDescription = null,
                            tint = AccentGreen
                        )
                        Text(
                            text = "AI Response",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AccentGreen
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = response,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }

    if (showAiDialog) {
        AlertDialog(
            onDismissRequest = { showAiDialog = false },
            title = { Text(if (analyzeNotes) "Analyze Notes" else "Analyze Book") },
            text = {
                Column {
                    Text(
                        if (analyzeNotes) "What would you like to know about your notes?"
                        else "What would you like to know about this book?"
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = question,
                        onValueChange = { question = it },
                        label = { Text("Ask anything...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
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
                    enabled = question.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp)
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