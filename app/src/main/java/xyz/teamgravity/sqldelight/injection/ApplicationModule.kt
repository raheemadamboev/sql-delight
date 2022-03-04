package xyz.teamgravity.sqldelight.injection

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.sqldelight.PersonDatabase
import xyz.teamgravity.sqldelight.data.local.PersonDao
import xyz.teamgravity.sqldelight.data.local.PersonDaoImpl
import xyz.teamgravity.sqldelight.data.repository.PersonRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver =
        AndroidSqliteDriver(
            schema = PersonDatabase.Schema,
            context = app,
            name = "person.db"
        )

    @Provides
    @Singleton
    fun providePersonDatabase(driver: SqlDriver) = PersonDatabase(driver)

    @Provides
    @Singleton
    fun providePersonDao(db: PersonDatabase): PersonDao = PersonDaoImpl(db)

    @Provides
    @Singleton
    fun providePersonRepository(dao: PersonDao): PersonRepository = PersonRepository(dao)
}