package com.example.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.HackPathViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Dashboard)
    object Curriculum : Screen("curriculum", "120 Days", Icons.Default.FormatListNumbered)
    object SkillTree : Screen("skills", "Skill Tree", Icons.Default.AccountTree)
    object Resources : Screen("resources", "Arsenal", Icons.Default.MenuBook)
    object Profile : Screen("profile", "Profile", Icons.Default.Security)
    object DayDetail : Screen("day_detail/{dayId}", "Day Detail", Icons.Default.Article) {
        fun createRoute(dayId: Int) = "day_detail/$dayId"
    }
    object Terminal : Screen("terminal", "Sandbox", Icons.Default.Terminal)
}

@Composable
fun MainScreen(viewModel: HackPathViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        Screen.Dashboard,
        Screen.Curriculum,
        Screen.SkillTree,
        Screen.Resources,
        Screen.Profile
    )

    val isBottomBarVisible = bottomNavItems.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                NavigationBar(
                    containerColor = SurfaceDark,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    bottomNavItems.forEach { screen ->
                        val isSelected = currentDestination?.route == screen.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.title,
                                    tint = if (isSelected) NeonMint else TextMuted
                                )
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 10.sp
                                    ),
                                    color = if (isSelected) NeonMint else TextMuted
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = SurfaceElevated
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    viewModel = viewModel,
                    onNavigateToDay = { dayId ->
                        navController.navigate(Screen.DayDetail.createRoute(dayId))
                    },
                    onNavigateToCurriculum = {
                        navController.navigate(Screen.Curriculum.route)
                    },
                    onNavigateToSkills = {
                        navController.navigate(Screen.SkillTree.route)
                    },
                    onNavigateToResources = {
                        navController.navigate(Screen.Resources.route)
                    },
                    onNavigateToTerminal = {
                        navController.navigate(Screen.Terminal.route)
                    }
                )
            }

            composable(Screen.Curriculum.route) {
                CurriculumScreen(
                    viewModel = viewModel,
                    onNavigateToDay = { dayId ->
                        navController.navigate(Screen.DayDetail.createRoute(dayId))
                    }
                )
            }

            composable(Screen.SkillTree.route) {
                SkillTreeScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Resources.route) {
                ResourceLibraryScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.DayDetail.route,
                arguments = listOf(navArgument("dayId") { type = NavType.IntType })
            ) { backStackEntry ->
                val dayId = backStackEntry.arguments?.getInt("dayId") ?: 1
                DayDetailScreen(
                    dayId = dayId,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToDay = { newDayId ->
                        navController.navigate(Screen.DayDetail.createRoute(newDayId)) {
                            popUpTo(Screen.DayDetail.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Terminal.route) {
                TerminalSandboxScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
