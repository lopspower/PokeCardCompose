package com.mikhaellopez.designsystem

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoFixNormal
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikhaellopez.ui.base.*
import com.mikhaellopez.ui.theme.BaseTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DesignSystem() {
    Scaffold(
        topBar = {
            Toolbar(
                title = "Design System",
                menuActions = {
                    MenuIcon(
                        imageVector = Icons.Default.AutoFixNormal,
                        contentDescription = "Switch Dark/Light Mode",
                    ) {
                        if (AppCompatDelegate.MODE_NIGHT_YES == AppCompatDelegate.getDefaultNightMode()) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(paddingValues)
            ) {
                //region INIT SNACKBAR & BOTTOM SHEET
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                val modalBottomSheetState = rememberModalBottomSheetState(
                    ModalBottomSheetValue.Hidden,
                    confirmStateChange = {
                        it != ModalBottomSheetValue.HalfExpanded
                    }
                )
                //endregion

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(all = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    //region COLORS
                    Row {
                        CardTheme(
                            backgroundColor = MaterialTheme.colors.primary,
                            text = "Primary",
                            modifier = Modifier.weight(1f, true)
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        CardTheme(
                            backgroundColor = MaterialTheme.colors.primaryVariant,
                            text = "PrimaryVariant",
                            modifier = Modifier.weight(1f, true)
                        )
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Row {
                        CardTheme(
                            backgroundColor = MaterialTheme.colors.secondary,
                            text = "Secondary",
                            modifier = Modifier.weight(1f, true)
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        CardTheme(
                            backgroundColor = MaterialTheme.colors.secondaryVariant,
                            text = "SecondaryVariant",
                            modifier = Modifier.weight(1f, true)
                        )
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Row {
                        CardTheme(
                            backgroundColor = MaterialTheme.colors.background,
                            text = "Background",
                            modifier = Modifier.weight(1f, true)
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        CardTheme(
                            backgroundColor = MaterialTheme.colors.error,
                            text = "Error",
                            modifier = Modifier.weight(1f, true)
                        )
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Row {
                        CardTheme(
                            backgroundColor = MaterialTheme.colors.surface,
                            text = "Default Surface",
                            modifier = Modifier.weight(1f, true)
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    //endregion

                    //region BUTTONS
                    Button(
                        onClick = { /* ... */ },
                        contentPadding = ButtonDefaults.ContentPadding
                    ) {
                        Text("Regular".uppercase())
                    }

                    Button(
                        onClick = { /* ... */ },
                        contentPadding = ButtonDefaults.ContentPadding
                    ) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Regular with icon".uppercase())
                    }

                    TextButton(
                        onClick = { /* ... */ },
                        contentPadding = ButtonDefaults.ContentPadding
                    ) {
                        Text("Text button".uppercase())
                    }

                    OutlinedButton(
                        onClick = { /* ... */ },
                        contentPadding = ButtonDefaults.ContentPadding
                    ) {
                        Text("Outlined".uppercase())
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    //endregion

                    //region PROGRESS
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.size(12.dp))
                    LinearProgressIndicator()
                    Spacer(modifier = Modifier.size(16.dp))
                    //endregion

                    //region FAB
                    Row {
                        FloatingActionButton(onClick = { /* ... */ }) {
                            Text("FAB")
                        }

                        Spacer(modifier = Modifier.size(20.dp))

                        FloatingActionButton(onClick = { /* ... */ }) {
                            Icon(Icons.Filled.Add, "FAB")
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    //endregion

                    //region BUTTON SNACKBAR
                    Button(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Snackbar message",
                                    "ACTION",
                                    SnackbarDuration.Short
                                )
                            }
                        },
                        contentPadding = ButtonDefaults.ContentPadding
                    ) {
                        Text("Show Snackbar".uppercase())
                    }
                    //endregion

                    //region BUTTON BOTTOM SHEET
                    Button(
                        onClick = {
                            scope.launch {
                                if (modalBottomSheetState.isVisible) {
                                    modalBottomSheetState.hide()
                                } else {
                                    modalBottomSheetState.show()
                                }
                            }
                        },
                        contentPadding = ButtonDefaults.ContentPadding
                    ) {
                        Text("Show BottomSheet".uppercase())
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    //endregion

                    //region SWITCH
                    Row {
                        Switch(
                            checked = true,
                            onCheckedChange = { /* ... */ }
                        )

                        Spacer(modifier = Modifier.size(20.dp))

                        Switch(
                            checked = false,
                            onCheckedChange = { /* ... */ }
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    //endregion

                    //region TEXT FIELD
                    var text1 by remember { mutableStateOf(TextFieldValue("")) }
                    TextField(
                        value = text1,
                        label = { Text(text = "Text Field") },
                        onValueChange = { newText -> text1 = newText },
                        placeholder = { Text(text = "Placeholder") },
                    )
                    Spacer(modifier = Modifier.size(12.dp))

                    var text2 by remember { mutableStateOf(TextFieldValue("")) }
                    TextField(
                        value = text2,
                        label = { Text(text = "Text Field With Error") },
                        isError = true,
                        onValueChange = { newText -> text2 = newText },
                        placeholder = { Text(text = "Placeholder") },
                    )
                    Spacer(modifier = Modifier.size(12.dp))

                    var textOutlinedTextField by remember { mutableStateOf(TextFieldValue("")) }
                    OutlinedTextField(
                        value = textOutlinedTextField,
                        label = { Text(text = "Outlined Text Field") },
                        placeholder = { Text(text = "Placeholder") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "emailIcon"
                            )
                        },
                        onValueChange = { textOutlinedTextField = it }
                    )
                    Spacer(modifier = Modifier.size(12.dp))

                    var textOutlinedTextField2 by remember { mutableStateOf(TextFieldValue("")) }
                    OutlinedTextField(
                        value = textOutlinedTextField2,
                        label = { Text(text = "Outlined Text Field") },
                        isError = true,
                        placeholder = { Text(text = "Placeholder") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "emailIcon"
                            )
                        },
                        onValueChange = { textOutlinedTextField2 = it }
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    //endregion

                    //region TYPOGRAPHY
                    Text(
                        text = "Headline 1",
                        style = MaterialTheme.typography.h1
                    )
                    Text(
                        text = "Headline 2",
                        style = MaterialTheme.typography.h2
                    )
                    Text(
                        text = "Headline 3",
                        style = MaterialTheme.typography.h3
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Headline 4",
                        style = MaterialTheme.typography.h4
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Headline 5",
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Headline 6",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Subtitle 1",
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Subtitle 2",
                        style = MaterialTheme.typography.subtitle2
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Body 1",
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Body 2",
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Button".uppercase(),
                        style = MaterialTheme.typography.button
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Caption",
                        style = MaterialTheme.typography.caption
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Overline".uppercase(),
                        style = MaterialTheme.typography.overline
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    //endregion
                }

                //region SNACKBAR
                Box(modifier = Modifier.fillMaxSize()) {
                    SnackbarHost(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        hostState = snackbarHostState
                    ) { snackbarData ->
                        val actionComposable: (@Composable () -> Unit)? =
                            if (snackbarData.actionLabel != null) {
                                @Composable {
                                    TextButton(
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = SnackbarDefaults.primaryActionColor
                                        ),
                                        onClick = { snackbarData.performAction() },
                                        content = { Text(snackbarData.actionLabel!!) }
                                    )
                                }
                            } else {
                                null
                            }
                        Snackbar(
                            modifier = Modifier.padding(12.dp),
                            content = { Text(snackbarData.message) },
                            action = actionComposable
                        )
                    }
                }
                //endregion

                //region BOTTOM SHEET
                Box(modifier = Modifier.fillMaxSize()) {
                    ModalBottomSheetLayout(
                        sheetBackgroundColor = Color.Transparent,
                        sheetState = modalBottomSheetState,
                        sheetContent = {
                            BottomSheet {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                ) {
                                    Text(
                                        text = "Bottom Sheet Content",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        },
                        content = {}
                    )
                }
                //endregion
            }
        }
    )
}

@Composable
private fun CardTheme(
    backgroundColor: Color,
    text: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        backgroundColor = backgroundColor,
        elevation = 4.dp
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            maxLines = 1
        )
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun Preview() {
    BaseTheme {
        DesignSystem()
    }
}
