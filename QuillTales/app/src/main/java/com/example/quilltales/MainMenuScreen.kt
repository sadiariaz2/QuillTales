package com.example.quilltales
//MAIN INTERFACE OF THE APP
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// ─── Data models for age‑based filtering of stories ─────────────────────────────────────────────────────────────
enum class AgeGroup { AGE_7_12, AGE_13_17, AGE_18_40 }

data class Story(
    val title: String,
    val content: String,
    val category: String,
    val ageGroup: AgeGroup
)

// ─── Main menu screen ──────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    userName: String,
    userAge: Int,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onStoryClick: (String, String) -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    val context = LocalContext.current

    // Determine age group
    val ageGroup = when (userAge) {
        in 7..12   -> AgeGroup.AGE_7_12
        in 13..17  -> AgeGroup.AGE_13_17
        else       -> AgeGroup.AGE_18_40
    }

    // Load stories
    val allStories = remember { demoStories }

    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery      by remember { mutableStateOf("") }

    var showAccountDialog        by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showThemeDialog          by remember { mutableStateOf(false) }
    var showSupportDialog        by remember { mutableStateOf(false) }

    // Filter stories based on category
    val filteredStories = remember(selectedCategory, ageGroup) {
        allStories.filter {
            (selectedCategory == "All" || it.category == selectedCategory)
                    && it.ageGroup == ageGroup      //this checks for the story filtered based on age and then story category
        }
    }
    //Search bar
    val displayedStories = remember(searchQuery, filteredStories) {
        if (searchQuery.isBlank()) filteredStories
        else filteredStories.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope       = rememberCoroutineScope()

    //The Drawer - The navigational bar-menu
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                Text("Menu", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(16.dp))
                Divider()
                DrawerItem("My Account")     { showAccountDialog = true }
                DrawerItem("Themes")         { showThemeDialog = true }
                CategoryFilterItem(selectedCategory) { selectedCategory = it }
                DrawerItem("Support")        { showSupportDialog = true }
                DrawerItem("Logout")         { onLogout() }
                DrawerItem("Delete Account") {
                    context.getSharedPreferences("quilltales_prefs", Context.MODE_PRIVATE) //SharedPreferences remembers key details  in its file
                        .edit().clear().apply()
                    onDeleteAccount()
                }
            }
        },
        content = {
            Scaffold(
                topBar = {             // Settings of Menu and its options
                    TopAppBar(
                        title = {
                            Text(
                                "QuillTales",
                                style     = MaterialTheme.typography.displayLarge,
                                modifier  = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                            }
                        }
                    )
                }
            ) { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    // ─── Background image  ─────────────────────────────
                    Image(
                        painter           = painterResource(R.drawable.bg),
                        contentDescription= null,
                        modifier          = Modifier.matchParentSize(),
                        contentScale      = ContentScale.Crop
                    )
                    // ─── end background change ──────────────────────────────────────────────

                    // ───  UI  ─────────────────────────────────────────────────────
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            "$userName's journey through tales of tales",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(Modifier.height(16.dp))

                        Surface(
                            color          = MaterialTheme.colorScheme.surface,
                            tonalElevation = 4.dp,
                            shape          = MaterialTheme.shapes.small,
                            modifier       = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value         = searchQuery,
                                onValueChange = { searchQuery = it },
                                label         = { Text("Search Stories") },
                                modifier      = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        LazyColumn {           //for scrollable items/list
                            items(displayedStories) { story ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onStoryClick(story.title, story.content) } //making story titles clickable to view content
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        story.title,
                                        style    = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Divider()       //adding an underline for each story
                                }
                            }
                        }
                    }
                }
            }
        }
    )
