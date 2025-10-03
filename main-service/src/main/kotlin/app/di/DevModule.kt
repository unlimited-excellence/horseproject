package app.di

import com.axus.id.Session
import infrastructure.persistence.DatabaseFactory
import org.koin.dsl.module

val devModule = module {
    single { Session.createWithPassword(21, "hudhawWADhlawdaxfnwlAWdhawu") }

    single {
        DatabaseFactory.apply {
            init()
        }
    }
}
