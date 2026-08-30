package com.example.data.seed

import com.example.data.database.HackPathDatabase
import com.example.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CurriculumSeeder {

    suspend fun seedDatabaseIfEmpty(database: HackPathDatabase) = withContext(Dispatchers.IO) {
        val count = database.curriculumDao().getTotalDaysCount()
        if (count >= 120) {
            return@withContext
        }

        // 1. Seed Initial User
        val existingUser = database.userDao().getUserSync()
        if (existingUser == null) {
            database.userDao().insertUser(
                UserEntity(
                    id = 1,
                    username = "GhostSec",
                    email = "operator@hackpath.io",
                    rank = "Script Kiddie",
                    level = 1,
                    xp = 0,
                    streak = 1,
                    completedDaysCount = 0,
                    currentDay = 1,
                    lastActiveDate = System.currentTimeMillis()
                )
            )
        }

        // 2. Seed All 120 Days
        val allDays = mutableListOf<DayEntity>()
        allDays.addAll(CurriculumPhase1.getDays())
        allDays.addAll(CurriculumPhase2.getDays())
        allDays.addAll(CurriculumPhase3.getDays())
        allDays.addAll(CurriculumPhase4.getDays())
        allDays.addAll(CurriculumPhase5.getDays())
        allDays.addAll(CurriculumPhase6.getDays())
        database.curriculumDao().insertDays(allDays)

        // 3. Seed Milestone Quizzes
        val quizzes = listOf(
            QuizQuestionEntity(
                id = 1,
                dayId = 1,
                question = "Which command is used to inspect file permissions and hidden files in Linux?",
                optionA = "dir /a",
                optionB = "ls -la",
                optionC = "show -all",
                optionD = "cat .hidden",
                correctIndex = 1,
                explanation = "'ls -la' lists all files in long format including hidden files starting with a dot (.)."
            ),
            QuizQuestionEntity(
                id = 2,
                dayId = 2,
                question = "In the Linux permissions 'rwxr-xr--', what permissions does the 'Others' category have?",
                optionA = "Read, Write, and Execute",
                optionB = "Read and Execute only",
                optionC = "Read only",
                optionD = "Write only",
                correctIndex = 2,
                explanation = "Permissions are grouped into Owner (rwx), Group (r-x), and Others (r--). 'r--' corresponds to read-only."
            ),
            QuizQuestionEntity(
                id = 3,
                dayId = 8,
                question = "What is the primary risk of an SUID binary owned by root?",
                optionA = "It cannot be read by standard users",
                optionB = "It always runs with the permissions of the root owner, which can lead to privilege escalation",
                optionC = "It automatically terminates background processes",
                optionD = "It encrypts user home directories",
                correctIndex = 1,
                explanation = "SUID (Set User ID) executes the binary with the permissions of the file owner (root), allowing potential privilege escalation if vulnerable."
            ),
            QuizQuestionEntity(
                id = 4,
                dayId = 24,
                question = "Why is an Nmap SYN Stealth Scan (-sS) considered stealthier than a full TCP Connect scan (-sT)?",
                optionA = "It encrypts the TCP packet headers with SSL",
                optionB = "It tears down the connection with RST before completing the 3-way handshake, avoiding socket connection logging",
                optionC = "It routes traffic through the Tor network automatically",
                optionD = "It uses UDP instead of TCP",
                correctIndex = 1,
                explanation = "SYN scans send a SYN, receive SYN-ACK, and immediately send RST without completing the TCP 3-way handshake, so standard application servers often don't log the connection."
            ),
            QuizQuestionEntity(
                id = 5,
                dayId = 45,
                question = "What is the root cause of SQL Injection vulnerabilities?",
                optionA = "Using an unencrypted HTTP connection",
                optionB = "Concatenating unvalidated user input directly into dynamic database SQL queries",
                optionC = "Running MySQL on the default port 3306",
                optionD = "Using integer primary keys",
                correctIndex = 1,
                explanation = "SQL Injection occurs when user-supplied input is directly concatenated into SQL query strings rather than bound as parameters."
            ),
            QuizQuestionEntity(
                id = 6,
                dayId = 64,
                question = "Which type of XSS is permanently saved on the server database and executed whenever any user views the infected page?",
                optionA = "Reflected XSS",
                optionB = "Stored (Persistent) XSS",
                optionC = "DOM-based XSS",
                optionD = "Blind CSS Injection",
                correctIndex = 1,
                explanation = "Stored XSS embeds malicious JavaScript permanently in the database (e.g., comments, forum posts) which executes for every visitor."
            ),
            QuizQuestionEntity(
                id = 7,
                dayId = 85,
                question = "What does the Kerberoasting attack target to crack Active Directory service account passwords offline?",
                optionA = "NTLMv1 challenge responses",
                optionB = "Kerberos TGS (Ticket Granting Service) service tickets encrypted with the service account's NTLM hash",
                optionC = "Cleartext passwords in the SAM registry hive",
                optionD = "SMB signing packets",
                correctIndex = 1,
                explanation = "Any domain user can request TGS tickets for accounts with an SPN. These tickets are encrypted with the account's password hash and can be brute-forced offline using Hashcat or John."
            ),
            QuizQuestionEntity(
                id = 8,
                dayId = 110,
                question = "Which vulnerability scoring metric is universally used by penetration testers to communicate risk objectively?",
                optionA = "CVE ID only",
                optionB = "CVSS v3.1 / v4.0 (Common Vulnerability Scoring System)",
                optionC = "NVD ranking count",
                optionD = "Shodan rank",
                correctIndex = 1,
                explanation = "CVSS provides an open framework for communicating the characteristics and severity of software vulnerabilities with scores from 0.0 to 10.0."
            )
        )
        database.quizDao().insertQuestions(quizzes)

        // 4. Seed Resource Library
        val resources = listOf(
            ResourceEntity(
                id = 1,
                title = "TryHackMe - Pre-Security & Complete Beginner Path",
                url = "https://tryhackme.com/path/outline/beginner",
                category = "Platform",
                description = "Hands-on browser-based Linux, Networking, and Web Security rooms designed for aspiring ethical hackers.",
                tag = "Beginner Labs"
            ),
            ResourceEntity(
                id = 2,
                title = "Hack The Box - Starting Point & Academy",
                url = "https://academy.hackthebox.com",
                category = "Platform",
                description = "Gamified penetration testing labs from tier-0 fundamental machines to active domain exploitation.",
                tag = "Pro Labs"
            ),
            ResourceEntity(
                id = 3,
                title = "PortSwigger Web Security Academy",
                url = "https://portswigger.net/web-security",
                category = "Web Pentest",
                description = "Free, interactive web vulnerability labs from the creators of Burp Suite covering SQLi, XSS, SSRF, and XXE.",
                tag = "OWASP Labs"
            ),
            ResourceEntity(
                id = 4,
                title = "GTFOBins - Unix Binary Privilege Escalation",
                url = "https://gtfobins.github.io",
                category = "PrivEsc",
                description = "Curated list of Unix binaries that can be exploited by an attacker to bypass local security restrictions and escalate privileges.",
                tag = "PrivEsc"
            ),
            ResourceEntity(
                id = 5,
                title = "LOLBAS - Living Off The Land Binaries (Windows)",
                url = "https://lolbas-project.github.io",
                category = "PrivEsc",
                description = "Living Off The Land binaries, scripts, and libraries for Windows privilege escalation, execution, and defense evasion.",
                tag = "Windows"
            ),
            ResourceEntity(
                id = 6,
                title = "PayloadsAllTheThings - Pentest Cheat Sheets",
                url = "https://github.com/swisskyrepo/PayloadsAllTheThings",
                category = "Cheatsheet",
                description = "Massive GitHub archive of payloads, command injection strings, reverse shells, and web exploit checklists.",
                tag = "Payloads"
            ),
            ResourceEntity(
                id = 7,
                title = "SecLists - Security Tester's Companion",
                url = "https://github.com/danielmiessler/SecLists",
                category = "Wordlists",
                description = "The gold standard collection of usernames, passwords, URLs, sensitive data patterns, and fuzzing payloads.",
                tag = "Wordlists"
            ),
            ResourceEntity(
                id = 8,
                title = "CyberChef - The Cyber Swiss Army Knife",
                url = "https://gchq.github.io/CyberChef",
                category = "Tool",
                description = "Browser-based cryptographic, encoding, and data extraction utility created by GCHQ.",
                tag = "Crypto Tool"
            ),
            ResourceEntity(
                id = 9,
                title = "MITRE ATT&CK Framework",
                url = "https://attack.mitre.org",
                category = "Cheatsheet",
                description = "Globally accessible knowledge base of adversary tactics, techniques, and procedures (TTPs) based on real-world observations.",
                tag = "Threat Intel"
            ),
            ResourceEntity(
                id = 10,
                title = "HackTricks - The Hacker's Encyclopedia",
                url = "https://book.hacktricks.xyz",
                category = "Cheatsheet",
                description = "Detailed pentesting knowledge base covering cloud security, Active Directory, network protocols, and reverse engineering.",
                tag = "Wiki"
            )
        )
        database.resourceDao().insertResources(resources)

        // 5. Seed Achievement Badges
        val badges = listOf(
            BadgeEntity(
                id = "badge_first_blood",
                title = "First Blood",
                description = "Complete your first mission day on HackPath.",
                iconName = "flag"
            ),
            BadgeEntity(
                id = "badge_phase1",
                title = "Terminal Whisperer",
                description = "Conquer Phase 1: Linux, Bash, Python & Networking foundations.",
                iconName = "terminal"
            ),
            BadgeEntity(
                id = "badge_phase2",
                title = "Recon Specialist",
                description = "Conquer Phase 2: OSINT, Nmap, Shodan & vulnerability discovery.",
                iconName = "radar"
            ),
            BadgeEntity(
                id = "badge_phase3",
                title = "Exploit Developer",
                description = "Conquer Phase 3: Metasploit, Reverse Shells, Buffer Overflows & PrivEsc.",
                iconName = "bug"
            ),
            BadgeEntity(
                id = "badge_phase4",
                title = "Web Breaker",
                description = "Conquer Phase 4: OWASP Top 10, Burp Suite, SQLi & API Security.",
                iconName = "language"
            ),
            BadgeEntity(
                id = "badge_phase5",
                title = "Domain Dominator",
                description = "Conquer Phase 5: Active Directory, Kerberoasting & BloodHound.",
                iconName = "domain"
            ),
            BadgeEntity(
                id = "badge_phase6",
                title = "Elite Red Teamer",
                description = "Complete all 120 days and graduate job-ready as a Penetration Tester.",
                iconName = "military_tech"
            ),
            BadgeEntity(
                id = "badge_streak_7",
                title = "Cyber Operator",
                description = "Maintain a 7-day mission streak without missing a target.",
                iconName = "fire"
            ),
            BadgeEntity(
                id = "badge_quiz_master",
                title = "Master of Theory",
                description = "Score 100% on 5 daily knowledge check quizzes.",
                iconName = "school"
            ),
            BadgeEntity(
                id = "badge_terminal_guru",
                title = "CLI Virtuoso",
                description = "Execute 20+ simulated commands in the interactive Terminal Sandbox.",
                iconName = "code"
            )
        )
        database.badgeDao().insertBadges(badges)

        // 6. Seed Skill Tree Nodes
        val skills = listOf(
            SkillNodeEntity(
                id = "skill_linux_foundations",
                name = "Linux Kernel & Shell Architecture",
                phaseIndex = 1,
                description = "Master Linux file hierarchies, permissions, user security, and system administration."
            ),
            SkillNodeEntity(
                id = "skill_networking_protocols",
                name = "TCP/IP & Network Protocols",
                phaseIndex = 1,
                description = "Packet analysis with Wireshark, subnetting, ARP/DNS mechanics, and firewall traversal."
            ),
            SkillNodeEntity(
                id = "skill_python_automation",
                name = "Python & Bash Scripting",
                phaseIndex = 1,
                description = "Automate port sweeps, HTTP requests, packet sniffers, and multi-threaded port fuzzers."
            ),
            SkillNodeEntity(
                id = "skill_recon_osint",
                name = "OSINT & Surface Mapping",
                phaseIndex = 2,
                description = "Subdomain enumeration, DNS bruteforcing, GitHub dorks, and Shodan IoT reconnaissance."
            ),
            SkillNodeEntity(
                id = "skill_network_scanning",
                name = "Nmap & Port Scanning",
                phaseIndex = 2,
                description = "Service version detection, stealth SYN sweeps, and Nmap Scripting Engine (NSE) automation."
            ),
            SkillNodeEntity(
                id = "skill_vulnerability_analysis",
                name = "Vulnerability Assessment & CVEs",
                phaseIndex = 2,
                description = "CVSS scoring, searchsploit exploit identification, and Nessus/Nuclei vulnerability scanners."
            ),
            SkillNodeEntity(
                id = "skill_reverse_shells",
                name = "Reverse & Bind Shells",
                phaseIndex = 3,
                description = "Netcat listeners, Python PTY shell stabilization, socat encrypted tunnels, and msfvenom."
            ),
            SkillNodeEntity(
                id = "skill_linux_privesc",
                name = "Linux Privilege Escalation",
                phaseIndex = 3,
                description = "SUID exploits, writable cron jobs, LinPEAS auditing, capability abuse, and kernel exploits."
            ),
            SkillNodeEntity(
                id = "skill_windows_privesc",
                name = "Windows Privilege Escalation",
                phaseIndex = 3,
                description = "Unquoted service paths, token impersonation (SeImpersonate), WinPEAS, and registry autoruns."
            ),
            SkillNodeEntity(
                id = "skill_owasp_top10",
                name = "OWASP Top 10 Web Vulnerabilities",
                phaseIndex = 4,
                description = "Cross-Site Scripting (XSS), SQLi, Command Injection, IDOR, SSRF, and CSRF exploitation."
            ),
            SkillNodeEntity(
                id = "skill_burp_suite",
                name = "Burp Suite & HTTP Interception",
                phaseIndex = 4,
                description = "Repeater, Intruder, Match/Replace rules, Macro sessions, and extension engineering."
            ),
            SkillNodeEntity(
                id = "skill_api_jwt_security",
                name = "REST API & JWT Security",
                phaseIndex = 4,
                description = "Broken Object Level Authorization (BOLA), JWT signature bypasses, and rate-limit evasion."
            ),
            SkillNodeEntity(
                id = "skill_kerberos_attacks",
                name = "Kerberos Exploitation",
                phaseIndex = 5,
                description = "Kerberoasting, AS-REP Roasting, Golden/Silver Tickets, and Unconstrained Delegation."
            ),
            SkillNodeEntity(
                id = "skill_bloodhound_ad",
                name = "BloodHound & Attack Paths",
                phaseIndex = 5,
                description = "Graph-based Active Directory analysis, GenericAll rights abuse, and shortest path to Domain Admin."
            ),
            SkillNodeEntity(
                id = "skill_pivoting_tunnels",
                name = "Pivoting & Network Tunnels",
                phaseIndex = 5,
                description = "Chisel, SSH dynamic port forwarding, Proxychains, and multi-tier subnet routing."
            ),
            SkillNodeEntity(
                id = "skill_report_writing",
                name = "Professional Pentest Reporting",
                phaseIndex = 6,
                description = "Executive summaries, technical risk matrices, proof-of-concept chains, and remediation guidance."
            )
        )
        database.skillDao().insertSkillNodes(skills)
    }
}
