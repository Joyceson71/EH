package com.example.data.seed

import com.example.data.model.DayEntity

object CurriculumPhase2 {
    fun getDays(): List<DayEntity> = (21..40).map { dayNum ->
        when (dayNum) {
            21 -> DayEntity(
                id = 21, phase = 2, dayNumber = 21,
                title = "Passive OSINT Principles & Attack Surface Discovery",
                subtitle = "Collect intelligence without touching target infrastructure directly.",
                concept = """
                    Open Source Intelligence (OSINT) gathers publicly available data about target organizations:
                    - Passive Reconnaissance: Interacting exclusively with third-party aggregators (Search engines, Certificate Transparency logs, DNS databases, Whois). Zero packets are sent to target servers, generating zero firewall/SIEM alerts.
                    - Primary Intelligence Objectives:
                      1. Subdomain infrastructure (staging, dev, admin portals).
                      2. Leaked employee email addresses and credential dumps.
                      3. Tech stack indicators (cloud provider, DNS provider, email security gateway).
                      4. Associated Autonomous System Numbers (ASNs) and netblocks.
                """.trimIndent(),
                keyTakeaways = "• Passive recon produces zero logs on target intrusion detection systems.\n• Certificate Transparency logs record every SSL certificate issued publicly.\n• ASN lookups reveal all IP ranges owned by an enterprise.",
                commandsCode = "# Lookup IP ownership and ASN for an organization\nwhois -h whois.radb.net -- '-i origin AS15169'",
                videoTitle = "OSINT for Ethical Hackers: Passive Reconnaissance Masterclass",
                videoUrl = "https://www.youtube.com/watch?v=qwA6MmbeGNo",
                videoChannel = "TCM Security",
                videoDuration = "32m",
                readTitle = "OSINT Framework & Tool Repository",
                readUrl = "https://osintframework.com/",
                readSource = "OSINT Framework",
                labTitle = "Passive Reconnaissance Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/passiverecon",
                labPlatform = "TryHackMe",
                labDescription = "Gather corporate asset maps using passive DNS and WHOIS databases.",
                xpReward = 50
            )
            22 -> DayEntity(
                id = 22, phase = 2, dayNumber = 22,
                title = "Google Dorking & Advanced Search Operators",
                subtitle = "Uncover exposed credentials, SQL dumps, internal panels, and sensitive PDFs.",
                concept = """
                    Google Dorking (Google Hacking) utilizes search operators to query index caches:
                    Essential Search Operators:
                    - `site:example.com`: Restricts queries to target domain.
                    - `filetype:pdf` / `filetype:env` / `filetype:sql` / `filetype:log`: Finds specific file extensions.
                    - `inurl:admin` / `inurl:wp-content` / `inurl:api/v1`: Matches keywords in URL strings.
                    - `intitle:"index of /"`: Finds directory indexing enabled on web servers.
                    - `intext:"BEGIN RSA PRIVATE KEY"`: Discovers leaked private keys.
                    - `cache:target.com`: Views cached versions of pages even after removal.
                    Google Hacking Database (GHDB) maintained by Exploit-DB catalogs thousands of high-impact dorks.
                """.trimIndent(),
                keyTakeaways = "• `intitle:\"index of /\"` discovers exposed directory listings with downloadable backups.\n• Combine operators (e.g. `site:target.com ext:env DB_PASSWORD`) for high-precision queries.\n• GHDB contains pre-compiled dorks categorized by vulnerability type.",
                commandsCode = "# Example Dork combinations:\nsite:target.com inurl:gitlab OR inurl:jenkins\nsite:target.com filetype:sql OR filetype:env \"password\"",
                videoTitle = "Google Dorking: How Hackers Find Everything",
                videoUrl = "https://www.youtube.com/watch?v=F0f5rY9UvXg",
                videoChannel = "NetworkChuck",
                videoDuration = "19m",
                readTitle = "Google Hacking Database (GHDB) Official Index",
                readUrl = "https://www.exploit-db.com/google-hacking-database",
                readSource = "Exploit-DB",
                labTitle = "Google Dorking Room - TryHackMe",
                labUrl = "https://tryhackme.com/room/googledorking",
                labPlatform = "TryHackMe",
                labDescription = "Use advanced search queries to locate hidden employee portals and test files.",
                xpReward = 50
            )
            23 -> DayEntity(
                id = 23, phase = 2, dayNumber = 23,
                title = "WHOIS, DNS Recon & Certificate Transparency Logs",
                subtitle = "Query crt.sh, dig, and DNSdumpster to map all organization subdomains.",
                concept = """
                    Certificate Transparency (CT) is an open framework monitoring SSL/TLS certificate issuance. Whenever a CA issues a certificate for `api.dev.target.com`, it enters a tamper-evident public append-only log.
                    Querying crt.sh provides an instant inventory of historical and current subdomains.
                    DNS Recon Tools:
                    - `dig`: Queries specific DNS servers directly.
                    - `sublist3r` & `subfinder`: Aggregates passive records across dozens of threat intelligence sources (VirusTotal, SecurityTrails, Censys, AlienVault).
                    - `assetfinder`: High-speed subdomain finder written in Go.
                """.trimIndent(),
                keyTakeaways = "• Certificate Transparency (crt.sh) reveals dev and staging subdomains often forgotten by IT.\n• DNS AXFR (Zone Transfer) vulnerability leaks the entire DNS zone file if misconfigured.\n• DNS bruteforcing with `gobuster dns` identifies hidden internal subdomains.",
                commandsCode = "# Query Certificate Transparency logs via curl and jq\ncurl -s 'https://crt.sh/?q=%25.target.com&output=json' | jq -r '.[].name_value' | sort -u\n\n# Attempt DNS Zone Transfer on target nameserver\ndig axfr @ns1.target.com target.com",
                videoTitle = "Subdomain Enumeration Masterclass: crt.sh to Subfinder",
                videoUrl = "https://www.youtube.com/watch?v=MIydmK-kG4g",
                videoChannel = "NahamSec",
                videoDuration = "26m",
                readTitle = "crt.sh Certificate Search & CT Log Specification",
                readUrl = "https://crt.sh/",
                readSource = "Sectigo",
                labTitle = "DNS Reconnaissance & Enumeration Lab",
                labUrl = "https://tryhackme.com/room/dnsindetail",
                labPlatform = "TryHackMe",
                labDescription = "Perform DNS zone transfers and query passive certificate registries.",
                xpReward = 50
            )
            24 -> DayEntity(
                id = 24, phase = 2, dayNumber = 24,
                title = "Shodan, Censys & IoT Search Engines",
                subtitle = "Find exposed industrial control systems, open databases, cameras, and unpatched servers.",
                concept = """
                    Shodan is the search engine for Internet-connected devices, port scanning the entire IPv4 address space continuously.
                    Key Shodan Search Filters:
                    - `org:"Target Corp"`: Filters hosts owned by a specific organization.
                    - `port:21,22,3389,445,9200,27017`: Finds exposed services (e.g., Elasticsearch 9200, MongoDB 27017).
                    - `product:"Apache httpd" version:"2.4.49"`: Identifies servers vulnerable to specific CVEs (e.g. CVE-2021-41773).
                    - `has_screenshot:true`: Discovers open RDP and VNC desktops with authentication disabled.
                    - `country:"US" title:"Dashboard"`: Finds exposed administrative panels.
                """.trimIndent(),
                keyTakeaways = "• Shodan indexes banners, not webpage HTML content.\n• Searching for default credentials in Shodan banners frequently yields immediate initial access.\n• Censys and FOFA provide complementary internet-wide scan data.",
                commandsCode = "# Search Shodan from CLI using API key\nshodan search --fields ip_str,port,org 'org:\"Target Corporation\" 200 OK'\n\n# Get host summary for an IP\nshodan host 198.51.100.1",
                videoTitle = "Shodan: The Search Engine for Hackers",
                videoUrl = "https://www.youtube.com/watch?v=Fj2FkGg5h4A",
                videoChannel = "David Bombal",
                videoDuration = "23m",
                readTitle = "Shodan Query Syntax & Search Reference Guide",
                readUrl = "https://help.shodan.io/the-basics/search-query-syntax",
                readSource = "Shodan.io",
                labTitle = "Shodan.io Recon Room - TryHackMe",
                labUrl = "https://tryhackme.com/room/shodan",
                labPlatform = "TryHackMe",
                labDescription = "Use Shodan queries to locate vulnerable IoT devices and open webcams.",
                xpReward = 50
            )
            25 -> DayEntity(
                id = 25, phase = 2, dayNumber = 25,
                title = "TheHarvester, Email Harvesting & Social Engineering OSINT",
                subtitle = "Collect employee usernames, LinkedIn profiles, and leaked credentials.",
                concept = """
                    Social Engineering and spear phishing rely on comprehensive employee reconnaissance:
                    - `theHarvester`: Gathers emails, names, subdomains, IPs, and employee profiles across Google, Bing, LinkedIn, Hunter.io, and PGP key servers.
                    - Password Leak Databases (HaveIBeenPwned, DeHashed): Check if target employee emails were compromised in historic corporate breaches.
                    - Username Enumeration (`sherlock` / `whatsmyname`): Discovers developer accounts across 300+ social platforms.
                    - Email Format Convention: Identifying whether target uses `first.last@company.com` or `flast@company.com` allows generating full target userlists for password spraying.
                """.trimIndent(),
                keyTakeaways = "• Identifying corporate email patterns enables construction of valid username wordlists.\n• Breached passwords from 3rd party leaks are frequently reused across corporate VPNs.\n• Sherlock checks for identical usernames across hundreds of social networks.",
                commandsCode = "# Run theHarvester against target domain\ntheHarvester -d target.com -b google,linkedin,bing -l 200\n\n# Search for username across 300 platforms with sherlock\nsherlock johndoe_sec",
                videoTitle = "TheHarvester & OSINT for Gathering Email Lists",
                videoUrl = "https://www.youtube.com/watch?v=Zf1r1k2R8oQ",
                videoChannel = "TCM Security",
                videoDuration = "18m",
                readTitle = "TheHarvester Official Tool Documentation",
                readUrl = "https://github.com/laramies/theHarvester",
                readSource = "GitHub",
                labTitle = "OSINT & People Enumeration Lab",
                labUrl = "https://tryhackme.com/room/ohsint",
                labPlatform = "TryHackMe",
                labDescription = "Track an online persona across forums, image metadata, and social media.",
                xpReward = 50
            )
            26 -> DayEntity(
                id = 26, phase = 2, dayNumber = 26,
                title = "Metadata Analysis with ExifTool & Document Intelligence",
                subtitle = "Extract software versions, author usernames, camera GPS coordinates from PDFs and images.",
                concept = """
                    Public corporate documents (PDFs, DOCX, XLSX) published on corporate websites contain rich EXIF and XMP metadata:
                    - Author / Creator tags: Reveals internal employee usernames and operating systems.
                    - Producer / Application tags: Discovers exact desktop software versions (e.g. Microsoft Word 2016 MSO 16.0.4266.1001).
                    - Internal Path names: Discovers corporate file server shares (`\\\\fileserver\\dept\\user\\doc.pdf`).
                    - GPS Coordinates in images: Pinpoints exact physical office locations.
                    - Tools: `exiftool` (CLI metadata extractor) and `foca` (automated document harvester).
                """.trimIndent(),
                keyTakeaways = "• PDF metadata frequently leaks internal Active Directory usernames in the Author field.\n• Downloading corporate public whitepapers via Google Dorking feeds metadata scrapers.\n• `exiftool -all= file.jpg` strips all metadata for defensive hygiene.",
                commandsCode = "# Extract all metadata tags from a downloaded PDF\nexiftool -all -G1 corporate_annual_report.pdf\n\n# Strip all metadata from an image\nexiftool -all= sensitive_photo.jpg",
                videoTitle = "Exiftool: Extracting Secrets from Files & Photos",
                videoUrl = "https://www.youtube.com/watch?v=eY0Vj0v3jBo",
                videoChannel = "John Hammond",
                videoDuration = "16m",
                readTitle = "ExifTool Documentation by Phil Harvey",
                readUrl = "https://exiftool.org/",
                readSource = "ExifTool",
                labTitle = "Digital Forensics & Metadata Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/exiftool",
                labPlatform = "TryHackMe",
                labDescription = "Extract hidden GPS coordinates and author credentials from evidence files.",
                xpReward = 50
            )
            27 -> DayEntity(
                id = 27, phase = 2, dayNumber = 27,
                title = "GitHub, GitLab & Secret Leak Reconnaissance",
                subtitle = "Hunt for hardcoded API keys, database credentials, AWS tokens, and commit history secrets.",
                concept = """
                    Developers routinely commit proprietary code containing sensitive secrets to public repositories:
                    - High-Value Target Secrets: AWS Access Keys (`AKIA...`), Slack Webhook URLs, Stripe API keys, Private RSA keys, Database connection strings (`postgres://user:pass@host/db`).
                    - Git History Mining: Secrets committed and later deleted still persist permanently in git commit history!
                    - Tools:
                      * `trufflehog`: Scans git history for high-entropy strings and valid credentials.
                      * `gitleaks`: Fast regex-based secret scanner for repositories.
                      * `gitrob` / `git-hound`: Searches GitHub for sensitive keywords associated with enterprise organizations.
                """.trimIndent(),
                keyTakeaways = "• Deleting a secret in a new commit does NOT remove it from git history.\n• Trufflehog automatically tests discovered API keys against live endpoints to verify validity.\n• Search GitHub with `org:TargetCompany \"password\"` or `filename:.env`.",
                commandsCode = "# Scan a GitHub repository for leaked API keys with trufflehog\ntrufflehog git https://github.com/target-org/vulnerable-repo.git\n\n# Inspect full git log commits with diffs\ngit log -p -S 'password'",
                videoTitle = "How Hackers Find Passwords Leaked on GitHub",
                videoUrl = "https://www.youtube.com/watch?v=vVj4uNfF9mE",
                videoChannel = "David Bombal",
                videoDuration = "21m",
                readTitle = "TruffleHog Open Source Secret Detection Guide",
                readUrl = "https://github.com/trufflesecurity/trufflehog",
                readSource = "Truffle Security",
                labTitle = "Git & Secret Leak Analysis Room",
                labUrl = "https://tryhackme.com/room/githappens",
                labPlatform = "TryHackMe",
                labDescription = "Recover historical commits in a compromised repository to find API tokens.",
                xpReward = 50
            )
            28 -> DayEntity(
                id = 28, phase = 2, dayNumber = 28,
                title = "Active Reconnaissance Fundamentals & Port Scanning Theory",
                subtitle = "Transition from passive to active scanning; understand firewall and IDS detection.",
                concept = """
                    Active Reconnaissance sends crafted probes directly to target hosts:
                    - Network Interaction: Direct TCP connections, ICMP echoes, and UDP datagrams are generated.
                    - Legal Scope Warning: Active scanning without authorization is illegal. Always verify Rules of Engagement.
                    - Scanning Objectives:
                      1. Host Discovery (Is the machine alive?).
                      2. Open Port Identification (Which TCP/UDP sockets accept connections?).
                      3. Service Banner Identification (What daemon software and version is running?).
                      4. Operating System Fingerprinting (TCP window size, IP TTL values, TCP options ordering).
                """.trimIndent(),
                keyTakeaways = "• Active recon leaves IP records in target router and firewall logs.\n• TTL values can hint at OS: Linux ~64, Windows ~128, Cisco/Network ~255.\n• Comprehensive recon minimizes missed attack vectors.",
                commandsCode = "# Ping sweep target subnet to discover active hosts\nnmap -sn 192.168.1.0/24\n\n# Test ICMP echo reply\nping -c 2 10.10.10.10",
                videoTitle = "Active vs Passive Reconnaissance in Pentesting",
                videoUrl = "https://www.youtube.com/watch?v=m7HwJ7JjT6M",
                videoChannel = "TCM Security",
                videoDuration = "17m",
                readTitle = "Nmap Network Scanning Book (Official Fyodor Guide)",
                readUrl = "https://nmap.org/book/man.html",
                readSource = "Nmap.org",
                labTitle = "Network Services 101 - TryHackMe",
                labUrl = "https://tryhackme.com/room/networkservices",
                labPlatform = "TryHackMe",
                labDescription = "Discover listening services and investigate unauthenticated SMB shares.",
                xpReward = 50
            )
            29 -> DayEntity(
                id = 29, phase = 2, dayNumber = 29,
                title = "Nmap Host Discovery & Ping Sweep Techniques",
                subtitle = "Bypass ICMP blocks using TCP SYN ping (`-PS`), TCP ACK ping (`-PA`), and ARP scans.",
                concept = """
                    Firewalls frequently drop ICMP echo requests (`ping`), giving a false impression that a host is offline.
                    Nmap Host Discovery Flags:
                    - `-sn`: Disable port scan; host discovery only (ping sweep).
                    - `-Pn`: Skip host discovery entirely; treat all targets as alive (essential when target drops ping).
                    - `-PE`: Standard ICMP Echo Request.
                    - `-PS22,80,443`: TCP SYN Ping to ports 22, 80, 443 (probes if host acknowledges TCP packets).
                    - `-PA80`: TCP ACK Ping (sends unprompted ACK; alive hosts reply with RST).
                    - `-PR`: ARP Ping (fastest and most reliable on local Ethernet networks).
                """.trimIndent(),
                keyTakeaways = "• Use `-Pn` when scanning protected cloud servers that block ICMP.\n• ARP scans (`-PR`) cannot be blocked on local subnets because ARP is required for L2 communication.\n• TCP SYN ping generates a response even if the target port is closed (RST packet returned).",
                commandsCode = "# Perform fast TCP SYN ping discovery across network without port scan\nsudo nmap -sn -PS22,80,443,3389 192.168.1.0/24\n\n# Scan target host treating it as online\nsudo nmap -Pn 10.10.10.50",
                videoTitle = "Nmap Host Discovery: How to Find Hidden Devices",
                videoUrl = "https://www.youtube.com/watch?v=34dZc0c7E_E",
                videoChannel = "NetworkChuck",
                videoDuration = "22m",
                readTitle = "Nmap Reference Guide: Host Discovery",
                readUrl = "https://nmap.org/book/man-host-discovery.html",
                readSource = "Nmap.org",
                labTitle = "Nmap Host Discovery Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/nmap01",
                labPlatform = "TryHackMe",
                labDescription = "Identify live hosts across different subnets using advanced ping techniques.",
                xpReward = 50
            )
            30 -> DayEntity(
                id = 30, phase = 2, dayNumber = 30,
                title = "Nmap Port Scanning Mastery (SYN `-sS`, Connect `-sT`, UDP `-sU`)",
                subtitle = "Master raw socket mechanics, half-open scans, RST packet responses, and timing templates.",
                concept = """
                    Nmap port scan types interact with the TCP stack in distinct ways:
                    - SYN Stealth Scan (`-sS` - Default for root):
                      Sends SYN packet. If port is open, target replies SYN-ACK. Nmap immediately sends RST (aborts connection before completion). Fast, rarely logged by application-level logs.
                    - TCP Connect Scan (`-sT` - Default for non-root):
                      Completes full 3-way handshake via OS `connect()` syscall. Slower, leaves connection logs.
                    - UDP Scan (`-sU`):
                      Sends empty UDP packets. If ICMP Port Unreachable received -> Closed. If response received -> Open. If no response -> `open|filtered`.
                    - Timing Templates: `-T0` (Paranoid/IDS evasion), `-T3` (Normal), `-T4` (Aggressive, recommended for CTFs), `-T5` (Insane).
                """.trimIndent(),
                keyTakeaways = "• `-sS` requires root/sudo privileges because it builds raw TCP packets.\n• `-p-` scans all 65,535 TCP ports instead of just the top 1,000.\n• UDP scans take significantly longer due to ICMP rate limiting in operating systems.",
                commandsCode = "# Fast SYN scan of all 65535 ports with timing template 4\nsudo nmap -sS -p- -T4 --min-rate 1000 10.10.10.10\n\n# Scan top 20 UDP ports\nsudo nmap -sU --top-ports 20 10.10.10.10",
                videoTitle = "Nmap Tutorial: Master Port Scanning with Hands-on Labs",
                videoUrl = "https://www.youtube.com/watch?v=4t4kBkMsDbY",
                videoChannel = "David Bombal",
                videoDuration = "31m",
                readTitle = "Nmap Reference: Port Scanning Techniques",
                readUrl = "https://nmap.org/book/man-port-scanning-techniques.html",
                readSource = "Nmap.org",
                labTitle = "Nmap Port Scanning Room - TryHackMe",
                labUrl = "https://tryhackme.com/room/furthernmap",
                labPlatform = "TryHackMe",
                labDescription = "Execute stealth SYN scans, UDP scans, and full 65k port enumerations.",
                xpReward = 50
            )
            31 -> DayEntity(
                id = 31, phase = 2, dayNumber = 31,
                title = "Nmap Service Version Detection (`-sV`) & OS Fingerprinting (`-O`)",
                subtitle = "Probe banner signatures, match version strings against CPE databases, and inspect TCP window probes.",
                concept = """
                    Knowing an open port is not enough; you must identify the exact software daemon and version:
                    - Service Version Detection (`-sV`):
                      Nmap queries open ports with specialized probes and matches responses against `nmap-service-probes` database of thousands of signatures.
                      `--version-intensity 0-9`: Higher values execute deeper probe chains (default 7).
                    - OS Fingerprinting (`-O`):
                      Sends 16 TCP, UDP, and ICMP probes with subtle RFC quirks. Analyzes TCP Options order, IP ID sequence generation, and initial window size against `nmap-os-db`.
                    - Aggressive Scan (`-A`): Enables OS detection (`-O`), version detection (`-sV`), script scanning (`-sC`), and traceroute.
                """.trimIndent(),
                keyTakeaways = "• Exact version strings (e.g. ProFTPd 1.3.3c) allow direct matching in Exploit-DB.\n• OS detection accuracy improves when at least one open and one closed TCP port are found.\n• `-oA output_file` exports results in normal, XML, and greppable formats simultaneously.",
                commandsCode = "# Comprehensive service & OS enumeration with full output export\nsudo nmap -sC -sV -O -p22,80,445 -oA nmap_scan 10.10.10.10",
                videoTitle = "Service Enumeration & Version Detection in Nmap",
                videoUrl = "https://www.youtube.com/watch?v=N_uP7oP9_a0",
                videoChannel = "John Hammond",
                videoDuration = "21m",
                readTitle = "Nmap Service and Application Version Detection",
                readUrl = "https://nmap.org/book/vscan.html",
                readSource = "Nmap.org",
                labTitle = "Nmap Deep Dive & Version Detection",
                labUrl = "https://tryhackme.com/room/furthernmap",
                labPlatform = "TryHackMe",
                labDescription = "Detect outdated Apache and SMB versions and generate XML reports.",
                xpReward = 50
            )
            32 -> DayEntity(
                id = 32, phase = 2, dayNumber = 32,
                title = "Nmap Scripting Engine (NSE) & Vulnerability Scanning",
                subtitle = "Leverage 600+ Lua scripts for automated vulnerability checks (`vuln`), auth bypass, and discovery.",
                concept = """
                    The Nmap Scripting Engine (NSE) empowers users to write and execute custom Lua scripts:
                    NSE Script Categories:
                    - `default` (`-sC`): Safe, high-speed discovery scripts.
                    - `vuln`: Scans for known high-severity CVE vulnerabilities (e.g. MS17-010 EternalBlue, Heartbleed).
                    - `auth`: Tests default credentials and authentication bypass on services.
                    - `safe`: Scripts unlikely to crash fragile target services.
                    - `discovery`: Queries active directories, SNMP trees, SMB shares, and database schemas.
                    Location of NSE scripts: `/usr/share/nmap/scripts/`
                """.trimIndent(),
                keyTakeaways = "• `nmap --script vuln <target>` runs an automated vulnerability scan for critical CVEs.\n• Pass arguments to scripts using `--script-args`.\n• Inspect script source code directly in `/usr/share/nmap/scripts/` to understand check mechanics.",
                commandsCode = "# Scan for SMB vulnerabilities (EternalBlue, SMBGhost)\nsudo nmap -p 445 --script smb-vuln* 10.10.10.10\n\n# Enumerate HTTP title, methods, and robots.txt\nnmap -p 80 --script http-title,http-methods,http-robots.txt target.com",
                videoTitle = "Nmap Scripting Engine (NSE) Tutorial for Ethical Hackers",
                videoUrl = "https://www.youtube.com/watch?v=zJg9v_Wc33A",
                videoChannel = "NetworkChuck",
                videoDuration = "23m",
                readTitle = "Nmap Scripting Engine (NSE) Official Documentation",
                readUrl = "https://nmap.org/book/nse.html",
                readSource = "Nmap.org",
                labTitle = "NSE Scripts in Practice - TryHackMe",
                labUrl = "https://tryhackme.com/room/furthernmap",
                labPlatform = "TryHackMe",
                labDescription = "Execute SMB and HTTP vulnerability scripts against vulnerable targets.",
                xpReward = 50
            )
            33 -> DayEntity(
                id = 33, phase = 2, dayNumber = 33,
                title = "Masscan: High-Speed Internet-Scale Scanning",
                subtitle = "Transmit asynchronous SYN packets at up to 10 million packets per second using custom network drivers.",
                concept = """
                    Masscan is the fastest Internet port scanner, capable of scanning the entire Internet in under 5 minutes:
                    - Architecture: Uses custom asynchronous raw packet generator (PF_RING / custom TCP stack), completely bypassing the Linux kernel network stack.
                    - Asynchronous Model: Transmits SYN packets in a randomized stream without waiting for responses; a separate listener thread records returned SYN-ACK packets.
                    - Pentest Best Practice: Use Masscan for lightning-fast port discovery across large enterprise ranges (/16 or /8), then pipe discovered open ports into Nmap (`-sV -sC`) for precision banner inspection.
                """.trimIndent(),
                keyTakeaways = "• Masscan only discovers open ports; it does NOT perform service versioning or NSE scripting.\n• Always set `--rate` responsibly to avoid overwhelming network routers.\n• Masscan uses randomized IP hashing to distribute traffic evenly across target subnets.",
                commandsCode = "# Scan a /24 subnet for port 80 and 443 at 5,000 packets/sec\nsudo masscan 192.168.1.0/24 -p80,443 --rate=5000\n\n# Scan top 100 ports across entire enterprise range\nsudo masscan 10.0.0.0/16 --top-ports 100 --rate 10000 -oL open_ports.txt",
                videoTitle = "Masscan: Scan the Internet in 5 Minutes",
                videoUrl = "https://www.youtube.com/watch?v=Vz8B94gN_2U",
                videoChannel = "David Bombal",
                videoDuration = "19m",
                readTitle = "Masscan Official GitHub Repository & Documentation",
                readUrl = "https://github.com/robertdavidgraham/masscan",
                readSource = "GitHub",
                labTitle = "High Speed Port Scanning Lab",
                labUrl = "https://tryhackme.com/room/furthernmap",
                labPlatform = "TryHackMe",
                labDescription = "Configure Masscan rate limits and parse output into targeted Nmap commands.",
                xpReward = 50
            )
            34 -> DayEntity(
                id = 34, phase = 2, dayNumber = 34,
                title = "SMB & NetBIOS Enumeration (enum4linux, smbclient, crackmapexec)",
                subtitle = "Extract Windows domain users, password policies, null session shares, and RPC pipes.",
                concept = """
                    Server Message Block (SMB, ports 139 & 445) is a core Windows networking protocol for file sharing and IPC:
                    - Null Sessions (Anonymous Access): Connecting without credentials (`username=""`, `password=""`) to query domain users, groups, and shares.
                    - Tools:
                      * `smbclient`: FTP-like interface to connect to SMB shares (`smbclient -L //10.10.10.10 -N`).
                      * `enum4linux` / `enum4linux-ng`: Wrapper for samba tools automating user, share, and RID cycling enumeration.
                      * `crackmapexec smb` / `netexec`: Swiss-army knife for pentesting Active Directory SMB services.
                """.trimIndent(),
                keyTakeaways = "• Null sessions (`-N`) often grant read access to sensitive backup and config shares.\n• RID cycling (`lookupsid`) brute-forces Windows user accounts sequentially by security identifier.\n• SMB signing disabled allows NTLM relay attacks.",
                commandsCode = "# List SMB shares anonymously\nsmbclient -L //10.10.10.10 -N\n\n# Full automated SMB enumeration using enum4linux\nenum4linux -a 10.10.10.10",
                videoTitle = "SMB Enumeration & Exploitation for Pentesters",
                videoUrl = "https://www.youtube.com/watch?v=3g8uK0_x088",
                videoChannel = "TCM Security",
                videoDuration = "27m",
                readTitle = "HackTricks: Pentesting SMB (Port 445/139)",
                readUrl = "https://book.hacktricks.xyz/network-services-pentesting/pentesting-smb",
                readSource = "HackTricks",
                labTitle = "Network Services: SMB Enumeration - TryHackMe",
                labUrl = "https://tryhackme.com/room/networkservices",
                labPlatform = "TryHackMe",
                labDescription = "Connect to misconfigured SMB shares and retrieve internal configuration keys.",
                xpReward = 50
            )
            35 -> DayEntity(
                id = 35, phase = 2, dayNumber = 35,
                title = "SNMP, NFS & RPC Service Enumeration",
                subtitle = "Walk SNMP MIB trees with default community strings (`public`, `private`) and mount NFS shares.",
                concept = """
                    Enterprise network services frequently leak internal architecture due to default configurations:
                    - SNMP (Simple Network Management Protocol, UDP 161/162):
                      Monitors network devices. If default community strings (`public` or `private`) are accepted, attackers can walk Management Information Base (MIB) trees to dump full running processes, network interfaces, routing tables, and user accounts. Tool: `snmpwalk`, `onesixtyone`.
                    - NFS (Network File System, port 2049):
                      Unrestricted exports allow mounting remote disks (`showmount -e`). If `no_root_squash` is enabled, root on client translates to root on server!
                    - RPC (Remote Procedure Call, port 111 / `rpcbind`):
                      `rpcclient` allows executing anonymous RPC commands to query Active Directory domain state.
                """.trimIndent(),
                keyTakeaways = "• SNMP community strings act as cleartext passwords; `public` is the most common default.\n• `showmount -e <target>` lists all export directories shareable over NFS.\n• `no_root_squash` on NFS mounts enables instant root privilege escalation.",
                commandsCode = "# Walk SNMP tree using public community string\nsnmpwalk -v2c -c public 10.10.10.10\n\n# List and mount remote NFS exports\nshowmount -e 10.10.10.10\nsudo mount -t nfs 10.10.10.10:/shared /mnt/target",
                videoTitle = "Enumerating SNMP and NFS in Penetration Tests",
                videoUrl = "https://www.youtube.com/watch?v=0kFqG1G98k8",
                videoChannel = "IppSec",
                videoDuration = "24m",
                readTitle = "HackTricks: Pentesting SNMP & NFS Services",
                readUrl = "https://book.hacktricks.xyz/network-services-pentesting/pentesting-snmp",
                readSource = "HackTricks",
                labTitle = "Network Services 2: NFS & SNMP - TryHackMe",
                labUrl = "https://tryhackme.com/room/networkservices2",
                labPlatform = "TryHackMe",
                labDescription = "Mount an unsecured NFS drive, create a SUID binary, and elevate privileges.",
                xpReward = 50
            )
            36 -> DayEntity(
                id = 36, phase = 2, dayNumber = 36,
                title = "Web Content Discovery & Directory Busting (Gobuster & Feroxbuster)",
                subtitle = "Fuzz hidden admin paths, `.git` repositories, backup files (`.bak`, `.zip`), and API routes.",
                concept = """
                    Web servers host hidden endpoints and administrative dashboards not linked from the main homepage:
                    - Directory Busting Methodology: Sends HTTP requests sequentially for thousands of common directory/file names from wordlists (SecLists) and filters responses by HTTP status code (200, 301, 403).
                    - High-Value Extensions to Fuzz: `.php`, `.bak`, `.old`, `.sql`, `.zip`, `.json`, `.txt`, `.config`, `.env`.
                    - Modern Fuzzing Tools:
                      * `gobuster dir`: Ultra-fast Go directory brute-forcer.
                      * `feroxbuster`: Recursive, high-speed Rust-based web discovery tool.
                      * `ffuf`: Fast and flexible web fuzzer supporting parameter, header, and path fuzzing.
                """.trimIndent(),
                keyTakeaways = "• Always test for file extensions (`-x php,txt,bak,zip`) in addition to bare directory names.\n• SecLists (`directory-list-2.3-medium.txt`) is the standard wordlist for web content discovery.\n• Look out for exposed `.git` folders (`/.git/HEAD`) which allow full source code extraction.",
                commandsCode = "# Run Gobuster directory enumeration with extensions\ngobuster dir -u http://10.10.10.10 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -x php,txt,bak,html -t 40\n\n# Recursive directory discovery with feroxbuster\nferoxbuster -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt",
                videoTitle = "Directory Busting with Gobuster, Feroxbuster, & FFUF",
                videoUrl = "https://www.youtube.com/watch?v=iKPsw_oUeR0",
                videoChannel = "InsiderPhD",
                videoDuration = "22m",
                readTitle = "FFUF (Fuzz Faster U Fool) Complete Guide",
                readUrl = "https://github.com/ffuf/ffuf",
                readSource = "FFUF",
                labTitle = "Content Discovery Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/contentdiscovery",
                labPlatform = "TryHackMe",
                labDescription = "Fuzz hidden directories and discover an exposed secret database backup.",
                xpReward = 50
            )
            37 -> DayEntity(
                id = 37, phase = 2, dayNumber = 37,
                title = "Virtual Host (VHost) & Subdomain Fuzzing",
                subtitle = "Bypass reverse proxies by manipulating the HTTP `Host:` header to discover internal virtual hosts.",
                concept = """
                    A single web server with one IP address often hosts dozens of different websites using Virtual Hosts (VHosts). The web server (Nginx/Apache) inspects the HTTP `Host:` header to route requests:
                    - VHost Fuzzing Mechanics:
                      Sends HTTP requests to the target IP address while substituting the `Host:` header with entries from a subdomain wordlist:
                      `GET / HTTP/1.1`
                      `Host: admin.target.com`
                    - Difference between Subdomain Enum & VHost Fuzzing:
                      DNS subdomain enum finds records published in public DNS; VHost fuzzing identifies virtual hosts configured directly on the web server that lack public DNS records!
                """.trimIndent(),
                keyTakeaways = "• Filter false positives in ffuf using `-fs` (filter by response size) or `-fc` (filter code).\n• Internal portals like `dev.target.com` or `vpn.target.com` are often configured as VHosts.\n• Custom staging domains often lack IP whitelisting.",
                commandsCode = "# Fuzz virtual hosts using ffuf and filter out default page size (e.g. 1540 bytes)\nffuf -u http://10.10.10.10 -H 'Host: FUZZ.target.com' -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -fs 1540",
                videoTitle = "Virtual Host Fuzzing with FFUF: Step-by-Step",
                videoUrl = "https://www.youtube.com/watch?v=aN3KAf4y5_g",
                videoChannel = "NahamSec",
                videoDuration = "18m",
                readTitle = "PayloadsAllTheThings: Subdomain & VHost Fuzzing",
                readUrl = "https://github.com/swisskyrepo/PayloadsAllTheThings",
                readSource = "PayloadsAllTheThings",
                labTitle = "Subdomain & VHost Fuzzing Practice",
                labUrl = "https://tryhackme.com/room/subdomainenumeration",
                labPlatform = "TryHackMe",
                labDescription = "Find an unlisted development vhost to uncover an administrative interface.",
                xpReward = 50
            )
            38 -> DayEntity(
                id = 38, phase = 2, dayNumber = 38,
                title = "Web Vulnerability Scanning with Nikto & WhatWeb",
                subtitle = "Fingerprint tech stacks, CMS engines (WordPress, Drupal), and dangerous HTTP methods.",
                concept = """
                    Automated web server fingerprinting and vulnerability scanning:
                    - `WhatWeb`: Recognizes web technologies, CMS types, blogging platforms, JavaScript libraries, server headers, and analytics tags.
                    - `Nikto`: Open-source web server scanner that tests for 6,700+ dangerous files/programs, outdated server software, insecure HTTP headers (missing CSP, X-Frame-Options), and default files.
                    - `wpscan`: Dedicated security scanner for WordPress installations, checking vulnerable plugins, themes, and brute-forcing user logins via XML-RPC.
                """.trimIndent(),
                keyTakeaways = "• WhatWeb reveals exact jQuery, React, WordPress, and web server versions in seconds.\n• Nikto flags dangerous HTTP methods like PUT (can allow arbitrary file upload).\n• WPScan can enumerate WordPress usernames automatically via `?author=1`.",
                commandsCode = "# Fingerprint complete technology stack of a web app\nwhatweb -a 3 http://target.com\n\n# Run comprehensive Nikto web server scan\nnikto -h http://target.com -output nikto_report.txt",
                videoTitle = "Web App Fingerprinting with Nikto, WhatWeb & WPScan",
                videoUrl = "https://www.youtube.com/watch?v=0bT5g1k6D3E",
                videoChannel = "TCM Security",
                videoDuration = "20m",
                readTitle = "Nikto Web Scanner Documentation & Options",
                readUrl = "https://cirt.net/Nikto2",
                readSource = "CIRT.net",
                labTitle = "Vulnerability Scanning with Nikto - TryHackMe",
                labUrl = "https://tryhackme.com/room/rpnikto",
                labPlatform = "TryHackMe",
                labDescription = "Scan an outdated Apache server and identify exploitable CGI scripts.",
                xpReward = 50
            )
            39 -> DayEntity(
                id = 39, phase = 2, dayNumber = 39,
                title = "Reconnaissance Data Aggregation & Target Attack Surface Mapping",
                subtitle = "Combine subdomain lists, open ports, and technologies into a structured attack strategy.",
                concept = """
                    Raw scan output must be transformed into actionable attack vectors:
                    Attack Surface Mapping Workflow:
                    1. Normalize IP & Subdomain Inventory: Aggregate outputs from subfinder, crt.sh, and amass into a deduplicated list.
                    2. Web Probe: Run `httpx` to verify which subdomains respond on HTTP/HTTPS and capture status codes and titles.
                    3. Port Correlation: Merge Nmap/Masscan results to identify unique entry points (SSH, FTP, SMB, RDP, MySQL).
                    4. Prioritization Matrix:
                       * Priority 1: Unauthenticated services & default credential opportunities (SMB null session, Jenkins dashboard).
                       * Priority 2: Outdated daemons with published CVEs (Apache 2.4.49, vsftpd 2.3.4).
                       * Priority 3: Custom web apps for deep manual inspection (SQLi, IDOR, XSS).
                """.trimIndent(),
                keyTakeaways = "• `httpx -silent -status-code -title` rapidly filters alive web assets from thousands of subdomains.\n• Organizing recon notes systematically prevents overlooking simple vulnerabilities.\n• High-priority targets have outdated versions with known remote code execution (RCE) CVEs.",
                commandsCode = "# Complete one-line recon pipeline\ncat subdomains.txt | httpx -silent -title -tech-detect -status-code -o live_targets.txt",
                videoTitle = "Building an Automated Bug Bounty Recon Pipeline",
                videoUrl = "https://www.youtube.com/watch?v=y_K1gP9_G9s",
                videoChannel = "NahamSec",
                videoDuration = "29m",
                readTitle = "ProjectDiscovery Tool Suite Documentation",
                readUrl = "https://docs.projectdiscovery.io/",
                readSource = "ProjectDiscovery",
                labTitle = "Reconnaissance & Asset Discovery Lab",
                labUrl = "https://tryhackme.com/room/passiverecon",
                labPlatform = "TryHackMe",
                labDescription = "Build a complete target asset map and prioritize vulnerability entry points.",
                xpReward = 50
            )
            40 -> DayEntity(
                id = 40, phase = 2, dayNumber = 40,
                title = "Phase 2 Reconnaissance Capstone: Full Target Enumeration Assessment",
                subtitle = "Execute passive OSINT, Nmap port scanning, SMB querying, and directory busting against a mock enterprise.",
                concept = """
                    Congratulations on completing Phase 2: Reconnaissance & OSINT!
                    You have mastered:
                    1. Passive OSINT, Certificate Transparency logs, and ASN intelligence.
                    2. Google Dorking and sensitive file discovery.
                    3. Shodan & IoT search engines for exposed enterprise assets.
                    4. Metadata extraction with ExifTool and git repository secret hunting.
                    5. Nmap host discovery, stealth SYN scanning, versioning, and NSE scripting.
                    6. High-speed network scanning with Masscan.
                    7. Network service enumeration (SMB, SNMP, NFS).
                    8. Web content fuzzing, VHost fuzzing, and technology fingerprinting.

                    You are now ready to begin Phase 3: Exploitation Basics!
                """.trimIndent(),
                keyTakeaways = "• Thorough reconnaissance accounts for 80% of a successful penetration test.\n• Phase 3 shifts focus to weaponizing discoveries: CVEs, Metasploit, SQLi, XSS, and reverse shells.",
                commandsCode = "# Run final verification scan against assessment target\nsudo nmap -sC -sV -p- -T4 -oA final_recon 10.10.10.100",
                videoTitle = "Recon to Root: Full Pentest Methodology Overview",
                videoUrl = "https://www.youtube.com/watch?v=WqmsS2f_sN0",
                videoChannel = "IppSec",
                videoDuration = "34m",
                readTitle = "PTES: Penetration Testing Execution Standard - Intelligence Gathering",
                readUrl = "http://www.pentest-standard.org/index.php/Intelligence_Gathering",
                readSource = "PTES",
                labTitle = "Phase 2 Capstone Machine - TryHackMe",
                labUrl = "https://tryhackme.com/room/kenobi",
                labPlatform = "TryHackMe",
                labDescription = "Enumerate Samba shares, mount NFS, exploit ProFTPd, and capture root flag.",
                xpReward = 100
            )
            else -> DayEntity(
                id = dayNum, phase = 2, dayNumber = dayNum,
                title = "Reconnaissance Deep Dive $dayNum", subtitle = "Advanced OSINT & scanning technique.",
                concept = "Detailed enumeration and vulnerability scanning mechanics.",
                keyTakeaways = "• Verify scope.\n• Correlate findings.", commandsCode = "nmap -sV -sC target.com",
                videoTitle = "Reconnaissance Video", videoUrl = "https://youtube.com", videoChannel = "TCM Security", videoDuration = "20m",
                readTitle = "Recon Reference", readUrl = "https://hacktricks.xyz", readSource = "HackTricks",
                labTitle = "Recon Lab $dayNum", labUrl = "https://tryhackme.com", labPlatform = "TryHackMe", labDescription = "Complete recon tasks.", xpReward = 50
            )
        }
    }
}
