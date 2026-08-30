package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.HackPathViewModel
import kotlinx.coroutines.launch

data class TerminalLog(
    val command: String,
    val output: String,
    val isError: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalSandboxScreen(
    viewModel: HackPathViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var commandInput by remember { mutableStateOf("") }
    var terminalHistory by remember {
        mutableStateOf(
            listOf(
                TerminalLog(
                    command = "system_init",
                    output = "HackPath Terminal Sandbox v2.4.0\n[+] Type 'help' for command reference or tap quick command chips below."
                )
            )
        )
    }

    fun executeCommand(rawCmd: String) {
        val cmd = rawCmd.trim()
        if (cmd.isBlank()) return

        if (cmd.equals("clear", ignoreCase = true)) {
            terminalHistory = emptyList()
            commandInput = ""
            return
        }

        val output = when {
            cmd.equals("help", ignoreCase = true) -> """
                AVAILABLE COMMANDS:
                • whoami                   - Print current user identity
                • id                       - Print user and group IDs
                • pwd                      - Print working directory
                • ls -la                   - List directory contents
                • sudo -l                  - Audit user sudo privileges
                • nmap <target>            - Simulate network port scan
                • searchsploit <query>     - Search Exploit-DB archive
                • sqlmap <url>             - Simulate SQL Injection scan
                • linpeas                  - Run Linux Privilege Escalation check
                • john                     - Run password cracking test
                • cat /etc/passwd          - View system user list
                • clear                    - Clear terminal output
            """.trimIndent()

            cmd.equals("whoami", ignoreCase = true) -> "ghostsec"
            cmd.equals("id", ignoreCase = true) -> "uid=1000(ghostsec) gid=1000(ghostsec) groups=1000(ghostsec),27(sudo),110(lxd)"
            cmd.equals("pwd", ignoreCase = true) -> "/home/ghostsec/hackpath/missions"

            cmd.startsWith("ls", ignoreCase = true) -> """
                total 48
                drwxr-xr-x 2 ghostsec ghostsec 4096 Aug 30 08:30 .
                drwxr-xr-x 6 ghostsec ghostsec 4096 Aug 30 08:20 ..
                -rwxr-xr-x 1 ghostsec ghostsec 8420 Aug 30 08:25 linpeas.sh
                -rw-r--r-- 1 ghostsec ghostsec 1024 Aug 30 08:24 notes.txt
                -rw-r--r-- 1 ghostsec ghostsec  512 Aug 30 08:22 targets.txt
                -rwsr-xr-x 1 root     root     4096 Aug 30 08:15 /usr/bin/find [SUID]
            """.trimIndent()

            cmd.equals("sudo -l", ignoreCase = true) -> """
                Matching Defaults entries for ghostsec on hackpath-box:
                    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin

                User ghostsec may run the following commands on hackpath-box:
                    (ALL : ALL) NOPASSWD: /usr/bin/find
                    (root) /usr/bin/python3 /opt/maintenance.py
            """.trimIndent()

            cmd.startsWith("nmap", ignoreCase = true) -> """
                Starting Nmap 7.94 ( https://nmap.org ) at 2026-08-30 08:30 UTC
                Nmap scan report for 10.10.10.10
                Host is up (0.021s latency).
                Not shown: 996 closed tcp ports
                PORT    STATE SERVICE     VERSION
                22/tcp  open  ssh         OpenSSH 8.2p1 Ubuntu 4ubuntu0.5
                80/tcp  open  http        Apache httpd 2.4.49 ((Unix))
                |_http-server-header: Apache/2.4.49
                |_http-title: Target Enterprise Portal
                445/tcp open  netbios-ssn Samba smbd 4.6.2
                3306/tcp open mysql       MySQL 5.7.33
                Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel
            """.trimIndent()

            cmd.startsWith("searchsploit", ignoreCase = true) -> """
                ------------------------------------------------- ---------------------------------
                 Exploit Title                                   |  Path
                ------------------------------------------------- ---------------------------------
                Apache 2.4.49 - Path Traversal & Remote Code Ex  | multiple/webapps/50383.py
                Apache 2.4.49/2.4.50 - Remote Code Execution (c  | multiple/webapps/50406.sh
                Apache + PHP < 5.3.12 / < 5.4.2 - cgi-bin RCE    | php/remote/29290.c
                ------------------------------------------------- ---------------------------------
                Shellcodes: No Results
            """.trimIndent()

            cmd.startsWith("sqlmap", ignoreCase = true) -> """
                [08:30:15] [INFO] testing connection to the target URL
                [08:30:16] [INFO] checking if the target is protected by some kind of WAF/IPS
                [08:30:17] [INFO] testing 'AND boolean-based blind - WHERE or HAVING clause'
                [08:30:18] [INFO] GET parameter 'id' is vulnerable!
                ---
                Parameter: id (GET)
                    Type: boolean-based blind
                    Title: AND boolean-based blind - WHERE or HAVING clause
                    Payload: id=1 AND 7821=7821
                    Type: UNION query
                    Title: Generic UNION query (NULL) - 3 columns
                ---
                [08:30:19] [INFO] the back-end DBMS is MySQL 5.7.33
                available databases [2]:
                [*] information_schema
                [*] hackpath_db
            """.trimIndent()

            cmd.equals("linpeas", ignoreCase = true) || cmd.equals("./linpeas.sh", ignoreCase = true) -> """
                ╔══════════╣ SUID - Check unusual SUID binaries
                -rwsr-xr-x 1 root root 194K Feb  7  2024 /usr/bin/find ---> GTFOBins: find . -exec /bin/sh -p \; (VULNERABLE!)
                -rwsr-xr-x 1 root root  44K Feb 21  2024 /usr/bin/newgrp
                -rwsr-xr-x 1 root root  59K Feb 21  2024 /usr/bin/passwd

                ╔══════════╣ Writable Cron Jobs
                /etc/crontab: */5 * * * * root /opt/backup.sh (Writable by user ghostsec!)
            """.trimIndent()

            cmd.startsWith("john", ignoreCase = true) -> """
                Loaded 1 password hash (sha512crypt, crypt(3) $6$ [SHA512 256/256 AVX2 4x])
                Cost 1 (iteration count) is 5000 for all loaded hashes
                Press 'q' or Ctrl-C to abort, almost any other key for status
                password123      (root)
                1g 0:00:00:01 DONE (2026-08-30 08:30) 0.8196g/s 1445p/s 1445c/s 1445C/s secret..password123
                Use the "--show" option to display all of the cracked passwords reliably
            """.trimIndent()

            cmd.equals("cat /etc/passwd", ignoreCase = true) -> """
                root:x:0:0:root:/root:/bin/bash
                daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin
                bin:x:2:2:bin:/bin:/usr/sbin/nologin
                sys:x:3:3:sys:/dev:/usr/sbin/nologin
                ghostsec:x:1000:1000:GhostSec User,,,:/home/ghostsec:/bin/bash
                mysql:x:114:120:MySQL Server,,,:/nonexistent:/bin/false
            """.trimIndent()

            else -> "bash: command not found: $cmd. Type 'help' for available commands."
        }

        terminalHistory = terminalHistory + TerminalLog(command = cmd, output = output)
        commandInput = ""
        coroutineScope.launch {
            listState.animateScrollToItem(terminalHistory.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TerminalBlack)
    ) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.Terminal, contentDescription = null, tint = NeonMint)
                    Text(
                        text = "TERMINAL SANDBOX",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = NeonMint
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                }
            },
            actions = {
                IconButton(onClick = { terminalHistory = emptyList() }) {
                    Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = "Clear", tint = TextMuted)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
        )

        // Command Suggestions Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val suggestions = listOf("help", "whoami", "nmap 10.10.10.10", "searchsploit apache", "sqlmap -u", "linpeas", "sudo -l", "ls -la", "clear")
            items(suggestions) { s ->
                SuggestionChip(
                    onClick = { executeCommand(s) },
                    label = { Text(s, fontFamily = FontFamily.Monospace, fontSize = 11.sp) },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = BackgroundDark,
                        labelColor = NeonMint
                    ),
                    border = SuggestionChipDefaults.suggestionChipBorder(
                        borderColor = BorderDark,
                        enabled = true
                    )
                )
            }
        }

        // Terminal Output Screen
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(terminalHistory) { log ->
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "[root@hackpath ~]#",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            ),
                            color = ThreatOrange
                        )
                        Text(
                            text = log.command,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            ),
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = log.output,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily.Monospace,
                            lineHeight = 18.sp
                        ),
                        color = if (log.isError) ThreatOrange else NeonMint
                    )
                }
            }
        }

        // Command Prompt Input
        Surface(
            color = SurfaceDark,
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "$",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    ),
                    color = NeonMint
                )

                OutlinedTextField(
                    value = commandInput,
                    onValueChange = { commandInput = it },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("terminal_command_input"),
                    placeholder = {
                        Text(
                            text = "type command (e.g. nmap, linpeas, whoami)...",
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                            color = TextMuted
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        color = TextPrimary
                    ),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonMint,
                        unfocusedBorderColor = BorderDark,
                        cursorColor = NeonMint,
                        focusedContainerColor = BackgroundDark,
                        unfocusedContainerColor = BackgroundDark
                    ),
                    shape = RoundedCornerShape(6.dp)
                )

                IconButton(
                    onClick = { executeCommand(commandInput) },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(NeonMint)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Execute",
                        tint = BackgroundDark,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