/* Menu options and their dialog boxes*/
    // ─── My Account dialog ─────────────────────────────────────────────────────────────
    if (showAccountDialog) {
        val prefs = context.getSharedPreferences("quilltales_prefs", Context.MODE_PRIVATE)
        val savedUsername = prefs.getString("reg_username", "") ?: ""
        AlertDialog(
            onDismissRequest = { showAccountDialog = false },
            containerColor   = MaterialTheme.colorScheme.surface,
            title            = { Text("My Account", style = MaterialTheme.typography.labelMedium) },
            text             = {
                Column {
                    Text("Username: $savedUsername", style = MaterialTheme.typography.labelMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Name: $userName", style = MaterialTheme.typography.labelMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Age: $userAge", style = MaterialTheme.typography.labelMedium)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = {
                        showAccountDialog = false
                        showChangePasswordDialog = true
                    }) {
                        Text("Change Password", style = MaterialTheme.typography.labelMedium)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAccountDialog = false }) {
                    Text("OK", style = MaterialTheme.typography.labelMedium)
                }
            }
        )
    }

    // ─── Change‑password dialog ─────────────────────────────────────────────────────
    var currentPass by remember { mutableStateOf("") }
    var newPass     by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var passError   by remember { mutableStateOf("") }

    if (showChangePasswordDialog) {
        val prefs = context.getSharedPreferences("quilltales_prefs", Context.MODE_PRIVATE)
        val savedPassword = prefs.getString("reg_password", "") ?: ""
        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            containerColor   = MaterialTheme.colorScheme.surface,
            title            = { Text("Change Password", style = MaterialTheme.typography.labelMedium) },
            text             = {
                Column {
                    OutlinedTextField(
                        value         = currentPass,
                        onValueChange = { currentPass = it },
                        label         = { Text("Current Password") },
                        modifier      = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value         = newPass,
                        onValueChange = { newPass = it },
                        label         = { Text("New Password") },
                        modifier      = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value         = confirmPass,
                        onValueChange = { confirmPass = it },
                        label         = { Text("Confirm Password") },
                        modifier      = Modifier.fillMaxWidth()
                    )
                    if (passError.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp))
                        Text(passError, color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    when {
                        currentPass.trim() != savedPassword ->
                            passError = "Current password is incorrect."
                        newPass.isBlank() ->
                            passError = "New password cannot be empty."
                        newPass != confirmPass ->
                            passError = "Passwords do not match."
                        else -> {
                            prefs.edit().putString("reg_password", newPass.trim()).apply()
                            Toast.makeText(context, "Password changed", Toast.LENGTH_SHORT).show()
                            showChangePasswordDialog = false
                            currentPass = ""
                            newPass = ""
                            confirmPass = ""
                            passError = ""
                        }
                    }
                }) {
                    Text("Confirm", style = MaterialTheme.typography.labelMedium)
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) {
                    Text("Cancel", style = MaterialTheme.typography.labelMedium)
                }
            }
        )
    }

    // ─── Theme changer dialog ─────────────────────────────────────────────────────
    if (showThemeDialog) {
        val options = listOf("Light", "Dark")
        var chosen by remember { mutableStateOf(if (isDarkTheme) "Dark" else "Light") }
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            containerColor   = MaterialTheme.colorScheme.surface,
            title            = { Text("Choose Theme", style = MaterialTheme.typography.labelMedium) },
            text             = {
                Column {
                    options.forEach { opt ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { chosen = opt }
                                .padding(vertical = 6.dp)
                        ) {
                            RadioButton(
                                selected = chosen == opt,
                                onClick  = { chosen = opt }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(opt, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onThemeChange(chosen == "Dark")
                    showThemeDialog = false
                }) {
                    Text("Apply", style = MaterialTheme.typography.labelMedium)
                }
            },
            dismissButton = {
                TextButton(onClick = { showThemeDialog = false }) {
                    Text("Cancel", style = MaterialTheme.typography.labelMedium)
                }
            }
        )
    }

    // ─── Support dialog ─────────────────────────────────────────────────────────
    if (showSupportDialog) {
        AlertDialog(
            onDismissRequest = { showSupportDialog = false },
            containerColor   = MaterialTheme.colorScheme.surface,
            title            = { Text("Support", style = MaterialTheme.typography.labelMedium) },
            text             = {
                Text(
                    "Need help? Email us at support@quilltales.com\n" +
                            "or call +971‑234‑1234.\n\n" +
                            "We usually reply within 24 hours.",
                    style = MaterialTheme.typography.labelMedium
                )
            },
            confirmButton = {
                TextButton(onClick = { showSupportDialog = false }) {
                    Text("OK", style = MaterialTheme.typography.labelMedium)
                }
            }
        )
    }
}


// ─── Helper composables ─────────────────────────────────────────────────────────────

// A single clickable item for use in a navigation drawer or menu
@Composable
fun DrawerItem(label: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.labelMedium)
    }
}
 //Category menu and its drop down to filter stories
@Composable
private fun CategoryFilterItem(
    selectedCategory: String,
    onCategoryChosen: (String) -> Unit
) {
    val categories = listOf("All", "Action", "Horror", "Fantasy", "Mystery")
    var expanded by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxWidth().padding(16.dp)) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Filter: $selectedCategory")
        }
        DropdownMenu(expanded, onDismissRequest = { expanded = false }) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategoryChosen(category)
                        expanded = false
                    }
                )
            }
        }
    }
}
