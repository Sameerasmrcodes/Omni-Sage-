package com.omnisage.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF101116)
private val Surface = Color(0xFF1B1D26)
private val Accent = Color(0xFF9B8CFF)
private val MainText = Color(0xFFF4F3FA)
private val Muted = Color(0xFF9A9BA8)

data class ChatMessage(val text: String, val fromUser: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { OmniSageApp() }
    }
}

@Composable
fun OmniSageApp() {
    var input by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(
            listOf(ChatMessage("Hello! I'm Omni Sage, your personal AI assistant. How can I help you today?", false))
        )
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Background,
            surface = Surface,
            primary = Accent,
            onBackground = MainText,
            onSurface = MainText
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Background)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            TopBar(onNewChat = {
                messages = listOf(ChatMessage("A fresh start. What would you like to work on?", false))
            })

            if (messages.size == 1) {
                WelcomePanel()
            }

            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages) { message -> MessageBubble(message) }
            }

            Composer(
                value = input,
                onValueChange = { input = it },
                onSend = {
                    val text = input.trim()
                    if (text.isNotEmpty()) {
                        messages = messages + ChatMessage(text, true)
                        input = ""
                        messages = messages + ChatMessage(
                            "I'm currently in preview mode. AI connection will be added in the next build. For now, the chat interface is ready.",
                            false
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun TopBar(onNewChat: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(42.dp).background(Accent.copy(alpha = 0.18f), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("✦", color = Accent, fontSize = 25.sp)
        }
        Spacer(Modifier.width(11.dp))
        Column(Modifier.weight(1f)) {
            Text("OMNI SAGE", color = MainText, fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
            Text("PERSONAL AI ASSISTANT", color = Muted, fontSize = 9.sp, letterSpacing = 1.3.sp)
        }
        IconButton(onClick = onNewChat) {
            Icon(Icons.Default.Add, contentDescription = "New chat", tint = MainText)
        }
    }
    HorizontalDivider(color = Color.White.copy(alpha = 0.07f))
}

@Composable
private fun WelcomePanel() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(66.dp).background(Accent.copy(alpha = 0.14f), CircleShape),
            contentAlignment = Alignment.Center
        ) { Text("✦", color = Accent, fontSize = 34.sp) }
        Spacer(Modifier.height(14.dp))
        Text("What’s on your mind?", color = MainText, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(6.dp))
        Text("Ask, plan, learn or create. Omni Sage is here to help.", color = Muted, fontSize = 13.sp)
    }
}

@Composable
private fun MessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.fromUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier.widthIn(max = 300.dp)
                .background(
                    if (message.fromUser) Accent.copy(alpha = 0.20f) else Surface,
                    RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 15.dp, vertical = 12.dp)
        ) {
            if (!message.fromUser) {
                Text("OMNI SAGE", color = Accent, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(Modifier.height(5.dp))
            }
            Text(message.text, color = MainText, fontSize = 14.sp, lineHeight = 21.sp)
        }
    }
}

@Composable
private fun Composer(value: String, onValueChange: (String) -> Unit, onSend: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp)
            .background(Surface, RoundedCornerShape(25.dp)).padding(start = 18.dp, end = 7.dp, top = 7.dp, bottom = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f).padding(vertical = 10.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = MainText, fontSize = 14.sp),
            singleLine = false,
            maxLines = 4,
            decorationBox = { inner ->
                if (value.isEmpty()) Text("Message Omni Sage...", color = Muted, fontSize = 14.sp)
                inner()
            }
        )
        IconButton(
            onClick = onSend,
            modifier = Modifier.size(42.dp).background(Accent, CircleShape)
        ) {
            Icon(Icons.Default.Send, contentDescription = "Send", tint = Color(0xFF101116), modifier = Modifier.size(19.dp))
        }
    }
    Text(
        "Preview build • AI responses will be connected next",
        color = Muted.copy(alpha = 0.7f),
        fontSize = 10.sp,
        modifier = Modifier.fillMaxWidth().padding(bottom = 9.dp),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}
