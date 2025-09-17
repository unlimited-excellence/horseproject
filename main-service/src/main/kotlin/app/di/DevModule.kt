package app.di

import com.axus.id.Session
import infrastructure.persistence.DatabaseFactory
import org.koin.dsl.module

val devModule = module {
    single { Session.createWithPassword(21, "hudhawWADhlawdaxfnwlAWdhawu") }
    single {
        DatabaseFactory(
            url = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:5432/postgres",
            user = "postgres.acclahyabmvwgmhtbhzf",
            password = "06TzniO10CQvLlDo"
        )
    }
}