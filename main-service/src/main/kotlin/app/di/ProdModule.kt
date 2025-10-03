package app.di

import com.axus.id.Session
import infrastructure.persistence.DatabaseFactory
import org.koin.dsl.module

val prodModule = module {
    single {
        Session(
            System.getenv("WINELORE_AUID")!!.toLong(),
            System.getenv("WINELORE_REFRESH_TOKEN")!!
        )
    }

    single {
        DatabaseFactory.apply {
            init()
        }
    }
}
