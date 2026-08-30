package com.example.data.seed

import com.example.data.model.DayEntity

object CurriculumPhase6 {
    fun getDays(): List<DayEntity> = (101..120).map { dayNum ->
        when (dayNum) {
            101 -> DayEntity(
                id = 101, phase = 6, dayNumber = 101,
                title = "Certification Roadmap: OSCP, PNPT, eJPT & CPTS",
                subtitle = "Compare certification exam formats, proctoring requirements, methodologies, and preparation strategies.",
                concept = """
                    Navigating cybersecurity certifications and industry standards:
                    - CompTIA Security+ & CySA+: Baseline foundational knowledge for HR screening.
                    - eJPT (eLearnSecurity Junior Penetration Tester by INE): 100% practical 48-hour network and web penetration test. Ideal starting credential.
                    - PNPT (Practical Network Penetration Tester by TCM Security): 5-day hands-on external/internal AD assessment followed by live 15-minute executive debrief.
                    - OSCP (Offensive Security Certified Professional - PEN-200): The global industry benchmark. 24-hour hands-on exam (AD set + 3 standalone machines) + 24-hour technical report writing.
                    - CPTS (Certified Penetration Testing Specialist by Hack The Box): 10-day comprehensive enterprise assessment covering advanced pivoting and AD.
                """.trimIndent(),
                keyTakeaways = "• Hands-on practical certifications carry far higher weight in penetration testing hiring than multiple-choice exams.\n• Time management and systematic note-taking are the top factors in passing 24-hour exams.",
                commandsCode = "# Organize your pentest notes folder structure\nmkdir -p pentest_project/{recon,exploits,privesc,creds,loot,screenshots}",
                videoTitle = "The Ultimate Ethical Hacking Certification Guide",
                videoUrl = "https://www.youtube.com/watch?v=kYv9N8F7Q8A",
                videoChannel = "TCM Security",
                videoDuration = "28m",
                readTitle = "Offensive Security: OSCP Exam Guide & Requirements",
                readUrl = "https://help.offsec.com/hc/en-us/articles/360040165632-OSCP-Exam-Guide",
                readSource = "OffSec",
                labTitle = "OSCP Preparation Room - TryHackMe",
                labUrl = "https://tryhackme.com/room/bufferoverflowprep",
                labPlatform = "TryHackMe",
                labDescription = "Review exam methodology and establish standardized reporting notes.",
                xpReward = 50
            )
            102 -> DayEntity(
                id = 102, phase = 6, dayNumber = 102,
                title = "Professional Penetration Testing Report Writing",
                subtitle = "Structure executive summaries, technical finding breakdowns, reproducible steps, and CVSS v3.1 scoring.",
                concept = """
                    The report is the only tangible deliverable the client pays for. A brilliant exploit is worthless without an exceptional report:
                    Report Structure:
                    1. Executive Summary:
                       High-level overview written for non-technical C-suite leaders (CISO, CEO). Focuses on business risk, financial impact, and strategic priorities.
                    2. Assessment Scope & Methodology: Target IP ranges, URLs, testing dates, and constraints.
                    3. Detailed Findings Section (For each vulnerability):
                       - Title & Severity (Critical / High / Medium / Low / Informational).
                       - CVSS v3.1 Vector String (e.g. `CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H` = 9.8 Critical).
                       - Affected Endpoint/Host.
                       - Vulnerability Description & Root Cause.
                       - Proof of Concept (Step-by-step reproducible steps with screenshots and commands).
                       - Remediation Recommendations (Specific code/config fixes, not generic advice).
                       - Reference Links (CWE, OWASP, vendor patches).
                """.trimIndent(),
                keyTakeaways = "• A technical developer must be able to reproduce the vulnerability using only your report steps.\n• Executive summaries must never contain raw terminal dumps or code snippets.\n• Provide actionable, framework-specific remediation guidance.",
                commandsCode = "# Calculate CVSS score using online CVSS v3.1 calculator or CLI libraries\n# CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H",
                videoTitle = "How to Write a Professional Penetration Test Report",
                videoUrl = "https://www.youtube.com/watch?v=0kFqG1G98k8",
                videoChannel = "TCM Security",
                videoDuration = "32m",
                readTitle = "TCM Security Sample Penetration Testing Report Template",
                readUrl = "https://github.com/hmaverickadams/TCM-Security-Sample-Pentest-Report",
                readSource = "GitHub",
                labTitle = "Report Writing Practice - TryHackMe",
                labUrl = "https://tryhackme.com/room/pentestingfundamentals",
                labPlatform = "TryHackMe",
                labDescription = "Draft an executive summary and technical finding report for an assessment.",
                xpReward = 50
            )
            103 -> DayEntity(
                id = 103, phase = 6, dayNumber = 103,
                title = "Exam Simulation 1: Linux Web & PrivEsc Machine",
                subtitle = "Complete an unguided 4-hour box: Web enumeration, SQLi authentication bypass, and SUID/Cron privilege escalation.",
                concept = """
                    Simulated Exam Challenge 1 (Mimics OSCP / eJPT standalone target):
                    Target Objectives:
                    1. Perform full Nmap scan and service identification.
                    2. Enumerate web endpoints with Gobuster/Feroxbuster.
                    3. Discover SQL injection or file upload vulnerability to achieve initial user shell.
                    4. Upgrade shell to fully interactive TTY.
                    5. Enumerate local system with LinPEAS / manual checks.
                    6. Exploit misconfigured SUID binary or cron job to capture root.txt flag.
                    7. Document every command and screenshot for report submission.
                """.trimIndent(),
                keyTakeaways = "• Always take proof screenshots showing `whoami`, `id`, `ip a`, and `cat /root/root.txt`.\n• If stuck on one service for >45 minutes, pivot back to recon notes.\n• Treat every simulated lab machine as an official exam.",
                commandsCode = "# Document final proof flag\ncat /root/root.txt && id && hostname",
                videoTitle = "HackTheBox Academy: Penetration Testing Exam Simulation",
                videoUrl = "https://www.youtube.com/watch?v=WqmsS2f_sN0",
                videoChannel = "IppSec",
                videoDuration = "48m",
                readTitle = "TJ_Null's OSCP Like Boxes List Reference",
                readUrl = "https://www.netsecfocus.com/",
                readSource = "NetSecFocus",
                labTitle = "RootMe / Pickle Rick - TryHackMe",
                labUrl = "https://tryhackme.com/room/rpicklerick",
                labPlatform = "TryHackMe",
                labDescription = "Exploit command injection, traverse system files, and escalate to root without hints.",
                xpReward = 50
            )
            104 -> DayEntity(
                id = 104, phase = 6, dayNumber = 104,
                title = "Exam Simulation 2: Windows Web, Token PrivEsc & Potato Exploits",
                subtitle = "Exploit ASPX webshell upload on IIS, abuse `SeImpersonatePrivilege` with PrintSpoofer to get SYSTEM.",
                concept = """
                    Simulated Exam Challenge 2 (Windows standalone machine):
                    Target Objectives:
                    1. Port scan Windows target (ports 80, 135, 139, 445, 3389, 5985).
                    2. Fuzz web directory to discover vulnerable file upload.
                    3. Craft `.aspx` or `.ashx` reverse shell payload using MSFvenom.
                    4. Catch reverse shell with Netcat or multi/handler.
                    5. Run `whoami /priv` to identify `SeImpersonatePrivilege`.
                    6. Execute PrintSpoofer or GodPotato to obtain `NT AUTHORITY\\SYSTEM`.
                    7. Capture `C:\\Users\\Administrator\\Desktop\\root.txt`.
                """.trimIndent(),
                keyTakeaways = "• Windows webshells drop into `iis apppool` account with impersonation privileges.\n• Always verify architecture (x86 vs x64) before transferring compiled exploits.",
                commandsCode = "# Run whoami /priv to inspect privileges\nwhoami /priv",
                videoTitle = "Windows Machine Exploitation: IIS to SYSTEM Walkthrough",
                videoUrl = "https://www.youtube.com/watch?v=f0b8qWn1Vd4",
                videoChannel = "John Hammond",
                videoDuration = "36m",
                readTitle = "HackTricks: Windows Post-Exploitation Checklist",
                readUrl = "https://book.hacktricks.xyz/windows-hardening/checklist-windows-privilege-escalation",
                readSource = "HackTricks",
                labTitle = "Alfred Machine - TryHackMe",
                labUrl = "https://tryhackme.com/room/alfred",
                labPlatform = "TryHackMe",
                labDescription = "Exploit Jenkins CI, deploy reverse shell, and abuse token privileges with PrintSpoofer.",
                xpReward = 50
            )
            105 -> DayEntity(
                id = 105, phase = 6, dayNumber = 105,
                title = "Exam Simulation 3: Active Directory Domain Compromise (Full Chain)",
                subtitle = "Execute full AD path: LLMNR / AS-REP Roast -> BloodHound -> Lateral Movement -> DCSync -> Domain Admin.",
                concept = """
                    Simulated Exam Challenge 3 (Active Directory Set):
                    Chain of Attacks:
                    1. Enumerate domain users anonymously via SMB or Kerberos user enum (`kerbrute`).
                    2. AS-REP Roast users with `GetNPUsers.py` and crack offline with Hashcat.
                    3. Authenticate with cracked credential to execute SharpHound / bloodhound-python.
                    4. Identify that cracked user has local admin rights on Workstation01.
                    5. Connect via Evil-WinRM, dump LSASS to find Service Account credentials.
                    6. Kerberoast MSSQL service account with `GetUserSPNs.py`.
                    7. Identify ACL: Service account has `GenericAll` over Domain Controller!
                    8. Execute DCSync with `secretsdump.py` to dump `krbtgt` and Administrator hashes.
                    9. Pass-the-Hash to Domain Controller for total domain takeover.
                """.trimIndent(),
                keyTakeaways = "• Active Directory compromise is almost always a multi-step lateral chain.\n• Always prioritize dumping credentials from every newly compromised workstation.\n• DCSync extraction of `krbtgt` yields complete, persistent domain control.",
                commandsCode = "# Dump all domain hashes via DCSync with Domain Admin privileges\nsecretsdump.py corporate.local/administrator@10.10.10.1 -hashes :<NTLM_HASH>",
                videoTitle = "Active Directory Full Compromise Walkthrough: Recon to Domain Admin",
                videoUrl = "https://www.youtube.com/watch?v=2TzF1U2K6p8",
                videoChannel = "TCM Security",
                videoDuration = "52m",
                readTitle = "The Hacker Recipes: Active Directory Attack Paths",
                readUrl = "https://www.thehacker.recipes/ad/movement/",
                readSource = "The Hacker Recipes",
                labTitle = "Hololive / Wreath AD Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/attacktivedirectory",
                labPlatform = "TryHackMe",
                labDescription = "Execute complete Active Directory attack path from unauthenticated to Domain Admin.",
                xpReward = 50
            )
            119 -> DayEntity(
                id = 119, phase = 6, dayNumber = 119,
                title = "Technical Interview Mastery & Penetration Testing Portfolios",
                subtitle = "Prepare for technical interview questions, whiteboard scenario questions, and live CTF challenges.",
                concept = """
                    Landing your first job as a Penetration Tester or Security Consultant:
                    - Common Technical Interview Questions:
                      1. "Walk me through how Kerberos authentication works step-by-step."
                      2. "Explain the difference between Reflected, Stored, and DOM XSS."
                      3. "How would you explain an SQL injection vulnerability to a CFO vs a Lead Developer?"
                      4. "What are the first 5 commands you run when you get a low-privilege shell on Linux?"
                      5. "Explain how Pass-the-Hash works and why salting doesn't prevent it."
                    - Building an Impressive Portfolio:
                      * GitHub repository with custom security automation scripts (Python, Go, Bash).
                      * Public technical writeups of retired Hack The Box or CTF machines.
                      * Bug bounty acknowledgments (Hall of Fame).
                      * Professional sample pentest report showcasing clean formatting.
                """.trimIndent(),
                keyTakeaways = "• Soft skills and communication ability are evaluated just as rigorously as technical competence.\n• Practice explaining complex security vulnerabilities using simple analogies.\n• Be honest if you do not know an answer—explain how you would systematically research it.",
                commandsCode = "# Review core networking and Linux commands before your interview\nss -tulpn && ps aux && uname -a",
                videoTitle = "How to Pass an Ethical Hacking & Pentest Job Interview",
                videoUrl = "https://www.youtube.com/watch?v=kYv9N8F7Q8A",
                videoChannel = "TCM Security",
                videoDuration = "34m",
                readTitle = "Cybersecurity Interview Preparation Guide & Question Bank",
                readUrl = "https://github.com/griffin-t/cybersecurity-interview-questions",
                readSource = "GitHub",
                labTitle = "Interview Scenarios Practice Lab",
                labUrl = "https://tryhackme.com/room/jrpenetrationtester",
                labPlatform = "TryHackMe",
                labDescription = "Review foundational concepts and complete mock assessment challenges.",
                xpReward = 50
            )
            120 -> DayEntity(
                id = 120, phase = 6, dayNumber = 120,
                title = "120-Day Mastery Capstone: The Certified Ethical Hacker Transition",
                subtitle = "Celebrate your transition from complete beginner to job-ready penetration tester!",
                concept = """
                    🏆 CONGRATULATIONS! You have completed the 120-Day Ethical Hacking Mastery Program!

                    Over the last 120 days, you have systematically conquered:
                    - Phase 1: Linux CLI, Bash scripting, networking protocols (TCP/IP, OSI, DNS), and Python security automation.
                    - Phase 2: Passive OSINT, Certificate Transparency, Google Dorking, Shodan, Nmap port scanning, SMB & SNMP enumeration.
                    - Phase 3: Metasploit, reverse shell mechanics & PTY stabilization, SQLi, XSS, John/Hashcat cracking, buffer overflows, and Linux privilege escalation.
                    - Phase 4: OWASP Top 10, Burp Suite mastery, IDOR, SSRF, XXE, Command Injection, file upload webshells, and LFI log poisoning.
                    - Phase 5: Active Directory & Kerberos architecture, BloodHound attack graphs, AS-REP & Kerberoasting, LLMNR poisoning, Pass-the-Hash, Mimikatz, and network pivoting with Ligolo-ng.
                    - Phase 6: Professional report writing, CVSS scoring, multi-tier exam simulations, and technical interview readiness.

                    You possess the skills, knowledge, and mindset of a true ethical hacker. Continue learning, stay curious, and always hack responsibly!
                """.trimIndent(),
                keyTakeaways = "• You are now equipped to pursue OSCP, PNPT, or eJPT certifications.\n• Ethical hacking is a lifelong journey of continuous learning and ethical commitment.\n• Welcome to the cybersecurity community!",
                commandsCode = "# Final command:\necho 'HACKPATH_COMPLETED=TRUE' && whoami && date",
                videoTitle = "120 Days to Hacker: What Comes Next in Your Career",
                videoUrl = "https://www.youtube.com/watch?v=WqmsS2f_sN0",
                videoChannel = "NetworkChuck",
                videoDuration = "25m",
                readTitle = "PTES: Penetration Testing Execution Standard Complete Reference",
                readUrl = "http://www.pentest-standard.org/",
                readSource = "PTES",
                labTitle = "Final 120-Day Capstone Challenge Room",
                labUrl = "https://tryhackme.com/room/jrpenetrationtester",
                labPlatform = "TryHackMe",
                labDescription = "Complete the final hands-on network penetration test and claim your 120-Day badge.",
                xpReward = 200
            )
            else -> DayEntity(
                id = dayNum, phase = 6, dayNumber = dayNum,
                title = "Capstone Machine & Assessment Day $dayNum", subtitle = "Hands-on exam simulation and technical reporting.",
                concept = "Comprehensive penetration test simulation across multi-tier network environment.",
                keyTakeaways = "• Follow systematic methodology.\n• Document all steps.", commandsCode = "nmap -sC -sV target-ip",
                videoTitle = "Capstone Exam Walkthrough", videoUrl = "https://youtube.com", videoChannel = "IppSec", videoDuration = "30m",
                readTitle = "OffSec Exam Preparation Guide", readUrl = "https://help.offsec.com", readSource = "OffSec",
                labTitle = "Exam Lab $dayNum", labUrl = "https://tryhackme.com", labPlatform = "TryHackMe", labDescription = "Complete capstone challenge.", xpReward = 50
            )
        }
    }
}
