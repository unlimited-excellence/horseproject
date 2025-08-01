package app.di

import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module


object ModuleLoader {
    fun configureDI() {
        startKoin {
            modules(loadEnvironmentSpecificModule(), loadUniversalModule())
        }
    }

    fun loadEnvironmentSpecificModule(): Module {
        val environment = System.getenv("ENVIRONMENT") ?: "dev"
        return when(environment.lowercase()) {
            "prod", "production" -> prodModule
            else -> devModule
        }
    }

    fun loadUniversalModule(): Module = universalModule
}